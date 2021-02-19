package com.mb.ext.msg;

public class WXSuscribeMessage {
private String touser;
private String template_id;
private String page;
private WXSuscribeMessageData data;
public String getTouser() {
	return touser;
}
public void setTouser(String touser) {
	this.touser = touser;
}
public String getTemplate_id() {
	return template_id;
}
public void setTemplate_id(String template_id) {
	this.template_id = template_id;
}
public String getPage() {
	return page;
}
public void setPage(String page) {
	this.page = page;
}
public WXSuscribeMessageData getData() {
	return data;
}
public void setData(WXSuscribeMessageData data) {
	this.data = data;
}

}
