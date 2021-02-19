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

import com.mb.ext.core.entity.UserEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the product_collect database table.
 * 
 */
@Entity
@Table(name = "product_collect")
@NamedQuery(name = "ProductCollectEntity.findAll", query = "SELECT u FROM ProductCollectEntity u")
public class ProductCollectEntity extends AbstractBaseEntity
{
	

	private static final long serialVersionUID = 1L;

	public String getProductCollectUuid() {
		return productCollectUuid;
	}

	public void setProductCollectUuid(String productCollectUuid) {
		this.productCollectUuid = productCollectUuid;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public ProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(ProductEntity productEntity) {
		this.productEntity = productEntity;
	}

	@Id
	@GeneratedValue(generator = "PRODUCT_COLLECT_UUID")
	@GenericGenerator(name = "PRODUCT_COLLECT_UUID", strategy = "uuid")
	@Column(name = "PRODUCT_COLLECT_UUID", nullable = false, length = 36)
	private String productCollectUuid;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UUID")
	private ProductEntity productEntity;
}