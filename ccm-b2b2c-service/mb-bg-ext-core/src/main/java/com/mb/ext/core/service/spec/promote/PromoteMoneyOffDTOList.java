
package com.mb.ext.core.service.spec.promote;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PromoteMoneyOffDTOList extends AbstractBaseDTO
{



	/**
	 * 
	 */
	private static final long serialVersionUID = -1455362716132844265L;
	
	private List<PromoteMoneyOffDTO> moneyOffs = new ArrayList<PromoteMoneyOffDTO>();

	public List<PromoteMoneyOffDTO> getMoneyOffs() {
		return moneyOffs;
	}

	public void setMoneyOffs(List<PromoteMoneyOffDTO> moneyOffs) {
		this.moneyOffs = moneyOffs;
	}
	
}
