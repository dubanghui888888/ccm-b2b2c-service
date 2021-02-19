
package com.mb.ext.core.service.spec;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysLogDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private List<SysLogDTO> logs = new ArrayList<SysLogDTO>();

	int total;
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<SysLogDTO> getLogs() {
		return logs;
	}

	public void setLogs(List<SysLogDTO> logs) {
		this.logs = logs;
	} 
	
}
