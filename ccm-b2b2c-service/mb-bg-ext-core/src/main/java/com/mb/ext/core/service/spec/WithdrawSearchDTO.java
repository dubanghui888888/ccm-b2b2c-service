
package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class WithdrawSearchDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	public String[] getWithdrawStatusArray() {
		return withdrawStatusArray;
	}

	public void setWithdrawStatusArray(String[] withdrawStatusArray) {
		this.withdrawStatusArray = withdrawStatusArray;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public String getMerchantUuid() {
		return merchantUuid;
	}

	public void setMerchantUuid(String merchantUuid) {
		this.merchantUuid = merchantUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public static final String KEY_STATUS = "WITHDRAWSTATUS";
	
	public static final String KEY_WITHDRAWDATE = "WITHDRAWDATE";
	
	public static final String KEY_NAME = "NAME";
	
	public static final String KEY_USER = "USER";
	
	public static final String KEY_MERCHANT = "MERCHANT";
	
	public static final String SORT_BY = "SORT_BY";

	String[] keyArray = new String[]{};
	
	String name;
	
	String userUuid;
	
	String merchantUuid;
	
	String sortBy;
	
	int startIndex;
	
	int pageSize;
	
	int total;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	String[] withdrawStatusArray;	//提现状态
	

	Date withdrawDateStart;	//提现申请日期
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getWithdrawDateStart() {
		return withdrawDateStart;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setWithdrawDateStart(Date withdrawDateStart) {
		this.withdrawDateStart = withdrawDateStart;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getWithdrawDateEnd() {
		return withdrawDateEnd;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setWithdrawDateEnd(Date withdrawDateEnd) {
		this.withdrawDateEnd = withdrawDateEnd;
	}

	Date withdrawDateEnd;	//提现申请日期
	
	public String[] getKeyArray() {
		return keyArray;
	}

	public void setKeyArray(String[] keyArray) {
		this.keyArray = keyArray;
	}
	
}
