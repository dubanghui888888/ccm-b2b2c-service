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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.ext.core.entity.UserEntity;
import com.mb.framework.entity.AbstractBaseEntity;

@Entity
@Table(name = "coupon_order")
@NamedQuery(name = "CouponOrderEntity.findAll", query = "SELECT u FROM CouponOrderEntity u")
public class CouponOrderEntity extends AbstractBaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "COUPONORDER_UUID")
	@GenericGenerator(name = "COUPONORDER_UUID", strategy = "uuid")
	@Column(name = "COUPONORDER_UUID", nullable = false, length = 36)
	private String couponOrderUuid;
	
	//用户
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;
		
	//券
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUPON_UUID")
	private CouponEntity couponEntity;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "COUPON_ORDER_TIME")
	private Date couponOrderTime;
	
	@Column(name = "COUPON_UNIT")
	private int couponUnit;
	
	public int getCouponUnit() {
		return couponUnit;
	}

	public void setCouponUnit(int couponUnit) {
		this.couponUnit = couponUnit;
	}

	public int getCouponPoint() {
		return couponPoint;
	}

	public void setCouponPoint(int couponPoint) {
		this.couponPoint = couponPoint;
	}

	public int getActualPoint() {
		return actualPoint;
	}

	public void setActualPoint(int actualPoint) {
		this.actualPoint = actualPoint;
	}

	public String getCouponImageUrl() {
		return couponImageUrl;
	}

	public void setCouponImageUrl(String couponImageUrl) {
		this.couponImageUrl = couponImageUrl;
	}

	@Column(name = "COUPON_POINT")
	private int couponPoint;
	
	@Column(name = "ACTUAL_POINT")
	private int actualPoint;
	
	@Column(name = "COUPON_IMAGE_URL")
	private String couponImageUrl;
	
	public String getCouponOrderUuid() {
		return couponOrderUuid;
	}

	public void setCouponOrderUuid(String couponOrderUuid) {
		this.couponOrderUuid = couponOrderUuid;
	}
	

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public CouponEntity getCouponEntity() {
		return couponEntity;
	}

	public void setCouponEntity(CouponEntity couponEntity) {
		this.couponEntity = couponEntity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCouponOrderTime() {
		return couponOrderTime;
	}

	public void setCouponOrderTime(Date couponOrderTime) {
		this.couponOrderTime = couponOrderTime;
	}

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

	public String getCouponDesc() {
		return couponDesc;
	}

	public void setCouponDesc(String couponDesc) {
		this.couponDesc = couponDesc;
	}

	@Column(name = "VALID_START_DATE")
	private Date startDate;
	
	@Column(name = "VALID_END_DATE")
	private Date endDate;
	
	@Column(name = "COUPON_DESC")
	private String couponDesc;
	
	@Column(name = "COUPON_NAME")
	private String couponName;

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

}
