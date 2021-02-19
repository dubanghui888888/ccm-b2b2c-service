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

import com.mb.ext.core.entity.FileEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the point_product_image database table.
 * 
 */
@Entity
@Table(name = "point_product_image")
@NamedQuery(name = "PointProductImageEntity.findAll", query = "SELECT u FROM PointProductImageEntity u")
public class PointProductImageEntity extends AbstractBaseEntity
{
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PRODUCTIMAGE_UUID")
	@GenericGenerator(name = "PRODUCTIMAGE_UUID", strategy = "uuid")
	@Column(name = "PRODUCTIMAGE_UUID", nullable = false, length = 36)
	private String productImageUuid;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UUID")
	private PointProductEntity productEntity;
	
	public String getProductImageUuid() {
		return productImageUuid;
	}

	public void setProductImageUuid(String productImageUuid) {
		this.productImageUuid = productImageUuid;
	}

	public PointProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(PointProductEntity productEntity) {
		this.productEntity = productEntity;
	}

	public FileEntity getFileEntity() {
		return fileEntity;
	}

	public void setFileEntity(FileEntity fileEntity) {
		this.fileEntity = fileEntity;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FILE_UUID")
	private FileEntity fileEntity;

}