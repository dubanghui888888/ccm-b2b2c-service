package com.mb.ext.msg;

public class WXSuscribeMessageDataOrderReturn extends WXSuscribeMessageData{
	 
	//订单编号{{character_string4.DATA}}
	private WXSuscribeMessageDataValue character_string4;
	
	//申请时间{{date3.DATA}}
	private WXSuscribeMessageDataValue date3;
	
	//商品名称{{thing5.DATA}}
	private WXSuscribeMessageDataValue thing5;
	
	public WXSuscribeMessageDataValue getCharacter_string4() {
		return character_string4;
	}

	public void setCharacter_string4(WXSuscribeMessageDataValue character_string4) {
		this.character_string4 = character_string4;
	}

	public WXSuscribeMessageDataValue getDate3() {
		return date3;
	}

	public void setDate3(WXSuscribeMessageDataValue date3) {
		this.date3 = date3;
	}

	public WXSuscribeMessageDataValue getThing5() {
		return thing5;
	}

	public void setThing5(WXSuscribeMessageDataValue thing5) {
		this.thing5 = thing5;
	}

	public WXSuscribeMessageDataValue getAmount2() {
		return amount2;
	}

	public void setAmount2(WXSuscribeMessageDataValue amount2) {
		this.amount2 = amount2;
	}

	public WXSuscribeMessageDataValue getThing6() {
		return thing6;
	}

	public void setThing6(WXSuscribeMessageDataValue thing6) {
		this.thing6 = thing6;
	}

	//退款金额{{amount2.DATA}}
	private WXSuscribeMessageDataValue amount2;
	
	//退款状态{{thing6.DATA}}
	private WXSuscribeMessageDataValue thing6;
	
}
