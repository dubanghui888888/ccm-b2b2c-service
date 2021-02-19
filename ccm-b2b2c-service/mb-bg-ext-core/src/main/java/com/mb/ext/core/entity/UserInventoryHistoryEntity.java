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
 * The persistent class for the user_inventory_history database table.
 * 
 */
@Entity
@Table(name = "user_inventory_history")
@NamedQuery(name = "UserInventoryHistoryEntity.findAll", query = "SELECT u FROM UserInventoryHistoryEntity u")
public class UserInventoryHistoryEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "USERINVENTORYHISTORY_UUID")
	@GenericGenerator(name = "USERINVENTORYHISTORY_UUID", strategy = "uuid")
	@Column(name = "USERINVENTORYHISTORY_UUID", nullable = false, length = 36)
	private String userInventoryHistoryUuid;

	
	@Column(name = "BALANCEBEFORE")
	private int balanceBefore;
	
	@Column(name = "BALANCEPOUCHBEFORE")
	private int balancePouchBefore;
	
	@Column(name = "BALANCEPOUCHAFTER")
	private int balancePouchAfter;
	
	@Column(name = "TRANUNITVALUE")
	private String tranUnitValue;
	
	public int getBalancePouchBefore() {
		return balancePouchBefore;
	}

	public void setBalancePouchBefore(int balancePouchBefore) {
		this.balancePouchBefore = balancePouchBefore;
	}

	public int getBalancePouchAfter() {
		return balancePouchAfter;
	}

	public void setBalancePouchAfter(int balancePouchAfter) {
		this.balancePouchAfter = balancePouchAfter;
	}

	public String getTranUnitValue() {
		return tranUnitValue;
	}

	public void setTranUnitValue(String tranUnitValue) {
		this.tranUnitValue = tranUnitValue;
	}

	public String getUserInventoryHistoryUuid() {
		return userInventoryHistoryUuid;
	}

	public void setUserInventoryHistoryUuid(String userInventoryHistoryUuid) {
		this.userInventoryHistoryUuid = userInventoryHistoryUuid;
	}

	public int getBalanceBefore() {
		return balanceBefore;
	}

	public void setBalanceBefore(int balanceBefore) {
		this.balanceBefore = balanceBefore;
	}

	public int getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(int balanceAfter) {
		this.balanceAfter = balanceAfter;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public int getTranUnit() {
		return tranUnit;
	}

	public void setTranUnit(int tranUnit) {
		this.tranUnit = tranUnit;
	}

	public boolean isIncrease() {
		return isIncrease;
	}

	public void setIncrease(boolean isIncrease) {
		this.isIncrease = isIncrease;
	}

	@Column(name = "BALANCEAFTER")
	private int balanceAfter;
	
	@Column(name = "TRANTYPE")
	private String tranType;
	
	@Column(name = "TRANDESC")
	private String tranDesc;
	
	public String getTranDesc() {
		return tranDesc;
	}

	public void setTranDesc(String tranDesc) {
		this.tranDesc = tranDesc;
	}

	@Column(name = "TRANUNIT")
	private int tranUnit;
	
	@Column(name = "ISINCREASE")
	private boolean isIncrease;
	

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;

	
}