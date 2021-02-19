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
import org.hibernate.annotations.Where;

import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * 秒杀商品表
 * 
 */
@Entity
@Table(name = "group_buy_def")
@NamedQuery(name = "GroupBuyDefEntity.findAll", query = "SELECT u FROM GroupBuyDefEntity u")
public class GroupBuyDefEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 2233062138920658290L;

	@Id
	@GeneratedValue(generator = "GROUP_BUY_DEF_UUID")
	@GenericGenerator(name = "GROUP_BUY_DEF_UUID", strategy = "uuid")
	@Column(name = "GROUP_BUY_DEF_UUID", nullable = false, length = 36)
	private String groupBuyDefUuid;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGroupBuyDefUuid() {
		return groupBuyDefUuid;
	}

	public void setGroupBuyDefUuid(String groupBuyDefUuid) {
		this.groupBuyDefUuid = groupBuyDefUuid;
	}

	public int getMinUserCount() {
		return minUserCount;
	}

	public void setMinUserCount(int minUserCount) {
		this.minUserCount = minUserCount;
	}

	public int getMaxTranDays() {
		return maxTranDays;
	}

	public void setMaxTranDays(int maxTranDays) {
		this.maxTranDays = maxTranDays;
	}

	@Column(name = "STATUS")
	private String status;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "NAME")
	private String name;
	
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

	public List<GroupBuyProductEntity> getGroupBuyProductList() {
		return groupBuyProductList;
	}

	public void setGroupBuyProductList(List<GroupBuyProductEntity> groupBuyProductList) {
		this.groupBuyProductList = groupBuyProductList;
	}

	@Column(name = "MIN_USER_COUNT")
	private int minUserCount;
	
	@Column(name = "MAX_TRAN_DAYS")
	private int maxTranDays;
	
	@Column(name = "START_TIME")
	private Date startTime;
	
	@Column(name = "END_TIME")
	private Date endTime;
	
	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "groupBuyDefEntity")
	@Where(clause="IS_DELETED = 0")
	private List<GroupBuyProductEntity> groupBuyProductList;
	
}