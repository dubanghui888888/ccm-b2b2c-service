package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.UserTreeDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserTreeEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("userTreeDAO")
@Qualifier("userTreeDAO")
public class UserTreeDAOImpl extends AbstractBaseDAO<UserTreeEntity> implements UserTreeDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public UserTreeDAOImpl()
	{
		super();
		this.setEntityClass(UserTreeEntity.class);
	}

	/**
	 * This method is used for inserting user information.
	 * 
	 * @param user
	 */
	@Override
	public void addUserTree(UserTreeEntity userTreeEntity) throws DAOException
	{
		save(userTreeEntity);
	}
	
	/**
	 * @param name
	 * @return 
	 * @throws Exception
	 */

	@Override
	public void updateUserTree(UserTreeEntity userTreeEntity) throws DAOException {
		update(userTreeEntity);
		
	}

	@Override
	public void deleteUserTree(UserTreeEntity userTreeEntity) throws DAOException {
		
		deletePhysical(userTreeEntity);
		
	}
	
	@Override
	public List<UserEntity> getChildUsers(UserEntity userEntity) throws DAOException {
		List<UserEntity> userEntityList = new ArrayList<UserEntity>();
		try {
			userEntityList = em.createQuery("select b.userEntity from UserTreeEntity b where b.ancestorEntity.userUuid = :UUID and b.isDeleted=:isDeleted",UserEntity.class)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: ");
		}
		return userEntityList;
	}
	
	@Override
	public List<UserEntity> getParentUsers(UserEntity userEntity) throws DAOException {
		List<UserEntity> userEntityList = new ArrayList<UserEntity>();
		try {
			userEntityList = em.createQuery("select b.ancestorEntity from UserTreeEntity b where b.userEntity.userUuid = :UUID and b.isDeleted=:isDeleted",UserEntity.class)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: ");
		}
		return userEntityList;
	}

	@Override
	public List<UserTreeEntity> getUserTreeByUserEntity(UserEntity userEntity) throws DAOException {
		List<UserTreeEntity> userTreeEntityList = new ArrayList<UserTreeEntity>();
		try {
			userTreeEntityList = em.createQuery("select b from UserTreeEntity b where b.userEntity.userUuid = :UUID and b.isDeleted=:isDeleted",UserTreeEntity.class)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: ");
		}
		return userTreeEntityList;
	}
	
	@Override
	public UserTreeEntity getUserTreeByUuid(String userTreeUuid)
			throws DAOException {
		UserTreeEntity userTreeEntity = null;
		try {
			userTreeEntity = (UserTreeEntity)em.createQuery("select b from UserTreeEntity b where b.userTreeUuid = :UUID and b.isDeleted=:isDeleted",UserTreeEntity.class)
					.setParameter("UUID", userTreeUuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+userTreeUuid);
		}
		return userTreeEntity;
	}
	
	@Override
	public void executeInsertUpdateNativeSQL(String sql) throws DAOException {
		try{
			em.createNativeQuery(sql).executeUpdate();
		}catch(Exception e){
			throw new DAOException(e);
		}
		
	}
}
