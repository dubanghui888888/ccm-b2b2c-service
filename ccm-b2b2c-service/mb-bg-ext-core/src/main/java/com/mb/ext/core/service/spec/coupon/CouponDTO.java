
package com.mb.ext.core.service.spec.coupon;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class CouponDTO extends AbstractBaseDTO
{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String couponUuid;
	
	private boolean isActive;
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	private String imageUrl;
	
	private String memo;
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	public String getBenefitType() {
		return benefitType;
	}

	public void setBenefitType(String benefitType) {
		this.benefitType = benefitType;
	}

	private String benefitType;
	
	List<ProductDTO> benefitProductList;
	
	private MerchantDTO merchantDTO;
	
	public List<ProductDTO> getBenefitProductList() {
		return benefitProductList;
	}

	public void setBenefitProductList(List<ProductDTO> benefitProductList) {
		this.benefitProductList = benefitProductList;
	}

	private String name;
	
	private String type;
	
	private String validType;
	
	private int validDays;
	
	public int getValidDays() {
		return validDays;
	}

	public void setValidDays(int validDays) {
		this.validDays = validDays;
	}

	public String getValidType() {
		return validType;
	}

	public void setValidType(String validType) {
		this.validType = validType;
	}

	private Date startDate;
	
	private Date endDate;
	
	public String getCouponUuid() {
		return couponUuid;
	}

	public void setCouponUuid(String couponUuid) {
		this.couponUuid = couponUuid;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getAvailableCount() {
		return availableCount;
	}

	public void setAvailableCount(int availableCount) {
		this.availableCount = availableCount;
	}

	public int getLimitPerUser() {
		return limitPerUser;
	}

	public void setLimitPerUser(int limitPerUser) {
		this.limitPerUser = limitPerUser;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public BigDecimal getBenefitCash() {
		return benefitCash;
	}

	public void setBenefitCash(BigDecimal benefitCash) {
		this.benefitCash = benefitCash;
	}

	public BigDecimal getBenefitDiscount() {
		return benefitDiscount;
	}

	public void setBenefitDiscount(BigDecimal benefitDiscount) {
		this.benefitDiscount = benefitDiscount;
	}

	public BigDecimal getConditionAmount() {
		return conditionAmount;
	}

	public void setConditionAmount(BigDecimal conditionAmount) {
		this.conditionAmount = conditionAmount;
	}

	private int totalCount;
	
	private int availableCount;
	
	private int limitPerUser;
	
	private String couponCode;
	
	private BigDecimal benefitCash;
	
	private BigDecimal benefitDiscount;
	
	private BigDecimal conditionAmount;
	
	private UserDTO userDTO;

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	
}
