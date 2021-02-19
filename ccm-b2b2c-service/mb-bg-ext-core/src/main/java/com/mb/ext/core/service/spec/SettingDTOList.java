
package com.mb.ext.core.service.spec;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class SettingDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	List<SettingDTO> settings = new ArrayList<SettingDTO>();

	public List<SettingDTO> getSettings() {
		return settings;
	}

	public void setSettings(List<SettingDTO> settings) {
		this.settings = settings;
	}
	
	
	
	

}
