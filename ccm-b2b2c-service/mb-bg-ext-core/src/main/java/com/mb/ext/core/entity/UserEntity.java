package com.mb.ext.core.entity;

import java.util.Date;

import javax.persistence.CascadeType;
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

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "user")
@NamedQuery(name = "UserEntity.findAll", query = "SELECT u FROM UserEntity u")
public class UserEntity extends AbstractBaseEntity
{

	public UserAuthEntity getUserAuthEntity() {
		return userAuthEntity;
	}

	public void setUserAuthEntity(UserAuthEntity userAuthEntity) {
		this.userAuthEntity = userAuthEntity;
	}

	private static final long serialVersionUID = 1L;

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPersonalEmail() {
		return personalEmail;
	}

	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}

	public String getPersonalPhone() {
		return personalPhone;
	}

	public void setPersonalPhone(String personalPhone) {
		this.personalPhone = personalPhone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(generator = "USER_UUID")
	@GenericGenerator(name = "USER_UUID", strategy = "uuid")
	@Column(name = "USER_UUID", nullable = false, length = 36)
	private String userUuid;

	@Column(name = "PHOTOURL",length = 200)
	private String photoUrl;

	@Column(name = "NAME", length = 20)
	private String name;
	
	public boolean isNotificationEnabled() {
		return isNotificationEnabled;
	}

	public void setNotificationEnabled(boolean isNotificationEnabled) {
		this.isNotificationEnabled = isNotificationEnabled;
	}

	@Column(name = "IS_PROFIT_WELFARE_RECEIVED")
	private boolean isProfitWelfareReceived;

	public boolean isProfitWelfareReceived() {
		return isProfitWelfareReceived;
	}

	public void setProfitWelfareReceived(boolean profitWelfareReceived) {
		isProfitWelfareReceived = profitWelfareReceived;
	}

	@Column(name = "MEMO", length = 100)
	private String memo;
	
	public UserEntity getSupervisorL1() {
		return supervisorL1;
	}

	public void setSupervisorL1(UserEntity supervisorL1) {
		this.supervisorL1 = supervisorL1;
	}

	public UserEntity getInvitationUser() {
		return invitationUser;
	}

	public void setInvitationUser(UserEntity invitationUser) {
		this.invitationUser = invitationUser;
	}

	@Column(name = "IS_NOTIFICATION_ENABLED")
	private boolean isNotificationEnabled;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPERVISOR_UUID")
	private UserEntity supervisorL1;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INVITATION_UUID")
	private UserEntity invitationUser;

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "SEX", length = 1)
	private String sex;
	
	@Column(name = "OPENID", length = 45)
	private String openId;
	
	public String getOpenIdOfc() {
		return openIdOfc;
	}

	public void setOpenIdOfc(String openIdOfc) {
		this.openIdOfc = openIdOfc;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	@Column(name = "OPENID_OFC", length = 45)
	private String openIdOfc;
	
	@Column(name = "UNIONID", length = 45)
	private String unionId;
	
	@Column(name = "REGISTER_DATE")
	private Date registerDate;

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Column(name = "PERSONALEMAIL", length = 40)
	private String personalEmail;
	
	@Column(name = "PERSONALPHONE", length = 17)
	private String personalPhone;

	@Column(name = "ID",length = 24)
	private String id;
	
	@Column(name = "IDCARDNO")
	private String idCardNo;

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	@Column(name = "PERSONALPHONE_COUNTRYCODE", length = 4)
	private String personalPhoneCountryCode;

	public String getPersonalPhoneCountryCode() {
		return personalPhoneCountryCode;
	}

	public void setPersonalPhoneCountryCode(String personalPhoneCountryCode) {
		this.personalPhoneCountryCode = personalPhoneCountryCode;
	}

	public void setUserTokenEntity(UserTokenEntity userTokenEntity) {
		this.userTokenEntity = userTokenEntity;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userEntity")
	private UserAuthEntity userAuthEntity;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userEntity")
	private UserTokenEntity userTokenEntity;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userEntity")
	private UserWechatEntity userWechatEntity;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userEntity")
	private UserInventoryEntity userInventoryEntity;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userEntity")
	private UserBalanceEntity userBalanceEntity;

	public UserBalanceEntity getUserBalanceEntity() {
		return userBalanceEntity;
	}

	public void setUserBalanceEntity(UserBalanceEntity userBalanceEntity) {
		this.userBalanceEntity = userBalanceEntity;
	}

	public UserInventoryEntity getUserInventoryEntity() {
		return userInventoryEntity;
	}

	public void setUserInventoryEntity(UserInventoryEntity userInventoryEntity) {
		this.userInventoryEntity = userInventoryEntity;
	}
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public UserLevelEntity getUserLevelEntity() {
		return userLevelEntity;
	}

	public void setUserLevelEntity(UserLevelEntity userLevelEntity) {
		this.userLevelEntity = userLevelEntity;
	}

	@Column(name = "ISACTIVE")
	private boolean isActive;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_LEVEL_UUID")
	private UserLevelEntity userLevelEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRAINER_UUID")
	private TrainerEntity trainerEntity;
	
	public TrainerEntity getTrainerEntity() {
		return trainerEntity;
	}

	public void setTrainerEntity(TrainerEntity trainerEntity) {
		this.trainerEntity = trainerEntity;
	}

	public UserWechatEntity getUserWechatEntity() {
		return userWechatEntity;
	}

	public void setUserWechatEntity(UserWechatEntity userWechatEntity) {
		this.userWechatEntity = userWechatEntity;
	}

	public UserTokenEntity getUserTokenEntity() {
		return userTokenEntity;
	}

	public void setUseTokenEntity(UserTokenEntity userTokenEntity) {
		this.userTokenEntity = userTokenEntity;
	}

}