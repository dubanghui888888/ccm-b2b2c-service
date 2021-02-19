
package com.mb.ext.core.service.spec;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.constant.FundConstants;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.util.JsonDateTimeDeserializer;
import com.mb.ext.core.util.JsonDateTimeSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStatementDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String userStatementUuid;
	
	private UserDTO userDTO;
	
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
	
	public int getTransactionPoint() {
		return transactionPoint;
	}

	public void setTransactionPoint(int transactionPoint) {
		this.transactionPoint = transactionPoint;
	}

	public String getTransactionDesc() {
		return transactionDesc;
	}

	public void setTransactionDesc(String transactionDesc) {
		this.transactionDesc = transactionDesc;
	}

	private String transactionCode;

	private int transactionPoint;
	
	private String transactionDesc;
	
	public String getUserStatementUuid() {
		return userStatementUuid;
	}

	public void setUserStatementUuid(String userStatementUuid) {
		this.userStatementUuid = userStatementUuid;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public String getTransactionType() {
		return transactionType;
	}
	
	public String getTransactionTypeDesc() {
		String transactionTypeDesc = "";
		switch (transactionType) {
		case FundConstants.USER_STATEMENT_TRANSACTION_TYPE_ORDER_PAY_BALANCE:
			transactionTypeDesc = "订单支付";
			break;
		case FundConstants.USER_STATEMENT_TRANSACTION_TYPE_PERFORMANCE:
			transactionTypeDesc = "团队业绩奖金";
			break;
		case FundConstants.USER_STATEMENT_TRANSACTION_TYPE_RECRUIT:
			transactionTypeDesc = "推广奖金";
			break;
		case FundConstants.USER_STATEMENT_TRANSACTION_TYPE_REFUND:
			transactionTypeDesc = "订单退款";
			break;
		case FundConstants.USER_STATEMENT_TRANSACTION_TYPE_SALE:
			transactionTypeDesc = "销售奖金";
			break;
		case FundConstants.USER_STATEMENT_TRANSACTION_TYPE_TRAINER:
			transactionTypeDesc = "培训奖金";
			break;
		case FundConstants.USER_STATEMENT_TRANSACTION_TYPE_WITHDRAW:
			transactionTypeDesc = "余额提现";
			break;
		default:
			break;
		}
		return transactionTypeDesc;
	}
	
	public void setTransactionTypeDesc(String transactionTypeDesc) {
		
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getTransactionTime() {
		return transactionTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
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
	public BigDecimal getBalanceBefore() {
		return balanceBefore;
	}

	public void setBalanceBefore(BigDecimal balanceBefore) {
		this.balanceBefore = balanceBefore;
	}

	public BigDecimal getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(BigDecimal balanceAfter) {
		this.balanceAfter = balanceAfter;
	}

	public int getPointBefore() {
		return pointBefore;
	}

	public void setPointBefore(int pointBefore) {
		this.pointBefore = pointBefore;
	}

	public int getPointAfter() {
		return pointAfter;
	}

	public void setPointAfter(int pointAfter) {
		this.pointAfter = pointAfter;
	}

	private BigDecimal balanceBefore;
	
	private BigDecimal balanceAfter;
	
	private int pointBefore;
	
	private int pointAfter;
}
