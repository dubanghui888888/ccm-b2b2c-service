
package com.mb.ext.core.service.spec.coupon;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.util.JsonDateMinuteSerializer;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class CouponOrderDTO extends AbstractBaseDTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String couponOrderUuid;
	
	private UserDTO userDTO;
	
	private CouponDTO couponDTO;
	
	private String userUuid;
	
	private String couponUuid;
	
	private String type;
	
	private Date couponOrderTime;
	
	private Date startDate;
	
	private Date endDate;
	
	private String couponDesc;
	
	private String couponName;
	
	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public int getCouponUnit() {
		return couponUnit;
	}

	public void setCouponUnit(int couponUnit) {
		this.couponUnit = couponUnit;
	}

	public int getCouponPoint() {
		return CouponPoint;
	}

	public void setCouponPoint(int couponPoint) {
		CouponPoint = couponPoint;
	}

	public int getActualPoint() {
		return actualPoint;
	}

	public void setActualPoint(int actualPoint) {
		this.actualPoint = actualPoint;
	}

	public String getCouponImageUrl() {
		return couponImageUrl;
	}

	public void setCouponImageUrl(String couponImageUrl) {
		this.couponImageUrl = couponImageUrl;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public String getCouponUuid() {
		return couponUuid;
	}

	public void setCouponUuid(String couponUuid) {
		this.couponUuid = couponUuid;
	}

	private int couponUnit;
	
	private int CouponPoint;
	
	private int actualPoint;
	
	private String couponImageUrl;

	public String getCouponOrderUuid() {
		return couponOrderUuid;
	}

	public void setCouponOrderUuid(String couponOrderUuid) {
		this.couponOrderUuid = couponOrderUuid;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@JsonSerialize(using=JsonDateMinuteSerializer.class)
	public Date getCouponOrderTime() {
		return couponOrderTime;
	}
	@JsonSerialize(using=JsonDateSerializer.class) 
	public void setCouponOrderTime(Date couponOrderTime) {
		this.couponOrderTime = couponOrderTime;
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

	public String getCouponDesc() {
		return couponDesc;
	}

	public void setCouponDesc(String couponDesc) {
		this.couponDesc = couponDesc;
	}
	
}
