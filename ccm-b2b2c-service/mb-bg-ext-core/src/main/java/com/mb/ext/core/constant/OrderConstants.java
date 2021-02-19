package com.mb.ext.core.constant;

public class OrderConstants {
	
	public final static String ORDER_TYPE_MN = "MN";	//线上购买合并订单号
	
	public final static String ORDER_TYPE_GM = "GM";	//线上购买订单
	
	public final static String ORDER_TYPE_PD = "PD";	//线下消费订单
	
	public final static String ORDER_TYPE_MC = "MC";	//充值
	
	public final static String ORDER_TYPE_AF = "AF";	//售后单
	
	public final static String ORDER_TYPE_MW = "MW";	//商家提现编号
	
	public final static String ORDER_TYPE_UW = "UW";	//会员提现编号
	
	public final static String ORDER_DELIVERY_TYPE_EXPRESS = "1";	//快递配送
	
	public final static String ORDER_DELIVERY_TYPE_CITY = "2";	//同城配送
	
	public final static String ORDER_DELIVERY_TYPE_PICK = "3";	//门店自提
	
	public final static String ORDER_STATUS_NOT_PAYED = "0";	//待付款
	
	public final static String ORDER_STATUS_NOT_DELIVERIED = "1";	//已付款待发货
	
	public final static String ORDER_STATUS_DELIVERIED = "2";	//已发货
	
	public final static String ORDER_STATUS_COMPLETED = "3";	//已完成-待评价（小程序）
	
	public final static String ORDER_STATUS_CANCELLED = "4";	//已取消
	
	public final static String ORDER_STATUS_EVALUATED = "5";	//已评价-已完成（小程序）
	
	public final static String ORDER_PAYMENT_METHOD_BALANCE = "3";	//余额
	
	public final static String ORDER_PAYMENT_METHOD_ALIPAY = "2";	//支付宝
	
	public final static String ORDER_PAYMENT_METHOD_WECHAT = "1";	//微信
	
	public final static String ORDER_AFTER_SALE_TYPE_REFUND= "1";	//售后仅退款
	
	public final static String ORDER_AFTER_SALE_TYPE_REFUND_PRODUCT= "2";	//售后退款退货
	
	public final static String ORDER_AFTER_SALE_STATUS_CREATE= "0";	//售后处理中
	
	public final static String ORDER_AFTER_SALE_STATUS_APPROVED= "1";	//售后审核通过
	
	public final static String ORDER_AFTER_SALE_STATUS_REJECTED= "2";	//售后审核拒绝
	
	public final static String ORDER_AFTER_SALE_STATUS_WAITING_CONFIRM_COURIER= "3";	//退款退货中用户已邮寄, 待卖家确认收货
	
	public final static String ORDER_AFTER_SALE_STATUS_COMPLETED= "9";	//退款成功
//	public final static String ORDER_STATUS_SENT = "2";	//卖家已发货
//	
//	public final static String ORDER_STATUS_RECEIVED = "3";	//买家已签收
//	
//	public final static String ORDER_STATUS_CANCELLED = "4";	//已取消
//	
//	public final static String ORDER_STATUS_REJECTED = "5";	//资金验收失败
//	
//	public final static String ORDER_STATUS_RETURN_APPLICATED = "1";	//买家申请退货
//	
//	public final static String ORDER_STATUS_RETURN_NOT_SENT = "2";	//退款, 等待买家发货
//	
//	public final static String ORDER_STATUS_RETURN_REJECTED = "3";	//卖家拒绝退货
//	
//	public final static String ORDER_STATUS_RETURN_SENT = "4";	//退款,卖家已发货
//	
//	public final static String ORDER_STATUS_RETURN_RECEIVED = "5";	//退款,卖家已收货
//	
//	public final static int FREIGHT_CHARGETYPE_WEIGHT = 1;		//按重量计算运费
//	
//	public final static int FREIGHT_CHARGETYPE_UNIT = 2;		//按件数计算运费
	
//	public final static String USER_INTEGRAL_RULE= "INTEGRAL_RULE";		//用户积分设置
	
//	public final static String USER_REGISTER_INTEGRAL= "REGISTER_INTEGRAL";		//用户注册送积分
	
//	public final static String USER_INTEGRAL_STATEMENT_TRANSACTION_TYPE_ORDER = "1";	//购物积分
	
//	public final static String USER_INTEGRAL_STATEMENT_TRANSACTION_TYPE_REGISTER = "2";	//登录积分
	
//	public final static String USER_INTEGRAL_STATEMENT_TRANSACTION_TYPE_SIGN = "3";	//签到积分
	
//	public final static BigDecimal USER_PERFORMANCE_UNIT_PRICE = BigDecimal.valueOf(1090);	//团队业绩每箱价格
	
}
