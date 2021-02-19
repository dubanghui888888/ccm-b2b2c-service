package com.mb.ext.core.dao;

import com.mb.ext.core.entity.NoteChannelEntity;
import com.mb.framework.exception.DAOException;



public interface NoteChannelDAO
{
	void createNoteChannel(NoteChannelEntity noteChannelEntity) throws DAOException;
	
	void updateNoteChannel(NoteChannelEntity noteChannelEntity) throws DAOException;
	
	void deleteNoteChannel(NoteChannelEntity noteChannelEntity) throws DAOException;
	
	NoteChannelEntity getNoteChannelByType(String noteType) throws DAOException;
}
