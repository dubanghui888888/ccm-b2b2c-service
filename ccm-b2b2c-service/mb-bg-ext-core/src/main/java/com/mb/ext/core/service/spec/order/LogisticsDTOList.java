package com.mb.ext.core.service.spec.order;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class LogisticsDTOList extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private List<LogisticsDTO> logisticses = new ArrayList<LogisticsDTO>();

	public List<LogisticsDTO> getLogisticses() {
		return logisticses;
	}

	public void setLogisticses(List<LogisticsDTO> logisticses) {
		this.logisticses = logisticses;
	}

	
}
