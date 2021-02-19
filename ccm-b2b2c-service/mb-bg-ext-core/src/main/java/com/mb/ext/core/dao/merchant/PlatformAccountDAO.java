package com.mb.ext.core.dao.merchant;

import java.util.List;

import com.mb.ext.core.entity.merchant.PlatformAccountEntity;
import com.mb.framework.exception.DAOException;



public interface PlatformAccountDAO
{
	void createPlatformAccount(PlatformAccountEntity platformAccountEntity) throws DAOException;
	
	void updatePlatformAccount(PlatformAccountEntity platformAccountEntity) throws DAOException;
	
	void deletePlatformAccount(PlatformAccountEntity platformAccountEntity) throws DAOException;
	
	List<PlatformAccountEntity> getPlatformAccount() throws DAOException;
	
	PlatformAccountEntity getAccountByUuid(String uuid) throws DAOException;
	
}
