package com.mb.ext.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the opt_contact database table.
 * 
 */
@Entity
@Table(name = "opt_contact")
@NamedQuery(name = "OptContactEntity.findAll", query = "SELECT u FROM OptContactEntity u")
public class OptContactEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "OPTCONTACT_UUID")
	@GenericGenerator(name = "OPTCONTACT_UUID", strategy = "uuid")
	@Column(name = "OPTCONTACT_UUID", nullable = false, length = 36)
	private String optContactUuid;

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	@Column(name = "CONTACTTYPE", nullable = false, length = 10)
	private String contactType;

	@Column(name = "CONTACTNO", nullable = false, length = 20)
	private String contactNo;	

	public String getOptContactUuid() {
		return optContactUuid;
	}

	public void setOptContactUuid(String optContactUuid) {
		this.optContactUuid = optContactUuid;
	}
	
}