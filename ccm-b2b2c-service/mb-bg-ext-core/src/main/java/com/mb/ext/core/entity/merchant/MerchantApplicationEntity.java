package com.mb.ext.core.entity.merchant;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the merchant_application database table.
 * 
 */
@Entity
@Table(name = "merchant_application")
@NamedQuery(name = "MerchantApplicationEntity.findAll", query = "SELECT u FROM MerchantApplicationEntity u")
public class MerchantApplicationEntity extends AbstractBaseEntity
{



	private static final long serialVersionUID = 1L;

	public String getMerchantAddress() {
		return merchantAddress;
	}

	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Id
	@GeneratedValue(generator = "MERCHANT_APPLICATION_UUID")
	@GenericGenerator(name = "MERCHANT_APPLICATION_UUID", strategy = "uuid")
	@Column(name = "MERCHANT_APPLICATION_UUID", nullable = false, length = 36)
	private String merchantApplicationUuid;

	@Column(name = "MOBILENO", length = 20)
	private String mobileNo;
	
	public String getMerchantApplicationUuid() {
		return merchantApplicationUuid;
	}

	public void setMerchantApplicationUuid(String merchantApplicationUuid) {
		this.merchantApplicationUuid = merchantApplicationUuid;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Column(name = "APPLICATION_ID",length = 45)
	private String applicationId;
	
	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public Date getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}

	public Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	@Column(name = "APPLICATION_STATUS",length = 5)
	private String applicationStatus;
	
	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	@Column(name = "MERCHANTNAME",length = 100)
	private String merchantName;

	@Column(name = "MERCHANTDESCRIPTION",length = 200)
	private String merchantDescription;

	public String getMerchantDescription() {
		return merchantDescription;
	}

	public void setMerchantDescription(String merchantDescription) {
		this.merchantDescription = merchantDescription;
	}

	@Column(name = "MERCHANTADDRESS",length = 45)
	private String merchantAddress;
	
	@Column(name = "CONTACTNAME",length = 20)
	private String contactName;
	
	@Column(name = "APPLICATION_TIME")
	private Date applicationTime;
	
	@Column(name = "VERIFY_TIME")
	private Date verifyTime;
	
	@Column(name = "MEMO")
	private String memo;
	
	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	@Column(name = "REFERRER")
	private String referrer;
	
	@Column(name = "PROVINCE")
	private String province;
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "CITY")
	private String city;
	
	@Column(name = "DISTRICT")
	private String district;
	
	@Column(name = "LATITUDE")
	private Double latitude;
	
	@Column(name = "LONGITUDE")
	private Double longitude;

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}