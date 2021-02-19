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

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the point_product_comment_image database table.
 * 
 */
@Entity
@Table(name = "point_product_comment_image")
@NamedQuery(name = "PointProductCommentImageEntity.findAll", query = "SELECT u FROM PointProductCommentImageEntity u")
public class PointProductCommentImageEntity extends AbstractBaseEntity
{
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PRODUCTCOMMENTIMAGE_UUID")
	@GenericGenerator(name = "PRODUCTCOMMENTIMAGE_UUID", strategy = "uuid")
	@Column(name = "PRODUCTCOMMENTIMAGE_UUID", nullable = false, length = 36)
	private String productCommentImageUuid;

	@Column(name = "IMAGEURL", length = 200)
	private String imageUrl;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UUID")
	private PointProductEntity productEntity;
	
	public String getProductCommentImageUuid() {
		return productCommentImageUuid;
	}

	public void setProductCommentImageUuid(String productCommentImageUuid) {
		this.productCommentImageUuid = productCommentImageUuid;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public PointProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(PointProductEntity productEntity) {
		this.productEntity = productEntity;
	}

	public PointProductCommentEntity getProductCommentEntity() {
		return productCommentEntity;
	}

	public void setProductCommentEntity(PointProductCommentEntity productCommentEntity) {
		this.productCommentEntity = productCommentEntity;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTCOMMENT_UUID")
	private PointProductCommentEntity productCommentEntity;
	
}