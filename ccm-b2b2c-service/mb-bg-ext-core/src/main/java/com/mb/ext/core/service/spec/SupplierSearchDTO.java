
package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class SupplierSearchDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	
	public static final String KEY_SUPPLIERADDRESS = "SUPPLIERADDRESS";
	
	public static final String KEY_SUPPLIERNAME = "SUPPLIERNAME";
	
	public static final String KEY_MOBILENO = "MOBILENO";
	
	public static final String KEY_REGISTERDATE = "REGISTERDATE";
	
	public static final String KEY_RANKING_DATE = "RANKINGDATE";
	
	public static final String SORT_BY = "SORT_BY";

	String[] keyArray = new String[]{};
	
	String sortBy;
	
	int startIndex;
	
	int pageSize;
	
	int total;
	
	String sorts;
	
	Date rankingDateStart;//排行开始日期
	
	Date rankingDateEnd;//排行结束日期

	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getRankingDateStart() {
		return rankingDateStart;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public void setRankingDateStart(Date rankingDateStart) {
		this.rankingDateStart = rankingDateStart;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getRankingDateEnd() {
		return rankingDateEnd;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public void setRankingDateEnd(Date rankingDateEnd) {
		this.rankingDateEnd = rankingDateEnd;
	}

	public String getSorts() {
		return sorts;
	}

	public void setSorts(String sorts) {
		this.sorts = sorts;
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

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	String supplierAddress;	//地址
	
	String supplierName;	//姓名
	
	String mobileNo;	//电话号码

	public String getSupplierAddress() {
		return supplierAddress;
	}

	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	Date registerDateStart;	//注册日期
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getRegisterDateStart() {
		return registerDateStart;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setRegisterDateStart(Date registerDateStart) {
		this.registerDateStart = registerDateStart;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getRegisterDateEnd() {
		return registerDateEnd;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setRegisterDateEnd(Date registerDateEnd) {
		this.registerDateEnd = registerDateEnd;
	}

	Date registerDateEnd;	//注册日期
	
	public String[] getKeyArray() {
		return keyArray;
	}

	public void setKeyArray(String[] keyArray) {
		this.keyArray = keyArray;
	}
	
}
