package com.mb.ext.core.dao.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.AdminRoleDAO;
import com.mb.ext.core.entity.AdminRoleEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("adminRoleDAO")
@Qualifier("adminRoleDAO")
public class AdminRoleDAOImpl extends AbstractBaseDAO<AdminRoleEntity> implements AdminRoleDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public AdminRoleDAOImpl()
	{
		super();
		this.setEntityClass(AdminRoleEntity.class);
	}

	@Override
	public void addAdminRole(AdminRoleEntity adminRoleEntity) throws DAOException {
		
		save(adminRoleEntity);
		
	}

	@Override
	public void deleteAdminRole(AdminRoleEntity adminRoleEntity)
			throws DAOException {
		 
		deletePhysical(adminRoleEntity);
		
	}



}
