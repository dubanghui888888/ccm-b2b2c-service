package com.mb.ext.msg;

public class WXSuscribeMessageDataAward extends WXSuscribeMessageData{
	 
	//活动名称{{thing1.DATA}}
	private WXSuscribeMessageDataValue thing1;
	
	//奖励内容{{thing2.DATA}}
	private WXSuscribeMessageDataValue thing2;
	
	public WXSuscribeMessageDataValue getThing1() {
		return thing1;
	}

	public void setThing1(WXSuscribeMessageDataValue thing1) {
		this.thing1 = thing1;
	}

	public WXSuscribeMessageDataValue getThing2() {
		return thing2;
	}

	public void setThing2(WXSuscribeMessageDataValue thing2) {
		this.thing2 = thing2;
	}

	public WXSuscribeMessageDataValue getThing3() {
		return thing3;
	}

	public void setThing3(WXSuscribeMessageDataValue thing3) {
		this.thing3 = thing3;
	}

	//温馨提示{{thing3.DATA}}
	private WXSuscribeMessageDataValue thing3;
	
}
