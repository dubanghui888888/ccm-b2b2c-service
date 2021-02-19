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

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the product_sku_attr database table.
 * 
 */
@Entity
@Table(name = "product_sku_attr")
@NamedQuery(name = "ProductSkuAttrEntity.findAll", query = "SELECT u FROM ProductSkuAttrEntity u")
public class ProductSkuAttrEntity extends AbstractBaseEntity
{
	
	public String getProductSkuAttrUuid() {
		return productSkuAttrUuid;
	}

	public void setProductSkuAttrUuid(String productSkuAttrUuid) {
		this.productSkuAttrUuid = productSkuAttrUuid;
	}

	public String getSkuAttrName() {
		return skuAttrName;
	}

	public void setSkuAttrName(String skuAttrName) {
		this.skuAttrName = skuAttrName;
	}

	public boolean isCateAttr() {
		return isCateAttr;
	}

	public void setCateAttr(boolean isCateAttr) {
		this.isCateAttr = isCateAttr;
	}

	public ProductCateAttrEntity getProductCateAttrEntity() {
		return productCateAttrEntity;
	}

	public void setProductCateAttrEntity(ProductCateAttrEntity productCateAttrEntity) {
		this.productCateAttrEntity = productCateAttrEntity;
	}

	public ProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(ProductEntity productEntity) {
		this.productEntity = productEntity;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PRODUCTSKUATTR_UUID")
	@GenericGenerator(name = "PRODUCTSKUATTR_UUID", strategy = "uuid")
	@Column(name = "PRODUCTSKUATTR_UUID", nullable = false, length = 36)
	private String productSkuAttrUuid;
	//SKU属性名称
	@Column(name = "PRODUCT_ATTR_NAME", length = 45)
	private String skuAttrName;

	//该属性是否继承自类目
	@Column(name = "IS_CATE_ATTR")
	private boolean isCateAttr;

	//类目外键
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UUID")
	private ProductEntity productEntity;
	
	//当IS_CATE_SKUATTR为true时, 表明该属性继承自类目, 外键关联到类目属性
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTCATEATTR_UUID")
	private ProductCateAttrEntity productCateAttrEntity;
	
}