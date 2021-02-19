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
 * The persistent class for the user_point_statement database table.
 * 
 */
@Entity
@Table(name = "user_point_statement")
@NamedQuery(name = "UserPointStatementEntity.findAll", query = "SELECT u FROM UserPointStatementEntity u")
public class UserPointStatementEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "USERPOINTSTATEMENT_UUID")
	@GenericGenerator(name = "USERPOINTSTATEMENT_UUID", strategy = "uuid")
	@Column(name = "USERPOINTSTATEMENT_UUID", nullable = false, length = 36)
	private String userPointStatementUuid;

	
	public String getUserPointStatementUuid() {
		return userPointStatementUuid;
	}

	public void setUserPointStatementUuid(String userPointStatementUuid) {
		this.userPointStatementUuid = userPointStatementUuid;
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

	public int getPointBefore() {
		return pointBefore;
	}

	public void setPointBefore(int pointBefore) {
		this.pointBefore = pointBefore;
	}

	public int getPointAfter() {
		return pointAfter;
	}

	public void setPointAfter(int pointAfter) {
		this.pointAfter = pointAfter;
	}


	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;
	
	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;
	
	@Column(name = "TRANSACTION_TIME")
	private Date transactionTime;
	
	public int getTransactionPoint() {
		return transactionPoint;
	}

	public void setTransactionPoint(int transactionPoint) {
		this.transactionPoint = transactionPoint;
	}

	public String getTransactionDesc() {
		return transactionDesc;
	}

	public void setTransactionDesc(String transactionDesc) {
		this.transactionDesc = transactionDesc;
	}

	@Column(name = "TRANSACTION_CODE")
	private String transactionCode;
	
	@Column(name = "TRANSACTION_POINT")
	private int transactionPoint;
	
	@Column(name = "TRANSACTION_DESC")
	private String transactionDesc;
	
	@Column(name = "POINTBEFORE")
	private int pointBefore;
	
	@Column(name = "POINTAFTER")
	private int pointAfter;
}