
package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class RefundSearchDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	public String[] getRefundStatusArray() {
		return refundStatusArray;
	}

	public void setRefundStatusArray(String[] refundStatusArray) {
		this.refundStatusArray = refundStatusArray;
	}

	public static final String KEY_STATUS = "REFUNDSTATUS";
	
	public static final String KEY_REFUNDDATE = "REFUNDDATE";
	
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

	String[] refundStatusArray;	//退款状态
	

	Date refundDateStart;	//退款申请日期
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getRefundDateStart() {
		return refundDateStart;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setRefundDateStart(Date refundDateStart) {
		this.refundDateStart = refundDateStart;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getRefundDateEnd() {
		return refundDateEnd;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setRefundDateEnd(Date refundDateEnd) {
		this.refundDateEnd = refundDateEnd;
	}

	Date refundDateEnd;	//退款申请日期
	
	public String[] getKeyArray() {
		return keyArray;
	}

	public void setKeyArray(String[] keyArray) {
		this.keyArray = keyArray;
	}
	
}
