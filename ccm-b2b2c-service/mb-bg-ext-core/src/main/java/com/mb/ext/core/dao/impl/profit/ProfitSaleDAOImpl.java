package com.mb.ext.core.dao.impl.profit;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.profit.ProfitSaleDAO;
import com.mb.ext.core.entity.profit.ProfitSaleEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("profitSaleDAO")
@Qualifier("profitSaleDAO")
public class ProfitSaleDAOImpl extends AbstractBaseDAO<ProfitSaleEntity> implements ProfitSaleDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public ProfitSaleDAOImpl()
	{
		super();
		this.setEntityClass(ProfitSaleEntity.class);
	}

	/**
	 * This method is used for inserting user information.
	 * 
	 * @param user
	 */
	@Override
	public void addProfitSale(ProfitSaleEntity profitSaleEntity) throws DAOException
	{
		save(profitSaleEntity);
	}
	
	/**
	 * @param name
	 * @return 
	 * @throws Exception
	 */

	@Override
	public void updateProfitSale(ProfitSaleEntity profitSaleEntity) throws DAOException {
		update(profitSaleEntity);
		
	}

	@Override
	public void deleteProfitSale(ProfitSaleEntity profitSaleEntity) throws DAOException {
		
		deletePhysical(profitSaleEntity);
		
	}
	
	@Override
	public List<ProfitSaleEntity> getProfitSales() throws DAOException {
		List<ProfitSaleEntity> profitSaleEntityList = new ArrayList<ProfitSaleEntity>();
		try {
			profitSaleEntityList = em.createQuery("select b from ProfitSaleEntity b where b.isDeleted=:isDeleted",ProfitSaleEntity.class)
					.setParameter("isDeleted", Boolean.FALSE)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: ");
		}
		return profitSaleEntityList;
	}

	@Override
	public ProfitSaleEntity getProfitSaleByUuid(String profitSaleUuid)
			throws DAOException {
		ProfitSaleEntity profitSaleEntity = null;
		try {
			profitSaleEntity = (ProfitSaleEntity)em.createQuery("select b from ProfitSaleEntity b where b.profitSaleUuid = :UUID and b.isDeleted=:isDeleted",ProfitSaleEntity.class)
					.setParameter("UUID", profitSaleUuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+profitSaleUuid);
		}
		return profitSaleEntity;
	}
	
	@Override
	public ProfitSaleEntity getProfitSaleByUserLevel(String userLevelUuid)
			throws DAOException {
		ProfitSaleEntity profitSaleEntity = null;
		try {
			profitSaleEntity = (ProfitSaleEntity)em.createQuery("select b from ProfitSaleEntity b where b.profitUserLevelEntity.userLevelUuid = :UUID and b.isDeleted=:isDeleted",ProfitSaleEntity.class)
					.setParameter("UUID", userLevelUuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for profit sale: "+userLevelUuid);
		}
		return profitSaleEntity;
	}
}
