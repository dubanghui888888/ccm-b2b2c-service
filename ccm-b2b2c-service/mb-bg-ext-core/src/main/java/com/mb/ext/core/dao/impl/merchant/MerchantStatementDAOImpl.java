package com.mb.ext.core.dao.impl.merchant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.merchant.MerchantStatementDAO;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.merchant.MerchantStatementEntity;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.MerchantStatementSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("merchantStatementDAO")
@Qualifier("merchantStatementDAO")
public class MerchantStatementDAOImpl extends AbstractBaseDAO<MerchantStatementEntity> implements MerchantStatementDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public MerchantStatementDAOImpl()
	{
		super();
		this.setEntityClass(MerchantStatementEntity.class);
	}

	@Override
	public void createMerchantStatement(MerchantStatementEntity merchantStatementEntity)
			throws DAOException {
		save(merchantStatementEntity);
		
	}

	@Override
	public void updateMerchantStatement(MerchantStatementEntity merchantStatementEntity)
			throws DAOException {
		update(merchantStatementEntity);
		
	}

	@Override
	public void deleteMerchantStatement(MerchantStatementEntity merchantStatementEntity)
			throws DAOException {
		deletePhysical(merchantStatementEntity);
			
	}

	@Override
	public List<MerchantStatementEntity> getStatementByMerchant(MerchantEntity merchantEntity) throws DAOException {
		List<MerchantStatementEntity> statementEntityList = new ArrayList<MerchantStatementEntity>();
		try {
			statementEntityList = em.createQuery("select b from MerchantStatementEntity b where b.merchantEntity.merchantUuid = :UUID and  b.isDeleted=:isDeleted",MerchantStatementEntity.class).setParameter("UUID", merchantEntity.getMerchantUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant statement");
		}
		return statementEntityList;
	}
	
	@Override
	public List<MerchantStatementEntity> getStatementByMerchantType(MerchantEntity merchantEntity, String transactionType) throws DAOException {
		List<MerchantStatementEntity> statementEntityList = new ArrayList<MerchantStatementEntity>();
		try {
			statementEntityList = em.createQuery("select b from MerchantStatementEntity b where b.merchantEntity.merchantUuid = :UUID and b.transactionType = :TYPE and  b.isDeleted=:isDeleted",MerchantStatementEntity.class).setParameter("UUID", merchantEntity.getMerchantUuid()).setParameter("TYPE", transactionType).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant statement");
		}
		return statementEntityList;
	}
	
	@Override
	public List<MerchantStatementEntity> getStatementByDate(MerchantEntity merchantEntity, Date startDate, Date endDate) throws DAOException {
		List<MerchantStatementEntity> statementEntityList = new ArrayList<MerchantStatementEntity>();
		try {
			statementEntityList = em.createQuery("select b from MerchantStatementEntity b where b.merchantEntity.merchantUuid = :UUID and date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE)  and  b.isDeleted=:isDeleted",MerchantStatementEntity.class).setParameter("UUID", merchantEntity.getMerchantUuid()).setParameter("STARTDATE", startDate).setParameter("ENDDATE", endDate).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant statement");
		}
		return statementEntityList;
	}
	
	@Override
	public BigDecimal getTranAmountByMerchantDate(MerchantEntity merchantEntity, Date startDate, Date endDate) throws DAOException {
		BigDecimal transactionAmount = new BigDecimal(0);
		try {
			transactionAmount = em.createQuery("select sum(b.transactionAmount) from MerchantStatementEntity b where b.merchantEntity.merchantUuid = :UUID and date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE)  and  b.isDeleted=:isDeleted",BigDecimal.class).setParameter("UUID", merchantEntity.getMerchantUuid()).setParameter("STARTDATE", startDate).setParameter("ENDDATE", endDate).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant statement");
		}
		return transactionAmount;
	}
	
	@Override
	public BigDecimal getTranAmountByMerchantDateType(MerchantEntity merchantEntity, Date startDate, Date endDate, String tranType) throws DAOException {
		BigDecimal transactionAmount = new BigDecimal(0);
		try {
			transactionAmount = em.createQuery("select sum(b.transactionAmount) from MerchantStatementEntity b where b.merchantEntity.merchantUuid = :UUID and date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE) and b.transactionType = :TRANTYPE  and  b.isDeleted=:isDeleted",BigDecimal.class).setParameter("UUID", merchantEntity.getMerchantUuid()).setParameter("STARTDATE", startDate).setParameter("ENDDATE", endDate).setParameter("TRANTYPE", tranType).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant statement");
		}
		return transactionAmount;
	}
	
	@Override
	public BigDecimal getTranAmountByDateType(Date startDate, Date endDate, String tranType) throws DAOException {
		BigDecimal transactionAmount = new BigDecimal(0);
		try {
			transactionAmount = em.createQuery("select sum(b.transactionAmount) from MerchantStatementEntity b where date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE) and b.transactionType = :TRANTYPE  and  b.isDeleted=:isDeleted",BigDecimal.class).setParameter("STARTDATE", startDate).setParameter("ENDDATE", endDate).setParameter("TRANTYPE", tranType).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant statement");
		}
		return transactionAmount;
	}
	
	@Override
	public List<MerchantStatementEntity> getStatementByDateType(Date startDate, Date endDate, String tranType) throws DAOException {
		List<MerchantStatementEntity> statementEntityList = new ArrayList<MerchantStatementEntity>();
		try {
			statementEntityList = em.createQuery("select b from MerchantStatementEntity b where date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE) and b.transactionType = :TRANTYPE  and  b.isDeleted=:isDeleted",MerchantStatementEntity.class).setParameter("STARTDATE", startDate).setParameter("ENDDATE", endDate).setParameter("TRANTYPE", tranType).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant statement");
		}
		return statementEntityList;
	}
	
	@Override
	public BigDecimal getTranAmountByDate(Date startDate, Date endDate) throws DAOException {
		BigDecimal transactionAmount = new BigDecimal(0);
		try {
			transactionAmount = em.createQuery("select sum(b.transactionAmount) from MerchantStatementEntity b where date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE)  and  b.isDeleted=:isDeleted",BigDecimal.class).setParameter("STARTDATE", startDate).setParameter("ENDDATE", endDate).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant statement");
		}
		return transactionAmount;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChartDTO> getIncrementTranAmountChart(Date startDate, Date endDate)
			throws DAOException {
		List<ChartDTO> dtoList = new ArrayList<ChartDTO>();
		try {
			List<Object> resultList = new ArrayList<Object>();
			String sqlStr = "select t1.datelist, ifnull(t2.transactionamount,0) from summary_day as t1 left join (select date_create, sum(transaction_amount) as transactionamount from merchantstatement where date(date_create)>=date(:STARTDATE) and date(date_create)<=date(:ENDDATE) group by date_create) as t2 on date(t1.datelist) = date(t2.date_create) where date(t1.datelist)>=date(:STARTDATE) and date(t1.datelist)<=date(:ENDDATE) order by t1.datelist";
			resultList = em.createNativeQuery(sqlStr)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.getResultList();
			for (int i=0; i<resultList.size();i++){
				Object[] result = (Object[]) resultList.get(i);
				String dateStr = String.valueOf(result[0]);
				BigDecimal merchantAmount = (BigDecimal) result[1];
				ChartDTO chartDTO = new ChartDTO();
				chartDTO.setValueDate(dateStr);
				chartDTO.setValueDecimal(merchantAmount);
				dtoList.add(chartDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found for merchant: ");
		}
		return dtoList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChartDTO> getIncrementTranAmountChartByMerchant(MerchantEntity merchantEntity, Date startDate, Date endDate)
			throws DAOException {
		List<ChartDTO> dtoList = new ArrayList<ChartDTO>();
		try {
			List<Object> resultList = new ArrayList<Object>();
			String sqlStr = "select t1.datelist, ifnull(t2.transactionamount,0) from summary_day as t1 left join (select date_create, sum(transaction_amount) as transactionamount from merchantstatement where merchant_uuid = :MERCHANTUUID and date(date_create)>=date(:STARTDATE) and date(date_create)<=date(:ENDDATE) group by date_create) as t2 on date(t1.datelist) = date(t2.date_create) where date(t1.datelist)>=date(:STARTDATE) and date(t1.datelist)<=date(:ENDDATE) order by t1.datelist";
			resultList = em.createNativeQuery(sqlStr)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid())
					.getResultList();
			for (int i=0; i<resultList.size();i++){
				Object[] result = (Object[]) resultList.get(i);
				String dateStr = String.valueOf(result[0]);
				BigDecimal merchantAmount = (BigDecimal) result[1];
				ChartDTO chartDTO = new ChartDTO();
				chartDTO.setValueDate(dateStr);
				chartDTO.setValueDecimal(merchantAmount);
				dtoList.add(chartDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found for merchant: ");
		}
		return dtoList;
	}
	
	@Override
	public List<MerchantStatementEntity> searchMerchantStatement(MerchantStatementSearchDTO merchantStatementSearchDTO) throws DAOException {
		List<MerchantStatementEntity> merchantStatementEntityList = new ArrayList<MerchantStatementEntity>();
		String query = "select b from MerchantStatementEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = merchantStatementSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (MerchantStatementSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo like :MOBILENO";
			} else if (MerchantStatementSearchDTO.KEY_TRANTYPE.equals(key)) {
				query = query + " and b.transactionType = :TRANSACTIONTYPE";
			} else if (MerchantStatementSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (MerchantStatementSearchDTO.KEY_MERCHANTNAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (MerchantStatementSearchDTO.KEY_STATEMENTDATE.equals(key)) {
				query = query + " and date(b.transactionTime) >= date(:TRANSACTIONTIMESTART) and date(b.transactionTime) <= date(:TRANSACTIONTIMEEND)";
			} else if (MerchantStatementSearchDTO.KEY_AMOUNT.equals(key)) {
				query = query + " and b.transactionAmount >= :MINAMOUNT and b.transactionAmount <= :MAXAMOUNT";
			} else if (MerchantStatementSearchDTO.KEY_POINT.equals(key)) {
				query = query + " and b.transactionPoint >= :MINPOINT and b.transactionPoint <= :MAXPOINT";
			}
		}
		 query = query + " order by b.transactionTime desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, MerchantStatementEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (MerchantStatementSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" + merchantStatementSearchDTO.getMobileNo() + "%");
				} else if (MerchantStatementSearchDTO.KEY_TRANTYPE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTYPE",
							merchantStatementSearchDTO.getTransactionType());
				} else if (MerchantStatementSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							merchantStatementSearchDTO.getMerchantUuid());
				} else if (MerchantStatementSearchDTO.KEY_MERCHANTNAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%" + merchantStatementSearchDTO.getMerchantName() + "%");
				} else if (MerchantStatementSearchDTO.KEY_STATEMENTDATE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTIMESTART", merchantStatementSearchDTO.getStatementDateStart());
					typedQuery.setParameter("TRANSACTIONTIMEEND", merchantStatementSearchDTO.getStatementDateEnd());
				} else if (MerchantStatementSearchDTO.KEY_AMOUNT.equals(key)) {
					typedQuery.setParameter("MINAMOUNT", merchantStatementSearchDTO.getMinAmount());
					typedQuery.setParameter("MAXAMOUNT", merchantStatementSearchDTO.getMaxAmount());
				}
			}
			merchantStatementEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(merchantStatementSearchDTO.getStartIndex()).setMaxResults(merchantStatementSearchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return merchantStatementEntityList;
	}
	
	@Override
	public int searchMerchantStatementTotal(MerchantStatementSearchDTO merchantStatementSearchDTO) throws DAOException {
		Long total = Long.valueOf(0);
		String query = "select count(b) from MerchantStatementEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = merchantStatementSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (MerchantStatementSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo like :MOBILENO";
			} else if (MerchantStatementSearchDTO.KEY_TRANTYPE.equals(key)) {
				query = query + " and b.transactionType = :TRANSACTIONTYPE";
			} else if (MerchantStatementSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (MerchantStatementSearchDTO.KEY_MERCHANTNAME.equals(key)) {
				query = query + " and b.merchantName like :MERCHANTNAME";
			} else if (MerchantStatementSearchDTO.KEY_STATEMENTDATE.equals(key)) {
				query = query + " and date(b.transactionTime) >= date(:TRANSACTIONTIMESTART) and date(b.transactionTime) <= date(:TRANSACTIONTIMEEND)";
			} else if (MerchantStatementSearchDTO.KEY_AMOUNT.equals(key)) {
				query = query + " and b.transactionAmount >= :MINAMOUNT and b.transactionAmount <= :MAXAMOUNT";
			} else if (MerchantStatementSearchDTO.KEY_POINT.equals(key)) {
				query = query + " and b.transactionPoint >= :MINPOINT and b.transactionPoint <= :MAXPOINT";
			}
		}
		 query = query + " order by b.transactionTime desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (MerchantStatementSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" + merchantStatementSearchDTO.getMobileNo() + "%");
				} else if (MerchantStatementSearchDTO.KEY_TRANTYPE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTYPE",
							merchantStatementSearchDTO.getTransactionType());
				} else if (MerchantStatementSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							merchantStatementSearchDTO.getMerchantUuid());
				} else if (MerchantStatementSearchDTO.KEY_MERCHANTNAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%" + merchantStatementSearchDTO.getMerchantName() + "%");
				} else if (MerchantStatementSearchDTO.KEY_STATEMENTDATE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTIMESTART", merchantStatementSearchDTO.getStatementDateStart());
					typedQuery.setParameter("TRANSACTIONTIMEEND", merchantStatementSearchDTO.getStatementDateEnd());
				} else if (MerchantStatementSearchDTO.KEY_AMOUNT.equals(key)) {
					typedQuery.setParameter("MINAMOUNT", merchantStatementSearchDTO.getMinAmount());
					typedQuery.setParameter("MAXAMOUNT", merchantStatementSearchDTO.getMaxAmount());
				}
			}
			total = (Long) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total==null?0:total.intValue();
	}
	
	@Override
	public int searchMerchantStatementPoint(MerchantStatementSearchDTO merchantStatementSearchDTO) throws DAOException {
		Long total = Long.valueOf(0);
		String query = "select sum(b.transactionPoint) from MerchantStatementEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = merchantStatementSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (MerchantStatementSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo like :MOBILENO";
			} else if (MerchantStatementSearchDTO.KEY_TRANTYPE.equals(key)) {
				query = query + " and b.transactionType = :TRANSACTIONTYPE";
			} else if (MerchantStatementSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (MerchantStatementSearchDTO.KEY_MERCHANTNAME.equals(key)) {
				query = query + " and b.merchantName like :MERCHANTNAME";
			} else if (MerchantStatementSearchDTO.KEY_STATEMENTDATE.equals(key)) {
				query = query + " and date(b.transactionTime) >= date(:TRANSACTIONTIMESTART) and date(b.transactionTime) <= date(:TRANSACTIONTIMEEND)";
			} else if (MerchantStatementSearchDTO.KEY_AMOUNT.equals(key)) {
				query = query + " and b.transactionAmount >= :MINAMOUNT and b.transactionAmount <= :MAXAMOUNT";
			} else if (MerchantStatementSearchDTO.KEY_POINT.equals(key)) {
				query = query + " and b.transactionPoint >= :MINPOINT and b.transactionPoint <= :MAXPOINT";
			}
		}
		 query = query + " order by b.transactionTime desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (MerchantStatementSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" + merchantStatementSearchDTO.getMobileNo() + "%");
				} else if (MerchantStatementSearchDTO.KEY_TRANTYPE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTYPE",
							merchantStatementSearchDTO.getTransactionType());
				} else if (MerchantStatementSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							merchantStatementSearchDTO.getMerchantUuid());
				} else if (MerchantStatementSearchDTO.KEY_MERCHANTNAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%" + merchantStatementSearchDTO.getMerchantName() + "%");
				} else if (MerchantStatementSearchDTO.KEY_STATEMENTDATE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTIMESTART", merchantStatementSearchDTO.getStatementDateStart());
					typedQuery.setParameter("TRANSACTIONTIMEEND", merchantStatementSearchDTO.getStatementDateEnd());
				} else if (MerchantStatementSearchDTO.KEY_AMOUNT.equals(key)) {
					typedQuery.setParameter("MINAMOUNT", merchantStatementSearchDTO.getMinAmount());
					typedQuery.setParameter("MAXAMOUNT", merchantStatementSearchDTO.getMaxAmount());
				}
			}
			total = (Long) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total==null?0:total.intValue();
	}
	
	@Override
	public BigDecimal searchMerchantStatementAmount(MerchantStatementSearchDTO merchantStatementSearchDTO) throws DAOException {
		BigDecimal total = BigDecimal.valueOf(0);
		String query = "select sum(b.transactionAmount) from MerchantStatementEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = merchantStatementSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (MerchantStatementSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo like :MOBILENO";
			} else if (MerchantStatementSearchDTO.KEY_TRANTYPE.equals(key)) {
				query = query + " and b.transactionType = :TRANSACTIONTYPE";
			} else if (MerchantStatementSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (MerchantStatementSearchDTO.KEY_MERCHANTNAME.equals(key)) {
				query = query + " and b.merchantName like :MERCHANTNAME";
			} else if (MerchantStatementSearchDTO.KEY_STATEMENTDATE.equals(key)) {
				query = query + " and date(b.transactionTime) >= date(:TRANSACTIONTIMESTART) and date(b.transactionTime) <= date(:TRANSACTIONTIMEEND)";
			} else if (MerchantStatementSearchDTO.KEY_AMOUNT.equals(key)) {
				query = query + " and b.transactionAmount >= :MINAMOUNT and b.transactionAmount <= :MAXAMOUNT";
			} else if (MerchantStatementSearchDTO.KEY_POINT.equals(key)) {
				query = query + " and b.transactionPoint >= :MINPOINT and b.transactionPoint <= :MAXPOINT";
			}
		}
		 query = query + " order by b.transactionTime desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, BigDecimal.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (MerchantStatementSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" + merchantStatementSearchDTO.getMobileNo() + "%");
				} else if (MerchantStatementSearchDTO.KEY_TRANTYPE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTYPE",
							merchantStatementSearchDTO.getTransactionType());
				} else if (MerchantStatementSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							merchantStatementSearchDTO.getMerchantUuid());
				} else if (MerchantStatementSearchDTO.KEY_MERCHANTNAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%" + merchantStatementSearchDTO.getMerchantName() + "%");
				} else if (MerchantStatementSearchDTO.KEY_STATEMENTDATE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTIMESTART", merchantStatementSearchDTO.getStatementDateStart());
					typedQuery.setParameter("TRANSACTIONTIMEEND", merchantStatementSearchDTO.getStatementDateEnd());
				} else if (MerchantStatementSearchDTO.KEY_AMOUNT.equals(key)) {
					typedQuery.setParameter("MINAMOUNT", merchantStatementSearchDTO.getMinAmount());
					typedQuery.setParameter("MAXAMOUNT", merchantStatementSearchDTO.getMaxAmount());
				}
			}
			total = (BigDecimal) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total == null?BigDecimal.valueOf(0):total;
	}

}
