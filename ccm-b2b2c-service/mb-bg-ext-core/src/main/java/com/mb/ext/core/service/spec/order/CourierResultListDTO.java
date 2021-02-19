package com.mb.ext.core.service.spec.order;

/**
 * 全国快递物流查询返回数据子对象
 * @author 
 *
 */
public class CourierResultListDTO{
	
	private String time;
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private String status;
	
}
