package com.mb.ext.core.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the application database table.
 * 
 */
@Entity
@Table(name = "application")
@NamedQuery(name = "ApplicationEntity.findAll", query = "SELECT u FROM ApplicationEntity u")
public class ApplicationEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "APPLICATION_UUID")
	@GenericGenerator(name = "APPLICATION_UUID", strategy = "uuid")
	@Column(name = "APPLICATION_UUID", nullable = false, length = 36)
	private String applicationUuid;


	@Column(name = "APPLICATION_NAME", nullable = false, length = 60)
	private String name;
	
	@Column(name = "ROOTAPPLICATION", length = 45)
	private String rootApplication;
	
	public String getRootApplication() {
		return rootApplication;
	}

	public void setRootApplication(String rootApplication) {
		this.rootApplication = rootApplication;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Column(name = "APPLICATION_DESC", length = 200)
	private String desc;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<FunctionEntity> getFunctionEntityList() {
		return functionEntityList;
	}

	public void setFunctionEntityList(List<FunctionEntity> functionEntityList) {
		this.functionEntityList = functionEntityList;
	}

	@Column(name = "APPLICATION_CODE", nullable = false, length = 20)
	private String code;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "applicationEntity")
	private List<FunctionEntity> functionEntityList;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApplicationUuid() {
		return applicationUuid;
	}

	public void setApplicationUuid(String applicationUuid) {
		this.applicationUuid = applicationUuid;
	}

}