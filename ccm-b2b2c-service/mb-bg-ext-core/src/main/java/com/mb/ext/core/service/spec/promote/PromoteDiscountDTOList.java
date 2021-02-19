
package com.mb.ext.core.service.spec.promote;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PromoteDiscountDTOList extends AbstractBaseDTO
{



	/**
	 * 
	 */
	private static final long serialVersionUID = -1455362716132844265L;
	
	private List<PromoteDiscountDTO> discounts = new ArrayList<PromoteDiscountDTO>();

	public List<PromoteDiscountDTO> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<PromoteDiscountDTO> discounts) {
		this.discounts = discounts;
	}

	
}
