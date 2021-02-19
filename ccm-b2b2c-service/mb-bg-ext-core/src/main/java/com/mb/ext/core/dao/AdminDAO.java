package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.AdminEntity;
import com.mb.framework.exception.DAOException;


public interface AdminDAO
{
	/**
	 * 
	 * This method is used to add admin to enterprise.
	 * 
	 * @param adminEntity
	 */
	void addAdmin(AdminEntity adminEntity) throws DAOException;
	
	void updateAdmin(AdminEntity adminEntity) throws DAOException;
	
	void deleteAdmin(AdminEntity adminEntity) throws DAOException;
	
	public AdminEntity getAdminById(String id) throws DAOException;
	
	public AdminEntity getAdminByUuid(String uuid) throws DAOException;
	
	public List<AdminEntity> getAdmins() throws DAOException;
	
	public AdminEntity getAdminByTokenId(String tokenId) throws DAOException;
	
}
