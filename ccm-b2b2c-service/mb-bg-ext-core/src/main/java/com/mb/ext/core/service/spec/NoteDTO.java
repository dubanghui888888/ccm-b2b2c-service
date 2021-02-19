
package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateTimeSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class NoteDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	public String getNoteUuid() {
		return noteUuid;
	}

	public void setNoteUuid(String noteUuid) {
		this.noteUuid = noteUuid;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	private String application;
	
	private String content;
	
	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	private String action;
	
	private String noteUuid;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNoteStatus() {
		return noteStatus;
	}

	public void setNoteStatus(String noteStatus) {
		this.noteStatus = noteStatus;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	public Date getSendTime() {
		return sendTime;
	}
	@JsonSerialize(using=JsonDateTimeSerializer.class) 
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	private String noteStatus;
	
	private Date sendTime;

}
