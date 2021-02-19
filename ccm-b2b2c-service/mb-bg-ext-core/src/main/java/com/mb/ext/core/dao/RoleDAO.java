package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.RoleEntity;
import com.mb.framework.exception.DAOException;



public interface RoleDAO
{
	RoleEntity getRoleByName(String name) throws DAOException;
	
	RoleEntity getRoleByNameAndEnt(String name, String entId) throws DAOException;
	
	List<RoleEntity> getRoles() throws DAOException;
	
	List<RoleEntity> getRolesByEnt(String entId) throws DAOException;
	
	void addRole(RoleEntity roleEntity) throws DAOException;
	
	void updateRole(RoleEntity roleEntity) throws DAOException;
	
	void deleteRole(RoleEntity roleEntity) throws DAOException;
}
