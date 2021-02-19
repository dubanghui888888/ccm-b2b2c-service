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
 * The persistent class for the user_auth database table.
 * 
 */
@Entity
@Table(name = "user_auth")
@NamedQuery(name = "UserAuthEntity.findAll", query = "SELECT u FROM UserAuthEntity u")
public class UserAuthEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "USERAUTH_UUID")
	@GenericGenerator(name = "USERAUTH_UUID", strategy = "uuid")
	@Column(name = "USERAUTH_UUID", nullable = false, length = 36)
	private String userAuthUuid;

	@Column(name = "PASSWORD", nullable = false, length = 100)
	private String password;
	
	@Column(name = "LASTLOGINTIME")
	private Date lastLoginTime;
	
	@Column(name = "FAILCOUNT")
	private int failCount;
	
	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	@Column(name = "SUCCESSCOUNT")
	private int successCount;
	
	public String getUserAuthUuid() {
		return userAuthUuid;
	}

	public void setUserAuthUuid(String userAuthUuid) {
		this.userAuthUuid = userAuthUuid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	@Column(name = "ISLOCKED")
	private boolean isLocked;
	
	public boolean isActivated() {
		return isActivated;
	}

	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}
	//账户是否停用
	@Column(name = "ISACTIVATED")
	private boolean isActivated;

	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;


	
}