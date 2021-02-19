
package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonMonthSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PerformanceSearchDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	public static final String KEY_USERNAME = "USERNAME";
	
	public static final String KEY_PERSONALPHONE = "PERSONALPHONE";
	
	public static final String KEY_PERFORMANCEDATE = "PERFORMANCEDATE";
	
	public static final String KEY_USER = "USER";
	
	public static final String SORT_BY = "SORT_BY";

	String[] keyArray = new String[]{};
	
	String sortBy;
	
	int startIndex;
	
	int pageSize;
	
	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	int total;
	
	private String userUuid;
	
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

	private String userName;
	
	private String personalPhone;

	Date performanceDateStart;	
	@DateTimeFormat(pattern="yyyy-MM")
	public Date getPerformanceDateStart() {
		return performanceDateStart;
	}
	public String getPersonalPhone() {
		return personalPhone;
	}

	public void setPersonalPhone(String personalPhone) {
		this.personalPhone = personalPhone;
	}

	public void setPerformanceDateStart(Date performanceDateStart) {
		this.performanceDateStart = performanceDateStart;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonSerialize(using=JsonMonthSerializer.class)
	public void setAwardDateStart(Date performanceDateStart) {
		this.performanceDateStart = performanceDateStart;
	}
	@DateTimeFormat(pattern="yyyy-MM")
	public Date getPerformanceDateEnd() {
		return performanceDateEnd;
	}
	@JsonSerialize(using=JsonMonthSerializer.class)
	public void setPerformanceDateEnd(Date performanceDateEnd) {
		this.performanceDateEnd = performanceDateEnd;
	}

	Date performanceDateEnd;	
	
	public String[] getKeyArray() {
		return keyArray;
	}

	public void setKeyArray(String[] keyArray) {
		this.keyArray = keyArray;
	}
	
}
