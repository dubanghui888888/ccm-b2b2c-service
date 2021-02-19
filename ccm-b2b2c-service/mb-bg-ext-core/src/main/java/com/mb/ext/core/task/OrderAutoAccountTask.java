package com.mb.ext.core.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.dao.SettingDAO;
import com.mb.ext.core.dao.order.OrderDAO;
import com.mb.ext.core.entity.order.OrderEntity;
import com.mb.ext.core.service.OrderService;
import com.mb.ext.core.service.spec.OrderSearchDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Component
@EnableScheduling
public class OrderAutoAccountTask {

	@Autowired
	OrderDAO orderDAO;
	
	@Autowired
	SettingDAO settingDAO;
	
	@Autowired
	OrderService orderService;
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * 订单自动结算任务(商户资金, 佣金, 积分赠送)
	 */
	@Scheduled(cron = "20 0 0 * * ?") // 每天凌晨执行
//	@Scheduled(cron = "1 * * * * ?") 
	@Transactional
	public void run() {
		logger.info("-------------------开始执行订单自动结算计划任务------------------------");
		int pageNo = 1;
		int pageSize = 20;
		try {
			OrderSearchDTO orderSearchDTO =  new OrderSearchDTO();
			//处理已过售后期并且没有结算的订单
			orderSearchDTO.setKeyArray(new String[] {OrderSearchDTO.KEY_IS_AFTER_SALE_EXPRED,OrderSearchDTO.KEY_IS_ACCOUNTED,OrderSearchDTO.KEY_IS_AFTER_SALE,OrderSearchDTO.KEY_ORDER_TYPE_LIST});
			orderSearchDTO.setAccounted(false);	//未结算
			orderSearchDTO.setAfterSale(false);	//未在售后中
			List<String> orderTypeList = new ArrayList<String>();
			//非积分订单
			orderTypeList.add("0");
			orderTypeList.add("1");
			orderTypeList.add("2");
			orderSearchDTO.setOrderTypeList(orderTypeList);
			int total = orderDAO.searchOrdersTotal(orderSearchDTO);
			while (((pageNo - 1) * pageSize) < total) {
				List<OrderEntity> orderList = orderDAO.searchOrders(orderSearchDTO, (pageNo-1)*pageSize, pageSize);
				for (OrderEntity orderEntity : orderList) {
					OrderDTO orderDTO = new OrderDTO();
					orderDTO.setOrderNo(orderEntity.getOrderNo());
					orderService.accountOrder(orderDTO);
				}
				pageNo++;
			}

		} catch (DAOException e) {
			e.printStackTrace();
			logger.error("计划任务自动结算执行失败:" + e.getMessage());
		} catch (BusinessException e) {
			e.printStackTrace();
			logger.error("计划任务自动结算执行失败:" + e.getMessage());
		}
		logger.info("-------------------执行订单自动结算计划任务结束------------------------");
	}

}
