
package com.mb.ext.core.service.spec;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfitAwardDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String profitAwardUuid;
	
	private int conditionUserUnit;
	
	private int profitProductUnit;
	
	public String getProfitAwardUuid() {
		return profitAwardUuid;
	}

	public void setProfitAwardUuid(String profitAwardUuid) {
		this.profitAwardUuid = profitAwardUuid;
	}

	public int getConditionUserUnit() {
		return conditionUserUnit;
	}

	public void setConditionUserUnit(int conditionUserUnit) {
		this.conditionUserUnit = conditionUserUnit;
	}

	public int getProfitProductUnit() {
		return profitProductUnit;
	}

	public void setProfitProductUnit(int profitProductUnit) {
		this.profitProductUnit = profitProductUnit;
	}

	public void setUserLevelName(String userLevelName){
		
	}
	
}
