package com.mb.framework.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.mb.framework.util.StringUtil;

@MappedSuperclass
public abstract class AbstractBaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "IS_DELETED")
	private boolean isDeleted;

	@Column(name = "DATE_CREATE" , nullable = false)
	private Date createDate = new Date();

	@Column(name = "CREATE_BY",length = 50)
	private String createBy;

	@Column(name = "DATE_UPDATE")
	private Date updateDate = new Date();

	@Column(name = "UPDATE_BY", length = 50)
	private String updateBy;

	/**
	 * 
	 * This method is used to get object in form of string
	 * @return
	 */
	public String toString() {
		return StringUtil.objectToString(this);
	}

	/**
	 * @return the isDeleted
	 */
	public boolean isDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the updateBy
	 */
	public String getUpdateBy() {
		return updateBy;
	}

	/**
	 * @param updateBy the updateBy to set
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}



}
