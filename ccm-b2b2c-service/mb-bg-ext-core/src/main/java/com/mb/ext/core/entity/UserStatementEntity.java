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
 * The persistent class for the user_statement database table.
 * 
 */
@Entity
@Table(name = "user_statement")
@NamedQuery(name = "UserStatementEntity.findAll", query = "SELECT u FROM UserStatementEntity u")
public class UserStatementEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "USERSTATEMENT_UUID")
	@GenericGenerator(name = "USERSTATEMENT_UUID", strategy = "uuid")
	@Column(name = "USERSTATEMENT_UUID", nullable = false, length = 36)
	private String userStatementUuid;

	
	public String getUserStatementUuid() {
		return userStatementUuid;
	}

	public void setUserStatementUuid(String userStatementUuid) {
		this.userStatementUuid = userStatementUuid;
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


	public BigDecimal getBalanceBefore() {
		return balanceBefore;
	}

	public void setBalanceBefore(BigDecimal balanceBefore) {
		this.balanceBefore = balanceBefore;
	}

	public BigDecimal getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(BigDecimal balanceAfter) {
		this.balanceAfter = balanceAfter;
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


	@Column(name = "TRANSACTION_AMOUNT")
	private BigDecimal transactionAmount;
	
	@Column(name = "TRANSACTION_CODE")
	private String transactionCode;
	
	@Column(name = "TRANSACTION_POINT")
	private int transactionPoint;
	
	@Column(name = "TRANSACTION_DESC")
	private String transactionDesc;
	
	@Column(name = "BALANCEBEFORE")
	private BigDecimal balanceBefore;
	
	@Column(name = "BALANCEAFTER")
	private BigDecimal balanceAfter;
	
	@Column(name = "POINTBEFORE")
	private int pointBefore;
	
	@Column(name = "POINTAFTER")
	private int pointAfter;
}