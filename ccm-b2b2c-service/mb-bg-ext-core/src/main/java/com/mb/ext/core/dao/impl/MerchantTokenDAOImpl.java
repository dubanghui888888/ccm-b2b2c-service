package com.mb.ext.core.dao.impl;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.MerchantTokenDAO;
import com.mb.ext.core.entity.MerchantTokenEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("merchantTokenDAO")
@Qualifier("merchantTokenDAO")
public class MerchantTokenDAOImpl extends AbstractBaseDAO<MerchantTokenEntity> implements MerchantTokenDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public MerchantTokenDAOImpl()
	{
		super();
		this.setEntityClass(MerchantTokenEntity.class);
	}

	@Override
	public void addMerchantToken(MerchantTokenEntity merchantTokenEntity)
			throws DAOException {
		
		save(merchantTokenEntity);

	}

	@Override
	public void deleteMerchantToken(MerchantTokenEntity merchantTokenEntity)
			throws DAOException {
		deletePhysical(merchantTokenEntity);
		
	}

	@Override
	public MerchantTokenEntity findByUserId(String userId) throws DAOException {
		
		MerchantTokenEntity userSessionEntity = null;
		try {
			userSessionEntity = (MerchantTokenEntity)em.createQuery("select b from MerchantTokenEntity b where b.userEntity.id = :ID and b.isDeleted=:isDeleted",MerchantTokenEntity.class).setParameter("ID", userId).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+userId);
		}
		return userSessionEntity;
		
	}

	@Override
	public MerchantTokenEntity findByTokenId(String tokenId) throws DAOException {
		MerchantTokenEntity userSessionEntity = null;
		try {
			userSessionEntity = (MerchantTokenEntity)em.createQuery("select b from MerchantTokenEntity b where b.tokenId = :TOKENID and b.isDeleted=:isDeleted",MerchantTokenEntity.class).setParameter("TOKENID", tokenId).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+tokenId);
		}
		return userSessionEntity;
	}

	@Override
	public MerchantTokenEntity findByTokenIdEntId(String tokenId, String entId)
			throws DAOException {
		MerchantTokenEntity userSessionEntity = null;
		try {
			userSessionEntity = (MerchantTokenEntity)em.createQuery("select b from MerchantTokenEntity b where b.entEntity.id = :ENTID and b.tokenId = :TOKENID and b.isDeleted=:isDeleted",MerchantTokenEntity.class).setParameter("ENTID", entId).setParameter("TOKENID", tokenId).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+tokenId);
		}
		return userSessionEntity;
	}


}
