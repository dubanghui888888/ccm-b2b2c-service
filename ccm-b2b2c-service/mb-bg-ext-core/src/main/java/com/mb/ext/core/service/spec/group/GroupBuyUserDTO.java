package com.mb.ext.core.service.spec.group;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.util.JsonDateMinuteDeserializer;
import com.mb.ext.core.util.JsonDateMinuteSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class GroupBuyUserDTO extends AbstractBaseDTO{
	
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String groupBuyUserUuid;

	private GroupBuyDTO groupBuyDTO;
	
	private UserDTO userDTO;
	
	private OrderDTO orderDTO;

	public String getGroupBuyUserUuid() {
		return groupBuyUserUuid;
	}

	public void setGroupBuyUserUuid(String groupBuyUserUuid) {
		this.groupBuyUserUuid = groupBuyUserUuid;
	}

	public GroupBuyDTO getGroupBuyDTO() {
		return groupBuyDTO;
	}

	public void setGroupBuyDTO(GroupBuyDTO groupBuyDTO) {
		this.groupBuyDTO = groupBuyDTO;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public OrderDTO getOrderDTO() {
		return orderDTO;
	}

	public void setOrderDTO(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getJoinTime() {
		return joinTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	public boolean isOwner() {
		return isOwner;
	}

	public void setOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}

	private Date joinTime;
	
	private boolean isOwner;
}
