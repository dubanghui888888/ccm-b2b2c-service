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
import com.mb.ext.core.dao.UserAwardDAO;
import com.mb.ext.core.entity.UserAwardEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.AwardSearchDTO;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("userAwardDAO")
@Qualifier("userAwardDAO")
public class UserAwardDAOImpl extends AbstractBaseDAO<UserAwardEntity> implements UserAwardDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public UserAwardDAOImpl()
	{
		super();
		this.setEntityClass(UserAwardEntity.class);
	}

	@Override
	public void createUserAward(UserAwardEntity userAwardEntity)
			throws DAOException {
		save(userAwardEntity);
		
	}

	@Override
	public void updateUserAward(UserAwardEntity userAwardEntity)
			throws DAOException {
		update(userAwardEntity);
		
	}

	@Override
	public void deleteUserAward(UserAwardEntity userAwardEntity)
			throws DAOException {
		deletePhysical(userAwardEntity);
			
	}
	
	@Override
	public UserAwardEntity getAwardByUuid(String uuid) throws DAOException {
		UserAwardEntity awardEntity = null;
		try {
			awardEntity = em.createQuery("select b from UserAwardEntity b where b.userAwardUuid = :UUID and  b.isDeleted=:isDeleted",UserAwardEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user award");
		}
		return awardEntity;
	}

	@Override
	public List<UserAwardEntity> getAwardByUser(UserEntity userEntity) throws DAOException {
		List<UserAwardEntity> awardEntityList = new ArrayList<UserAwardEntity>();
		try {
			awardEntityList = em.createQuery("select b from UserAwardEntity b where b.userEntity.userUuid = :UUID and  b.isDeleted=:isDeleted order by b.createDate desc",UserAwardEntity.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user award");
		}
		return awardEntityList;
	}
	
	@Override
	public List<UserAwardEntity> getAwardByUserType(UserEntity userEntity, String transactionType) throws DAOException {
		List<UserAwardEntity> awardEntityList = new ArrayList<UserAwardEntity>();
		try {
			awardEntityList = em.createQuery("select b from UserAwardEntity b where b.userEntity.userUuid = :UUID and b.transactionType = :TYPE and  b.isDeleted=:isDeleted",UserAwardEntity.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("TYPE", transactionType).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user award");
		}
		return awardEntityList;
	}
	
	@Override
	public List<UserAwardEntity> getAwardByTranCode(String transactionCode) throws DAOException {
		List<UserAwardEntity> awardEntityList = new ArrayList<UserAwardEntity>();
		try {
			awardEntityList = em.createQuery("select b from UserAwardEntity b where  b.transactionCode = :CODE and  b.isDeleted=:isDeleted",UserAwardEntity.class).setParameter("CODE", transactionCode).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user award");
		}
		return awardEntityList;
	}
	
	@Override
	public List<UserAwardEntity> getAwardByDate(UserEntity userEntity, Date startDate, Date endDate) throws DAOException {
		List<UserAwardEntity> awardEntityList = new ArrayList<UserAwardEntity>();
		try {
			awardEntityList = em.createQuery("select b from UserAwardEntity b where b.userEntity.userUuid = :UUID and date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE)  and  b.isDeleted=:isDeleted",UserAwardEntity.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("STARTDATE", startDate).setParameter("ENDDATE", endDate).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user award");
		}
		return awardEntityList;
	}
	
	@Override
	public BigDecimal getTranAmountByUserDate(UserEntity userEntity, Date startDate, Date endDate) throws DAOException {
		BigDecimal tranAmount = new BigDecimal(0);
		try {
			tranAmount = em.createQuery("select sum(b.transactionAmount) from UserAwardEntity b where b.userEntity.userUuid = :UUID and date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE)  and  b.isDeleted=:isDeleted",BigDecimal.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("STARTDATE", startDate).setParameter("ENDDATE", endDate).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user award");
		}
		return tranAmount;
	}
	
	@Override
	public BigDecimal getTranAmountByUserDateType(UserEntity userEntity, Date startDate, Date endDate, String tranType) throws DAOException {
		BigDecimal tranAmount = new BigDecimal(0);
		try {
			tranAmount = em.createQuery("select sum(b.transactionAmount) from UserAwardEntity b where b.userEntity.userUuid = :UUID and date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE) and b.transactionType = :TRANTYPE  and  b.isDeleted=:isDeleted",BigDecimal.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("STARTDATE", startDate).setParameter("ENDDATE", endDate).setParameter("TRANTYPE", tranType).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user award");
		}
		return tranAmount;
	}
	
	@Override
	public BigDecimal getTranAmountByDateType(Date startDate, Date endDate, String tranType) throws DAOException {
		BigDecimal tranAmount = null;
		try {
			tranAmount = em.createQuery("select sum(b.transactionAmount) from UserAwardEntity b where date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE) and b.transactionType = :TRANTYPE  and  b.isDeleted=:isDeleted",BigDecimal.class).setParameter("STARTDATE", startDate).setParameter("ENDDATE", endDate).setParameter("TRANTYPE", tranType).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user award");
		}
		if(tranAmount == null)	tranAmount = new BigDecimal(0);
		return tranAmount;
	}
	
	@Override
	public List<UserAwardEntity> getAwardByDateType(Date startDate, Date endDate, String tranType) throws DAOException {
		List<UserAwardEntity> awardEntityList = new ArrayList<UserAwardEntity>();
		try {
			awardEntityList = em.createQuery("select b from UserAwardEntity b where date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE) and b.transactionType = :TRANTYPE  and  b.isDeleted=:isDeleted",UserAwardEntity.class).setParameter("STARTDATE", startDate).setParameter("ENDDATE", endDate).setParameter("TRANTYPE", tranType).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user award");
		}
		return awardEntityList;
	}
	
	@Override
	public BigDecimal getTranAmountByDate(Date startDate, Date endDate) throws DAOException {
		BigDecimal tranAmount = null;
		try {
			tranAmount = em.createQuery("select sum(b.transactionAmount) from UserAwardEntity b where date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE)  and  b.isDeleted=:isDeleted",BigDecimal.class).setParameter("STARTDATE", startDate).setParameter("ENDDATE", endDate).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user award");
		}
		if(tranAmount == null) tranAmount = new BigDecimal(0);
		return tranAmount;
	}
	
	@Override
	public BigDecimal getTranAmountByDateAndMerchant(MerchantEntity merchantEntity, Date startDate, Date endDate) throws DAOException {
		BigDecimal tranAmount = null;
		try {
			tranAmount = em.createQuery("select sum(b.transactionAmount) from UserAwardEntity b where date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE)  and b.userEntity.merchantEntity.merchantUuid = :MERCHANTUUID and  b.isDeleted=:isDeleted",BigDecimal.class)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid())
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user award");
		}
		if(tranAmount == null) tranAmount = new BigDecimal(0);
		return tranAmount;
	}
	
	@Override
	public BigDecimal getTranAmountByDateTypeAndMerchant(MerchantEntity merchantEntity, Date startDate, Date endDate, String transactionType) throws DAOException {
		BigDecimal tranAmount = null;
		try {
			tranAmount = em.createQuery("select sum(b.transactionAmount) from UserAwardEntity b where date(b.transactionTime) >= date(:STARTDATE) and date(b.transactionTime) <= date(:ENDDATE)  and b.userEntity.merchantEntity.merchantUuid = :MERCHANTUUID and b.transactionType = :TRANSACTIONTYPE and  b.isDeleted=:isDeleted",BigDecimal.class)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid())
					.setParameter("TRANSACTIONTYPE", transactionType)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user award");
		}
		if(tranAmount == null) tranAmount = new BigDecimal(0);
		return tranAmount;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChartDTO> getIncrementTranAmountChart(Date startDate, Date endDate)
			throws DAOException {
		List<ChartDTO> dtoList = new ArrayList<ChartDTO>();
		try {
			List<Object> resultList = new ArrayList<Object>();
			String sqlStr = "select t1.datelist, ifnull(t2.transactionamount,0) from summary_day as t1 left join (select date_create, sum(transaction_amount) as transactionamount from useraward where date(date_create)>=date(:STARTDATE) and date(date_create)<=date(:ENDDATE) group by date(date_create)) as t2 on date(t1.datelist) = date(t2.date_create) where date(t1.datelist)>=date(:STARTDATE) and date(t1.datelist)<=date(:ENDDATE) order by t1.datelist";
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
			String sqlStr = "select t1.datelist, ifnull(t2.transactionamount,0) from summary_day as t1 left join (select date_create, sum(transaction_amount) as transactionamount from useraward where user_uuid = :USERUUID and date(date_create)>=date(:STARTDATE) and date(date_create)<=date(:ENDDATE) group by date(date_create)) as t2 on date(t1.datelist) = date(t2.date_create) where date(t1.datelist)>=date(:STARTDATE) and date(t1.datelist)<=date(:ENDDATE) order by t1.datelist";
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
	public int searchUserAwardTotal(AwardSearchDTO awardSearchDTO) throws DAOException {
		Long total = new Long(0);
		String query = "select count(b) from UserAwardEntity b where  b.isDeleted=:isDeleted";
		String[] keyArray = awardSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (AwardSearchDTO.KEY_TRANSACTIONTYPE.equals(key)) {
				query = query + " and b.transactionType = :TRANSACTIONTYPE";
			} else if (AwardSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid = :USERUUID";
			} else if (AwardSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.userEntity.name = :NAME";
			} else if (AwardSearchDTO.KEY_AWARDDATE.equals(key)) {
				query = query
						+ " and b.transactionTime>= :AWARDDATESTART and b.transactionTime <= :AWARDDATEEND";
			}
		}
		query = query + " order by b.createDate desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (AwardSearchDTO.KEY_TRANSACTIONTYPE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTYPE",
							awardSearchDTO.getTransactionType());
				} else if (AwardSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							awardSearchDTO.getUserUuid());
				} else if (AwardSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							awardSearchDTO.getName());
				} else if (AwardSearchDTO.KEY_AWARDDATE.equals(key)) {
					typedQuery.setParameter("AWARDDATESTART",
							awardSearchDTO.getAwardDateStart());
					typedQuery.setParameter("AWARDDATEEND",
							awardSearchDTO.getAwardDateEnd());
				}
			}
			total = (Long)typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total.intValue();
	}
	
	@Override
	public BigDecimal searchUserAwardAmount(AwardSearchDTO awardSearchDTO) throws DAOException {
		BigDecimal total = new BigDecimal(0);
		String query = "select sum(b.transactionAmount) from UserAwardEntity b where  b.isDeleted=:isDeleted";
		String[] keyArray = awardSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (AwardSearchDTO.KEY_TRANSACTIONTYPE.equals(key)) {
				query = query + " and b.transactionType = :TRANSACTIONTYPE";
			} else if (AwardSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid = :USERUUID";
			} else if (AwardSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.userEntity.name = :NAME";
			} else if (AwardSearchDTO.KEY_AWARDDATE.equals(key)) {
				query = query
						+ " and b.transactionTime>= :AWARDDATESTART and b.transactionTime <= :AWARDDATEEND";
			}
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, BigDecimal.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (AwardSearchDTO.KEY_TRANSACTIONTYPE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTYPE",
							awardSearchDTO.getTransactionType());
				} else if (AwardSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							awardSearchDTO.getUserUuid());
				} else if (AwardSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							awardSearchDTO.getName());
				} else if (AwardSearchDTO.KEY_AWARDDATE.equals(key)) {
					typedQuery.setParameter("AWARDDATESTART",
							awardSearchDTO.getAwardDateStart());
					typedQuery.setParameter("AWARDDATEEND",
							awardSearchDTO.getAwardDateEnd());
				}
			}
			total = (BigDecimal)typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total;
	}
	
	@Override
	public List<UserAwardEntity> searchUserAward(AwardSearchDTO awardSearchDTO,
			int startIndex, int pageSize) throws DAOException {
		List<UserAwardEntity> userAwardEntityList = new ArrayList<UserAwardEntity>();
		String query = "select b from UserAwardEntity b where  b.isDeleted=:isDeleted";
		String[] keyArray = awardSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (AwardSearchDTO.KEY_TRANSACTIONTYPE.equals(key)) {
				query = query + " and b.transactionType = :TRANSACTIONTYPE";
			} else if (AwardSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid = :USERUUID";
			} else if (AwardSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.userEntity.name = :NAME";
			} else if (AwardSearchDTO.KEY_AWARDDATE.equals(key)) {
				query = query
						+ " and b.transactionTime>= :AWARDDATESTART and b.transactionTime <= :AWARDDATEEND";
			}
		}
		query = query + " order by b.createDate desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, UserAwardEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (AwardSearchDTO.KEY_TRANSACTIONTYPE.equals(key)) {
					typedQuery.setParameter("TRANSACTIONTYPE",
							awardSearchDTO.getTransactionType());
				} else if (AwardSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							awardSearchDTO.getUserUuid());
				} else if (AwardSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							awardSearchDTO.getName());
				} else if (AwardSearchDTO.KEY_AWARDDATE.equals(key)) {
					typedQuery.setParameter("AWARDDATESTART",
							awardSearchDTO.getAwardDateStart());
					typedQuery.setParameter("AWARDDATEEND",
							awardSearchDTO.getAwardDateEnd());
				}
			}
			userAwardEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(startIndex).setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return userAwardEntityList;
	}

}
