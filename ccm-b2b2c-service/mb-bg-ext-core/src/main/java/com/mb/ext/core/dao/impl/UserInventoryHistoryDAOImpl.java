package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.UserInventoryHistoryDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserInventoryHistoryEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("userInventoryHistoryDAO")
@Qualifier("userInventoryHistoryDAO")
public class UserInventoryHistoryDAOImpl extends AbstractBaseDAO<UserInventoryHistoryEntity> implements UserInventoryHistoryDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public UserInventoryHistoryDAOImpl()
	{
		super();
		this.setEntityClass(UserInventoryHistoryEntity.class);
	}

	@Override
	public void addUserInventoryHistory(UserInventoryHistoryEntity userInventoryHistoryEntity)
			throws DAOException {
		
		save(userInventoryHistoryEntity);

	}

	@Override
	public void deleteUserInventoryHistory(UserInventoryHistoryEntity userInventoryHistoryEntity)
			throws DAOException {
		deletePhysical(userInventoryHistoryEntity);
		
	}
	
	@Override
	public void updateUserInventoryHistory(UserInventoryHistoryEntity userInventoryHistoryEntity)
			throws DAOException {
		
		update(userInventoryHistoryEntity);
		
	}

	@Override
	public UserInventoryHistoryEntity findByUuid(String uuid) throws DAOException {
		
		UserInventoryHistoryEntity userInventoryHistoryEntity = null;
		try {
			userInventoryHistoryEntity = (UserInventoryHistoryEntity)em.createQuery("select b from UserInventoryHistoryEntity b where b.userInventoryHistoryUuid = :UUID and b.isDeleted=:isDeleted",UserInventoryHistoryEntity.class)
					.setParameter("UUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for inventory: "+uuid);
		}
		return userInventoryHistoryEntity;
		
	}
	
	@Override
	public List<UserInventoryHistoryEntity> findByUser(UserEntity userEntity) throws DAOException {
		
		List<UserInventoryHistoryEntity> userInventoryHistoryEntityList = new ArrayList<UserInventoryHistoryEntity>();
		try {
			userInventoryHistoryEntityList = em.createQuery("select b from UserInventoryHistoryEntity b where b.userEntity.userUuid = :UUID and b.isDeleted=:isDeleted order by b.createDate desc",UserInventoryHistoryEntity.class)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for inventory: "+userEntity.getName());
		}
		return userInventoryHistoryEntityList;
		
	}




}
