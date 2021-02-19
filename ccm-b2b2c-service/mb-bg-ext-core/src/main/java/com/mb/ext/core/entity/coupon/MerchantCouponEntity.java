package com.mb.ext.core.entity.coupon;

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

@Entity
@Table(name = "merchant_coupon")
@NamedQuery(name = "MerchantCouponEntity.findAll", query = "SELECT u FROM MerchantCouponEntity u")
public class MerchantCouponEntity extends AbstractBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "MERCHANTCOUPON_UUID")
	@GenericGenerator(name = "MERCHANTCOUPON_UUID", strategy = "uuid")
	@Column(name = "MERCHANTCOUPON_UUID", nullable = false, length = 36)
	private String merchantCouponUuid;
	
	//
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUPON_UUID")
	private CouponEntity couponEntity;
		
	//
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;

	public String getMerchantCouponUuid() {
		return merchantCouponUuid;
	}

	public void setMerchantCouponUuid(String merchantCouponUuid) {
		this.merchantCouponUuid = merchantCouponUuid;
	}

	public CouponEntity getCouponEntity() {
		return couponEntity;
	}

	public void setCouponEntity(CouponEntity couponEntity) {
		this.couponEntity = couponEntity;
	}

	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

}
