package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserInventoryHistoryEntity;
import com.mb.framework.exception.DAOException;



public interface UserInventoryHistoryDAO
{
	void addUserInventoryHistory(UserInventoryHistoryEntity userInventoryHistoryEntity) throws DAOException;
	
	void updateUserInventoryHistory(UserInventoryHistoryEntity userInventoryHistoryEntity) throws DAOException;
	
	void deleteUserInventoryHistory(UserInventoryHistoryEntity userInventoryHistoryEntity) throws DAOException;
	
	List<UserInventoryHistoryEntity> findByUser(UserEntity userEntity) throws DAOException;
	
	UserInventoryHistoryEntity findByUuid(String uuid) throws DAOException;
	
}
