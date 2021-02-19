/**
 * 
 */
package com.mb.ext.msg;

/**
 * <xml>
 <ToUserName><![CDATA[toUser]]></ToUserName>
 <FromUserName><![CDATA[fromUser]]></FromUserName>
 <CreateTime>1351776360</CreateTime>
 <MsgType><![CDATA[link]]></MsgType>
 <Title><![CDATA[����ƽ̨��������]]></Title>
 <Description><![CDATA[����ƽ̨��������]]></Description>
 <Url><![CDATA[url]]></Url>
 <MsgId>1234567890123456</MsgId>
 </xml>
 */
/**
 * @author qiaojianhui ��Ϣ���� link
 */
public class WXMessageLink extends WXMessageObject {
	private String Title;
	private String Description;

	/**
	 * @return the title
	 */
	public String getTitle() {
		return Title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		Title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return Description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		Description = description;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return Url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		Url = url;
	}

	private String Url;
}
