
package com.mb.ext.core.service.spec;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	public String getTokenId() {
		return tokenId;
	}

	public String getOpenIdOfc() {
		return openIdOfc;
	}

	public void setOpenIdOfc(String openIdOfc) {
		this.openIdOfc = openIdOfc;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	private String code;
	
	private String openId;
	
	private String openIdOfc;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getDirectUserCount() {
		return directUserCount;
	}

	public void setDirectUserCount(int directUserCount) {
		this.directUserCount = directUserCount;
	}

	public int getTeamUserCount() {
		return teamUserCount;
	}

	public void setTeamUserCount(int teamUserCount) {
		this.teamUserCount = teamUserCount;
	}

	public BigDecimal getDirectSaleAmount() {
		return directSaleAmount;
	}

	public void setDirectSaleAmount(BigDecimal directSaleAmount) {
		this.directSaleAmount = directSaleAmount;
	}

	public BigDecimal getTeamSaleAmount() {
		return teamSaleAmount;
	}

	public void setTeamSaleAmount(BigDecimal teamSaleAmount) {
		this.teamSaleAmount = teamSaleAmount;
	}

	private String unionId;
	
	//直接邀请会员
	int directUserCount ;
	//团队会员数
	int teamUserCount ;
	//个人销售额
	BigDecimal directSaleAmount ;
	//团队销售额
	BigDecimal teamSaleAmount ;
	
	public boolean isNotificationEnabled() {
		return isNotificationEnabled;
	}

	public void setNotificationEnabled(boolean isNotificationEnabled) {
		this.isNotificationEnabled = isNotificationEnabled;
	}

	private String tokenId;
	
	private int productUnit;
	
	boolean isNotificationEnabled;
	
	private int signDateNum;
	
	public int getSignDateNum() {
		return signDateNum;
	}

	public void setSignDateNum(int signDateNum) {
		this.signDateNum = signDateNum;
	}

	public int getProductPoint() {
		return productPoint;
	}

	public void setProductPoint(int productPoint) {
		this.productPoint = productPoint;
	}

	private BigDecimal productAmount;
	
	private int productPoint;
	
	private BigDecimal awardAmount;
	
	public BigDecimal getAwardAmount() {
		return awardAmount;
	}

	public void setAwardAmount(BigDecimal awardAmount) {
		this.awardAmount = awardAmount;
	}

	public int getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(int productUnit) {
		this.productUnit = productUnit;
	}

	public BigDecimal getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(BigDecimal productAmount) {
		this.productAmount = productAmount;
	}

	private int total;
	
	private int totalAssignPoint;
	
	private int totalOrder;
	
	private int totalL1;

	public int getTotalL1() {
		return totalL1;
	}

	public void setTotalL1(int totalL1) {
		this.totalL1 = totalL1;
	}

	public int getTotal() {
		return total;
	}

	public int getTotalOrder() {
		return totalOrder;
	}

	public void setTotalOrder(int totalOrder) {
		this.totalOrder = totalOrder;
	}

	public int getTotalAssignPoint() {
		return totalAssignPoint;
	}

	public void setTotalAssignPoint(int totalAssignPoint) {
		this.totalAssignPoint = totalAssignPoint;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	List<OrderDTO> orderList;
	
	List<UserInventoryHistoryDTO> inventoryHistoryList;
	
	public List<OrderDTO> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderDTO> orderList) {
		this.orderList = orderList;
	}

	public List<UserInventoryHistoryDTO> getInventoryHistoryList() {
		return inventoryHistoryList;
	}

	public void setInventoryHistoryList(
			List<UserInventoryHistoryDTO> inventoryHistoryList) {
		this.inventoryHistoryList = inventoryHistoryList;
	}

	public List<UserDeliveryDTO> getDeliveryList() {
		return deliveryList;
	}

	public void setDeliveryList(List<UserDeliveryDTO> deliveryList) {
		this.deliveryList = deliveryList;
	}

	List<UserDeliveryDTO> deliveryList;

	boolean isMerchant;
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	private boolean isActive;
	
	public boolean isMerchant() {
		return isMerchant;
	}

	public void setMerchant(boolean isMerchant) {
		this.isMerchant = isMerchant;
	}

	int startIndex;
	
	int pageSize;
	
	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	private String id;
	
	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	private String idCardNo;
	
	private int userCount;
	
	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	private String userUuid;
	
	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String type;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String password;

	private String photoUrl;
	
	public String getSupervisorL1Name(){
		String supervisorL1Name = "";
		if(supervisorL1 != null){
			supervisorL1Name = supervisorL1.getName();
		}
		return supervisorL1Name;
	}
	
	public String getSupervisorL1PersonalPhone(){
		String supervisorL1PersonalPhone = "";
		if(supervisorL1 != null){
			supervisorL1PersonalPhone = supervisorL1.getPersonalPhone();
		}
		return supervisorL1PersonalPhone;
	}
	
	public void setSupervisorL1Name(String name){
		
	}
	
	public void setSupervisorL1PersonalPhone(String personalPhone){
		
	}
	
	public String getSupervisorL2Name(){
		String supervisorL2Name = "";
		if(supervisorL2 != null){
			supervisorL2Name = supervisorL2.getName();
		}
		return supervisorL2Name;
	}
	
	public void setSupervisorL2Name(String name){
		
	}
	
	public String getSupervisorL3Name(){
		String supervisorL3Name = "";
		if(supervisorL3 != null){
			supervisorL3Name = supervisorL3.getName();
		}
		return supervisorL3Name;
	}
	
	public void setSupervisorL3Name(String name){
		
	}
	
	public UserDTO getSupervisorL1() {
		return supervisorL1;
	}

	public void setSupervisorL1(UserDTO supervisorL1) {
		this.supervisorL1 = supervisorL1;
	}

	public UserDTO getSupervisorL2() {
		return supervisorL2;
	}

	public void setSupervisorL2(UserDTO supervisorL2) {
		this.supervisorL2 = supervisorL2;
	}

	public UserDTO getSupervisorL3() {
		return supervisorL3;
	}

	public void setSupervisorL3(UserDTO supervisorL3) {
		this.supervisorL3 = supervisorL3;
	}

	private String qrCodeUrl;
	
	private UserDTO supervisorL1;
	
	private UserDTO supervisorL2;
	
	private UserDTO supervisorL3;
	
	public String getActualPhotoUrl() {
		return actualPhotoUrl;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public void setActualPhotoUrl(String actualPhotoUrl) {
		this.actualPhotoUrl = actualPhotoUrl;
	}

	private String actualPhotoUrl;
	
	private Date lastLoginTime;
	
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

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	private int failCount;
	
	private boolean isLocked;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

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

	public String getPersonalEmail() {
		return personalEmail;
	}

	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}

	public String getPersonalPhone() {
		return personalPhone;
	}

	public void setPersonalPhone(String personalPhone) {
		this.personalPhone = personalPhone;
	}

	private String createBy;
	
	private String updateBy;

	public UserLevelDTO getUserLevelDTO() {
		return userLevelDTO;
	}

	public void setUserLevelDTO(UserLevelDTO userLevelDTO) {
		this.userLevelDTO = userLevelDTO;
	}

	private String name;
	
	private String memo;

	private UserLevelDTO userLevelDTO;
	
	public String getUserLevelName(){
		if(userLevelDTO != null)
			return userLevelDTO.getName();
		else 
			return "";
	}

	public void setUserLevelName(String userLevelName){
		
	}
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	private String sex;
	
	public String getPersonalPhoneCountryCode() {
		return personalPhoneCountryCode;
	}

	public void setPersonalPhoneCountryCode(String personalPhoneCountryCode) {
		this.personalPhoneCountryCode = personalPhoneCountryCode;
	}

	private String personalEmail;

	private String personalPhone;
	
	private String personalPhoneCountryCode;
	
	private Date createDate;
	
	private Date registerDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getCreateDate() {
		return createDate;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public Date getRegisterDate() {
		return registerDate;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	private String newPassword;
	
	private String actionType;
	
	private String verificationCode;
	
	private String verifyType;

	public String getVerifyType() {
		return verifyType;
	}

	public void setVerifyType(String verifyType) {
		this.verifyType = verifyType;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	
	public void setIdentified(boolean isIdentified){
	}
	
	private int successCount;

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	
	private MerchantDTO merchantDTO;
	
	public String getMerchantAddress(){
		String merchantAddress = "";
		if(merchantDTO != null){
			merchantAddress = merchantDTO.getMerchantAddress();
		}
		return merchantAddress;
	}
	public void setMerchantAddress(String merchantAddress){
		
	}
	

	public UserBalanceDTO getUserBalanceDTO() {
		return userBalanceDTO;
	}

	public void setUserBalanceDTO(UserBalanceDTO userBalanceDTO) {
		this.userBalanceDTO = userBalanceDTO;
	}

	private UserInventoryDTO userInventoryDTO;
	

	private UserBalanceDTO userBalanceDTO;
	
	private UserPerformanceDTO userPerformanceDTO;
	
	public UserPerformanceDTO getUserPerformanceDTO() {
		return userPerformanceDTO;
	}

	public void setUserPerformanceDTO(UserPerformanceDTO userPerformanceDTO) {
		this.userPerformanceDTO = userPerformanceDTO;
	}

	public BigDecimal getAvailableBalance(){
		BigDecimal availableBalance = new BigDecimal(0);
		if(userBalanceDTO != null){
			availableBalance = userBalanceDTO.getAvailableBalance();
		}
		return availableBalance;
	}
	
	public void setAvailableBalance(BigDecimal availableBalance){
		
	}
	
	public int getAvailablePoint(){
		int availablePoint = 0;
		if(userBalanceDTO != null){
			availablePoint = userBalanceDTO.getAvailablePoint();
		}
		return availablePoint;
	}
	
	public BigDecimal getPerformanceAmount(){
		BigDecimal performanceAmount = new BigDecimal(0);
		if(userPerformanceDTO != null){
			performanceAmount = userPerformanceDTO.getPerformanceAmount();
		}
		return performanceAmount;
	}
	
	public void setPerformanceAmount(BigDecimal performanceAmount){
		
	}
	
	public BigDecimal getPerformanceAward(){
		BigDecimal performanceAward = new BigDecimal(0);
		if(userPerformanceDTO != null){
			performanceAward = userPerformanceDTO.getPerformanceAward();
		}
		return performanceAward;
	}
	
	public void setPerformanceAward(BigDecimal performanceAward){
		
	}
	
	public BigDecimal getLedgerBalance(){
		BigDecimal ledgerBalance = new BigDecimal(0);
		if(userBalanceDTO != null){
			ledgerBalance = userBalanceDTO.getLedgerBalance();
		}
		return ledgerBalance;
	}
	
	public void setLedgerBalance(BigDecimal ledgerBalance){
		
	}
	
	public int getInventoryBalance(){
		int inventoryBalance = 0;
		if(userInventoryDTO != null){
			inventoryBalance = userInventoryDTO.getBalance();
		}
		return inventoryBalance;
	}
	
	public void setInventoryBalance(int inventoryBalance){
		
	}
	

	public UserInventoryDTO getUserInventoryDTO() {
		return userInventoryDTO;
	}

	public void setUserInventoryDTO(UserInventoryDTO userInventoryDTO) {
		this.userInventoryDTO = userInventoryDTO;
	}

	private List<String> paymentResultUrlList;
	
	public List<String> getPaymentResultUrlList() {
		return paymentResultUrlList;
	}

	public void setPaymentResultUrlList(List<String> paymentResultUrlList) {
		this.paymentResultUrlList = paymentResultUrlList;
	}


	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}
	
	private int couponCount;
	
	private int couponOrderPoint;
	
	public int getCouponCount() {
		return couponCount;
	}

	public void setCouponCount(int couponCount) {
		this.couponCount = couponCount;
	}

	private int orderPoint;
	
	private int rankingTotalPoint;

	public int getCouponOrderPoint() {
		return couponOrderPoint;
	}

	public void setCouponOrderPoint(int couponOrderPoint) {
		this.couponOrderPoint = couponOrderPoint;
	}

	public int getOrderPoint() {
		return orderPoint;
	}

	public List<UserWithdrawDTO> getWithdrawList() {
		return withdrawList;
	}

	public void setWithdrawList(List<UserWithdrawDTO> withdrawList) {
		this.withdrawList = withdrawList;
	}

	public List<UserAwardDTO> getAwardList() {
		return awardList;
	}

	public void setAwardList(List<UserAwardDTO> awardList) {
		this.awardList = awardList;
	}

	public void setOrderPoint(int orderPoint) {
		this.orderPoint = orderPoint;
	}

	public int getRankingTotalPoint() {
		return rankingTotalPoint;
	}

	public void setRankingTotalPoint(int rankingTotalPoint) {
		this.rankingTotalPoint = rankingTotalPoint;
	}
	
	List<UserWithdrawDTO> withdrawList;
	
	List<UserAwardDTO> awardList;

	public boolean isProfitWelfareReceived() {
		return isProfitWelfareReceived;
	}

	public void setProfitWelfareReceived(boolean profitWelfareReceived) {
		isProfitWelfareReceived = profitWelfareReceived;
	}

	boolean  isProfitWelfareReceived;
	
}
