
package com.mb.ext.core.service.spec;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String actionType;
	
	private String adminUuid;
	
	public String getAdminUuid() {
		return adminUuid;
	}

	public void setAdminUuid(String adminUuid) {
		this.adminUuid = adminUuid;
	}

	private String id;
	
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	private String mobileNo;
	
	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public List<AdminRoleDTO> getAdminRoleList() {
		return adminRoleList;
	}

	public void setAdminRoleList(List<AdminRoleDTO> adminRoleList) {
		this.adminRoleList = adminRoleList;
	}

	private boolean isActive;
	
	private String password;
	
	private String newPassword;
	
	private String email;
	
	private Date lastLoginTime;
	
	private int failCount;
	
	private int successCount;
	
	private boolean isLocked;
	
	private String verificationCode;
	
	private String createBy;
	
	private List<AdminRoleDTO> adminRoleList = new ArrayList<AdminRoleDTO>();
	
	private BigDecimal availableBalance;
	
	private BigDecimal transactionAmount;
	
	private int userCount;
	
	private int userCountL1;
	
	private int userCountL2;
	
	private int userCountL3;
	
	private int userCountL4;
	
	private int partnerCount;
	
	private int partnerCountL1;
	
	private int partnerCountL2;
	
	public int getPartnerCount() {
		return partnerCount;
	}

	public void setPartnerCount(int partnerCount) {
		this.partnerCount = partnerCount;
	}

	public int getPartnerCountL1() {
		return partnerCountL1;
	}

	public void setPartnerCountL1(int partnerCountL1) {
		this.partnerCountL1 = partnerCountL1;
	}

	public int getPartnerCountL2() {
		return partnerCountL2;
	}

	public void setPartnerCountL2(int partnerCountL2) {
		this.partnerCountL2 = partnerCountL2;
	}

	public int getPartnerCountL3() {
		return partnerCountL3;
	}

	public void setPartnerCountL3(int partnerCountL3) {
		this.partnerCountL3 = partnerCountL3;
	}

	public int getPartnerCountL4() {
		return partnerCountL4;
	}

	public void setPartnerCountL4(int partnerCountL4) {
		this.partnerCountL4 = partnerCountL4;
	}

	private int partnerCountL3;
	
	private int partnerCountL4;
	
	private int productUnit;
	
	private int productUnitBaoDan;
	
	private BigDecimal awardAmount;
	
	private BigDecimal awardAmountRecruit;
	
	private BigDecimal awardAmountSale;
	
	private BigDecimal awardAmountMerchant;
	
	public BigDecimal getAwardAmountTrain() {
		return awardAmountTrain;
	}

	public void setAwardAmountTrain(BigDecimal awardAmountTrain) {
		this.awardAmountTrain = awardAmountTrain;
	}

	public BigDecimal getAwardAmountPartner() {
		return awardAmountPartner;
	}

	public void setAwardAmountPartner(BigDecimal awardAmountPartner) {
		this.awardAmountPartner = awardAmountPartner;
	}

	public BigDecimal getAwardAmountPerformance() {
		return awardAmountPerformance;
	}

	public void setAwardAmountPerformance(BigDecimal awardAmountPerformance) {
		this.awardAmountPerformance = awardAmountPerformance;
	}

	private BigDecimal awardAmountTrain;
	
	private BigDecimal awardAmountPartner;
	
	private BigDecimal awardAmountPerformance;
	
	public BigDecimal getAwardAmount() {
		return awardAmount;
	}

	public void setAwardAmount(BigDecimal awardAmount) {
		this.awardAmount = awardAmount;
	}

	public BigDecimal getAwardAmountRecruit() {
		return awardAmountRecruit;
	}

	public void setAwardAmountRecruit(BigDecimal awardAmountRecruit) {
		this.awardAmountRecruit = awardAmountRecruit;
	}

	public BigDecimal getAwardAmountSale() {
		return awardAmountSale;
	}

	public void setAwardAmountSale(BigDecimal awardAmountSale) {
		this.awardAmountSale = awardAmountSale;
	}

	public BigDecimal getAwardAmountMerchant() {
		return awardAmountMerchant;
	}

	public void setAwardAmountMerchant(BigDecimal awardAmountMerchant) {
		this.awardAmountMerchant = awardAmountMerchant;
	}

	public int getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(int productUnit) {
		this.productUnit = productUnit;
	}

	public int getProductUnitBaoDan() {
		return productUnitBaoDan;
	}

	public void setProductUnitBaoDan(int productUnitBaoDan) {
		this.productUnitBaoDan = productUnitBaoDan;
	}

	public int getProductUnitFuGou() {
		return productUnitFuGou;
	}

	public void setProductUnitFuGou(int productUnitFuGou) {
		this.productUnitFuGou = productUnitFuGou;
	}

	public BigDecimal getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(BigDecimal productAmount) {
		this.productAmount = productAmount;
	}

	public BigDecimal getProductAmountBaoDan() {
		return productAmountBaoDan;
	}

	public void setProductAmountBaoDan(BigDecimal productAmountBaoDan) {
		this.productAmountBaoDan = productAmountBaoDan;
	}

	public BigDecimal getProductAmountFuGou() {
		return productAmountFuGou;
	}

	public void setProductAmountFuGou(BigDecimal productAmountFuGou) {
		this.productAmountFuGou = productAmountFuGou;
	}

	private int productUnitFuGou;
	
	private BigDecimal productAmount;
	
	private BigDecimal productAmountBaoDan;
	
	private BigDecimal productAmountFuGou;
	
	public int getUserCountL1() {
		return userCountL1;
	}

	public void setUserCountL1(int userCountL1) {
		this.userCountL1 = userCountL1;
	}

	public int getUserCountL2() {
		return userCountL2;
	}

	public void setUserCountL2(int userCountL2) {
		this.userCountL2 = userCountL2;
	}

	public int getUserCountL3() {
		return userCountL3;
	}

	public void setUserCountL3(int userCountL3) {
		this.userCountL3 = userCountL3;
	}

	public int getUserCountL4() {
		return userCountL4;
	}

	public void setUserCountL4(int userCountL4) {
		this.userCountL4 = userCountL4;
	}

	private int incrementUserCount;
	
	private int merchantCount;
	
	private int merchantInventory;
	
	private int userInventory;
	
	public int getMerchantInventory() {
		return merchantInventory;
	}

	public void setMerchantInventory(int merchantInventory) {
		this.merchantInventory = merchantInventory;
	}

	public int getUserInventory() {
		return userInventory;
	}

	public void setUserInventory(int userInventory) {
		this.userInventory = userInventory;
	}

	public int getDeliveryInventory() {
		return deliveryInventory;
	}

	public void setDeliveryInventory(int deliveryInventory) {
		this.deliveryInventory = deliveryInventory;
	}

	private int deliveryInventory;
	
	private List<ChartDTO> userCountChart;
	
	public List<ChartDTO> getUserCountChart() {
		return userCountChart;
	}

	public void setUserCountChart(List<ChartDTO> userCountChart) {
		this.userCountChart = userCountChart;
	}

	public List<ChartDTO> getMerchantCountChart() {
		return merchantCountChart;
	}

	public void setMerchantCountChart(List<ChartDTO> merchantCountChart) {
		this.merchantCountChart = merchantCountChart;
	}

	public List<ChartDTO> getFundChart() {
		return fundChart;
	}

	public void setFundChart(List<ChartDTO> fundChart) {
		this.fundChart = fundChart;
	}

	private List<ChartDTO> merchantCountChart;
	
	private List<ChartDTO> fundChart;
	
	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public int getIncrementUserCount() {
		return incrementUserCount;
	}

	public void setIncrementUserCount(int incrementUserCount) {
		this.incrementUserCount = incrementUserCount;
	}

	public int getMerchantCount() {
		return merchantCount;
	}

	public void setMerchantCount(int merchantCount) {
		this.merchantCount = merchantCount;
	}

	public int getIncrementMerchantCount() {
		return incrementMerchantCount;
	}

	public void setIncrementMerchantCount(int incrementMerchantCount) {
		this.incrementMerchantCount = incrementMerchantCount;
	}

	private int incrementMerchantCount;

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}
}
