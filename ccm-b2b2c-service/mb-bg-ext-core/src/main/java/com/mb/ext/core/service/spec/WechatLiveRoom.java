package com.mb.ext.core.service.spec;

import java.util.List;

public class WechatLiveRoom{

		private String name;
		
		private int roomid;
		
		private String cover_img;	//直播间背景墙
		
		private int live_status;	//直播状态 101: 直播中, 102: 未开始, 103: 已结束, 104: 禁播, 105: 暂停中, 106: 异常, 107: 已过期
		
		private long start_time;	//直播计划开始时间，列表按照 start_time 降序排列
		
		private long end_time;		//直播计划结束时间
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getRoomid() {
			return roomid;
		}

		public void setRoomid(int roomid) {
			this.roomid = roomid;
		}

		public String getCover_img() {
			return cover_img;
		}

		public void setCover_img(String cover_img) {
			this.cover_img = cover_img;
		}

		public int getLive_status() {
			return live_status;
		}

		public void setLive_status(int live_status) {
			this.live_status = live_status;
		}

		public long getStart_time() {
			return start_time;
		}

		public void setStart_time(long start_time) {
			this.start_time = start_time;
		}

		public long getEnd_time() {
			return end_time;
		}

		public void setEnd_time(long end_time) {
			this.end_time = end_time;
		}

		public String getAnchor_name() {
			return anchor_name;
		}

		public void setAnchor_name(String anchor_name) {
			this.anchor_name = anchor_name;
		}

		public String getAnchor_img() {
			return anchor_img;
		}

		public void setAnchor_img(String anchor_img) {
			this.anchor_img = anchor_img;
		}

		public List<WechatLiveRoomGoods> getGoods() {
			return goods;
		}

		public void setGoods(List<WechatLiveRoomGoods> goods) {
			this.goods = goods;
		}

		private String anchor_name;	//主播名
		
		private String anchor_img;
		
		private List<WechatLiveRoomGoods> goods;	//商品列表
		
}
