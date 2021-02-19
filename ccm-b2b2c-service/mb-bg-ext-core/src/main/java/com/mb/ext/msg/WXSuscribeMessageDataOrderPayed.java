package com.mb.ext.msg;

public class WXSuscribeMessageDataOrderPayed extends WXSuscribeMessageData{
	 
	//订单编号{{character_string1.DATA}}
	private WXSuscribeMessageDataValue character_string1;
	
	//支付时间{{date2.DATA}}
	private WXSuscribeMessageDataValue date2;
	
	//商品名称{{thing3.DATA}}
	private WXSuscribeMessageDataValue thing3;
	
	//支付金额{{amount4.DATA}}
	private WXSuscribeMessageDataValue amount4;
	
	public WXSuscribeMessageDataValue getCharacter_string1() {
		return character_string1;
	}

	public void setCharacter_string1(WXSuscribeMessageDataValue character_string1) {
		this.character_string1 = character_string1;
	}

	public WXSuscribeMessageDataValue getDate2() {
		return date2;
	}

	public void setDate2(WXSuscribeMessageDataValue date2) {
		this.date2 = date2;
	}

	public WXSuscribeMessageDataValue getThing3() {
		return thing3;
	}

	public void setThing3(WXSuscribeMessageDataValue thing3) {
		this.thing3 = thing3;
	}

	public WXSuscribeMessageDataValue getAmount4() {
		return amount4;
	}

	public void setAmount4(WXSuscribeMessageDataValue amount4) {
		this.amount4 = amount4;
	}

	public WXSuscribeMessageDataValue getThing7() {
		return thing7;
	}

	public void setThing7(WXSuscribeMessageDataValue thing7) {
		this.thing7 = thing7;
	}

	//备注{{thing7.DATA}}
	private WXSuscribeMessageDataValue thing7;
	
}
