
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
public class UserStatementDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private List<UserStatementDTO> statements = new ArrayList<UserStatementDTO>();
	
	private BigDecimal totalTranAmount;
	
	private int total;

	public BigDecimal getTotalTranAmount() {
		return totalTranAmount;
	}

	public void setTotalTranAmount(BigDecimal totalTranAmount) {
		this.totalTranAmount = totalTranAmount;
	}

	public List<UserStatementDTO> getStatements() {
		return statements;
	}

	public void setStatements(List<UserStatementDTO> statements) {
		this.statements = statements;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
}
