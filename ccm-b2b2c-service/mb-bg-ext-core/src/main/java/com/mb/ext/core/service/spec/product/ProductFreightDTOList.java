package com.mb.ext.core.service.spec.product;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ProductFreightDTOList extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private List<ProductFreightDTO> freights = new ArrayList<ProductFreightDTO>();

	private int total;

	public List<ProductFreightDTO> getFreights() {
		return freights;
	}

	public void setFreights(List<ProductFreightDTO> freights) {
		this.freights = freights;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
