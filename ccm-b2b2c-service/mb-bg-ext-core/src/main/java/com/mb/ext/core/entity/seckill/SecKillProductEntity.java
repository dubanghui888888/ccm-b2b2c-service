package com.mb.ext.core.entity.seckill;

import java.math.BigDecimal;
import java.util.Date;

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
@Table(name = "sec_kill_product")
@NamedQuery(name = "SecKillProductEntity.findAll", query = "SELECT u FROM SecKillProductEntity u")
public class SecKillProductEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 2233062138920658290L;

	@Id
	@GeneratedValue(generator = "SEC_KILL_PRODUCT_UUID")
	@GenericGenerator(name = "SEC_KILL_PRODUCT_UUID", strategy = "uuid")
	@Column(name = "SEC_KILL_PRODUCT_UUID", nullable = false, length = 36)
	private String secKillProductUuid;

	public String getSecKillProductUuid() {
		return secKillProductUuid;
	}

	public void setSecKillProductUuid(String secKillProductUuid) {
		this.secKillProductUuid = secKillProductUuid;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	//商品
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UUID")
	private ProductEntity productEntity;
	
	@Column(name = "STOCK")
	private int stock;
	
	@Column(name = "SOLD_UNIT")
	private int soldUnit;
	
	public int getSoldUnit() {
		return soldUnit;
	}

	public void setSoldUnit(int soldUnit) {
		this.soldUnit = soldUnit;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Column(name = "UNIT_PRICE")
	private BigDecimal unitPrice;
	
	@Column(name = "STATUS")
	private String status;
	
	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	@Column(name = "START_TIME")
	private Date startTime;
	
	@Column(name = "END_TIME")
	private Date endTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;
	
}