package com.mb.ext.core.task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.constant.FundConstants;
import com.mb.ext.core.constant.ProfitConstants;
import com.mb.ext.core.dao.UserAwardDAO;
import com.mb.ext.core.dao.UserBalanceDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.UserPerformanceDAO;
import com.mb.ext.core.dao.UserStatementDAO;
import com.mb.ext.core.dao.profit.ProfitDistributionDAO;
import com.mb.ext.core.dao.profit.ProfitPerformanceDAO;
import com.mb.ext.core.entity.UserAwardEntity;
import com.mb.ext.core.entity.UserBalanceEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserPerformanceEntity;
import com.mb.ext.core.entity.UserStatementEntity;
import com.mb.ext.core.entity.profit.ProfitDistributionEntity;
import com.mb.ext.core.entity.profit.ProfitPerformanceEntity;
import com.mb.ext.core.util.PaymentUtil;
import com.mb.ext.msg.WxSuscribeMessageSender;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Component
@EnableScheduling
public class UserPerformanceAwardTask {

	@Autowired
	UserPerformanceDAO userPerformanceDAO;

	@Autowired
	ProfitPerformanceDAO profitPerformanceDAO;

	@Autowired
	UserDAO userDAO;

	@Autowired
	UserBalanceDAO userBalanceDAO;
	
	@Autowired
	UserStatementDAO userStatementDAO;

	@Autowired
	UserAwardDAO userAwardDAO;
	
	@Autowired
	ProfitDistributionDAO profitDistributionDAO;
	
	@Autowired
	WxSuscribeMessageSender wxSuscribeMessageSender;
	
	@Autowired
	PaymentUtil paymentUtil;

	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * 团队业绩奖金每月计算一次
	 */
	@Scheduled(cron = "40 0 0 1 * ?") // 每月1号凌晨执行
//	@Scheduled(cron = "0 * * * * ?") 
	@Transactional
	public void run() {
		
		//开启三级分销体系后不计算团队业绩奖金
		try {
			ProfitDistributionEntity profitDistributionEntity = profitDistributionDAO.getProfitDistribution();
			if(profitDistributionEntity != null && profitDistributionEntity.isDistributionEnabled()) {
				logger.info("已开启三级分销, 不计算团队业绩奖金");
				return;
			}
		}catch(DAOException e) {
			logger.error("获取三级分销设置发生异常: "+e.getMessage());
		}
		
		logger.info("-------------------开始执行计算团队业绩奖金计划任务------------------------");
		Date currDate = new Date();
		BigDecimal zero = new BigDecimal(0);
		Date performanceDate = DateUtils.addMonths(currDate, -1);
		int pageNo = 1;
		int pageSize = 10;
		try {
			int totalUserPerformance = userPerformanceDAO.getUserPerformanceTotal(performanceDate);
			while (((pageNo - 1) * pageSize) < totalUserPerformance) {
				List<UserPerformanceEntity> userPerformanceEntityList = userPerformanceDAO
						.getUserPerformancesPagination(performanceDate, (pageNo - 1) * pageSize, pageSize);
				for (Iterator<UserPerformanceEntity> iterator = userPerformanceEntityList.iterator(); iterator
						.hasNext();) {
					UserPerformanceEntity userPerformanceEntity = iterator.next();
					BigDecimal performanceAmount = userPerformanceEntity.getPerformanceAmount();
					List<ProfitPerformanceEntity> profitPerformanceEntitylist = profitPerformanceDAO.getProfitPerformanceByUserLevel(userPerformanceEntity.getUserEntity().getUserLevelEntity());
					//计算应得奖金
					BigDecimal performanceAward = calculatePerformanceAward(profitPerformanceEntitylist, performanceAmount);
					
					// 计算下级应得奖金, 减去后得到实际应得奖金
					List<UserEntity> childUserList = userDAO.getL1ChildUsers(userPerformanceEntity.getUserEntity());
					for (Iterator<UserEntity> childUserIterator = childUserList.iterator(); childUserIterator
							.hasNext();) {
						UserEntity childUserEntity = childUserIterator.next();
						List<ProfitPerformanceEntity> childProfitPerformanceEntitylist = profitPerformanceDAO.getProfitPerformanceByUserLevel(childUserEntity.getUserLevelEntity());
						UserPerformanceEntity childUserPerformanceEntity = userPerformanceDAO
								.getPerformanceByUserDate(childUserEntity, performanceDate);
						if (childUserPerformanceEntity != null) {
							BigDecimal childPerformanceAmount = childUserPerformanceEntity.getPerformanceAmount();// 下级当月业绩金额
							BigDecimal childPerformanceAward = calculatePerformanceAward(childProfitPerformanceEntitylist, childPerformanceAmount);
							performanceAward = performanceAward.subtract(childPerformanceAward);
						}
					}
					if (performanceAward.compareTo(zero) > 0) {
						userPerformanceEntity.setPerformanceAward(performanceAward);
						userPerformanceDAO.updateUserPerformance(userPerformanceEntity);

						// 更新奖金总额和可提现余额
						UserEntity userEntity = userPerformanceEntity.getUserEntity();
						UserBalanceEntity userBalance = userEntity.getUserBalanceEntity();
						
						BigDecimal balanceBefore = userBalance.getAvailableBalance();	
						BigDecimal balanceAfter = userBalance.getAvailableBalance().add(performanceAward);
						
						userBalance.setAvailableBalance(performanceAward.add(userBalance.getAvailableBalance()));
						userBalance.setUpdateBy("system");
						userBalanceDAO.updateUserBalance(userBalance);
						// 添加奖金明细
						UserAwardEntity awardEntity = new UserAwardEntity();
						awardEntity.setTransactionType(ProfitConstants.AWARD_TRANTYPE_PERFORMANCE);
						awardEntity.setTransactionCode("");
						awardEntity.setTransactionTime(new Date());
						awardEntity.setTransactionAmount(performanceAward);
						String description = (performanceDate.getYear() + 1900) + "年" + (performanceDate.getMonth() + 1)
								+ "月" + "团队业绩奖金";
						awardEntity.setTransactionDesc(description);
						awardEntity.setUserEntity(userEntity);
						awardEntity.setCreateBy("system");
						awardEntity.setUpdateBy("system");
						userAwardDAO.createUserAward(awardEntity);
						
						//更新对账单
						UserStatementEntity statementEntity = new UserStatementEntity();
						statementEntity.setUserEntity(userEntity);
						statementEntity.setTransactionAmount(performanceAward);
						statementEntity.setTransactionType(FundConstants.USER_STATEMENT_TRANSACTION_TYPE_PERFORMANCE);
						statementEntity.setTransactionCode("");
						statementEntity.setTransactionTime(new Date());
						statementEntity.setBalanceBefore(balanceBefore);
						statementEntity.setBalanceAfter(balanceAfter);
						statementEntity.setCreateBy("system");
						statementEntity.setUpdateBy("system");
						userStatementDAO.createUserStatement(statementEntity);
						
						//发送奖励通知订阅消息
						wxSuscribeMessageSender.sendAwardMsg(paymentUtil.getWechatAppIdMiniProgram(), paymentUtil.getWechatAppSecretMiniProgram(), userEntity.getOpenId(), awardEntity.getTransactionType(),String.valueOf(awardEntity.getTransactionAmount()),awardEntity.getTransactionDesc());
					}

				}
				pageNo++;
			}

		} catch (DAOException e) {
			e.printStackTrace();
			logger.error("计划任务团队业绩奖金执行失败:" + e.getMessage());
		}
		logger.info("-------------------执行计算团队业绩奖金计划任务结束------------------------");
	}

	BigDecimal calculatePerformanceLevel(List<ProfitPerformanceEntity> profitPerformanceEntitylist,
			BigDecimal performanceAmount) {
		BigDecimal rating = new BigDecimal(0);
		for (Iterator<ProfitPerformanceEntity> iterator = profitPerformanceEntitylist.iterator(); iterator.hasNext();) {
			ProfitPerformanceEntity profitPerformanceEntity = (ProfitPerformanceEntity) iterator.next();
			if (performanceAmount.compareTo(profitPerformanceEntity.getAmount()) >= 0) {
				BigDecimal performanceRating = profitPerformanceEntity.getRating();
				if (performanceRating.compareTo(rating) > 0)
					rating = performanceRating;
			}

		}
		return rating;
	}

	BigDecimal calculatePerformanceAward(List<ProfitPerformanceEntity> profitPerformanceEntityList, BigDecimal performanceAmount) {
		BigDecimal rating = calculatePerformanceLevel(profitPerformanceEntityList,performanceAmount);
		BigDecimal performanceAward = performanceAmount.multiply(rating.divide(new BigDecimal(100)));
		return performanceAward;
	}
	public static void main(String[] args) {
		List<ProfitPerformanceEntity> profitPerformanceEntityList = new ArrayList<ProfitPerformanceEntity>();
		ProfitPerformanceEntity e1 = new ProfitPerformanceEntity();
		e1.setAmount(new BigDecimal(33000));
		e1.setRating(new BigDecimal(5));
		
		ProfitPerformanceEntity e2 = new ProfitPerformanceEntity();
		e2.setAmount(new BigDecimal(99000));
		e2.setRating(new BigDecimal(10));
		
		ProfitPerformanceEntity e3 = new ProfitPerformanceEntity();
		e3.setAmount(new BigDecimal(330000));
		e3.setRating(new BigDecimal(15));
		
		ProfitPerformanceEntity e4 = new ProfitPerformanceEntity();
		e4.setAmount(new BigDecimal(990000));
		e4.setRating(new BigDecimal(20));
		profitPerformanceEntityList.add(e4);
		profitPerformanceEntityList.add(e3);
		profitPerformanceEntityList.add(e2);
		profitPerformanceEntityList.add(e1);
	}
}
