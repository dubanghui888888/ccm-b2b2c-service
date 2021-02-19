
package com.mb.ext.core.service.spec;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateTimeSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PermissionDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String roleName;
	
	private String newRoleName;
	
	public String getNewRoleName() {
		return newRoleName;
	}
	public void setNewRoleName(String newRoleName) {
		this.newRoleName = newRoleName;
	}

	private String roleDesc;
	
	private String functionSummary;
	
	private Date creationDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getCreationDate() {
		return creationDate;
	}
	@JsonSerialize(using=JsonDateTimeSerializer.class) 
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getFunctionSummary() {
		return functionSummary;
	}

	public void setFunctionSummary(String functionSummary) {
		this.functionSummary = functionSummary;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getRoleName() {
		return roleName;
	}

	public List<FunctionDTO> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<FunctionDTO> functionList) {
		this.functionList = functionList;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	private List<FunctionDTO> functionList = new ArrayList<FunctionDTO>();




}
