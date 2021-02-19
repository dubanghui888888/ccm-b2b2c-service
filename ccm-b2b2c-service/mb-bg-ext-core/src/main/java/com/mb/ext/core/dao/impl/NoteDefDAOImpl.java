package com.mb.ext.core.dao.impl;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.NoteDefDAO;
import com.mb.ext.core.entity.NoteDefEntity;
import com.mb.ext.core.entity.RoleEntity;
import com.mb.framework.dao.AbstractDAO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("noteDefDAO")
@Qualifier("noteDefDAO")
public class NoteDefDAOImpl extends AbstractDAO<NoteDefEntity> implements NoteDefDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public NoteDefDAOImpl()
	{
		super();
		this.setEntityClass(NoteDefEntity.class);
	}

	@Override
	public NoteDefEntity getNoteDefByType(String noteType) throws DAOException {
		NoteDefEntity noteDefEntity = null;
		try {
			noteDefEntity = (NoteDefEntity) em
					.createQuery(
							"select b from NoteDefEntity b where b.noteType = :NOTETYPE and b.isDeleted=:isDeleted",
							NoteDefEntity.class)
					.setParameter("NOTETYPE", noteType)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for note def: " + noteType);
		}
		return noteDefEntity;
	}

	

}
