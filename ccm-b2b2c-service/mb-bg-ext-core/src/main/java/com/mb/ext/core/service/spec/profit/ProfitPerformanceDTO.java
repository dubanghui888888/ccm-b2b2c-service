
package com.mb.ext.core.service.spec.profit;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserLevelDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfitPerformanceDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String profitPerformanceUuid;
	
	private BigDecimal amount;
	
	public UserLevelDTO getProfitUserLevel() {
		return profitUserLevel;
	}

	public void setProfitUserLevel(UserLevelDTO profitUserLevel) {
		this.profitUserLevel = profitUserLevel;
	}

	private BigDecimal rating;
	
	private UserLevelDTO profitUserLevel;

	public String getProfitPerformanceUuid() {
		return profitPerformanceUuid;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public void setProfitPerformanceUuid(String profitPerformanceUuid) {
		this.profitPerformanceUuid = profitPerformanceUuid;
	}
	
}
