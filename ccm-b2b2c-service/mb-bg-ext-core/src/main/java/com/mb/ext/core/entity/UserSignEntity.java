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
 * The persistent class for the user_sign database table.
 * 
 */
@Entity
@Table(name = "user_sign")
@NamedQuery(name = "UserSignEntity.findAll", query = "SELECT u FROM UserSignEntity u")
public class UserSignEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "USERSIGN_UUID")
	@GenericGenerator(name = "USERSIGN_UUID", strategy = "uuid")
	@Column(name = "USERSIGN_UUID", nullable = false, length = 36)
	private String userSignUuid;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;
	
	public String getUserSignUuid() {
		return userSignUuid;
	}

	public void setUserSignUuid(String userSignUuid) {
		this.userSignUuid = userSignUuid;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public int getSignPoint() {
		return signPoint;
	}

	public void setSignPoint(int signPoint) {
		this.signPoint = signPoint;
	}

	@Column(name = "SIGN_TIME")
	private Date signTime;
	
	@Column(name = "SIGN_POINT")
	private int signPoint;
}