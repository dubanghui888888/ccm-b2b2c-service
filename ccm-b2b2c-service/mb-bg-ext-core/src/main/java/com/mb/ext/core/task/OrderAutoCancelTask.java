package com.mb.ext.core.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.dao.SettingDAO;
import com.mb.ext.core.dao.order.OrderDAO;
import com.mb.ext.core.entity.SettingEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Component
@EnableScheduling
public class OrderAutoCancelTask {

	@Autowired
	OrderDAO orderDAO;

	@Autowired
	SettingDAO settingDAO;
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 *
	 */
	@Scheduled(cron = "25 0 0 * * ?") // 每天凌晨执行
//	@Scheduled(cron = "0 * * * * ?")
	@Transactional
	public void run() {
		logger.info("-------------------开始执行订单自动取消计划任务------------------------");
		try {
		SettingEntity settingEntity = settingDAO.getSettingByName("ORDER_AUTO_CANCEL");
		int orderAutoCancelDays = Integer.valueOf(settingEntity.getValue());
		
		String sql = "UPDATE `order` set CANCEL_TIME = current_timestamp, ORDER_STATUS = '4' WHERE ORDER_STATUS = '0' AND DATE_ADD(ORDER_TIME, INTERVAL "+orderAutoCancelDays+" DAY) < current_timestamp";
		
		orderDAO.executeInsertUpdateNativeSQL(sql);

		} catch (DAOException e) {
			e.printStackTrace();
			logger.error("计划任务自动取消执行失败:" + e.getMessage());
		}
		logger.info("-------------------执行订单自动取消计划任务结束------------------------");
	}
}
