
package com.mb.ext.core.service.spec.coupon;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class CouponOrderDTOList extends AbstractBaseDTO
{
	private static final long serialVersionUID = 1L;
	
	private List<CouponOrderDTO> couponOrders = new ArrayList<CouponOrderDTO>();
	
	private int total;
	
	private int totalPoint;

	public int getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<CouponOrderDTO> getCouponOrders() {
		return couponOrders;
	}

	public void setCouponOrders(List<CouponOrderDTO> couponOrders) {
		this.couponOrders = couponOrders;
	}
	

}
