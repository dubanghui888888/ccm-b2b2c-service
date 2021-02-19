package com.mb.ext.core.dao.merchant;

import com.mb.ext.core.entity.merchant.PlatformBalanceEntity;
import com.mb.framework.exception.DAOException;



public interface PlatformBalanceDAO
{
	void createPlatformBalance(PlatformBalanceEntity platformBalanceEntity) throws DAOException;
	
	void updatePlatformBalance(PlatformBalanceEntity platformBalanceEntity) throws DAOException;
	
	void deletePlatformBalance(PlatformBalanceEntity platformBalanceEntity) throws DAOException;
	
	PlatformBalanceEntity getPlatformBalance() throws DAOException;

	
}
