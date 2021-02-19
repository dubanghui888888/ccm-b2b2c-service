package com.mb.ext.core.entity.order;

import java.math.BigDecimal;

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

import com.mb.ext.core.entity.group.GroupBuyProductEntity;
import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.ext.core.entity.point.PointProductSkuEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductSkuEntity;
import com.mb.ext.core.entity.seckill.SecKillProductEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the order_product database table.
 * 
 */
@Entity
@Table(name = "order_product")
@NamedQuery(name = "OrderProductEntity.findAll", query = "SELECT u FROM OrderProductEntity u")
public class OrderProductEntity extends AbstractBaseEntity
{

	public int getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(int productUnit) {
		this.productUnit = productUnit;
	}

	public BigDecimal getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(BigDecimal productAmount) {
		this.productAmount = productAmount;
	}

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ORDER_PRODUCT_UUID")
	@GenericGenerator(name = "ORDER_PRODUCT_UUID", strategy = "uuid")
	@Column(name = "ORDER_PRODUCT_UUID", nullable = false, length = 36)
	private String orderProductUuid;

	@Column(name = "PRODUCT_UNIT_POINT")
	private int productUnitPoint;
	
	@Column(name = "PRODUCT_UNIT_PRICE")
	private BigDecimal productUnitPrice;
	
	public BigDecimal getProductUnitPrice() {
		return productUnitPrice;
	}

	public void setProductUnitPrice(BigDecimal productUnitPrice) {
		this.productUnitPrice = productUnitPrice;
	}

	@Column(name = "PRODUCT_UNIT")
	private int productUnit;
	
	@Column(name = "PRODUCT_CODE")
	private String productCode;
	
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public int getProductUnitPoint() {
		return productUnitPoint;
	}

	public void setProductUnitPoint(int productUnitPoint) {
		this.productUnitPoint = productUnitPoint;
	}

	@Column(name = "PRODUCT_AMOUNT")
	private BigDecimal productAmount;
	
	@Column(name = "PRODUCT_POINT")
	private int productPoint;

	public int getProductPoint() {
		return productPoint;
	}

	public void setProductPoint(int productPoint) {
		this.productPoint = productPoint;
	}

	public int getActualPoint() {
		return actualPoint;
	}

	public void setActualPoint(int actualPoint) {
		this.actualPoint = actualPoint;
	}

	@Column(name = "ACTUAL_AMOUNT")
	private BigDecimal actualAmount;
	
	@Column(name = "ACTUAL_POINT")
	private int actualPoint;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UUID")
	private ProductEntity productEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_BUY_PRODUCT_UUID")
	private GroupBuyProductEntity groupBuyProductEntity;
	
	public GroupBuyProductEntity getGroupBuyProductEntity() {
		return groupBuyProductEntity;
	}

	public void setGroupBuyProductEntity(GroupBuyProductEntity groupBuyProductEntity) {
		this.groupBuyProductEntity = groupBuyProductEntity;
	}

	public SecKillProductEntity getSecKillProductEntity() {
		return secKillProductEntity;
	}

	public void setSecKillProductEntity(SecKillProductEntity secKillProductEntity) {
		this.secKillProductEntity = secKillProductEntity;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SEC_KILL_PRODUCT_UUID")
	private SecKillProductEntity secKillProductEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POINT_PRODUCT_UUID")
	private PointProductEntity pointProductEntity;
	
	public String getOrderProductUuid() {
		return orderProductUuid;
	}

	public void setOrderProductUuid(String orderProductUuid) {
		this.orderProductUuid = orderProductUuid;
	}

	public OrderEntity getOrderEntity() {
		return orderEntity;
	}

	public void setOrderEntity(OrderEntity orderEntity) {
		this.orderEntity = orderEntity;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_UUID")
	private OrderEntity orderEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTSKU_UUID")
	private ProductSkuEntity productSkuEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POINT_PRODUCT_SKU_UUID")
	private PointProductSkuEntity pointProductSkuEntity;
	
	@Column(name = "PRODUCT_NAME")
	private String productName;
	
	public PointProductEntity getPointProductEntity() {
		return pointProductEntity;
	}

	public void setPointProductEntity(PointProductEntity pointProductEntity) {
		this.pointProductEntity = pointProductEntity;
	}

	public PointProductSkuEntity getPointProductSkuEntity() {
		return pointProductSkuEntity;
	}

	public void setPointProductSkuEntity(PointProductSkuEntity pointProductSkuEntity) {
		this.pointProductSkuEntity = pointProductSkuEntity;
	}

	@Column(name = "PRODUCT_IMAGE_URL")
	private String productImageUrl;
	
	@Column(name = "PRODUCT_SKU_DESC")
	private String productSkuDesc;
	
	public ProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(ProductEntity productEntity) {
		this.productEntity = productEntity;
	}

	public ProductSkuEntity getProductSkuEntity() {
		return productSkuEntity;
	}

	public void setProductSkuEntity(ProductSkuEntity productSkuEntity) {
		this.productSkuEntity = productSkuEntity;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductImageUrl() {
		return productImageUrl;
	}

	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}

	public String getProductSkuDesc() {
		return productSkuDesc;
	}

	public void setProductSkuDesc(String productSkuDesc) {
		this.productSkuDesc = productSkuDesc;
	}
	
}