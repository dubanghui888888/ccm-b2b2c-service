package com.mb.ext.core.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.UserSignDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserSignEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("userSignDAO")
@Qualifier("userSignDAO")
public class UserSignDAOImpl extends AbstractBaseDAO<UserSignEntity> implements UserSignDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public UserSignDAOImpl()
	{
		super();
		this.setEntityClass(UserSignEntity.class);
	}

	@Override
	public void createUserSign(UserSignEntity userSignEntity)
			throws DAOException {
		save(userSignEntity);
		
	}

	@Override
	public UserSignEntity getUserSignByDate(UserEntity userEntity, Date signDate) throws DAOException {
		UserSignEntity userSignEntity = null;
		try {
			userSignEntity = (UserSignEntity) em
					.createQuery(
							"select b from UserSignEntity b where b.userEntity.userUuid = :USERUUID and date(b.signTime) = date(:SIGNDATE) and b.isDeleted=:isDeleted",
							UserSignEntity.class)
					.setParameter("USERUUID", userEntity.getUserUuid())
					.setParameter("SIGNDATE", signDate)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: " + userEntity.getUserUuid());
		}
		return userSignEntity;
	}
	
	@Override
	public List<UserSignEntity> getSignByUser(UserEntity userEntity) throws DAOException {
		List<UserSignEntity> userSignEntityList = null;
		try {
			userSignEntityList = em
					.createQuery(
							"select b from UserSignEntity b where b.userEntity.userUuid = :USERUUID and b.isDeleted=:isDeleted",
							UserSignEntity.class)
					.setParameter("USERUUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: " + userEntity.getUserUuid());
		}
		return userSignEntityList;
	}

	@Override
	public UserSignEntity getLatestUserSign(UserEntity userEntity) throws DAOException {
		UserSignEntity userSignEntity = null;
		try {
			userSignEntity = (UserSignEntity) em
					.createQuery(
							"select b from UserSignEntity b where b.userEntity.userUuid = :USERUUID and b.isDeleted=:isDeleted order by b.signTime desc limit 1",
							UserSignEntity.class)
					.setParameter("USERUUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: " + userEntity.getUserUuid());
		}
		return userSignEntity;
	}
}
