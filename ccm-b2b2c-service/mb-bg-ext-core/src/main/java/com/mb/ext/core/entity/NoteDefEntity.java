package com.mb.ext.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the note_def database table.
 * 
 */
@Entity
@Table(name = "note_def")
@NamedQuery(name = "NoteDefEntity.findAll", query = "SELECT u FROM NoteDefEntity u")
public class NoteDefEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	public String getNotedefUuid() {
		return notedefUuid;
	}

	public void setNotedefUuid(String notedefUuid) {
		this.notedefUuid = notedefUuid;
	}

	public String getNoteAction() {
		return noteAction;
	}

	public void setNoteAction(String noteAction) {
		this.noteAction = noteAction;
	}

	public String getNoteType() {
		return noteType;
	}

	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}

	public String getNoteValue() {
		return noteValue;
	}

	public void setNoteValue(String noteValue) {
		this.noteValue = noteValue;
	}

	public String getNoteApplication() {
		return noteApplication;
	}

	public void setNoteApplication(String noteApplication) {
		this.noteApplication = noteApplication;
	}

	@Id
	@GeneratedValue(generator = "NOTEDEF_UUID")
	@GenericGenerator(name = "NOTEDEF_UUID", strategy = "uuid")
	@Column(name = "NOTEDEF_UUID", nullable = false, length = 36)
	private String notedefUuid;

	@Column(name = "NOTE_APPLICATION", nullable = false, length = 45)
	private String noteApplication;
	
	@Column(name = "NOTE_TYPE", nullable = false, length = 45)
	private String noteType;
	
	@Column(name = "NOTE_VALUE", nullable = false, length = 300)
	private String noteValue;
	
	@Column(name = "NOTE_ACTION", length = 45)
	private String noteAction;
	
	
}