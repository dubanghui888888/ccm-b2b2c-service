package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.NoteEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.framework.exception.DAOException;



public interface NoteDAO
{
	void createNote(NoteEntity noteEntity) throws DAOException;
	
	void updateNote(NoteEntity noteEntity) throws DAOException;
	
	void deleteNote(NoteEntity noteEntity) throws DAOException;
	
	List<NoteEntity> getNotes(UserEntity userEntity) throws DAOException;
	
	NoteEntity getNote(String noteUuid) throws DAOException;
	
	int getUnreadNoteCount(UserEntity userEntity) throws DAOException;
	
	void markUserNoteRead(UserEntity userEntity) throws DAOException;
}
