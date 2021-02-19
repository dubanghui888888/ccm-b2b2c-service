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
 * The persistent class for the point_product_video database table.
 * 
 */
@Entity
@Table(name = "point_product_video")
@NamedQuery(name = "PointProductVideoEntity.findAll", query = "SELECT u FROM PointProductVideoEntity u")
public class PointProductVideoEntity extends AbstractBaseEntity
{
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PRODUCTVIDEO_UUID")
	@GenericGenerator(name = "PRODUCTVIDEO_UUID", strategy = "uuid")
	@Column(name = "PRODUCTVIDEO_UUID", nullable = false, length = 36)
	private String productVideoUuid;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UUID")
	private PointProductEntity productEntity;
	
	public String getProductVideoUuid() {
		return productVideoUuid;
	}

	public void setProductVideoUuid(String productVideoUuid) {
		this.productVideoUuid = productVideoUuid;
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