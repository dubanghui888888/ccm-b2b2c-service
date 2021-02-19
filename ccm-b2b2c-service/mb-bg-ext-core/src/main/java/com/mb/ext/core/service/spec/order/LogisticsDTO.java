package com.mb.ext.core.service.spec.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class LogisticsDTO  extends AbstractBaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String logisticsUuid;

	private String name;

	public String getLogisticsUuid() {
		return logisticsUuid;
	}

	public void setLogisticsUuid(String logisticsUuid) {
		this.logisticsUuid = logisticsUuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
