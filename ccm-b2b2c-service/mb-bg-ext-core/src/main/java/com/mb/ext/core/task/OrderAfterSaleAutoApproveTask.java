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
import com.mb.ext.core.service.OrderAfterSaleService;
import com.mb.ext.core.service.spec.OrderAfterSaleSearchDTO;
import com.mb.ext.core.service.spec.order.OrderAfterSaleDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Component
@EnableScheduling
public class OrderAfterSaleAutoApproveTask {

	@Autowired
	OrderDAO orderDAO;

	@Autowired
	SettingDAO settingDAO;
	
	@Autowired
	OrderAfterSaleService orderAfterSaleService;
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 *
	 */
	@Scheduled(cron = "15 0 0 * * ?") // 每天凌晨执行
//	@Scheduled(cron = "0 * * * * ?") //测试每分钟执行
	@Transactional
	public void run() {
		logger.info("-------------------开始执行退款自动同意计划任务------------------------");
		try {
			SettingEntity settingEntity = settingDAO.getSettingByName("ORDER_AFTERSALE_PROCESS_PERIOD");
			int orderAfterSaleProcessDays = Integer.valueOf(settingEntity.getValue());
			OrderAfterSaleSearchDTO orderAfterSaleSearchDTO = new OrderAfterSaleSearchDTO();
			String[] keyArray = new String[] {"STATUS","APPLICATION_TIME"};
			orderAfterSaleSearchDTO.setKeyArray(keyArray);
			Date applicationDateStart = DateUtils.addYears(new Date(), 0-10);
			Date applicationDateEnd = DateUtils.addDays(new Date(), 0-orderAfterSaleProcessDays);
			orderAfterSaleSearchDTO.setApplicationDateStart(applicationDateStart);
			orderAfterSaleSearchDTO.setApplicationDateEnd(applicationDateEnd);
			orderAfterSaleSearchDTO.setStatus(OrderConstants.ORDER_AFTER_SALE_STATUS_CREATE);
			orderAfterSaleSearchDTO.setStartIndex(0);
			orderAfterSaleSearchDTO.setPageSize(10);
			List<OrderAfterSaleDTO> orderAfterSaleList = orderAfterSaleService.searchOrderAfterSale(orderAfterSaleSearchDTO);
			while(orderAfterSaleList.size()>0) {
				for (OrderAfterSaleDTO orderAfterSaleDTO : orderAfterSaleList) {
					orderAfterSaleService.approveOrderAfterSale(orderAfterSaleDTO);
				}
				orderAfterSaleList = orderAfterSaleService.searchOrderAfterSale(orderAfterSaleSearchDTO);
			}
		} catch (DAOException e) {
			e.printStackTrace();
			logger.error("计划任务退款自动同意执行失败:" + e.getMessage());
		} catch (BusinessException e) {
			e.printStackTrace();
			logger.error("计划任务退款自动同意执行失败:" + e.getMessage());
		}
		logger.info("-------------------执行订单退款自动同意计划任务结束------------------------");
	}

}
