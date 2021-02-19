package com.mb.ext.core.dao.merchant;

import java.util.List;

import com.mb.ext.core.entity.merchant.MerchantAccountEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.framework.exception.DAOException;



public interface MerchantAccountDAO
{
	void createMerchantAccount(MerchantAccountEntity merchantAccountEntity) throws DAOException;
	
	void updateMerchantAccount(MerchantAccountEntity merchantAccountEntity) throws DAOException;
	
	void deleteMerchantAccount(MerchantAccountEntity merchantAccountEntity) throws DAOException;
	
	List<MerchantAccountEntity> getAccountByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	MerchantAccountEntity getAccountByUuid(String uuid) throws DAOException;
	
}
