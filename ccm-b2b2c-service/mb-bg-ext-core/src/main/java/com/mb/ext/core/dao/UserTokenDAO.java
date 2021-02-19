package com.mb.ext.core.dao;

import com.mb.ext.core.entity.UserTokenEntity;
import com.mb.framework.exception.DAOException;



public interface UserTokenDAO
{
	void addUserToken(UserTokenEntity userSessionEntity) throws DAOException;
	
	void deleteUserToken(UserTokenEntity userSessionEntity) throws DAOException;
	
	UserTokenEntity findByUserId(String userId) throws DAOException;
	
	UserTokenEntity findByTokenId(String tokenId) throws DAOException;
	
	UserTokenEntity findByTokenIdEntId(String tokenId, String entId) throws DAOException;
}
