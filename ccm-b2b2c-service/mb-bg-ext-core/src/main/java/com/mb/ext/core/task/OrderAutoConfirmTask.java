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
import com.mb.ext.core.entity.SettingEntity;
import com.mb.ext.core.service.OrderService;
import com.mb.ext.core.service.spec.OrderSearchDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Component
@EnableScheduling
public class OrderAutoConfirmTask {

	@Autowired
	OrderDAO orderDAO;

	@Autowired
	SettingDAO settingDAO;
	
	@Autowired
	OrderService orderService;
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 *
	 */
	@Scheduled(cron = "30 0 0 * * ?") // 每天凌晨执行
//	@Scheduled(cron = "0 * * * * ?") //测试每分钟执行
	@Transactional
	public void run() {
		logger.info("-------------------开始执行订单自动收货计划任务------------------------");
		try {
			SettingEntity settingEntity = settingDAO.getSettingByName("ORDER_AUTO_CONFIRM");
			int orderAutoConfirmDays = Integer.valueOf(settingEntity.getValue());
			OrderSearchDTO orderSearchDTO = new OrderSearchDTO();
			String[] keyArray = new String[] {"ORDERSTATUS","DELIVERYDATE"};
			orderSearchDTO.setKeyArray(keyArray);
			Date deliveryDateStart = DateUtils.addYears(new Date(), 0-10);
			Date deliveryDateEnd = DateUtils.addDays(new Date(), 0-orderAutoConfirmDays);
			orderSearchDTO.setDeliveryDateStart(deliveryDateStart);
			orderSearchDTO.setDeliveryDateEnd(deliveryDateEnd);
			orderSearchDTO.setOrderStatus(OrderConstants.ORDER_STATUS_DELIVERIED);
			int total = orderService.searchOrderTotal(orderSearchDTO);
			logger.info("共找到" + total + "个订单需要自动确认收货.");			
			int pageSize = 1;
			int pageNo = ((int)Math.ceil((double)total/(double)pageSize));
			for(int cPageNo = 1; cPageNo<=pageNo; cPageNo++) {
				List<OrderDTO> orderList = orderService.searchOrders(orderSearchDTO, (cPageNo-1)*pageSize, pageSize);
				for (OrderDTO orderDTO : orderList) {
					orderService.confirmOrder(orderDTO);
				}
			}
//			String sql = "UPDATE `order` set CONFIRM_TIME = current_timestamp, ORDER_STATUS = '3' WHERE ORDER_STATUS = '2' AND DATE_ADD(DELIVERY_TIME, INTERVAL "+orderAutoConfirmDays+" DAY) < current_timestamp";
//			
//			orderDAO.executeInsertUpdateNativeSQL(sql);
		

		} catch (DAOException e) {
			e.printStackTrace();
			logger.error("计划任务自动收货执行失败:" + e.getMessage());
		} catch (BusinessException e) {
			e.printStackTrace();
			logger.error("计划任务自动收货执行失败:" + e.getMessage());
		}
		logger.info("-------------------执行订单自动收货计划任务结束------------------------");
	}

}
