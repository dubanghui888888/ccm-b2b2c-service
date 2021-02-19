package com.mb.ext.core.entity.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the product_brand database table.
 * 
 */
@Entity
@Table(name = "product_brand")
@NamedQuery(name = "ProductBrandEntity.findAll", query = "SELECT u FROM ProductBrandEntity u")
public class ProductBrandEntity extends AbstractBaseEntity
{
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PRODUCTBRAND_UUID")
	@GenericGenerator(name = "PRODUCTBRAND_UUID", strategy = "uuid")
	@Column(name = "PRODUCTBRAND_UUID", nullable = false, length = 36)
	private String productBrandUuid;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "NAME", nullable = false, length = 40)
	private String name;
	
	@Column(name = "DESCRIPTION", length = 100)
	private String description;
	
	@Column(name = "LOGOURL", length = 500)
	private String logoUrl;
	
	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public int getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(int sortNumber) {
		this.sortNumber = sortNumber;
	}

	@Column(name = "SORTNUMBER")
	private int sortNumber;
	

	public String getProductBrandUuid() {
		return productBrandUuid;
	}

	public void setProductBrandUuid(String productBrandUuid) {
		this.productBrandUuid = productBrandUuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}