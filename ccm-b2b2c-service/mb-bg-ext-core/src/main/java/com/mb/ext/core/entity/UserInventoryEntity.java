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
 * The persistent class for the user_inventory database table.
 * 
 */
@Entity
@Table(name = "user_inventory")
@NamedQuery(name = "UserInventoryEntity.findAll", query = "SELECT u FROM UserInventoryEntity u")
public class UserInventoryEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "USERINVENTORY_UUID")
	@GenericGenerator(name = "USERINVENTORY_UUID", strategy = "uuid")
	@Column(name = "USERINVENTORY_UUID", nullable = false, length = 36)
	private String userInventoryUuid;

	
	@Column(name = "BALANCE")
	private int balance;
	
	@Column(name = "BALANCE_POUCH")
	private int balancePouch;
	
	public int getBalancePouch() {
		return balancePouch;
	}

	public void setBalancePouch(int balancePouch) {
		this.balancePouch = balancePouch;
	}

	public String getUserInventoryUuid() {
		return userInventoryUuid;
	}

	public void setUserInventoryUuid(String userInventoryUuid) {
		this.userInventoryUuid = userInventoryUuid;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
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

	
}