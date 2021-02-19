package com.mb.ext.core.entity.point;

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
 * The persistent class for the point_product_attr_value database table.
 * 
 */
@Entity
@Table(name = "point_product_attr_value")
@NamedQuery(name = "PointProductAttrValueEntity.findAll", query = "SELECT u FROM PointProductAttrValueEntity u")
public class PointProductAttrValueEntity extends AbstractBaseEntity
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

	public PointProductCateAttrEntity getProductCateAttrEntity() {
		return productCateAttrEntity;
	}

	public void setProductCateAttrEntity(PointProductCateAttrEntity productCateAttrEntity) {
		this.productCateAttrEntity = productCateAttrEntity;
	}

	public PointProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(PointProductEntity productEntity) {
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
	private PointProductEntity productEntity;
	
	//当IS_CATE_ATTR为true时, 表明该属性继承自类目, 外键关联到类目属性
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTCATEATTR_UUID")
	private PointProductCateAttrEntity productCateAttrEntity;
	
}