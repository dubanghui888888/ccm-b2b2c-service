
package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class SysLogSearchDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	public static final String KEY_LOGTYPE = "LOGTYPE";
	
	public static final String KEY_LOGCATEGORY = "LOGCATEGORY";
	
	public static final String KEY_NAME = "NAME";
	
	public static final String KEY_LOGDATE = "LOGDATE";
	
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

	String logType;	//系统或人工
	
	String logCategory; 	//日志类型
	
	String name;	//账户名
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogCategory() {
		return logCategory;
	}

	public void setLogCategory(String logCategory) {
		this.logCategory = logCategory;
	}

	Date logDateStart;	//注册日期
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getLogDateStart() {
		return logDateStart;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setLogDateStart(Date logDateStart) {
		this.logDateStart = logDateStart;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getLogDateEnd() {
		return logDateEnd;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setLogDateEnd(Date logDateEnd) {
		this.logDateEnd = logDateEnd;
	}

	Date logDateEnd;	//日志日期
	
	public String[] getKeyArray() {
		return keyArray;
	}

	public void setKeyArray(String[] keyArray) {
		this.keyArray = keyArray;
	}
	
}
