package com.mb.ext.core.entity.merchant;

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
 * The persistent class for the merchant_balance database table.
 * 
 */
@Entity
@Table(name = "merchant_balance")
@NamedQuery(name = "MerchantBalanceEntity.findAll", query = "SELECT u FROM MerchantBalanceEntity u")
public class MerchantBalanceEntity extends AbstractBaseEntity
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

	public int getAvailablePoint() {
		return availablePoint;
	}

	public void setAvailablePoint(int availablePoint) {
		this.availablePoint = availablePoint;
	}

	public int getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "MERCHANTBALANCE_UUID")
	@GenericGenerator(name = "MERCHANTBALANCE_UUID", strategy = "uuid")
	@Column(name = "MERCHANTBALANCE_UUID", nullable = false, length = 36)
	private String merchantBalanceUuid;

	
	public String getMerchantBalanceUuid() {
		return merchantBalanceUuid;
	}

	public void setMerchantBalanceUuid(String merchantBalanceUuid) {
		this.merchantBalanceUuid = merchantBalanceUuid;
	}


	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}


	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;
	
	//可用余额
	@Column(name = "AVAILABLE_BALANCE")
	private BigDecimal availableBalance;
	
	//总余额
	@Column(name = "TOTAL_BALANCE")
	private BigDecimal totalBalance;
	
	//可用积分
	@Column(name = "AVAILABLE_POINT")
	private int availablePoint;
	
	//总积分
	@Column(name = "TOTAL_POINT")
	private int totalPoint;

}