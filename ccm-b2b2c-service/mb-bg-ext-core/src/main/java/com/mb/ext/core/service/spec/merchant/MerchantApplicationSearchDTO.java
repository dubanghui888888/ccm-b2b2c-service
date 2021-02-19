
package com.mb.ext.core.service.spec.merchant;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class MerchantApplicationSearchDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	public static final String KEY_MERCHANT_NAME = "MERCHANT_NAME";
	
	public static final String KEY_MERCHANT_MOBILENO = "MERCHANT_MOBILENO";
	
	public static final String KEY_STATUS = "APPLICATION_STATUS";
	
	public static final String KEY_APPLICATION_DATE = "APPLICATION_DATE";
	
	public static final String SORT_BY = "SORT_BY";

	String[] keyArray = new String[]{};
	
	String sortBy;
	
	int startIndex;
	
	int pageSize;
	
	public String getMerchantMobileNo() {
		return merchantMobileNo;
	}

	public void setMerchantMobileNo(String merchantMobileNo) {
		this.merchantMobileNo = merchantMobileNo;
	}

	private String merchantName;
	
	private String merchantMobileNo;
	
	private String status;
	
	public int getStartIndex() {
		return startIndex;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	Date applicationDateEnd;	
	
	public String[] getKeyArray() {
		return keyArray;
	}

	public void setKeyArray(String[] keyArray) {
		this.keyArray = keyArray;
	}
	
}
