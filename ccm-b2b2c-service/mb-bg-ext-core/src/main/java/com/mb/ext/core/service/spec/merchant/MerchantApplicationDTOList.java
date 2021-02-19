
package com.mb.ext.core.service.spec.merchant;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantApplicationDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private List<MerchantApplicationDTO> applications = new ArrayList<MerchantApplicationDTO>();
	
	public List<MerchantApplicationDTO> getApplications() {
		return applications;
	}

	public void setApplications(List<MerchantApplicationDTO> applications) {
		this.applications = applications;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	private int total;
}
