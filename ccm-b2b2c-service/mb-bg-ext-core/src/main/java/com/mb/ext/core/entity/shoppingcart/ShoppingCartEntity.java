package com.mb.ext.core.entity.shoppingcart;

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

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductSkuEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the shopping_cart database table.
 * 
 */
@Entity
@Table(name = "shopping_cart")
@NamedQuery(name = "ShoppingCartEntity.findAll", query = "SELECT u FROM ShoppingCartEntity u")
public class ShoppingCartEntity extends AbstractBaseEntity
{
	
	public String getShoppingCartUuid() {
		return shoppingCartUuid;
	}

	public void setShoppingCartUuid(String shoppingCartUuid) {
		this.shoppingCartUuid = shoppingCartUuid;
	}

	public ProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(ProductEntity productEntity) {
		this.productEntity = productEntity;
	}

	private static final long serialVersionUID = 1L;

	public ProductSkuEntity getProductSkuEntity() {
		return productSkuEntity;
	}

	public void setProductSkuEntity(ProductSkuEntity productSkuEntity) {
		this.productSkuEntity = productSkuEntity;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	@Id
	@GeneratedValue(generator = "SHOPPINGCART_UUID")
	@GenericGenerator(name = "SHOPPINGCART_UUID", strategy = "uuid")
	@Column(name = "SHOPPINGCART_UUID", nullable = false, length = 36)
	private String shoppingCartUuid;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UUID")
	private ProductEntity productEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTSKU_UUID")
	private ProductSkuEntity productSkuEntity;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;
	
	@Column(name = "UNIT")
	private int unit;

}