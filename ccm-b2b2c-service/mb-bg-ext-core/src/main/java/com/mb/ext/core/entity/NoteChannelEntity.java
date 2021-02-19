package com.mb.ext.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the note_channel database table.
 * 
 */
@Entity
@Table(name = "note_channel")
@NamedQuery(name = "NoteChannelEntity.findAll", query = "SELECT u FROM NoteChannelEntity u")
public class NoteChannelEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	public String getNotechannelUuid() {
		return notechannelUuid;
	}

	public String getNoteType() {
		return noteType;
	}

	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}

	public boolean isSMSEnabled() {
		return isSMSEnabled;
	}

	public void setSMSEnabled(boolean isSMSEnabled) {
		this.isSMSEnabled = isSMSEnabled;
	}

	public boolean isEmailEnabled() {
		return isEmailEnabled;
	}

	public void setEmailEnabled(boolean isEmailEnabled) {
		this.isEmailEnabled = isEmailEnabled;
	}

	public boolean isWechatEnabled() {
		return isWechatEnabled;
	}

	public void setWechatEnabled(boolean isWechatEnabled) {
		this.isWechatEnabled = isWechatEnabled;
	}

	public void setNotechannelUuid(String notechannelUuid) {
		this.notechannelUuid = notechannelUuid;
	}

	@Id
	@GeneratedValue(generator = "NOTECHANNEL_UUID")
	@GenericGenerator(name = "NOTECHANNEL_UUID", strategy = "uuid")
	@Column(name = "NOTECHANNEL_UUID", nullable = false, length = 36)
	private String notechannelUuid;

	@Column(name = "NOTETYPE", nullable = false, length = 45)
	private String noteType;
	
	@Column(name = "ISSMSENABLED")
	private boolean isSMSEnabled;
	
	@Column(name = "ISEMAILENABLED")
	private boolean isEmailEnabled;
	
	@Column(name = "ISWECHATENABLED")
	private boolean isWechatEnabled;
	
}