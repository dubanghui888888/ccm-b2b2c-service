package com.mb.ext.core.service.spec.point;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PointProductCateDTOList extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private List<PointProductCateDTO> cates = new ArrayList<PointProductCateDTO>();

	public List<PointProductCateDTO> getCates() {
		return cates;
	}

	public void setCates(List<PointProductCateDTO> cates) {
		this.cates = cates;
	}
	
}
