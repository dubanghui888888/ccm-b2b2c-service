package com.mb.ext.core.dao;

import com.mb.ext.core.entity.AdminRoleEntity;
import com.mb.framework.exception.DAOException;



public interface AdminRoleDAO
{
	void addAdminRole(AdminRoleEntity adminRoleEntity) throws DAOException;
	
	void deleteAdminRole(AdminRoleEntity adminRoleEntity) throws DAOException;
	
}
