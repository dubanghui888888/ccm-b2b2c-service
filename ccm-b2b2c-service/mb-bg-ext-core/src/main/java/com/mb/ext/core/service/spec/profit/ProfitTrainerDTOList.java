
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
public class ProfitTrainerDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private List<ProfitTrainerDTO> trainerList = new ArrayList<ProfitTrainerDTO>();

	public List<ProfitTrainerDTO> getTrainerList() {
		return trainerList;
	}

	public void setTrainerList(List<ProfitTrainerDTO> trainerList) {
		this.trainerList = trainerList;
	}
	
	UserLevelDTO profitUserLevel;

	public UserLevelDTO getProfitUserLevel() {
		return profitUserLevel;
	}

	public void setProfitUserLevel(UserLevelDTO profitUserLevel) {
		this.profitUserLevel = profitUserLevel;
	}
	
}
