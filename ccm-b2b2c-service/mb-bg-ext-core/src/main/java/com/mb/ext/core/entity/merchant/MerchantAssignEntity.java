package com.mb.ext.core.entity.merchant;

import java.math.BigDecimal;
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
 * The persistent class for the merchant_assign database table.
 * 
 */
@Entity
@Table(name = "merchant_assign")
@NamedQuery(name = "MerchantAssignEntity.findAll", query = "SELECT u FROM MerchantAssignEntity u")
public class MerchantAssignEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "MERCHANTASSIGN_UUID")
	@GenericGenerator(name = "MERCHANTASSIGN_UUID", strategy = "uuid")
	@Column(name = "MERCHANTASSIGN_UUID", nullable = false, length = 36)
	private String merchantAssignUuid;

	
	public String getMerchantAssignUuid() {
		return merchantAssignUuid;
	}

	public int getMerchantPointBefore() {
		return merchantPointBefore;
	}

	public void setMerchantPointBefore(int merchantPointBefore) {
		this.merchantPointBefore = merchantPointBefore;
	}

	public int getMerchantPointAfter() {
		return merchantPointAfter;
	}

	public void setMerchantPointAfter(int merchantPointAfter) {
		this.merchantPointAfter = merchantPointAfter;
	}

	public int getUserPointBefore() {
		return userPointBefore;
	}

	public void setUserPointBefore(int userPointBefore) {
		this.userPointBefore = userPointBefore;
	}

	public int getUserPointAfter() {
		return userPointAfter;
	}

	public void setUserPointAfter(int userPointAfter) {
		this.userPointAfter = userPointAfter;
	}

	public void setMerchantAssignUuid(String merchantAssignUuid) {
		this.merchantAssignUuid = merchantAssignUuid;
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
	
	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public Date getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}

	public BigDecimal getTranAmount() {
		return tranAmount;
	}

	public void setTranAmount(BigDecimal tranAmount) {
		this.tranAmount = tranAmount;
	}

	public String getAssignNo() {
		return assignNo;
	}

	public void setAssignNo(String assignNo) {
		this.assignNo = assignNo;
	}

	public int getAssignPoint() {
		return assignPoint;
	}

	public void setAssignPoint(int assignPoint) {
		this.assignPoint = assignPoint;
	}


	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;
	
	@Column(name = "ASSIGN_TIME")
	private Date assignTime;
	
	@Column(name = "TRAN_AMOUNT")
	private BigDecimal tranAmount;
	
	@Column(name = "ASSIGN_NO")
	private String assignNo;

	@Column(name = "ASSIGN_POINT")
	private int assignPoint;
	
	//商家划拨前积分
	@Column(name = "MERCHANT_POINT_BEFORE")
	private int merchantPointBefore;
	
	//商家充值后积分
	@Column(name = "MERCHANT_POINT_AFTER")
	private int merchantPointAfter;
	
	//会员划拨前积分
	@Column(name = "USER_POINT_BEFORE")
	private int userPointBefore;
	
	//会员充值后积分
	@Column(name = "USER_POINT_AFTER")
	private int userPointAfter;
	
}