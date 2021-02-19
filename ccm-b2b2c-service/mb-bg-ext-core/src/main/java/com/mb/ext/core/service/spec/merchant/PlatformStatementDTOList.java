
package com.mb.ext.core.service.spec.merchant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlatformStatementDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private List<PlatformStatementDTO> statements = new ArrayList<PlatformStatementDTO>();
	
	private BigDecimal totalTranAmount;

	public BigDecimal getTotalTranAmount() {
		return totalTranAmount;
	}

	public void setTotalTranAmount(BigDecimal totalTranAmount) {
		this.totalTranAmount = totalTranAmount;
	}

	public List<PlatformStatementDTO> getStatements() {
		return statements;
	}

	public void setStatements(List<PlatformStatementDTO> statements) {
		this.statements = statements;
	}
	
}
