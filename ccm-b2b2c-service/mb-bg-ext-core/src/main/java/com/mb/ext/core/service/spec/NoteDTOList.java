
package com.mb.ext.core.service.spec;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class NoteDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private List<NoteDTO> notes = new ArrayList<NoteDTO>();

	private int unreadCount;
	
	public int getUnreadCount() {
		return unreadCount;
	}

	public void setUnreadCount(int unreadCount) {
		this.unreadCount = unreadCount;
	}

	public List<NoteDTO> getNotes() {
		return notes;
	}

	public void setNotes(List<NoteDTO> notes) {
		this.notes = notes;
	}

}
