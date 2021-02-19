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
 * The persistent class for the product_attr_value database table.
 * 
 */
@Entity
@Table(name = "product_attr_value")
@NamedQuery(name = "ProductAttrValueEntity.findAll", query = "SELECT u FROM ProductAttrValueEntity u")
public class ProductAttrValueEntity extends AbstractBaseEntity
{

	public String getProductAttrValueUuid() {
		return productAttrValueUuid;
	}

	public void setProductAttrValueUuid(String productAttrValueUuid) {
		this.productAttrValueUuid = productAttrValueUuid;
	}

	public String getProductAttrName() {
		return productAttrName;
	}

	public void setProductAttrName(String productAttrName) {
		this.productAttrName = productAttrName;
	}

	public String getProductAttrValue() {
		return productAttrValue;
	}

	public void setProductAttrValue(String productAttrValue) {
		this.productAttrValue = productAttrValue;
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
	@GeneratedValue(generator = "PRODUCTATTRVALUE_UUID")
	@GenericGenerator(name = "PRODUCTATTRVALUE_UUID", strategy = "uuid")
	@Column(name = "PRODUCTATTRVALUE_UUID", nullable = false, length = 36)
	private String productAttrValueUuid;
	//产品属性名称
	@Column(name = "PRODUCT_ATTR_NAME", length = 45)
	private String productAttrName;
	//产品属性值
	@Column(name = "PRODUCT_ATTR_VALUE", length = 100)
	private String productAttrValue;
	
	//该属性是否继承自类目
	@Column(name = "IS_CATE_ATTR")
	private boolean isCateAttr;

	//类目外键
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UUID")
	private ProductEntity productEntity;
	
	//当IS_CATE_ATTR为true时, 表明该属性继承自类目, 外键关联到类目属性
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTCATEATTR_UUID")
	private ProductCateAttrEntity productCateAttrEntity;
	
}