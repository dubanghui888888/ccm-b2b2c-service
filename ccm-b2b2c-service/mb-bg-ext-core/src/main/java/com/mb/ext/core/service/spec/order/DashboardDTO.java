package com.mb.ext.core.service.spec.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
/**
 * 用于存储后台仪表团数据
 * @author 
 *
 */
@JsonInclude(Include.NON_NULL)
public class DashboardDTO extends AbstractBaseDTO{
	
	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private int userCount;
	
	private int totalUserCount;
	
	private int totalOrderCount;
	
	public int getTotalOrderCount() {
		return totalOrderCount;
	}

	public void setTotalOrderCount(int totalOrderCount) {
		this.totalOrderCount = totalOrderCount;
	}

	private int totalProductCount;
	
	private int totalMerchantCount;
	
	public int getMerchantCount() {
		return merchantCount;
	}

	public void setMerchantCount(int merchantCount) {
		this.merchantCount = merchantCount;
	}

	public float getMerchantCountGrowthRate() {
		return merchantCountGrowthRate;
	}

	public void setMerchantCountGrowthRate(float merchantCountGrowthRate) {
		this.merchantCountGrowthRate = merchantCountGrowthRate;
	}

	private float userCountGrowthRate;
	
	private int merchantCount;
	
	private float merchantCountGrowthRate;
	
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

	public float getUserCountGrowthRate() {
		return userCountGrowthRate;
	}

	public void setUserCountGrowthRate(float userCountGrowthRate) {
		this.userCountGrowthRate = userCountGrowthRate;
	}

	public float getOrderCountGrowthRate() {
		return orderCountGrowthRate;
	}

	public void setOrderCountGrowthRate(float orderCountGrowthRate) {
		this.orderCountGrowthRate = orderCountGrowthRate;
	}

	public float getOrderAmountGrowthRate() {
		return orderAmountGrowthRate;
	}

	public void setOrderAmountGrowthRate(float orderAmountGrowthRate) {
		this.orderAmountGrowthRate = orderAmountGrowthRate;
	}

	private int orderCount;
	
	private int orderCountDay7;
	
	private BigDecimal orderAmountDay7;
	
	private BigDecimal afterSaleAmountDay7;
	
	private BigDecimal totalOrderAmount;
	
	private BigDecimal merchantAwardAmount;
	
	private BigDecimal userAwardAmount;
	
	public BigDecimal getUserAwardAmount() {
		return userAwardAmount;
	}

	public void setUserAwardAmount(BigDecimal userAwardAmount) {
		this.userAwardAmount = userAwardAmount;
	}

	public int getTotalUserCount() {
		return totalUserCount;
	}

	public void setTotalUserCount(int totalUserCount) {
		this.totalUserCount = totalUserCount;
	}

	public int getTotalProductCount() {
		return totalProductCount;
	}

	public void setTotalProductCount(int totalProductCount) {
		this.totalProductCount = totalProductCount;
	}

	public int getTotalMerchantCount() {
		return totalMerchantCount;
	}

	public void setTotalMerchantCount(int totalMerchantCount) {
		this.totalMerchantCount = totalMerchantCount;
	}

	public int getOrderCountDay7() {
		return orderCountDay7;
	}

	public void setOrderCountDay7(int orderCountDay7) {
		this.orderCountDay7 = orderCountDay7;
	}

	public BigDecimal getOrderAmountDay7() {
		return orderAmountDay7;
	}

	public void setOrderAmountDay7(BigDecimal orderAmountDay7) {
		this.orderAmountDay7 = orderAmountDay7;
	}

	public BigDecimal getAfterSaleAmountDay7() {
		return afterSaleAmountDay7;
	}

	public void setAfterSaleAmountDay7(BigDecimal afterSaleAmountDay7) {
		this.afterSaleAmountDay7 = afterSaleAmountDay7;
	}

	public BigDecimal getTotalOrderAmount() {
		return totalOrderAmount;
	}

	public void setTotalOrderAmount(BigDecimal totalOrderAmount) {
		this.totalOrderAmount = totalOrderAmount;
	}

	public BigDecimal getMerchantAwardAmount() {
		return merchantAwardAmount;
	}

	public void setMerchantAwardAmount(BigDecimal merchantAwardAmount) {
		this.merchantAwardAmount = merchantAwardAmount;
	}

	public BigDecimal getMerchantWithdrawAmount() {
		return merchantWithdrawAmount;
	}

	public void setMerchantWithdrawAmount(BigDecimal merchantWithdrawAmount) {
		this.merchantWithdrawAmount = merchantWithdrawAmount;
	}

	public int getToVerifyMerchantWithdrawCount() {
		return toVerifyMerchantWithdrawCount;
	}

	public void setToVerifyMerchantWithdrawCount(int toVerifyMerchantWithdrawCount) {
		this.toVerifyMerchantWithdrawCount = toVerifyMerchantWithdrawCount;
	}

	public int getToPayMerchantWithdrawCount() {
		return toPayMerchantWithdrawCount;
	}

	public void setToPayMerchantWithdrawCount(int toPayMerchantWithdrawCount) {
		this.toPayMerchantWithdrawCount = toPayMerchantWithdrawCount;
	}

	private BigDecimal merchantWithdrawAmount;
	
	private float orderCountGrowthRate;
	
	private BigDecimal orderAmount;
	
	private BigDecimal awardAmount;
	
	int toVerifyMerchantWithdrawCount;
	
	int toPayMerchantWithdrawCount;
	
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

	public BigDecimal getAwardAmount() {
		return awardAmount;
	}

	public void setAwardAmount(BigDecimal awardAmount) {
		this.awardAmount = awardAmount;
	}

	public float getAwardAmountGrowthRate() {
		return awardAmountGrowthRate;
	}

	public void setAwardAmountGrowthRate(float awardAmountGrowthRate) {
		this.awardAmountGrowthRate = awardAmountGrowthRate;
	}

	private float orderAmountGrowthRate;
	
	private float awardAmountGrowthRate;
	
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
	
	int toVerifyWithdrawCount;
	
	int toPayWithdrawCount;
	
	public int getToVerifyWithdrawCount() {
		return toVerifyWithdrawCount;
	}

	public void setToVerifyWithdrawCount(int toVerifyWithdrawCount) {
		this.toVerifyWithdrawCount = toVerifyWithdrawCount;
	}

	public int getToPayWithdrawCount() {
		return toPayWithdrawCount;
	}

	public void setToPayWithdrawCount(int toPayWithdrawCount) {
		this.toPayWithdrawCount = toPayWithdrawCount;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
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
	
}
