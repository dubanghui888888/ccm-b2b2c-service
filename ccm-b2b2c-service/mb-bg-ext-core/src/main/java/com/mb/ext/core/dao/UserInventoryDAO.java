package com.mb.ext.core.dao;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserInventoryEntity;
import com.mb.framework.exception.DAOException;



public interface UserInventoryDAO
{
	void addUserInventory(UserInventoryEntity userSessionEntity) throws DAOException;
	
	void deleteUserInventory(UserInventoryEntity userSessionEntity) throws DAOException;
	
	void updateUserInventory(UserInventoryEntity userSessionEntity) throws DAOException;
	
	UserInventoryEntity findByUser(UserEntity userEntity) throws DAOException;
	
	int getTotalUserInventory() throws DAOException;
}
