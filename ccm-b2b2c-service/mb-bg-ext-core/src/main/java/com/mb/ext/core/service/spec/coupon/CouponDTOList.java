
package com.mb.ext.core.service.spec.coupon;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class CouponDTOList extends AbstractBaseDTO
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<CouponDTO> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<CouponDTO> coupons) {
		this.coupons = coupons;
	}

	private List<CouponDTO> coupons = new ArrayList<CouponDTO>();
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	private int total;
}
