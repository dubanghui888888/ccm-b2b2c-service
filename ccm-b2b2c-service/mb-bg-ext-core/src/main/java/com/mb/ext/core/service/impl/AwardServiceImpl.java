package com.mb.ext.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.constant.FundConstants;
import com.mb.ext.core.constant.OrderConstants;
import com.mb.ext.core.constant.ProfitConstants;
import com.mb.ext.core.dao.FileDAO;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.SettingDAO;
import com.mb.ext.core.dao.UserAwardDAO;
import com.mb.ext.core.dao.UserBalanceDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.UserInventoryDAO;
import com.mb.ext.core.dao.UserInventoryHistoryDAO;
import com.mb.ext.core.dao.UserLevelDAO;
import com.mb.ext.core.dao.UserPerformanceDAO;
import com.mb.ext.core.dao.UserStatementDAO;
import com.mb.ext.core.dao.UserTreeDAO;
import com.mb.ext.core.dao.merchant.PlatformBalanceDAO;
import com.mb.ext.core.dao.merchant.PlatformStatementDAO;
import com.mb.ext.core.dao.order.OrderDAO;
import com.mb.ext.core.dao.profit.ProfitDistributionDAO;
import com.mb.ext.core.dao.profit.ProfitRecruitDAO;
import com.mb.ext.core.dao.profit.ProfitSaleDAO;
import com.mb.ext.core.entity.UserAwardEntity;
import com.mb.ext.core.entity.UserBalanceEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserLevelEntity;
import com.mb.ext.core.entity.UserPerformanceEntity;
import com.mb.ext.core.entity.UserStatementEntity;
import com.mb.ext.core.entity.order.OrderEntity;
import com.mb.ext.core.entity.profit.ProfitDistributionEntity;
import com.mb.ext.core.entity.profit.ProfitRecruitEntity;
import com.mb.ext.core.entity.profit.ProfitSaleEntity;
import com.mb.ext.core.service.AwardService;
import com.mb.ext.core.service.NoteService;
import com.mb.ext.core.service.ProfitService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.OrderSearchDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.util.PaymentUtil;
import com.mb.ext.core.util.SMSSenderUtil;
import com.mb.ext.msg.WxSuscribeMessageSender;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;

@Service
@Qualifier("AwardService")
public class AwardServiceImpl extends AbstractService implements AwardService<BodyDTO> {

	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("userLevelDAO")
	private UserLevelDAO userLevelDAO;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("userTreeDAO")
	private UserTreeDAO userTreeDAO;
	
	@Autowired
	@Qualifier("userStatementDAO")
	private UserStatementDAO userStatementDAO;
	
	@Autowired
	private WxSuscribeMessageSender wxSuscribeMessageSender;
	
	@Autowired
	private PaymentUtil paymentUtil;

	@Autowired
	@Qualifier("userInventoryDAO")
	private UserInventoryDAO userInventoryDAO;

	@Autowired
	@Qualifier("ProfitService")
	private ProfitService profitService;

	@Autowired
	@Qualifier("profitSaleDAO")
	private ProfitSaleDAO profitSaleDAO;

	@Autowired
	private SMSSenderUtil smsSenderUtil;

	@Autowired
	@Qualifier("profitRecruitDAO")
	private ProfitRecruitDAO profitRecruitDAO;

	@Autowired
	@Qualifier("userInventoryHistoryDAO")
	private UserInventoryHistoryDAO userInventoryHistoryDAO;

	@Autowired
	@Qualifier("userAwardDAO")
	private UserAwardDAO userAwardDAO;

	@Autowired
	@Qualifier("userBalanceDAO")
	private UserBalanceDAO userBalanceDAO;

	@Autowired
	@Qualifier("userPerformanceDAO")
	private UserPerformanceDAO userPerformanceDAO;

	@Autowired
	@Qualifier("fileDAO")
	private FileDAO fileDAO;

	@Autowired
	@Qualifier("settingDAO")
	private SettingDAO settingDAO;

	@Autowired
	@Qualifier("sequenceDAO")
	private SequenceDAO sequenceDAO;

	@Autowired
	@Qualifier("orderDAO")
	private OrderDAO orderDAO;

	@Autowired
	@Qualifier("platformBalanceDAO")
	private PlatformBalanceDAO platformBalanceDAO;

	@Autowired
	@Qualifier("platformStatementDAO")
	private PlatformStatementDAO platformStatementDAO;
	
	@Autowired
	@Qualifier("profitDistributionDAO")
	private ProfitDistributionDAO profitDistributionDAO;

	@Autowired
	@Qualifier("NoteService")
	private NoteService noteService;

	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	@Override
	public void processAward(String orderNo) throws BusinessException {

		try {

			OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderNo);
			UserEntity userEntity = orderEntity.getUserEntity();
			/* 当前会员等级 */
			UserLevelEntity currentUserLevelEntity = userEntity.getUserLevelEntity();

			/* 是否产生推广收益标记 */
			boolean isProfitRecruitGenerated = false;
			/* 是否产生销售收益标记 */
			boolean isProfitSaleGenerated = true;

			/* 是否达到升级会员等级条件 */
			UserLevelEntity nextUserLevelEntity = userLevelDAO.getParentUserLevel(currentUserLevelEntity);
			if (isUserLevelArchived(userEntity, nextUserLevelEntity)) {
				/* 只有从免费会员升级至具有推广或提成的会员等级才会产生推广收益 */
				if (!currentUserLevelEntity.isRecruitProfitEnabled() && !currentUserLevelEntity.isSaleProfitEnabled()) {
					isProfitRecruitGenerated = true;
					isProfitSaleGenerated = false;
				}
			}
			// 推广收益
			if (isProfitRecruitGenerated)
				processRecruitAward(orderEntity, nextUserLevelEntity);
			// 销售收益
			if (isProfitSaleGenerated) {
				/* 计算团队月销售额 */
				processUserPerformance(orderEntity);
				/* 计算销售收益 */
				processSaleAward(orderEntity);
			}

		} catch (DAOException e) {
			throw new BusinessException(e);
		}
	}
	
	@Override
	public void processDistribution(String orderNo) throws BusinessException {
		try {
			OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderNo);
			processDistributionAward(orderEntity);
		} catch (DAOException e) {
			logger.error("处理订单号"+orderNo+"分销佣金时发生异常:"+e.getMessage());
			throw new BusinessException(e);
		}
	}
	
	/**分销佣金
	 * @param orderEntity
	 * @throws BusinessException
	 */
	private void processDistributionAward(OrderEntity orderEntity) throws BusinessException {
		if (orderEntity == null)
			return;
		try {
			/* 用实际支付金额来计算分销佣金 */
			String orderNo = orderEntity.getOrderNo();
			BigDecimal orderAmount = orderEntity.getActualAmount();
			//佣金类型
			String awardType = ProfitConstants.AWARD_TRANTYPE_SALE;
			UserEntity userEntity = orderEntity.getUserEntity();
			
			ProfitDistributionEntity distributionEntity = profitDistributionDAO.getProfitDistribution();
			if(distributionEntity == null || !distributionEntity.isDistributionEnabled())
				return;
			int distributionLevel = distributionEntity.getDistributionLevel();
			UserEntity level1User = userEntity.getInvitationUser();	//上级
			UserEntity level2User = level1User == null ? null:level1User.getInvitationUser();//上上级
			UserEntity level3User = level2User == null ? null:level2User.getInvitationUser();//上上上级
			BigDecimal level1Rate = distributionEntity.getLevel1Rate();	//一级分佣比例
			BigDecimal level2Rate = distributionEntity.getLevel2Rate();	//二级分佣比例
			BigDecimal level3Rate = distributionEntity.getLevel3Rate();	//三级分佣比例
			//订单描述
			String description = "会员"+(userEntity.getName()==null?"":userEntity.getName())+(userEntity.getPersonalPhone()==null?"":userEntity.getPersonalPhone()) + "购买商品" + orderEntity.getProductUnit() + "件,实际支付金额"
					+ orderAmount + "元";
			//第一级分佣
			if(distributionLevel>=1 && level1User != null && level1Rate != null) {
				/* 分佣金额 */
				BigDecimal profit = orderAmount.multiply(level1Rate).divide(BigDecimal.valueOf(100), 2,
						BigDecimal.ROUND_HALF_UP);
				BigDecimal balanceBefore = level1User.getUserBalanceEntity().getAvailableBalance();	
				BigDecimal balanceAfter = level1User.getUserBalanceEntity().getAvailableBalance().add(profit);

				// 更新收益余额
				updateUserBalance(level1User, balanceAfter);
				
				// 添加收益明细
				UserAwardEntity awardEntity = createUserAward(orderNo, level1User, profit, description, awardType);
				
				//更新对账单
				createUserStatement(orderNo, level1User, profit, balanceBefore, balanceAfter, FundConstants.USER_STATEMENT_TRANSACTION_TYPE_SALE);
				
				//发送奖励通知订阅消息
				wxSuscribeMessageSender.sendAwardMsg(paymentUtil.getWechatAppIdMiniProgram(), paymentUtil.getWechatAppSecretMiniProgram(), awardEntity.getUserEntity().getOpenId(), awardEntity.getTransactionType(),String.valueOf(awardEntity.getTransactionAmount()),awardEntity.getTransactionDesc());
			}
			//第二级分佣
			if(distributionLevel>=2 && level2User != null && level2Rate != null) {
				/* 分佣金额 */
				BigDecimal profit = orderAmount.multiply(level2Rate).divide(BigDecimal.valueOf(100), 2,
						BigDecimal.ROUND_HALF_UP);
				BigDecimal balanceBefore = level2User.getUserBalanceEntity().getAvailableBalance();	
				BigDecimal balanceAfter = level2User.getUserBalanceEntity().getAvailableBalance().add(profit);

				// 更新收益余额
				updateUserBalance(level2User, balanceAfter);
				
				// 添加收益明细
				UserAwardEntity awardEntity = createUserAward(orderNo, level2User, profit, description, awardType);
				
				//更新对账单
				createUserStatement(orderNo, level2User, profit, balanceBefore, balanceAfter, FundConstants.USER_STATEMENT_TRANSACTION_TYPE_SALE);
				
				//发送奖励通知订阅消息
				wxSuscribeMessageSender.sendAwardMsg(paymentUtil.getWechatAppIdMiniProgram(), paymentUtil.getWechatAppSecretMiniProgram(), awardEntity.getUserEntity().getOpenId(), awardEntity.getTransactionType(),String.valueOf(awardEntity.getTransactionAmount()),awardEntity.getTransactionDesc());
			}
			//第三级分佣
			if(distributionLevel>=3 && level3User != null && level3Rate != null) {
				/* 分佣金额 */
				BigDecimal profit = orderAmount.multiply(level3Rate).divide(BigDecimal.valueOf(100), 2,
						BigDecimal.ROUND_HALF_UP);
				BigDecimal balanceBefore = level3User.getUserBalanceEntity().getAvailableBalance();	
				BigDecimal balanceAfter = level3User.getUserBalanceEntity().getAvailableBalance().add(profit);

				// 更新收益余额
				updateUserBalance(level3User, balanceAfter);
				
				// 添加收益明细
				UserAwardEntity awardEntity = createUserAward(orderNo, level3User, profit, description, awardType);
				
				//更新对账单
				createUserStatement(orderNo, level3User, profit, balanceBefore, balanceAfter, FundConstants.USER_STATEMENT_TRANSACTION_TYPE_SALE);
				
				//发送奖励通知订阅消息
				wxSuscribeMessageSender.sendAwardMsg(paymentUtil.getWechatAppIdMiniProgram(), paymentUtil.getWechatAppSecretMiniProgram(), awardEntity.getUserEntity().getOpenId(), awardEntity.getTransactionType(),String.valueOf(awardEntity.getTransactionAmount()),awardEntity.getTransactionDesc());
			}
		} catch (DAOException e) {
			throw new BusinessException(e);
		}
	}
	
	private void updateUserBalance(UserEntity userEntity, BigDecimal availableBalance) throws DAOException {
		UserBalanceEntity balanceEntity = userEntity.getUserBalanceEntity();
		balanceEntity.setAvailableBalance(availableBalance);
		userBalanceDAO.updateUserBalance(balanceEntity);
	}

	private void createUserStatement(String tranCode, UserEntity userEntity, BigDecimal tranAmount, BigDecimal balanceBefore,
			BigDecimal balanceAfter, String tranType) throws DAOException {
		UserStatementEntity statementEntity = new UserStatementEntity();
		statementEntity.setUserEntity(userEntity);
		statementEntity.setTransactionAmount(tranAmount);
		statementEntity.setTransactionType(tranType);
		statementEntity.setTransactionCode(tranCode);
		statementEntity.setTransactionTime(new Date());
		statementEntity.setBalanceBefore(balanceBefore);
		statementEntity.setBalanceAfter(balanceAfter);
		statementEntity.setCreateBy(userEntity.getId());
		statementEntity.setUpdateBy(userEntity.getId());
		userStatementDAO.createUserStatement(statementEntity);
	}
	
	private UserAwardEntity createUserAward(String tranCode, UserEntity userEntity, BigDecimal tranAmount,
			String tranDesc, String tranType) throws DAOException {
		UserAwardEntity awardEntity = new UserAwardEntity();
		awardEntity.setTransactionAmount(tranAmount);
		awardEntity.setTransactionCode(tranCode);
		awardEntity.setTransactionType(tranType);
		awardEntity.setTransactionTime(new Date());
		awardEntity.setTransactionDesc(tranDesc);
		awardEntity.setUserEntity(userEntity);

		awardEntity.setCreateBy(userEntity.getId());
		awardEntity.setUpdateBy(userEntity.getId());
		userAwardDAO.createUserAward(awardEntity);
		return awardEntity;
	}
	
	@Override
	public void processUpgrade(UserDTO userDTO) throws BusinessException {

		try {

			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			
			List<UserEntity> userList =  userTreeDAO.getParentUsers(userEntity);
			
			/*处理该会员及上级会员升级*/
			for (UserEntity user : userList) {
				processUserUpgrade(user);
			}
			/*生成会员树*/
			for (UserEntity user : userList) {
				userService.buildUserTree(user);
			}
			
		} catch (DAOException e) {
			throw new BusinessException(e);
		}
	}

	private void processUserUpgrade(UserEntity userEntity)
			throws BusinessException {
		try {
			if (userEntity != null) {
				/* 当前会员等级 */
				UserLevelEntity currentUserLevelEntity = userEntity.getUserLevelEntity();

				/* 是否达到升级会员等级条件 */
				UserLevelEntity nextUserLevelEntity = userLevelDAO.getParentUserLevel(currentUserLevelEntity);
				
				UserEntity supervisor = userEntity.getSupervisorL1(); // 会员上级
				if (isUserLevelArchived(userEntity, nextUserLevelEntity)) {
					/* 更新会员等级 */
					userEntity.setUserLevelEntity(nextUserLevelEntity);
					/* 升级之后, 上级发生改变 */
					if (supervisor != null) {
						userEntity.setSupervisorL1(supervisor.getSupervisorL1());
					}
					userDAO.updateUser(userEntity);
				}
			}
		} catch (DAOException e) {
			throw new BusinessException(e);
		}
	}

	private void processUserPerformance(OrderEntity orderEntity) throws BusinessException {
		try {
			UserEntity userEntity = orderEntity.getUserEntity();
			Date currDate = new Date();
			currDate.setDate(1);
			UserEntity supervisorEntity = userEntity;
			while (supervisorEntity != null) {
				UserPerformanceEntity userPerformanceEntity = userPerformanceDAO
						.getPerformanceByUserDate(supervisorEntity, currDate);
				if (userPerformanceEntity == null) {
					userPerformanceEntity = new UserPerformanceEntity();
					userPerformanceEntity.setUserEntity(supervisorEntity);
					userPerformanceEntity.setPerformanceDate(currDate);
					userPerformanceEntity.setPerformanceAmount(orderEntity.getProductAmount());
					userPerformanceEntity.setCreateBy(userEntity.getId());
					userPerformanceEntity.setUpdateBy(userEntity.getId());
					userPerformanceDAO.createUserPerformance(userPerformanceEntity);
				} else {
					userPerformanceEntity.setPerformanceAmount(
							userPerformanceEntity.getPerformanceAmount().add(orderEntity.getProductAmount()));
					userPerformanceDAO.updateUserPerformance(userPerformanceEntity);
				}
				supervisorEntity = supervisorEntity.getSupervisorL1();
			}
		} catch (DAOException e) {
			throw new BusinessException(e);
		}
	}

	private void processSaleAward(OrderEntity orderEntity) throws BusinessException {
		if (orderEntity == null)
			return;
		try {
			/* 用实际支付金额来计算销售收益 */
			BigDecimal orderAmount = orderEntity.getActualAmount();
			UserEntity userEntity = orderEntity.getUserEntity();
			UserEntity supervisorEntity = userEntity.getSupervisorL1();
			while (supervisorEntity != null) {
				/* 获取提成比例参数设置 */
				ProfitSaleEntity profitSaleEntity = profitSaleDAO
						.getProfitSaleByUserLevel(supervisorEntity.getUserLevelEntity().getUserLevelUuid());
				if (profitSaleEntity != null) {
					/* 提成率 */
					BigDecimal profitRate = profitSaleEntity.getProfitRate();
					/* 提成金额 */
					BigDecimal profit = orderAmount.multiply(profitRate).divide(BigDecimal.valueOf(100), 2,
							BigDecimal.ROUND_HALF_UP);
					
					BigDecimal balanceBefore = userEntity.getUserBalanceEntity().getAvailableBalance();	
					BigDecimal balanceAfter = userEntity.getUserBalanceEntity().getAvailableBalance().add(profit);

					// 更新收益余额
					UserBalanceEntity balanceEntity = supervisorEntity.getUserBalanceEntity();
					balanceEntity.setAvailableBalance(profit.add(balanceEntity.getAvailableBalance()));
					userBalanceDAO.updateUserBalance(balanceEntity);

					// 更新收益明细
					UserAwardEntity awardEntity = new UserAwardEntity();
					awardEntity.setTransactionAmount(profit);
					awardEntity.setTransactionCode(orderEntity.getOrderNo());
					awardEntity.setTransactionType(ProfitConstants.AWARD_TRANTYPE_SALE);
					awardEntity.setTransactionTime(new Date());
					String description = "会员"+(userEntity.getName()==null?"":userEntity.getName())+(userEntity.getPersonalPhone()==null?"":userEntity.getPersonalPhone()) + "购买商品" + orderEntity.getProductUnit() + "件,实际支付金额"
							+ orderAmount + "元";
					awardEntity.setTransactionDesc(description);
					awardEntity.setUserEntity(supervisorEntity);

					awardEntity.setCreateBy(userEntity.getId());
					awardEntity.setUpdateBy(userEntity.getId());
					userAwardDAO.createUserAward(awardEntity);
					
					//更新对账单
					UserStatementEntity statementEntity = new UserStatementEntity();
					statementEntity.setUserEntity(supervisorEntity);
					statementEntity.setTransactionAmount(profit);
					statementEntity.setTransactionType(FundConstants.USER_STATEMENT_TRANSACTION_TYPE_SALE);
					statementEntity.setTransactionCode(orderEntity.getOrderNo());
					statementEntity.setTransactionTime(new Date());
					statementEntity.setBalanceBefore(balanceBefore);
					statementEntity.setBalanceAfter(balanceAfter);
					statementEntity.setCreateBy(orderEntity.getUserEntity().getId());
					statementEntity.setUpdateBy(orderEntity.getUserEntity().getId());
					userStatementDAO.createUserStatement(statementEntity);
					
					//发送奖励通知订阅消息
					wxSuscribeMessageSender.sendAwardMsg(paymentUtil.getWechatAppIdMiniProgram(), paymentUtil.getWechatAppSecretMiniProgram(), awardEntity.getUserEntity().getOpenId(), awardEntity.getTransactionType(),String.valueOf(awardEntity.getTransactionAmount()),awardEntity.getTransactionDesc());
				}
				supervisorEntity = supervisorEntity.getSupervisorL1();
			}
		} catch (DAOException e) {
			throw new BusinessException(e);
		}
	}

	private void processRecruitAward(OrderEntity orderEntity, UserLevelEntity nextUserLevelEntity)
			throws BusinessException {
		try {
			if (orderEntity == null || nextUserLevelEntity == null)
				return;
			UserEntity userEntity = orderEntity.getUserEntity();
			UserEntity supervisorEntity = userEntity.getSupervisorL1();
			while (supervisorEntity != null) {
				/* 获取提成参数设置 */
				ProfitRecruitEntity profitRecruitEntity = profitRecruitDAO.getProfitRecruit(
						nextUserLevelEntity.getUserLevelUuid(),
						supervisorEntity.getUserLevelEntity().getUserLevelUuid());
				if (profitRecruitEntity != null) {
					/* 提成金额 */
					BigDecimal profit = profitRecruitEntity.getProfit();
					BigDecimal balanceBefore = userEntity.getUserBalanceEntity().getAvailableBalance();	
					BigDecimal balanceAfter = userEntity.getUserBalanceEntity().getAvailableBalance().add(profit);
					// 更新收益余额
					UserBalanceEntity balanceEntity = supervisorEntity.getUserBalanceEntity();
					balanceEntity.setAvailableBalance(profit.add(balanceEntity.getAvailableBalance()));
					userBalanceDAO.updateUserBalance(balanceEntity);

					// 更新收益明细
					UserAwardEntity awardEntity = new UserAwardEntity();
					awardEntity.setTransactionAmount(profit);
					awardEntity.setTransactionCode(orderEntity.getOrderNo());
					awardEntity.setTransactionType(ProfitConstants.AWARD_TRANTYPE_RECRUIT);
					awardEntity.setTransactionTime(new Date());
					String description = "会员"+(userEntity.getName()==null?"":userEntity.getName())+(userEntity.getPersonalPhone()==null?"":userEntity.getPersonalPhone()) + "升级到" + nextUserLevelEntity.getName();
					awardEntity.setTransactionDesc(description);
					awardEntity.setUserEntity(supervisorEntity);

					awardEntity.setCreateBy(userEntity.getId());
					awardEntity.setUpdateBy(userEntity.getId());
					userAwardDAO.createUserAward(awardEntity);
					
					//更新对账单
					UserStatementEntity statementEntity = new UserStatementEntity();
					statementEntity.setUserEntity(supervisorEntity);
					statementEntity.setTransactionAmount(profit);
					statementEntity.setTransactionType(FundConstants.USER_STATEMENT_TRANSACTION_TYPE_RECRUIT);
					statementEntity.setTransactionCode(orderEntity.getOrderNo());
					statementEntity.setTransactionTime(new Date());
					statementEntity.setBalanceBefore(balanceBefore);
					statementEntity.setBalanceAfter(balanceAfter);
					statementEntity.setCreateBy(orderEntity.getUserEntity().getId());
					statementEntity.setUpdateBy(orderEntity.getUserEntity().getId());
					userStatementDAO.createUserStatement(statementEntity);
					
					//发送奖励通知订阅消息
					wxSuscribeMessageSender.sendAwardMsg(paymentUtil.getWechatAppIdMiniProgram(), paymentUtil.getWechatAppSecretMiniProgram(), awardEntity.getUserEntity().getOpenId(), awardEntity.getTransactionType(),String.valueOf(awardEntity.getTransactionAmount()),awardEntity.getTransactionDesc());
				}
				supervisorEntity = supervisorEntity.getSupervisorL1();
			}
		} catch (DAOException e) {
			throw new BusinessException(e);
		}
	}

	/**
	 * 会员是否达到指定会员等级条件
	 * 
	 * @param userEntity
	 * @param userLevelEntity
	 * @return
	 */
	boolean isUserLevelArchived(UserEntity userEntity, UserLevelEntity userLevelEntity) throws BusinessException {

		boolean isArchived = true;

		if (userEntity == null || userLevelEntity == null) {
			return false;
		}

		/* 是否要求会员达标 */
		boolean isRequiredByUser = userLevelEntity.isRequiredByUser();
		if (isRequiredByUser) {
			isArchived = isUserLevelArchivedUserCondition(userEntity, userLevelEntity);
		}

		/* 是否要求销售额达标 */
		boolean isRequiredByAmount = userLevelEntity.isRequiredByAmount();
		if (isRequiredByAmount) {
			isArchived = isUserLevelArchivedAmountCondition(userEntity, userLevelEntity);
		}

		return isArchived;

	}

	/**
	 * 会员邀请数是否达到指定会员等级条件
	 * 
	 * @param userEntity
	 * @param userLevelEntity
	 * @return
	 */
	boolean isUserLevelArchivedUserCondition(UserEntity userEntity, UserLevelEntity userLevelEntity)
			throws BusinessException {

		boolean isArchived = true;

		try {

			if (userEntity == null || userLevelEntity == null) {
				return false;
			}

			List<UserLevelEntity> levels = userLevelDAO.getUserLevels();

			/* 直邀会员数要求 */
			int requiredUserCountDirect = userLevelEntity.getRequiredUserCountDirect();
			/* 团队会员数要求 */
			int requiredUserCountTeam = userLevelEntity.getRequiredUserCountTeam();
			/* 都满足或者其中一个满足(OR, AND) */
			String symbol = userLevelEntity.getRequiredByUserSymbol();

			/* 获取会员直邀数(具有推广和销售收益的会员) */
			List<String> uuids = new ArrayList<String>();
			for (UserLevelEntity level : levels) {
				if (level.isRecruitProfitEnabled() || level.isSaleProfitEnabled()) {
					uuids.add(level.getUserLevelUuid());
				}
			}
			int userCountDirect = userDAO.getInvitedUserCountByLevelDate(userEntity, uuids,
					DateUtils.addYears(new Date(), -10), new Date());

			/* 获取团队会员数 */
			int userCountTeam = userDAO.getAllChildUserCount(userEntity);

			// 两者都要求
			if ("AND".equals(symbol)) {
				if (userCountDirect < requiredUserCountDirect || userCountTeam < requiredUserCountTeam) {
					return false;
				}
			}

			// 其中一个达到要求
			if ("OR".equals(symbol)) {
				if (userCountDirect < requiredUserCountDirect && userCountTeam < requiredUserCountTeam) {
					return false;
				}
			}
		} catch (DAOException e) {
			logger.error("检查会员数是否达到升级条件发生异常:" + e.getMessage());
			throw new BusinessException(e);
		}
		return isArchived;
	}

	/**
	 * 会员销售额是否达到指定会员等级条件
	 * 
	 * @param userEntity
	 * @param userLevelEntity
	 * @return
	 */
	boolean isUserLevelArchivedAmountCondition(UserEntity userEntity, UserLevelEntity userLevelEntity)
			throws BusinessException {

		boolean isArchived = true;
		try {
			if (userEntity == null || userLevelEntity == null) {
				return false;
			}

			/* 个人销售额要求 */
			BigDecimal requiredProductAmountDirect = userLevelEntity.getRequiredProductAmountDirect();
			/* 团队销售额要求 */
			BigDecimal requiredProductAmountTeam = userLevelEntity.getRequiredProductAmountTeam();
			/* 都满足或者其中一个满足(OR, AND) */
			String symbol = userLevelEntity.getRequiredByAmountSymbol();

			/* 获取个人销售额 */
			OrderSearchDTO orderSearchDTO = new OrderSearchDTO();
			orderSearchDTO.setKeyArray(new String[] { "ORDER_STATUS_LIST", "USER" });
			orderSearchDTO.setUserUuid(userEntity.getUserUuid());
			List<String> payedOrderStatusList = new ArrayList<String>();
			payedOrderStatusList.add(OrderConstants.ORDER_STATUS_NOT_DELIVERIED);
			payedOrderStatusList.add(OrderConstants.ORDER_STATUS_EVALUATED);
			payedOrderStatusList.add(OrderConstants.ORDER_STATUS_DELIVERIED);
			payedOrderStatusList.add(OrderConstants.ORDER_STATUS_COMPLETED);
			orderSearchDTO.setOrderStatusList(payedOrderStatusList);
			BigDecimal productAmountDirect = orderDAO.searchOrdersTotalAmount(orderSearchDTO);

			/* 获取团队销售额 */
			BigDecimal productAmountTeam = userPerformanceDAO.getTotalPerformanceByUser(userEntity);

			// 两者都要求
			if ("AND".equals(symbol)) {
				if (productAmountDirect.compareTo(requiredProductAmountDirect) < 0
						|| productAmountTeam.compareTo(requiredProductAmountTeam) < 0) {
					return false;
				}
			}

			// 其中一个达到要求
			if ("OR".equals(symbol)) {
				if (productAmountDirect.compareTo(requiredProductAmountDirect) < 0
						&& productAmountTeam.compareTo(requiredProductAmountTeam) < 0) {
					return false;
				}
			}
		} catch (DAOException e) {
			logger.error("检查会员数是否达到升级条件是发生异常:" + e.getMessage());
			throw new BusinessException(e);
		}
		return isArchived;
	}

}
