
package com.mb.ext.core.service.spec;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class MerchantAssignSearchDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	
	public static final String KEY_MERCHANT = "MERCHANT";
	
	public static final String KEY_USER = "USER";
	
	public static final String KEY_MERCHANTNAME = "MERCHANTNAME";
	
	public static final String KEY_USER_NAME = "USERNAME";
	
	public static final String KEY_USER_PERSONALPHONE = "USERPERSONALPHONE";
	
	public static final String KEY_MOBILENO = "MOBILENO";
	
	public static final String KEY_ASSIGNDATE = "ASSIGNDATE";
	
	public static final String KEY_AMOUNT = "AMOUNT";

	public static final String KEY_POINT = "POINT";
	
	public static final String SORT_BY = "SORT_BY";

	String[] keyArray = new String[]{};
	
	String sortBy;
	
	int startIndex;
	
	int pageSize;
	
	int total;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
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

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	String merchantAddress;	//地址
	
	String merchantName;	//商家名称
	
	String userName;	//会员姓名
	
	String userPersonalPhone;	//会员电话
	
	public String getUserUuid() {
		return userUuid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPersonalPhone() {
		return userPersonalPhone;
	}

	public void setUserPersonalPhone(String userPersonalPhone) {
		this.userPersonalPhone = userPersonalPhone;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	String mobileNo;	//电话号码
	
	String merchantUuid;	//编号
	
	String userUuid;	//会员

	public String getMerchantAddress() {
		return merchantAddress;
	}

	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	Date assignDateStart;	//注册日期
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getAssignDateStart() {
		return assignDateStart;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setAssignDateStart(Date assignDateStart) {
		this.assignDateStart = assignDateStart;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getAssignDateEnd() {
		return assignDateEnd;
	}
	public String getMerchantUuid() {
		return merchantUuid;
	}

	public void setMerchantUuid(String merchantUuid) {
		this.merchantUuid = merchantUuid;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public int getMinPoint() {
		return minPoint;
	}

	public void setMinPoint(int minPoint) {
		this.minPoint = minPoint;
	}

	public int getMaxPoint() {
		return maxPoint;
	}

	public void setMaxPoint(int maxPoint) {
		this.maxPoint = maxPoint;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public void setAssignDateEnd(Date assignDateEnd) {
		this.assignDateEnd = assignDateEnd;
	}

	Date assignDateEnd;	//注册日期
	
	BigDecimal minAmount;
	
	BigDecimal maxAmount;
	
	int minPoint;
	
	int maxPoint;
	
	public String[] getKeyArray() {
		return keyArray;
	}

	public void setKeyArray(String[] keyArray) {
		this.keyArray = keyArray;
	}
	
}
