package com.mb.ext.core.entity.coupon;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.framework.entity.AbstractBaseEntity;

@Entity
@Table(name = "couponwrite_off")
@NamedQuery(name = "CouponWriteOffEntity.findAll", query = "SELECT u FROM CouponWriteOffEntity u")
public class CouponWriteOffEntity extends AbstractBaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "COUPONWRITEOFF_UUID")
	@GenericGenerator(name = "COUPONWRITEOFF_UUID", strategy = "uuid")
	@Column(name = "COUPONWRITEOFF_UUID", nullable = false, length = 36)
	private String couponWriteOffUuid;
	
	//
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERCOUPON_UUID")
	private UserCouponEntity userCouponEntity;
		
	//
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;
	
	@Column(name = "COUPON_WRITE_OFF_TIME")
	private Date couponWriteOffTime;

	public String getCouponWriteOffUuid() {
		return couponWriteOffUuid;
	}

	public void setCouponWriteOffUuid(String couponWriteOffUuid) {
		this.couponWriteOffUuid = couponWriteOffUuid;
	}

	public UserCouponEntity getUserCouponEntity() {
		return userCouponEntity;
	}

	public void setUserCouponEntity(UserCouponEntity userCouponEntity) {
		this.userCouponEntity = userCouponEntity;
	}

	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	public Date getCouponWriteOffTime() {
		return couponWriteOffTime;
	}

	public void setCouponWriteOffTime(Date couponWriteOffTime) {
		this.couponWriteOffTime = couponWriteOffTime;
	}

}
