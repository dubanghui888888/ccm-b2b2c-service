package com.mb.ext.core.service.spec.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class OrderDTOList extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private List<OrderDTO> orders = new ArrayList<OrderDTO>();
	
	private MerchantDTO merchantDTO;

	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	int total;
	
	int totalToPay;
	
	int totalToDeliver;
	
	public int getTotalToPay() {
		return totalToPay;
	}

	public void setTotalToPay(int totalToPay) {
		this.totalToPay = totalToPay;
	}

	public int getTotalToDeliver() {
		return totalToDeliver;
	}

	public void setTotalToDeliver(int totalToDeliver) {
		this.totalToDeliver = totalToDeliver;
	}

	public int getTotalToConfirm() {
		return totalToConfirm;
	}

	public void setTotalToConfirm(int totalToConfirm) {
		this.totalToConfirm = totalToConfirm;
	}

	public int getTotalToEvaluate() {
		return totalToEvaluate;
	}

	public void setTotalToEvaluate(int totalToEvaluate) {
		this.totalToEvaluate = totalToEvaluate;
	}

	int totalToConfirm;
	
	int totalToEvaluate;
	
	int totalPoint;
	
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	BigDecimal totalAmount;
	
	public int getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<OrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDTO> orders) {
		this.orders = orders;
	}
	
}
