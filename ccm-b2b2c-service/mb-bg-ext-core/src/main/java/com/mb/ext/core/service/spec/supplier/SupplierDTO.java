
package com.mb.ext.core.service.spec.supplier;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String supplierUuid;
	
	private boolean isClosed;
	
	private Date registerDate;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public Date getRegisterDate() {
		return registerDate;
	}
	
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	private String memo;
	
	private int soldUnitTotal;

	public int getSoldUnitTotal() {
		return soldUnitTotal;
	}

	public void setSoldUnitTotal(int soldUnitTotal) {
		this.soldUnitTotal = soldUnitTotal;
	}

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}

	public String getSupplierUuid() {
		return supplierUuid;
	}

	public void setSupplierUuid(String supplierUuid) {
		this.supplierUuid = supplierUuid;
	}

	private List<UserDTO> userList;
	
	public List<UserDTO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	private String supplierName;
	
	private String supplierDescription;
	
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	private String mobileNo;


	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

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

	private String newPassword;
	
	private Date lastLoginTime;
	
	private int failCount;
	
	private int successCount;
	
	private String supplierAddress;
	
	private String contactName;
	
	private BigDecimal availableBalance;
	
	private BigDecimal totalBalance;
	
	private int availablePoint;
	
	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}

	public BigDecimal getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(BigDecimal totalBalance) {
		this.totalBalance = totalBalance;
	}

	public int getAvailablePoint() {
		return availablePoint;
	}

	public void setAvailablePoint(int availablePoint) {
		this.availablePoint = availablePoint;
	}

	public int getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}

	private int totalPoint;


	public String getSupplierDescription() {
		return supplierDescription;
	}

	public void setSupplierDescription(String supplierDescription) {
		this.supplierDescription = supplierDescription;
	}

	int userCount;
	
	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	private Date createDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getCreateDate() {
		return createDate;
	}
	@JsonSerialize(using=JsonDateSerializer.class) 
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
