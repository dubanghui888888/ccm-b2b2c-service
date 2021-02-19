package com.mb.ext.core.entity.content;

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

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the share_image database table.
 * 
 */
@Entity
@Table(name = "share_image")
@NamedQuery(name = "ShareImageEntity.findAll", query = "SELECT u FROM ShareImageEntity u")
public class ShareImageEntity extends AbstractBaseEntity
{
	public String getShareImageUuid() {
		return shareImageUuid;
	}

	public void setShareImageUuid(String shareImageUuid) {
		this.shareImageUuid = shareImageUuid;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "SHAREIMAGE_UUID")
	@GenericGenerator(name = "SHAREIMAGE_UUID", strategy = "uuid")
	@Column(name = "SHAREIMAGE_UUID", nullable = false, length = 36)
	private String shareImageUuid;

	@Column(name = "URL")
	private String url;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHARE_UUID")
	private ShareEntity shareEntity;

	public ShareEntity getShareEntity() {
		return shareEntity;
	}

	public void setShareEntity(ShareEntity shareEntity) {
		this.shareEntity = shareEntity;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}