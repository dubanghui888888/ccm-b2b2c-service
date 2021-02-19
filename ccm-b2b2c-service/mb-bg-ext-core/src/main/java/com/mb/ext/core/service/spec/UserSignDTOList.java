
package com.mb.ext.core.service.spec;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSignDTOList extends AbstractBaseDTO
{
	private static final long serialVersionUID = -5274244703032974051L;

	List<UserSignDTO> signs = new ArrayList<UserSignDTO>();

	public List<UserSignDTO> getSigns() {
		return signs;
	}

	public void setSigns(List<UserSignDTO> signs) {
		this.signs = signs;
	}
}
