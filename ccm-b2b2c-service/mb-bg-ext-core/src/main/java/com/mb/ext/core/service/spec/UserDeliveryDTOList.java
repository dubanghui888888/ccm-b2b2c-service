
package com.mb.ext.core.service.spec;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
public class UserDeliveryDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private List<UserDeliveryDTO> deliveryList = new ArrayList<UserDeliveryDTO>();
	
	private int total;

	public List<UserDeliveryDTO> getDeliveryList() {
		return deliveryList;
	}

	public void setDeliveryList(List<UserDeliveryDTO> deliveryList) {
		this.deliveryList = deliveryList;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
