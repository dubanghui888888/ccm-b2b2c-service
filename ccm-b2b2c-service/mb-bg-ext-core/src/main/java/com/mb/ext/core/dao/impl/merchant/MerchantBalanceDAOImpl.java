package com.mb.ext.core.dao.impl.merchant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.merchant.MerchantBalanceDAO;
import com.mb.ext.core.entity.merchant.MerchantBalanceEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("merchantBalanceDAO")
@Qualifier("merchantBalanceDAO")
public class MerchantBalanceDAOImpl extends AbstractBaseDAO<MerchantBalanceEntity> implements MerchantBalanceDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public MerchantBalanceDAOImpl()
	{
		super();
		this.setEntityClass(MerchantBalanceEntity.class);
	}

	@Override
	public void createMerchantBalance(MerchantBalanceEntity merchantBalanceEntity)
			throws DAOException {
		save(merchantBalanceEntity);
		
	}

	@Override
	public void updateMerchantBalance(MerchantBalanceEntity merchantBalanceEntity)
			throws DAOException {
		update(merchantBalanceEntity);
		
	}

	@Override
	public void deleteMerchantBalance(MerchantBalanceEntity merchantBalanceEntity)
			throws DAOException {
		deletePhysical(merchantBalanceEntity);
		
	}

	@Override
	public MerchantBalanceEntity getBalanceByMerchant(MerchantEntity merchantEntity) throws DAOException {
		MerchantBalanceEntity balanceEntity = null;
		try {
			balanceEntity = em.createQuery("select b from MerchantBalanceEntity b where b.merchantEntity.merchantUuid = :UUID and  b.isDeleted=:isDeleted",MerchantBalanceEntity.class).setParameter("UUID", merchantEntity.getMerchantUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return balanceEntity;
	}
	
	@Override
	public List<MerchantBalanceEntity> getMerchantBalances() throws DAOException {
		List<MerchantBalanceEntity> balanceEntityList = new ArrayList<MerchantBalanceEntity>();
		try {
			balanceEntityList = em.createQuery("select b from MerchantBalanceEntity b where b.isDeleted=:isDeleted",MerchantBalanceEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return balanceEntityList;
	}
	
	@Override
	public BigDecimal getBalance() throws DAOException {
		BigDecimal availableBalance = null;
		try {
			availableBalance = em.createQuery("select sum(b.availableBalance) from MerchantBalanceEntity b where  b.isDeleted=:isDeleted",BigDecimal.class).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return availableBalance;
	}

}
