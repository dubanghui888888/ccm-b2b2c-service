
package com.mb.ext.core.service.spec;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class UserInventoryHistoryDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private List<UserInventoryHistoryDTO> histories = new ArrayList<UserInventoryHistoryDTO>();

	public List<UserInventoryHistoryDTO> getHistories() {
		return histories;
	}

	public void setHistories(List<UserInventoryHistoryDTO> histories) {
		this.histories = histories;
	}
	
}
