package com.mb.ext.core.service.spec.order;

import com.mb.framework.service.spec.AbstractBaseDTO;

/**
 * 全国快递物流查询返回数据对象
 * @author 
 *
 */
public class CourierDTO extends AbstractBaseDTO{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7328544121596642054L;
	private String status;
	
	public String getStatus() {
		return status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CourierResultDTO getResult() {
		return result;
	}

	public void setResult(CourierResultDTO result) {
		this.result = result;
	}

	private String msg;
	
	private CourierResultDTO result;
	
}
