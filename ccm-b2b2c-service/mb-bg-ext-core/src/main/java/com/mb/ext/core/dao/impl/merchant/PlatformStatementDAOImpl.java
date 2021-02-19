package com.mb.ext.core.dao.impl.merchant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.merchant.PlatformStatementDAO;
import com.mb.ext.core.entity.merchant.PlatformStatementEntity;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("platformStatementDAO")
@Qualifier("platformStatementDAO")
public class PlatformStatementDAOImpl extends AbstractBaseDAO<PlatformStatementEntity> implements PlatformStatementDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public PlatformStatementDAOImpl()
	{
		super();
		this.setEntityClass(PlatformStatementEntity.class);
	}

	@Override
	public void createPlatformStatement(PlatformStatementEntity platformStatementEntity)
			throws DAOException {
		save(platformStatementEntity);
		
	}

	@Override
	public void updatePlatformStatement(PlatformStatementEntity platformStatementEntity)
			throws DAOException {
		update(platformStatementEntity);
		
	}

	@Override
	public void deletePlatformStatement(PlatformStatementEntity platformStatementEntity)
			throws DAOException {
		deletePhysical(platformStatementEntity);
			
	}

	@Override
	public List<PlatformStatementEntity> getStatement() throws DAOException {
		List<PlatformStatementEntity> statementEntityList = new ArrayList<PlatformStatementEntity>();
		try {
			statementEntityList = em.createQuery("select b.platformEntity from PlatformStatementEntity b where b.isDeleted=:isDeleted",PlatformStatementEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for platform statement");
		}
		return statementEntityList;
	}
	
	@Override
	public List<PlatformStatementEntity> getStatementByDate(Date startDate, Date endDate) throws DAOException {
		List<PlatformStatementEntity> statementEntityList = new ArrayList<PlatformStatementEntity>();
		try {
			statementEntityList = em.createQuery("select b from PlatformStatementEntity b where date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE)  and  b.isDeleted=:isDeleted",PlatformStatementEntity.class).setParameter("STARTDATE", startDate).setParameter("ENDDATE", endDate).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for platform statement");
		}
		return statementEntityList;
	}
	
	@Override
	public BigDecimal getTranAmountByDate(Date startDate, Date endDate) throws DAOException {
		BigDecimal tranAmount = new BigDecimal(0);
		try {
			tranAmount = em.createQuery("select sum(b.transactionAmount) from PlatformStatementEntity b where date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE)  and  b.isDeleted=:isDeleted",BigDecimal.class).setParameter("STARTDATE", startDate).setParameter("ENDDATE", endDate).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for platform statement");
		}
		return tranAmount;
	}
	
	@Override
	public BigDecimal getTranAmountByDateType(Date startDate, Date endDate, String tranType) throws DAOException {
		BigDecimal tranAmount = new BigDecimal(0);
		try {
			tranAmount = em.createQuery("select sum(b.transactionAmount) from PlatformStatementEntity b where date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE) and b.transactionType = :TRANTYPE  and  b.isDeleted=:isDeleted",BigDecimal.class).setParameter("STARTDATE", startDate).setParameter("ENDDATE", endDate).setParameter("TRANTYPE", tranType).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for platform statement");
		}
		return tranAmount;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChartDTO> getIncrementTranAmountChart(Date startDate, Date endDate)
			throws DAOException {
		List<ChartDTO> dtoList = new ArrayList<ChartDTO>();
		try {
			List<Object> resultList = new ArrayList<Object>();
			String sqlStr = "select t1.datelist, ifnull(t2.transactionamount,0) from summary_day as t1 left join (select date_create, sum(transaction_amount) as transactionamount from platformstatement where date(date_create)>=date(:STARTDATE) and date(date_create)<=date(:ENDDATE) group by date_create) as t2 on date(t1.datelist) = date(t2.date_create) where date(t1.datelist)>=date(:STARTDATE) and date(t1.datelist)<=date(:ENDDATE) order by t1.datelist";
			resultList = em.createNativeQuery(sqlStr)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.getResultList();
			for (int i=0; i<resultList.size();i++){
				Object[] result = (Object[]) resultList.get(i);
				String dateStr = String.valueOf(result[0]);
				BigDecimal platformAmount = (BigDecimal) result[1];
				ChartDTO chartDTO = new ChartDTO();
				chartDTO.setValueDate(dateStr);
				chartDTO.setValueDecimal(platformAmount);
				dtoList.add(chartDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found for platform: ");
		}
		return dtoList;
	}

}
