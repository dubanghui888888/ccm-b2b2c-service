
package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPointStatementDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String userPointStatementUuid;
	
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
	
	public String getUserPointStatementUuid() {
		return userPointStatementUuid;
	}

	public void setUserPointStatementUuid(String userPointStatementUuid) {
		this.userPointStatementUuid = userPointStatementUuid;
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

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTransactionTime() {
		return transactionTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
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

	private int pointBefore;
	
	private int pointAfter;
}
