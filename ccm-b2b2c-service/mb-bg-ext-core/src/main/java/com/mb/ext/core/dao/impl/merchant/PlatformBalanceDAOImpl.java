package com.mb.ext.core.dao.impl.merchant;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.merchant.PlatformBalanceDAO;
import com.mb.ext.core.entity.merchant.PlatformBalanceEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("platformBalanceDAO")
@Qualifier("platformBalanceDAO")
public class PlatformBalanceDAOImpl extends AbstractBaseDAO<PlatformBalanceEntity> implements PlatformBalanceDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public PlatformBalanceDAOImpl()
	{
		super();
		this.setEntityClass(PlatformBalanceEntity.class);
	}

	@Override
	public void createPlatformBalance(PlatformBalanceEntity platformBalanceEntity)
			throws DAOException {
		save(platformBalanceEntity);
		
	}

	@Override
	public void updatePlatformBalance(PlatformBalanceEntity platformBalanceEntity)
			throws DAOException {
		update(platformBalanceEntity);
		
	}

	@Override
	public void deletePlatformBalance(PlatformBalanceEntity platformBalanceEntity)
			throws DAOException {
		deletePhysical(platformBalanceEntity);
		
	}

	
	@Override
	public PlatformBalanceEntity getPlatformBalance() throws DAOException {
		PlatformBalanceEntity balanceEntity = null;
		try {
			balanceEntity = em.createQuery("select b from PlatformBalanceEntity b where b.isDeleted=:isDeleted",PlatformBalanceEntity.class).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for platform industry");
		}
		return balanceEntity;
	}


}
