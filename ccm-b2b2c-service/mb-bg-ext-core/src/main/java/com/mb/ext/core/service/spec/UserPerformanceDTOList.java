
package com.mb.ext.core.service.spec;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPerformanceDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private List<UserPerformanceDTO> performances = new ArrayList<UserPerformanceDTO>();
	
	int total;
	
	BigDecimal performanceAmount;
	
	public BigDecimal getPerformanceAmount() {
		return performanceAmount;
	}

	public void setPerformanceAmount(BigDecimal performanceAmount) {
		this.performanceAmount = performanceAmount;
	}

	public BigDecimal getPerformanceAward() {
		return performanceAward;
	}

	public void setPerformanceAward(BigDecimal performanceAward) {
		this.performanceAward = performanceAward;
	}

	BigDecimal performanceAward;

	public List<UserPerformanceDTO> getPerformances() {
		return performances;
	}

	public void setPerformances(List<UserPerformanceDTO> performances) {
		this.performances = performances;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
}
