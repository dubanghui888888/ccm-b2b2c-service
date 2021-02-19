
package com.mb.ext.core.service.spec;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class AdminRoleDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private AdminDTO adminDTO;
	
	private RoleDTO roleDTO;

	public AdminDTO getAdminDTO() {
		return adminDTO;
	}

	public void setAdminDTO(AdminDTO adminDTO) {
		this.adminDTO = adminDTO;
	}

	public RoleDTO getRoleDTO() {
		return roleDTO;
	}

	public void setRoleDTO(RoleDTO roleDTO) {
		this.roleDTO = roleDTO;
	}
	
	
	

}
