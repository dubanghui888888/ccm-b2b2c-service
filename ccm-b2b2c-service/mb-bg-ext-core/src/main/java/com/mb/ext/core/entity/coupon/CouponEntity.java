package com.mb.ext.core.entity.coupon;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the coupon database table.
 * 
 */
@Entity
@Table(name = "coupon")
@NamedQuery(name = "CouponEntity.findAll", query = "SELECT u FROM CouponEntity u")
public class CouponEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 1L;



	@Id
	@GeneratedValue(generator = "COUPON_UUID")
	@GenericGenerator(name = "COUPON_UUID", strategy = "uuid")
	@Column(name = "COUPON_UUID", nullable = false, length = 36)
	private String couponUuid;

	public String getCouponUuid() {
		return couponUuid;
	}

	public void setCouponUuid(String couponUuid) {
		this.couponUuid = couponUuid;
	}


	public boolean isActive() {
		return isActive;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

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

	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
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

	public String getBenefitType() {
		return benefitType;
	}

	public void setBenefitType(String benefitType) {
		this.benefitType = benefitType;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	@Column(name = "ISACTIVE")
	private boolean isActive;
	
	@Column(name = "BENEFIT_TYPE")
	private String benefitType;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "VALID_TYPE")
	private String validType;
	
	@Column(name = "VALID_DAYS")
	private int validDays;
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Column(name = "IMAGE_URL")
	private String imageUrl;
	
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

	@Column(name = "VALID_START_DATE")
	private Date startDate;
	
	@Column(name = "VALID_END_DATE")
	private Date endDate;
	
	@Column(name = "TOTALCOUNT")
	private int totalCount;
	
	@Column(name = "AVAILABLECOUNT")
	private int availableCount;
	
	@Column(name = "LIMITPERUSER")
	private int limitPerUser;
	
	@Column(name = "COUPONCODE")
	private String couponCode;
	
	@Column(name = "BENEFIT_CASH")
	private BigDecimal benefitCash;
	
	@Column(name = "BENEFIT_DISCOUNT")
	private BigDecimal benefitDiscount;
	
	@Column(name = "CONDITION_AMOUNT")
	private BigDecimal conditionAmount;
	
	public List<CouponProductEntity> getBenefitProductList() {
		return benefitProductList;
	}

	public void setBenefitProductList(List<CouponProductEntity> benefitProductList) {
		this.benefitProductList = benefitProductList;
	}

	@Column(name = "MEMO")
	private String memo;

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "couponEntity")
	private List<CouponProductEntity> benefitProductList;

}