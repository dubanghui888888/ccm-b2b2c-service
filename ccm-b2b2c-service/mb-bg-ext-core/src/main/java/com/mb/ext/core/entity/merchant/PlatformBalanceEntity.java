package com.mb.ext.core.entity.merchant;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the platform_balance database table.
 * 
 */
@Entity
@Table(name = "platform_balance")
@NamedQuery(name = "PlatformBalanceEntity.findAll", query = "SELECT u FROM PlatformBalanceEntity u")
public class PlatformBalanceEntity extends AbstractBaseEntity
{
	

	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}

	public BigDecimal getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(BigDecimal totalBalance) {
		this.totalBalance = totalBalance;
	}

	public int getAvailableIntegral() {
		return availableIntegral;
	}

	public void setAvailableIntegral(int availableIntegral) {
		this.availableIntegral = availableIntegral;
	}

	public int getTotalIntegral() {
		return totalIntegral;
	}

	public void setTotalIntegral(int totalIntegral) {
		this.totalIntegral = totalIntegral;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PLATFORMBALANCE_UUID")
	@GenericGenerator(name = "PLATFORMBALANCE_UUID", strategy = "uuid")
	@Column(name = "PLATFORMBALANCE_UUID", nullable = false, length = 36)
	private String platformBalanceUuid;

	
	public String getPlatformBalanceUuid() {
		return platformBalanceUuid;
	}

	public void setPlatformBalanceUuid(String platformBalanceUuid) {
		this.platformBalanceUuid = platformBalanceUuid;
	}

	//可用余额
	@Column(name = "AVAILABLE_BALANCE")
	private BigDecimal availableBalance;
	
	//总余额
	@Column(name = "TOTAL_BALANCE")
	private BigDecimal totalBalance;
	
	//可用积分
	@Column(name = "AVAILABLE_INTEGRAL")
	private int availableIntegral;
	
	//可用积分
	@Column(name = "TOTAL_INTEGRAL")
	private int totalIntegral;

}