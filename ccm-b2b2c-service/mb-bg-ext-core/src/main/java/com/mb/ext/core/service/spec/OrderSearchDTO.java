
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
public class OrderSearchDTO extends AbstractBaseDTO
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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public static final String KEY_ORDERNO = "ORDERNO";
	
	public static final String KEY_USER = "USER";
	
	public static final String KEY_MERCHANT = "MERCHANT";
	
	public static final String KEY_SUPPLIER = "SUPPLIER";
	
	public static final String KEY_SUPPLIERNAME = "SUPPLIERNAME";
	
	public static final String KEY_NAME = "NAME";
	
	public static final String KEY_PERSONALPHONE = "PERSONALPHONE";
	
	public static final String KEY_ORDERTYPE = "ORDERTYPE";
	
	public static final String KEY_ORDERSTATUS = "ORDERSTATUS";
	
	public static final String KEY_ORDER_STATUS_LIST = "ORDER_STATUS_LIST";
	
	public static final String KEY_ORDER_TYPE_LIST = "ORDER_TYPE_LIST";
	
	public static final String KEY_DELIVERY_TYPE_LIST = "DELIVERY_TYPE_LIST";
	
	public static final String KEY_ORDERDATE = "ORDERDATE";
	
	public static final String KEY_DELIVERYDATE = "DELIVERYDATE";
	
	public static final String KEY_IS_AFTER_SALE = "IS_AFTER_SALE";
	
	public static final String KEY_IS_ACCOUNTED = "IS_ACCOUNTED";
	
	public static final String KEY_IS_AFTER_SALE_EXPRED = "IS_AFTER_SALE_EXPIRED";
	
	public static final String KEY_SEC_KILL_PRODUCT = "SEC_KILL_PRODUCT";
	
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
	
	String secKillProductUuid;
	
	public String getSecKillProductUuid() {
		return secKillProductUuid;
	}

	public void setSecKillProductUuid(String secKillProductUuid) {
		this.secKillProductUuid = secKillProductUuid;
	}

	public boolean isAfterSale() {
		return isAfterSale;
	}

	public void setAfterSale(boolean isAfterSale) {
		this.isAfterSale = isAfterSale;
	}

	public boolean isAccounted() {
		return isAccounted;
	}

	public void setAccounted(boolean isAccounted) {
		this.isAccounted = isAccounted;
	}

	boolean isAfterSale;	//是否申请售后
	
	boolean isAccounted;	//是否已结算
	
	boolean isAfterSaleExpired;	//是否已过售后期
	
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
	
	String orderStatus;	//订单状态
	
	List<String> orderStatusList;	//订单状态列表
	
	List<String> orderTypeList;	//订单类型列表
	
	List<String> deliveryTypeList;	//收货列表
	
	public List<String> getDeliveryTypeList() {
		return deliveryTypeList;
	}

	public void setDeliveryTypeList(List<String> deliveryTypeList) {
		this.deliveryTypeList = deliveryTypeList;
	}

	String name;	//会员名称
	
	public List<String> getOrderStatusList() {
		return orderStatusList;
	}

	public List<String> getOrderTypeList() {
		return orderTypeList;
	}

	public void setOrderTypeList(List<String> orderTypeList) {
		this.orderTypeList = orderTypeList;
	}

	public void setOrderStatusList(List<String> orderStatusList) {
		this.orderStatusList = orderStatusList;
	}

	String personalPhone;	//会员电话号码
	
	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	String userUuid;	//会员唯一编号
	
	String supplierUuid;	//供应商唯一编号
	
	String supplierName;	//供应商名称
	
	String merchantUuid;


	public String getMerchantUuid() {
		return merchantUuid;
	}

	public void setMerchantUuid(String merchantUuid) {
		this.merchantUuid = merchantUuid;
	}

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
	Date deliveryDateStart;	//发货日期
	Date deliveryDateEnd;	//发货日期
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getDeliveryDateEnd() {
		return deliveryDateEnd;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setDeliveryDateEnd(Date deliveryDateEnd) {
		this.deliveryDateEnd = deliveryDateEnd;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getOrderDateStart() {
		return orderDateStart;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getDeliveryDateStart() {
		return deliveryDateStart;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setDeliveryDateStart(Date deliveryDateStart) {
		this.deliveryDateStart = deliveryDateStart;
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
