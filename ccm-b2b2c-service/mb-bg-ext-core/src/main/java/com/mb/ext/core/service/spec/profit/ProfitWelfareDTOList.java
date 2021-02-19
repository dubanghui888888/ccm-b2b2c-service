
package com.mb.ext.core.service.spec.profit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfitWelfareDTOList extends AbstractBaseDTO
{
	
	private List<ProfitWelfareDTO> welfares = new ArrayList<ProfitWelfareDTO>();

	public List<ProfitWelfareDTO> getWelfares() {
		return welfares;
	}

	public void setWelfares(List<ProfitWelfareDTO> welfares) {
		this.welfares = welfares;
	}
}
