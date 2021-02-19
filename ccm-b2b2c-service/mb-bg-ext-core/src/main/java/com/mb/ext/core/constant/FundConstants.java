package com.mb.ext.core.constant;

import java.math.BigDecimal;

public class FundConstants {

	public final static BigDecimal AMOUNT_ONE_CENT = new BigDecimal("0.01");	//一分钱
	
	public final static String WITHDRAW_STATUS_APPLICATED = "0";	//提交申请
	
	public final static String WITHDRAW_STATUS_APPROVED = "1";		//审核通过
		
	public final static String WITHDRAW_STATUS_REJECTED = "2";		//审核未通过
	
	public final static String WITHDRAW_STATUS_SUCCESS = "3";		//提现成功
	
	public final static String WITHDRAW_STATUS_FAIL = "4";			//提现失败
	
	public final static String WITHDRAW_PAYMENT_METHOD_BANK = "BANK";	//提现至银行
	
	public final static String WITHDRAW_PAYMENT_METHOD_ALIPAY = "ALIPAY";	//提现至支付宝
	
	public final static String WITHDRAW_PAYMENT_METHOD_WECHAT = "WECHAT";	//提现至微信
	
	public final static String CHARGE_PAYMENT_METHOD_ALIPAY = "ALIPAY";	//支付宝充值
	
	public final static String CHARGE_PAYMENT_METHOD_WECHAT = "WECHAT";	//微信充值
	
	public final static String ORDER_PAYMENT_METHOD_ALIPAY = "2";	//提现至支付宝
	
	public final static String ORDER_PAYMENT_METHOD_WECHAT = "1";	//提现至微信
	
	public final static String CHARGE_STATUS_NEW = "0";	//未支付
	
	public final static String CHARGE_STATUS_COMPLETED = "1";		//已支付
	
	public final static String MERCHANT_STATEMENT_TRANSACTION_TYPE_ORDER = "1";	//订单收入
	
	public final static String MERCHANT_STATEMENT_TRANSACTION_TYPE_ORDER_RETURN= "2";	//订单退款
	
	public final static String MERCHANT_STATEMENT_TRANSACTION_TYPE_WITHDRAW = "3";	//提现
	
	public final static String MERCHANT_STATEMENT_TRANSACTION_TYPE_AD_IN = "4";	//广告费用收入
	
	public final static String MERCHANT_STATEMENT_TRANSACTION_TYPE_AD_OUT = "5";//广告费用支出
	
	public final static String MERCHANT_STATEMENT_TRANSACTION_TYPE_MONEY_RETURN = "6";//会员消费返佣
	
	public final static String MERCHANT_STATEMENT_TRANSACTION_TYPE_PLATFORM_RETURN = "7";//平台提成
	
	public final static String MERCHANT_STATEMENT_TRANSACTION_TYPE_CHARGE = "8";	//充值
	
	public final static String MERCHANT_STATEMENT_TRANSACTION_TYPE_MONEY_DEDUCT = "9";//会员消费扣除
	
	
	public final static String USER_STATEMENT_TRANSACTION_TYPE_ORDER_PAY_BALANCE = "1";	//购买商品余额支付
	public final static String USER_STATEMENT_TRANSACTION_TYPE_RECRUIT = "2";	//推广奖金
	public final static String USER_STATEMENT_TRANSACTION_TYPE_SALE = "3";	//销售奖金
	public final static String USER_STATEMENT_TRANSACTION_TYPE_PERFORMANCE = "4";	//团队业绩奖金
	public final static String USER_STATEMENT_TRANSACTION_TYPE_TRAINER = "5";	//培训奖金
	public final static String USER_STATEMENT_TRANSACTION_TYPE_WITHDRAW = "6";	//提现
	public final static String USER_STATEMENT_TRANSACTION_TYPE_REFUND = "7";	//退款
}
