
package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.util.JsonDateMinuteDeserializer;
import com.mb.ext.core.util.JsonDateMinuteSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class UserDeliveryDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String userDeliveryUuid;
	
	private String deliveryStatus;
	
	private String deliveryAddress;
	
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

	private String deliveryPostCode;
	
	private String deliveryType;
	
	private String deliveryName;
	
	private String deliveryContactNo;
	
	private Date applicationTime;
	
	private String memo;
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getDeliveryPostCode() {
		return deliveryPostCode;
	}

	public void setDeliveryPostCode(String deliveryPostCode) {
		this.deliveryPostCode = deliveryPostCode;
	}

	private Date deliveryTime;
	
	private int deliveryQuantity;
	
	public String getUserDeliveryUuid() {
		return userDeliveryUuid;
	}

	public void setUserDeliveryUuid(String userDeliveryUuid) {
		this.userDeliveryUuid = userDeliveryUuid;
	}

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
	@JsonSerialize(using=JsonDateMinuteSerializer.class)
	public Date getApplicationTime() {
		return applicationTime;
	}
	@JsonDeserialize(using=JsonDateMinuteDeserializer.class)
	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}
	@JsonSerialize(using=JsonDateMinuteSerializer.class)
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	@JsonDeserialize(using=JsonDateMinuteDeserializer.class)
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

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	private String courierName;
	
	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	private String courierNo;
	
	private UserDTO userDTO;
	
	private MerchantDTO merchantDTO;
	
	public String getUserName() {
		String userName = "";
		if(userDTO != null) {
			userName = userDTO.getName();
		}
		return userName;
	}
	public void setUserName(String userName) {
		
	}
	public String getUserPersonalPhone() {
		String userPersonalPhone = "";
		if(userDTO != null) {
			userPersonalPhone = userDTO.getPersonalPhone();
		}
		return userPersonalPhone;
	}
	public void setUserPersonalPhone(String userPersonalPhone) {
		
	}
	public String getMerchantName() {
		String merchantName = "";
		if(merchantDTO != null) {
			merchantName = merchantDTO.getMerchantAddress();
		}
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		
	}
	
}
