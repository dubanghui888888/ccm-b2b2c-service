package com.mb.ext.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the logistics database table.
 * 
 */
@Entity
@Table(name = "logistics")
@NamedQuery(name = "LogisticsEntity.findAll", query = "SELECT u FROM LogisticsEntity u")
public class LogisticsEntity extends AbstractBaseEntity
{
	public String getLogisticsUuid() {
		return logisticsUuid;
	}

	public void setLogisticsUuid(String logisticsUuid) {
		this.logisticsUuid = logisticsUuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "LOGISTICS_UUID")
	@GenericGenerator(name = "LOGISTICS_UUID", strategy = "uuid")
	@Column(name = "LOGISTICS_UUID", nullable = false, length = 36)
	private String logisticsUuid;

	@Column(name = "NAME", nullable = false, length = 20)
	private String name;
	
}