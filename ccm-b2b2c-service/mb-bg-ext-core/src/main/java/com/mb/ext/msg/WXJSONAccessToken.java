/**
 * 
 */
package com.mb.ext.msg;

/**
 * @author qiaojianhui
 *
 */
public class WXJSONAccessToken {
private String access_token;
private String refresh_token;
private String openId;
private String scope;
public String getRefresh_token() {
	return refresh_token;
}
public void setRefresh_token(String refresh_token) {
	this.refresh_token = refresh_token;
}
public String getOpenId() {
	return openId;
}
public void setOpenId(String openId) {
	this.openId = openId;
}
public String getScope() {
	return scope;
}
public void setScope(String scope) {
	this.scope = scope;
}
/**
 * @return the access_token
 */
public String getAccess_token() {
	return access_token;
}
/**
 * @param access_token the access_token to set
 */
public void setAccess_token(String access_token) {
	this.access_token = access_token;
}
/**
 * @return the expires_in
 */
public int getExpires_in() {
	return expires_in;
}
/**
 * @param expires_in the expires_in to set
 */
public void setExpires_in(int expires_in) {
	this.expires_in = expires_in;
}
private int expires_in;
}
