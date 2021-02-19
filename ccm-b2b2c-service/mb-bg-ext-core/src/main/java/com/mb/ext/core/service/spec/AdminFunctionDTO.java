
package com.mb.ext.core.service.spec;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class AdminFunctionDTO extends AbstractBaseDTO
{



	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	List<FunctionDTO> functionList;
	
	List<AdminDTO> adminList;

	public List<FunctionDTO> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<FunctionDTO> functionList) {
		this.functionList = functionList;
	}

	public List<AdminDTO> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<AdminDTO> adminList) {
		this.adminList = adminList;
	}

}
