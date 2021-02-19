package com.mb.ext.core.entity;

import java.util.Date;

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

import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the partner database table.
 * 
 */
@Entity
@Table(name = "partner")
@NamedQuery(name = "PartnerEntity.findAll", query = "SELECT u FROM PartnerEntity u")
public class PartnerEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PARTNER_UUID")
	@GenericGenerator(name = "PARTNER_UUID", strategy = "uuid")
	@Column(name = "PARTNER_UUID", nullable = false, length = 36)
	private String partnerUuid;

	
	@Column(name = "PARTNER_LEVEL")
	private String partnerLevel;
	
	@Column(name = "EFFECTIVE_DATE")
	private Date effectiveDate;

	public String getPartnerUuid() {
		return partnerUuid;
	}

	public void setPartnerUuid(String partnerUuid) {
		this.partnerUuid = partnerUuid;
	}

	public String getPartnerLevel() {
		return partnerLevel;
	}

	public void setPartnerLevel(String partnerLevel) {
		this.partnerLevel = partnerLevel;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;
	
}