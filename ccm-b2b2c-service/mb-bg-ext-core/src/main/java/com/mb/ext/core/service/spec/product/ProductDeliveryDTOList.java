package com.mb.ext.core.service.spec.product;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ProductDeliveryDTOList extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private List<ProductDeliveryDTO> deliverys = new ArrayList<ProductDeliveryDTO>();

	private int total;

	public int getTotal() {
		return total;
	}

	public List<ProductDeliveryDTO> getDeliverys() {
		return deliverys;
	}

	public void setDeliverys(List<ProductDeliveryDTO> deliverys) {
		this.deliverys = deliverys;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
