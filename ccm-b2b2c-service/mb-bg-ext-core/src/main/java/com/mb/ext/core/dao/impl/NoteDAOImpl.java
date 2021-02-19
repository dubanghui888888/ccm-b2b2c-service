package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.NoteDAO;
import com.mb.ext.core.entity.NoteEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.framework.dao.AbstractDAO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("noteDAO")
@Qualifier("noteDAO")
public class NoteDAOImpl extends AbstractBaseDAO<NoteEntity> implements NoteDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public NoteDAOImpl()
	{
		super();
		this.setEntityClass(NoteEntity.class);
	}

	@Override
	public void createNote(NoteEntity noteEntity) throws DAOException {
		
		this.save(noteEntity);
		
	}

	@Override
	public void updateNote(NoteEntity noteEntity) throws DAOException {
		
		update(noteEntity);
		
	}

	@Override
	public void deleteNote(NoteEntity noteEntity) throws DAOException {
		this.deletePhysical(noteEntity);
		
	}

	@Override
	public List<NoteEntity> getNotes(UserEntity userEntity) throws DAOException {
		List<NoteEntity> noteEntityList = new ArrayList<NoteEntity>();
		try {
			noteEntityList =  em
					.createQuery(
							"select b from NoteEntity b where b.userEntity.userUuid = :USERUUID and b.isDeleted=:isDeleted order by b.sendTime desc",
							NoteEntity.class)
					.setParameter("USERUUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: " + userEntity.getName());
		}
		return noteEntityList;
	}

	@Override
	public NoteEntity getNote(String noteUuid) throws DAOException {
		NoteEntity noteEntity = new NoteEntity();
		try {
			noteEntity =  (NoteEntity)em
					.createQuery(
							"select b from NoteEntity b where b.noteUuid = :NOTEUUID and b.isDeleted=:isDeleted",
							NoteEntity.class)
					.setParameter("NOTEUUID", noteUuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for note: " + noteUuid);
		}
		return noteEntity;
	}

	@Override
	public int getUnreadNoteCount(UserEntity userEntity) throws DAOException {
		Long count = new Long(0);
		try {
			count =  (Long)em
					.createQuery(
							"select count(b.noteUuid) from NoteEntity b where b.userEntity.userUuid = :USERUUID and b.status = 'UNREAD' and b.isDeleted=:isDeleted",
							Long.class)
					.setParameter("USERUUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for note: " + userEntity.getName());
		}
		return count.intValue();
	}

	@Override
	public void markUserNoteRead(UserEntity userEntity) throws DAOException {
		try {
			
			String updateSql = "update note set status = 'READ' where status = 'UNREAD' and user_uuid = "+"'"+userEntity.getUserUuid()+"'";
			em.createNativeQuery(updateSql).executeUpdate();
			
		} catch (NoResultException e) {
			logger.info("No record found for note: " + userEntity.getUserUuid());
		}
	}

	

}
