package com.mb.ext.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the function database table.
 * 
 */
@Entity
@Table(name = "function")
@NamedQuery(name = "FunctionEntity.findAll", query = "SELECT u FROM FunctionEntity u")
public class FunctionEntity extends AbstractBaseEntity
{




	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ApplicationEntity getApplicationEntity() {
		return applicationEntity;
	}

	public void setApplicationEntity(ApplicationEntity applicationEntity) {
		this.applicationEntity = applicationEntity;
	}

	public String getFunctionUuid() {
		return functionUuid;
	}

	public void setFunctionUuid(String functionUuid) {
		this.functionUuid = functionUuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "FUNCTION_UUID")
	@GenericGenerator(name = "FUNCTION_UUID", strategy = "uuid")
	@Column(name = "FUNCTION_UUID", nullable = false, length = 36)
	private String functionUuid;


	@Column(name = "FUNCTION_NAME", nullable = false, length = 60)
	private String name;
	
	@Column(name = "FUNCTION_CODE", nullable = false, length = 20)
	private String code;
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Column(name = "FUNCTION_DESC", length = 200)
	private String desc;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_UUID")
	private ApplicationEntity applicationEntity;



	
}