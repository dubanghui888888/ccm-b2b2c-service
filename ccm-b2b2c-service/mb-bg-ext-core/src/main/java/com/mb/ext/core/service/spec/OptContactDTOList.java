
package com.mb.ext.core.service.spec;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class OptContactDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	List<OptContactDTO> contactList = new ArrayList<OptContactDTO>();

	public List<OptContactDTO> getContactList() {
		return contactList;
	}

	public void setContactList(List<OptContactDTO> contactList) {
		this.contactList = contactList;
	}
	

}
