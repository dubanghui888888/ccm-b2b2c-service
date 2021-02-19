package com.mb.ext.core.service.spec;

import java.util.List;

import com.mb.framework.service.spec.AbstractBaseDTO;

/**
 * 微信直播间返回数据
 */
public class WechatLiveRoomsResponse  extends AbstractBaseDTO{

	private static final long serialVersionUID = -5170368522247247499L;
	
	private int errcode;	//errcode = 0 代表成功；errcode = 1 代表未创建直播房间
	
	private String errmsg;
	
	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public List<WechatLiveRoom> getRoom_info() {
		return room_info;
	}

	public void setRoom_info(List<WechatLiveRoom> room_info) {
		this.room_info = room_info;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	private List<WechatLiveRoom> room_info;
	
	private int total;
	
}
