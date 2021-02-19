
package com.mb.ext.core.service;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.framework.exception.BusinessException;

/**前端用户认证接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface AuthenticationService<T extends BodyDTO>
{

	/**验证token是否合法
	 * @param tokenId
	 * @return
	 * @throws BusinessException
	 */
	boolean isValidToken (String tokenId) throws BusinessException;
	
	/**验证token是否过期
	 * @param tokenId
	 * @return
	 * @throws BusinessException
	 */
	boolean isTokenExpired (String tokenId) throws BusinessException;
	
	/**验证用户密码是否正确
	 * @param userDTO - 前端用户
	 * @return
	 * @throws BusinessException
	 */
	boolean validatePassword(UserDTO userDTO) throws BusinessException;
	
}
