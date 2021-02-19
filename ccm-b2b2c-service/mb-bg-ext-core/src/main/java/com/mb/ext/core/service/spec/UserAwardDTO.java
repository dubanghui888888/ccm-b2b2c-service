
package com.mb.ext.core.service.spec;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAwardDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String userAwardUuid;
	
	private UserDTO userDTO;
	
	private OrderDTO orderDTO;
	
	private String memo;
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

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
	
	private String transactionDesc;
	
	public String getTransactionDesc() {
		return transactionDesc;
	}

	public void setTransactionDesc(String transactionDesc) {
		this.transactionDesc = transactionDesc;
	}

	private Date transactionTime;
	
	private BigDecimal transactionAmount;
	
	private String transactionCode;
	
	public String getUserAwardUuid() {
		return userAwardUuid;
	}

	public void setUserAwardUuid(String userAwardUuid) {
		this.userAwardUuid = userAwardUuid;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}
	
	public String getUserName() {
		String userName = "";
		if(userDTO != null)
			userName  = userDTO.getName();
		return userName;
	}
	
	public void setUserName(String userName){
		
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
	
	public String getUserPersonalPhone() {
		String userPersonalPhone = "";
		if(userDTO != null)
			userPersonalPhone  = userDTO.getPersonalPhone();
		return userPersonalPhone;
	}
	
	public void setUserPersonalPhone(String userPersonalPhone){
		
	}
}
