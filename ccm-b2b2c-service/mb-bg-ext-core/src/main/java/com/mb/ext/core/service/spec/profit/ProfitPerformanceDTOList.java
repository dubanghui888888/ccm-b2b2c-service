
package com.mb.ext.core.service.spec.profit;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserLevelDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfitPerformanceDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private List<ProfitPerformanceDTO> performanceList = new ArrayList<ProfitPerformanceDTO>();

	public List<ProfitPerformanceDTO> getPerformanceList() {
		return performanceList;
	}

	public void setPerformanceList(List<ProfitPerformanceDTO> performanceList) {
		this.performanceList = performanceList;
	}
	
	UserLevelDTO profitUserLevel;

	public UserLevelDTO getProfitUserLevel() {
		return profitUserLevel;
	}

	public void setProfitUserLevel(UserLevelDTO profitUserLevel) {
		this.profitUserLevel = profitUserLevel;
	}
	
}
