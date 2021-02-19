
package com.mb.ext.core.service.spec.content;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class MateriaDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private List<MateriaDTO> materias = new ArrayList<MateriaDTO>();

	public List<MateriaDTO> getMaterias() {
		return materias;
	}

	public void setMaterias(List<MateriaDTO> materias) {
		this.materias = materias;
	}

}
