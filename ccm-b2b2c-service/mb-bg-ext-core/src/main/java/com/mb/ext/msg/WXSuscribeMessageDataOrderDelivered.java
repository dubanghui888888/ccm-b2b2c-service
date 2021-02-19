package com.mb.ext.msg;

public class WXSuscribeMessageDataOrderDelivered extends WXSuscribeMessageData{
	 
	//订单编号{{character_string1.DATA}}
	private WXSuscribeMessageDataValue character_string1;
	
	//发货时间{{date5.DATA}}
	private WXSuscribeMessageDataValue date5;
	
	public WXSuscribeMessageDataValue getCharacter_string1() {
		return character_string1;
	}

	public void setCharacter_string1(WXSuscribeMessageDataValue character_string1) {
		this.character_string1 = character_string1;
	}

	public WXSuscribeMessageDataValue getDate5() {
		return date5;
	}

	public void setDate5(WXSuscribeMessageDataValue date5) {
		this.date5 = date5;
	}

	public WXSuscribeMessageDataValue getThing2() {
		return thing2;
	}

	public void setThing2(WXSuscribeMessageDataValue thing2) {
		this.thing2 = thing2;
	}

	public WXSuscribeMessageDataValue getCharacter_string4() {
		return character_string4;
	}

	public void setCharacter_string4(WXSuscribeMessageDataValue character_string4) {
		this.character_string4 = character_string4;
	}

	public WXSuscribeMessageDataValue getThing7() {
		return thing7;
	}

	public void setThing7(WXSuscribeMessageDataValue thing7) {
		this.thing7 = thing7;
	}

	//商品名称{{thing2.DATA}}
	private WXSuscribeMessageDataValue thing2;
	
	//快递单号{{character_string4.DATA}}
	private WXSuscribeMessageDataValue character_string4;
	
	//快递公司{{thing7.DATA}}
	private WXSuscribeMessageDataValue thing7;
	
}
