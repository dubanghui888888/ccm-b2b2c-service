package com.mb.ext.core.service.spec;

public class WechatRefundResponse {

	private String return_code;
	private String return_msg;
	private String result_code;
	private String err_code;
	private String err_code_des;
	
	private String appid;
	private String mch_id;
	private String nonce_str;
	private String sign;
	public String getRefund_channel() {
		return refund_channel;
	}
	public void setRefund_channel(String refund_channel) {
		this.refund_channel = refund_channel;
	}
	private String transaction_id;
	private String out_trade_no;
	private String out_refund_no;
	private String refund_id;
	private String refund_channel;
	private int refund_fee;
	private int settlement_refund_fee;
	private int total_fee;
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getOut_refund_no() {
		return out_refund_no;
	}
	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}
	public String getRefund_id() {
		return refund_id;
	}
	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}
	public int getRefund_fee() {
		return refund_fee;
	}
	public void setRefund_fee(int refund_fee) {
		this.refund_fee = refund_fee;
	}
	public int getSettlement_refund_fee() {
		return settlement_refund_fee;
	}
	public void setSettlement_refund_fee(int settlement_refund_fee) {
		this.settlement_refund_fee = settlement_refund_fee;
	}
	public int getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}
	public int getSettlement_total_fee() {
		return settlement_total_fee;
	}
	public void setSettlement_total_fee(int settlement_total_fee) {
		this.settlement_total_fee = settlement_total_fee;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public String getCash_fee() {
		return cash_fee;
	}
	public void setCash_fee(String cash_fee) {
		this.cash_fee = cash_fee;
	}
	public String getCash_fee_type() {
		return cash_fee_type;
	}
	public void setCash_fee_type(String cash_fee_type) {
		this.cash_fee_type = cash_fee_type;
	}
	public int getCash_refund_fee() {
		return cash_refund_fee;
	}
	public void setCash_refund_fee(int cash_refund_fee) {
		this.cash_refund_fee = cash_refund_fee;
	}
	public String getCoupon_type_0() {
		return coupon_type_0;
	}
	public void setCoupon_type_0(String coupon_type_0) {
		this.coupon_type_0 = coupon_type_0;
	}
	public int getCoupon_refund_fee() {
		return coupon_refund_fee;
	}
	public void setCoupon_refund_fee(int coupon_refund_fee) {
		this.coupon_refund_fee = coupon_refund_fee;
	}
	public String getCoupon_refund_fee_0() {
		return coupon_refund_fee_0;
	}
	public void setCoupon_refund_fee_0(String coupon_refund_fee_0) {
		this.coupon_refund_fee_0 = coupon_refund_fee_0;
	}
	public int getCoupon_refund_count() {
		return coupon_refund_count;
	}
	public void setCoupon_refund_count(int coupon_refund_count) {
		this.coupon_refund_count = coupon_refund_count;
	}
	public String getCoupon_refund_id_0() {
		return coupon_refund_id_0;
	}
	public void setCoupon_refund_id_0(String coupon_refund_id_0) {
		this.coupon_refund_id_0 = coupon_refund_id_0;
	}
	public String getCoupon_type_1() {
		return coupon_type_1;
	}
	public void setCoupon_type_1(String coupon_type_1) {
		this.coupon_type_1 = coupon_type_1;
	}
	public String getCoupon_refund_fee_1() {
		return coupon_refund_fee_1;
	}
	public void setCoupon_refund_fee_1(String coupon_refund_fee_1) {
		this.coupon_refund_fee_1 = coupon_refund_fee_1;
	}
	public String getCoupon_refund_id_1() {
		return coupon_refund_id_1;
	}
	public void setCoupon_refund_id_1(String coupon_refund_id_1) {
		this.coupon_refund_id_1 = coupon_refund_id_1;
	}
	public String getCoupon_type_2() {
		return coupon_type_2;
	}
	public void setCoupon_type_2(String coupon_type_2) {
		this.coupon_type_2 = coupon_type_2;
	}
	public String getCoupon_refund_fee_2() {
		return coupon_refund_fee_2;
	}
	public void setCoupon_refund_fee_2(String coupon_refund_fee_2) {
		this.coupon_refund_fee_2 = coupon_refund_fee_2;
	}
	public String getCoupon_refund_id_2() {
		return coupon_refund_id_2;
	}
	public void setCoupon_refund_id_2(String coupon_refund_id_2) {
		this.coupon_refund_id_2 = coupon_refund_id_2;
	}
	private int settlement_total_fee;
	
	private String fee_type;
	private String cash_fee;
	private String cash_fee_type;
	private int cash_refund_fee;
	
	private String coupon_type_0;
	private int coupon_refund_fee;
	private String coupon_refund_fee_0;
	private int coupon_refund_count;
	private String coupon_refund_id_0;
	
	private String coupon_type_1;
	private String coupon_refund_fee_1;
	private String coupon_refund_id_1;
	
	private String coupon_type_2;
	private String coupon_refund_fee_2;
	private String coupon_refund_id_2;
}
