
package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ProductSearchDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	public static final String KEY_MERCHANT = "MERCHANT";
	
	public static final String KEY_PRODUCT_NAME = "PRODUCT_NAME";
	
	public static final String KEY_PROVINCE = "PROVINCE";
	
	public static final String KEY_CITY = "CITY";
	
	public static final String KEY_PRODUCT_CATE = "PRODUCT_CATE";
	
	public static final String KEY_SUPPLIER = "SUPPLIER";
	
	public static final String KEY_GROUP = "GROUP";
	
	public static final String KEY_BRAND = "BRAND";
	
	public static final String KEY_ON_SALE = "ON_SALE";
	
	public static final String KEY_RECOMMEND = "RECOMMEND";
	
	public static final String KEY_CATE_NAME = "CATE_NAME";
	
	public static final String KEY_HOT = "HOT";
	
	public static final String KEY_NEW = "NEW";
	
	public static final String KEY_TRAN_TYPE = "TRAN_TYPE";
	
	public static final String KEY_TRAN_TIME = "TRAN_TIME";
	
	private String tranType;
	
	private Date tranTimeStart;
	
	private Date tranTimeEnd;
	
	public String getTranType() {
		return tranType;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getTranTimeStart() {
		return tranTimeStart;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setTranTimeStart(Date tranTimeStart) {
		this.tranTimeStart = tranTimeStart;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getTranTimeEnd() {
		return tranTimeEnd;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setTranTimeEnd(Date tranTimeEnd) {
		this.tranTimeEnd = tranTimeEnd;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public int getUnitPointStart() {
		return unitPointStart;
	}

	public void setUnitPointStart(int unitPointStart) {
		this.unitPointStart = unitPointStart;
	}

	public int getUnitPointEnd() {
		return unitPointEnd;
	}

	public void setUnitPointEnd(int unitPointEnd) {
		this.unitPointEnd = unitPointEnd;
	}

	public static final String KEY_INVENTORY = "INVENTORY";
	
	public static final String KEY_UNIT_POINT = "UNIT_POINT";
	
	public static final String SORT_BY = "SORT_BY";
	
	String[] keyArray = new String[]{};
	
	int startIndex;
	
	int pageSize;
	
	int unitPointStart;

	int unitPointEnd;
	
	public String getMerchantUuid() {
		return merchantUuid;
	}

	public void setMerchantUuid(String merchantUuid) {
		this.merchantUuid = merchantUuid;
	}

	String merchantUuid;
	
	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	String sortBy;
	
	String sort;
	
	String productName;
	
	String province;
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	String city;
	
	String productCateUuid;
	
	String groupUuid;
	
	public String getGroupUuid() {
		return groupUuid;
	}

	public void setGroupUuid(String groupUuid) {
		this.groupUuid = groupUuid;
	}

	String supplierUuid;
	
	public String getProductBrandUuid() {
		return productBrandUuid;
	}

	public void setProductBrandUuid(String productBrandUuid) {
		this.productBrandUuid = productBrandUuid;
	}

	String productBrandUuid;
	
	public String[] getKeyArray() {
		return keyArray;
	}

	public void setKeyArray(String[] keyArray) {
		this.keyArray = keyArray;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCateUuid() {
		return productCateUuid;
	}

	public void setProductCateUuid(String productCateUuid) {
		this.productCateUuid = productCateUuid;
	}

	public String getSupplierUuid() {
		return supplierUuid;
	}

	public void setSupplierUuid(String supplierUuid) {
		this.supplierUuid = supplierUuid;
	}

	public boolean isOnSale() {
		return isOnSale;
	}

	public void setOnSale(boolean isOnSale) {
		this.isOnSale = isOnSale;
	}

	public boolean isRecommend() {
		return isRecommend;
	}

	public void setRecommend(boolean isRecommend) {
		this.isRecommend = isRecommend;
	}

	public boolean isHot() {
		return isHot;
	}

	public void setHot(boolean isHot) {
		this.isHot = isHot;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	boolean isOnSale;
	
	boolean isRecommend;
	
	boolean isHot;
	
	boolean isNew;
	
	int inventory;
	
	String cateName;

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	
	
}
