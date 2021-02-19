
package com.mb.ext.core.service.spec;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class UserInventoryDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private int balance;
	
	private int balancePouch;

	public int getBalancePouch() {
		return balancePouch;
	}

	public void setBalancePouch(int balancePouch) {
		this.balancePouch = balancePouch;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
}
