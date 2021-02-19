package com.mb.ext.core.entity;

import java.math.BigDecimal;

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
 * The persistent class for the user_balance database table.
 * 
 */
@Entity
@Table(name = "user_balance")
@NamedQuery(name = "UserBalanceEntity.findAll", query = "SELECT u FROM UserBalanceEntity u")
public class UserBalanceEntity extends AbstractBaseEntity
{
	

	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "USERBALANCE_UUID")
	@GenericGenerator(name = "USERBALANCE_UUID", strategy = "uuid")
	@Column(name = "USERBALANCE_UUID", nullable = false, length = 36)
	private String userBalanceUuid;

	
	public String getUserBalanceUuid() {
		return userBalanceUuid;
	}

	public void setUserBalanceUuid(String userBalanceUuid) {
		this.userBalanceUuid = userBalanceUuid;
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

	public int getAvailablePoint() {
		return availablePoint;
	}

	public BigDecimal getLedgerBalance() {
		return ledgerBalance;
	}

	public void setLedgerBalance(BigDecimal ledgerBalance) {
		this.ledgerBalance = ledgerBalance;
	}

	public int getLedgerPoint() {
		return ledgerPoint;
	}

	public void setLedgerPoint(int ledgerPoint) {
		this.ledgerPoint = ledgerPoint;
	}

	public void setAvailablePoint(int availablePoint) {
		this.availablePoint = availablePoint;
	}

	//可用余额
	@Column(name = "AVAILABLE_BALANCE")
	private BigDecimal availableBalance;
	
	//未到账余额
	@Column(name = "LEDGER_BALANCE")
	private BigDecimal ledgerBalance;
	
	//可用积分
	@Column(name = "AVAILABLE_POINT")
	private int availablePoint;
	
	//未到账积分
	@Column(name = "LEDGER_POINT")
	private int ledgerPoint;
}