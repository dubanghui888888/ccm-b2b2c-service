package com.mb.ext.core.service.spec.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class UserDeliveryAddressDTO  extends AbstractBaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userDeliveryAddressUuid;

	private String name;
	
	private String telephone;
	
	private String telephone2;
	
	private String country;
	
	private String province;
	
	private String city;
	
	private String area;
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	private String street;
	
	private String zipcode;
	
	private boolean isDefault;

	private UserDTO userDTO;

	public String getUserDeliveryAddressUuid() {
		return userDeliveryAddressUuid;
	}

	public void setUserDeliveryAddressUuid(String userDeliveryAddressUuid) {
		this.userDeliveryAddressUuid = userDeliveryAddressUuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getTelephone2() {
		return telephone2;
	}

	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	
}
