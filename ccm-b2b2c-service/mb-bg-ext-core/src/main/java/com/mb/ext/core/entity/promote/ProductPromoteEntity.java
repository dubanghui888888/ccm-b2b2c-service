package com.mb.ext.core.entity.promote;

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

import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.supplier.SupplierEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the product_promote database table.
 * 
 */
@Entity
@Table(name = "product_promote")
@NamedQuery(name = "ProductPromoteEntity.findAll", query = "SELECT u FROM ProductPromoteEntity u")
public class ProductPromoteEntity extends AbstractBaseEntity
{
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PRODUCTPROMOTE_UUID")
	@GenericGenerator(name = "PRODUCTPROMOTE_UUID", strategy = "uuid")
	@Column(name = "PRODUCTPROMOTE_UUID", nullable = false, length = 36)
	private String productPromoteUuid;

	@Column(name = "PROMOTE_TYPE")
	private String promoteType;
	
	@Column(name = "PROMOTE_UUID", length = 36)
	private String promoteUuid;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPPLIER_UUID")
	private SupplierEntity supplierEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UUID")
	private ProductEntity productEntity;
	

	public String getPromoteType() {
		return promoteType;
	}

	public void setPromoteType(String promoteType) {
		this.promoteType = promoteType;
	}

	public String getPromoteUuid() {
		return promoteUuid;
	}

	public void setPromoteUuid(String promoteUuid) {
		this.promoteUuid = promoteUuid;
	}

	public ProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(ProductEntity productEntity) {
		this.productEntity = productEntity;
	}

	public SupplierEntity getSupplierEntity() {
		return supplierEntity;
	}

	public void setSupplierEntity(SupplierEntity supplierEntity) {
		this.supplierEntity = supplierEntity;
	}

	public String getProductPromoteUuid() {
		return productPromoteUuid;
	}

	public void setProductPromoteUuid(String productPromoteUuid) {
		this.productPromoteUuid = productPromoteUuid;
	}

}