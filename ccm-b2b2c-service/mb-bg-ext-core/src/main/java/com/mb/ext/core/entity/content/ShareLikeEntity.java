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
 * The persistent class for the share_like database table.
 * 
 */
@Entity
@Table(name = "share_like")
@NamedQuery(name = "ShareLikeEntity.findAll", query = "SELECT u FROM ShareLikeEntity u")
public class ShareLikeEntity extends AbstractBaseEntity
{
	public String getShareLikeUuid() {
		return shareLikeUuid;
	}

	public void setShareLikeUuid(String shareLikeUuid) {
		this.shareLikeUuid = shareLikeUuid;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "SHARELIKE_UUID")
	@GenericGenerator(name = "SHARELIKE_UUID", strategy = "uuid")
	@Column(name = "SHARELIKE_UUID", nullable = false, length = 36)
	private String shareLikeUuid;

	@Column(name = "LIKE_TIME")
	private Date likeTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHARE_UUID")
	private ShareEntity shareEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;
	
	public ShareEntity getShareEntity() {
		return shareEntity;
	}

	public Date getLikeTime() {
		return likeTime;
	}

	public void setLikeTime(Date likeTime) {
		this.likeTime = likeTime;
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