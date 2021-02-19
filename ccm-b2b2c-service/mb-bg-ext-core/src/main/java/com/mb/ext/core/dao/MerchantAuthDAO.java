package com.mb.ext.core.dao;

import com.mb.ext.core.entity.MerchantAuthEntity;
import com.mb.framework.exception.DAOException;



public interface MerchantAuthDAO
{

	void addMerchantAuth(MerchantAuthEntity merchantAuthEntity) throws DAOException;
	
	void deleteMerchantAuth(MerchantAuthEntity merchantAuthEntity) throws DAOException;
	
	void deletePhysicalMerchantAuth(MerchantAuthEntity merchantAuthEntity) throws DAOException;
	
	void updateMerchantAuth(MerchantAuthEntity merchantAuthEntity) throws DAOException;
	
}
