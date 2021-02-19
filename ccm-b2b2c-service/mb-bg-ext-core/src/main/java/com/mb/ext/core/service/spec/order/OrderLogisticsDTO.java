package com.mb.ext.core.service.spec.order;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class OrderLogisticsDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;

	private String orderLogisticsUuid;
	
	private OrderDTO orderDTO;
	
	private LogisticsDTO logisticsDTO;
	
	private String expressNo;
	
	private String consigneeName;
	
	private String consigneeTelephone;
	
	private String consigneeTelephone2;
	
	private String consigneeAddress;
	
	private String consigneeZipcode;

	private BigDecimal logisticsFee;
	
	private BigDecimal agencyFee;
	
	private BigDecimal deliveryAmount;
	
	private String logisticsStatus;
	
	private String logisticsSettlementStatus;
	
	private String logisticsResultStatus;
	
	private String logisticsResult;
	
	private Date logisticsCreateTime;
	
	public String getOrderLogisticsUuid() {
		return orderLogisticsUuid;
	}

	public void setOrderLogisticsUuid(String orderLogisticsUuid) {
		this.orderLogisticsUuid = orderLogisticsUuid;
	}

	public OrderDTO getOrderDTO() {
		return orderDTO;
	}

	public void setOrderDTO(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
	}

	public LogisticsDTO getLogisticsDTO() {
		return logisticsDTO;
	}

	public void setLogisticsDTO(LogisticsDTO logisticsDTO) {
		this.logisticsDTO = logisticsDTO;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeTelephone() {
		return consigneeTelephone;
	}

	public void setConsigneeTelephone(String consigneeTelephone) {
		this.consigneeTelephone = consigneeTelephone;
	}

	public String getConsigneeTelephone2() {
		return consigneeTelephone2;
	}

	public void setConsigneeTelephone2(String consigneeTelephone2) {
		this.consigneeTelephone2 = consigneeTelephone2;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public String getConsigneeZipcode() {
		return consigneeZipcode;
	}

	public void setConsigneeZipcode(String consigneeZipcode) {
		this.consigneeZipcode = consigneeZipcode;
	}

	public BigDecimal getLogisticsFee() {
		return logisticsFee;
	}

	public void setLogisticsFee(BigDecimal logisticsFee) {
		this.logisticsFee = logisticsFee;
	}

	public BigDecimal getAgencyFee() {
		return agencyFee;
	}

	public void setAgencyFee(BigDecimal agencyFee) {
		this.agencyFee = agencyFee;
	}

	public BigDecimal getDeliveryAmount() {
		return deliveryAmount;
	}

	public void setDeliveryAmount(BigDecimal deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}

	public String getLogisticsStatus() {
		return logisticsStatus;
	}

	public void setLogisticsStatus(String logisticsStatus) {
		this.logisticsStatus = logisticsStatus;
	}

	public String getLogisticsSettlementStatus() {
		return logisticsSettlementStatus;
	}

	public void setLogisticsSettlementStatus(String logisticsSettlementStatus) {
		this.logisticsSettlementStatus = logisticsSettlementStatus;
	}

	public String getLogisticsResultStatus() {
		return logisticsResultStatus;
	}

	public void setLogisticsResultStatus(String logisticsResultStatus) {
		this.logisticsResultStatus = logisticsResultStatus;
	}

	public String getLogisticsResult() {
		return logisticsResult;
	}

	public void setLogisticsResult(String logisticsResult) {
		this.logisticsResult = logisticsResult;
	}

	public Date getLogisticsCreateTime() {
		return logisticsCreateTime;
	}

	public void setLogisticsCreateTime(Date logisticsCreateTime) {
		this.logisticsCreateTime = logisticsCreateTime;
	}

	public Date getLogisticsUpdateTime() {
		return logisticsUpdateTime;
	}

	public void setLogisticsUpdateTime(Date logisticsUpdateTime) {
		this.logisticsUpdateTime = logisticsUpdateTime;
	}

	public Date getLogisticsSettlementTime() {
		return logisticsSettlementTime;
	}

	public void setLogisticsSettlementTime(Date logisticsSettlementTime) {
		this.logisticsSettlementTime = logisticsSettlementTime;
	}

	public String getLogisticsPaymentChannel() {
		return logisticsPaymentChannel;
	}

	public void setLogisticsPaymentChannel(String logisticsPaymentChannel) {
		this.logisticsPaymentChannel = logisticsPaymentChannel;
	}

	public String getLogisticsPaymentOutTradeNo() {
		return logisticsPaymentOutTradeNo;
	}

	public void setLogisticsPaymentOutTradeNo(String logisticsPaymentOutTradeNo) {
		this.logisticsPaymentOutTradeNo = logisticsPaymentOutTradeNo;
	}

	public String getReconsiliationStatus() {
		return reconsiliationStatus;
	}

	public void setReconsiliationStatus(String reconsiliationStatus) {
		this.reconsiliationStatus = reconsiliationStatus;
	}

	public Date getReconsiliationTime() {
		return reconsiliationTime;
	}

	public void setReconsiliationTime(Date reconsiliationTime) {
		this.reconsiliationTime = reconsiliationTime;
	}

	private Date logisticsUpdateTime;
	
	private Date logisticsSettlementTime;
	
	private String logisticsPaymentChannel;
	
	private String logisticsPaymentOutTradeNo;
	
	private String reconsiliationStatus;
	
	private Date reconsiliationTime;
}
