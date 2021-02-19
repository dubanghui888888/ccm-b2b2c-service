package com.mb.ext.core.entity.merchant;

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

import com.mb.ext.core.entity.UserEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the merchant_follow database table.
 * 
 */
@Entity
@Table(name = "merchant_follow")
@NamedQuery(name = "MerchantFollowEntity.findAll", query = "SELECT u FROM MerchantFollowEntity u")
public class MerchantFollowEntity extends AbstractBaseEntity
{
	
	private static final long serialVersionUID = 1L;

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}


	@Id
	@GeneratedValue(generator = "MERCHANTFOLLOW_UUID")
	@GenericGenerator(name = "MERCHANTFOLLOW_UUID", strategy = "uuid")
	@Column(name = "MERCHANTFOLLOW_UUID", nullable = false, length = 36)
	private String merchantFollowUuid;

	public String getMerchantFollowUuid() {
		return merchantFollowUuid;
	}

	public void setMerchantFollowUuid(String merchantFollowUuid) {
		this.merchantFollowUuid = merchantFollowUuid;
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
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;
	
}