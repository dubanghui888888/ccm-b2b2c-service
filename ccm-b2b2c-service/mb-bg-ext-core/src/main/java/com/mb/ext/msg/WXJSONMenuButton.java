/**
 * 
 */
package com.mb.ext.msg;
 
/**
 * @author qiaojianhui
 *
 */
public class WXJSONMenuButton {
private String name;
private String type;
private WXJSONMenuButton[] sub_button;
/**
 * @return the name
 */
public String getName() {
	return name;
}
/**
 * @param name the name to set
 */
public void setName(String name) {
	this.name = name;
}
/**
 * @return the type
 */
public String getType() {
	return type;
}
/**
 * @param type the type to set
 */
public void setType(String type) {
	this.type = type;
}
/**
 * @return the sub_button
 */
public WXJSONMenuButton[] getSub_button() {
	return sub_button;
}
/**
 * @param sub_button the sub_button to set
 */
public void setSub_button(WXJSONMenuButton[] sub_button) {
	this.sub_button = sub_button;
}
 
}
