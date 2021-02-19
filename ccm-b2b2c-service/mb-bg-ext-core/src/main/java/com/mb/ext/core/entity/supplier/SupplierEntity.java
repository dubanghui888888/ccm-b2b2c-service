package com.mb.ext.core.entity.supplier;

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
 * The persistent class for the supplier database table.
 * 
 */
@Entity
@Table(name = "supplier")
@NamedQuery(name = "SupplierEntity.findAll", query = "SELECT u FROM SupplierEntity u")
public class SupplierEntity extends AbstractBaseEntity
{



	private static final long serialVersionUID = 1L;

	public String getSupplierAddress() {
		return supplierAddress;
	}

	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getSupplierUuid() {
		return supplierUuid;
	}

	public void setSupplierUuid(String supplierUuid) {
		this.supplierUuid = supplierUuid;
	}

	@Id
	@GeneratedValue(generator = "SUPPLIER_UUID")
	@GenericGenerator(name = "SUPPLIER_UUID", strategy = "uuid")
	@Column(name = "SUPPLIER_UUID", nullable = false, length = 36)
	private String supplierUuid;

	@Column(name = "MOBILENO", length = 20)
	private String mobileNo;
	
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Column(name = "SUPPLIERNAME",length = 100)
	private String supplierName;

	@Column(name = "SUPPLIERDESCRIPTION",length = 200)
	private String supplierDescription;

	public String getSupplierDescription() {
		return supplierDescription;
	}

	public void setSupplierDescription(String supplierDescription) {
		this.supplierDescription = supplierDescription;
	}

	@Column(name = "SUPPLIERADDRESS",length = 45)
	private String supplierAddress;
	
	@Column(name = "CONTACTNAME",length = 20)
	private String contactName;
	
	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}



	@Column(name = "ISCLOSED")
	private boolean isClosed;
	
	@Column(name = "REGISTER_DATE")
	private Date registerDate;
	
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}


	@Column(name = "MEMO")
	private String memo;

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}