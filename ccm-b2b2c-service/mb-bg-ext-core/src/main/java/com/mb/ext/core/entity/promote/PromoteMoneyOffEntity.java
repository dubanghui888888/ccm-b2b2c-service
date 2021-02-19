package com.mb.ext.core.entity.promote;

import java.math.BigDecimal;
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

import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the promote_money_off database table.
 * 
 */
@Entity
@Table(name = "promote_money_off")
@NamedQuery(name = "PromoteMoneyOffEntity.findAll", query = "SELECT u FROM PromoteMoneyOffEntity u")
public class PromoteMoneyOffEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 1L;



	@Id
	@GeneratedValue(generator = "PROMOTEMONEYOFF_UUID")
	@GenericGenerator(name = "PROMOTEMONEYOFF_UUID", strategy = "uuid")
	@Column(name = "PROMOTEMONEYOFF_UUID", nullable = false, length = 36)
	private String promoteMoneyOffUuid;

	public String getPromoteMoneyOffUuid() {
		return promoteMoneyOffUuid;
	}

	public void setPromoteMoneyOffUuid(String promoteMoneyOffUuid) {
		this.promoteMoneyOffUuid = promoteMoneyOffUuid;
	}


	public boolean isActive() {
		return isActive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getValidStartDate() {
		return validStartDate;
	}

	public void setValidStartDate(Date validStartDate) {
		this.validStartDate = validStartDate;
	}

	public Date getValidEndDate() {
		return validEndDate;
	}

	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}

	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	public BigDecimal getBenefitCash() {
		return benefitCash;
	}

	public void setBenefitCash(BigDecimal benefitCash) {
		this.benefitCash = benefitCash;
	}

	public BigDecimal getConditionAmount() {
		return conditionAmount;
	}

	public void setConditionAmount(BigDecimal conditionAmount) {
		this.conditionAmount = conditionAmount;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	@Column(name = "ISACTIVE")
	private boolean isActive;
	
	@Column(name = "NAME")
	private String name;

	@Column(name = "VALID_START_DATE")
	private Date validStartDate;
	
	@Column(name = "VALID_END_DATE")
	private Date validEndDate;
	
	@Column(name = "BENEFIT_CASH")
	private BigDecimal benefitCash;
	
	@Column(name = "CONDITION_AMOUNT")
	private BigDecimal conditionAmount;
	
	@Column(name = "BENEFIT_CASH_L2")
	private BigDecimal benefitCashL2;
	
	@Column(name = "CONDITION_AMOUNT_L2")
	private BigDecimal conditionAmountL2;
	
	public BigDecimal getBenefitCashL2() {
		return benefitCashL2;
	}

	public void setBenefitCashL2(BigDecimal benefitCashL2) {
		this.benefitCashL2 = benefitCashL2;
	}

	public BigDecimal getConditionAmountL2() {
		return conditionAmountL2;
	}

	public void setConditionAmountL2(BigDecimal conditionAmountL2) {
		this.conditionAmountL2 = conditionAmountL2;
	}

	public BigDecimal getBenefitCashL3() {
		return benefitCashL3;
	}

	public void setBenefitCashL3(BigDecimal benefitCashL3) {
		this.benefitCashL3 = benefitCashL3;
	}

	public BigDecimal getConditionAmountL3() {
		return conditionAmountL3;
	}

	public void setConditionAmountL3(BigDecimal conditionAmountL3) {
		this.conditionAmountL3 = conditionAmountL3;
	}

	@Column(name = "BENEFIT_CASH_L3")
	private BigDecimal benefitCashL3;
	
	@Column(name = "CONDITION_AMOUNT_L3")
	private BigDecimal conditionAmountL3;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;

}