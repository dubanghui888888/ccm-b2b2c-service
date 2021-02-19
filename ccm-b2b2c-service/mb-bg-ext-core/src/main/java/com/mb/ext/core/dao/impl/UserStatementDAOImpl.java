package com.mb.ext.core.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.UserStatementDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserStatementEntity;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.UserStatementSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("userStatementDAO")
@Qualifier("userStatementDAO")
public class UserStatementDAOImpl extends AbstractBaseDAO<UserStatementEntity> implements UserStatementDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public UserStatementDAOImpl()
	{
		super();
		this.setEntityClass(UserStatementEntity.class);
	}

	@Override
	public void createUserStatement(UserStatementEntity userStatementEntity)
			throws DAOException {
		save(userStatementEntity);
		
	}

	@Override
	public void updateUserStatement(UserStatementEntity userStatementEntity)
			throws DAOException {
		update(userStatementEntity);
		
	}

	@Override
	public void deleteUserStatement(UserStatementEntity userStatementEntity)
			throws DAOException {
		deletePhysical(userStatementEntity);
			
	}

	@Override
	public List<UserStatementEntity> getStatementByUser(UserEntity userEntity) throws DAOException {
		List<UserStatementEntity> statementEntityList = new ArrayList<UserStatementEntity>();
		try {
			statementEntityList = em.createQuery("select b from UserStatementEntity b where b.userEntity.userUuid = :UUID and  b.isDeleted=:isDeleted",UserStatementEntity.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user statement");
		}
		return statementEntityList;
	}
	
	@Override
	public List<UserStatementEntity> getStatementByUserType(UserEntity userEntity, String transactionType) throws DAOException {
		List<UserStatementEntity> statementEntityList = new ArrayList<UserStatementEntity>();
		try {
			statementEntityList = em.createQuery("select b from UserStatementEntity b where b.userEntity.userUuid = :UUID and b.transactionType = :TYPE and  b.isDeleted=:isDeleted",UserStatementEntity.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("TYPE", transactionType).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user statement");
		}
		return statementEntityList;
	}
	
	@Override
	public List<UserStatementEntity> getStatementByDate(UserEntity userEntity, Date startDate, Date endDate) throws DAOException {
		List<UserStatementEntity> statementEntityList = new ArrayList<UserStatementEntity>();
		try {
			statementEntityList = em.createQuery("select b from UserStatementEntity b where b.userEntity.userUuid = :UUID and date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE)  and  b.isDeleted=:isDeleted",UserStatementEntity.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("STARTDATE", startDate).setParameter("ENDDATE", endDate).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user statement");
		}
		return statementEntityList;
	}
	
	@Override
	public BigDecimal getTranAmountByUserDate(UserEntity userEntity, Date startDate, Date endDate) throws DAOException {
		BigDecimal tranAmount = new BigDecimal(0);
		try {
			tranAmount = em.createQuery("select sum(b.transactionAmount) from UserStatementEntity b where b.userEntity.userUuid = :UUID and date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE)  and  b.isDeleted=:isDeleted",BigDecimal.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("STARTDATE", startDate).setParameter("ENDDATE", endDate).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user statement");
		}
		return tranAmount;
	}
	
	@Override
	public BigDecimal getTranAmountByUserDateType(UserEntity userEntity, Date startDate, Date endDate, String tranType) throws DAOException {
		BigDecimal tranAmount = new BigDecimal(0);
		try {
			tranAmount = em.createQuery("select sum(b.transactionAmount) from UserStatementEntity b where b.userEntity.userUuid = :UUID and date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE) and b.transactionType = :TRANTYPE  and  b.isDeleted=:isDeleted",BigDecimal.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("STARTDATE", startDate).setParameter("ENDDATE", endDate).setParameter("TRANTYPE", tranType).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user statement");
		}
		return tranAmount;
	}
	
	@Override
	public BigDecimal getTranAmountByDateType(Date startDate, Date endDate, String tranType) throws DAOException {
		BigDecimal tranAmount = new BigDecimal(0);
		try {
			tranAmount = em.createQuery("select sum(b.transactionAmount) from UserStatementEntity b where date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE) and b.transactionType = :TRANTYPE  and  b.isDeleted=:isDeleted",BigDecimal.class).setParameter("STARTDATE", startDate).setParameter("ENDDATE", endDate).setParameter("TRANTYPE", tranType).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user statement");
		}
		return tranAmount;
	}
	
	@Override
	public List<UserStatementEntity> getStatementByDateType(Date startDate, Date endDate, String tranType) throws DAOException {
		List<UserStatementEntity> statementEntityList = new ArrayList<UserStatementEntity>();
		try {
			statementEntityList = em.createQuery("select b from UserStatementEntity b where date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE) and b.transactionType = :TRANTYPE  and  b.isDeleted=:isDeleted",UserStatementEntity.class).setParameter("STARTDATE", startDate).setParameter("ENDDATE", endDate).setParameter("TRANTYPE", tranType).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user statement");
		}
		return statementEntityList;
	}
	
	@Override
	public BigDecimal getTranAmountByDate(Date startDate, Date endDate) throws DAOException {
		BigDecimal tranAmount = new BigDecimal(0);
		try {
			tranAmount = em.createQuery("select sum(b.transactionAmount) from UserStatementEntity b where date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE)  and  b.isDeleted=:isDeleted",BigDecimal.class).setParameter("STARTDATE", startDate).setParameter("ENDDATE", endDate).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user statement");
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
			String sqlStr = "select t1.datelist, ifnull(t2.transactionamount,0) from summary_day as t1 left join (select date_create, sum(transaction_amount) as transactionamount from userstatement where date(date_create)>=date(:STARTDATE) and date(date_create)<=date(:ENDDATE) group by date_create) as t2 on date(t1.datelist) = date(t2.date_create) where date(t1.datelist)>=date(:STARTDATE) and date(t1.datelist)<=date(:ENDDATE) order by t1.datelist";
			resultList = em.createNativeQuery(sqlStr)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.getResultList();
			for (int i=0; i<resultList.size();i++){
				Object[] result = (Object[]) resultList.get(i);
				String dateStr = String.valueOf(result[0]);
				BigDecimal userAmount = (BigDecimal) result[1];
				ChartDTO chartDTO = new ChartDTO();
				chartDTO.setValueDate(dateStr);
				chartDTO.setValueDecimal(userAmount);
				dtoList.add(chartDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found for user: ");
		}
		return dtoList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChartDTO> getIncrementTranAmountChartByUser(UserEntity userEntity, Date startDate, Date endDate)
			throws DAOException {
		List<ChartDTO> dtoList = new ArrayList<ChartDTO>();
		try {
			List<Object> resultList = new ArrayList<Object>();
			String sqlStr = "select t1.datelist, ifnull(t2.transactionamount,0) from summary_day as t1 left join (select date_create, sum(transaction_amount) as transactionamount from userstatement where user_uuid = :USERUUID and date(date_create)>=date(:STARTDATE) and date(date_create)<=date(:ENDDATE) group by date_create) as t2 on date(t1.datelist) = date(t2.date_create) where date(t1.datelist)>=date(:STARTDATE) and date(t1.datelist)<=date(:ENDDATE) order by t1.datelist";
			resultList = em.createNativeQuery(sqlStr)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("USERUUID", userEntity.getUserUuid())
					.getResultList();
			for (int i=0; i<resultList.size();i++){
				Object[] result = (Object[]) resultList.get(i);
				String dateStr = String.valueOf(result[0]);
				BigDecimal userAmount = (BigDecimal) result[1];
				ChartDTO chartDTO = new ChartDTO();
				chartDTO.setValueDate(dateStr);
				chartDTO.setValueDecimal(userAmount);
				dtoList.add(chartDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found for user: ");
		}
		return dtoList;
	}
	
	@Override
	public List<UserStatementEntity> searchUserStatement(UserStatementSearchDTO userStatementSearchDTO) throws DAOException {
		List<UserStatementEntity> userStatementEntityList = new ArrayList<UserStatementEntity>();
		String query = "select b from UserStatementEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = userStatementSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (UserStatementSearchDTO.KEY_USERPERSONALPHONE.equals(key)) {
				query = query + " and b.userEntity.personalPhone like :USERPERSONALPHONE";
			} else if (UserStatementSearchDTO.KEY_TRANTYPE.equals(key)) {
				query = query + " and b.transactionType = :TRANSACTIONTYPE";
			} else if (UserStatementSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid = :USERUUID";
			} else if (UserStatementSearchDTO.KEY_USERNAME.equals(key)) {
				query = query + " and b.userEntity.userEntity.name like :USERNAME";
			} else if (UserStatementSearchDTO.KEY_STATEMENTDATE.equals(key)) {
				query = query + " and date(b.transactionTime) >= date(:TRANSACTIONTIMESTART) and date(b.transactionTime) <= date(:TRANSACTIONTIMEEND)";
			} else if (UserStatementSearchDTO.KEY_AMOUNT.equals(key)) {
				query = query + " and b.transactionAmount >= :MINAMOUNT and b.transactionAmount <= :MAXAMOUNT";
			} else if (UserStatementSearchDTO.KEY_POINT.equals(key)) {
				query = query + " and b.transactionPoint >= :MINPOINT and b.transactionPoint <= :MAXPOINT";
			} else if (UserStatementSearchDTO.KEY_TRANSACTION_TYPE.equals(key)) {
				query = query + " and b.transactionType in (:TRANSACTIONTYPELIST)";
			}
		}
		 query = query + " order by b.transactionTime desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, UserStatementEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (UserStatementSearchDTO.KEY_USERPERSONALPHONE.equals(key)) {
					typedQuery.setParameter("USERPERSONALPHONE",
							"%" + userStatementSearchDTO.getUserPersonalPhone() + "%");
				} else if (UserStatementSearchDTO.KEY_TRANTYPE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTYPE",
							userStatementSearchDTO.getTransactionType());
				} else if (UserStatementSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							userStatementSearchDTO.getUserUuid());
				} else if (UserStatementSearchDTO.KEY_USERNAME.equals(key)) {
					typedQuery.setParameter("USERNAME",
							"%" + userStatementSearchDTO.getUserName() + "%");
				} else if (UserStatementSearchDTO.KEY_STATEMENTDATE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTIMESTART", userStatementSearchDTO.getStatementDateStart());
					typedQuery.setParameter("TRANSACTIONTIMEEND", userStatementSearchDTO.getStatementDateEnd());
				} else if (UserStatementSearchDTO.KEY_AMOUNT.equals(key)) {
					typedQuery.setParameter("MINAMOUNT", userStatementSearchDTO.getMinAmount());
					typedQuery.setParameter("MAXAMOUNT", userStatementSearchDTO.getMaxAmount());
				} else if (UserStatementSearchDTO.KEY_POINT.equals(key)) {
					typedQuery.setParameter("MINPOINT", userStatementSearchDTO.getMinPoint());
					typedQuery.setParameter("MAXPOINT", userStatementSearchDTO.getMaxPoint());
				} else if (UserStatementSearchDTO.KEY_TRANSACTION_TYPE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTYPELIST",
							userStatementSearchDTO.getTransactionTypeList());
				} 
			}
			userStatementEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(userStatementSearchDTO.getStartIndex()).setMaxResults(userStatementSearchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return userStatementEntityList;
	}
	
	@Override
	public int searchUserStatementTotal(UserStatementSearchDTO userStatementSearchDTO) throws DAOException {
		Long total = Long.valueOf(0);
		String query = "select count(b) from UserStatementEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = userStatementSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (UserStatementSearchDTO.KEY_USERPERSONALPHONE.equals(key)) {
				query = query + " and b.userEntity.personalPhone like :USERPERSONALPHONE";
			} else if (UserStatementSearchDTO.KEY_TRANTYPE.equals(key)) {
				query = query + " and b.transactionType = :TRANSACTIONTYPE";
			} else if (UserStatementSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid like :USERUUID";
			} else if (UserStatementSearchDTO.KEY_USERNAME.equals(key)) {
				query = query + " and b.userEntity.name like :USERNAME";
			} else if (UserStatementSearchDTO.KEY_STATEMENTDATE.equals(key)) {
				query = query + " and date(b.transactionTime) >= date(:TRANSACTIONTIMESTART) and date(b.transactionTime) <= date(:TRANSACTIONTIMEEND)";
			} else if (UserStatementSearchDTO.KEY_AMOUNT.equals(key)) {
				query = query + " and b.transactionAmount >= :MINAMOUNT and b.transactionAmount <= :MAXAMOUNT";
			} else if (UserStatementSearchDTO.KEY_POINT.equals(key)) {
				query = query + " and b.transactionPoint >= :MINPOINT and b.transactionPoint <= :MAXPOINT";
			} else if (UserStatementSearchDTO.KEY_TRANSACTION_TYPE.equals(key)) {
				query = query + " and b.transactionType in (:TRANSACTIONTYPELIST)";
			}
		}
		 query = query + " order by b.transactionTime desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (UserStatementSearchDTO.KEY_USERPERSONALPHONE.equals(key)) {
					typedQuery.setParameter("USERPERSONALPHONE",
							"%" + userStatementSearchDTO.getUserPersonalPhone() + "%");
				} else if (UserStatementSearchDTO.KEY_TRANTYPE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTYPE",
							userStatementSearchDTO.getTransactionType());
				} else if (UserStatementSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							"%" + userStatementSearchDTO.getUserUuid() + "%");
				} else if (UserStatementSearchDTO.KEY_USERNAME.equals(key)) {
					typedQuery.setParameter("USERNAME",
							"%" + userStatementSearchDTO.getUserName() + "%");
				} else if (UserStatementSearchDTO.KEY_STATEMENTDATE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTIMESTART", userStatementSearchDTO.getStatementDateStart());
					typedQuery.setParameter("TRANSACTIONTIMEEND", userStatementSearchDTO.getStatementDateEnd());
				} else if (UserStatementSearchDTO.KEY_AMOUNT.equals(key)) {
					typedQuery.setParameter("MINAMOUNT", userStatementSearchDTO.getMinAmount());
					typedQuery.setParameter("MAXAMOUNT", userStatementSearchDTO.getMaxAmount());
				} else if (UserStatementSearchDTO.KEY_POINT.equals(key)) {
					typedQuery.setParameter("MINPOINT", userStatementSearchDTO.getMinPoint());
					typedQuery.setParameter("MAXPOINT", userStatementSearchDTO.getMaxPoint());
				} else if (UserStatementSearchDTO.KEY_TRANSACTION_TYPE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTYPELIST",
							userStatementSearchDTO.getTransactionTypeList());
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
	public int searchUserStatementPoint(UserStatementSearchDTO userStatementSearchDTO) throws DAOException {
		Long total = Long.valueOf(0);
		String query = "select sum(b.transactionPoint) from UserStatementEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = userStatementSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (UserStatementSearchDTO.KEY_USERPERSONALPHONE.equals(key)) {
				query = query + " and b.userEntity.personalPhone like :USERPERSONALPHONE";
			} else if (UserStatementSearchDTO.KEY_TRANTYPE.equals(key)) {
				query = query + " and b.transactionType = :TRANSACTIONTYPE";
			} else if (UserStatementSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid = :USERUUID";
			} else if (UserStatementSearchDTO.KEY_USERNAME.equals(key)) {
				query = query + " and b.userEntity.name like :USERNAME";
			} else if (UserStatementSearchDTO.KEY_STATEMENTDATE.equals(key)) {
				query = query + " and date(b.transactionTime) >= date(:TRANSACTIONTIMESTART) and date(b.transactionTime) <= date(:TRANSACTIONTIMEEND)";
			} else if (UserStatementSearchDTO.KEY_AMOUNT.equals(key)) {
				query = query + " and b.transactionAmount >= :MINAMOUNT and b.transactionAmount <= :MAXAMOUNT";
			} else if (UserStatementSearchDTO.KEY_POINT.equals(key)) {
				query = query + " and b.transactionPoint >= :MINPOINT and b.transactionPoint <= :MAXPOINT";
			} else if (UserStatementSearchDTO.KEY_TRANSACTION_TYPE.equals(key)) {
				query = query + " and b.transactionType in (:TRANSACTIONTYPELIST)";
			}
		}
		 query = query + " order by b.transactionTime desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (UserStatementSearchDTO.KEY_USERPERSONALPHONE.equals(key)) {
					typedQuery.setParameter("USERPERSONALPHONE",
							"%" + userStatementSearchDTO.getUserPersonalPhone() + "%");
				} else if (UserStatementSearchDTO.KEY_TRANTYPE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTYPE",
							userStatementSearchDTO.getTransactionType());
				} else if (UserStatementSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							"%" + userStatementSearchDTO.getUserUuid() + "%");
				} else if (UserStatementSearchDTO.KEY_USERNAME.equals(key)) {
					typedQuery.setParameter("USERNAME",
							"%" + userStatementSearchDTO.getUserName() + "%");
				} else if (UserStatementSearchDTO.KEY_STATEMENTDATE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTIMESTART", userStatementSearchDTO.getStatementDateStart());
					typedQuery.setParameter("TRANSACTIONTIMEEND", userStatementSearchDTO.getStatementDateEnd());
				} else if (UserStatementSearchDTO.KEY_AMOUNT.equals(key)) {
					typedQuery.setParameter("MINAMOUNT", userStatementSearchDTO.getMinAmount());
					typedQuery.setParameter("MAXAMOUNT", userStatementSearchDTO.getMaxAmount());
				} else if (UserStatementSearchDTO.KEY_POINT.equals(key)) {
					typedQuery.setParameter("MINPOINT", userStatementSearchDTO.getMinPoint());
					typedQuery.setParameter("MAXPOINT", userStatementSearchDTO.getMaxPoint());
				} else if (UserStatementSearchDTO.KEY_TRANSACTION_TYPE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTYPELIST",
							userStatementSearchDTO.getTransactionTypeList());
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
	public BigDecimal searchUserStatementAmount(UserStatementSearchDTO userStatementSearchDTO) throws DAOException {
		BigDecimal total = BigDecimal.valueOf(0);
		String query = "select sum(b.transactionPoint) from UserStatementEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = userStatementSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (UserStatementSearchDTO.KEY_USERPERSONALPHONE.equals(key)) {
				query = query + " and b.userEntity.personalPhone like :USERPERSONALPHONE";
			} else if (UserStatementSearchDTO.KEY_TRANTYPE.equals(key)) {
				query = query + " and b.transactionType = :TRANSACTIONTYPE";
			} else if (UserStatementSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid = :USERUUID";
			} else if (UserStatementSearchDTO.KEY_USERNAME.equals(key)) {
				query = query + " and b.userEntity.name like :USERNAME";
			} else if (UserStatementSearchDTO.KEY_STATEMENTDATE.equals(key)) {
				query = query + " and date(b.transactionTime) >= date(:TRANSACTIONTIMESTART) and date(b.transactionTime) <= date(:TRANSACTIONTIMEEND)";
			} else if (UserStatementSearchDTO.KEY_AMOUNT.equals(key)) {
				query = query + " and b.transactionAmount >= :MINAMOUNT and b.transactionAmount <= :MAXAMOUNT";
			} else if (UserStatementSearchDTO.KEY_POINT.equals(key)) {
				query = query + " and b.transactionPoint >= :MINPOINT and b.transactionPoint <= :MAXPOINT";
			} else if (UserStatementSearchDTO.KEY_TRANSACTION_TYPE.equals(key)) {
				query = query + " and b.transactionType in (:TRANSACTIONTYPELIST)";
			}
		}
		 query = query + " order by b.transactionTime desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, BigDecimal.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (UserStatementSearchDTO.KEY_USERPERSONALPHONE.equals(key)) {
					typedQuery.setParameter("USERPERSONALPHONE",
							"%" + userStatementSearchDTO.getUserPersonalPhone() + "%");
				} else if (UserStatementSearchDTO.KEY_TRANTYPE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTYPE",
							userStatementSearchDTO.getTransactionType());
				} else if (UserStatementSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							"%" + userStatementSearchDTO.getUserUuid() + "%");
				} else if (UserStatementSearchDTO.KEY_USERNAME.equals(key)) {
					typedQuery.setParameter("USERNAME",
							"%" + userStatementSearchDTO.getUserName() + "%");
				} else if (UserStatementSearchDTO.KEY_STATEMENTDATE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTIMESTART", userStatementSearchDTO.getStatementDateStart());
					typedQuery.setParameter("TRANSACTIONTIMEEND", userStatementSearchDTO.getStatementDateEnd());
				} else if (UserStatementSearchDTO.KEY_AMOUNT.equals(key)) {
					typedQuery.setParameter("MINAMOUNT", userStatementSearchDTO.getMinAmount());
					typedQuery.setParameter("MAXAMOUNT", userStatementSearchDTO.getMaxAmount());
				} else if (UserStatementSearchDTO.KEY_POINT.equals(key)) {
					typedQuery.setParameter("MINPOINT", userStatementSearchDTO.getMinPoint());
					typedQuery.setParameter("MAXPOINT", userStatementSearchDTO.getMaxPoint());
				} else if (UserStatementSearchDTO.KEY_TRANSACTION_TYPE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTYPELIST",
							userStatementSearchDTO.getTransactionTypeList());
				} 
			}
			total = (BigDecimal) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total;
	}


}
