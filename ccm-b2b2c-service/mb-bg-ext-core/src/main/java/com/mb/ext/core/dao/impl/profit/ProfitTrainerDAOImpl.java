package com.mb.ext.core.dao.impl.profit;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.profit.ProfitTrainerDAO;
import com.mb.ext.core.entity.UserLevelEntity;
import com.mb.ext.core.entity.profit.ProfitTrainerEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("profitTrainerDAO")
@Qualifier("profitTrainerDAO")
public class ProfitTrainerDAOImpl extends AbstractBaseDAO<ProfitTrainerEntity> implements ProfitTrainerDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public ProfitTrainerDAOImpl()
	{
		super();
		this.setEntityClass(ProfitTrainerEntity.class);
	}

	/**
	 * This method is used for inserting user information.
	 * 
	 * @param user
	 */
	@Override
	public void addProfitTrainer(ProfitTrainerEntity profitTrainerEntity) throws DAOException
	{
		save(profitTrainerEntity);
	}
	
	/**
	 * @param name
	 * @return 
	 * @throws Exception
	 */

	@Override
	public void updateProfitTrainer(ProfitTrainerEntity profitTrainerEntity) throws DAOException {
		update(profitTrainerEntity);
		
	}

	@Override
	public void deleteProfitTrainer(ProfitTrainerEntity profitTrainerEntity) throws DAOException {
		
		deletePhysical(profitTrainerEntity);
		
	}

	@Override
	public List<ProfitTrainerEntity> getProfitTrainer()
			throws DAOException {
		List<ProfitTrainerEntity> profitTrainerEntityList = new ArrayList<ProfitTrainerEntity>();
		try {
			profitTrainerEntityList = em.createQuery("select b from ProfitTrainerEntity b where  b.isDeleted=:isDeleted order by b.amount desc",ProfitTrainerEntity.class)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: ");
		}
		return profitTrainerEntityList;
	}
	
	@Override
	public List<ProfitTrainerEntity> getProfitTrainerByUserLevel(UserLevelEntity userLevel)
			throws DAOException {
		List<ProfitTrainerEntity> profitTrainerEntityList = new ArrayList<ProfitTrainerEntity>();
		try {
			profitTrainerEntityList = em.createQuery("select b from ProfitTrainerEntity b where b.profitUserLevelEntity.userLevelUuid = :UUID and b.isDeleted=:isDeleted order by b.amount desc",ProfitTrainerEntity.class)
					.setParameter("UUID", userLevel.getUserLevelUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: ");
		}
		return profitTrainerEntityList;
	}
}
