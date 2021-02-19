package com.mb.ext.core.dao.impl.promote;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.promote.PromoteFreightOffDAO;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.promote.PromoteFreightOffEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("promoteFreightOffDAO")
@Qualifier("promoteFreightOffDAO")
public class PromoteFreightOffDAOImpl extends AbstractBaseDAO<PromoteFreightOffEntity> implements PromoteFreightOffDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public PromoteFreightOffDAOImpl()
	{
		super();
		this.setEntityClass(PromoteFreightOffEntity.class);
	}

	@Override
	public void createPromoteFreightOff(PromoteFreightOffEntity promoteFreightOffEntity) throws DAOException {
		this.save(promoteFreightOffEntity);
		
	}

	@Override
	public void updatePromoteFreightOff(PromoteFreightOffEntity promoteFreightOffEntity) throws DAOException {
		this.update(promoteFreightOffEntity);
		
	}

	@Override
	public void deletePromoteFreightOff(PromoteFreightOffEntity promoteFreightOffEntity) throws DAOException {
		this.deletePhysical(promoteFreightOffEntity);
		
	}

	@Override
	public PromoteFreightOffEntity getPromoteFreightOffByName(MerchantEntity merchantEntity, String name) throws DAOException {
		PromoteFreightOffEntity promoteFreightOffEntity = null;
		try {
			promoteFreightOffEntity = (PromoteFreightOffEntity) em
					.createQuery(
							"select b from PromoteFreightOffEntity b where b.merchantEntity.merchantUuid = :UUID and b.name = :NAME and b.isDeleted=:isDeleted",
							PromoteFreightOffEntity.class)
					.setParameter("UUID", merchantEntity.getMerchantUuid())
					.setParameter("NAME", name)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for promoteFreightOff: " + name);
		}
		return promoteFreightOffEntity;
	}

	@Override
	public PromoteFreightOffEntity getPromoteFreightOffByUuid(String promoteFreightOffUuid) throws DAOException {
//		String entId = getEntId();
		PromoteFreightOffEntity promoteFreightOffEntity = null;
		try {
			promoteFreightOffEntity = (PromoteFreightOffEntity) em
					.createQuery(
							"select b from PromoteFreightOffEntity b where b.promoteFreightOffUuid = :PROMOTEFREIGHTOFFUUID and b.isDeleted=:isDeleted")
					.setParameter("PROMOTEFREIGHTOFFUUID", promoteFreightOffUuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for promoteFreightOff: " + promoteFreightOffUuid);
		}
		return promoteFreightOffEntity;
	}

	
	@Override
	public List<PromoteFreightOffEntity> getPromoteFreightOffByMerchant(MerchantEntity merchantEntity) throws DAOException {
		List<PromoteFreightOffEntity> promoteFreightOffEntityList = new ArrayList<PromoteFreightOffEntity>();
		try {
			promoteFreightOffEntityList =  em
					.createQuery(
							"select b from PromoteFreightOffEntity b where   b.merchantEntity.merchantUuid = :UUID and b.isDeleted=:isDeleted",
							PromoteFreightOffEntity.class)
					.setParameter("UUID", merchantEntity.getMerchantUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for promoteFreightOff: ");
		}
		return promoteFreightOffEntityList;
	}


}
