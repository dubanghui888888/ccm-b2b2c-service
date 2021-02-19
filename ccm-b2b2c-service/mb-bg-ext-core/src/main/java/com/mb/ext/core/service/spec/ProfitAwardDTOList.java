
package com.mb.ext.core.service.spec;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfitAwardDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private List<ProfitAwardDTO> awards = new ArrayList<ProfitAwardDTO>();

	public List<ProfitAwardDTO> getAwards() {
		return awards;
	}

	public void setAwards(List<ProfitAwardDTO> awards) {
		this.awards = awards;
	}

	
}
