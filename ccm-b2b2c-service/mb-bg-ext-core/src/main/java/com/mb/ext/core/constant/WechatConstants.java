package com.mb.ext.core.constant;

public class WechatConstants {

	/**
	 * 微信统一下单接口
	 */
	public final static String WECHAT_UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	/**
	 * 微信退款接口
	 */
	public final static String WECHAT_REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	/**
	 * 获取微信jsapi ticketַ
	 */
	public final static String URL_JSAPI_TICKET=" https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	
	/*
	 * 发送订阅消息
	 */
	public final static String URL_WX_SEND_SUSCRIBE_MSG = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=ACCESS_TOKEN";
	
	/**
	 * 支付成功订阅消息
	 */
	public final static String WX_MESSAGE_SUSCRIBE_ID_ORDER_PAYED = "nt6tq4z9YcwVfcPVQyU-lSTTaGvU0YHAN_XUBY3bW0I";
	
	/**
	 * 订单发货订阅消息
	 */
	public final static String WX_MESSAGE_SUSCRIBE_ID_ORDER_DELIVERED = "kiOjjLxOrmmJNrdEy1FvMC2KmTDw9X1mFLETRjF4siA";
	
	/**
	 * 退款状态订阅消息
	 */
	public final static String WX_MESSAGE_SUSCRIBE_ID_ORDER_RETURN = "C5MZY1fd89PM72PrNn5pMjkHlqUhJcdKWh1WKoVjjDI";
	
	/**
	 * 微信支付商品名称
	 */
	public final static String PRODUCT_NAME = "商品购买";
	
	/**
	 * 微信支付商品编码
	 */
	public final static String PRODUCT_ID = "spgm";
	
	
	public final static String URL_QRCODE_TICKET="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";

	public final static String URL_QRCODE="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET&redirect=http://score.junengshop.com";
	
	
	/**
	 * 自定义TOKEN
	 */
	public static final String TOKEN="qhy_ezWork_2017";
	
	/**
	 * 获取token
	 */
	public final static String URL_ACCESSTOKEN="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APP_ID&secret=APP_SECRET";
	
	/**
	 * 通过open id换取微信用户信息
	 */
	public final static String URL_WX_USER_INFO="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
	
	/**
	 * 发送客服消息(ACCESS_TOKEN对应上面的URL_ACCESSTOKEN_OFFICIAL_ACCOUNT)
	 */
	public final static String URL_WX_SEND_CUSTOMER_MSG="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

	/**
	 * 小程序参数二维码
	 */
	public final static String URL_WXA_CODE="https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN";

	/**
	 * 获取直播房间列表
	 */
	public final static String URL_LIVE_ROOMS = "https://api.weixin.qq.com/wxa/business/getliveinfo?access_token=ACCESS_TOKEN";
}
