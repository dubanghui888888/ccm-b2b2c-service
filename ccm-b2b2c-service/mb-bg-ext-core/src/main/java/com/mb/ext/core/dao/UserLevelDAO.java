package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.UserLevelEntity;
import com.mb.framework.exception.DAOException;


public interface UserLevelDAO
{
	/**
	 * 
	 * This method is used to add user to enterprise.
	 * 
	 * @param userLevelEntity
	 */
	void addUserLevel(UserLevelEntity userLevelEntity) throws DAOException;
	
	void updateUserLevel(UserLevelEntity userLevelEntity) throws DAOException;
	
	void deleteUserLevel(UserLevelEntity userLevelEntity) throws DAOException;
	
	public UserLevelEntity getUserLevelByName(String name) throws DAOException;
	
	public UserLevelEntity getUserLevelByUuid(String userLevelUuid) throws DAOException;
	
	public UserLevelEntity getUserLevelByDepth(int depth) throws DAOException;
	
	public UserLevelEntity getParentUserLevel(UserLevelEntity levelEntity) throws DAOException;

	public UserLevelEntity getChildUserLevel(UserLevelEntity levelEntity) throws DAOException;
	
	public UserLevelEntity getDefaultUserLevel() throws DAOException;
	
	public List<UserLevelEntity> getUserLevels() throws DAOException;
	
}
