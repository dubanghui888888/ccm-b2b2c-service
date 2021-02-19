
package com.mb.ext.core.service;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.framework.exception.BusinessException;

/**会员登录接口, 处理会员登录登出
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface LoginService<T extends BodyDTO>
{

	/** 会员登录
	 * @param userDTO - 会员
	 * @return
	 * @throws BusinessException
	 */
	String login (UserDTO userDTO) throws BusinessException;

	/**会员手机验证码登录
	 * @param userDTO - 会员
	 * @return
	 * @throws BusinessException
	 */
	String smsLogin (UserDTO userDTO) throws BusinessException;
	
	/** 会员手机验证码登录(不校验验证码, mock模式)
	 * @param userDTO - 会员
	 * @return
	 * @throws BusinessException
	 */
	String smsLoginWithoutVerification (UserDTO userDTO) throws BusinessException;

	/** 微信端登录通过open id
	 * @param userDTO - 会员
	 * @return
	 * @throws BusinessException
	 */
	String wechatLogin (UserDTO userDTO) throws BusinessException;
	
	/**会员登出
	 * @param userDTO - 会员
	 * @throws BusinessException
	 */
	void logout (UserDTO userDTO) throws BusinessException;
	
	/**会员登出
	 * @param tokenId
	 * @throws BusinessException
	 */
	void logout (String tokenId) throws BusinessException;
	
	/**重置会员密码
	 * @param userDTO - 会员
	 * @throws BusinessException
	 */
	void resetPassword(UserDTO userDTO) throws BusinessException;
	
	/**修改会员密码
	 * @param userDTO - 会员
	 * @throws BusinessException
	 */
	void changePassword(UserDTO userDTO) throws BusinessException;
	
}
