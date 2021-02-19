
package com.mb.ext.core.service.spec.coupon;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class UserCouponDTOList extends AbstractBaseDTO
{
	private static final long serialVersionUID = 1L;
	
	private List<UserCouponDTO> userCoupons = new ArrayList<UserCouponDTO>();
	
	private int total;

	public List<UserCouponDTO> getUserCoupons() {
		return userCoupons;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setUserCoupons(List<UserCouponDTO> userCoupons) {
		this.userCoupons = userCoupons;
	}

}
