
package com.mb.ext.core.service.spec;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfitUpgradeDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String profitUpgradeUuid;
	
	private int conditionUserUnit;
	
	public String getProfitUpgradeUuid() {
		return profitUpgradeUuid;
	}

	public void setProfitUpgradeUuid(String profitUpgradeUuid) {
		this.profitUpgradeUuid = profitUpgradeUuid;
	}

	public int getConditionUserUnit() {
		return conditionUserUnit;
	}

	public void setConditionUserUnit(int conditionUserUnit) {
		this.conditionUserUnit = conditionUserUnit;
	}

	public void setUserLevelName(String userLevelName){
		
	}
	
}
