package com.mb.ext.core.entity;

import java.util.Date;

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
 * The persistent class for the note database table.
 * 
 */
@Entity
@Table(name = "note")
@NamedQuery(name = "NoteEntity.findAll", query = "SELECT u FROM NoteEntity u")
public class NoteEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "NOTE_UUID")
	@GenericGenerator(name = "NOTE_UUID", strategy = "uuid")
	@Column(name = "NOTE_UUID", nullable = false, length = 36)
	private String noteUuid;

	@Column(name = "CONTENT", length = 1000)
	private String content;
	
	@Column(name = "STATUS", nullable = false, length = 15)
	private String status;
	
	@Column(name = "APPLICATION", nullable = false, length = 60)
	private String application;
	
	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	@Column(name = "ACTION", nullable = false, length = 45)
	private String action;
	
	@Column(name = "SEND_TIME")
	private Date sendTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;


	public String getNoteUuid() {
		return noteUuid;
	}

	public void setNoteUuid(String noteUuid) {
		this.noteUuid = noteUuid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
}