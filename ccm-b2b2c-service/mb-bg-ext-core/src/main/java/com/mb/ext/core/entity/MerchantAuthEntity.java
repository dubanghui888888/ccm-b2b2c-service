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
 * The persistent class for the merchant_auth database table.
 * 
 */
@Entity
@Table(name = "merchant_auth")
@NamedQuery(name = "MerchantAuthEntity.findAll", query = "SELECT u FROM MerchantAuthEntity u")
public class MerchantAuthEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "MERCHANTAUTH_UUID")
	@GenericGenerator(name = "MERCHANTAUTH_UUID", strategy = "uuid")
	@Column(name = "MERCHANTAUTH_UUID", nullable = false, length = 36)
	private String merchantAuthUuid;

	public String getTranPassword() {
		return tranPassword;
	}

	public void setTranPassword(String tranPassword) {
		this.tranPassword = tranPassword;
	}

	@Column(name = "PASSWORD", nullable = false, length = 100)
	private String password;
	
	@Column(name = "TRAN_PASSWORD")
	private String tranPassword;
	
	@Column(name = "LASTLOGINTIME")
	private Date lastLoginTime;
	
	@Column(name = "FAILCOUNT")
	private int failCount;
	
	public String getMerchantAuthUuid() {
		return merchantAuthUuid;
	}

	public void setMerchantAuthUuid(String merchantAuthUuid) {
		this.merchantAuthUuid = merchantAuthUuid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;


	
}