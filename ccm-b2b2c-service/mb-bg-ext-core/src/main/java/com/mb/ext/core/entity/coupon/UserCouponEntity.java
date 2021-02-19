package com.mb.ext.core.entity.coupon;

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

import com.mb.ext.core.entity.UserEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the user_coupon database table.
 * 
 */
@Entity
@Table(name = "user_coupon")
@NamedQuery(name = "UserCouponEntity.findAll", query = "SELECT u FROM UserCouponEntity u")
public class UserCouponEntity extends AbstractBaseEntity
{
	public String getUserCouponUuid() {
		return userCouponUuid;
	}

	public void setUserCouponUuid(String userCouponUuid) {
		this.userCouponUuid = userCouponUuid;
	}

	public String getReceiveChannel() {
		return receiveChannel;
	}

	public void setReceiveChannel(String receiveChannel) {
		this.receiveChannel = receiveChannel;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public String getUseChannel() {
		return useChannel;
	}

	public void setUseChannel(String useChannel) {
		this.useChannel = useChannel;
	}

	public CouponEntity getCouponEntity() {
		return couponEntity;
	}

	public void setCouponEntity(CouponEntity couponEntity) {
		this.couponEntity = couponEntity;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "USERCOUPON_UUID")
	@GenericGenerator(name = "USERCOUPON_UUID", strategy = "uuid")
	@Column(name = "USERCOUPON_UUID", nullable = false, length = 36)
	private String userCouponUuid;

	@Column(name = "RECEIVE_CHANNEL")
	private String receiveChannel;
	
	@Column(name = "RECEIVE_TIME")
	private Date receiveTime;
	
	@Column(name = "VALID_START_DATE")
	private Date startDate;
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "VALID_END_DATE")
	private Date endDate;
	
	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	@Column(name = "IS_USED")
	private boolean isUsed;
	
	@Column(name = "USE_TIME")
	private Date useTime;
	
	@Column(name = "USE_CHANNEL")
	private String useChannel;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUPON_UUID")
	private CouponEntity couponEntity;


	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	
}