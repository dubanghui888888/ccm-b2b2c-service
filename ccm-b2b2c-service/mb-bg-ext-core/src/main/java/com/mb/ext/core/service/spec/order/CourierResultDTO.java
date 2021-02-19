package com.mb.ext.core.service.spec.order;

import java.util.List;
/**
 * 全国快递物流查询返回数据子对象
 * @author 
 *
 */
public class CourierResultDTO{
	
	private String number;			/*  快递单号                  */
	private String type;			/*  快递公司简称                  */
	private String deliverystatus;	/* 0：快递收件(揽件)1.在途中 2.正在派件 3.已签收 4.派送失败 5.疑难件 6.退件签收  */
	private String issign;          /*  1.是否签收                  */
	private String expName;         /*  快递公司名称                */       
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDeliverystatus() {
		return deliverystatus;
	}
	public void setDeliverystatus(String deliverystatus) {
		this.deliverystatus = deliverystatus;
	}
	public String getIssign() {
		return issign;
	}
	public void setIssign(String issign) {
		this.issign = issign;
	}
	public String getExpName() {
		return expName;
	}
	public void setExpName(String expName) {
		this.expName = expName;
	}
	public String getExpSite() {
		return expSite;
	}
	public void setExpSite(String expSite) {
		this.expSite = expSite;
	}
	public String getExpPhone() {
		return expPhone;
	}
	public void setExpPhone(String expPhone) {
		this.expPhone = expPhone;
	}
	public String getCourier() {
		return courier;
	}
	public void setCourier(String courier) {
		this.courier = courier;
	}
	public String getCourierPhone() {
		return courierPhone;
	}
	public void setCourierPhone(String courierPhone) {
		this.courierPhone = courierPhone;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getTakeTime() {
		return takeTime;
	}
	public void setTakeTime(String takeTime) {
		this.takeTime = takeTime;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	private String expSite;         /*  快递公司官网                */
	private String expPhone;        /*  快递公司电话                */
	private String courier;         /*  快递员 或 快递站(没有则为空)*/
	private String courierPhone;    /*  快递员电话 (没有则为空)     */
	private String updateTime; 		/*  快递轨迹信息最新时间        */
	private String takeTime;        /*  发货到收货消耗时长 (截止最新轨迹)  */
	private String logo; 			/* 快递公司LOGO */
	private List<CourierResultListDTO> list;
	public List<CourierResultListDTO> getList() {
		return list;
	}
	public void setList(List<CourierResultListDTO> list) {
		this.list = list;
	}
		
}
