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
 * The persistent class for the swiper database table.
 * 
 */
@Entity
@Table(name = "swiper")
@NamedQuery(name = "SwiperEntity.findAll", query = "SELECT u FROM SwiperEntity u")
public class SwiperEntity extends AbstractBaseEntity
{
	public String getSwiperUuid() {
		return swiperUuid;
	}

	public void setSwiperUuid(String swiperUuid) {
		this.swiperUuid = swiperUuid;
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
	@GeneratedValue(generator = "SWIPER_UUID")
	@GenericGenerator(name = "SWIPER_UUID", strategy = "uuid")
	@Column(name = "SWIPER_UUID", nullable = false, length = 36)
	private String swiperUuid;

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	@Column(name = "URL")
	private String url;
	
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