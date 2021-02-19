/**
 * 
 */
package com.mb.ext.msg;

/**
 *  ΢����Ϣ���࣬����<br>
 *  ToUserName	������΢�ź�<br>
*   FromUserName	���ͷ��ʺţ�һ��OpenID��<br>
*   CreateTime	��Ϣ����ʱ�� �����ͣ�<br>
*   MsgType	text <br>
*   MsgId	��Ϣid��64λ����<br> ��5�������ֶΡ�
 * @author qiaojianhui
 * 
 */
public class WXMessageObject {
	private String ToUserName;
	private String FromUserName;
	private long CreateTime;
	private String MsgType;
	private long MsgId;
	
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public long getMsgId() {
		return MsgId;
	}
	public void setMsgId(long msgId) {
		MsgId = msgId;
	}
 
	
}
