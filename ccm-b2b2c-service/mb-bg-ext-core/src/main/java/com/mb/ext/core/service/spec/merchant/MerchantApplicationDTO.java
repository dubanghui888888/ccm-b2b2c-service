
package com.mb.ext.core.service.spec.merchant;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateMinuteDeserializer;
import com.mb.ext.core.util.JsonDateMinuteSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantApplicationDTO extends AbstractBaseDTO
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6458832523259011150L;

	private String merchantApplicationUuid;
	
	private String mobileNo;
	
	private String applicationId;
	
	private String applicationStatus;
	
	private String merchantName;
	
	private String merchantDescription;
	
	private String merchantAddress;
	
	private String contactName;
	
	private Date applicationTime;
	
	private Date verifyTime;
	
	public String getMerchantApplicationUuid() {
		return merchantApplicationUuid;
	}

	public void setMerchantApplicationUuid(String merchantApplicationUuid) {
		this.merchantApplicationUuid = merchantApplicationUuid;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantDescription() {
		return merchantDescription;
	}

	public void setMerchantDescription(String merchantDescription) {
		this.merchantDescription = merchantDescription;
	}

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
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getApplicationTime() {
		return applicationTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}
	@JsonSerialize(using=JsonDateMinuteSerializer.class)
	public Date getVerifyTime() {
		return verifyTime;
	}
	@JsonDeserialize(using=JsonDateMinuteDeserializer.class)
	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

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

	private String memo;
	
	private String referrer;
	
	private String province;
	
	private String city;
	
	private String district;
	
	private Double latitude;
	
	private Double longitude;
	
}
