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

import com.mb.ext.core.entity.AreaEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the product_freight_region database table.
 * 
 */
@Entity
@Table(name = "product_freight_region")
@NamedQuery(name = "ProductFreightRegionEntity.findAll", query = "SELECT u FROM ProductFreightRegionEntity u")
public class ProductFreightRegionEntity extends AbstractBaseEntity
{
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PRODUCTFREIGHTREGION_UUID")
	@GenericGenerator(name = "PRODUCTFREIGHTREGION_UUID", strategy = "uuid")
	@Column(name = "PRODUCTFREIGHTREGION_UUID", nullable = false, length = 36)
	private String productFreightRegionUuid;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTGREIGHT_UUID")
	private ProductFreightEntity productFreightEntity;
	
	public BigDecimal getFirstWeight() {
		return firstWeight;
	}

	public void setFirstWeight(BigDecimal firstWeight) {
		this.firstWeight = firstWeight;
	}

	public String getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}

	public BigDecimal getNextWeight() {
		return nextWeight;
	}

	public void setNextWeight(BigDecimal nextWeight) {
		this.nextWeight = nextWeight;
	}

	@Column(name = "AREA_ID")
	private String areaIds;
	
	@Column(name = "FIRST_UNIT")
	private int firstUnit;
	
	@Column(name = "NEXT_UNIT")
	private int nextUnit;
	
	@Column(name = "FIRST_WEIGHT")
	private BigDecimal firstWeight;
	
	@Column(name = "NEXT_WEIGHT")
	private BigDecimal nextWeight;

	@Column(name = "FIRST_PRICE")
	private BigDecimal firstPrice;
	
	
	
	public BigDecimal getNextPrice() {
		return nextPrice;
	}

	public void setNextPrice(BigDecimal nextPrice) {
		this.nextPrice = nextPrice;
	}

	@Column(name = "NEXT_PRICE")
	private BigDecimal nextPrice;
	
	public String getProductFreightRegionUuid() {
		return productFreightRegionUuid;
	}

	public void setProductFreightRegionUuid(String productFreightRegionUuid) {
		this.productFreightRegionUuid = productFreightRegionUuid;
	}

	public int getFirstUnit() {
		return firstUnit;
	}

	public void setFirstUnit(int firstUnit) {
		this.firstUnit = firstUnit;
	}

	public BigDecimal getFirstPrice() {
		return firstPrice;
	}

	public void setFirstPrice(BigDecimal firstPrice) {
		this.firstPrice = firstPrice;
	}

	public int getNextUnit() {
		return nextUnit;
	}

	public void setNextUnit(int nextUnit) {
		this.nextUnit = nextUnit;
	}

	public ProductFreightEntity getProductFreightEntity() {
		return productFreightEntity;
	}

	public void setProductFreightEntity(ProductFreightEntity productFreightEntity) {
		this.productFreightEntity = productFreightEntity;
	}

}