
package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class UserDeliverySearchDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	public static final String KEY_NAME = "NAME";
	
	public static final String KEY_DELIVERYSTATUS = "DELIVERYSTATUS";
	
	public static final String KEY_APPLICATIONDATE = "APPLICATIONDATE";
	
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

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
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

	String name;	//姓名
	
	String deliveryStatus; 	//提货状态
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	Date applicationDateStart;	//申请日期
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getApplicationDateStart() {
		return applicationDateStart;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setApplicationDateStart(Date applicationDateStart) {
		this.applicationDateStart = applicationDateStart;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getApplicationDateEnd() {
		return applicationDateEnd;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setApplicationDateEnd(Date applicationDateEnd) {
		this.applicationDateEnd = applicationDateEnd;
	}

	Date applicationDateEnd;	//申请日期
	
	public String[] getKeyArray() {
		return keyArray;
	}

	public void setKeyArray(String[] keyArray) {
		this.keyArray = keyArray;
	}
	
}
