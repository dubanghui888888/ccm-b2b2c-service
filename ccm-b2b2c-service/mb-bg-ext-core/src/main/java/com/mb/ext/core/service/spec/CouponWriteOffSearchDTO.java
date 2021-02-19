package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
public class CouponWriteOffSearchDTO extends AbstractBaseDTO {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	public static final String KEY_DATE = "DATE";
	
	public static final String KEY_TYPE = "TYPE";
	
	public static final String SORT_BY = "SORT_BY";
	
	public static final String KEY_USER_UUID = "USERUUID";
	
	public static final String KEY_MERCHANT_UUID = "MERCHANTUUID";
	
	public static final String KEY_MERCHANT_NAME = "MERCHANTNAME";
	
	public static final String KEY_PHONE = "PHONE";
	
	public static final String KEY_MERCHANT_PHONE = "MERCHANTPHONE";
	
	String userUuid;
	
	String phone;

	String merchantUuid;
	
	String merchantName;
	
	String merchantPhone;
	
	public String getMerchantPhone() {
		return merchantPhone;
	}
	public void setMerchantPhone(String merchantPhone) {
		this.merchantPhone = merchantPhone;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMerchantUuid() {
		return merchantUuid;
	}
	public void setMerchantUuid(String merchantUuid) {
		this.merchantUuid = merchantUuid;
	}
	public String getUserUuid() {
		return userUuid;
	}
	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	String sortBy;
	
	int startIndex;

	int pageSize;
	
	String type;
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String[] getKeyArray() {
		return keyArray;
	}
	public void setKeyArray(String[] keyArray) {
		this.keyArray = keyArray;
	}

	String[] keyArray = new String[]{};
	
	Date writeOffDateStart;	//订单日期
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getWriteOffDateStart() {
		return writeOffDateStart;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setWriteOffDateStart(Date writeOffDateStart) {
		this.writeOffDateStart = writeOffDateStart;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getWriteOffDateEnd() {
		return writeOffDateEnd;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setWriteOffDateEnd(Date writeOffDateEnd) {
		this.writeOffDateEnd = writeOffDateEnd;
	}

	Date writeOffDateEnd;	//订单日期
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
