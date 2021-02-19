package com.mb.ext.core.dao.impl;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.UserInventoryDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserInventoryEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("userInventoryDAO")
@Qualifier("userInventoryDAO")
public class UserInventoryDAOImpl extends AbstractBaseDAO<UserInventoryEntity> implements UserInventoryDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public UserInventoryDAOImpl()
	{
		super();
		this.setEntityClass(UserInventoryEntity.class);
	}

	@Override
	public void addUserInventory(UserInventoryEntity userInventoryEntity)
			throws DAOException {
		
		save(userInventoryEntity);

	}

	@Override
	public void deleteUserInventory(UserInventoryEntity userInventoryEntity)
			throws DAOException {
		deletePhysical(userInventoryEntity);
		
	}
	
	@Override
	public void updateUserInventory(UserInventoryEntity userInventoryEntity)
			throws DAOException {
		
		update(userInventoryEntity);
		
	}

	@Override
	public UserInventoryEntity findByUser(UserEntity userEntity) throws DAOException {
		
		UserInventoryEntity userInventoryEntity = null;
		try {
			userInventoryEntity = (UserInventoryEntity)em.createQuery("select b from UserInventoryEntity b where b.userEntity.userUuid = :UUID and b.isDeleted=:isDeleted",UserInventoryEntity.class)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+userEntity.getUserUuid());
		}
		return userInventoryEntity;
		
	}

	@Override
	public int getTotalUserInventory() throws DAOException {
		Long total = null;
		try {
			total = (Long)em.createQuery("select sum(b.balance) from UserInventoryEntity b where b.isDeleted=:isDeleted",Long.class)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: ");
		}
		if(total == null) total = new Long(0);
		return total.intValue();
		
	}




}
