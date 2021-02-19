
package com.mb.ext.core.service.spec.merchant;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.util.JsonDateTimeDeserializer;
import com.mb.ext.core.util.JsonDateTimeSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantAssignDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String merchantAssignUuid;
	
	private MerchantDTO merchantDTO;
	
	public String getMerchantAssignUuid() {
		return merchantAssignUuid;
	}
	public void setMerchantAssignUuid(String merchantAssignUuid) {
		this.merchantAssignUuid = merchantAssignUuid;
	}
	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}
	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}
	public UserDTO getUserDTO() {
		return userDTO;
	}
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getAssignTime() {
		return assignTime;
	}
	@JsonDeserialize(using=JsonDateTimeDeserializer.class)
	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}
	public BigDecimal getTranAmount() {
		return tranAmount;
	}
	public void setTranAmount(BigDecimal tranAmount) {
		this.tranAmount = tranAmount;
	}
	public String getAssignNo() {
		return assignNo;
	}
	public void setAssignNo(String assignNo) {
		this.assignNo = assignNo;
	}
	public int getAssignPoint() {
		return assignPoint;
	}
	public void setAssignPoint(int assignPoint) {
		this.assignPoint = assignPoint;
	}

	private UserDTO userDTO;
	
	private Date assignTime;
	
	private BigDecimal tranAmount;
	
	private String assignNo;

	public int getMerchantPointBefore() {
		return merchantPointBefore;
	}
	public void setMerchantPointBefore(int merchantPointBefore) {
		this.merchantPointBefore = merchantPointBefore;
	}
	public int getMerchantPointAfter() {
		return merchantPointAfter;
	}
	public void setMerchantPointAfter(int merchantPointAfter) {
		this.merchantPointAfter = merchantPointAfter;
	}
	public int getUserPointBefore() {
		return userPointBefore;
	}
	public void setUserPointBefore(int userPointBefore) {
		this.userPointBefore = userPointBefore;
	}
	public int getUserPointAfter() {
		return userPointAfter;
	}
	public void setUserPointAfter(int userPointAfter) {
		this.userPointAfter = userPointAfter;
	}

	private int assignPoint;
	
	private int merchantPointBefore;
	
	private int merchantPointAfter;
	
	private int userPointBefore;
	
	private int userPointAfter;
}
