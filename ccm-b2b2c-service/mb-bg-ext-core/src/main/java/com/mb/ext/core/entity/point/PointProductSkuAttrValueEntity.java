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
 * The persistent class for the point_product_sku_attr_value database table.
 * 
 */
@Entity
@Table(name = "point_product_sku_attr_value")
@NamedQuery(name = "PointProductSkuAttrValueEntity.findAll", query = "SELECT u FROM PointProductSkuAttrValueEntity u")
public class PointProductSkuAttrValueEntity extends AbstractBaseEntity
{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PRODUCTSKUATTRVALUE_UUID")
	@GenericGenerator(name = "PRODUCTSKUATTRVALUE_UUID", strategy = "uuid")
	@Column(name = "PRODUCTSKUATTRVALUE_UUID", nullable = false, length = 36)
	private String productSkuAttrValueUuid;
	//SKU属性值
	@Column(name = "PRODUCT_SKU_ATTR_VALUE", length = 45)
	private String skuAttrValue;
	
	//SKU属性名称
	@Column(name = "IMAGE_URL", length = 200)
	private String imageUrl;
	
	public String getProductSkuAttrValueUuid() {
		return productSkuAttrValueUuid;
	}

	public void setProductSkuAttrValueUuid(String productSkuAttrValueUuid) {
		this.productSkuAttrValueUuid = productSkuAttrValueUuid;
	}

	public String getSkuAttrValue() {
		return skuAttrValue;
	}

	public void setSkuAttrValue(String skuAttrValue) {
		this.skuAttrValue = skuAttrValue;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public PointProductSkuAttrEntity getProductSkuAttrEntity() {
		return productSkuAttrEntity;
	}

	public void setProductSkuAttrEntity(PointProductSkuAttrEntity productSkuAttrEntity) {
		this.productSkuAttrEntity = productSkuAttrEntity;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTSKUATTR_UUID")
	private PointProductSkuAttrEntity productSkuAttrEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UUID")
	private PointProductEntity productEntity;

	public PointProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(PointProductEntity productEntity) {
		this.productEntity = productEntity;
	}
	
}