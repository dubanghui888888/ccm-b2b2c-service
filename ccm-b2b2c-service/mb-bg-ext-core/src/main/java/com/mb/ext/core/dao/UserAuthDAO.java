package com.mb.ext.core.dao;

import com.mb.ext.core.entity.UserAuthEntity;
import com.mb.framework.exception.DAOException;



public interface UserAuthDAO
{

	void addUserAuth(UserAuthEntity userAuthEntity) throws DAOException;
	
	void deleteUserAuth(UserAuthEntity userAuthEntity) throws DAOException;
	
	void deletePhysicalUserAuth(UserAuthEntity userAuthEntity) throws DAOException;
	
	void updateUserAuth(UserAuthEntity userAuthEntity) throws DAOException;
	
}
