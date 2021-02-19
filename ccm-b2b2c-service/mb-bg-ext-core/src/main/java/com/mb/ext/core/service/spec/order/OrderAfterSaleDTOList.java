package com.mb.ext.core.service.spec.order;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class OrderAfterSaleDTOList extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8234640629870275600L;

	private List<OrderAfterSaleDTO> afterSaleList = new ArrayList<OrderAfterSaleDTO>();
	
	private int total;

	public List<OrderAfterSaleDTO> getAfterSaleList() {
		return afterSaleList;
	}

	public void setAfterSaleList(List<OrderAfterSaleDTO> afterSaleList) {
		this.afterSaleList = afterSaleList;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
