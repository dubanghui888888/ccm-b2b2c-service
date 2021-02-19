
package com.mb.ext.core.service;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.framework.exception.BusinessException;

/**验证码接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface VerificationService<T extends BodyDTO>
{

	/**发送手机验证码
	 * @param countryCode - 手机号码国家代码
	 * @param phoneNumber - 手机号
	 * @throws BusinessException
	 */
	void sendCodeBySMS(String countryCode, String phoneNumber) throws BusinessException;
	
	/**发送邮件验证码
	 * @param emailId - 邮箱地址
	 * @throws BusinessException
	 */
	void sendCodeByEmail (String emailId)  throws BusinessException;
	
	/**校验手机验证码
	 * @param countryCode - 手机号码国家代码
	 * @param phoneNumber - 手机号码
	 * @param code - 验证码
	 * @return 校验结果
	 * @throws BusinessException
	 */
	boolean verifyCodeBySMS (String countryCode, String phoneNumber, String code)  throws BusinessException;
	
	/**校验邮箱验证码
	 * @param emailId - 邮箱地址
	 * @param code - 验证码
	 * @return - 校验结果
	 * @throws BusinessException
	 */
	boolean verifyCodeByEmail (String emailId, String code)  throws BusinessException;
	
	
}
