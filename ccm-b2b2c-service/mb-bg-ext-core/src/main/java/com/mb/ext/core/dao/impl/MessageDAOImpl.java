package com.mb.ext.core.dao.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.MessageDAO;
import com.mb.ext.core.entity.MessageEntity;
import com.mb.framework.dao.AbstractDAO;
import com.mb.framework.util.log.LogHelper;

@Repository("messageDAO")
@Qualifier("messageDAO")
public class MessageDAOImpl extends AbstractDAO<MessageEntity> implements MessageDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public MessageDAOImpl()
	{
		super();
		this.setEntityClass(MessageEntity.class);
	}

	

}
