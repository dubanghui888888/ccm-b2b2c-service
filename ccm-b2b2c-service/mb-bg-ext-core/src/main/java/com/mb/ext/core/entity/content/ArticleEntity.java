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
 * The persistent class for the article database table.
 * 
 */
@Entity
@Table(name = "article")
@NamedQuery(name = "ArticleEntity.findAll", query = "SELECT u FROM ArticleEntity u")
public class ArticleEntity extends AbstractBaseEntity
{
	public String getArticleUuid() {
		return articleUuid;
	}

	public void setArticleUuid(String articleUuid) {
		this.articleUuid = articleUuid;
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

	public Date getPublishTime() {
		return publishTime;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
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
	@GeneratedValue(generator = "ARTICLE_UUID")
	@GenericGenerator(name = "ARTICLE_UUID", strategy = "uuid")
	@Column(name = "ARTICLE_UUID", nullable = false, length = 36)
	private String articleUuid;

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
	
	@Column(name = "LINK_TYPE")
	private String linkType;
	
	@Column(name = "PUBLISH_TIME")
	private Date publishTime;
	
	public String getArticleType() {
		return articleType;
	}

	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}

	@Column(name = "IS_FROM_OFFICIALACCOUNT")
	private boolean isFromOfficialAccount;
	
	@Column(name = "ARTICLE_TYPE")
	private String articleType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TAG_UUID")
	private TagEntity tagEntity;
}