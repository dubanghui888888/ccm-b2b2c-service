package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.UserLevelDAO;
import com.mb.ext.core.entity.UserLevelEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("userLevelDAO")
@Qualifier("userLevelDAO")
public class UserLevelDAOImpl extends AbstractBaseDAO<UserLevelEntity> implements UserLevelDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public UserLevelDAOImpl()
	{
		super();
		this.setEntityClass(UserLevelEntity.class);
	}

	/**
	 * This method is used for inserting user information.
	 * 
	 * @param user
	 */
	@Override
	public void addUserLevel(UserLevelEntity userLevelEntity) throws DAOException
	{
		save(userLevelEntity);
	}
	
	/**
	 * @param name
	 * @return 
	 * @throws Exception
	 */

	@Override
	public void updateUserLevel(UserLevelEntity userLevelEntity) throws DAOException {
		update(userLevelEntity);
		
	}

	@Override
	public void deleteUserLevel(UserLevelEntity userLevelEntity) throws DAOException {
		
		deletePhysical(userLevelEntity);
		
	}
	
	@Override
	public UserLevelEntity getUserLevelByName(String name) throws DAOException {
		UserLevelEntity userLevelEntity = null;
		try {
			userLevelEntity = (UserLevelEntity)em.createQuery("select b from UserLevelEntity b where b.name = :NAME and b.isDeleted=:isDeleted",UserLevelEntity.class).setParameter("NAME", name).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+name);
		}
		return userLevelEntity;
	}
	
	@Override
	public List<UserLevelEntity> getUserLevels() throws DAOException {
		List<UserLevelEntity> userLevelEntityList = new ArrayList<UserLevelEntity>();
		try {
			userLevelEntityList = em.createQuery("select b from UserLevelEntity b where b.isDeleted=:isDeleted order by b.depth",UserLevelEntity.class)
					.setParameter("isDeleted", Boolean.FALSE)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: ");
		}
		return userLevelEntityList;
	}

	@Override
	public UserLevelEntity getUserLevelByUuid(String userLevelUuid)
			throws DAOException {
		UserLevelEntity userLevelEntity = null;
		try {
			userLevelEntity = (UserLevelEntity)em.createQuery("select b from UserLevelEntity b where b.userLevelUuid = :UUID and b.isDeleted=:isDeleted",UserLevelEntity.class)
					.setParameter("UUID", userLevelUuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+userLevelUuid);
		}
		return userLevelEntity;
	}
	
	@Override
	public UserLevelEntity getUserLevelByDepth(int depth)
			throws DAOException {
		UserLevelEntity userLevelEntity = null;
		try {
			userLevelEntity = (UserLevelEntity)em.createQuery("select b from UserLevelEntity b where b.depth = :DEPTH and b.isDeleted=:isDeleted",UserLevelEntity.class)
					.setParameter("DEPTH", depth)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user level: "+depth);
		}
		return userLevelEntity;
	}
	
	@Override
	public UserLevelEntity getParentUserLevel(UserLevelEntity levelEntity)
			throws DAOException {
		UserLevelEntity userLevelEntity = null;
		try {
			userLevelEntity = (UserLevelEntity)em.createQuery("select b from UserLevelEntity b where b.depth = :DEPTH and b.isDeleted=:isDeleted",UserLevelEntity.class)
					.setParameter("DEPTH", levelEntity.getDepth()-1)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user level");
		}
		return userLevelEntity;
	}
	
	@Override
	public UserLevelEntity getChildUserLevel(UserLevelEntity levelEntity)
			throws DAOException {
		UserLevelEntity userLevelEntity = null;
		try {
			userLevelEntity = (UserLevelEntity)em.createQuery("select b from UserLevelEntity b where b.depth = :DEPTH and b.isDeleted=:isDeleted",UserLevelEntity.class)
					.setParameter("DEPTH", levelEntity.getDepth()+1)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user level");
		}
		return userLevelEntity;
	}
	
	@Override
	public UserLevelEntity getDefaultUserLevel()
			throws DAOException {
		List<UserLevelEntity> userLevelList = new ArrayList<UserLevelEntity>();
		try {
			userLevelList = em.createQuery("select b from UserLevelEntity b where b.isDefault = :ISDEFAULT and b.isDeleted=:isDeleted",UserLevelEntity.class)
					.setParameter("ISDEFAULT", true)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user level");
		}
		if(userLevelList.size()>0) 
			return userLevelList.get(0);
		else
			return null;
	}
}
