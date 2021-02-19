package com.mb.ext.core.entity;

import java.util.Date;

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
 * The persistent class for the user_token database table.
 * 
 */
@Entity
@Table(name = "user_token")
@NamedQuery(name = "UserTokenEntity.findAll", query = "SELECT u FROM UserTokenEntity u")
public class UserTokenEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "USERTOKEN_UUID")
	@GenericGenerator(name = "USERTOKEN_UUID", strategy = "uuid")
	@Column(name = "USERTOKEN_UUID", nullable = false, length = 36)
	private String userTokenUuid;

	@Column(name = "TOKENID", nullable = false, length = 36)
	private String tokenId;
	
	@Column(name = "LOGINTIME")
	private Date loginTime;
	
	@Column(name = "EXPIRETIME")
	private Date expireTime;
	

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;

	public String getUserTokenUuid() {
		return userTokenUuid;
	}

	public void setUserTokenUuid(String userTokenUuid) {
		this.userTokenUuid = userTokenUuid;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	
}