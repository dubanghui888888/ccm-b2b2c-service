
package com.mb.ext.core.service.spec.profit;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserLevelDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfitSaleDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String profitSaleUuid;
	
	private BigDecimal profitRate;
	
	public String getProfitSaleUuid() {
		return profitSaleUuid;
	}

	public BigDecimal getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(BigDecimal profitRate) {
		this.profitRate = profitRate;
	}

	public void setProfitSaleUuid(String profitSaleUuid) {
		this.profitSaleUuid = profitSaleUuid;
	}

	public UserLevelDTO getProfitUserLevelDTO() {
		return profitUserLevelDTO;
	}

	public void setProfitUserLevelDTO(UserLevelDTO profitUserLevelDTO) {
		this.profitUserLevelDTO = profitUserLevelDTO;
	}

	private UserLevelDTO profitUserLevelDTO;
}
