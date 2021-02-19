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
 * The persistent class for the product_sku_attr_value database table.
 * 
 */
@Entity
@Table(name = "product_sku_attr_value")
@NamedQuery(name = "ProductSkuAttrValueEntity.findAll", query = "SELECT u FROM ProductSkuAttrValueEntity u")
public class ProductSkuAttrValueEntity extends AbstractBaseEntity
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

	public ProductSkuAttrEntity getProductSkuAttrEntity() {
		return productSkuAttrEntity;
	}

	public void setProductSkuAttrEntity(ProductSkuAttrEntity productSkuAttrEntity) {
		this.productSkuAttrEntity = productSkuAttrEntity;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTSKUATTR_UUID")
	private ProductSkuAttrEntity productSkuAttrEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UUID")
	private ProductEntity productEntity;

	public ProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(ProductEntity productEntity) {
		this.productEntity = productEntity;
	}
	
}