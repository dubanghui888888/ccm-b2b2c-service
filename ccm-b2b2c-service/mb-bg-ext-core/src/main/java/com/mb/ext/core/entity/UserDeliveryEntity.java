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
 * The persistent class for the user_delivery database table.
 * 
 */
@Entity
@Table(name = "user_delivery")
@NamedQuery(name = "UserDeliveryEntity.findAll", query = "SELECT u FROM UserDeliveryEntity u")
public class UserDeliveryEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "USERDELIVERY_UUID")
	@GenericGenerator(name = "USERDELIVERY_UUID", strategy = "uuid")
	@Column(name = "USERDELIVERY_UUID", nullable = false, length = 36)
	private String userDeliveryUuid;

	
	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Date getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public int getDeliveryQuantity() {
		return deliveryQuantity;
	}

	public void setDeliveryQuantity(int deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}

	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public String getCourierNo() {
		return courierNo;
	}

	public void setCourierNo(String courierNo) {
		this.courierNo = courierNo;
	}

	@Column(name = "DELIVERY_STATUS")
	private String deliveryStatus;
	
	@Column(name = "DELIVERY_ADDRESS")
	private String deliveryAddress;
	
	public String getDeliveryPostCode() {
		return deliveryPostCode;
	}

	public void setDeliveryPostCode(String deliveryPostCode) {
		this.deliveryPostCode = deliveryPostCode;
	}

	@Column(name = "DELIVERY_POSTCODE")
	private String deliveryPostCode;
	
	@Column(name = "DELIVERY_TYPE")
	private String deliveryType;
	
	@Column(name = "APPLICATION_TIME")
	private Date applicationTime;
	
	@Column(name = "DELIVERY_TIME")
	private Date deliveryTime;
	
	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getDeliveryContactNo() {
		return deliveryContactNo;
	}

	public void setDeliveryContactNo(String deliveryContactNo) {
		this.deliveryContactNo = deliveryContactNo;
	}

	@Column(name = "DELIVERY_QUANTITY")
	private int deliveryQuantity;
	
	@Column(name = "DELIVERY_NAME")
	private String deliveryName;
	
	@Column(name = "DELIVERY_CONTACTNO")
	private String deliveryContactNo;
	
	@Column(name = "COURIER_NAME")
	private String courierName;
	
	@Column(name = "COURIER_NO")
	private String courierNo;
	
	@Column(name = "MEMO")
	private String memo;
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getUserDeliveryUuid() {
		return userDeliveryUuid;
	}

	public void setUserDeliveryUuid(String userDeliveryUuid) {
		this.userDeliveryUuid = userDeliveryUuid;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;
	
}