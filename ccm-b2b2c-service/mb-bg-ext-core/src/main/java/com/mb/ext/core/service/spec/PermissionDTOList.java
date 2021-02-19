
package com.mb.ext.core.service.spec;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PermissionDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private List<PermissionDTO> roles = new ArrayList<PermissionDTO>();

	public List<PermissionDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<PermissionDTO> roles) {
		this.roles = roles;
	}



}
