
package com.mb.ext.core.service.spec.coupon;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.util.JsonDateMinuteSerializer;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class CouponWriteOffDTO extends AbstractBaseDTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String couponWriteOffUuid;
	
	private String couponUuid;
	
	private UserCouponDTO userCouponDTO;
	
	private MerchantDTO merchantDTO;
	
	private Date couponWriteOffTime;

	public String getCouponUuid() {
		return couponUuid;
	}

	public void setCouponUuid(String couponUuid) {
		this.couponUuid = couponUuid;
	}

	public String getCouponWriteOffUuid() {
		return couponWriteOffUuid;
	}

	public void setCouponWriteOffUuid(String couponWriteOffUuid) {
		this.couponWriteOffUuid = couponWriteOffUuid;
	}

	public UserCouponDTO getUserCouponDTO() {
		return userCouponDTO;
	}

	public void setUserCouponDTO(UserCouponDTO userCouponDTO) {
		this.userCouponDTO = userCouponDTO;
	}

	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	@JsonSerialize(using=JsonDateMinuteSerializer.class)
	public Date getCouponWriteOffTime() {
		return couponWriteOffTime;
	}
	@JsonSerialize(using=JsonDateSerializer.class) 
	public void setCouponWriteOffTime(Date couponWriteOffTime) {
		this.couponWriteOffTime = couponWriteOffTime;
	}
	
}
