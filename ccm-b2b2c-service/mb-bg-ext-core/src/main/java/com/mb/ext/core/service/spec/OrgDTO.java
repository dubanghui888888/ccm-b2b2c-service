
package com.mb.ext.core.service.spec;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class OrgDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String name;
	
	private String desc;
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private String actionType;
	
	
	
	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	private String newName;
	
	private String mangerUserId;
	
	private String managerName;
	
	private String managerUuid;
	
	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerUuid() {
		return managerUuid;
	}

	public void setManagerUuid(String managerUuid) {
		this.managerUuid = managerUuid;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	public String getManagerMobileCountryCode() {
		return managerMobileCountryCode;
	}

	public void setManagerMobileCountryCode(String managerMobileCountryCode) {
		this.managerMobileCountryCode = managerMobileCountryCode;
	}

	public String getManagerMobileNo() {
		return managerMobileNo;
	}

	public void setManagerMobileNo(String managerMobileNo) {
		this.managerMobileNo = managerMobileNo;
	}

	private String managerEmail;
	
	private String managerMobileCountryCode;
	
	public String getParentOrgName() {
		return parentOrgName;
	}

	public void setParentOrgName(String parentOrgName) {
		this.parentOrgName = parentOrgName;
	}
	
	private String managerMobileNo;
	
	private String parentOrgName;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMangerUserId() {
		return mangerUserId;
	}

	public void setMangerUserId(String mangerUserId) {
		this.mangerUserId = mangerUserId;
	}
	
	public List<OrgDTO> getChildOrgs() {
		return childOrgs;
	}

	public void setChildOrgs(List<OrgDTO> childOrgs) {
		this.childOrgs = childOrgs;
	}

	private List<OrgDTO> childOrgs = new ArrayList<OrgDTO>();

	

}
