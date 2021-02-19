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
public class MerchantProductUnitTask {

	@Autowired
	MerchantDAO merchantDAO;

	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 *
	 */
	@Scheduled(cron = "5 0 * * * ?") // 每小时执行
	@Transactional
	public void run() {
		logger.info("-------------------开始执行商家已售和在售商品数量计划任务------------------------");
		try {
		//更新已售商品数量
		String soldUnitSql = "update merchant,(select merchant.MERCHANT_UUID, count(product.SOLDUNIT) as SOLD_UNIT from merchant left join product on product.MERCHANT_UUID = merchant.MERCHANT_UUID group by merchant.MERCHANT_UUID) as t set merchant.SOLD_UNIT = t.SOLD_UNIT where merchant.MERCHANT_UUID = t.MERCHANT_UUID";
		merchantDAO.executeInsertUpdateNativeSQL(soldUnitSql);
		//更新在售商品数量
		String saleUnitSql = "update merchant,(select product.MERCHANT_UUID, count(product.PRODUCT_UUID) as SALE_UNIT from product WHERE product.IS_ON_SALE is true group by product.MERCHANT_UUID) as t set merchant.SALE_UNIT = t.SALE_UNIT where merchant.MERCHANT_UUID = t.MERCHANT_UUID";
		merchantDAO.executeInsertUpdateNativeSQL(saleUnitSql);
		} catch (DAOException e) {
			e.printStackTrace();
			logger.error("计划任务商家已售和在售商品数量执行失败:" + e.getMessage());
		}
		logger.info("-------------------执行商家已售和在售商品数量计划任务结束------------------------");
	}
}
