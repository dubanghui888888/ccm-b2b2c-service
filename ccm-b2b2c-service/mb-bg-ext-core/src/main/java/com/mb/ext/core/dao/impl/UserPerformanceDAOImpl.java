package com.mb.ext.core.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.UserPerformanceDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserPerformanceEntity;
import com.mb.ext.core.service.spec.PerformanceSearchDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.UserPerformanceDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("userPerformanceDAO")
@Qualifier("userPerformanceDAO")
public class UserPerformanceDAOImpl extends AbstractBaseDAO<UserPerformanceEntity> implements UserPerformanceDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public UserPerformanceDAOImpl()
	{
		super();
		this.setEntityClass(UserPerformanceEntity.class);
	}

	@Override
	public void createUserPerformance(UserPerformanceEntity userPerformanceEntity)
			throws DAOException {
		save(userPerformanceEntity);
		
	}

	@Override
	public void updateUserPerformance(UserPerformanceEntity userPerformanceEntity)
			throws DAOException {
		update(userPerformanceEntity);
		
	}

	@Override
	public void deleteUserPerformance(UserPerformanceEntity userPerformanceEntity)
			throws DAOException {
		deletePhysical(userPerformanceEntity);
		
	}

	@Override
	public List<UserPerformanceEntity> getPerformanceByUser(UserEntity userEntity) throws DAOException {
		List<UserPerformanceEntity> performanceEntityList = new ArrayList<UserPerformanceEntity>();
		try {
			performanceEntityList = em.createQuery("select b from UserPerformanceEntity b where b.userEntity.userUuid = :UUID and  b.isDeleted=:isDeleted",UserPerformanceEntity.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user industry");
		}
		return performanceEntityList;
	}
	
	@Override
	public UserPerformanceEntity getPerformanceByUserDate(UserEntity userEntity, Date performanceDate) throws DAOException {
		UserPerformanceEntity performanceEntity = null;
		try {
			performanceEntity = em.createQuery("select b from UserPerformanceEntity b where b.userEntity.userUuid = :UUID and year(b.performanceDate) = year(:PERFORMANCEDATE) and month(b.performanceDate) = month(:PERFORMANCEDATE) and  b.isDeleted=:isDeleted",UserPerformanceEntity.class)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("PERFORMANCEDATE", performanceDate)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user industry");
		}
		return performanceEntity;
	}
	
	@Override
	public BigDecimal getTotalPerformanceByUser(UserEntity userEntity) throws DAOException {
		BigDecimal totalPerformance = null;
		try {
			totalPerformance = em.createQuery("select sum(b.performanceAmount) from UserPerformanceEntity b where b.userEntity.userUuid = :UUID and  b.isDeleted=:isDeleted",BigDecimal.class)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user industry");
		}
		return totalPerformance==null?BigDecimal.valueOf(0):totalPerformance;
	}
	
	@Override
	public BigDecimal getTotalPerformanceAwardByUser(UserEntity userEntity) throws DAOException {
		BigDecimal totalPerformance = null;
		try {
			totalPerformance = em.createQuery("select sum(b.performanceAward) from UserPerformanceEntity b where b.userEntity.userUuid = :UUID and  b.isDeleted=:isDeleted",BigDecimal.class)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user industry");
		}
		return totalPerformance==null?BigDecimal.valueOf(0):totalPerformance;
	}
	
	@Override
	public BigDecimal getTotalPerformanceAmountByDate(Date startDate, Date endDate) throws DAOException {
		BigDecimal totalPerformance = null;
		try {
			totalPerformance = em.createQuery("select sum(b.performanceAmount) from UserPerformanceEntity b where date(b.performanceDate) >= date(:STARTDATE) and date(b.performanceDate) <= date(:ENDDATE) and  b.isDeleted=:isDeleted",BigDecimal.class)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user industry");
		}
		return totalPerformance==null?BigDecimal.valueOf(0):totalPerformance;
	}
	
	@Override
	public BigDecimal getTotalPerformanceAwardByDate(Date startDate, Date endDate) throws DAOException {
		BigDecimal totalPerformance = null;
		try {
			totalPerformance = em.createQuery("select sum(b.performanceAward) from UserPerformanceEntity b where date(b.performanceDate) >= date(:STARTDATE) and date(b.performanceDate) <= date(:ENDDATE) and  b.isDeleted=:isDeleted",BigDecimal.class)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user industry");
		}
		return totalPerformance==null?BigDecimal.valueOf(0):totalPerformance;
	}
	
	@Override
	public List<UserPerformanceEntity> getPerformanceByUserDateRange(UserEntity userEntity, Date startDate, Date endDate) throws DAOException {
		List<UserPerformanceEntity> performanceEntityList = new ArrayList<UserPerformanceEntity>();
		try {
			performanceEntityList = em.createQuery("select b from UserPerformanceEntity b where b.userEntity.userUuid = :UUID and date(b.performanceDate) >= date(:STARTDATE) and date(b.performanceDate) <= date(:ENDDATE) and  b.isDeleted=:isDeleted order by b.performanceDate desc",UserPerformanceEntity.class)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user performance");
		}
		return performanceEntityList;
	}
	
	@Override
	public List<UserPerformanceEntity> getUserPerformances() throws DAOException {
		List<UserPerformanceEntity> performanceEntityList = new ArrayList<UserPerformanceEntity>();
		try {
			performanceEntityList = em.createQuery("select b from UserPerformanceEntity b where b.isDeleted=:isDeleted",UserPerformanceEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user industry");
		}
		return performanceEntityList;
	}
	@Override
	public int getUserPerformanceTotal(Date performanceDate) throws DAOException {
		Long total = new Long(0);
		try {
			total = em.createQuery("select count(b) from UserPerformanceEntity b where year(b.performanceDate) = year(:PERFORMANCEDATE) and month(b.performanceDate) = month(:PERFORMANCEDATE) and b.isDeleted=:isDeleted",Long.class)
					.setParameter("isDeleted", Boolean.FALSE)
					.setParameter("PERFORMANCEDATE", performanceDate)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user industry");
		}
		return total.intValue();
	}
	@Override
	public List<UserPerformanceEntity> getUserPerformancesPagination(Date performanceDate, int startIndex, int pageSize) throws DAOException {
		List<UserPerformanceEntity> performanceEntityList = new ArrayList<UserPerformanceEntity>();
		try {
			performanceEntityList = em.createQuery("select b from UserPerformanceEntity b where year(b.performanceDate) = year(:PERFORMANCEDATE) and month(b.performanceDate) = month(:PERFORMANCEDATE) and b.isDeleted=:isDeleted",UserPerformanceEntity.class)
					.setParameter("isDeleted", Boolean.FALSE)
					.setParameter("PERFORMANCEDATE", performanceDate)
					.setFirstResult(startIndex)
					.setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user industry");
		}
		return performanceEntityList;
	}

	@Override
	public List<UserPerformanceEntity> searchUserPerformance(PerformanceSearchDTO performanceSearchDTO, int startIndex,
			int pageSize) throws DAOException {

		List<UserPerformanceEntity> userPerformanceEntityList = new ArrayList<UserPerformanceEntity>();
		String query = "select b from UserPerformanceEntity b where  b.isDeleted=:isDeleted";
		String[] keyArray = performanceSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (PerformanceSearchDTO.KEY_USERNAME.equals(key)) {
				query = query + " and b.userEntity.name = :USERNAME";
			}else if (PerformanceSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid like :USERUUID";
			} else if (PerformanceSearchDTO.KEY_PERFORMANCEDATE.equals(key)) {
				query = query
						+ " and date(b.performanceDate)>= date(:PERFORMANCEDATESTART) and date(b.performanceDate) <= date(:PERFORMANCEDATEEND)";
			}
		}
		query = query + " order by b.performanceDate desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, UserPerformanceEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (PerformanceSearchDTO.KEY_USERNAME.equals(key)) {
					typedQuery.setParameter("USERNAME",
							"%"+performanceSearchDTO.getUserName()+"%");
				}else if (PerformanceSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							performanceSearchDTO.getUserUuid());
				} else if (PerformanceSearchDTO.KEY_PERFORMANCEDATE.equals(key)) {
					typedQuery.setParameter("PERFORMANCEDATESTART",
							performanceSearchDTO.getPerformanceDateStart());
					typedQuery.setParameter("PERFORMANCEDATEEND",
							performanceSearchDTO.getPerformanceDateEnd());
				}
			}
			userPerformanceEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(startIndex).setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return userPerformanceEntityList;
	
	}
	
	@Override
	public List<UserPerformanceDTO> searchUserTotalPerformance(PerformanceSearchDTO performanceSearchDTO, int startIndex,
			int pageSize) throws DAOException {

		List<UserPerformanceDTO> userPerformanceDTOList = new ArrayList<UserPerformanceDTO>();
		String query = "select b.userEntity as userEntity,sum(b.performanceAmount) as totalPerformanceAmount, sum(b.performanceAward) as totalPerformanceAward from UserPerformanceEntity b where  b.isDeleted=:isDeleted";
		String[] keyArray = performanceSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (PerformanceSearchDTO.KEY_USERNAME.equals(key)) {
				query = query + " and b.userEntity.name like :USERNAME";
			} else if (PerformanceSearchDTO.KEY_PERFORMANCEDATE.equals(key)) {
				query = query
						+ " and date(b.performanceDate)>= date(:PERFORMANCEDATESTART) and date(b.performanceDate) <= date(:PERFORMANCEDATEEND)";
			}
		}
		query = query + " group by b.userEntity";
		query = query + " order by b.performanceAmount desc";
		try {
			Query typedQuery = em.createQuery(query);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (PerformanceSearchDTO.KEY_USERNAME.equals(key)) {
					typedQuery.setParameter("USERNAME",
							"%"+performanceSearchDTO.getUserName()+"%");
				} else if (PerformanceSearchDTO.KEY_PERFORMANCEDATE.equals(key)) {
					typedQuery.setParameter("PERFORMANCEDATESTART",
							performanceSearchDTO.getPerformanceDateStart());
					typedQuery.setParameter("PERFORMANCEDATEEND",
							performanceSearchDTO.getPerformanceDateEnd());
				}
			}
			List<Object> resultList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(startIndex).setMaxResults(pageSize)
					.getResultList();
			for (int i=0; i<resultList.size();i++){
				Object[] result = (Object[]) resultList.get(i);
				UserPerformanceDTO performanceDTO = new UserPerformanceDTO();
				UserEntity userEntity = (UserEntity) result[0];
				UserDTO userDTO = new UserDTO();
				userDTO.setUserUuid(userEntity.getUserUuid());
				userDTO.setName(userEntity.getName());
				userDTO.setPersonalPhone(userEntity.getPersonalPhone());
				userDTO.setPersonalPhoneCountryCode(userEntity.getPersonalPhoneCountryCode());
				performanceDTO.setUserDTO(userDTO);;
				performanceDTO.setTotalPerformanceAmount((BigDecimal) result[1]);
				performanceDTO.setTotalPerformanceAward((BigDecimal) result[2]);
				userPerformanceDTOList.add(performanceDTO);
			}
		}catch (NoResultException e) {
			logger.info("No record found");
		}
		return userPerformanceDTOList;
	
	}
	
	@Override
	public BigDecimal searchUserTotalPerformanceAmount(PerformanceSearchDTO performanceSearchDTO) throws DAOException {

		BigDecimal totalPerformanceAmount = BigDecimal.valueOf(0l);
		String query = "select sum(b.performanceAmount) from UserPerformanceEntity b where  b.isDeleted=:isDeleted";
		String[] keyArray = performanceSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (PerformanceSearchDTO.KEY_USERNAME.equals(key)) {
				query = query + " and b.userEntity.name like :USERNAME";
			} else if (PerformanceSearchDTO.KEY_PERFORMANCEDATE.equals(key)) {
				query = query
						+ " and date(b.performanceDate)>= date(:PERFORMANCEDATESTART) and date(b.performanceDate) <= date(:PERFORMANCEDATEEND)";
			}
		}
		try {
			Query typedQuery = em.createQuery(query,BigDecimal.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (PerformanceSearchDTO.KEY_USERNAME.equals(key)) {
					typedQuery.setParameter("USERNAME",
							"%"+performanceSearchDTO.getUserName()+"%");
				} else if (PerformanceSearchDTO.KEY_PERFORMANCEDATE.equals(key)) {
					typedQuery.setParameter("PERFORMANCEDATESTART",
							performanceSearchDTO.getPerformanceDateStart());
					typedQuery.setParameter("PERFORMANCEDATEEND",
							performanceSearchDTO.getPerformanceDateEnd());
				}
			}
			totalPerformanceAmount = (BigDecimal) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		}catch (NoResultException e) {
			logger.info("No record found");
		}
		return totalPerformanceAmount;
	
	}
	
	@Override
	public BigDecimal searchUserTotalPerformanceAward(PerformanceSearchDTO performanceSearchDTO) throws DAOException {

		BigDecimal totalPerformanceAmount = BigDecimal.valueOf(0l);
		String query = "select sum(b.performanceAward) from UserPerformanceEntity b where  b.isDeleted=:isDeleted";
		String[] keyArray = performanceSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (PerformanceSearchDTO.KEY_USERNAME.equals(key)) {
				query = query + " and b.userEntity.name like :USERNAME";
			} else if (PerformanceSearchDTO.KEY_PERFORMANCEDATE.equals(key)) {
				query = query
						+ " and date(b.performanceDate)>= date(:PERFORMANCEDATESTART) and date(b.performanceDate) <= date(:PERFORMANCEDATEEND)";
			}
		}
		try {
			Query typedQuery = em.createQuery(query,BigDecimal.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (PerformanceSearchDTO.KEY_USERNAME.equals(key)) {
					typedQuery.setParameter("USERNAME",
							"%"+performanceSearchDTO.getUserName()+"%");
				} else if (PerformanceSearchDTO.KEY_PERFORMANCEDATE.equals(key)) {
					typedQuery.setParameter("PERFORMANCEDATESTART",
							performanceSearchDTO.getPerformanceDateStart());
					typedQuery.setParameter("PERFORMANCEDATEEND",
							performanceSearchDTO.getPerformanceDateEnd());
				}
			}
			totalPerformanceAmount = (BigDecimal) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		}catch (NoResultException e) {
			logger.info("No record found");
		}
		return totalPerformanceAmount;
	
	}

	@Override
	public int searchUserPerformanceTotal(PerformanceSearchDTO performanceSearchDTO) throws DAOException {

		Long total = new Long(0);
		String query = "select count(b) from UserPerformanceEntity b where  b.isDeleted=:isDeleted";
		String[] keyArray = performanceSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (PerformanceSearchDTO.KEY_USERNAME.equals(key)) {
				query = query + " and b.userEntity.name like :USERNAME";
			} else if (PerformanceSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid like :USERUUID";
			} else if (PerformanceSearchDTO.KEY_PERFORMANCEDATE.equals(key)) {
				query = query
						+ " and date(b.performanceDate)>= date(:PERFORMANCEDATESTART) and date(b.performanceDate) <= date(:PERFORMANCEDATEEND)";
			}
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (PerformanceSearchDTO.KEY_USERNAME.equals(key)) {
					typedQuery.setParameter("USERNAME",
							"%"+performanceSearchDTO.getUserName()+"%");
				}else if (PerformanceSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							performanceSearchDTO.getUserUuid());
				}  else if (PerformanceSearchDTO.KEY_PERFORMANCEDATE.equals(key)) {
					typedQuery.setParameter("PERFORMANCEDATESTART",
							performanceSearchDTO.getPerformanceDateStart());
					typedQuery.setParameter("PERFORMANCEDATEEND",
							performanceSearchDTO.getPerformanceDateEnd());
				}
			}
			total = (Long) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total.intValue();
	
	}
	
	@Override
	public int searchUserTotalPerformanceTotal(PerformanceSearchDTO performanceSearchDTO) throws DAOException {

		Long total = new Long(0);
		String query = "select count(distinct b.userEntity) from UserPerformanceEntity b where  b.isDeleted=:isDeleted";
		String[] keyArray = performanceSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (PerformanceSearchDTO.KEY_USERNAME.equals(key)) {
				query = query + " and b.userEntity.name like :USERNAME";
			} else if (PerformanceSearchDTO.KEY_PERFORMANCEDATE.equals(key)) {
				query = query
						+ " and date(b.performanceDate)>= date(:PERFORMANCEDATESTART) and date(b.performanceDate) <= date(:PERFORMANCEDATEEND)";
			}
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (PerformanceSearchDTO.KEY_USERNAME.equals(key)) {
					typedQuery.setParameter("USERNAME",
							"%"+performanceSearchDTO.getUserName()+"%");
				} else if (PerformanceSearchDTO.KEY_PERFORMANCEDATE.equals(key)) {
					typedQuery.setParameter("PERFORMANCEDATESTART",
							performanceSearchDTO.getPerformanceDateStart());
					typedQuery.setParameter("PERFORMANCEDATEEND",
							performanceSearchDTO.getPerformanceDateEnd());
				}
			}
			total = (Long) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total.intValue();
	
	}
}
