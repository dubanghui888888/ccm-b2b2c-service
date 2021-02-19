package com.mb.ext.core.entity.point;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the sign_setting database table.
 * 
 */
@Entity
@Table(name = "sign_setting")
@NamedQuery(name = "SignSettingEntity.findAll", query = "SELECT u FROM SignSettingEntity u")
public class SignSettingEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "SIGN_SETTING_UUID")
	@GenericGenerator(name = "SIGN_SETTING_UUID", strategy = "uuid")
	@Column(name = "SIGN_SETTING_UUID", nullable = false, length = 36)
	private String signSettingUuid;

	@Column(name = "IS_ENABLED")
	private boolean isEnabled;
	
	@Column(name = "DAY1_POINT")
	private int day1Point;
	
	@Column(name = "DAY2_POINT")
	private int day2Point;
	
	public String getSignSettingUuid() {
		return signSettingUuid;
	}

	public void setSignSettingUuid(String signSettingUuid) {
		this.signSettingUuid = signSettingUuid;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public int getDay1Point() {
		return day1Point;
	}

	public void setDay1Point(int day1Point) {
		this.day1Point = day1Point;
	}

	public int getDay2Point() {
		return day2Point;
	}

	public void setDay2Point(int day2Point) {
		this.day2Point = day2Point;
	}

	public int getDay3Point() {
		return day3Point;
	}

	public void setDay3Point(int day3Point) {
		this.day3Point = day3Point;
	}

	public int getDay4Point() {
		return day4Point;
	}

	public void setDay4Point(int day4Point) {
		this.day4Point = day4Point;
	}

	public int getDay5Point() {
		return day5Point;
	}

	public void setDay5Point(int day5Point) {
		this.day5Point = day5Point;
	}

	public int getDay6Point() {
		return day6Point;
	}

	public void setDay6Point(int day6Point) {
		this.day6Point = day6Point;
	}

	public int getDay7Point() {
		return day7Point;
	}

	public void setDay7Point(int day7Point) {
		this.day7Point = day7Point;
	}

	public String getBackgroundUrl() {
		return backgroundUrl;
	}

	public void setBackgroundUrl(String backgroundUrl) {
		this.backgroundUrl = backgroundUrl;
	}

	public String getRuleUrl() {
		return ruleUrl;
	}

	public void setRuleUrl(String ruleUrl) {
		this.ruleUrl = ruleUrl;
	}

	@Column(name = "DAY3_POINT")
	private int day3Point;
	
	@Column(name = "DAY4_POINT")
	private int day4Point;
	
	@Column(name = "DAY5_POINT")
	private int day5Point;
	
	@Column(name = "DAY6_POINT")
	private int day6Point;
	
	@Column(name = "DAY7_POINT")
	private int day7Point;
	
	@Column(name = "BACKGROUND_URL")
	private String backgroundUrl;
	
	@Column(name = "RULE_URL")
	private String ruleUrl;
	
}