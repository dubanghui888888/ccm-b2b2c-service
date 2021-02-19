package com.mb.ext.core.dao.impl.merchant;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.merchant.MerchantAccountDAO;
import com.mb.ext.core.entity.merchant.MerchantAccountEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("merchantAccountDAO")
@Qualifier("merchantAccountDAO")
public class MerchantAccountDAOImpl extends AbstractBaseDAO<MerchantAccountEntity> implements MerchantAccountDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public MerchantAccountDAOImpl()
	{
		super();
		this.setEntityClass(MerchantAccountEntity.class);
	}

	@Override
	public void createMerchantAccount(MerchantAccountEntity merchantAccountEntity)
			throws DAOException {
		save(merchantAccountEntity);
		
	}

	@Override
	public void updateMerchantAccount(MerchantAccountEntity merchantAccountEntity)
			throws DAOException {
		update(merchantAccountEntity);
		
	}

	@Override
	public void deleteMerchantAccount(MerchantAccountEntity merchantAccountEntity)
			throws DAOException {
		deletePhysical(merchantAccountEntity);
		
	}

	@Override
	public List<MerchantAccountEntity> getAccountByMerchant(MerchantEntity merchantEntity) throws DAOException {
		List<MerchantAccountEntity> accountEntityList = new ArrayList<MerchantAccountEntity>();
		try {
			accountEntityList = em.createQuery("select b from MerchantAccountEntity b where b.merchantEntity.merchantUuid = :UUID and  b.isDeleted=:isDeleted",MerchantAccountEntity.class).setParameter("UUID", merchantEntity.getMerchantUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return accountEntityList;
	}
	
	@Override
	public MerchantAccountEntity getAccountByUuid(String uuid) throws DAOException {
		MerchantAccountEntity accountEntity = new MerchantAccountEntity();
		try {
			accountEntity = em.createQuery("select b from MerchantAccountEntity b where b.merchantAccountUuid = :UUID and  b.isDeleted=:isDeleted",MerchantAccountEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return accountEntity;
	}

}
