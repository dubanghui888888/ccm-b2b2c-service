package com.mb.ext.core.service.spec.product;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ProductDeliveryDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String productDeliveryUuid;

	private MerchantDTO merchantDTO;
	
	private String name;
	
	private String description;
	
	private boolean isActive;
	
	public String getProductDeliveryUuid() {
		return productDeliveryUuid;
	}

	public void setProductDeliveryUuid(String productDeliveryUuid) {
		this.productDeliveryUuid = productDeliveryUuid;
	}

	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
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

	private boolean isDefault;
	
	private int deliveryConditionDistance;
	
	private BigDecimal deliveryConditionAmount;
	
	private int firstDistance;
	
	private BigDecimal firstPrice;
	
	private int nextDistance;
	
	private BigDecimal nextPrice;

	private String deliveryFromAddress;
	
	private String deliveryFromProvince;
	
	private String deliveryFromCity;
	
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

	private String deliveryFromDistrict;
	
	private String deliveryFromDetail;
	
	private BigDecimal deliveryFromLongitude;
	
	private BigDecimal deliveryFromLatitude;
}
