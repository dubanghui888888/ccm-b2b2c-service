
package com.mb.ext.core.service.spec.coupon;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class UserCouponDTO extends AbstractBaseDTO
{
	private static final long serialVersionUID = 1L;
	
	private String userCouponUuid;

	private String receiveChannel;
	
	private Date receiveTime;
	
	private String barCodeUrl;
	
	private Date startDate;
	
	private Date endDate;
	
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getBarCodeUrl() {
		return barCodeUrl;
	}

	public void setBarCodeUrl(String barCodeUrl) {
		this.barCodeUrl = barCodeUrl;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getReceiveTime() {
		return receiveTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	private boolean isUsed;
	
	private Date useTime;
	
	private String useChannel;
	
	public String getUserCouponUuid() {
		return userCouponUuid;
	}

	public void setUserCouponUuid(String userCouponUuid) {
		this.userCouponUuid = userCouponUuid;
	}

	public String getReceiveChannel() {
		return receiveChannel;
	}

	public void setReceiveChannel(String receiveChannel) {
		this.receiveChannel = receiveChannel;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getUseTime() {
		return useTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public String getUseChannel() {
		return useChannel;
	}

	public void setUseChannel(String useChannel) {
		this.useChannel = useChannel;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public CouponDTO getCouponDTO() {
		return couponDTO;
	}

	public void setCouponDTO(CouponDTO couponDTO) {
		this.couponDTO = couponDTO;
	}

	private UserDTO userDTO;
	
	private CouponDTO couponDTO;

}
