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
 * The persistent class for the admin_token database table.
 * 
 */
@Entity
@Table(name = "admin_token")
@NamedQuery(name = "AdminTokenEntity.findAll", query = "SELECT u FROM AdminTokenEntity u")
public class AdminTokenEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ADMINTOKEN_UUID")
	@GenericGenerator(name = "ADMINTOKEN_UUID", strategy = "uuid")
	@Column(name = "ADMINTOKEN_UUID", nullable = false, length = 36)
	private String adminTokenUuid;

	@Column(name = "TOKENID", nullable = false, length = 36)
	private String tokenId;
	
	@Column(name = "LOGINTIME")
	private Date loginTime;
	
	@Column(name = "EXPIRETIME")
	private Date expireTime;
	

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADMIN_UUID")
	private AdminEntity adminEntity;
	
	public String getAdminTokenUuid() {
		return adminTokenUuid;
	}

	public void setAdminTokenUuid(String adminTokenUuid) {
		this.adminTokenUuid = adminTokenUuid;
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

	public AdminEntity getAdminEntity() {
		return adminEntity;
	}

	public void setAdminEntity(AdminEntity adminEntity) {
		this.adminEntity = adminEntity;
	}

}