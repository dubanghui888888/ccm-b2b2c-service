/**
 * 
 */
package com.mb.ext.msg;

/**
 * @author qiaojianhui
 * 
 */
public class WXJSONMenuButtonClick extends  WXJSONMenuButton{ 
	private String key;

	public WXJSONMenuButtonClick(){
		this.setType("click");
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
 
}
