package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserWithdrawEntity;
import com.mb.ext.core.service.spec.WithdrawSearchDTO;
import com.mb.framework.exception.DAOException;



public interface UserWithdrawDAO
{
	void createUserWithdraw(UserWithdrawEntity userWithdrawEntity) throws DAOException;
	
	void updateUserWithdraw(UserWithdrawEntity userWithdrawEntity) throws DAOException;
	
	void deleteUserWithdraw(UserWithdrawEntity userWithdrawEntity) throws DAOException;
	
	List<UserWithdrawEntity> getWithdrawByUser(UserEntity userEntity) throws DAOException;
	
	List<UserWithdrawEntity> getWithdrawByStatus(String status) throws DAOException;
	
	List<UserWithdrawEntity> getWithdraws() throws DAOException;
	
	UserWithdrawEntity getWithdrawByUuid(String uuid) throws DAOException;
	
	List<UserWithdrawEntity> searchUserWithdraw(WithdrawSearchDTO withdrawSearchDTO, int startIndex, int pageSize) throws DAOException;
	
	int searchUserWithdrawTotal(WithdrawSearchDTO withdrawSearchDTO) throws DAOException;
	
}
