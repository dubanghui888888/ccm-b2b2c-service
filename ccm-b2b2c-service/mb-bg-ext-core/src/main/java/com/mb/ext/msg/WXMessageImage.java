/**
 * 
 */
package com.mb.ext.msg;
/**
 * <xml>
 <ToUserName><![CDATA[toUser]]></ToUserName>
 <FromUserName><![CDATA[fromUser]]></FromUserName>
 <CreateTime>1348831860</CreateTime>
 <MsgType><![CDATA[image]]></MsgType>
 <PicUrl><![CDATA[this is a url]]></PicUrl>
 <MediaId><![CDATA[media_id]]></MediaId>
 <MsgId>1234567890123456</MsgId>
 </xml>
 */

/**
 * ��Ϣ���� image
 * 
 * @author qiaojianhui * 
 */
public class WXMessageImage extends WXMessageObject {
private String PicUrl;
private String MediaId;
/**
 * @return the picUrl
 */
public String getPicUrl() {
	return PicUrl;
}
/**
 * @param picUrl the picUrl to set
 */
public void setPicUrl(String picUrl) {
	PicUrl = picUrl;
}
/**
 * @return the mediaId
 */
public String getMediaId() {
	return MediaId;
}
/**
 * @param mediaId the mediaId to set
 */
public void setMediaId(String mediaId) {
	MediaId = mediaId;
}
}
