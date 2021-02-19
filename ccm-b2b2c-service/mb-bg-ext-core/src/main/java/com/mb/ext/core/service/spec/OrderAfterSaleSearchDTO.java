
package com.mb.ext.core.service.spec;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class OrderAfterSaleSearchDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public static final String KEY_ORDERTYPE = "ORDERTYPE";

	public static final String KEY_ORDERNO = "ORDERNO";
	
	public static final String KEY_MERCHANT = "MERCHANT";
	
	public static final String KEY_SALENO = "SALENO";
	
	public static final String KEY_SALETYPE = "SALETYPE";
	
	public static final String KEY_STATUS = "STATUS";
	
	public static final String KEY_STATUS_LIST = "STATUS_LIST";
	
	public static final String KEY_USER = "USER";
	
	public static final String KEY_SUPPLIER = "SUPPLIER";
	
	public static final String KEY_SUPPLIERNAME = "SUPPLIERNAME";
	
	public static final String KEY_NAME = "NAME";
	
	public static final String KEY_PERSONALPHONE = "PERSONALPHONE";
	
	public static final String KEY_APPLICATION_TIME = "APPLICATION_TIME";
	
	public static final String SORT_BY = "SORT_BY";

	String[] keyArray = new String[]{};
	
	String sortBy;
	
	int startIndex;
	
	int pageSize;
	
	int total;
	
	public String getMerchantUuid() {
		return merchantUuid;
	}

	public void setMerchantUuid(String merchantUuid) {
		this.merchantUuid = merchantUuid;
	}

	String merchantUuid;
	
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

	String orderNo;	//订单号码
	
	String orderType;	//订单类型
	
	String saleNo;	//售后单号码
	
	String saleType;	//售后类型
	
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	String status;	//售后状态
	
	List<String> statusList;	//售后状态列表
	
	public String getSaleNo() {
		return saleNo;
	}

	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}

	public List<String> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	Date applicationDateStart;	//售后单日期
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

	Date applicationDateEnd;	//售后单日期
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPersonalPhone() {
		return personalPhone;
	}

	public void setPersonalPhone(String personalPhone) {
		this.personalPhone = personalPhone;
	}

	
	String name;	//会员名称
	
	String personalPhone;	//会员电话号码
	
	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	String userUuid;	//会员唯一编号

	public String[] getKeyArray() {
		return keyArray;
	}

	public void setKeyArray(String[] keyArray) {
		this.keyArray = keyArray;
	}
	
}
