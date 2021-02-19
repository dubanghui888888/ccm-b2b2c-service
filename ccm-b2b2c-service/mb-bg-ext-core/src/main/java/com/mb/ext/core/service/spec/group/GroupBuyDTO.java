package com.mb.ext.core.service.spec.group;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class GroupBuyDTO extends AbstractBaseDTO{
	
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String groupBuyUuid;
	
	private GroupBuyProductDTO groupBuyProductDTO;
	
	List<GroupBuyUserDTO> groupBuyUserList;
	
	private UserDTO ownerDTO;
	
	public List<GroupBuyUserDTO> getGroupBuyUserList() {
		return groupBuyUserList;
	}

	public void setGroupBuyUserList(List<GroupBuyUserDTO> groupBuyUserList) {
		this.groupBuyUserList = groupBuyUserList;
	}

	private OrderDTO orderDTO;

	public OrderDTO getOrderDTO() {
		return orderDTO;
	}

	public void setOrderDTO(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
	}

	private Date startTime;
	
	public String getGroupBuyUuid() {
		return groupBuyUuid;
	}

	public void setGroupBuyUuid(String groupBuyUuid) {
		this.groupBuyUuid = groupBuyUuid;
	}

	public GroupBuyProductDTO getGroupBuyProductDTO() {
		return groupBuyProductDTO;
	}

	public void setGroupBuyProductDTO(GroupBuyProductDTO groupBuyProductDTO) {
		this.groupBuyProductDTO = groupBuyProductDTO;
	}

	public UserDTO getOwnerDTO() {
		return ownerDTO;
	}

	public void setOwnerDTO(UserDTO ownerDTO) {
		this.ownerDTO = ownerDTO;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private Date endTime;

	public int getMinUserCount() {
		return minUserCount;
	}

	public void setMinUserCount(int minUserCount) {
		this.minUserCount = minUserCount;
	}

	public int getJoinUserCount() {
		return joinUserCount;
	}

	public void setJoinUserCount(int joinUserCount) {
		this.joinUserCount = joinUserCount;
	}

	private String status;

	private int minUserCount;

	private int joinUserCount;
}
