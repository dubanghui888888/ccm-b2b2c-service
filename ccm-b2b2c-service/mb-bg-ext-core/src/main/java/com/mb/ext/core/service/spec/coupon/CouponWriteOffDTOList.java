
package com.mb.ext.core.service.spec.coupon;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class CouponWriteOffDTOList extends AbstractBaseDTO
{
	private static final long serialVersionUID = 1L;
	
	private List<CouponWriteOffDTO> couponWriteOffList = new ArrayList<CouponWriteOffDTO>();

	private int total;

	public List<CouponWriteOffDTO> getCouponWriteOffList() {
		return couponWriteOffList;
	}

	public void setCouponWriteOffList(List<CouponWriteOffDTO> couponWriteOffList) {
		this.couponWriteOffList = couponWriteOffList;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
