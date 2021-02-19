
package com.mb.ext.core.service;

import java.util.List;

import com.mb.ext.core.service.spec.point.PointSettingDTO;
import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.GlobalSettingDTO;
import com.mb.ext.core.service.spec.PrintSettingDTO;
import com.mb.ext.core.service.spec.SettingDTO;
import com.mb.ext.core.service.spec.SettingDTOList;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.point.SignSettingDTO;
import com.mb.framework.exception.BusinessException;

/**参数接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface SettingService<T extends BodyDTO>
{

	/**添加参数
	 * @param name - 参数名
	 * @param value - 参数值
	 * @throws BusinessException
	 */
	void addSetting(String name, String value) throws BusinessException;
	
	/**更新参数
	 * @param name - 参数名
	 * @param value - 参数值
	 * @throws BusinessException
	 */
	void updateSetting(String name, String value) throws BusinessException;
	
	/**更新参数值
	 * @param settingDTOList - 参数列表
	 * @throws BusinessException
	 */
	void updateSettingList(SettingDTOList settingDTOList) throws BusinessException;
	
	/**根据参数名查询参数值
	 * @param name - 参数名
	 * @return 参数值
	 * @throws BusinessException
	 */
	SettingDTO getSettingByName(String name) throws BusinessException;
	
	/**获取所有参数
	 * @return 参数列表
	 * @throws BusinessException
	 */
	List<SettingDTO> getSettings() throws BusinessException;
	
	/**获取系统参数
	 * @return 参数列表
	 * @throws BusinessException
	 */
	List<SettingDTO> getSysSettings() throws BusinessException;
	
	/**获取应用设置
	 * @return
	 * @throws BusinessException
	 */
	GlobalSettingDTO getGlobalApplicationSetting() throws BusinessException;
	/**更新应用设置
	 * @return
	 * @throws BusinessException
	 */
	void updateGlobalApplicationSetting(GlobalSettingDTO globalSettingDTO) throws BusinessException;
	/**获取微信支付设置
	 * @return
	 * @throws BusinessException
	 */
	GlobalSettingDTO getGlobalWechatSetting() throws BusinessException;
	/**更新微信支付设置
	 * @return
	 * @throws BusinessException
	 */
	void updateGlobalWechatSetting(GlobalSettingDTO globalSettingDTO) throws BusinessException;
	/**获取支付宝支付设置
	 * @return
	 * @throws BusinessException
	 */
	GlobalSettingDTO getGlobalAlipaySetting() throws BusinessException;
	/**更新支付宝支付设置
	 * @return
	 * @throws BusinessException
	 */
	void updateGlobalAlipaySetting(GlobalSettingDTO globalSettingDTO) throws BusinessException;
	/**获取OSS存储设置
	 * @return
	 * @throws BusinessException
	 */
	GlobalSettingDTO getGlobalOssSetting() throws BusinessException;
	/**更新短信设置
	 * @return
	 * @throws BusinessException
	 */
	void updateGlobalOssSetting(GlobalSettingDTO globalSettingDTO) throws BusinessException;
	/**获取短信设置
	 * @return
	 * @throws BusinessException
	 */
	GlobalSettingDTO getGlobalSmsSetting() throws BusinessException;
	/**更新短信设置
	 * @return
	 * @throws BusinessException
	 */
	void updateGlobalSmsSetting(GlobalSettingDTO globalSettingDTO) throws BusinessException;
	/**获取高德地图设置
	 * @return
	 * @throws BusinessException
	 */
	GlobalSettingDTO getGlobalMapSetting() throws BusinessException;
	/**更新高德地图设置
	 * @return
	 * @throws BusinessException
	 */
	void updateGlobalMapSetting(GlobalSettingDTO globalSettingDTO) throws BusinessException;
	/**获取打印设置
	 * @return
	 * @throws BusinessException
	 */
	PrintSettingDTO getPrintSetting(MerchantDTO merchantDTO) throws BusinessException;
	/**更新打印设置
	 * @return
	 * @throws BusinessException
	 */
	void updatePrintSetting(PrintSettingDTO printSettingDTO) throws BusinessException;
	
	/**获取积分签到设置
	 * @return
	 * @throws BusinessException
	 */
	SignSettingDTO getSignSetting() throws BusinessException;
	/**更新积分签到设置
	 * @return
	 * @throws BusinessException
	 */
	void updateSignSetting(SignSettingDTO signSettingDTO) throws BusinessException;

	/**购物送积分设置
	 * @return
	 * @throws BusinessException
	 */
	PointSettingDTO getPointSetting() throws BusinessException;
	/**更新购物送积分设置
	 * @return
	 * @throws BusinessException
	 */
	void updatePointSetting(PointSettingDTO pointSettingDTO) throws BusinessException;
}
