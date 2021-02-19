package com.mb.ext.core.entity;

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
 * The persistent class for the verification_code database table.
 * 
 */
@Entity
@Table(name = "verification_code")
@NamedQuery(name = "VerificationCodeEntity.findAll", query = "SELECT u FROM VerificationCodeEntity u")
public class VerificationCodeEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "VERIFICATIONCODE_UUID")
	@GenericGenerator(name = "VERIFICATIONCODE_UUID", strategy = "uuid")
	@Column(name = "VERIFICATIONCODE_UUID", nullable = false, length = 36)
	private String verificationCodeUuid;

	@Column(name = "TYPE", nullable = false, length = 1)
	private String type;
	
	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	@Column(name = "MOBILE_COUNTRYCODE", length = 4)
	private String mobileCountryCode;
	
	@Column(name = "EXPIRE_TIME")
	private Date expireTime;
	
	public String getVerificationCodeUuid() {
		return verificationCodeUuid;
	}

	public void setVerificationCodeUuid(String verificationCodeUuid) {
		this.verificationCodeUuid = verificationCodeUuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMobileCountryCode() {
		return mobileCountryCode;
	}

	public void setMobileCountryCode(String mobileCountryCode) {
		this.mobileCountryCode = mobileCountryCode;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "MOBILE_NUMBER", length = 11)
	private String mobileNo;
	
	@Column(name = "CODE", length = 8)
	private String code;
	
	@Column(name = "EMAIL", length = 8)
	private String email;


	
}