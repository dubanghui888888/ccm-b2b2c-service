package com.mb.ext.core.dao.impl.profit;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.profit.ProfitPerformanceDAO;
import com.mb.ext.core.entity.UserLevelEntity;
import com.mb.ext.core.entity.profit.ProfitPerformanceEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("profitPerformanceDAO")
@Qualifier("profitPerformanceDAO")
public class ProfitPerformanceDAOImpl extends AbstractBaseDAO<ProfitPerformanceEntity> implements ProfitPerformanceDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public ProfitPerformanceDAOImpl()
	{
		super();
		this.setEntityClass(ProfitPerformanceEntity.class);
	}

	/**
	 * This method is used for inserting user information.
	 * 
	 * @param user
	 */
	@Override
	public void addProfitPerformance(ProfitPerformanceEntity profitPerformanceEntity) throws DAOException
	{
		save(profitPerformanceEntity);
	}
	
	/**
	 * @param name
	 * @return 
	 * @throws Exception
	 */

	@Override
	public void updateProfitPerformance(ProfitPerformanceEntity profitPerformanceEntity) throws DAOException {
		update(profitPerformanceEntity);
		
	}

	@Override
	public void deleteProfitPerformance(ProfitPerformanceEntity profitPerformanceEntity) throws DAOException {
		
		deletePhysical(profitPerformanceEntity);
		
	}

	@Override
	public List<ProfitPerformanceEntity> getProfitPerformance()
			throws DAOException {
		List<ProfitPerformanceEntity> profitPerformanceEntityList = new ArrayList<ProfitPerformanceEntity>();
		try {
			profitPerformanceEntityList = em.createQuery("select b from ProfitPerformanceEntity b where  b.isDeleted=:isDeleted order by b.amount desc",ProfitPerformanceEntity.class)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: ");
		}
		return profitPerformanceEntityList;
	}
	
	@Override
	public List<ProfitPerformanceEntity> getProfitPerformanceByUserLevel(UserLevelEntity userLevel)
			throws DAOException {
		List<ProfitPerformanceEntity> profitPerformanceEntityList = new ArrayList<ProfitPerformanceEntity>();
		try {
			profitPerformanceEntityList = em.createQuery("select b from ProfitPerformanceEntity b where b.profitUserLevelEntity.userLevelUuid = :UUID and b.isDeleted=:isDeleted order by b.amount desc",ProfitPerformanceEntity.class)
					.setParameter("UUID", userLevel.getUserLevelUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: ");
		}
		return profitPerformanceEntityList;
	}
}
