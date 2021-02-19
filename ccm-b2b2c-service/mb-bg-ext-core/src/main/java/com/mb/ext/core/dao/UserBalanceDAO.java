package com.mb.ext.core.dao;

import java.math.BigDecimal;
import java.util.List;

import com.mb.ext.core.entity.UserBalanceEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.framework.exception.DAOException;



public interface UserBalanceDAO
{
	void createUserBalance(UserBalanceEntity userBalanceEntity) throws DAOException;
	
	void updateUserBalance(UserBalanceEntity userBalanceEntity) throws DAOException;
	
	void deleteUserBalance(UserBalanceEntity userBalanceEntity) throws DAOException;
	
	UserBalanceEntity getBalanceByUser(UserEntity userEntity) throws DAOException;
	
	List<UserBalanceEntity> getUserBalances() throws DAOException;
	
	BigDecimal getTotalAvailableBalance() throws DAOException;
	
}
