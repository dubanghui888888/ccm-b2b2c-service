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
 * The persistent class for the message database table.
 * 
 */
@Entity
@Table(name = "message")
@NamedQuery(name = "MessageEntity.findAll", query = "SELECT u FROM MessageEntity u")
public class MessageEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "MESSAGE_UUID")
	@GenericGenerator(name = "MESSAGE_UUID", strategy = "uuid")
	@Column(name = "MESSAGE_UUID", nullable = false, length = 36)
	private String messageUuid;

	@Column(name = "CONTENT", nullable = false, length = 500)
	private String content;
	
	@Column(name = "SEND_TIME")
	private Date sendTime;
	
	

	public String getMessageUuid() {
		return messageUuid;
	}

	public void setMessageUuid(String messageUuid) {
		this.messageUuid = messageUuid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public UserEntity getFromUserEntity() {
		return fromUserEntity;
	}

	public void setFromUserEntity(UserEntity fromUserEntity) {
		this.fromUserEntity = fromUserEntity;
	}

	public UserEntity getToUserEntity() {
		return toUserEntity;
	}

	public void setToUserEntity(UserEntity toUserEntity) {
		this.toUserEntity = toUserEntity;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FROM_USER_UUID")
	private UserEntity fromUserEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TO_USER_UUID")
	private UserEntity toUserEntity;


	
}