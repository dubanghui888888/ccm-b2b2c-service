package com.mb.ext.core.service.spec;

/**
 * 微信直播间请求数据
 */
public class WechatLiveRoomsRequest{

	
	// 起始拉取房间，start = 0 表示从第 1 个房间开始拉取
	private int start;
	// 每次拉取的个数上限，不要设置过大，建议 100 以内
	private int limit;
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
}
