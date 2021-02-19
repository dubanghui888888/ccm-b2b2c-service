
package com.mb.ext.core.service.spec.promote;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PromoteFreightOffDTOList extends AbstractBaseDTO
{



	/**
	 * 
	 */
	private static final long serialVersionUID = -1455362716132844265L;
	
	private List<PromoteFreightOffDTO> freightOffs = new ArrayList<PromoteFreightOffDTO>();

	public List<PromoteFreightOffDTO> getFreightOffs() {
		return freightOffs;
	}

	public void setFreightOffs(List<PromoteFreightOffDTO> freightOffs) {
		this.freightOffs = freightOffs;
	}
	
}
