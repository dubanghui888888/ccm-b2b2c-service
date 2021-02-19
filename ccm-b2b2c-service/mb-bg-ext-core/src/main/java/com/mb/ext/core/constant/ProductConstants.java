package com.mb.ext.core.constant;

public class ProductConstants {

	
	public final static String DELIMETER = ",";
	
	public final static String PRODUCT_TYPE_REAL = "1";	//实物商品, 物流发货
	
	public final static String PRODUCT_TYPE_VIRTUAL = "2";	//虚拟商品, 无需物流, 无需核销
	
	public final static String PRODUCT_TYPE_VOUCHER = "3";	//核销类商品, 无需物流, 可核销
	
	public final static String PRODUCT_VERIFY_STATUS_SUBMITTED = "0";	//审核中
	
	public final static String PRODUCT_VERIFY_STATUS_APPROVED = "1";	//审核通过
	
	public final static String PRODUCT_VERIFY_STATUS_REJECTED = "2";	//审核未通过
}
