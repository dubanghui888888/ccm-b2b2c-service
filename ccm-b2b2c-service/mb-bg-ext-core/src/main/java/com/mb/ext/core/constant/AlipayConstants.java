package com.mb.ext.core.constant;

public class AlipayConstants {

	// 请求网关地址
    public static String ALIPAY_URL = "https://openapi.alipay.com/gateway.do";
    // 编码
    public static String ALIPAY_CHARSET = "UTF-8";
    // 返回格式
    public static String ALIPAY_FORMAT = "json";
    
    // RSA2
    public static String ALIPAY_SIGNTYPE = "RSA2";
    // 商品名称
    public static String ALIPAY_PRODUCT_NAME = "商品购买";
    // 电脑网站支付
    public static String ALIPAY_PRODUCT_CODE = "FAST_INSTANT_TRADE_PAY";
    //快捷手机wap支付
    public static String ALIPAY_PRODUCT_CODE_QUICK_WAP_WAY = "QUICK_WAP_WAY";
    //快捷手机MSECURITY支付
    public static String ALIPAY_PRODUCT_CODE_QUICK_MSECURITY_WAY = "QUICK_MSECURITY_PAY";
    
  //购买商品后同步跳转通知
    public static String ALIPAY_RETURN_URL_PAY = "http://b2b2c.ccmao.net/pages/money/paySuccess";
}
