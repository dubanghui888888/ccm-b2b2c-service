
package com.mb.ext.core.service.spec;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminDTOList extends AbstractBaseDTO
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2812467474386139963L;
	private List<AdminDTO> admins = new ArrayList<AdminDTO>();

	public List<AdminDTO> getAdmins() {
		return admins;
	}

	public void setAdmins(List<AdminDTO> admins) {
		this.admins = admins;
	}
}
