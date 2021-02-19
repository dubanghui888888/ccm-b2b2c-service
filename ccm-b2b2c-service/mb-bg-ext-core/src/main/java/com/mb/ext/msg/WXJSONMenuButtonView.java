/**
 * 
 */
package com.mb.ext.msg;

/**
 * @author qiaojianhui
 *
 */
public class WXJSONMenuButtonView  extends  WXJSONMenuButton{ 	
 	private String url;
 	
 	public WXJSONMenuButtonView(){
 	 	this.setType("view");
 	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
