
package com.mb.ext.core.service.spec;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPerformanceDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String userPerformanceUuid;
	
	private UserDTO userDTO;
	
	private Date performanceDate;
	
	private Date startDate;
	
	private Date endDate;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM")
	public Date getStartDate() {
		return startDate;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM")
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM")
	public Date getEndDate() {
		return endDate;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM")
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	private BigDecimal performanceAmount;
	
	private BigDecimal totalPerformanceAmount;
	
	public BigDecimal getTotalPerformanceAmount() {
		return totalPerformanceAmount;
	}
	public void setTotalPerformanceAmount(BigDecimal totalPerformanceAmount) {
		this.totalPerformanceAmount = totalPerformanceAmount;
	}
	public String getUserPerformanceUuid() {
		return userPerformanceUuid;
	}

	public void setUserPerformanceUuid(String userPerformanceUuid) {
		this.userPerformanceUuid = userPerformanceUuid;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM")
	public Date getPerformanceDate() {
		return performanceDate;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM")
	public void setPerformanceDate(Date performanceDate) {
		this.performanceDate = performanceDate;
	}

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

	private BigDecimal performanceAward;
	
	public BigDecimal getVirtualPerformanceAmount() {
		return virtualPerformanceAmount;
	}
	public void setVirtualPerformanceAmount(BigDecimal virtualPerformanceAmount) {
		this.virtualPerformanceAmount = virtualPerformanceAmount;
	}

	private BigDecimal totalPerformanceAward;
	
	private BigDecimal virtualPerformanceAmount;
	

	public BigDecimal getTotalPerformanceAward() {
		return totalPerformanceAward;
	}
	public void setTotalPerformanceAward(BigDecimal totalPerformanceAward) {
		this.totalPerformanceAward = totalPerformanceAward;
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
	
	public String getUserPersonalPhone() {
		String userPersonalPhone = "";
		if(userDTO != null)
			userPersonalPhone  = userDTO.getPersonalPhone();
		return userPersonalPhone;
	}
	
	public void setUserPersonalPhone(String userPersonalPhone){
		
	}

}
