package com.mb.ext.core.entity.coupon;

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

import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the coupon_product database table.
 * 
 */
@Entity
@Table(name = "coupon_product")
@NamedQuery(name = "CouponProductEntity.findAll", query = "SELECT u FROM CouponProductEntity u")
public class CouponProductEntity extends AbstractBaseEntity
{


	public String getCouponProductUuid() {
		return couponProductUuid;
	}

	public void setCouponProductUuid(String couponProductUuid) {
		this.couponProductUuid = couponProductUuid;
	}

	public ProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(ProductEntity productEntity) {
		this.productEntity = productEntity;
	}

	public CouponEntity getCouponEntity() {
		return couponEntity;
	}

	public void setCouponEntity(CouponEntity couponEntity) {
		this.couponEntity = couponEntity;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "COUPONPRODUCT_UUID")
	@GenericGenerator(name = "COUPONPRODUCT_UUID", strategy = "uuid")
	@Column(name = "COUPONPRODUCT_UUID", nullable = false, length = 36)
	private String couponProductUuid;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UUID")
	private ProductEntity productEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUPON_UUID")
	private CouponEntity couponEntity;

	
}