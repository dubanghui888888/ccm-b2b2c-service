package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.UserDeliveryEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.UserDeliverySearchDTO;
import com.mb.framework.exception.DAOException;



public interface UserDeliveryDAO
{
	void addUserDelivery(UserDeliveryEntity userDeliveryEntity) throws DAOException;
	
	void deleteUserDelivery(UserDeliveryEntity userDeliveryEntity) throws DAOException;
	
	void updateUserDelivery(UserDeliveryEntity userDeliveryEntity) throws DAOException;
	
	UserDeliveryEntity findByUuid(String uuid) throws DAOException;
	
	List<UserDeliveryEntity> findByUser(UserEntity userEntity) throws DAOException;
	
	List<UserDeliveryEntity> findByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	List<UserDeliveryEntity> findAll() throws DAOException;
	
	List<UserDeliveryEntity> searchUserDelivery(UserDeliverySearchDTO userDeliverySearchDTO, int startIndex, int pageSize) throws DAOException;
	
	int searchUserDeliveryTotal(UserDeliverySearchDTO userDeliverySearchDTO) throws DAOException;
	
	int getPendingDeliveryQuantity() throws DAOException;
	
}
