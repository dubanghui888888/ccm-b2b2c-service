/**
 * 
 */
package com.mb.ext.msg;

/**
 * <xml>
 <ToUserName><![CDATA[toUser]]></ToUserName>
 <FromUserName><![CDATA[fromUser]]></FromUserName>
 <CreateTime>1351776360</CreateTime>
 <MsgType><![CDATA[location]]></MsgType>
 <Location_X>23.134521</Location_X>
 <Location_Y>113.358803</Location_Y>
 <Scale>20</Scale>
 <Label><![CDATA[λ����Ϣ]]></Label>
 <MsgId>1234567890123456</MsgId>
 </xml>
 */
/**
 * ��Ϣ����Ϊ location
 * 
 * @author qiaojianhui
 * 
 */
public class WXMessageLocation extends WXMessageObject {

	private String Location_X;

	/**
	 * @return the location_X
	 */
	public String getLocation_X() {
		return Location_X;
	}

	/**
	 * @param location_X
	 *            the location_X to set
	 */
	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}

	/**
	 * @return the location_Y
	 */
	public String getLocation_Y() {
		return Location_Y;
	}

	/**
	 * @param location_Y
	 *            the location_Y to set
	 */
	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}

	/**
	 * @return the scale
	 */
	public String getScale() {
		return Scale;
	}

	/**
	 * @param scale
	 *            the scale to set
	 */
	public void setScale(String scale) {
		Scale = scale;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return Label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		Label = label;
	}

	private String Location_Y;
	private String Scale;
	private String Label;
}
