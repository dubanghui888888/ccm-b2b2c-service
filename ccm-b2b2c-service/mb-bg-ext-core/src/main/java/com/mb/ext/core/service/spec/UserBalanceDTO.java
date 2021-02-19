
package com.mb.ext.core.service.spec;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class UserBalanceDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private UserDTO userDTO;
	
	private int availablePoint;
	
	private int ledgerPoint;
	
	public int getLedgerPoint() {
		return ledgerPoint;
	}

	public void setLedgerPoint(int ledgerPoint) {
		this.ledgerPoint = ledgerPoint;
	}

	public int getAvailablePoint() {
		return availablePoint;
	}

	public void setAvailablePoint(int availablePoint) {
		this.availablePoint = availablePoint;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}

	public BigDecimal getLedgerBalance() {
		return ledgerBalance;
	}

	public void setLedgerBalance(BigDecimal ledgerBalance) {
		this.ledgerBalance = ledgerBalance;
	}

	private BigDecimal availableBalance;
	
	private BigDecimal ledgerBalance;
	
}
