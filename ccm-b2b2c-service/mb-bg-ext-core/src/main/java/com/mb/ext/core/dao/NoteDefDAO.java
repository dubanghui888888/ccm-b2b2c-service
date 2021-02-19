package com.mb.ext.core.dao;

import com.mb.ext.core.entity.NoteDefEntity;
import com.mb.framework.exception.DAOException;



public interface NoteDefDAO
{
	NoteDefEntity getNoteDefByType(String noteType) throws DAOException;
}
