package com.mb.ext.core.entity.profit;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the profit_distribution database table.
 * 
 */
@Entity
@Table(name = "profit_distribution")
@NamedQuery(name = "ProfitDistributionEntity.findAll", query = "SELECT u FROM ProfitDistributionEntity u")
public class ProfitDistributionEntity extends AbstractBaseEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8891887617767255623L;

	public String getProfitDistributionUuid() {
		return profitDistributionUuid;
	}

	public void setProfitDistributionUuid(String profitDistributionUuid) {
		this.profitDistributionUuid = profitDistributionUuid;
	}


	@Id
	@GeneratedValue(generator = "PROFIT_DISTRIBUTION_UUID")
	@GenericGenerator(name = "PROFIT_DISTRIBUTION_UUID", strategy = "uuid")
	@Column(name = "PROFIT_DISTRIBUTION_UUID", nullable = false, length = 36)
	private String profitDistributionUuid;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "IS_DISTRIBUTION_ENABLED")
	private boolean isDistributionEnabled;
	
	@Column(name = "IS_DISTRIBUTOR_PURCHASE_ENABLED")
	private boolean isDistributorPurchaseEnabled;
	
	@Column(name = "DISTRIBUTION_LEVEL")
	private int distributionLevel;
	
	@Column(name = "LEVEL1_RATE")
	private BigDecimal level1Rate;
	
	@Column(name = "LEVEL2_RATE")
	private BigDecimal level2Rate;
	
	@Column(name = "LEVEL3_RATE")
	private BigDecimal level3Rate;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDistributionEnabled() {
		return isDistributionEnabled;
	}

	public void setDistributionEnabled(boolean isDistributionEnabled) {
		this.isDistributionEnabled = isDistributionEnabled;
	}

	public boolean isDistributorPurchaseEnabled() {
		return isDistributorPurchaseEnabled;
	}

	public void setDistributorPurchaseEnabled(boolean isDistributorPurchaseEnabled) {
		this.isDistributorPurchaseEnabled = isDistributorPurchaseEnabled;
	}

	public int getDistributionLevel() {
		return distributionLevel;
	}

	public void setDistributionLevel(int distributionLevel) {
		this.distributionLevel = distributionLevel;
	}

	public BigDecimal getLevel1Rate() {
		return level1Rate;
	}

	public void setLevel1Rate(BigDecimal level1Rate) {
		this.level1Rate = level1Rate;
	}

	public BigDecimal getLevel2Rate() {
		return level2Rate;
	}

	public void setLevel2Rate(BigDecimal level2Rate) {
		this.level2Rate = level2Rate;
	}

	public BigDecimal getLevel3Rate() {
		return level3Rate;
	}

	public void setLevel3Rate(BigDecimal level3Rate) {
		this.level3Rate = level3Rate;
	}

	public String getApplicationContent() {
		return applicationContent;
	}

	public void setApplicationContent(String applicationContent) {
		this.applicationContent = applicationContent;
	}

	public boolean isShareRequired() {
		return isShareRequired;
	}

	public void setShareRequired(boolean isShareRequired) {
		this.isShareRequired = isShareRequired;
	}

	public boolean isFormRequired() {
		return isFormRequired;
	}

	public void setFormRequired(boolean isFormRequired) {
		this.isFormRequired = isFormRequired;
	}

	public boolean isApproveRequired() {
		return isApproveRequired;
	}

	public void setApproveRequired(boolean isApproveRequired) {
		this.isApproveRequired = isApproveRequired;
	}


	@Column(name = "APPLICATION_CONTENT")
	private String applicationContent;
	
	@Column(name = "IS_SHARE_REQUIRED")
	private boolean isShareRequired;
	
	@Column(name = "IS_FORM_REQUIRED")
	private boolean isFormRequired;
	
	@Column(name = "IS_APPROVE_REQUIRED")
	private boolean isApproveRequired;
	
}