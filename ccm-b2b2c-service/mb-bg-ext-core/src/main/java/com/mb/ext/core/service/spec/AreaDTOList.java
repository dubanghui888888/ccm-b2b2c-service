package com.mb.ext.core.service.spec;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class AreaDTOList extends AbstractBaseDTO{
	
	private static final long serialVersionUID = 1262614150217776706L;

	private List<AreaDTO> areas;

	public List<AreaDTO> getAreas() {
		return areas;
	}

	public void setAreas(List<AreaDTO> areas) {
		this.areas = areas;
	}
	
}
