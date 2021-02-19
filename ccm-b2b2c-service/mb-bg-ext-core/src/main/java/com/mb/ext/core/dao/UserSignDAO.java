package com.mb.ext.core.dao;

import java.util.Date;
import java.util.List;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserSignEntity;
import com.mb.framework.exception.DAOException;



public interface UserSignDAO
{
	void createUserSign(UserSignEntity userSignEntity) throws DAOException;
	
	UserSignEntity getUserSignByDate(UserEntity userEntity, Date signDate) throws DAOException;
	
	UserSignEntity getLatestUserSign(UserEntity userEntity) throws DAOException;
	
	List<UserSignEntity> getSignByUser(UserEntity userEntity) throws DAOException;
	
}
