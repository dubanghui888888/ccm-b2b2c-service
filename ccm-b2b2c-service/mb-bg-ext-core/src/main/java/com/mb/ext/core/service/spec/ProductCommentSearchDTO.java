package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
public class ProductCommentSearchDTO extends AbstractBaseDTO {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	public static final String KEY_DATE = "DATE";
	
	public static final String KEY_MERCHANT = "MERCHANT";
	
	public static final String KEY_PRODUCT_UUID = "PRODUCTUUID";
	
	public static final String KEY_PRODUCT_NAME = "PRODUCTNAME";
	
	public static final String KEY_SUPPLIER_NAME = "SUPPLIERNAME";
	
	public static final String KEY_REPLIED = "REPLIED";
	
	public static final String KEY_NOT_REPLIED = "NOTREPLIED";
	
	String productName;
	
	String productUuid;

	String sortBy;
	
	public String getMerchantUuid() {
		return merchantUuid;
	}
	public void setMerchantUuid(String merchantUuid) {
		this.merchantUuid = merchantUuid;
	}

	String merchantUuid;
	
	int startIndex;

	int pageSize;
	
	String supplierName;

	public String getProductUuid() {
		return productUuid;
	}
	public void setProductUuid(String productUuid) {
		this.productUuid = productUuid;
	}

	String[] keyArray = new String[]{};
	
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
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
	public String[] getKeyArray() {
		return keyArray;
	}
	public void setKeyArray(String[] keyArray) {
		this.keyArray = keyArray;
	}

	Date dateStart;	//评论开始日期
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getDateStart() {
		return dateStart;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getDateEnd() {
		return dateEnd;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	Date dateEnd;	//评论结束日期
	
}
