package com.mb.ext.core.dao.impl.profit;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.profit.ProfitRecruitDAO;
import com.mb.ext.core.entity.profit.ProfitRecruitEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("profitRecruitDAO")
@Qualifier("profitRecruitDAO")
public class ProfitRecruitDAOImpl extends AbstractBaseDAO<ProfitRecruitEntity> implements ProfitRecruitDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public ProfitRecruitDAOImpl()
	{
		super();
		this.setEntityClass(ProfitRecruitEntity.class);
	}

	/**
	 * This method is used for inserting user information.
	 * 
	 * @param user
	 */
	@Override
	public void addProfitRecruit(ProfitRecruitEntity profitRecruitEntity) throws DAOException
	{
		save(profitRecruitEntity);
	}
	
	/**
	 * @param name
	 * @return 
	 * @throws Exception
	 */

	@Override
	public void updateProfitRecruit(ProfitRecruitEntity profitRecruitEntity) throws DAOException {
		update(profitRecruitEntity);
		
	}

	@Override
	public void deleteProfitRecruit(ProfitRecruitEntity profitRecruitEntity) throws DAOException {
		
		deletePhysical(profitRecruitEntity);
		
	}
	
	@Override
	public ProfitRecruitEntity getProfitRecruitByName(String name) throws DAOException {
		ProfitRecruitEntity profitRecruitEntity = null;
		try {
			profitRecruitEntity = (ProfitRecruitEntity)em.createQuery("select b from ProfitRecruitEntity b where b.recruitUserLevelEntity.name = :NAME and b.isDeleted=:isDeleted",ProfitRecruitEntity.class)
					.setParameter("NAME", name)
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+name);
		}
		return profitRecruitEntity;
	}
	
	@Override
	public List<ProfitRecruitEntity> getProfitRecruits() throws DAOException {
		List<ProfitRecruitEntity> profitRecruitEntityList = new ArrayList<ProfitRecruitEntity>();
		try {
			profitRecruitEntityList = em.createQuery("select b from ProfitRecruitEntity b where b.isDeleted=:isDeleted order by b.recruitUserLevelEntity.userLevelUuid",ProfitRecruitEntity.class)
					.setParameter("isDeleted", Boolean.FALSE)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: ");
		}
		return profitRecruitEntityList;
	}

	@Override
	public ProfitRecruitEntity getProfitRecruitByUuid(String profitRecruitUuid)
			throws DAOException {
		ProfitRecruitEntity profitRecruitEntity = null;
		try {
			profitRecruitEntity = (ProfitRecruitEntity)em.createQuery("select b from ProfitRecruitEntity b where b.profitRecruitUuid = :UUID and b.isDeleted=:isDeleted",ProfitRecruitEntity.class)
					.setParameter("UUID", profitRecruitUuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+profitRecruitUuid);
		}
		return profitRecruitEntity;
	}
	
	@Override
	public ProfitRecruitEntity getProfitRecruit(String recruitUserLevelUuid, String profitUserLevelUuid)
			throws DAOException {
		ProfitRecruitEntity profitRecruitEntity = null;
		try {
			profitRecruitEntity = (ProfitRecruitEntity)em.createQuery("select b from ProfitRecruitEntity b where b.recruitUserLevelEntity.userLevelUuid = :RECRUITUUID and b.profitUserLevelEntity.userLevelUuid = :PROFITUUID and b.isDeleted=:isDeleted",ProfitRecruitEntity.class)
					.setParameter("RECRUITUUID", recruitUserLevelUuid)
					.setParameter("PROFITUUID", profitUserLevelUuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for profit recruit");
		}
		return profitRecruitEntity;
	}
}
