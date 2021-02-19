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
 * The persistent class for the share_comment_at database table.
 * 
 */
@Entity
@Table(name = "share_comment_at")
@NamedQuery(name = "ShareCommentAtEntity.findAll", query = "SELECT u FROM ShareCommentAtEntity u")
public class ShareCommentAtEntity extends AbstractBaseEntity
{
	public String getShareCommentAtUuid() {
		return shareCommentAtUuid;
	}

	public void setShareCommentAtUuid(String shareCommentAtUuid) {
		this.shareCommentAtUuid = shareCommentAtUuid;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "SHARECOMMENTAT_UUID")
	@GenericGenerator(name = "SHARECOMMENTAT_UUID", strategy = "uuid")
	@Column(name = "SHARECOMMENTAT_UUID", nullable = false, length = 36)
	private String shareCommentAtUuid;

	public ShareCommentEntity getShareCommentEntity() {
		return shareCommentEntity;
	}

	public void setShareCommentEntity(ShareCommentEntity shareCommentEntity) {
		this.shareCommentEntity = shareCommentEntity;
	}

	@Column(name = "COMMENT")
	private String comment;
	
	@Column(name = "COMMENT_TIME")
	private Date commentTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHARECOMMENT_UUID")
	private ShareCommentEntity shareCommentEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;

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

}