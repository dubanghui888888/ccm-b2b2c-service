package com.mb.ext.core.entity.product;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the product_delivery database table.
 * 
 */
@Entity
@Table(name = "product_delivery")
@NamedQuery(name = "ProductDeliveryEntity.findAll", query = "SELECT u FROM ProductDeliveryEntity u")
public class ProductDeliveryEntity extends AbstractBaseEntity
{
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PRODUCTDELIVERY_UUID")
	@GenericGenerator(name = "PRODUCTDELIVERY_UUID", strategy = "uuid")
	@Column(name = "PRODUCTDELIVERY_UUID", nullable = false, length = 36)
	private String productDeliveryUuid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;

	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	@Column(name = "NAME", nullable = false, length = 40)
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "IS_ACTIVE")
	private boolean isActive;
	
	@Column(name = "IS_DEFAULT")
	private boolean isDefault;
	
	@Column(name = "DELIVERY_CONDITION_DISTANCE")
	private int deliveryConditionDistance;
	
	@Column(name = "DELIVERY_CONDITION_AMOUNT")
	private BigDecimal deliveryConditionAmount;
	
	@Column(name = "FIRST_DISTANCE")
	private int firstDistance;
	
	@Column(name = "FIRST_PRICE")
	private BigDecimal firstPrice;
	
	@Column(name = "NEXT_DISTANCE")
	private int nextDistance;
	
	@Column(name = "NEXT_PRICE")
	private BigDecimal nextPrice;

	@Column(name = "DELIVERY_FROM_ADDRESS")
	private String deliveryFromAddress;
	
	@Column(name = "DELIVERY_FROM_PROVINCE")
	private String deliveryFromProvince;
	
	@Column(name = "DELIVERY_FROM_CITY")
	private String deliveryFromCity;
	
	@Column(name = "DELIVERY_FROM_LONGITUDE")
	private BigDecimal deliveryFromLongitude;
	
	@Column(name = "DELIVERY_FROM_LATITUDE")
	private BigDecimal deliveryFromLatitude;
	
	public BigDecimal getDeliveryFromLongitude() {
		return deliveryFromLongitude;
	}

	public void setDeliveryFromLongitude(BigDecimal deliveryFromLongitude) {
		this.deliveryFromLongitude = deliveryFromLongitude;
	}

	public BigDecimal getDeliveryFromLatitude() {
		return deliveryFromLatitude;
	}

	public void setDeliveryFromLatitude(BigDecimal deliveryFromLatitude) {
		this.deliveryFromLatitude = deliveryFromLatitude;
	}

	public String getProductDeliveryUuid() {
		return productDeliveryUuid;
	}

	public void setProductDeliveryUuid(String productDeliveryUuid) {
		this.productDeliveryUuid = productDeliveryUuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public int getDeliveryConditionDistance() {
		return deliveryConditionDistance;
	}

	public void setDeliveryConditionDistance(int deliveryConditionDistance) {
		this.deliveryConditionDistance = deliveryConditionDistance;
	}

	public BigDecimal getDeliveryConditionAmount() {
		return deliveryConditionAmount;
	}

	public void setDeliveryConditionAmount(BigDecimal deliveryConditionAmount) {
		this.deliveryConditionAmount = deliveryConditionAmount;
	}

	public int getFirstDistance() {
		return firstDistance;
	}

	public void setFirstDistance(int firstDistance) {
		this.firstDistance = firstDistance;
	}

	public BigDecimal getFirstPrice() {
		return firstPrice;
	}

	public void setFirstPrice(BigDecimal firstPrice) {
		this.firstPrice = firstPrice;
	}

	public int getNextDistance() {
		return nextDistance;
	}

	public void setNextDistance(int nextDistance) {
		this.nextDistance = nextDistance;
	}

	public BigDecimal getNextPrice() {
		return nextPrice;
	}

	public void setNextPrice(BigDecimal nextPrice) {
		this.nextPrice = nextPrice;
	}

	public String getDeliveryFromAddress() {
		return deliveryFromAddress;
	}

	public void setDeliveryFromAddress(String deliveryFromAddress) {
		this.deliveryFromAddress = deliveryFromAddress;
	}

	public String getDeliveryFromProvince() {
		return deliveryFromProvince;
	}

	public void setDeliveryFromProvince(String deliveryFromProvince) {
		this.deliveryFromProvince = deliveryFromProvince;
	}

	public String getDeliveryFromCity() {
		return deliveryFromCity;
	}

	public void setDeliveryFromCity(String deliveryFromCity) {
		this.deliveryFromCity = deliveryFromCity;
	}

	public String getDeliveryFromDistrict() {
		return deliveryFromDistrict;
	}

	public void setDeliveryFromDistrict(String deliveryFromDistrict) {
		this.deliveryFromDistrict = deliveryFromDistrict;
	}

	public String getDeliveryFromDetail() {
		return deliveryFromDetail;
	}

	public void setDeliveryFromDetail(String deliveryFromDetail) {
		this.deliveryFromDetail = deliveryFromDetail;
	}

	@Column(name = "DELIVERY_FROM_DISTRICT")
	private String deliveryFromDistrict;
	
	@Column(name = "DELIVERY_FROM_DETAIL")
	private String deliveryFromDetail;
	

}