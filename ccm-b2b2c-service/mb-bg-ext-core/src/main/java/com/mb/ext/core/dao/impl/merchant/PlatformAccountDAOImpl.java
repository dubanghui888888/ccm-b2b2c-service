package com.mb.ext.core.dao.impl.merchant;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.merchant.PlatformAccountDAO;
import com.mb.ext.core.entity.merchant.PlatformAccountEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("platformAccountDAO")
@Qualifier("platformAccountDAO")
public class PlatformAccountDAOImpl extends AbstractBaseDAO<PlatformAccountEntity> implements PlatformAccountDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public PlatformAccountDAOImpl()
	{
		super();
		this.setEntityClass(PlatformAccountEntity.class);
	}

	@Override
	public void createPlatformAccount(PlatformAccountEntity platformAccountEntity)
			throws DAOException {
		save(platformAccountEntity);
		
	}

	@Override
	public void updatePlatformAccount(PlatformAccountEntity platformAccountEntity)
			throws DAOException {
		update(platformAccountEntity);
		
	}

	@Override
	public void deletePlatformAccount(PlatformAccountEntity platformAccountEntity)
			throws DAOException {
		deletePhysical(platformAccountEntity);
		
	}

	@Override
	public List<PlatformAccountEntity> getPlatformAccount() throws DAOException {
		List<PlatformAccountEntity> accountEntityList = new ArrayList<PlatformAccountEntity>();
		try {
			accountEntityList = em.createQuery("select b from PlatformAccountEntity b where b.isDeleted=:isDeleted",PlatformAccountEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for platform industry");
		}
		return accountEntityList;
	}
	
	@Override
	public PlatformAccountEntity getAccountByUuid(String uuid) throws DAOException {
		PlatformAccountEntity accountEntity = new PlatformAccountEntity();
		try {
			accountEntity = em.createQuery("select b from PlatformAccountEntity b where b.platformAccountUuid = :UUID and  b.isDeleted=:isDeleted",PlatformAccountEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for platform industry");
		}
		return accountEntity;
	}

}
