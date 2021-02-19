package com.mb.framework.service.spec;

import java.io.Serializable;

public abstract class AbstractBaseDTO implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * This method is used to get object in form of string
	 * @return
	 */
	
	
	private String createBy;
	
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	private String updateBy;
	
	private String actionType;
	
}
