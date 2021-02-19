package com.mb.ext.core.task;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.constant.OrderConstants;
import com.mb.ext.core.dao.SettingDAO;
import com.mb.ext.core.dao.order.OrderDAO;
import com.mb.ext.core.dao.product.ProductCommentDAO;
import com.mb.ext.core.entity.SettingEntity;
import com.mb.ext.core.entity.order.OrderEntity;
import com.mb.ext.core.entity.order.OrderProductEntity;
import com.mb.ext.core.entity.product.ProductCommentEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.service.spec.OrderSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Component
@EnableScheduling
public class OrderAutoEvaluateTask {

	@Autowired
	OrderDAO orderDAO;
	
	@Autowired
	SettingDAO settingDAO;
	
	@Autowired
	ProductCommentDAO productCommentDAO;
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 *
	 */
	@Scheduled(cron = "35 0 0 * * ?") // 每天凌晨执行
//	@Scheduled(cron = "0 * * * * ?") 
	@Transactional
	public void run() {
		logger.info("-------------------开始执行订单自动评价计划任务------------------------");
		int pageNo = 1;
		int pageSize = 20;
		try {
			SettingEntity settingEntity = settingDAO.getSettingByName("ORDER_AUTO_EVALUATE");
			int orderAutoEvaluateDays = Integer.valueOf(settingEntity.getValue());
			
			OrderSearchDTO orderSearchDTO =  new OrderSearchDTO();
			orderSearchDTO.setKeyArray(new String[] {"ORDERSTATUS"});
			orderSearchDTO.setOrderStatus(OrderConstants.ORDER_STATUS_COMPLETED);
			int total = orderDAO.searchOrdersTotal(orderSearchDTO);
			while (((pageNo - 1) * pageSize) < total) {
				List<OrderEntity> orderList = orderDAO.searchOrders(orderSearchDTO, (pageNo-1)*pageSize, pageSize);
				for (OrderEntity orderEntity : orderList) {
					Date confirmDate = orderEntity.getConfirmTime();
					if(confirmDate != null) {
						if(new Date().compareTo(DateUtils.addDays(confirmDate, orderAutoEvaluateDays))>0){
							List<OrderProductEntity> productList = orderEntity.getOrderProductList();
							for (OrderProductEntity orderProductEntity : productList) {
								ProductEntity productEntity = orderProductEntity.getProductEntity();
								ProductCommentEntity productCommentEntity = new ProductCommentEntity();
								productCommentEntity.setProductEntity(productEntity);
								productCommentEntity.setUserEntity(orderEntity.getUserEntity());
								productCommentEntity.setCommentRank("5");
								productCommentEntity.setEvaluateTime(new Date());
								productCommentEntity.setOrderEntity(orderEntity);
								productCommentEntity.setCommentContent("自动好评");
								productCommentEntity.setUpdateBy("system");
								productCommentEntity.setCreateBy("system");
								productCommentDAO.addProductComment(productCommentEntity);
							}
							
						}
						orderEntity.setOrderStatus(OrderConstants.ORDER_STATUS_EVALUATED);
						orderDAO.updateOrder(orderEntity);
					}
				}
				pageNo++;
			}

		} catch (DAOException e) {
			e.printStackTrace();
			logger.error("计划任务自动收货执行失败:" + e.getMessage());
		}
		logger.info("-------------------执行订单自动评价计划任务结束------------------------");
	}

}
