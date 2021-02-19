package com.mb.ext.core.task;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.constant.GroupBuyConstants;
import com.mb.ext.core.dao.group.GroupBuyUserDAO;
import com.mb.ext.core.dao.group.GroupBuyDAO;
import com.mb.ext.core.entity.order.OrderEntity;
import com.mb.ext.core.entity.group.GroupBuyEntity;
import com.mb.ext.core.entity.group.GroupBuyUserEntity;
import com.mb.ext.core.service.OrderService;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

/**
 * 拼团单过期退款
 *
 */
@Component
public class GroupBuyExpiredTask {

	@Autowired
	OrderService orderService;

	@Autowired
	GroupBuyDAO groupBuyDAO;
	
	@Autowired
	GroupBuyUserDAO groupBuyUserDAO;
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	@Scheduled(cron = "0 * * * * ?")
	@Transactional
	public void run() {
		logger.info("-------------------开始执行拼团单过期检查计划任务------------------------");
		try {
			
			List<GroupBuyEntity> expiredGroupBuys = groupBuyDAO.getExpiredGroupBuys();
			for (Iterator<GroupBuyEntity> iterator = expiredGroupBuys.iterator(); iterator.hasNext();) {
				GroupBuyEntity groupBuyEntity = iterator.next();
				int minUserCount = groupBuyEntity.getGroupBuyProductEntity().getGroupBuyDefEntity().getMinUserCount();
				int joinedUserCount = groupBuyUserDAO.getGroupBuyUserCount(groupBuyEntity);
				if(joinedUserCount<minUserCount) {
					//参团人数不足, 拼团失败
					groupBuyEntity.setStatus(GroupBuyConstants.STATUS_FAILED);
					//拼团订单自动取消
					cancelGroupBuyOrder(groupBuyEntity);
				}else {
					groupBuyEntity.setStatus(GroupBuyConstants.STATUS_COMPLETED);
				}
				groupBuyDAO.updateGroupBuy(groupBuyEntity);
			}
			

		} catch (DAOException e) {
			e.printStackTrace();
			logger.error("计划任务自动取消执行失败:" + e.getMessage());
		}
		logger.info("-------------------执行开始执行拼团单过期检查计划任务结束------------------------");
	}

	private void cancelGroupBuyOrder(GroupBuyEntity groupBuyEntity) {
		
		try {
			List<GroupBuyUserEntity> groupBuyUserEntityList = groupBuyUserDAO.getUsersBuyGroupBuy(groupBuyEntity);
			for (Iterator<GroupBuyUserEntity> iterator = groupBuyUserEntityList.iterator(); iterator.hasNext();) {
				GroupBuyUserEntity groupBuyUserEntity = (GroupBuyUserEntity) iterator.next();
				OrderEntity orderEntity = groupBuyUserEntity.getOrderEntity();
				OrderDTO orderDTO = new OrderDTO();
				orderDTO.setOrderNo(orderEntity.getOrderNo());
				orderService.cancelOrder(orderDTO);
			}
		} catch (DAOException e) {
			logger.error("获取拼团用户发生异常: "+e.getMessage());
		} catch (BusinessException e) {
			logger.error("取消拼团订单发生异常: "+e.getMessage());
		}
		
	}
}
