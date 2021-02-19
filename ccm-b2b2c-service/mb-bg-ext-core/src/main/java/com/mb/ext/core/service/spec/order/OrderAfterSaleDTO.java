package com.mb.ext.core.service.spec.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class OrderAfterSaleDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8234640629870275600L;

	private String orderAfterSaleUuid;

	private String afterSaleType;
	
	private String courierName;
	
	private MerchantDTO merchantDTO;
	
	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getRefundMsg() {
		return refundMsg;
	}

	public void setRefundMsg(String refundMsg) {
		this.refundMsg = refundMsg;
	}

	private String refundId;
	
	private String refundMsg;
	
	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public String getCourierNo() {
		return courierNo;
	}

	public void setCourierNo(String courierNo) {
		this.courierNo = courierNo;
	}

	private String courierNo;
	
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	private String rejectReason;
	
	private BigDecimal afterSaleAmount;
	
	public String getSaleNo() {
		return saleNo;
	}

	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}

	private String afterSaleDescription;
	
	private String saleNo;
	
	public OrderDTO getOrderDTO() {
		return orderDTO;
	}

	public void setOrderDTO(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
	}

	public List<String> getImageUrlList() {
		return imageUrlList;
	}

	public void setImageUrlList(List<String> imageUrlList) {
		this.imageUrlList = imageUrlList;
	}

	private Date timeApplication;
	
	private OrderDTO orderDTO;
	
	public String getOrderAfterSaleUuid() {
		return orderAfterSaleUuid;
	}

	public void setOrderAfterSaleUuid(String orderAfterSaleUuid) {
		this.orderAfterSaleUuid = orderAfterSaleUuid;
	}

	public String getAfterSaleType() {
		return afterSaleType;
	}

	public void setAfterSaleType(String afterSaleType) {
		this.afterSaleType = afterSaleType;
	}

	public BigDecimal getAfterSaleAmount() {
		return afterSaleAmount;
	}

	public void setAfterSaleAmount(BigDecimal afterSaleAmount) {
		this.afterSaleAmount = afterSaleAmount;
	}

	public String getAfterSaleDescription() {
		return afterSaleDescription;
	}

	public void setAfterSaleDescription(String afterSaleDescription) {
		this.afterSaleDescription = afterSaleDescription;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getTimeApplication() {
		return timeApplication;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public void setTimeApplication(Date timeApplication) {
		this.timeApplication = timeApplication;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getTimeOperation() {
		return timeOperation;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public void setTimeOperation(Date timeOperation) {
		this.timeOperation = timeOperation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private Date timeOperation;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getTimeCouirer() {
		return timeCouirer;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public void setTimeCouirer(Date timeCouirer) {
		this.timeCouirer = timeCouirer;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getTimeConfirm() {
		return timeConfirm;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public void setTimeConfirm(Date timeConfirm) {
		this.timeConfirm = timeConfirm;
	}

	private Date timeCouirer;
	
	private Date timeConfirm;
	
	private String status;
	
	private List<String> imageUrlList;

	}
