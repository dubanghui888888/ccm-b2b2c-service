
package com.mb.ext.core.service.spec.point;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class SignSettingDTO extends AbstractBaseDTO
{

	private static final long serialVersionUID = -5444408664751460073L;

	private String signSettingUuid;

	private boolean isEnabled;
	
	private int day1Point;
	
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

	private int day3Point;
	
	private int day4Point;
	
	private int day5Point;
	
	private int day6Point;
	
	private int day7Point;
	
	private String backgroundUrl;
	
	private String ruleUrl;
}
