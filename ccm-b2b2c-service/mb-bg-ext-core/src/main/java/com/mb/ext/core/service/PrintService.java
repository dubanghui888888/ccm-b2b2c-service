
package com.mb.ext.core.service;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.PrintSettingDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.framework.exception.BusinessException;

/** 打印接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface PrintService<T extends BodyDTO>
{

	/**检查打印机设置
	 * 
	 * @throws BusinessException
	 */
	void checkPrintSetting(MerchantDTO merchantDTO) throws BusinessException;
	
	/**获取打印机设置
	 * 
	 * @throws BusinessException
	 */
	PrintSettingDTO getPrintSetting(MerchantDTO merchantDTO) throws BusinessException;
	
	/**打印订单
	 * @param orderNo - 订单号
	 * @throws BusinessException
	 */
	void printOrder(String orderNo, PrintSettingDTO printSettingDTO) throws BusinessException;

}
