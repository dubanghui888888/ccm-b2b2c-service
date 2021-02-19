package com.mb.ext.msg;

import com.mb.ext.msg.WXMessageObject;
/**
 * <xml>
 <ToUserName><![CDATA[toUser]]></ToUserName>
 <FromUserName><![CDATA[fromUser]]></FromUserName>
 <CreateTime>1348831860</CreateTime>
 <MsgType><![CDATA[text]]></MsgType>
 <Content><![CDATA[this is a test]]></Content>
 <MsgId>1234567890123456</MsgId>
 </xml>
 */
/**
 * �̳л���WXMessageObject ���ɵ��ı���Ϣ��
  * @author qiaojianhui * 
 */
public class WXMessageText extends WXMessageObject {
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
