package com.mb.ext.core.service.spec.product;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ProductFreightDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String productFreightUuid;

	private String name;
	
	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	private int chargeType;
	
	private MerchantDTO merchantDTO;
	
	public String getProductFreightUuid() {
		return productFreightUuid;
	}

	public void setProductFreightUuid(String productFreightUuid) {
		this.productFreightUuid = productFreightUuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getChargeType() {
		return chargeType;
	}

	public void setChargeType(int chargeType) {
		this.chargeType = chargeType;
	}

	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public boolean isShippingFree() {
		return isShippingFree;
	}

	public void setShippingFree(boolean isShippingFree) {
		this.isShippingFree = isShippingFree;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public BigDecimal getShippingFreeConditionAmount() {
		return shippingFreeConditionAmount;
	}

	public void setShippingFreeConditionAmount(BigDecimal shippingFreeConditionAmount) {
		this.shippingFreeConditionAmount = shippingFreeConditionAmount;
	}

	public int getDefaultFirstUnit() {
		return defaultFirstUnit;
	}

	public void setDefaultFirstUnit(int defaultFirstUnit) {
		this.defaultFirstUnit = defaultFirstUnit;
	}

	public int getDefaultNextUnit() {
		return defaultNextUnit;
	}

	public void setDefaultNextUnit(int defaultNextUnit) {
		this.defaultNextUnit = defaultNextUnit;
	}

	public BigDecimal getDefaultFirstWeight() {
		return defaultFirstWeight;
	}

	public void setDefaultFirstWeight(BigDecimal defaultFirstWeight) {
		this.defaultFirstWeight = defaultFirstWeight;
	}

	public BigDecimal getDefaultNextWeight() {
		return defaultNextWeight;
	}

	public void setDefaultNextWeight(BigDecimal defaultNextWeight) {
		this.defaultNextWeight = defaultNextWeight;
	}

	public BigDecimal getDefaultFirstPrice() {
		return defaultFirstPrice;
	}

	public void setDefaultFirstPrice(BigDecimal defaultFirstPrice) {
		this.defaultFirstPrice = defaultFirstPrice;
	}

	public BigDecimal getDefaultNextPrice() {
		return defaultNextPrice;
	}

	public void setDefaultNextPrice(BigDecimal defaultNextPrice) {
		this.defaultNextPrice = defaultNextPrice;
	}

	private String courierName;
	
	private boolean isShippingFree;
	
	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	private boolean isActive;
	
	private boolean isDefault;
	
	private BigDecimal shippingFreeConditionAmount;
	
	public List<ProductFreightRegionDTO> getFreghtRegionList() {
		return freghtRegionList;
	}

	public void setFreghtRegionList(List<ProductFreightRegionDTO> freghtRegionList) {
		this.freghtRegionList = freghtRegionList;
	}

	private int defaultFirstUnit;
	
	private int defaultNextUnit;
	
	private BigDecimal defaultFirstWeight;
	
	private BigDecimal defaultNextWeight;

	private BigDecimal defaultFirstPrice;
	
	private BigDecimal defaultNextPrice;
	
	private List<ProductFreightRegionDTO> freghtRegionList;
}
