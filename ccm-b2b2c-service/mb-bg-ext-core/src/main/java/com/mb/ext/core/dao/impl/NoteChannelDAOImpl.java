package com.mb.ext.core.dao.impl;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.NoteChannelDAO;
import com.mb.ext.core.entity.NoteChannelEntity;
import com.mb.framework.dao.AbstractDAO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("noteChannelDAO")
@Qualifier("noteChannelDAO")
public class NoteChannelDAOImpl extends AbstractDAO<NoteChannelEntity> implements NoteChannelDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public NoteChannelDAOImpl()
	{
		super();
		this.setEntityClass(NoteChannelEntity.class);
	}

	@Override
	public NoteChannelEntity getNoteChannelByType(String noteType) throws DAOException {
		NoteChannelEntity noteChannelEntity = null;
		try {
			noteChannelEntity = (NoteChannelEntity) em
					.createQuery(
							"select b from NoteChannelEntity b where b.noteType = :NOTETYPE and b.isDeleted=:isDeleted",
							NoteChannelEntity.class)
					.setParameter("NOTETYPE", noteType)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for note channel: " + noteType);
		}
		return noteChannelEntity;
	}

	@Override
	public void createNoteChannel(NoteChannelEntity noteChannelEntity)
			throws DAOException {
		this.save(noteChannelEntity);
		
	}

	@Override
	public void updateNoteChannel(NoteChannelEntity noteChannelEntity)
			throws DAOException {
		this.update(noteChannelEntity);
		
	}

	@Override
	public void deleteNoteChannel(NoteChannelEntity noteChannelEntity)
			throws DAOException {
		this.deletePhysical(noteChannelEntity);
		
	}
}
