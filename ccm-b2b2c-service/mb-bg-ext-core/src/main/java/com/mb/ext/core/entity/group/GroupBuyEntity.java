package com.mb.ext.core.entity.group;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.product.ProductGroupEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * 团购单表
 * 
 */
@Entity
@Table(name = "group_buy")
@NamedQuery(name = "GroupBuyEntity.findAll", query = "SELECT u FROM GroupBuyEntity u")
public class GroupBuyEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 2233062138920658290L;

	@Id
	@GeneratedValue(generator = "GROUP_BUY_UUID")
	@GenericGenerator(name = "GROUP_BUY_UUID", strategy = "uuid")
	@Column(name = "GROUP_BUY_UUID", nullable = false, length = 36)
	private String groupBuyUuid;

	public String getGroupBuyUuid() {
		return groupBuyUuid;
	}

	public void setGroupBuyUuid(String groupBuyUuid) {
		this.groupBuyUuid = groupBuyUuid;
	}

	public GroupBuyProductEntity getGroupBuyProductEntity() {
		return groupBuyProductEntity;
	}

	public void setGroupBuyProductEntity(GroupBuyProductEntity groupBuyProductEntity) {
		this.groupBuyProductEntity = groupBuyProductEntity;
	}

	public UserEntity getOwnerEntity() {
		return ownerEntity;
	}

	public void setOwnerEntity(UserEntity ownerEntity) {
		this.ownerEntity = ownerEntity;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_BUY_PRODUCT_UUID")
	private GroupBuyProductEntity groupBuyProductEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER_UUID")
	private UserEntity ownerEntity;

	
	public List<GroupBuyUserEntity> getGroupBuyUserList() {
		return groupBuyUserList;
	}

	public void setGroupBuyUserList(List<GroupBuyUserEntity> groupBuyUserList) {
		this.groupBuyUserList = groupBuyUserList;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "groupBuyEntity")
	private List<GroupBuyUserEntity> groupBuyUserList;

	@Column(name = "START_TIME")
	private Date startTime;
	
	@Column(name = "END_TIME")
	private Date endTime;
	
	@Column(name = "STATUS")
	private String status;
	
}