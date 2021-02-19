package com.mb.ext.core.service.spec.order;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class OrderSummaryDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private int orderRecord;
	
	private int orderPoint;
	
	private int orderRecordNotDelivered;
	
	private int orderRecordDelivered;
	
	private int orderRecordCancelled;
	
	private int orderRecordCompleted;
	
	private int orderPointNotDelivered;
	
	private int orderPointDelivered;
	
	private int orderPointCancelled;
	
	private int orderPointCompleted;
	
	public int getOrderRecordCompleted() {
		return orderRecordCompleted;
	}

	public void setOrderRecordCompleted(int orderRecordCompleted) {
		this.orderRecordCompleted = orderRecordCompleted;
	}

	public int getOrderPointCompleted() {
		return orderPointCompleted;
	}

	public void setOrderPointCompleted(int orderPointCompleted) {
		this.orderPointCompleted = orderPointCompleted;
	}

	public int getOrderRecordNotDelivered() {
		return orderRecordNotDelivered;
	}

	public void setOrderRecordNotDelivered(int orderRecordNotDelivered) {
		this.orderRecordNotDelivered = orderRecordNotDelivered;
	}

	public int getOrderRecordDelivered() {
		return orderRecordDelivered;
	}

	public void setOrderRecordDelivered(int orderRecordDelivered) {
		this.orderRecordDelivered = orderRecordDelivered;
	}

	public int getOrderRecordCancelled() {
		return orderRecordCancelled;
	}

	public void setOrderRecordCancelled(int orderRecordCancelled) {
		this.orderRecordCancelled = orderRecordCancelled;
	}

	public int getOrderPointNotDelivered() {
		return orderPointNotDelivered;
	}

	public void setOrderPointNotDelivered(int orderPointNotDelivered) {
		this.orderPointNotDelivered = orderPointNotDelivered;
	}

	public int getOrderPointDelivered() {
		return orderPointDelivered;
	}

	public void setOrderPointDelivered(int orderPointDelivered) {
		this.orderPointDelivered = orderPointDelivered;
	}

	public int getOrderPointCancelled() {
		return orderPointCancelled;
	}

	public void setOrderPointCancelled(int orderPointCancelled) {
		this.orderPointCancelled = orderPointCancelled;
	}

	public int getOrderRecord() {
		return orderRecord;
	}

	public void setOrderRecord(int orderRecord) {
		this.orderRecord = orderRecord;
	}

	public int getOrderPoint() {
		return orderPoint;
	}

	public void setOrderPoint(int orderPoint) {
		this.orderPoint = orderPoint;
	}

	private int productUnit;
	
	private int productUnitBaoDan;
	
	private int productUnitFuGou;
	
	private BigDecimal productAmount;
	
	public int getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(int productUnit) {
		this.productUnit = productUnit;
	}

	public int getProductUnitBaoDan() {
		return productUnitBaoDan;
	}

	public void setProductUnitBaoDan(int productUnitBaoDan) {
		this.productUnitBaoDan = productUnitBaoDan;
	}

	public int getProductUnitFuGou() {
		return productUnitFuGou;
	}

	public void setProductUnitFuGou(int productUnitFuGou) {
		this.productUnitFuGou = productUnitFuGou;
	}

	public BigDecimal getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(BigDecimal productAmount) {
		this.productAmount = productAmount;
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

	public List<ChartDTO> getProductUnitChart() {
		return productUnitChart;
	}

	public void setProductUnitChart(List<ChartDTO> productUnitChart) {
		this.productUnitChart = productUnitChart;
	}

	public List<ChartDTO> getProductAmountChart() {
		return productAmountChart;
	}

	public void setProductAmountChart(List<ChartDTO> productAmountChart) {
		this.productAmountChart = productAmountChart;
	}

	public List<ChartDTO> getOrderRecordChart() {
		return orderRecordChart;
	}

	public void setOrderRecordChart(List<ChartDTO> orderRecordChart) {
		this.orderRecordChart = orderRecordChart;
	}

	public List<ChartDTO> getOrderPointChart() {
		return orderPointChart;
	}

	public void setOrderPointChart(List<ChartDTO> orderPointChart) {
		this.orderPointChart = orderPointChart;
	}

	private BigDecimal productAmountBaoDan;
	
	private BigDecimal productAmountFuGou;
	
	private List<ChartDTO> orderRecordChart;
	
	private List<ChartDTO> productUnitChart;
	
	private List<ChartDTO> productAmountChart;
	
	private List<ChartDTO> orderPointChart;
	
}
