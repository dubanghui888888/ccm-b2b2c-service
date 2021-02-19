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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.ext.core.entity.FileEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the product_sku database table.
 * 
 */
@Entity
@Table(name = "product_sku")
@NamedQuery(name = "ProductSkuEntity.findAll", query = "SELECT u FROM ProductSkuEntity u")
public class ProductSkuEntity extends AbstractBaseEntity
{
	

	public String getProductSkuUuid() {
		return productSkuUuid;
	}

	public void setProductSkuUuid(String productSkuUuid) {
		this.productSkuUuid = productSkuUuid;
	}

	public ProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(ProductEntity productEntity) {
		this.productEntity = productEntity;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PRODUCTSKU_UUID")
	@GenericGenerator(name = "PRODUCTSKU_UUID", strategy = "uuid")
	@Column(name = "PRODUCTSKU_UUID", nullable = false, length = 36)
	private String productSkuUuid;
	//SKU(规格)编号
	@Column(name = "SKU_CODE", length = 45)
	private String skuCode;
	
	//SKU属性组合, 格式为[属性值1,属性值2], 如[1023,1025], 对应颜色为红色, 大小为M
	@Column(name = "PRODUCT_SKU_ATTR_VALUE_UUIDS", length = 500)
	private String skuAttrValueUuids;
	
	public String getSkuAttrValueUuids() {
		return skuAttrValueUuids;
	}

	public void setSkuAttrValueUuids(String skuAttrValueUuids) {
		this.skuAttrValueUuids = skuAttrValueUuids;
	}

	
	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public int getSkuTotalUnit() {
		return skuTotalUnit;
	}

	public void setSkuTotalUnit(int skuTotalUnit) {
		this.skuTotalUnit = skuTotalUnit;
	}

	public int getSkuWarnUnit() {
		return skuWarnUnit;
	}

	public void setSkuWarnUnit(int skuWarnUnit) {
		this.skuWarnUnit = skuWarnUnit;
	}

	public int getSkuSoldUnit() {
		return skuSoldUnit;
	}

	public void setSkuSoldUnit(int skuSoldUnit) {
		this.skuSoldUnit = skuSoldUnit;
	}

	//SKU购买需要积分数
	@Column(name = "SKU_UNIT_POINT")
	private int skuUnitPoint;
	
	@Column(name = "SKU_UNIT_PRICE")
	private BigDecimal skuUnitPrice;
	
	public BigDecimal getSkuUnitPrice() {
		return skuUnitPrice;
	}

	public void setSkuUnitPrice(BigDecimal skuUnitPrice) {
		this.skuUnitPrice = skuUnitPrice;
	}

	public BigDecimal getSkuUnitPriceStandard() {
		return skuUnitPriceStandard;
	}

	public void setSkuUnitPriceStandard(BigDecimal skuUnitPriceStandard) {
		this.skuUnitPriceStandard = skuUnitPriceStandard;
	}

	//SKU购买需要积分数(会员)
	@Column(name = "SKU_UNIT_POINT_STANDARD")
	private int skuUnitPointStandard;
	
	@Column(name = "SKU_UNIT_PRICE_STANDARD")
	private BigDecimal skuUnitPriceStandard;
	

	//SKU总库存数量
	@Column(name = "SKU_TOTAL_UNIT")
	private int skuTotalUnit;
	
	public int getSkuUnitPoint() {
		return skuUnitPoint;
	}

	public void setSkuUnitPoint(int skuUnitPoint) {
		this.skuUnitPoint = skuUnitPoint;
	}

	public int getSkuUnitPointStandard() {
		return skuUnitPointStandard;
	}

	public void setSkuUnitPointStandard(int skuUnitPointStandard) {
		this.skuUnitPointStandard = skuUnitPointStandard;
	}

	//SKU 预警库存数量
	@Column(name = "SKU_WARN_UNIT")
	private int skuWarnUnit;
	
	//SKU已售数量
	@Column(name = "SKU_SOLD_UNIT")
	private int skuSoldUnit;
	
	public String getSkuImageUrl() {
		return skuImageUrl;
	}

	public void setSkuImageUrl(String skuImageUrl) {
		this.skuImageUrl = skuImageUrl;
	}

	//SKU商品图片
	@Column(name = "SKU_IMAGE_URL")
	private String skuImageUrl;
	

	//商品外键
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UUID")
	private ProductEntity productEntity;
}