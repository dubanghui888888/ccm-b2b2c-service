package com.mb.ext.core.dao.impl.profit;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.profit.ProfitWelfareDAO;
import com.mb.ext.core.entity.profit.ProfitWelfareEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Repository("profitWelfareDAO")
@Qualifier("profitWelfareDAO")
public class ProfitWelfareDAOImpl extends AbstractBaseDAO<ProfitWelfareEntity> implements ProfitWelfareDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public ProfitWelfareDAOImpl()
	{
		super();
		this.setEntityClass(ProfitWelfareEntity.class);
	}

	/**
	 * This method is used for inserting user information.
	 * 
	 * @param profitWelfareEntity
	 */
	@Override
	public void addProfitWelfare(ProfitWelfareEntity profitWelfareEntity) throws DAOException
	{
		save(profitWelfareEntity);
	}
	
	/**
	 * @param profitWelfareEntity
	 * @return 
	 * @throws Exception
	 */

	@Override
	public void updateProfitWelfare(ProfitWelfareEntity profitWelfareEntity) throws DAOException {
		update(profitWelfareEntity);
		
	}

	@Override
	public void deleteProfitWelfare(ProfitWelfareEntity profitWelfareEntity) throws DAOException {
		
		deletePhysical(profitWelfareEntity);
		
	}

	@Override
	public void deleteAllProfitWelfare() throws DAOException {

		em.createNativeQuery("delete from profit_welfare").executeUpdate();

	}
	
	@Override
	public List<ProfitWelfareEntity> getProfitWelfareByType(String type) throws DAOException {
		List<ProfitWelfareEntity> profitWelfareEntityList = null;
		try {
			profitWelfareEntityList = em.createQuery("select b from ProfitWelfareEntity b where b.welfareType = :WELFARETYPE and b.isDeleted=:isDeleted",ProfitWelfareEntity.class)
					.setParameter("WELFARETYPE", type)
					.setParameter("isDeleted", Boolean.FALSE)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for profit welfare type: "+type);
		}
		return profitWelfareEntityList;
	}
	
	@Override
	public List<ProfitWelfareEntity> getProfitWelfares() throws DAOException {
		List<ProfitWelfareEntity> profitWelfareEntityList = new ArrayList<ProfitWelfareEntity>();
		try {
			profitWelfareEntityList = em.createQuery("select b from ProfitWelfareEntity b where b.isDeleted=:isDeleted",ProfitWelfareEntity.class)
					.setParameter("isDeleted", Boolean.FALSE)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: ");
		}
		return profitWelfareEntityList;
	}

	@Override
	public ProfitWelfareEntity getProfitWelfareByUuid(String profitWelfareUuid)
			throws DAOException {
		ProfitWelfareEntity profitWelfareEntity = null;
		try {
			profitWelfareEntity = (ProfitWelfareEntity)em.createQuery("select b from ProfitWelfareEntity b where b.profitWelfareUuid = :UUID and b.isDeleted=:isDeleted",ProfitWelfareEntity.class)
					.setParameter("UUID", profitWelfareUuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+profitWelfareUuid);
		}
		return profitWelfareEntity;
	}

}
