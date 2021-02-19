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
 * The persistent class for the setting database table.
 * 
 */
@Entity
@Table(name = "setting")
@NamedQuery(name = "SettingEntity.findAll", query = "SELECT u FROM SettingEntity u")
public class SettingEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "SETTING_UUID")
	@GenericGenerator(name = "SETTING_UUID", strategy = "uuid")
	@Column(name = "SETTING_UUID", nullable = false, length = 36)
	private String settingUuid;

	@Column(name = "SETTING_NAME", nullable = false, length = 45)
	private String name;

	@Column(name = "SETTING_VALUE", length = 45)
	private String value;	

	@Column(name = "SETTING_DESC", length = 200)
	private String description;


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSettingUuid() {
		return settingUuid;
	}

	public void setSettingUuid(String settingUuid) {
		this.settingUuid = settingUuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}