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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.ext.core.entity.UserEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the share_comment database table.
 * 
 */
@Entity
@Table(name = "share_comment")
@NamedQuery(name = "ShareCommentEntity.findAll", query = "SELECT u FROM ShareCommentEntity u")
public class ShareCommentEntity extends AbstractBaseEntity
{
	public String getShareCommentUuid() {
		return shareCommentUuid;
	}

	public void setShareCommentUuid(String shareCommentUuid) {
		this.shareCommentUuid = shareCommentUuid;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "SHARECOMMENT_UUID")
	@GenericGenerator(name = "SHARECOMMENT_UUID", strategy = "uuid")
	@Column(name = "SHARECOMMENT_UUID", nullable = false, length = 36)
	private String shareCommentUuid;

	@Column(name = "COMMENT")
	private String comment;
	
	@Column(name = "COMMENT_TIME")
	private Date commentTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHARE_UUID")
	private ShareEntity shareEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;
	
	public ShareEntity getShareEntity() {
		return shareEntity;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public void setShareEntity(ShareEntity shareEntity) {
		this.shareEntity = shareEntity;
	}


}