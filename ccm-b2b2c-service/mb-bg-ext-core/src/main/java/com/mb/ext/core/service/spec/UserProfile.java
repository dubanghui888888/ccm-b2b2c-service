
package com.mb.ext.core.service.spec;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class UserProfile extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String id;

	private String name;

	private String sex;
	
	private String university;

	private String entEmail;
	
	private String personalEmail;
	
	private String entPhone;

	public String getPersonalPhoneCountryCode() {
		return personalPhoneCountryCode;
	}

	public void setPersonalPhoneCountryCode(String personalPhoneCountryCode) {
		this.personalPhoneCountryCode = personalPhoneCountryCode;
	}

	private String personalPhone;
	
	private String personalPhoneCountryCode;
	
	private String grade;
	
	private String entId;

	public String getEntId() {
		return entId;
	}

	public void setEntId(String entId) {
		this.entId = entId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getEntEmail() {
		return entEmail;
	}

	public void setEntEmail(String entEmail) {
		this.entEmail = entEmail;
	}

	public String getPersonalEmail() {
		return personalEmail;
	}

	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}

	public String getEntPhone() {
		return entPhone;
	}

	public void setEntPhone(String entPhone) {
		this.entPhone = entPhone;
	}

	public String getPersonalPhone() {
		return personalPhone;
	}

	public void setPersonalPhone(String personalPhone) {
		this.personalPhone = personalPhone;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

}
