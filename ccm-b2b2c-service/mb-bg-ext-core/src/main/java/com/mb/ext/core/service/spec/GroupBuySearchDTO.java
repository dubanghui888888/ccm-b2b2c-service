
package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class GroupBuySearchDTO extends AbstractBaseDTO
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

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public static final String KEY_ORDERNO = "ORDERNO";
	
	public static final String KEY_USER = "USER";
	
	public static final String KEY_SUPPLIER = "SUPPLIER";
	
	public static final String KEY_SUPPLIERNAME = "SUPPLIERNAME";
	
	public static final String KEY_NAME = "NAME";
	
	public static final String KEY_PERSONALPHONE = "PERSONALPHONE";
	
	public static final String KEY_ORDERTYPE = "ORDERTYPE";
	
	public static final String KEY_GROUP_BUY_STATUS = "GROUP_BUY_STATUS";

	public static final String KEY_GROUP_BUY_PRODUCT = "GROUP_BUY_PRODUCT";
	
	public static final String KEY_GROUP_BUY_DEF = "GROUP_BUY_DEF";
	
	public static final String KEY_ORDERDATE = "ORDERDATE";
	
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

	String orderNo;	//订单号码
	
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

	String orderType;	//订单类型
	
	public String getGroupBuyStatus() {
		return groupBuyStatus;
	}

	public void setGroupBuyStatus(String groupBuyStatus) {
		this.groupBuyStatus = groupBuyStatus;
	}

	String groupBuyStatus;	//团购状态
	
	String name;	//会员名称

	String personalPhone;	//会员电话号码
	
	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	String userUuid;	//会员唯一编号

	public String getGroupBuyProductUuid() {
		return groupBuyProductUuid;
	}

	public String getGroupBuyDefUuid() {
		return groupBuyDefUuid;
	}

	public void setGroupBuyDefUuid(String groupBuyDefUuid) {
		this.groupBuyDefUuid = groupBuyDefUuid;
	}

	public void setGroupBuyProductUuid(String groupBuyProductUuid) {
		this.groupBuyProductUuid = groupBuyProductUuid;
	}

	String groupBuyProductUuid;	//拼团商品唯一编号
	
	String groupBuyDefUuid;	//拼团活动唯一编号
	
	String supplierUuid;	//供应商唯一编号
	
	String supplierName;	//供应商名称


	public String getSupplierUuid() {
		return supplierUuid;
	}

	public void setSupplierUuid(String supplierUuid) {
		this.supplierUuid = supplierUuid;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	Date orderDateStart;	//订单日期
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getOrderDateStart() {
		return orderDateStart;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setOrderDateStart(Date orderDateStart) {
		this.orderDateStart = orderDateStart;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getOrderDateEnd() {
		return orderDateEnd;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setOrderDateEnd(Date orderDateEnd) {
		this.orderDateEnd = orderDateEnd;
	}

	Date orderDateEnd;	//订单日期
	
	public String[] getKeyArray() {
		return keyArray;
	}

	public void setKeyArray(String[] keyArray) {
		this.keyArray = keyArray;
	}
	
}
