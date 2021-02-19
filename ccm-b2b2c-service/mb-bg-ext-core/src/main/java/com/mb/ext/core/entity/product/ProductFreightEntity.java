package com.mb.ext.core.entity.product;

import java.math.BigDecimal;
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
 * The persistent class for the product_freight database table.
 * 
 */
@Entity
@Table(name = "product_freight")
@NamedQuery(name = "ProductFreightEntity.findAll", query = "SELECT u FROM ProductFreightEntity u")
public class ProductFreightEntity extends AbstractBaseEntity
{
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PRODUCTFREIGHT_UUID")
	@GenericGenerator(name = "PRODUCTFREIGHT_UUID", strategy = "uuid")
	@Column(name = "PRODUCTFREIGHT_UUID", nullable = false, length = 36)
	private String productFreightUuid;

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
	
	@Column(name = "CHARGETYPE")
	private int chargeType;
	
	@Column(name = "COURIER_NAME")
	private String courierName;
	
	@Column(name = "IS_SHIPPING_FREE")
	private boolean isShippingFree;
	
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

	public List<ProductFreightRegionEntity> getFreghtRegionList() {
		return freghtRegionList;
	}

	public void setFreghtRegionList(List<ProductFreightRegionEntity> freghtRegionList) {
		this.freghtRegionList = freghtRegionList;
	}

	@Column(name = "IS_ACTIVE")
	private boolean isActive;
	
	@Column(name = "IS_DEFAULT")
	private boolean isDefault;
	
	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Column(name = "SHIPPING_FREE_CONDITION_AMOUNT")
	private BigDecimal shippingFreeConditionAmount;
	
	@Column(name = "DEFAULT_FIRST_UNIT")
	private int defaultFirstUnit;
	
	@Column(name = "DEFAULT_NEXT_UNIT")
	private int defaultNextUnit;
	
	@Column(name = "DEFAULT_FIRST_WEIGHT")
	private BigDecimal defaultFirstWeight;
	
	@Column(name = "DEFAULT_NEXT_WEIGHT")
	private BigDecimal defaultNextWeight;

	@Column(name = "DEFAULT_FIRST_PRICE")
	private BigDecimal defaultFirstPrice;
	
	@Column(name = "DEFAULT_NEXT_PRICE")
	private BigDecimal defaultNextPrice;
	
	public int getChargeType() {
		return chargeType;
	}

	public void setChargeType(int chargeType) {
		this.chargeType = chargeType;
	}

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
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productFreightEntity")
	private List<ProductFreightRegionEntity> freghtRegionList;

}