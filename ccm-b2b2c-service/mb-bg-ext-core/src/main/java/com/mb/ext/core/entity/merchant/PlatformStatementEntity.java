package com.mb.ext.core.entity.merchant;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the platform_statement database table.
 * 
 */
@Entity
@Table(name = "platform_statement")
@NamedQuery(name = "PlatformStatementEntity.findAll", query = "SELECT u FROM PlatformStatementEntity u")
public class PlatformStatementEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PLATFORMSTATEMENT_UUID")
	@GenericGenerator(name = "PLATFORMSTATEMENT_UUID", strategy = "uuid")
	@Column(name = "PLATFORMSTATEMENT_UUID", nullable = false, length = 36)
	private String platformStatementUuid;

	
	public String getPlatformStatementUuid() {
		return platformStatementUuid;
	}

	public void setPlatformStatementUuid(String platformStatementUuid) {
		this.platformStatementUuid = platformStatementUuid;
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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;
	
	@Column(name = "TRANSACTION_TIME")
	private Date transactionTime;
	
	@Column(name = "TRANSACTION_AMOUNT")
	private BigDecimal transactionAmount;
	
	@Column(name = "TRANSACTION_CODE")
	private String transactionCode;
	
	@Column(name = "BALANCE")
	private BigDecimal balance;
}