package com.mb.ext.core.dao.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.ApplicationDAO;
import com.mb.ext.core.entity.ApplicationEntity;
import com.mb.framework.dao.AbstractDAO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("applicationDAO")
@Qualifier("applicationDAO")
public class ApplicationDAOImpl extends AbstractDAO<ApplicationEntity> implements ApplicationDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public ApplicationDAOImpl()
	{
		super();
		this.setEntityClass(ApplicationEntity.class);
	}

	@Override
	public void addApplication(ApplicationEntity applicationEntity)
			throws DAOException {
		
		save(applicationEntity);
		
	}

	

}
