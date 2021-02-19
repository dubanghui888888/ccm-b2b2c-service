
package com.mb.ext.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.framework.exception.BusinessException;

/**后台用户服务接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface AdminService<T extends BodyDTO>
{
	
	/** 根据token查询用户信息
	 * @param tokenId
	 * @return
	 * @throws BusinessException
	 */
	AdminDTO getAdminDTOByTokenId(String tokenId) throws BusinessException;
	
	/**存储当前线程的后台用户信息
	 * @param adminDTO
	 * @throws BusinessException
	 */
	void setAdminProfile(AdminDTO adminDTO) throws BusinessException;
	
	/**获取当前线程访问的后台用户信息
	 * @return
	 * @throws BusinessException
	 */
	AdminDTO getAdminProfile() throws BusinessException;

	/**后台用户登录
	 * @param adminDTO - 后台用户
	 * @return
	 * @throws BusinessException
	 */
	String login (AdminDTO adminDTO) throws BusinessException;
	
	/**后台用户登出
	 * @param adminDTO - 后台用户
	 * @return
	 * @throws BusinessException
	 */
	void logout (AdminDTO adminDTO) throws BusinessException;
	
	/**后台用户登出
	 * @param tokenId
	 * @return
	 * @throws BusinessException
	 */
	void logout (String tokenId) throws BusinessException;
	
	/**重置密码
	 * @param adminDTO - 后台用户
	 * @throws BusinessException
	 */
	void resetPassword(AdminDTO adminDTO) throws BusinessException;
	
	/**修改密码
	 * @param adminDTO - 后台用户
	 * @throws BusinessException
	 */
	void changePassword(AdminDTO adminDTO) throws BusinessException;
	
	/**创建后台用户
	 * @param adminDTO - 后台用户
	 * @throws BusinessException
	 */
	void createAdmin(AdminDTO adminDTO) throws BusinessException;
	
	/**删除后台用户
	 * @param adminDTO - 后台用户
	 * @throws BusinessException
	 */
	void deleteAdmin(AdminDTO adminDTO) throws BusinessException;
	
	/**更新后台用户信息
	 * @param adminDTO - 后台用户
	 * @throws BusinessException
	 */
	void updateAdmin(AdminDTO adminDTO) throws BusinessException;
	
	/**启用后台用户
	 * @param adminDTO - 后台用户
	 * @throws BusinessException
	 */
	void enableAdmin(AdminDTO adminDTO) throws BusinessException;
	
	/**停用后台用户
	 * @param adminDTO - 后台用户
	 * @throws BusinessException
	 */
	void disableAdmin(AdminDTO adminDTO) throws BusinessException;
	
	/**解除后台用户锁定
	 * @param adminDTO - 后台用户
	 * @throws BusinessException
	 */
	void unlockAdmin(AdminDTO adminDTO) throws BusinessException;
	
	/**锁定后台用户
	 * @param adminDTO - 后台用户
	 * @throws BusinessException
	 */
	void lockAdmin(AdminDTO adminDTO) throws BusinessException;
	
	/**根据用户ID获取用信信息
	 * @param id - 后台用户ID
	 * @return
	 * @throws BusinessException
	 */
	AdminDTO getAdminById(String id) throws BusinessException;
	
	/**获取所有后台用户
	 * @return
	 * @throws BusinessException
	 */
	List<AdminDTO> getAdmins() throws BusinessException;
	
	/**修改后台用户角色
	 * @param adminDTO - 后台用户
	 * @throws BusinessException
	 */
	void changeAdminRole(AdminDTO adminDTO) throws BusinessException;
	
}
