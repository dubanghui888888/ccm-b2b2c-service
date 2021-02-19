package com.mb.ext.core.service.spec;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
public class UserCouponSearchDTO extends AbstractBaseDTO {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	public static final String KEY_USER_UUID = "USERUUID";
	
	public static final String SORT_BY = "SORT_BY";
	
	public static final String KEY_IS_USED = "IS_USED";
	
	public static final String KEY_IS_OVERDUE = "IS_OVERDUE";
	
	public static final String KEY_NOT_OVERDUE = "NOT_OVERDUE";
	
	public static final String KEY_PHONE = "PHONE";
	
	public static final String KEY_TYPE = "TYPE";
	
	public static final String KEY_COUPON_NAME = "COUPONNAME";
	
	String phone;
	
	String type;
	
	String couponName;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	String userCouponUuid;

	public String getUserCouponUuid() {
		return userCouponUuid;
	}

	public void setUserCouponUuid(String userCouponUuid) {
		this.userCouponUuid = userCouponUuid;
	}

	boolean isUsed;
	
	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	String userUuid;
	
	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String[] getKeyArray() {
		return keyArray;
	}

	public void setKeyArray(String[] keyArray) {
		this.keyArray = keyArray;
	}

	String sortBy;
	
	int startIndex;

	int pageSize;
	
	String[] keyArray = new String[]{};
	
}
