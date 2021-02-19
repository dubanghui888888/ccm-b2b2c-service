
package com.mb.ext.core.service.spec;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
public class TrainerDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private List<TrainerDTO> trainerList = new ArrayList<TrainerDTO>();
	
	private int total;

	public int getTotal() {
		return total;
	}

	public List<TrainerDTO> getTrainerList() {
		return trainerList;
	}

	public void setTrainerList(List<TrainerDTO> trainerList) {
		this.trainerList = trainerList;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
