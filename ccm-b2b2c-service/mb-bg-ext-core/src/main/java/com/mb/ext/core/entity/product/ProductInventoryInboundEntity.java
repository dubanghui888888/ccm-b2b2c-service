package com.mb.ext.core.entity.product;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the product_inventory_inbound database table.
 * 
 */
@Entity
@Table(name = "product_inventory_inbound")
@NamedQuery(name = "ProductInventoryInboundEntity.findAll", query = "SELECT u FROM ProductInventoryInboundEntity u")
public class ProductInventoryInboundEntity extends AbstractBaseEntity
{
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PRODUCT_INVENTORY_INBOUND_UUID")
	@GenericGenerator(name = "PRODUCT_INVENTORY_INBOUND_UUID", strategy = "uuid")
	@Column(name = "PRODUCT_INVENTORY_INBOUND_UUID", nullable = false, length = 36)
	private String productInventoryInboundUuid;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UUID")
	private ProductEntity productEntity;
	
	public String getProductInventoryInboundUuid() {
		return productInventoryInboundUuid;
	}

	public void setProductInventoryInboundUuid(String productInventoryInboundUuid) {
		this.productInventoryInboundUuid = productInventoryInboundUuid;
	}

	public ProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(ProductEntity productEntity) {
		this.productEntity = productEntity;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_SKU_UUID")
	private ProductSkuEntity productSkuEntity;
	
	@Column(name = "TRAN_UNIT")
	private int tranUnit;
	
	@Column(name = "TRAN_TIME")
	private Date tranTime;
	
	@Column(name = "MEMO")
	private String memo;
	
	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	@Column(name = "SKU_NAME")
	private String skuName;
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public ProductSkuEntity getProductSkuEntity() {
		return productSkuEntity;
	}

	public void setProductSkuEntity(ProductSkuEntity productSkuEntity) {
		this.productSkuEntity = productSkuEntity;
	}

	public int getTranUnit() {
		return tranUnit;
	}

	public void setTranUnit(int tranUnit) {
		this.tranUnit = tranUnit;
	}

	public Date getTranTime() {
		return tranTime;
	}

	public void setTranTime(Date tranTime) {
		this.tranTime = tranTime;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	@Column(name = "TRAN_TYPE")
	private String tranType;

}