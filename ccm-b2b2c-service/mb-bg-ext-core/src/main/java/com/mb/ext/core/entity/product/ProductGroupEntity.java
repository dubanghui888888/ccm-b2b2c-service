package com.mb.ext.core.entity.product;

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

import com.mb.ext.core.entity.GroupEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the product_group database table.
 * 
 */
@Entity
@Table(name = "product_group")
@NamedQuery(name = "ProductGroupEntity.findAll", query = "SELECT u FROM ProductGroupEntity u")
public class ProductGroupEntity extends AbstractBaseEntity
{
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PRODUCTGROUP_UUID")
	@GenericGenerator(name = "PRODUCTGROUP_UUID", strategy = "uuid")
	@Column(name = "PRODUCTGROUP_UUID", nullable = false, length = 36)
	private String productGroupUuid;
	
	public String getProductGroupUuid() {
		return productGroupUuid;
	}

	public void setProductGroupUuid(String productGroupUuid) {
		this.productGroupUuid = productGroupUuid;
	}

	public ProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(ProductEntity productEntity) {
		this.productEntity = productEntity;
	}

	public GroupEntity getGroupEntity() {
		return groupEntity;
	}

	public void setGroupEntity(GroupEntity groupEntity) {
		this.groupEntity = groupEntity;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UUID")
	private ProductEntity productEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_UUID")
	private GroupEntity groupEntity;
	
}