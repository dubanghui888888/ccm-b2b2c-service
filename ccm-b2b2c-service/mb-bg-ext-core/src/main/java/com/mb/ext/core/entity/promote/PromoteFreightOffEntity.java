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
 * The persistent class for the promote_freight_off database table.
 * 
 */
@Entity
@Table(name = "promote_freight_off")
@NamedQuery(name = "PromoteFreightOffEntity.findAll", query = "SELECT u FROM PromoteFreightOffEntity u")
public class PromoteFreightOffEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 1L;



	@Id
	@GeneratedValue(generator = "PROMOTEFREIGHTOFF_UUID")
	@GenericGenerator(name = "PROMOTEFREIGHTOFF_UUID", strategy = "uuid")
	@Column(name = "PROMOTEFREIGHTOFF_UUID", nullable = false, length = 36)
	private String promoteFreightOffUuid;

	public String getPromoteFreightOffUuid() {
		return promoteFreightOffUuid;
	}

	public void setPromoteFreightOffUuid(String promoteFreightOffUuid) {
		this.promoteFreightOffUuid = promoteFreightOffUuid;
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
	
	@Column(name = "CONDITION_AMOUNT")
	private BigDecimal conditionAmount;
	

	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;

}