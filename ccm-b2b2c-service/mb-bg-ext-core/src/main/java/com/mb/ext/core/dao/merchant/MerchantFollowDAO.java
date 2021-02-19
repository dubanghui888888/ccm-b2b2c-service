package com.mb.ext.core.dao.merchant;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.merchant.MerchantFollowEntity;
import com.mb.ext.core.entity.merchant.MerchantUserEntity;
import com.mb.framework.exception.DAOException;

import java.util.List;


public interface MerchantFollowDAO
{
	void createMerchantFollow(MerchantFollowEntity merchantUserEntity) throws DAOException;
	
	void updateMerchantFollow(MerchantFollowEntity merchantUserEntity) throws DAOException;
	
	void deleteMerchantFollow(MerchantFollowEntity merchantUserEntity) throws DAOException;
	
	List<UserEntity> getFollowsByMerchant(MerchantEntity merchantEntity) throws DAOException;

	List<MerchantEntity> getFollowedMerchantsByUser(UserEntity userEntity) throws DAOException;
	
	MerchantFollowEntity getMerchantFollow(MerchantEntity merchantEntity, UserEntity userEntity) throws DAOException;
	
	List<MerchantEntity> searchMerchantByFollow(UserEntity userEntity, int startIndex, int pageSize) throws DAOException;
	
	int searchMerchantByFollowTotal(UserEntity userEntity) throws DAOException;

	List<UserEntity> searchFollowByMerchant(MerchantEntity merchantEntity, int startIndex, int pageSize) throws DAOException;

	int searchFollowByMerchantTotal(MerchantEntity merchantEntity) throws DAOException;
}
