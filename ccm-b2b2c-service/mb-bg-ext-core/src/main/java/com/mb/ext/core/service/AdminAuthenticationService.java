
package com.mb.ext.core.service;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.framework.exception.BusinessException;

/** 后台登录账户验证接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface AdminAuthenticationService<T extends BodyDTO>
{

	/** 检查token是否合法
	 * @param tokenId
	 * @return
	 * @throws BusinessException
	 */
	boolean isValidToken (String tokenId) throws BusinessException;
	
	/**检查token是否过期
	 * @param tokenId
	 * @return
	 * @throws BusinessException
	 */
	boolean isTokenExpired (String tokenId) throws BusinessException;
	
	/** 验证登录密码
	 * @param adminDTO
	 * @return
	 * @throws BusinessException
	 */
	boolean validatePassword(AdminDTO adminDTO) throws BusinessException;
	
}
