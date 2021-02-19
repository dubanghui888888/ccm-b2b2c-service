package com.mb.ext.core.service.spec.order;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class MerchantSummaryDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private int merchantCount;
	
	private int assignPoint;
	
	private int assignTotal;
	
	public int getAssignTotal() {
		return assignTotal;
	}

	public void setAssignTotal(int assignTotal) {
		this.assignTotal = assignTotal;
	}

	public int getMerchantCount() {
		return merchantCount;
	}

	public void setMerchantCount(int merchantCount) {
		this.merchantCount = merchantCount;
	}

	public int getAssignPoint() {
		return assignPoint;
	}

	public void setAssignPoint(int assignPoint) {
		this.assignPoint = assignPoint;
	}

	public BigDecimal getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(BigDecimal chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	private BigDecimal chargeAmount;
	
	public int getChargePoint() {
		return chargePoint;
	}

	public void setChargePoint(int chargePoint) {
		this.chargePoint = chargePoint;
	}

	public int getChargeTotal() {
		return chargeTotal;
	}

	public void setChargeTotal(int chargeTotal) {
		this.chargeTotal = chargeTotal;
	}

	private int chargePoint;
	
	private int chargeTotal;
	
	private List<ChartDTO> merchantCountChart;

	public List<ChartDTO> getMerchantCountChart() {
		return merchantCountChart;
	}

	public void setMerchantCountChart(List<ChartDTO> merchantCountChart) {
		this.merchantCountChart = merchantCountChart;
	}
	
}
