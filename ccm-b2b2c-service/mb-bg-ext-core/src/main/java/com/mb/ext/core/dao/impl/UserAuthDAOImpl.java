package com.mb.ext.core.dao.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.UserAuthDAO;
import com.mb.ext.core.entity.UserAuthEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("userAuthDAO")
@Qualifier("userAuthDAO")
public class UserAuthDAOImpl extends AbstractBaseDAO<UserAuthEntity> implements UserAuthDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public UserAuthDAOImpl()
	{
		super();
		this.setEntityClass(UserAuthEntity.class);
	}

	@Override
	public void addUserAuth(UserAuthEntity userAuthEntity) throws DAOException {
		
		save(userAuthEntity);
		
	}

	@Override
	public void updateUserAuth(UserAuthEntity userAuthEntity)
			throws DAOException {
		update(userAuthEntity);
		
	}

	@Override
	public void deleteUserAuth(UserAuthEntity userAuthEntity)
			throws DAOException {
		deletePhysical(userAuthEntity);
		
	}

	@Override
	public void deletePhysicalUserAuth(UserAuthEntity userAuthEntity)
			throws DAOException {
		
		deletePhysical(userAuthEntity);
		
	}

	

}
