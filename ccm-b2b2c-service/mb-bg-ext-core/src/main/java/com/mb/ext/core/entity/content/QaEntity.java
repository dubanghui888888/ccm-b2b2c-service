package com.mb.ext.core.entity.content;

import java.util.Date;

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
 * The persistent class for the qa database table.
 * 
 */
@Entity
@Table(name = "qa")
@NamedQuery(name = "QaEntity.findAll", query = "SELECT u FROM QaEntity u")
public class QaEntity extends AbstractBaseEntity
{
	public String getQaUuid() {
		return qaUuid;
	}

	public void setQaUuid(String qaUuid) {
		this.qaUuid = qaUuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public boolean isFromOfficialAccount() {
		return isFromOfficialAccount;
	}

	public void setFromOfficialAccount(boolean isFromOfficialAccount) {
		this.isFromOfficialAccount = isFromOfficialAccount;
	}

	public TagEntity getTagEntity() {
		return tagEntity;
	}

	public void setTagEntity(TagEntity tagEntity) {
		this.tagEntity = tagEntity;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "QA_UUID")
	@GenericGenerator(name = "QA_UUID", strategy = "uuid")
	@Column(name = "QA_UUID", nullable = false, length = 36)
	private String qaUuid;

	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "DIGEST")
	private String digest;
	
	@Column(name = "CONTENT")
	private String content;
	
	@Column(name = "ISPUBLISHED")
	private boolean isPublished;
	
	public boolean isPublished() {
		return isPublished;
	}

	public void setPublished(boolean isPublished) {
		this.isPublished = isPublished;
	}

	public int getNumberOfReading() {
		return numberOfReading;
	}

	public void setNumberOfReading(int numberOfReading) {
		this.numberOfReading = numberOfReading;
	}

	@Column(name = "NUMBER_OF_READING")
	private int numberOfReading;
	
	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	@Column(name = "COVERIMAGE_URL")
	private String coverImageUrl;
	
	@Column(name = "MEDIA_ID")
	private String mediaId;
	
	@Column(name = "PREVIEW_URL")
	private String previewUrl;
	
	public boolean isHot() {
		return isHot;
	}

	public void setHot(boolean isHot) {
		this.isHot = isHot;
	}

	@Column(name = "PUBLISH_TIME")
	private Date publishTime;

	@Column(name = "IS_FROM_OFFICIALACCOUNT")
	private boolean isFromOfficialAccount;
	
	@Column(name = "ISHOT")
	private boolean isHot;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TAG_UUID")
	private TagEntity tagEntity;
}