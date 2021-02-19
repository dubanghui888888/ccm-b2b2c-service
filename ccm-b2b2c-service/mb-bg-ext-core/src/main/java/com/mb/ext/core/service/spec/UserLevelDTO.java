
package com.mb.ext.core.service.spec;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLevelDTO extends AbstractBaseDTO
{

	private static final long serialVersionUID = -6601947950691842897L;

	/**
	 * serialVersionUID
	 */
	private String userLevelUuid;
	
	private String name;
	
	private BigDecimal productDiscount;
	
	public String getUserLevelUuid() {
		return userLevelUuid;
	}

	public BigDecimal getProductDiscount() {
		return productDiscount;
	}

	public void setProductDiscount(BigDecimal productDiscount) {
		this.productDiscount = productDiscount;
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

	private boolean isRecruitProfitEnabled;
	
	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	private boolean isSaleProfitEnabled;
	
	private boolean isDefault;
	
	private int requiredUserCountDirect;
	
	private int requiredUserCountTeam;
	
	private BigDecimal requiredProductAmountDirect;
	
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

	private BigDecimal requiredProductAmountTeam;
	
	private boolean isRequiredByUser;
	
	private String requiredByUserSymbol;
	
	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	private boolean isRequiredByAmount;
	
	private String requiredByAmountSymbol;
	
	private String memo;
	
	private int depth;
}
