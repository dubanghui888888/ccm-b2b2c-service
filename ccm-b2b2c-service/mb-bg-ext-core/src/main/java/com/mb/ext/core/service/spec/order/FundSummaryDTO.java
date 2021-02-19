package com.mb.ext.core.service.spec.order;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class FundSummaryDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private BigDecimal productAmount;
	
	public BigDecimal getPerformanceAmount() {
		return performanceAmount;
	}

	public void setPerformanceAmount(BigDecimal performanceAmount) {
		this.performanceAmount = performanceAmount;
	}

	public BigDecimal getPerformanceAward() {
		return performanceAward;
	}

	public void setPerformanceAward(BigDecimal performanceAward) {
		this.performanceAward = performanceAward;
	}

	private BigDecimal awardAmount;
	
	private BigDecimal availableBalance;
	
	private BigDecimal performanceAmount;
	
	private BigDecimal performanceAward;
	
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

	private BigDecimal ledgerBalance;
	
	public BigDecimal getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(BigDecimal productAmount) {
		this.productAmount = productAmount;
	}

	public BigDecimal getAwardAmount() {
		return awardAmount;
	}

	public void setAwardAmount(BigDecimal awardAmount) {
		this.awardAmount = awardAmount;
	}

	public BigDecimal getAwardAmountRecruit() {
		return awardAmountRecruit;
	}

	public void setAwardAmountRecruit(BigDecimal awardAmountRecruit) {
		this.awardAmountRecruit = awardAmountRecruit;
	}

	public BigDecimal getAwardAmountSale() {
		return awardAmountSale;
	}

	public void setAwardAmountSale(BigDecimal awardAmountSale) {
		this.awardAmountSale = awardAmountSale;
	}

	public BigDecimal getAwardAmountMerchant() {
		return awardAmountMerchant;
	}

	public void setAwardAmountMerchant(BigDecimal awardAmountMerchant) {
		this.awardAmountMerchant = awardAmountMerchant;
	}

	public BigDecimal getProductAmountBaoDan() {
		return productAmountBaoDan;
	}

	public void setProductAmountBaoDan(BigDecimal productAmountBaoDan) {
		this.productAmountBaoDan = productAmountBaoDan;
	}

	public BigDecimal getProductAmountFuGou() {
		return productAmountFuGou;
	}

	public void setProductAmountFuGou(BigDecimal productAmountFuGou) {
		this.productAmountFuGou = productAmountFuGou;
	}

	public List<ChartDTO> getOrderAmountChart() {
		return orderAmountChart;
	}

	public void setOrderAmountChart(List<ChartDTO> orderAmountChart) {
		this.orderAmountChart = orderAmountChart;
	}

	public List<ChartDTO> getAwardAmountChart() {
		return awardAmountChart;
	}

	public void setAwardAmountChart(List<ChartDTO> awardAmountChart) {
		this.awardAmountChart = awardAmountChart;
	}

	private BigDecimal awardAmountRecruit;
	
	private BigDecimal awardAmountSale;
	
	public BigDecimal getAwardAmountPartner() {
		return awardAmountPartner;
	}

	public void setAwardAmountPartner(BigDecimal awardAmountPartner) {
		this.awardAmountPartner = awardAmountPartner;
	}

	private BigDecimal awardAmountMerchant;
	
	private BigDecimal awardAmountTrain;
	
	private BigDecimal awardAmountPartner;
	
	public BigDecimal getAwardAmountPerformance() {
		return awardAmountPerformance;
	}

	public void setAwardAmountPerformance(BigDecimal awardAmountPerformance) {
		this.awardAmountPerformance = awardAmountPerformance;
	}

	private BigDecimal awardAmountPerformance;
	
	public BigDecimal getAwardAmountTrain() {
		return awardAmountTrain;
	}

	public void setAwardAmountTrain(BigDecimal awardAmountTrain) {
		this.awardAmountTrain = awardAmountTrain;
	}

	private BigDecimal productAmountBaoDan;
	
	private BigDecimal productAmountFuGou;
	
	private List<ChartDTO> orderAmountChart;
	
	private List<ChartDTO> awardAmountChart;
	
}
