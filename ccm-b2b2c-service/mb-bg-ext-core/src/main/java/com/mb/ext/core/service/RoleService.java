
package com.mb.ext.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.RoleDTO;
import com.mb.framework.exception.BusinessException;

/**后台角色接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface RoleService<T extends BodyDTO>
{

	/** 添加角色
	 * @param roleDTO - 角色
	 * @throws BusinessException
	 */
	void addRole(RoleDTO roleDTO) throws BusinessException;
	
	/**删除角色
	 * @param roleDTO - 角色
	 * @throws BusinessException
	 */
	void deleteRole(RoleDTO roleDTO) throws BusinessException;
	
	/**更新角色
	 * @param roleDTO - 角色
	 * @throws BusinessException
	 */
	void updateRole(RoleDTO roleDTO) throws BusinessException;
	
	/**启用角色
	 * @param roleDTO - 角色
	 * @throws BusinessException
	 */
	void enableRole (RoleDTO roleDTO) throws BusinessException;
	
	/**禁用角色
	 * @param roleDTO - 角色
	 * @throws BusinessException
	 */
	void disableRole (RoleDTO roleDTO) throws BusinessException;
	
	/**根据名称查询角色
	 * @param name - 角色名
	 * @return
	 * @throws BusinessException
	 */
	RoleDTO getRoleByName(String name) throws BusinessException;
	
	/** 获取所有角色
	 * @return
	 * @throws BusinessException
	 */
	List<RoleDTO> getRoles() throws BusinessException;
	
}
