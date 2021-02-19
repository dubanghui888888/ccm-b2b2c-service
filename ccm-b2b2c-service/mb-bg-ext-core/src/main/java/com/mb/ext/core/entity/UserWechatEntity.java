package com.mb.ext.core.entity;

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
 * The persistent class for the user_wechat database table.
 * 
 */
@Entity
@Table(name = "user_wechat")
@NamedQuery(name = "UserWechatEntity.findAll", query = "SELECT u FROM UserWechatEntity u")
public class UserWechatEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "USERWECHAT_UUID")
	@GenericGenerator(name = "USERWECHAT_UUID", strategy = "uuid")
	@Column(name = "USERWECHAT_UUID", nullable = false, length = 36)
	private String userWechatUuid;

	@Column(name = "OPENID", nullable = false, length = 50)
	private String openId;
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;


	public String getUserWechatUuid() {
		return userWechatUuid;
	}


	public void setUserWechatUuid(String userWechatUuid) {
		this.userWechatUuid = userWechatUuid;
	}


	public String getOpenId() {
		return openId;
	}


	public void setOpenId(String openId) {
		this.openId = openId;
	}


	public UserEntity getUserEntity() {
		return userEntity;
	}


	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
}