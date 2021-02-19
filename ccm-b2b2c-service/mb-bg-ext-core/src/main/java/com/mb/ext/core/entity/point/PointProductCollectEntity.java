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

import com.mb.ext.core.entity.UserEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the point_product_collect database table.
 * 
 */
@Entity
@Table(name = "point_product_collect")
@NamedQuery(name = "PointProductCollectEntity.findAll", query = "SELECT u FROM PointProductCollectEntity u")
public class PointProductCollectEntity extends AbstractBaseEntity
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

	public PointProductEntity getPointProductEntity() {
		return productEntity;
	}

	public void setPointProductEntity(PointProductEntity productEntity) {
		this.productEntity = productEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
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
	private PointProductEntity productEntity;
}