
package com.mb.ext.core.service.spec.profit;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfitDistributionDTO extends AbstractBaseDTO
{

	public String getProfitDistributionUuid() {
		return profitDistributionUuid;
	}

	public void setProfitDistributionUuid(String profitDistributionUuid) {
		this.profitDistributionUuid = profitDistributionUuid;
	}

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

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String profitDistributionUuid;

	private String name;
	
	private boolean isDistributionEnabled;
	
	private boolean isDistributorPurchaseEnabled;
	
	private int distributionLevel;
	
	private BigDecimal level1Rate;
	
	private BigDecimal level2Rate;
	
	private BigDecimal level3Rate;
	
	private String applicationContent;
	
	private boolean isShareRequired;
	
	private boolean isFormRequired;
	
	private boolean isApproveRequired;
}
