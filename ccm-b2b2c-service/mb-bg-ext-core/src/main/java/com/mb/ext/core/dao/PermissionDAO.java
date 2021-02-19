package com.mb.ext.core.dao;

import com.mb.ext.core.entity.PermissionEntity;
import com.mb.framework.exception.DAOException;



public interface PermissionDAO
{
	void addPermission(PermissionEntity permissionEntity) throws DAOException;
	
	void deletePermission(PermissionEntity permissionEntity) throws DAOException;
	
	PermissionEntity getPermissionByRoleFunction(String roleName, String functionCode) throws DAOException;
}
