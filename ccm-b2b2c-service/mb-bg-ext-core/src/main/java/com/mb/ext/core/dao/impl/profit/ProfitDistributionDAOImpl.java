package com.mb.ext.core.dao.impl.profit;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.profit.ProfitDistributionDAO;
import com.mb.ext.core.entity.profit.ProfitDistributionEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("profitDistributionDAO")
@Qualifier("profitDistributionDAO")
public class ProfitDistributionDAOImpl extends AbstractBaseDAO<ProfitDistributionEntity> implements ProfitDistributionDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public ProfitDistributionDAOImpl()
	{
		super();
		this.setEntityClass(ProfitDistributionEntity.class);
	}

	/**
	 * This method is used for inserting user information.
	 * 
	 * @param user
	 */
	@Override
	public void addProfitDistribution(ProfitDistributionEntity profitDistributionEntity) throws DAOException
	{
		save(profitDistributionEntity);
	}
	
	/**
	 * @param name
	 * @return 
	 * @throws Exception
	 */

	@Override
	public void updateProfitDistribution(ProfitDistributionEntity profitDistributionEntity) throws DAOException {
		update(profitDistributionEntity);
		
	}
	

	@Override
	public ProfitDistributionEntity getProfitDistribution()
			throws DAOException {
		ProfitDistributionEntity profitDistributionEntity = null;
		try {
			profitDistributionEntity = (ProfitDistributionEntity)em.createQuery("select b from ProfitDistributionEntity b where b.isDeleted=:isDeleted",ProfitDistributionEntity.class)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for profit distribution");
		}
		return profitDistributionEntity;
	}
}
