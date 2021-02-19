package com.mb.ext.core.entity.group;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.order.OrderEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * 团购单表
 * 
 */
@Entity
@Table(name = "group_buy_user")
@NamedQuery(name = "GroupBuyUserEntity.findAll", query = "SELECT u FROM GroupBuyUserEntity u")
public class GroupBuyUserEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 2233062138920658290L;

	@Id
	@GeneratedValue(generator = "GROUP_BUY_USER_UUID")
	@GenericGenerator(name = "GROUP_BUY_USER_UUID", strategy = "uuid")
	@Column(name = "GROUP_BUY_USER_UUID", nullable = false, length = 36)
	private String groupBuyUserUuid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_BUY_UUID")
	private GroupBuyEntity groupBuyEntity;
	
	public String getGroupBuyUserUuid() {
		return groupBuyUserUuid;
	}

	public void setGroupBuyUserUuid(String groupBuyUserUuid) {
		this.groupBuyUserUuid = groupBuyUserUuid;
	}

	public GroupBuyEntity getGroupBuyEntity() {
		return groupBuyEntity;
	}

	public void setGroupBuyEntity(GroupBuyEntity groupBuyEntity) {
		this.groupBuyEntity = groupBuyEntity;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public OrderEntity getOrderEntity() {
		return orderEntity;
	}

	public void setOrderEntity(OrderEntity orderEntity) {
		this.orderEntity = orderEntity;
	}

	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	public boolean isOwner() {
		return isOwner;
	}

	public void setOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_UUID")
	private OrderEntity orderEntity;

	@Column(name = "JOIN_TIME")
	private Date joinTime;
	
	@Column(name = "IS_OWNER")
	private boolean isOwner;
	
}