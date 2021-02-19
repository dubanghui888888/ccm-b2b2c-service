package com.mb.ext.core.dao.impl.promote;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.promote.PromoteDiscountDAO;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.promote.PromoteDiscountEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("promoteDiscountDAO")
@Qualifier("promoteDiscountDAO")
public class PromoteDiscountDAOImpl extends AbstractBaseDAO<PromoteDiscountEntity> implements PromoteDiscountDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public PromoteDiscountDAOImpl()
	{
		super();
		this.setEntityClass(PromoteDiscountEntity.class);
	}

	@Override
	public void createPromoteDiscount(PromoteDiscountEntity promoteDiscountEntity) throws DAOException {
		this.save(promoteDiscountEntity);
		
	}

	@Override
	public void updatePromoteDiscount(PromoteDiscountEntity promoteDiscountEntity) throws DAOException {
		this.update(promoteDiscountEntity);
		
	}

	@Override
	public void deletePromoteDiscount(PromoteDiscountEntity promoteDiscountEntity) throws DAOException {
		this.deletePhysical(promoteDiscountEntity);
		
	}

	@Override
	public PromoteDiscountEntity getPromoteDiscountByName(MerchantEntity merchantEntity, String name) throws DAOException {
		PromoteDiscountEntity promoteDiscountEntity = null;
		try {
			promoteDiscountEntity = (PromoteDiscountEntity) em
					.createQuery(
							"select b from PromoteDiscountEntity b where b.merchantEntity.merchantUuid = :UUID and b.name = :NAME and b.isDeleted=:isDeleted",
							PromoteDiscountEntity.class)
					.setParameter("UUID", merchantEntity.getMerchantUuid())
					.setParameter("NAME", name)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for promoteDiscount: " + name);
		}
		return promoteDiscountEntity;
	}

	@Override
	public PromoteDiscountEntity getPromoteDiscountByUuid(String promoteDiscountUuid) throws DAOException {
//		String entId = getEntId();
		PromoteDiscountEntity promoteDiscountEntity = null;
		try {
			promoteDiscountEntity = (PromoteDiscountEntity) em
					.createQuery(
							"select b from PromoteDiscountEntity b where b.promoteDiscountUuid = :PROMOTEDISCOUNTUUID and b.isDeleted=:isDeleted")
					.setParameter("PROMOTEDISCOUNTUUID", promoteDiscountUuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for promoteDiscount: " + promoteDiscountUuid);
		}
		return promoteDiscountEntity;
	}

	
	@Override
	public List<PromoteDiscountEntity> getPromoteDiscountByMerchant(MerchantEntity merchantEntity) throws DAOException {
		List<PromoteDiscountEntity> promoteDiscountEntityList = new ArrayList<PromoteDiscountEntity>();
		try {
			promoteDiscountEntityList =  em
					.createQuery(
							"select b from PromoteDiscountEntity b where   b.merchantEntity.merchantUuid = :UUID and b.isDeleted=:isDeleted",
							PromoteDiscountEntity.class)
					.setParameter("UUID", merchantEntity.getMerchantUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for promoteDiscount: ");
		}
		return promoteDiscountEntityList;
	}


}
