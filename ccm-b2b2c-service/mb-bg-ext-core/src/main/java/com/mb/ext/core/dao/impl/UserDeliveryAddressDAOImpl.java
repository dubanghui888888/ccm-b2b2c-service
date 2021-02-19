package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.UserDeliveryAddressDAO;
import com.mb.ext.core.entity.UserDeliveryAddressEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("userDeliveryAddressDAO")
@Qualifier("userDeliveryAddressDAO")
public class UserDeliveryAddressDAOImpl extends AbstractBaseDAO<UserDeliveryAddressEntity> implements UserDeliveryAddressDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public UserDeliveryAddressDAOImpl()
	{
		super();
		this.setEntityClass(UserDeliveryAddressEntity.class);
	}

	@Override
	public void addUserDeliveryAddress(UserDeliveryAddressEntity userDeliveryAddressEntity)
			throws DAOException {
		
		save(userDeliveryAddressEntity);

	}
	
	@Override
	public void updateUserDeliveryAddress(UserDeliveryAddressEntity userDeliveryAddressEntity)
			throws DAOException {
		
		update(userDeliveryAddressEntity);

	}

	@Override
	public void deleteUserDeliveryAddress(UserDeliveryAddressEntity userDeliveryAddressEntity)
			throws DAOException {
		deletePhysical(userDeliveryAddressEntity);
		
	}

	@Override
	public UserDeliveryAddressEntity getDeliveryAddressByUuid(String uuid) throws DAOException {
		
		UserDeliveryAddressEntity userDeliveryAddressEntity = null;
		try {
			userDeliveryAddressEntity = (UserDeliveryAddressEntity)em.createQuery("select b from UserDeliveryAddressEntity b where b.userDeliveryAddressUuid = :UUID and b.isDeleted=:isDeleted",UserDeliveryAddressEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+uuid);
		}
		return userDeliveryAddressEntity;
		
	}

	@Override
	public List<UserDeliveryAddressEntity> getDeliveryAddressByUser(UserEntity userEntity) throws DAOException {
		List<UserDeliveryAddressEntity> userDeliveryAddressEntityList = new ArrayList<UserDeliveryAddressEntity>();
		try {
			userDeliveryAddressEntityList = em.createQuery("select b from UserDeliveryAddressEntity b where b.userEntity.userUuid = :UUID and b.isDeleted=:isDeleted",UserDeliveryAddressEntity.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+userEntity.getUserUuid());
		}
		return userDeliveryAddressEntityList;
	}


}
