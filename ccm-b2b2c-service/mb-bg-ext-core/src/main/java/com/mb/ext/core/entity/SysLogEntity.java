package com.mb.ext.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the sys_log database table.
 * 
 */
@Entity
@Table(name = "sys_log")
@NamedQuery(name = "SysLogEntity.findAll", query = "SELECT u FROM SysLogEntity u")
public class SysLogEntity extends AbstractBaseEntity
{

	public String getSysLogUuid() {
		return sysLogUuid;
	}


	public void setSysLogUuid(String sysLogUuid) {
		this.sysLogUuid = sysLogUuid;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "SYSLOG_UUID")
	@GenericGenerator(name = "SYSLOG_UUID", strategy = "uuid")
	@Column(name = "SYSLOG_UUID", nullable = false, length = 36)
	private String sysLogUuid;


	@Column(name = "LOG_DATE")
	private Date logDate;
	
	public Date getLogDate() {
		return logDate;
	}


	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}


	public String getLogUserId() {
		return logUserId;
	}


	public void setLogUserId(String logUserId) {
		this.logUserId = logUserId;
	}


	public String getLogType() {
		return logType;
	}


	public void setLogType(String logType) {
		this.logType = logType;
	}


	public String getLogCategory() {
		return logCategory;
	}


	public void setLogCategory(String logCategory) {
		this.logCategory = logCategory;
	}


	public String getLogDetail() {
		return logDetail;
	}


	public void setLogDetail(String logDetail) {
		this.logDetail = logDetail;
	}

	@Column(name = "LOG_USER_ID")
	private String logUserId;
	
	@Column(name = "LOG_TYPE")
	private String logType;
	
	@Column(name = "LOG_CATEGORY")
	private String logCategory;
	
	@Column(name = "LOG_DETAIL")
	private String logDetail;

}