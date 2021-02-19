package com.mb.ext.core.entity;

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

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the user_delivery_address database table.
 * 
 */
@Entity
@Table(name = "user_delivery_address")
@NamedQuery(name = "UserDeliveryAddressEntity.findAll", query = "SELECT u FROM UserDeliveryAddressEntity u")
public class UserDeliveryAddressEntity extends AbstractBaseEntity
{
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

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "USERDELIVERYADDRESS_UUID")
	@GenericGenerator(name = "USERDELIVERYADDRESS_UUID", strategy = "uuid")
	@Column(name = "USERDELIVERYADDRESS_UUID", nullable = false, length = 36)
	private String userDeliveryAddressUuid;

	@Column(name = "NAME", length = 20)
	private String name;
	
	@Column(name = "TELEPHONE", length = 12)
	private String telephone;
	
	@Column(name = "TELEPHONE2", length = 12)
	private String telephone2;
	
	@Column(name = "COUNTRY", length = 25)
	private String country;
	
	@Column(name = "PROVINCE", length = 25)
	private String province;
	
	@Column(name = "CITY", length = 15)
	private String city;
	
	@Column(name = "AREA", length = 15)
	private String area;
	
	@Column(name = "STREET", length = 100)
	private String street;
	
	@Column(name = "ZIPCODE", length = 6)
	private String zipcode;
	
	@Column(name = "IS_DEFAULT")
	private boolean isDefault;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	
}