package com.mb.ext.core.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.dao.MerchantDAO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Component
@EnableScheduling
public class MerchantScoreEvaluateTask {

	@Autowired
	MerchantDAO merchantDAO;

	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 *
	 */
	@Scheduled(cron = "10 0 0 * * ?") // 每天凌晨4点执行
//	@Scheduled(cron = "0 * * * * ?")
	@Transactional
	public void run() {
		logger.info("-------------------开始执行商家评分计划任务------------------------");
		try {
		
		String sql = "update merchant,(select merchant.MERCHANT_UUID as MERCHANT_UUID, CAST(avg(product_comment.COMMENT_RANK) as DECIMAL(10,1)) as score from product_comment,product,merchant where product_comment.PRODUCT_UUID = product.product_uuid and product.MERCHANT_UUID = merchant.MERCHANT_UUID group by merchant.MERCHANT_UUID) as t set merchant.score = t.score where merchant.MERCHANT_UUID = t.MERCHANT_UUID";
		
		merchantDAO.executeInsertUpdateNativeSQL(sql);

		} catch (DAOException e) {
			e.printStackTrace();
			logger.error("计划任务商家评分执行失败:" + e.getMessage());
		}
		logger.info("-------------------执行商家评分计划任务结束------------------------");
	}
}
