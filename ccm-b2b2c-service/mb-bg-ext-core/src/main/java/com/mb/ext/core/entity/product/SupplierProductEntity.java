package com.mb.ext.core.entity.product;

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

import com.mb.ext.core.entity.supplier.SupplierEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the supplier_product database table.
 * 
 */
@Entity
@Table(name = "supplier_product")
@NamedQuery(name = "SupplierProductEntity.findAll", query = "SELECT u FROM SupplierProductEntity u")
public class SupplierProductEntity extends AbstractBaseEntity
{
	
	private static final long serialVersionUID = 1L;

	public String getSupplierProductUuid() {
		return supplierProductUuid;
	}

	public void setSupplierProductUuid(String supplierProductUuid) {
		this.supplierProductUuid = supplierProductUuid;
	}

	public ProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(ProductEntity productEntity) {
		this.productEntity = productEntity;
	}


	@Id
	@GeneratedValue(generator = "SUPPLIERPRODUCT_UUID")
	@GenericGenerator(name = "SUPPLIERPRODUCT_UUID", strategy = "uuid")
	@Column(name = "SUPPLIERPRODUCT_UUID", nullable = false, length = 36)
	private String supplierProductUuid;


	public SupplierEntity getSupplierEntity() {
		return supplierEntity;
	}

	public void setSupplierEntity(SupplierEntity supplierEntity) {
		this.supplierEntity = supplierEntity;
	}


	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPPLIER_UUID")
	private SupplierEntity supplierEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UUID")
	private ProductEntity productEntity;
	
}