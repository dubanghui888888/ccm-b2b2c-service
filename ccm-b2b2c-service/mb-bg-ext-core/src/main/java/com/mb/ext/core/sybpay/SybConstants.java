package com.mb.ext.core.sybpay;

public class SybConstants {
	public static final String SYB_ORGID = "";
	public static final String SYB_CUSID = "56065104816DHRE";
	public static final String SYB_APPID = "00177295";
	public static final String SYB_APPKEY = "123wangsheng";
	public static final String SYB_APIURL = "https://vsp.allinpay.com/apiweb/unitorder";//生产环境
	public static final String SYB_H5URL = "https://syb.allinpay.com/apiweb/h5unionpay/unionorder";//生产环境
	
	/**
	 * 通联支付回调接口
	 */
	public final static String SYB_NOTIFY_URL = "http://p.junengshop.com/juneng/sybPayResponse";	//聚能链和
	/**
	 * 通联支付页面同步回调地址
	 */
	public final static String SYB_RETURN_URL = "http://p.junengshop.com/payResult?orderNo=OUTTRADENO";
}
