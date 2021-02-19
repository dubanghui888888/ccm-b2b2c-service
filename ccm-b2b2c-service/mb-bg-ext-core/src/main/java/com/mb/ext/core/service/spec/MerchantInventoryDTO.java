
package com.mb.ext.core.service.spec;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class MerchantInventoryDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String merchantInventoryUuid;
	
	private MerchantDTO merchantDTO;
	
	public String getMerchantInventoryUuid() {
		return merchantInventoryUuid;
	}

	public void setMerchantInventoryUuid(String merchantInventoryUuid) {
		this.merchantInventoryUuid = merchantInventoryUuid;
	}

	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	private int ledgerBalance;
	
	public int getLedgerBalancePouch() {
		return ledgerBalancePouch;
	}

	public void setLedgerBalancePouch(int ledgerBalancePouch) {
		this.ledgerBalancePouch = ledgerBalancePouch;
	}

	public int getAvailableBalancePouch() {
		return availableBalancePouch;
	}

	public void setAvailableBalancePouch(int availableBalancePouch) {
		this.availableBalancePouch = availableBalancePouch;
	}

	private int ledgerBalancePouch;
	
	private int availableBalancePouch;
	
	private int availableBalance;

	public int getLedgerBalance() {
		return ledgerBalance;
	}

	public void setLedgerBalance(int ledgerBalance) {
		this.ledgerBalance = ledgerBalance;
	}

	public int getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(int availableBalance) {
		this.availableBalance = availableBalance;
	}

}
