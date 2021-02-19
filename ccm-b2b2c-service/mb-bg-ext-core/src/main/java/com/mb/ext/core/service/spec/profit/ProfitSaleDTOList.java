
package com.mb.ext.core.service.spec.profit;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfitSaleDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private List<ProfitSaleDTO> sales = new ArrayList<ProfitSaleDTO>();

	public List<ProfitSaleDTO> getSales() {
		return sales;
	}

	public void setSales(List<ProfitSaleDTO> sales) {
		this.sales = sales;
	}
	
}
