
package com.mb.ext.core.service.spec.merchant;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlatformStatementDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String platformStatementUuid;
	
	private OrderDTO orderDTO;
	
	public OrderDTO getOrderDTO() {
		return orderDTO;
	}

	public void setOrderDTO(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
	}

	private Date startDate;
	
	private Date endDate;
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	private String transactionType;
	
	private Date transactionTime;
	
	private BigDecimal transactionAmount;
	
	private String transactionCode;

	public String getPlatformStatementUuid() {
		return platformStatementUuid;
	}

	public void setPlatformStatementUuid(String platformStatementUuid) {
		this.platformStatementUuid = platformStatementUuid;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Date getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	private BigDecimal balance;
	
}
