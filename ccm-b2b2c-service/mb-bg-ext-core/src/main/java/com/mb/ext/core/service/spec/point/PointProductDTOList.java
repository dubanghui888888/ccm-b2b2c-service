package com.mb.ext.core.service.spec.point;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PointProductDTOList extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private List<PointProductDTO> products = new ArrayList<PointProductDTO>();
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	private int total;

	public List<PointProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<PointProductDTO> products) {
		this.products = products;
	}

	
}
