
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
public class UserAwardDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private List<UserAwardDTO> awards = new ArrayList<UserAwardDTO>();
	
	private BigDecimal totalTranAmount;
	
	int total;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public BigDecimal getTotalTranAmount() {
		return totalTranAmount;
	}

	public void setTotalTranAmount(BigDecimal totalTranAmount) {
		this.totalTranAmount = totalTranAmount;
	}

	public List<UserAwardDTO> getAwards() {
		return awards;
	}

	public void setAwards(List<UserAwardDTO> awards) {
		this.awards = awards;
	}
	
}
