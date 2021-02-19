package com.mb.ext.core.entity.group;

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

import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * 秒杀商品表
 * 
 */
@Entity
@Table(name = "group_buy_product")
@NamedQuery(name = "GroupBuyProductEntity.findAll", query = "SELECT u FROM GroupBuyProductEntity u")
public class GroupBuyProductEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 2233062138920658290L;

	@Id
	@GeneratedValue(generator = "GROUP_BUY_PRODUCT_UUID")
	@GenericGenerator(name = "GROUP_BUY_PRODUCT_UUID", strategy = "uuid")
	@Column(name = "GROUP_BUY_PRODUCT_UUID", nullable = false, length = 36)
	private String groupBuyProductUuid;

	public String getgroupBuyProductUuid() {
		return groupBuyProductUuid;
	}

	public void setgroupBuyProductUuid(String groupBuyProductUuid) {
		this.groupBuyProductUuid = groupBuyProductUuid;
	}

	public ProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(ProductEntity productEntity) {
		this.productEntity = productEntity;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}


	//商品
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UUID")
	private ProductEntity productEntity;
	
	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_BUY_DEF_UUID")
	private GroupBuyDefEntity groupBuyDefEntity;
	
	public GroupBuyDefEntity getGroupBuyDefEntity() {
		return groupBuyDefEntity;
	}

	public void setGroupBuyDefEntity(GroupBuyDefEntity groupBuyDefEntity) {
		this.groupBuyDefEntity = groupBuyDefEntity;
	}

	public String getGroupBuyProductUuid() {
		return groupBuyProductUuid;
	}

	public void setGroupBuyProductUuid(String groupBuyProductUuid) {
		this.groupBuyProductUuid = groupBuyProductUuid;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Column(name = "STOCK")
	private int stock;
	
	public int getSoldUnit() {
		return soldUnit;
	}

	public void setSoldUnit(int soldUnit) {
		this.soldUnit = soldUnit;
	}


	@Column(name = "SOLD_UNIT")
	private int soldUnit;
	
	@Column(name = "UNIT_PRICE")
	private BigDecimal unitPrice;
	
}