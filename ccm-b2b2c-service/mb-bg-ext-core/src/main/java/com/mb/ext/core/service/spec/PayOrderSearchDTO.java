
package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PayOrderSearchDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	public static final String KEY_PERSONAL_PHONE = "PHONE";
	
	public static final String KEY_MOBILENO = "MOBILENO";
	
	public static final String KEY_MERCHANT_NAME = "MERCHANTNAME";
	
	public static final String KEY_DATE = "DATE";
	
	String sortBy;
	
	int startIndex;

	int pageSize;
	
	String phone;
	
	String mobileNo;
	
	Date orderDateStart;	//订单开始日期
	
	Date orderDateEnd;	//订单结束日期
	
	String[] keyArray = new String[]{};
	
	String merchantName;
	
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String[] getKeyArray() {
		return keyArray;
	}

	public void setKeyArray(String[] keyArray) {
		this.keyArray = keyArray;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getOrderDateStart() {
		return orderDateStart;
	}
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setOrderDateStart(Date orderDateStart) {
		this.orderDateStart = orderDateStart;
	}
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getOrderDateEnd() {
		return orderDateEnd;
	}
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setOrderDateEnd(Date orderDateEnd) {
		this.orderDateEnd = orderDateEnd;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
}
