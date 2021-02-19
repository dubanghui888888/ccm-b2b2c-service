package com.mb.ext.core.dao.merchant;

import java.util.List;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.merchant.MerchantUserEntity;
import com.mb.framework.exception.DAOException;



public interface MerchantUserDAO
{
	void createMerchantUser(MerchantUserEntity merchantUserEntity) throws DAOException;
	
	void updateMerchantUser(MerchantUserEntity merchantUserEntity) throws DAOException;
	
	void deleteMerchantUser(MerchantUserEntity merchantUserEntity) throws DAOException;
	
	List<UserEntity> getUsersByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	MerchantUserEntity getMerchantUser(MerchantEntity merchantEntity, UserEntity userEntity) throws DAOException;
	
	List<MerchantEntity> searchMerchantByUser(UserEntity userEntity, int startIndex, int pageSize) throws DAOException;
	
	int searchMerchantByUserTotal(UserEntity userEntity) throws DAOException;
}
