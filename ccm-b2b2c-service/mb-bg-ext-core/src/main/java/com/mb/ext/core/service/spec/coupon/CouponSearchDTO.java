
package com.mb.ext.core.service.spec.coupon;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class CouponSearchDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	
	public static final String KEY_ACTIVE = "ACTIVE";
	
	public static final String KEY_TYPE = "TYPE";
	
	public static final String KEY_NAME = "NAME";
	
	public static final String KEY_USER = "USER";
	
	public static final String KEY_MERCHANT = "MERCHANT";

	public static final String KEY_MERCHANT_NAME = "MERCHANT_NAME";

	public static final String KEY_MERCHANT_MOBILE_NO = "MERCHANT_MOBILE_NO";
	
	public static final String KEY_COUPON_UUID = "COUPON_UUID";

	public static final String KEY_PRODUCT = "PRODUCT";
	
	public String getCouponUuid() {
		return couponUuid;
	}

	public void setCouponUuid(String couponUuid) {
		this.couponUuid = couponUuid;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public String getUserMobileNo() {
		return userMobileNo;
	}

	public void setUserMobileNo(String userMobileNo) {
		this.userMobileNo = userMobileNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public static final String KEY_COUPON_ISUSED = "IS_USED";
	
	public static final String KEY_COUPON_EXPIRED = "IS_EXPIRED";
	
	public static final String KEY_COUPON_STARTED = "IS_STARTED";
	
	public static final String KEY_COUPON_USER_MOBILENO = "USER_MOBILE_NO";
	
	public static final String KEY_COUPON_USER_NAME = "USER_NAME";

	String[] keyArray = new String[]{};
	
	int startIndex;
	
	String couponUuid;
	
	String merchantUuid;

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantMobileNo() {
		return merchantMobileNo;
	}

	public void setMerchantMobileNo(String merchantMobileNo) {
		this.merchantMobileNo = merchantMobileNo;
	}

	String merchantName;

	String merchantMobileNo;

	public String getProductUuid() {
		return productUuid;
	}

	public void setProductUuid(String productUuid) {
		this.productUuid = productUuid;
	}

	String productUuid;
	
	public String getMerchantUuid() {
		return merchantUuid;
	}

	public void setMerchantUuid(String merchantUuid) {
		this.merchantUuid = merchantUuid;
	}

	String userUuid;
	
	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	boolean isUsed;
	
	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	boolean isExpired;
	
	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}

	boolean isStarted;
	
	String userMobileNo;
	
	String userName;
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	int pageSize;
	
	public String[] getKeyArray() {
		return keyArray;
	}

	public void setKeyArray(String[] keyArray) {
		this.keyArray = keyArray;
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

	boolean isActive;
	
	String type;
	
	String name;

}
