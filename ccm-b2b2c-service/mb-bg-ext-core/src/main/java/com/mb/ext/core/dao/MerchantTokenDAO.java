package com.mb.ext.core.dao;

import com.mb.ext.core.entity.MerchantTokenEntity;
import com.mb.framework.exception.DAOException;



public interface MerchantTokenDAO
{
	void addMerchantToken(MerchantTokenEntity userSessionEntity) throws DAOException;
	
	void deleteMerchantToken(MerchantTokenEntity userSessionEntity) throws DAOException;
	
	MerchantTokenEntity findByUserId(String userId) throws DAOException;
	
	MerchantTokenEntity findByTokenId(String tokenId) throws DAOException;
	
	MerchantTokenEntity findByTokenIdEntId(String tokenId, String entId) throws DAOException;
}
