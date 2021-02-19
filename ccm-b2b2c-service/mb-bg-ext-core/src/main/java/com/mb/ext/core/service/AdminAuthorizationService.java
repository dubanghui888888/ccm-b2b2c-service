
package com.mb.ext.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.FunctionDTO;
import com.mb.ext.core.service.spec.PermissionDTO;
import com.mb.framework.exception.BusinessException;

/**后台权限控制接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface AdminAuthorizationService<T extends BodyDTO>
{

	/** 获取后台用户的权限
	 * @param adminDTO - 后台用户
	 * @return
	 * @throws BusinessException
	 */
	List<PermissionDTO> getPermissions(AdminDTO adminDTO) throws BusinessException;
	
	/**获取系统定义的所有权限
	 * @return
	 * @throws BusinessException
	 */
	List<PermissionDTO> getAllPermissions() throws BusinessException;
	
	/** 给特定角色授权
	 * @param permissionDTO
	 * @throws BusinessException
	 */
	void grantPermissions(PermissionDTO permissionDTO) throws BusinessException;
	
	/**检查用户是否授权访问某个功能
	 * @param adminDTO - 用户
	 * @param functionDTO - 功能
	 * @return
	 * @throws BusinessException
	 */
	boolean isAuthorized(AdminDTO adminDTO, FunctionDTO functionDTO) throws BusinessException;
	
	/**获取授权某个角色的功能
	 * @param roleName - 角色名
	 * @return
	 * @throws BusinessException
	 */
	PermissionDTO getPermissionByRoleName(String roleName) throws BusinessException;
	
	/**获取系统定义的二级功能
	 * @return
	 * @throws BusinessException
	 */
	List<FunctionDTO> getFunctions() throws BusinessException;

	/**获取某个用户能访问的功能
	 * @param adminDTO - 后台用户
	 * @return
	 * @throws BusinessException
	 */
	List<FunctionDTO> getFunctions(AdminDTO adminDTO) throws BusinessException;
}
