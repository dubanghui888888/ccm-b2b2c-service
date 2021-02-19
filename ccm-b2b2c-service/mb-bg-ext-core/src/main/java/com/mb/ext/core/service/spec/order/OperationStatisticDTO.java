package com.mb.ext.core.service.spec.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
/**
 * 用于存储后台运营分析数据
 * @author 
 *
 */
@JsonInclude(Include.NON_NULL)
public class OperationStatisticDTO extends AbstractBaseDTO{
	
	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public int getMerchantCount() {
		return merchantCount;
	}

	public void setMerchantCount(int merchantCount) {
		this.merchantCount = merchantCount;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private int userCount;
	
	private int merchantCount;
	
	private List<ChartDTO> userCountChart;
	
	private List<ChartDTO> merchantCountChart;
	
	public List<ChartDTO> getMerchantCountChart() {
		return merchantCountChart;
	}

	public void setMerchantCountChart(List<ChartDTO> merchantCountChart) {
		this.merchantCountChart = merchantCountChart;
	}

	public List<ChartDTO> getOrderCountChart() {
		return orderCountChart;
	}

	public void setOrderCountChart(List<ChartDTO> orderCountChart) {
		this.orderCountChart = orderCountChart;
	}

	public List<ChartDTO> getOrderAmountChart() {
		return orderAmountChart;
	}

	public void setOrderAmountChart(List<ChartDTO> orderAmountChart) {
		this.orderAmountChart = orderAmountChart;
	}

	private List<ChartDTO> orderCountChart;
	
	private List<ChartDTO> orderAmountChart;
	
	public List<ChartDTO> getUserCountChart() {
		return userCountChart;
	}

	public void setUserCountChart(List<ChartDTO> userCountChart) {
		this.userCountChart = userCountChart;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getAwardAmount() {
		return awardAmount;
	}

	public void setAwardAmount(BigDecimal awardAmount) {
		this.awardAmount = awardAmount;
	}

	private int orderCount;
	
	private BigDecimal orderAmount;
	
	private BigDecimal awardAmount;
	
	public List<OrderProductDTO> getTopUnitProductList() {
		return topUnitProductList;
	}

	public void setTopUnitProductList(List<OrderProductDTO> topUnitProductList) {
		this.topUnitProductList = topUnitProductList;
	}

	public List<OrderProductDTO> getTopAmountProductList() {
		return topAmountProductList;
	}

	public void setTopAmountProductList(List<OrderProductDTO> topAmountProductList) {
		this.topAmountProductList = topAmountProductList;
	}

	public int getToPayOrderCount() {
		return toPayOrderCount;
	}

	public void setToPayOrderCount(int toPayOrderCount) {
		this.toPayOrderCount = toPayOrderCount;
	}

	public int getToDeliverOrderCount() {
		return toDeliverOrderCount;
	}

	public void setToDeliverOrderCount(int toDeliverOrderCount) {
		this.toDeliverOrderCount = toDeliverOrderCount;
	}

	public int getAfterSaleOrderCount() {
		return afterSaleOrderCount;
	}

	public void setAfterSaleOrderCount(int afterSaleOrderCount) {
		this.afterSaleOrderCount = afterSaleOrderCount;
	}

	public int getToReplyProductCommentCount() {
		return toReplyProductCommentCount;
	}

	public void setToReplyProductCommentCount(int toReplyProductCommentCount) {
		this.toReplyProductCommentCount = toReplyProductCommentCount;
	}

	List<OrderProductDTO> topUnitProductList;
	
	List<OrderProductDTO> topAmountProductList;
	
	List<MerchantDTO> topUnitMerchantList;
	
	public List<MerchantDTO> getTopUnitMerchantList() {
		return topUnitMerchantList;
	}

	public void setTopUnitMerchantList(List<MerchantDTO> topUnitMerchantList) {
		this.topUnitMerchantList = topUnitMerchantList;
	}

	public List<MerchantDTO> getTopAmountMerchantList() {
		return topAmountMerchantList;
	}

	public void setTopAmountMerchantList(List<MerchantDTO> topAmountMerchantList) {
		this.topAmountMerchantList = topAmountMerchantList;
	}

	List<MerchantDTO> topAmountMerchantList;
	
	int toPayOrderCount;
	
	public int getToReviewAfterSaleCount() {
		return toReviewAfterSaleCount;
	}

	public void setToReviewAfterSaleCount(int toReviewAfterSaleCount) {
		this.toReviewAfterSaleCount = toReviewAfterSaleCount;
	}

	public int getToConfirmAfterSaleCount() {
		return toConfirmAfterSaleCount;
	}

	public void setToConfirmAfterSaleCount(int toConfirmAfterSaleCount) {
		this.toConfirmAfterSaleCount = toConfirmAfterSaleCount;
	}

	int toDeliverOrderCount;
	
	int toReviewAfterSaleCount;
	
	int toConfirmAfterSaleCount;
	
	int afterSaleOrderCount;
	
	int toReplyProductCommentCount;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}
	public String getMerchantUuid() {
		return merchantUuid;
	}

	public void setMerchantUuid(String merchantUuid) {
		this.merchantUuid = merchantUuid;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	private Date startDate;
	
	private Date endDate;
	
	private String merchantUuid;
	
}
