
package com.mb.ext.core.service.spec.profit;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserLevelDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfitRecruitDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String profitRecruitUuid;
	
	private BigDecimal profit;
	
	public String getProfitRecruitUuid() {
		return profitRecruitUuid;
	}

	public void setProfitRecruitUuid(String profitRecruitUuid) {
		this.profitRecruitUuid = profitRecruitUuid;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public UserLevelDTO getRecruitUserLevelDTO() {
		return recruitUserLevelDTO;
	}

	public void setRecruitUserLevelDTO(UserLevelDTO recruitUserLevelDTO) {
		this.recruitUserLevelDTO = recruitUserLevelDTO;
	}

	public UserLevelDTO getProfitUserLevelDTO() {
		return profitUserLevelDTO;
	}

	public void setProfitUserLevelDTO(UserLevelDTO profitUserLevelDTO) {
		this.profitUserLevelDTO = profitUserLevelDTO;
	}

	private UserLevelDTO recruitUserLevelDTO;
	
	private UserLevelDTO profitUserLevelDTO;
}
