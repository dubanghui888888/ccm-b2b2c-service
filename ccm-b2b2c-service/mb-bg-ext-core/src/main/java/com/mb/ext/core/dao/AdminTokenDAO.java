package com.mb.ext.core.dao;

import com.mb.ext.core.entity.AdminTokenEntity;
import com.mb.framework.exception.DAOException;



public interface AdminTokenDAO
{
	void addAdminToken(AdminTokenEntity adminSessionEntity) throws DAOException;
	
	void deleteAdminToken(AdminTokenEntity adminSessionEntity) throws DAOException;
	
	AdminTokenEntity findByAdminId(String adminId) throws DAOException;
	
	AdminTokenEntity findByTokenId(String tokenId) throws DAOException;
	
	AdminTokenEntity findByTokenIdEntId(String tokenId, String entId) throws DAOException;
}
