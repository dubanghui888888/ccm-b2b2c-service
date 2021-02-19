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

import com.mb.ext.core.entity.UserEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the share database table.
 * 
 */
@Entity
@Table(name = "share")
@NamedQuery(name = "ShareEntity.findAll", query = "SELECT u FROM ShareEntity u")
public class ShareEntity extends AbstractBaseEntity
{
	public String getShareUuid() {
		return shareUuid;
	}

	public void setShareUuid(String shareUuid) {
		this.shareUuid = shareUuid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public TagEntity getTagEntity() {
		return tagEntity;
	}

	public void setTagEntity(TagEntity tagEntity) {
		this.tagEntity = tagEntity;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "SHARE_UUID")
	@GenericGenerator(name = "SHARE_UUID", strategy = "uuid")
	@Column(name = "SHARE_UUID", nullable = false, length = 36)
	private String shareUuid;

	@Column(name = "CONTENT")
	private String content;
	
	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	@Column(name = "LIKES")
	private int likes;
	
	@Column(name = "ISPUBLISHED")
	private boolean isPublished;
	
	public boolean isPublished() {
		return isPublished;
	}

	public void setPublished(boolean isPublished) {
		this.isPublished = isPublished;
	}
	
	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	@Column(name = "PUBLISH_TIME")
	private Date publishTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TAG_UUID")
	private TagEntity tagEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;
}