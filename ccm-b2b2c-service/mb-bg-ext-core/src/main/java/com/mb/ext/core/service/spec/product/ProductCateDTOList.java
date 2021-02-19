package com.mb.ext.core.service.spec.product;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ProductCateDTOList extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private List<ProductCateDTO> cates = new ArrayList<ProductCateDTO>();

	public List<ProductCateDTO> getCates() {
		return cates;
	}

	public void setCates(List<ProductCateDTO> cates) {
		this.cates = cates;
	}
	
}
