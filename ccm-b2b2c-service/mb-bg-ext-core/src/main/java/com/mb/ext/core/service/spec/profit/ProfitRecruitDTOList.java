
package com.mb.ext.core.service.spec.profit;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfitRecruitDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private List<ProfitRecruitDTO> recruits = new ArrayList<ProfitRecruitDTO>();

	public List<ProfitRecruitDTO> getRecruits() {
		return recruits;
	}

	public void setRecruits(List<ProfitRecruitDTO> recruits) {
		this.recruits = recruits;
	}
	
}
