package com.mb.ext.core.dao.impl.promote;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.promote.PromoteMoneyOffDAO;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.promote.PromoteMoneyOffEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("promoteMoneyOffDAO")
@Qualifier("promoteMoneyOffDAO")
public class PromoteMoneyOffDAOImpl extends AbstractBaseDAO<PromoteMoneyOffEntity> implements PromoteMoneyOffDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public PromoteMoneyOffDAOImpl()
	{
		super();
		this.setEntityClass(PromoteMoneyOffEntity.class);
	}

	@Override
	public void createPromoteMoneyOff(PromoteMoneyOffEntity promoteMoneyOffEntity) throws DAOException {
		this.save(promoteMoneyOffEntity);
		
	}

	@Override
	public void updatePromoteMoneyOff(PromoteMoneyOffEntity promoteMoneyOffEntity) throws DAOException {
		this.update(promoteMoneyOffEntity);
		
	}

	@Override
	public void deletePromoteMoneyOff(PromoteMoneyOffEntity promoteMoneyOffEntity) throws DAOException {
		this.deletePhysical(promoteMoneyOffEntity);
		
	}

	@Override
	public PromoteMoneyOffEntity getPromoteMoneyOffByName(MerchantEntity merchantEntity, String name) throws DAOException {
		PromoteMoneyOffEntity promoteMoneyOffEntity = null;
		try {
			promoteMoneyOffEntity = (PromoteMoneyOffEntity) em
					.createQuery(
							"select b from PromoteMoneyOffEntity b where b.merchantEntity.merchantUuid = :UUID and b.name = :NAME and b.isDeleted=:isDeleted",
							PromoteMoneyOffEntity.class)
					.setParameter("UUID", merchantEntity.getMerchantUuid())
					.setParameter("NAME", name)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for promoteMoneyOff: " + name);
		}
		return promoteMoneyOffEntity;
	}

	@Override
	public PromoteMoneyOffEntity getPromoteMoneyOffByUuid(String promoteMoneyOffUuid) throws DAOException {
//		String entId = getEntId();
		PromoteMoneyOffEntity promoteMoneyOffEntity = null;
		try {
			promoteMoneyOffEntity = (PromoteMoneyOffEntity) em
					.createQuery(
							"select b from PromoteMoneyOffEntity b where b.promoteMoneyOffUuid = :PROMOTEMONEYOFFUUID and b.isDeleted=:isDeleted")
					.setParameter("PROMOTEMONEYOFFUUID", promoteMoneyOffUuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for promoteMoneyOff: " + promoteMoneyOffUuid);
		}
		return promoteMoneyOffEntity;
	}

	
	@Override
	public List<PromoteMoneyOffEntity> getPromoteMoneyOffByMerchant(MerchantEntity merchantEntity) throws DAOException {
		List<PromoteMoneyOffEntity> promoteMoneyOffEntityList = new ArrayList<PromoteMoneyOffEntity>();
		try {
			promoteMoneyOffEntityList =  em
					.createQuery(
							"select b from PromoteMoneyOffEntity b where   b.merchantEntity.merchantUuid = :UUID and b.isDeleted=:isDeleted",
							PromoteMoneyOffEntity.class)
					.setParameter("UUID", merchantEntity.getMerchantUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for promoteMoneyOff: ");
		}
		return promoteMoneyOffEntityList;
	}


}
