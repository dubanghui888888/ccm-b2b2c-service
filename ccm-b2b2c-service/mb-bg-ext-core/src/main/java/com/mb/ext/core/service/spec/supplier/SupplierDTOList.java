
package com.mb.ext.core.service.spec.supplier;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierDTOList extends AbstractBaseDTO
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2812467474386139963L;
	private List<SupplierDTO> suppliers = new ArrayList<SupplierDTO>();

	
	private int total;
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<SupplierDTO> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(List<SupplierDTO> suppliers) {
		this.suppliers = suppliers;
	}
}
