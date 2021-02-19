
package com.mb.ext.core.service.spec.merchant;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.mb.ext.core.service.spec.coupon.CouponDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
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
public class MerchantDTO extends AbstractBaseDTO
{

	public boolean isFollowed() {
		return isFollowed;
	}

	public void setFollowed(boolean followed) {
		isFollowed = followed;
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private int followTotal;

	private boolean isFollowed;

	public int getFollowTotal() {
		return followTotal;
	}

	public void setFollowTotal(int followTotal) {
		this.followTotal = followTotal;
	}

	private int orderUnit;
	
	private int orderReturnUnit;
	
	public int getOrderReturnUnit() {
		return orderReturnUnit;
	}

	public void setOrderReturnUnit(int orderReturnUnit) {
		this.orderReturnUnit = orderReturnUnit;
	}

	public BigDecimal getOrderReturnAmount() {
		return orderReturnAmount;
	}

	public void setOrderReturnAmount(BigDecimal orderReturnAmount) {
		this.orderReturnAmount = orderReturnAmount;
	}

	private BigDecimal orderAmount;
	
	private BigDecimal orderReturnAmount;
	
	private String merchantUuid;
	
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public int getOrderUnit() {
		return orderUnit;
	}

	public void setOrderUnit(int orderUnit) {
		this.orderUnit = orderUnit;
	}

	private boolean isClosed;
	
	private String referrer;
	
	private boolean isRecharge;
	
	private BigDecimal chargeTotal;
	
	private int assignPointTotal;
	
	public boolean isRecharge() {
		return isRecharge;
	}

	public void setRecharge(boolean isRecharge) {
		this.isRecharge = isRecharge;
	}

	public BigDecimal getChargeTotal() {
		return chargeTotal;
	}

	public void setChargeTotal(BigDecimal chargeTotal) {
		this.chargeTotal = chargeTotal;
	}
	
	public int getAssignPointTotal() {
		return assignPointTotal;
	}

	public void setAssignPointTotal(int assignPointTotal) {
		this.assignPointTotal = assignPointTotal;
	}

	public String getPaymentQrCode() {
		return paymentQrCode;
	}

	public void setPaymentQrCode(String paymentQrCode) {
		this.paymentQrCode = paymentQrCode;
	}

	public String getPaymentRedirectUrl() {
		return paymentRedirectUrl;
	}

	public void setPaymentRedirectUrl(String paymentRedirectUrl) {
		this.paymentRedirectUrl = paymentRedirectUrl;
	}

	private String paymentQrCode;
	
	public String getChargeRedirectUrl() {
		return chargeRedirectUrl;
	}

	public void setChargeRedirectUrl(String chargeRedirectUrl) {
		this.chargeRedirectUrl = chargeRedirectUrl;
	}

	private String paymentRedirectUrl;
	
	private String chargeRedirectUrl;
	
	private int exchangeRate;
	
	public String getTranPassword() {
		return tranPassword;
	}

	public int getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(int exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public void setTranPassword(String tranPassword) {
		this.tranPassword = tranPassword;
	}

	private String password;
	
	private String tranPassword;
	
	private String verificationCode;
	
	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private Date registerDate;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getRegisterDate() {
		return registerDate;
	}
	
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	private String memo;

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}

	public String getMerchantUuid() {
		return merchantUuid;
	}

	public void setMerchantUuid(String merchantUuid) {
		this.merchantUuid = merchantUuid;
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
	
	private String merchantId;

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	private BigDecimal weight;
	
	private BigDecimal score;
	
	private int soldUnit;
	
	public int getSoldUnit() {
		return soldUnit;
	}

	public void setSoldUnit(int soldUnit) {
		this.soldUnit = soldUnit;
	}

	public int getSaleUnit() {
		return saleUnit;
	}

	public void setSaleUnit(int saleUnit) {
		this.saleUnit = saleUnit;
	}

	private int saleUnit;
	
	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	private String merchantName;
	
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	private String merchantDescription;
	
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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

	public String getMerchantAddress() {
		return merchantAddress;
	}

	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
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
	
	private String merchantAddress;
	
	private String logo;
	
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

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


	public String getMerchantDescription() {
		return merchantDescription;
	}

	public void setMerchantDescription(String merchantDescription) {
		this.merchantDescription = merchantDescription;
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

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	private String province;
	
	private String city;
	
	private String district;
	
	private Double latitude;
	
	private Double longitude;
	
	private Double userLatitude;
	
	private Double userLongitude;
	
	private Double distance;

	public List<ProductDTO> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductDTO> productList) {
		this.productList = productList;
	}

	public List<CouponDTO> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<CouponDTO> couponList) {
		this.couponList = couponList;
	}

	private List<ProductDTO> productList;

	private List<CouponDTO> couponList;

	public Double getUserLatitude() {
		return userLatitude;
	}

	public void setUserLatitude(Double userLatitude) {
		this.userLatitude = userLatitude;
	}

	public Double getUserLongitude() {
		return userLongitude;
	}

	public void setUserLongitude(Double userLongitude) {
		this.userLongitude = userLongitude;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}
}
