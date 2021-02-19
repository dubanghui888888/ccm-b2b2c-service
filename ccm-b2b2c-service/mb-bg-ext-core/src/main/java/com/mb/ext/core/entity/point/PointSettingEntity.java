package com.mb.ext.core.entity.point;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

import java.math.BigDecimal;

/**
 * The persistent class for the point_setting database table.
 * 
 */
@Entity
@Table(name = "point_setting")
@NamedQuery(name = "PointSettingEntity.findAll", query = "SELECT u FROM PointSettingEntity u")
public class PointSettingEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "POINT_SETTING_UUID")
	@GenericGenerator(name = "POINT_SETTING_UUID", strategy = "uuid")
	@Column(name = "POINT_SETTING_UUID", nullable = false, length = 36)
	private String pointSettingUuid;

	@Column(name = "IS_ENABLED")
	private boolean isEnabled;

	public String getPointSettingUuid() {
		return pointSettingUuid;
	}

	public void setPointSettingUuid(String pointSettingUuid) {
		this.pointSettingUuid = pointSettingUuid;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean enabled) {
		isEnabled = enabled;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	@Column(name = "AMOUNT")
	private BigDecimal amount;
	
	@Column(name = "POINT")
	private int point;

}