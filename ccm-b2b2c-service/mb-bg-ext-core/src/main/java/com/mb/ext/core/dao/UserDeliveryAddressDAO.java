package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.UserDeliveryAddressEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.framework.exception.DAOException;



public interface UserDeliveryAddressDAO
{
	void addUserDeliveryAddress(UserDeliveryAddressEntity userDeliveryAddressEntity) throws DAOException;
	
	void updateUserDeliveryAddress(UserDeliveryAddressEntity userDeliveryAddressEntity) throws DAOException;
	
	void deleteUserDeliveryAddress(UserDeliveryAddressEntity userDeliveryAddressEntity) throws DAOException;
	
	UserDeliveryAddressEntity getDeliveryAddressByUuid(String uuid) throws DAOException;
	
	List<UserDeliveryAddressEntity> getDeliveryAddressByUser(UserEntity userEntity) throws DAOException;

}
