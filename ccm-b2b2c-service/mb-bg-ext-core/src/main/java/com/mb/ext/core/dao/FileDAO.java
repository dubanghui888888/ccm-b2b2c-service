package com.mb.ext.core.dao;

import com.mb.ext.core.entity.FileEntity;
import com.mb.framework.exception.DAOException;



public interface FileDAO
{
	void createFile(FileEntity fileEntity) throws DAOException;
	
	void updateFile(FileEntity fileEntity) throws DAOException;
	
	void deleteFile(FileEntity fileEntity) throws DAOException;
	
	FileEntity getFileByUuid(String uuid) throws DAOException;
	
}
