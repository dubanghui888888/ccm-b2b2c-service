package com.mb.ext.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.ext.core.entity.product.ProductCateEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the ad database table.
 * 
 */
@Entity
@Table(name = "ad")
@NamedQuery(name = "AdEntity.findAll", query = "SELECT u FROM AdEntity u")
public class AdEntity extends AbstractBaseEntity
{
	public String getAdUuid() {
		return adUuid;
	}

	public void setAdUuid(String adUuid) {
		this.adUuid = adUuid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private static final long serialVersionUID = 1L;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Id
	@GeneratedValue(generator = "AD_UUID")
	@GenericGenerator(name = "AD_UUID", strategy = "uuid")
	@Column(name = "AD_UUID", nullable = false, length = 36)
	private String adUuid;

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		this.isActive = active;
	}

	@Column(name = "URL")
	private String url;
	
	@Column(name = "ISACTIVE")
	private boolean isActive;
	
	@Column(name = "LINK_TYPE")
	private String linkType;
	
	@Column(name = "CONTENT")
	private String content;
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "LOCATION")
	private String location;
	
	//
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTCATE_UUID")
	private ProductCateEntity productCateEntity;

	public ProductCateEntity getProductCateEntity() {
		return productCateEntity;
	}

	public void setProductCateEntity(ProductCateEntity productCateEntity) {
		this.productCateEntity = productCateEntity;
	}
}