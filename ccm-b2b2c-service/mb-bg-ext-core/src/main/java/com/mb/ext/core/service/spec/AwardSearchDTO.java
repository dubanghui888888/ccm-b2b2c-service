
package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class AwardSearchDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	public static final String KEY_TRANSACTIONTYPE = "TRANSACTIONTYPE";
	
	public static final String KEY_AWARDDATE = "AWARDDATE";
	
	public static final String KEY_USER = "USER";
	
	public static final String KEY_NAME = "NAME";
	
	public static final String SORT_BY = "SORT_BY";

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

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
	
	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Date getAwardDateEnd() {
		return awardDateEnd;
	}

	private String transactionType;
	
	private String userUuid;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	Date awardDateStart;	//提现申请日期
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getAwardDateStart() {
		return awardDateStart;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setAwardDateStart(Date awardDateStart) {
		this.awardDateStart = awardDateStart;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getAwaqrdDateEnd() {
		return awardDateEnd;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setAwardDateEnd(Date awardDateEnd) {
		this.awardDateEnd = awardDateEnd;
	}

	Date awardDateEnd;	//提现申请日期
	
	public String[] getKeyArray() {
		return keyArray;
	}

	public void setKeyArray(String[] keyArray) {
		this.keyArray = keyArray;
	}
	
}
