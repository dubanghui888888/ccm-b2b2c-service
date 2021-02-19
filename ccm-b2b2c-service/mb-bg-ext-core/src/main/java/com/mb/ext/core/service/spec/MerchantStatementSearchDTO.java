
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
public class MerchantStatementSearchDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	public static final String KEY_TRANTYPE = "TRANTYPE"; 
	
	public static final String KEY_MERCHANT = "MERCHANT";
	
	public static final String KEY_MERCHANTNAME = "MERCHANTNAME";
	
	public static final String KEY_MOBILENO = "MOBILENO";
	
	public static final String KEY_STATEMENTDATE = "STATEMENTDATE";
	
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
	
	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	String transactionType;	//交易类型
	
	String merchantName;	//姓名
	
	String mobileNo;	//电话号码
	
	String merchantUuid;	//编号

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	Date statementDateStart;	//注册日期
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getStatementDateStart() {
		return statementDateStart;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setStatementDateStart(Date statementDateStart) {
		this.statementDateStart = statementDateStart;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getStatementDateEnd() {
		return statementDateEnd;
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
	public void setStatementDateEnd(Date statementDateEnd) {
		this.statementDateEnd = statementDateEnd;
	}

	Date statementDateEnd;	//注册日期
	
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
