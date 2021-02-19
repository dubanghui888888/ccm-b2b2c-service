
package com.mb.ext.core.service.spec;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class EntDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String uuid;
	
	private String name;
	
	private String registerPassword;
	
	private String registerEmail;
	
	public String getRegisterPassword() {
		return registerPassword;
	}

	public void setRegisterPassword(String registerPassword) {
		this.registerPassword = registerPassword;
	}

	public String getRegisterEmail() {
		return registerEmail;
	}

	public void setRegisterEmail(String registerEmail) {
		this.registerEmail = registerEmail;
	}

	public String getRegisterMobileCountryCode() {
		return registerMobileCountryCode;
	}

	public void setRegisterMobileCountryCode(String registerMobileCountryCode) {
		this.registerMobileCountryCode = registerMobileCountryCode;
	}

	public String getRegisterMobileNo() {
		return registerMobileNo;
	}

	public void setRegisterMobileNo(String registerMobileNo) {
		this.registerMobileNo = registerMobileNo;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	private String registerMobileCountryCode;
	
	private String registerMobileNo;
	
	private String verificationCode;
	
	private String createBy;
	
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	private String updateBy;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getEntPhone() {
		return entPhone;
	}

	public void setEntPhone(String entPhone) {
		this.entPhone = entPhone;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getLicenceUrl() {
		return licenceUrl;
	}

	public void setLicenceUrl(String licenceUrl) {
		this.licenceUrl = licenceUrl;
	}

	public String getEntEmail() {
		return entEmail;
	}

	public void setEntEmail(String entEmail) {
		this.entEmail = entEmail;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	private String logoUrl;
	
/*	private String actualLogoUrl;

	public String getActualLogoUrl() {
		return actualLogoUrl;
	}

	public void setActualLogoUrl(String actualLogoUrl) {
		this.actualLogoUrl = actualLogoUrl;
	}*/

	private String shortName;
	
	private String shortEnglishName;
	
	public String getShortEnglishName() {
		return shortEnglishName;
	}

	public void setShortEnglishName(String shortEnglishName) {
		this.shortEnglishName = shortEnglishName;
	}

	private String entPhone;

	private String contactName;
	
	private String contactPhone;
	
	private String licenceUrl;
	
/*	public String getActualLicenceUrl() {
		return actualLicenceUrl;
	}

	public void setActualLicenceUrl(String actualLicenceUrl) {
		this.actualLicenceUrl = actualLicenceUrl;
	}

	private String actualLicenceUrl;*/

	private String entEmail;
	
	private String contactEmail;
	
	private String openId;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

}
