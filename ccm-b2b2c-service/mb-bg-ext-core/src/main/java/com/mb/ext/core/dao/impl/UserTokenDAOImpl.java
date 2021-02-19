package com.mb.ext.core.dao.impl;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.UserTokenDAO;
import com.mb.ext.core.entity.UserTokenEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("userTokenDAO")
@Qualifier("userTokenDAO")
public class UserTokenDAOImpl extends AbstractBaseDAO<UserTokenEntity> implements UserTokenDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public UserTokenDAOImpl()
	{
		super();
		this.setEntityClass(UserTokenEntity.class);
	}

	@Override
	public void addUserToken(UserTokenEntity userTokenEntity)
			throws DAOException {
		
		save(userTokenEntity);

	}

	@Override
	public void deleteUserToken(UserTokenEntity userTokenEntity)
			throws DAOException {
		deletePhysical(userTokenEntity);
		
	}

	@Override
	public UserTokenEntity findByUserId(String userId) throws DAOException {
		
		UserTokenEntity userSessionEntity = null;
		try {
			userSessionEntity = (UserTokenEntity)em.createQuery("select b from UserTokenEntity b where b.userEntity.id = :ID and b.isDeleted=:isDeleted",UserTokenEntity.class).setParameter("ID", userId).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+userId);
		}
		return userSessionEntity;
		
	}

	@Override
	public UserTokenEntity findByTokenId(String tokenId) throws DAOException {
		UserTokenEntity userSessionEntity = null;
		try {
			userSessionEntity = (UserTokenEntity)em.createQuery("select b from UserTokenEntity b where b.tokenId = :TOKENID and b.isDeleted=:isDeleted",UserTokenEntity.class).setParameter("TOKENID", tokenId).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+tokenId);
		}
		return userSessionEntity;
	}

	@Override
	public UserTokenEntity findByTokenIdEntId(String tokenId, String entId)
			throws DAOException {
		UserTokenEntity userSessionEntity = null;
		try {
			userSessionEntity = (UserTokenEntity)em.createQuery("select b from UserTokenEntity b where b.entEntity.id = :ENTID and b.tokenId = :TOKENID and b.isDeleted=:isDeleted",UserTokenEntity.class).setParameter("ENTID", entId).setParameter("TOKENID", tokenId).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+tokenId);
		}
		return userSessionEntity;
	}


}
