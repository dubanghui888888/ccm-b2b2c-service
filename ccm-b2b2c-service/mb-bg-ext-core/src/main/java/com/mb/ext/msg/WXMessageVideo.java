/**
 * 
 */
package com.mb.ext.msg;

/**
 * <xml>
 <ToUserName><![CDATA[toUser]]></ToUserName>
 <FromUserName><![CDATA[fromUser]]></FromUserName>
 <CreateTime>1357290913</CreateTime>
 <MsgType><![CDATA[video]]></MsgType>
 <MediaId><![CDATA[media_id]]></MediaId>
 <ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>
 <MsgId>1234567890123456</MsgId>
 </xml>
 */
/**
 * @author qiaojianhui ��ƵΪvideo С��ƵΪshortvideo
 */
public class WXMessageVideo extends WXMessageObject {
	private String MediaId;
	private String ThumbMediaId;

	/**
	 * @return the mediaId
	 */
	public String getMediaId() {
		return MediaId;
	}

	/**
	 * @param mediaId
	 *            the mediaId to set
	 */
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	/**
	 * @return the thumbMediaId
	 */
	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	/**
	 * @param thumbMediaId
	 *            the thumbMediaId to set
	 */
	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
}
