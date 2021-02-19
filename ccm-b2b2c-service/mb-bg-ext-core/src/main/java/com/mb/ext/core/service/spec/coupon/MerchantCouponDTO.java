
package com.mb.ext.core.service.spec.coupon;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class MerchantCouponDTO extends AbstractBaseDTO
{
	private static final long serialVersionUID = 1L;
	
	private String merchantCouponUuid;
	
	private CouponDTO couponDTO;

	private MerchantDTO merchantDTO;
	
	private List<MerchantDTO> merchantList;

	public List<MerchantDTO> getMerchantList() {
		return merchantList;
	}

	public void setMerchantList(List<MerchantDTO> merchantList) {
		this.merchantList = merchantList;
	}

	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	public String getMerchantCouponUuid() {
		return merchantCouponUuid;
	}

	public void setMerchantCouponUuid(String merchantCouponUuid) {
		this.merchantCouponUuid = merchantCouponUuid;
	}

	public CouponDTO getCouponDTO() {
		return couponDTO;
	}

	public void setCouponDTO(CouponDTO couponDTO) {
		this.couponDTO = couponDTO;
	}

}
