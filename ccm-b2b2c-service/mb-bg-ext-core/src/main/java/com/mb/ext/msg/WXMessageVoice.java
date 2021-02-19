/**
 * 
 */
package com.mb.ext.msg;
/**
 * <xml>
<ToUserName><![CDATA[toUser]]></ToUserName>
<FromUserName><![CDATA[fromUser]]></FromUserName>
<CreateTime>1357290913</CreateTime>
<MsgType><![CDATA[voice]]></MsgType>
<MediaId><![CDATA[media_id]]></MediaId>
<Format><![CDATA[Format]]></Format>
<MsgId>1234567890123456</MsgId>
</xml>
 */
/**
 * ����Ϊ voice
 * @author qiaojianhui
 *
 */
public class WXMessageVoice extends WXMessageObject {
private String MediaId;
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
/**
 * @return the format
 */
public String getFormat() {
	return Format;
}
/**
 * @param format the format to set
 */
public void setFormat(String format) {
	Format = format;
}
private String Format;
}
