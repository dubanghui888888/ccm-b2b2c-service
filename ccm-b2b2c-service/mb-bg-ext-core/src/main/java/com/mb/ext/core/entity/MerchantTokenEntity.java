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
 * The persistent class for the merchant_token database table.
 * 
 */
@Entity
@Table(name = "merchant_token")
@NamedQuery(name = "MerchantTokenEntity.findAll", query = "SELECT u FROM MerchantTokenEntity u")
public class MerchantTokenEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "MERCHANTTOKEN_UUID")
	@GenericGenerator(name = "MERCHANTTOKEN_UUID", strategy = "uuid")
	@Column(name = "MERCHANTTOKEN_UUID", nullable = false, length = 36)
	private String merchantTokenUuid;

	@Column(name = "TOKENID", nullable = false, length = 36)
	private String tokenId;
	
	@Column(name = "LOGINTIME")
	private Date loginTime;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;

	public String getMerchantTokenUuid() {
		return merchantTokenUuid;
	}

	public void setMerchantTokenUuid(String merchantTokenUuid) {
		this.merchantTokenUuid = merchantTokenUuid;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
}