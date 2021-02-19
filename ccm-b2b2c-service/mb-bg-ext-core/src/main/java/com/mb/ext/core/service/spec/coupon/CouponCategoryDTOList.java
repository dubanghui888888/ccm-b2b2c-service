
package com.mb.ext.core.service.spec.coupon;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class CouponCategoryDTOList extends AbstractBaseDTO
{
	private static final long serialVersionUID = 1L;
	
	private List<CouponCategoryDTO> couponCategoryDTOList = new ArrayList<CouponCategoryDTO>();

	public List<CouponCategoryDTO> getCouponCategoryDTOList() {
		return couponCategoryDTOList;
	}

	public void setCouponCategoryDTOList(
			List<CouponCategoryDTO> couponCategoryDTOList) {
		this.couponCategoryDTOList = couponCategoryDTOList;
	}

	

}
