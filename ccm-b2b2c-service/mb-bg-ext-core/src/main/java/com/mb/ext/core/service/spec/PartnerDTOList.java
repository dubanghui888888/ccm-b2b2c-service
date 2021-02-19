
package com.mb.ext.core.service.spec;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
public class PartnerDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private List<PartnerDTO> partnerList = new ArrayList<PartnerDTO>();
	
	private int total;

	public List<PartnerDTO> getPartnerList() {
		return partnerList;
	}

	public void setPartnerList(List<PartnerDTO> partnerList) {
		this.partnerList = partnerList;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
