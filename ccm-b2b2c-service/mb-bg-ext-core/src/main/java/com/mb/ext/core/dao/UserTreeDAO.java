package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserTreeEntity;
import com.mb.framework.exception.DAOException;


public interface UserTreeDAO
{
	/**
	 * 
	 * This method is used to add user to enterprise.
	 * 
	 * @param userTreeEntity
	 */
	void addUserTree(UserTreeEntity userTreeEntity) throws DAOException;
	
	void updateUserTree(UserTreeEntity userTreeEntity) throws DAOException;
	
	void deleteUserTree(UserTreeEntity userTreeEntity) throws DAOException;
	
	public UserTreeEntity getUserTreeByUuid(String userTreeUuid) throws DAOException;
	
	public List<UserEntity> getChildUsers(UserEntity userEntity) throws DAOException;
	
	public List<UserTreeEntity> getUserTreeByUserEntity(UserEntity userEntity) throws DAOException;
	
	public List<UserEntity> getParentUsers(UserEntity userEntity) throws DAOException;
	
	void executeInsertUpdateNativeSQL(String sql) throws DAOException;
	
}
