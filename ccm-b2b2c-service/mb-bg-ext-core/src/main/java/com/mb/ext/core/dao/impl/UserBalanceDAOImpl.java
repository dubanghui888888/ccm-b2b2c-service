package com.mb.ext.core.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.UserBalanceDAO;
import com.mb.ext.core.entity.UserBalanceEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("userBalanceDAO")
@Qualifier("userBalanceDAO")
public class UserBalanceDAOImpl extends AbstractBaseDAO<UserBalanceEntity> implements UserBalanceDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public UserBalanceDAOImpl()
	{
		super();
		this.setEntityClass(UserBalanceEntity.class);
	}

	@Override
	public void createUserBalance(UserBalanceEntity userBalanceEntity)
			throws DAOException {
		save(userBalanceEntity);
		
	}

	@Override
	public void updateUserBalance(UserBalanceEntity userBalanceEntity)
			throws DAOException {
		update(userBalanceEntity);
		
	}

	@Override
	public void deleteUserBalance(UserBalanceEntity userBalanceEntity)
			throws DAOException {
		deletePhysical(userBalanceEntity);
		
	}

	@Override
	public UserBalanceEntity getBalanceByUser(UserEntity userEntity) throws DAOException {
		UserBalanceEntity balanceEntity = null;
		try {
			balanceEntity = em.createQuery("select b from UserBalanceEntity b where b.userEntity.userUuid = :UUID and  b.isDeleted=:isDeleted",UserBalanceEntity.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user industry");
		}
		return balanceEntity;
	}
	
	@Override
	public List<UserBalanceEntity> getUserBalances() throws DAOException {
		List<UserBalanceEntity> balanceEntityList = new ArrayList<UserBalanceEntity>();
		try {
			balanceEntityList = em.createQuery("select b from UserBalanceEntity b where b.isDeleted=:isDeleted",UserBalanceEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user industry");
		}
		return balanceEntityList;
	}

	@Override
	public BigDecimal getTotalAvailableBalance() throws DAOException {
		BigDecimal totalAvailableBalance = BigDecimal.valueOf(0l);
		try {
			totalAvailableBalance = em.createQuery("select sum(b.availableBalance) from UserBalanceEntity b where b.isDeleted=:isDeleted",BigDecimal.class).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user industry");
		}
		return totalAvailableBalance;
	}
}
