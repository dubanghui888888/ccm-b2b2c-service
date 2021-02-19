package com.mb.ext.core.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the user_level database table.
 * 
 */
@Entity
@Table(name = "user_level")
@NamedQuery(name = "UserLevelEntity.findAll", query = "SELECT u FROM UserLevelEntity u")
public class UserLevelEntity extends AbstractBaseEntity
{

	public String getUserLevelUuid() {
		return userLevelUuid;
	}


	public void setUserLevelUuid(String userLevelUuid) {
		this.userLevelUuid = userLevelUuid;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "USER_LEVEL_UUID")
	@GenericGenerator(name = "USER_LEVEL_UUID", strategy = "uuid")
	@Column(name = "USER_LEVEL_UUID", nullable = false, length = 36)
	private String userLevelUuid;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "PRODUCT_DISCOUNT")
	private BigDecimal productDiscount;
	
	public BigDecimal getProductDiscount() {
		return productDiscount;
	}


	public void setProductDiscount(BigDecimal productDiscount) {
		this.productDiscount = productDiscount;
	}


	@Column(name = "IS_RECRUIT_PROFIT_ENABLED")
	private boolean isRecruitProfitEnabled;
	
	@Column(name = "IS_SALE_PROFIT_ENABLED")
	private boolean isSaleProfitEnabled;
	
	@Column(name = "REQUIRED_USER_COUNT_DIRECT")
	private int requiredUserCountDirect;
	
	@Column(name = "REQUIRED_USER_COUNT_TEAM")
	private int requiredUserCountTeam;

	public boolean isRecruitProfitEnabled() {
		return isRecruitProfitEnabled;
	}


	public void setRecruitProfitEnabled(boolean isRecruitProfitEnabled) {
		this.isRecruitProfitEnabled = isRecruitProfitEnabled;
	}


	public boolean isSaleProfitEnabled() {
		return isSaleProfitEnabled;
	}


	public void setSaleProfitEnabled(boolean isSaleProfitEnabled) {
		this.isSaleProfitEnabled = isSaleProfitEnabled;
	}


	public int getRequiredUserCountDirect() {
		return requiredUserCountDirect;
	}


	public void setRequiredUserCountDirect(int requiredUserCountDirect) {
		this.requiredUserCountDirect = requiredUserCountDirect;
	}


	public int getRequiredUserCountTeam() {
		return requiredUserCountTeam;
	}


	public void setRequiredUserCountTeam(int requiredUserCountTeam) {
		this.requiredUserCountTeam = requiredUserCountTeam;
	}


	public BigDecimal getRequiredProductAmountDirect() {
		return requiredProductAmountDirect;
	}


	public void setRequiredProductAmountDirect(BigDecimal requiredProductAmountDirect) {
		this.requiredProductAmountDirect = requiredProductAmountDirect;
	}


	public BigDecimal getRequiredProductAmountTeam() {
		return requiredProductAmountTeam;
	}


	public void setRequiredProductAmountTeam(BigDecimal requiredProductAmountTeam) {
		this.requiredProductAmountTeam = requiredProductAmountTeam;
	}


	public String getMemo() {
		return memo;
	}


	public void setMemo(String memo) {
		this.memo = memo;
	}


	@Column(name = "REQUIRED_PRODUCT_AMOUNT_DIRECT")
	private BigDecimal requiredProductAmountDirect;
	
	@Column(name = "REQUIRED_PRODUCT_AMOUNT_TEAM")
	private BigDecimal requiredProductAmountTeam;
	
	@Column(name = "IS_REQUIRED_BY_USER")
	private boolean isRequiredByUser;
	
	@Column(name = "REQUIRED_BY_USER_SYMBOL")
	private String requiredByUserSymbol;
	
	public boolean isRequiredByUser() {
		return isRequiredByUser;
	}


	public void setRequiredByUser(boolean isRequiredByUser) {
		this.isRequiredByUser = isRequiredByUser;
	}


	public String getRequiredByUserSymbol() {
		return requiredByUserSymbol;
	}


	public void setRequiredByUserSymbol(String requiredByUserSymbol) {
		this.requiredByUserSymbol = requiredByUserSymbol;
	}


	public boolean isRequiredByAmount() {
		return isRequiredByAmount;
	}


	public void setRequiredByAmount(boolean isRequiredByAmount) {
		this.isRequiredByAmount = isRequiredByAmount;
	}


	public String getRequiredByAmountSymbol() {
		return requiredByAmountSymbol;
	}


	public void setRequiredByAmountSymbol(String requiredByAmountSymbol) {
		this.requiredByAmountSymbol = requiredByAmountSymbol;
	}


	@Column(name = "IS_REQUIRED_BY_AMOUNT")
	private boolean isRequiredByAmount;
	
	@Column(name = "REQUIRED_BY_AMOUNT_SYMBOL")
	private String requiredByAmountSymbol;
	
	@Column(name = "IS_DEFAULT")
	private boolean isDefault;
	
	@Column(name = "DEPTH")
	private int depth;

	public int getDepth() {
		return depth;
	}


	public void setDepth(int depth) {
		this.depth = depth;
	}


	public boolean isDefault() {
		return isDefault;
	}


	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Column(name = "MEMO")
	private String memo;

}