
package com.mb.ext.core.service.spec.coupon;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class CouponCategoryDTO extends AbstractBaseDTO
{
	private static final long serialVersionUID = 1L;
	
	private String couponCategoryName;

	public String getCouponCategoryName() {
		return couponCategoryName;
	}

	public void setCouponCategoryName(String couponCategoryName) {
		this.couponCategoryName = couponCategoryName;
	}

}
