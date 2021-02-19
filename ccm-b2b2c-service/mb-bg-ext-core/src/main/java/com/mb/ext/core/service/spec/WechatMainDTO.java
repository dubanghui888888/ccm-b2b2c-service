
package com.mb.ext.core.service.spec;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WechatMainDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	public EntDTO getEntDTO() {
		return entDTO;
	}

	public void setEntDTO(EntDTO entDTO) {
		this.entDTO = entDTO;
	}

	private List<NoteDTO> notes;
	
	public List<NoteDTO> getNotes() {
		return notes;
	}

	public void setNotes(List<NoteDTO> notes) {
		this.notes = notes;
	}

	private List<SettingDTO> settings;
	
	public List<SettingDTO> getSettings() {
		return settings;
	}

	public void setSettings(List<SettingDTO> settings) {
		this.settings = settings;
	}

	private EntDTO entDTO;
	
	private UserDTO userDTO;

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	

}
