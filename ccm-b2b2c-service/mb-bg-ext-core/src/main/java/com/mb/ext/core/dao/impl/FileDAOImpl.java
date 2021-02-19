package com.mb.ext.core.dao.impl;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.FileDAO;
import com.mb.ext.core.entity.FileEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("fileDAO")
@Qualifier("fileDAO")
public class FileDAOImpl extends AbstractBaseDAO<FileEntity> implements FileDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public FileDAOImpl()
	{
		super();
		this.setEntityClass(FileEntity.class);
	}

	@Override
	public void createFile(FileEntity fileEntity) throws DAOException {
		this.save(fileEntity);
		
	}

	@Override
	public void updateFile(FileEntity fileEntity) throws DAOException {
		this.update(fileEntity);
		
	}

	@Override
	public void deleteFile(FileEntity fileEntity) throws DAOException {
		this.deletePhysical(fileEntity);
		
	}

	@Override
	public FileEntity getFileByUuid(String uuid) throws DAOException {
		FileEntity fileEntity = null;
		try {
			fileEntity = (FileEntity) em
					.createQuery(
							"select b from FileEntity b where b.fileUuid = :UUID and b.isDeleted=:isDeleted",
							FileEntity.class)
					.setParameter("UUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for file: " + uuid);
		}
		return fileEntity;
	}



}
