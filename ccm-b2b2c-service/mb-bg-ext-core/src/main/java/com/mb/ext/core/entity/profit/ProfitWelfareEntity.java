package com.mb.ext.core.entity.profit;

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

import com.mb.ext.core.entity.coupon.CouponEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import org.hibernate.annotations.GenericGenerator;

import com.mb.ext.core.entity.UserLevelEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the profit_welfare database table.
 * 
 */
@Entity
@Table(name = "profit_welfare")
@NamedQuery(name = "ProfitWelfareEntity.findAll", query = "SELECT u FROM ProfitWelfareEntity u")
public class ProfitWelfareEntity extends AbstractBaseEntity
{

	@Id
	@GeneratedValue(generator = "PROFIT_WELFARE_UUID")
	@GenericGenerator(name = "PROFIT_WELFARE_UUID", strategy = "uuid")
	@Column(name = "PROFIT_WELFARE_UUID", nullable = false, length = 36)
	private String profitWelfareUuid;

	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUPON_UUID")
	private CouponEntity couponEntity;

	@Column(name = "COUPON_COUNT")
	private int couponCount;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UUID")
	private ProductEntity productEntity;

	@Column(name = "PRODUCT_UNIT_PRICE")
	private BigDecimal productUnitPrice;
	
	@Column(name = "WELFARE_TYPE")
	private String welfareType;

	@Column(name = "POINT_GIVEN")
	private int pointGiven;

	public String getProfitWelfareUuid() {
		return profitWelfareUuid;
	}

	public void setProfitWelfareUuid(String profitWelfareUuid) {
		this.profitWelfareUuid = profitWelfareUuid;
	}

	public CouponEntity getCouponEntity() {
		return couponEntity;
	}

	public void setCouponEntity(CouponEntity couponEntity) {
		this.couponEntity = couponEntity;
	}

	public int getCouponCount() {
		return couponCount;
	}

	public void setCouponCount(int couponCount) {
		this.couponCount = couponCount;
	}

	public ProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(ProductEntity productEntity) {
		this.productEntity = productEntity;
	}

	public BigDecimal getProductUnitPrice() {
		return productUnitPrice;
	}

	public void setProductUnitPrice(BigDecimal productUnitPrice) {
		this.productUnitPrice = productUnitPrice;
	}

	public String getWelfareType() {
		return welfareType;
	}

	public void setWelfareType(String welfareType) {
		this.welfareType = welfareType;
	}

	public int getPointGiven() {
		return pointGiven;
	}

	public void setPointGiven(int pointGiven) {
		this.pointGiven = pointGiven;
	}

	public boolean isPointEnabled() {
		return pointEnabled;
	}

	public void setPointEnabled(boolean pointEnabled) {
		this.pointEnabled = pointEnabled;
	}

	public boolean isCouponEnabled() {
		return couponEnabled;
	}

	public void setCouponEnabled(boolean couponEnabled) {
		this.couponEnabled = couponEnabled;
	}

	public boolean isProductEnabled() {
		return productEnabled;
	}

	public void setProductEnabled(boolean productEnabled) {
		this.productEnabled = productEnabled;
	}

	@Column(name = "POINT_ENABLED")
	private boolean pointEnabled;

	@Column(name = "COUPON_ENABLED")
	private boolean couponEnabled;

	@Column(name = "PRODUCT_ENABLED")
	private boolean productEnabled;
	
}