
package com.mb.ext.core.service.spec.merchant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantWithdrawDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private List<MerchantWithdrawDTO> withdraws = new ArrayList<MerchantWithdrawDTO>();
	
	private int total;
	
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	private BigDecimal totalAmount;

	public List<MerchantWithdrawDTO> getWithdraws() {
		return withdraws;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setWithdraws(List<MerchantWithdrawDTO> withdraws) {
		this.withdraws = withdraws;
	}
	
}
