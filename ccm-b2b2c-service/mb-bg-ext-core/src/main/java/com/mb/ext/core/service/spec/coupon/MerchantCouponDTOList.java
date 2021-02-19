
package com.mb.ext.core.service.spec.coupon;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class MerchantCouponDTOList extends AbstractBaseDTO
{
	private static final long serialVersionUID = 1L;
	
	private List<MerchantCouponDTO> merchantCoupons = new ArrayList<MerchantCouponDTO>();

	public List<MerchantCouponDTO> getMerchantCoupons() {
		return merchantCoupons;
	}

	public void setMerchantCoupons(List<MerchantCouponDTO> merchantCoupons) {
		this.merchantCoupons = merchantCoupons;
	}
	
}
