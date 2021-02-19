package com.mb.ext.core.entity;

import java.math.BigDecimal;
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
 * The persistent class for the user_award database table.
 * 
 */
@Entity
@Table(name = "user_award")
@NamedQuery(name = "UserAwardEntity.findAll", query = "SELECT u FROM UserAwardEntity u")
public class UserAwardEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "USERAWARD_UUID")
	@GenericGenerator(name = "USERAWARD_UUID", strategy = "uuid")
	@Column(name = "USERAWARD_UUID", nullable = false, length = 36)
	private String userAwardUuid;

	
	public String getUserAwardUuid() {
		return userAwardUuid;
	}

	public void setUserAwardUuid(String userAwardUuid) {
		this.userAwardUuid = userAwardUuid;
	}


	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Date getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}


	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;
	
	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;
	
	public String getTransactionDesc() {
		return transactionDesc;
	}

	public void setTransactionDesc(String transactionDesc) {
		this.transactionDesc = transactionDesc;
	}


	@Column(name = "TRANSACTION_DESC")
	private String transactionDesc;
	
	@Column(name = "TRANSACTION_TIME")
	private Date transactionTime;
	
	@Column(name = "TRANSACTION_AMOUNT")
	private BigDecimal transactionAmount;
	
	@Column(name = "TRANSACTION_CODE")
	private String transactionCode;
	
	@Column(name = "MEMO")
	private String memo;


	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}