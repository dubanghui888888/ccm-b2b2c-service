package com.mb.ext.core.service.spec.order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PayOrderDTOList extends AbstractBaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private List<PayOrderDTO> payOrderList = new ArrayList<PayOrderDTO>();

	int total;
	
	BigDecimal asinfoPlatformTotal;
	
	BigDecimal asinfoMerchantTotal;

	public BigDecimal getAsinfoPlatformTotal() {
		return asinfoPlatformTotal;
	}

	public void setAsinfoPlatformTotal(BigDecimal asinfoPlatformTotal) {
		this.asinfoPlatformTotal = asinfoPlatformTotal;
	}

	public BigDecimal getAsinfoMerchantTotal() {
		return asinfoMerchantTotal;
	}

	public void setAsinfoMerchantTotal(BigDecimal asinfoMerchantTotal) {
		this.asinfoMerchantTotal = asinfoMerchantTotal;
	}

	public List<PayOrderDTO> getPayOrderList() {
		return payOrderList;
	}

	public void setPayOrderList(List<PayOrderDTO> payOrderList) {
		this.payOrderList = payOrderList;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
