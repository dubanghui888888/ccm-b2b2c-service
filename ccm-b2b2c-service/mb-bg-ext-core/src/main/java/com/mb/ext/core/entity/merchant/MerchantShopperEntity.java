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

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the merchant_shopper database table.
 * 
 */
@Entity
@Table(name = "merchant_shopper")
@NamedQuery(name = "MerchantShopperEntity.findAll", query = "SELECT u FROM MerchantShopperEntity u")
public class MerchantShopperEntity extends AbstractBaseEntity
{
	
	private static final long serialVersionUID = 1L;

	public String getMerchantUserUuid() {
		return merchantShopperUuid;
	}

	public void setMerchantUserUuid(String merchantShopperUuid) {
		this.merchantShopperUuid = merchantShopperUuid;
	}

	@Id
	@GeneratedValue(generator = "MERCHANTSHOPPER_UUID")
	@GenericGenerator(name = "MERCHANTSHOPPER_UUID", strategy = "uuid")
	@Column(name = "MERCHANTSHOPPER_UUID", nullable = false, length = 36)
	private String merchantShopperUuid;

	public String getMerchantShopperUuid() {
		return merchantShopperUuid;
	}

	public void setMerchantShopperUuid(String merchantShopperUuid) {
		this.merchantShopperUuid = merchantShopperUuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
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
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "SEX")
	private String sex;
	
	@Column(name = "MOBILENO")
	private String mobileNo;
	
	@Column(name = "PHOTO")
	private String photo;
	
	@Column(name = "IS_ENABLED")
	private boolean isEnabled;
	
}