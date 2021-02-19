package com.mb.ext.core.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.UserSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("userDAO")
@Qualifier("userDAO")
public class UserDAOImpl extends AbstractBaseDAO<UserEntity> implements UserDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public UserDAOImpl() {
		super();
		this.setEntityClass(UserEntity.class);
	}

	/**
	 * This method is used for inserting user information.
	 * 
	 * @param user
	 */
	@Override
	public void addUser(UserEntity userEntity) throws DAOException {
		save(userEntity);
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public UserEntity getUserById(String id) throws DAOException {
		UserEntity userEntity = null;
		try {
			userEntity = (UserEntity) em
					.createQuery(
							"select b from UserEntity b where b.id = :ID and b.isDeleted=:isDeleted",
							UserEntity.class).setParameter("ID", id)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: " + id);
		}
		return userEntity;
	}
	
	@Override
	public UserEntity getUserByOpenId(String openId) throws DAOException {
		UserEntity userEntity = null;
		try {
			userEntity = (UserEntity) em
					.createQuery(
							"select b from UserEntity b where b.openId = :OPENID and b.isDeleted=:isDeleted",
							UserEntity.class).setParameter("OPENID", openId)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: " + openId);
		}
		return userEntity;
	}

	/**
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<UserEntity> getUserByMerchant(MerchantEntity merchantEntity)
			throws DAOException {
		List<UserEntity> userEntityList = null;
		try {
			userEntityList = em
					.createQuery(
							"select b from UserEntity b where b.merchantEntity.merchantUuid=:UUID and b.isActive=:ISACTIVE and b.isDeleted=:isDeleted",
							UserEntity.class)
					.setParameter("UUID", merchantEntity.getMerchantUuid())
					.setParameter("ISACTIVE", true)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: "
					+ merchantEntity.getMerchantName());
		}
		return userEntityList;
	}

	@Override
	public List<UserEntity> getL1ChildUsers(UserEntity userEntity)
			throws DAOException {
		List<UserEntity> userEntityList = null;
		try {
			userEntityList = em
					.createQuery(
							"select b from UserEntity b where b.supervisorL1.userUuid=:UUID and b.isActive=:ISACTIVE and b.isDeleted=:isDeleted",
							UserEntity.class)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("ISACTIVE", true)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: " + userEntity.getUserUuid());
		}
		return userEntityList;
	}
	
	@Override
	public List<UserEntity> getL1AndL2ChildUsers(UserEntity userEntity)
			throws DAOException {
		List<UserEntity> userEntityList = null;
		try {
			userEntityList = em
					.createQuery(
							"select b from UserEntity b where (b.supervisorL1.userUuid=:UUID or b.supervisorL1.supervisorL1.userUuid=:UUID) and b.isActive=:ISACTIVE and b.isDeleted=:isDeleted",
							UserEntity.class)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("ISACTIVE", true)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: " + userEntity.getUserUuid());
		}
		return userEntityList;
	}
	
	@Override
	public List<UserEntity> getL1ChildUsersByDate(UserEntity userEntity, Date startDate, Date endDate)
			throws DAOException {
		List<UserEntity> userEntityList = null;
		try {
			userEntityList = em
					.createQuery(
							"select b from UserEntity b where b.supervisorL1.userUuid=:UUID and b.registerDate > :STARTDATE and b.registerDate <= :ENDDATE and b.isActive=:ISACTIVE and b.isDeleted=:isDeleted",
							UserEntity.class)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("ISACTIVE", true)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: " + userEntity.getUserUuid());
		}
		return userEntityList;
	}
	
	@Override
	public int getL1ChildUserCount(UserEntity userEntity)
			throws DAOException {
		Long total = new Long(0);
		try {
			total = em
					.createQuery(
							"select count(b) from UserEntity b where b.supervisorL1.userUuid=:UUID and b.isActive=:ISACTIVE and b.isDeleted=:isDeleted",
							Long.class)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("ISACTIVE", true)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: " + userEntity.getUserUuid());
		}
		return total.intValue();
	}
	
	@Override
	public int getL1ChildUserCountByDate(UserEntity userEntity, Date startDate, Date endDate)
			throws DAOException {
		Long total = new Long(0);
		try {
			total = em
					.createQuery(
							"select count(b) from UserEntity b where b.supervisorL1.userUuid=:UUID  and b.registerDate > :STARTDATE and b.registerDate <= :ENDDATE and b.isActive=:ISACTIVE and b.isDeleted=:isDeleted",
							Long.class)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("ISACTIVE", true)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: " + userEntity.getUserUuid());
		}
		return total.intValue();
	}
	
	@Override
	public int getL1ChildUserCountByLevel(UserEntity userEntity, List<String> levelNameList)
			throws DAOException {
		Long total = new Long(0);
		try {
			total = em
					.createQuery(
							"select count(b) from UserEntity b where b.supervisorL1.userUuid=:UUID and b.userLevelEntity.name in :LEVELLIST and b.isActive=:ISACTIVE and b.isDeleted=:isDeleted",
							Long.class)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("LEVELLIST", levelNameList)
					.setParameter("ISACTIVE", true)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: " + userEntity.getUserUuid());
		}
		return total.intValue();
	}
	
	@Override
	public int getL1ChildUserCountByLevelDate(UserEntity userEntity, List<String> levelNameList, Date startDate, Date endDate)
			throws DAOException {
		Long total = new Long(0);
		try {
			total = em
					.createQuery(
							"select count(b) from UserEntity b where b.supervisorL1.userUuid=:UUID and b.registerDate > :STARTDATE and b.registerDate <= :ENDDATE and b.userLevelEntity.name in :LEVELLIST and b.isActive=:ISACTIVE and b.isDeleted=:isDeleted",
							Long.class)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("LEVELLIST", levelNameList)
					.setParameter("ISACTIVE", true)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: " + userEntity.getUserUuid());
		}
		return total.intValue();
	}
	
	@Override
	public int getInvitedUserCountByLevelDate(UserEntity userEntity, List<String> levelList, Date startDate, Date endDate)
			throws DAOException {
		Long total = new Long(0);
		try {
			total = em
					.createQuery(
							"select count(b) from UserEntity b where b.invitationUser != null and b.invitationUser.userUuid=:UUID and b.registerDate > :STARTDATE and b.registerDate <= :ENDDATE and b.userLevelEntity.userLevelUuid in :LEVELLIST and b.isActive=:ISACTIVE and b.isDeleted=:isDeleted",
							Long.class)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("LEVELLIST", levelList)
					.setParameter("ISACTIVE", true)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: " + userEntity.getUserUuid());
		}
		return total == null? 0: total.intValue();
	}
	
	@Override
	public List<UserEntity> getInvitedUsersByLevelDate(UserEntity userEntity, List<String> levelList, Date startDate, Date endDate)
			throws DAOException {
		List<UserEntity> userList = new ArrayList<UserEntity>();
		try {
			userList = em
					.createQuery(
							"select b from UserEntity b where b.invitationUser != null and b.invitationUser.userUuid=:UUID and b.registerDate > :STARTDATE and b.registerDate <= :ENDDATE and b.userLevelEntity.userLevelUuid in :LEVELLIST and b.isActive=:ISACTIVE and b.isDeleted=:isDeleted",
							UserEntity.class)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("LEVELLIST", levelList)
					.setParameter("ISACTIVE", true)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: " + userEntity.getUserUuid());
		}
		return userList;
	}
	
	@Override
	public int getAllChildUserCount(UserEntity userEntity)
			throws DAOException {
		Long total = new Long(0);
		try {
			total = em
					.createQuery(
							"select count(b) from UserTreeEntity b where b.ancestorEntity.userUuid=:UUID and b.userEntity.isActive=:ISACTIVE and b.isDeleted=:isDeleted",
							Long.class)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("ISACTIVE", true)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: " + userEntity.getUserUuid());
		}
		return total.intValue();
	}

	@Override
	public UserEntity getUserByMobileNo(String countryCode, String mobileNo)
			throws DAOException {
		UserEntity userEntity = null;
		try {
			userEntity = (UserEntity) em
					.createQuery(
							"select b from UserEntity b where  b.personalPhoneCountryCode = :COUNTRYCODE and b.personalPhone = :MOBILENO and b.isDeleted=:isDeleted",
							UserEntity.class)
					.setParameter("COUNTRYCODE", countryCode)
					.setParameter("MOBILENO", mobileNo)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: " + mobileNo);
		}
		return userEntity;
	}

	@Override
	public UserEntity getUserByIdCardNo(String idCardNo) throws DAOException {
		UserEntity userEntity = null;
		try {
			userEntity = (UserEntity) em
					.createQuery(
							"select b from UserEntity b where  b.idCardNo = :IDCARDNO and b.isDeleted=:isDeleted",
							UserEntity.class)
					.setParameter("IDCARDNO", idCardNo)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: " + idCardNo);
		}
		return userEntity;
	}

	@Override
	public void updateUser(UserEntity userEntity) throws DAOException {
		update(userEntity);

	}

	@Override
	public void deleteUser(UserEntity userEntity) throws DAOException {

		deletePhysical(userEntity);

	}

	@Override
	public void deletePhysicalUser(UserEntity userEntity) throws DAOException {

		deletePhysical(userEntity);

	}

	@Override
	public int getUserCountByMerchant(MerchantEntity merchantEntity)
			throws DAOException {
		int count = 0;
		try {
			count = em
					.createQuery(
							"select b from UserEntity b where b.merchantEntity.merchantUuid = :UUID and b.isActive=:ISACTIVE and b.isDeleted=:isDeleted",
							UserEntity.class)
					.setParameter("UUID", merchantEntity.getMerchantUuid())
					.setParameter("ISACTIVE", true)
					.setParameter("isDeleted", Boolean.FALSE).getResultList()
					.size();
			return count;
		} catch (NoResultException e) {
			logger.info("No record found for merchant: "
					+ merchantEntity.getMerchantUuid());
		}
		return count;
	}

	@Override
	public int getIncrementUserCountByMerchantDate(
			MerchantEntity merchantEntity, Date startDate, Date endDate)
			throws DAOException {
		int count = 0;
		try {
			count = em
					.createQuery(
							"select count(b.merchantUserUuid) from MerchantUserEntity b where b.merchantEntity.merchantUuid = :UUID and b.userEntity.isActive=:ISACTIVE and date(b.createDate) >= date(:STARTDATE) and date(b.createDate) <= date(:ENDDATE) and b.isDeleted=:isDeleted",
							Long.class)
					.setParameter("UUID", merchantEntity.getMerchantUuid())
					.setParameter("ISACTIVE", true)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult()
					.intValue();
			return count;
		} catch (NoResultException e) {
			logger.info("No record found for merchant: "
					+ merchantEntity.getMerchantUuid());
		}
		return count;
	}
	
	@Override
	public int getIncrementUserCountByMerchantDateLevel(
			MerchantEntity merchantEntity, Date startDate, Date endDate, String userLevelName)
			throws DAOException {
		int count = 0;
		try {
			count = em
					.createQuery(
							"select count(b.userUuid) from UserEntity b where b.merchantEntity.merchantUuid = :UUID and b.isActive=:ISACTIVE and date(b.createDate) >= date(:STARTDATE) and date(b.createDate) <= date(:ENDDATE) and b.userLevelEntity.name = :LEVELNAME and b.isDeleted=:isDeleted",
							Long.class)
					.setParameter("UUID", merchantEntity.getMerchantUuid())
					.setParameter("ISACTIVE", true)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("LEVELNAME", userLevelName)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult()
					.intValue();
			return count;
		} catch (NoResultException e) {
			logger.info("No record found for merchant: "
					+ merchantEntity.getMerchantUuid());
		}
		return count;
	}

	@Override
	public int getIncrementUserCountByDate(Date startDate, Date endDate)
			throws DAOException {
		int count = 0;
		try {
			count = em
					.createQuery(
							"select count(b.userUuid) from UserEntity b where b.isActive=:ISACTIVE and date(b.createDate) >= date(:STARTDATE) and date(b.createDate) <= date(:ENDDATE) and b.isDeleted=:isDeleted",
							Long.class).setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("ISACTIVE", true)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult()
					.intValue();
			return count;
		} catch (NoResultException e) {
			logger.info("No record found for user: ");
		}
		return count;
	}


	@Override
	public UserEntity getUserByTokenId(String tokenId) throws DAOException {
		UserEntity userEntity = null;
		try {
			userEntity = (UserEntity) em
					.createQuery(
							"select b from UserEntity b where  b.userTokenEntity.tokenId = :TOKENID and b.isDeleted=:isDeleted",
							UserEntity.class).setParameter("TOKENID", tokenId)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: " + tokenId);
		}
		return userEntity;
	}

	@Override
	public List<UserEntity> getAllUsersPagination(int startIndex, int pageSize)
			throws DAOException {
		List<UserEntity> userEntityList = null;
		try {
			userEntityList = em
					.createQuery(
							"select b from UserEntity b where b.isDeleted=:isDeleted",
							UserEntity.class)
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(startIndex).setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user");
		}
		return userEntityList;
	}

	@Override
	public List<UserEntity> getAllUsers() throws DAOException {
		List<UserEntity> userEntityList = null;
		try {
			userEntityList = em
					.createQuery(
							"select b from UserEntity b where b.isDeleted=:isDeleted",
							UserEntity.class)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user");
		}
		return userEntityList;
	}

	@Override
	public List<UserEntity> getInactiveUsers() throws DAOException {
		List<UserEntity> userEntityList = null;
		try {
			userEntityList = em
					.createQuery(
							"select b from UserEntity b where b.isActive = :ISACTIVE and b.isDeleted=:isDeleted",
							UserEntity.class)
					.setParameter("ISACTIVE", Boolean.FALSE)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user");
		}
		return userEntityList;
	}
	
	@Override
	public UserEntity getUserByUuid(String userUuid) throws DAOException {
		UserEntity userEntity = null;
		try {
			userEntity = (UserEntity) em
					.createQuery(
							"select b from UserEntity b where b.userUuid = :USERUUID and b.isDeleted=:isDeleted",
							UserEntity.class)
					.setParameter("USERUUID", userUuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: " + userUuid);
		}
		return userEntity;
	}

	@Override
	public int getUserCount() throws DAOException {
		int count = 0;
		try {
			count = em
					.createQuery(
							"select count(b.userUuid) from UserEntity b where b.isActive = :ISACTIVE and b.isDeleted=:isDeleted",
							Long.class)
							.setParameter("ISACTIVE", true)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult()
					.intValue();
			return count;
		} catch (NoResultException e) {
			logger.info("No record found ");
		}
		return count;
	}
	
	@Override
	public int getUserCountByLevel(String userLevelName) throws DAOException {
		int count = 0;
		try {
			count = em
					.createQuery(
							"select count(b.userUuid) from UserEntity b where b.isActive = :ISACTIVE and b.userLevelEntity.name = :LEVELNAME and b.isDeleted=:isDeleted",
							Long.class)
					.setParameter("LEVELNAME", userLevelName)
					.setParameter("ISACTIVE", true)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult()
					.intValue();
			return count;
		} catch (NoResultException e) {
			logger.info("No record found ");
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChartDTO> getIncrementUserChart(Date startDate, Date endDate)
			throws DAOException {
		List<ChartDTO> dtoList = new ArrayList<ChartDTO>();
		try {
			List<Object> resultList = new ArrayList<Object>();
			String sqlStr = "select t1.datelist, ifnull(t2.usercount,0) from summary_day as t1 left join (select date_create, count(user_uuid) as usercount from user where isactive = :ISACTIVE and date(date_create)>=date(:STARTDATE) and date(date_create)<=date(:ENDDATE) group by date(date_create)) as t2 on date(t1.datelist) = date(t2.date_create) where date(t1.datelist)>=date(:STARTDATE) and date(t1.datelist)<=date(:ENDDATE) order by t1.datelist";
			resultList = em.createNativeQuery(sqlStr)
					.setParameter("ISACTIVE", true)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate).getResultList();
			for (int i = 0; i < resultList.size(); i++) {
				Object[] result = (Object[]) resultList.get(i);
				String dateStr = String.valueOf(result[0]);
				BigInteger merchantCount = (BigInteger) result[1];
				ChartDTO chartDTO = new ChartDTO();
				chartDTO.setValueDate(dateStr);
				chartDTO.setValueInt(merchantCount.intValue());
				dtoList.add(chartDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found for merchant: ");
		}
		return dtoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChartDTO> getIncrementUserChartByMerchant(
			MerchantEntity merchantEntity, Date startDate, Date endDate)
			throws DAOException {
		List<ChartDTO> dtoList = new ArrayList<ChartDTO>();
		try {
			List<Object> resultList = new ArrayList<Object>();
			String sqlStr = "select t1.datelist, ifnull(t2.usercount,0) from summary_day as t1 left join (select date_create, count(merchantuser_uuid) as usercount from merchant_user where merchant_uuid=:MERCHANTUUID and date(date_create)>=date(:STARTDATE) and date(date_create)<=date(:ENDDATE) group by date(date_create)) as t2 on date(t1.datelist) = date(t2.date_create) where date(t1.datelist)>=date(:STARTDATE) and date(t1.datelist)<=date(:ENDDATE) order by t1.datelist";
			resultList = em
					.createNativeQuery(sqlStr)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("MERCHANTUUID",
							merchantEntity.getMerchantUuid()).getResultList();
			for (int i = 0; i < resultList.size(); i++) {
				Object[] result = (Object[]) resultList.get(i);
				String dateStr = String.valueOf(result[0]);
				BigInteger merchantCount = (BigInteger) result[1];
				ChartDTO chartDTO = new ChartDTO();
				chartDTO.setValueDate(dateStr);
				chartDTO.setValueInt(merchantCount.intValue());
				dtoList.add(chartDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found for merchant: ");
		}
		return dtoList;
	}

	@Override
	public List<UserEntity> searchUsers(UserSearchDTO userSearchDTO,
			int startIndex, int pageSize) throws DAOException {
		List<UserEntity> userEntityList = new ArrayList<UserEntity>();
		String query = "select b from UserEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = userSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (UserSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.name like :NAME";
			} else if (UserSearchDTO.KEY_PERSONALPHONE.equals(key)) {
				query = query + " and b.personalPhone like :PERSONALPHONE";
			} else if (UserSearchDTO.KEY_IDCARDNO.equals(key)) {
				query = query + " and b.idCardNo like :IDCARDNO";
			} else if (UserSearchDTO.KEY_LEVEL.equals(key)) {
				query = query + " and b.userLevelEntity.userLevelUuid = :LEVELUUID";
			} else if (UserSearchDTO.KEY_REGISTERDATE.equals(key)) {
				query = query
						+ " and b.registerDate >= :REGISTERDATESTART and b.registerDate <= :REGISTERDATEEND";
			}
		}
		query = query + " order by b.registerDate desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, UserEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (UserSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							"%" + userSearchDTO.getName() + "%");
				} else if (UserSearchDTO.KEY_PERSONALPHONE.equals(key)) {
					typedQuery.setParameter("PERSONALPHONE", "%"
							+ userSearchDTO.getPersonalPhone() + "%");
				} else if (UserSearchDTO.KEY_IDCARDNO.equals(key)) {
					typedQuery.setParameter("IDCARDNO",
							"%" + userSearchDTO.getIdCardNo() + "%");
				} else if (UserSearchDTO.KEY_LEVEL.equals(key)) {
					typedQuery.setParameter("LEVELUUID", userSearchDTO.getUserLevelUuid());
				} else if (UserSearchDTO.KEY_REGISTERDATE.equals(key)) {
					typedQuery.setParameter("REGISTERDATESTART",
							userSearchDTO.getRegisterDateStart());
					typedQuery.setParameter("REGISTERDATEEND",
							userSearchDTO.getRegisterDateEnd());
				}
			}
			userEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(startIndex).setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return userEntityList;
	}
	
	@Override
	public List<UserEntity> searchMerchantUsers(MerchantEntity merchantEntity, UserSearchDTO userSearchDTO,
			int startIndex, int pageSize) throws DAOException {
		List<UserEntity> userEntityList = new ArrayList<UserEntity>();
		String query = "select b from UserEntity b, MerchantUserEntity c where b.userUuid = c.userEntity.userUuid and c.merchantEntity.merchantUuid = :MERCHANTUUID and b.isActive = :ISACTIVE and b.isDeleted=:isDeleted";
		String[] keyArray = userSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (UserSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.name like :NAME";
			} else if (UserSearchDTO.KEY_PERSONALPHONE.equals(key)) {
				query = query + " and b.personalPhone like :PERSONALPHONE";
			} else if (UserSearchDTO.KEY_REGISTERDATE.equals(key)) {
				query = query
						+ " and b.registerDate >= :REGISTERDATESTART and b.registerDate <= :REGISTERDATEEND";
			}
		}
		query = query + " order by c.createDate desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, UserEntity.class);
			typedQuery.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid());
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (UserSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							"%" + userSearchDTO.getName() + "%");
				} else if (UserSearchDTO.KEY_PERSONALPHONE.equals(key)) {
					typedQuery.setParameter("PERSONALPHONE", "%"
							+ userSearchDTO.getPersonalPhone() + "%");
				} else if (UserSearchDTO.KEY_REGISTERDATE.equals(key)) {
					typedQuery.setParameter("REGISTERDATESTART",
							userSearchDTO.getRegisterDateStart());
					typedQuery.setParameter("REGISTERDATEEND",
							userSearchDTO.getRegisterDateEnd());
				}
			}
			userEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setParameter("ISACTIVE", Boolean.TRUE)
					.setFirstResult(startIndex).setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return userEntityList;
	}
	
	@Override
	public int searchUserTotal(UserSearchDTO userSearchDTO) throws DAOException {
		Long total = Long.valueOf(0);
		String query = "select count(b) from UserEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = userSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (UserSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.name like :NAME";
			} else if (UserSearchDTO.KEY_PERSONALPHONE.equals(key)) {
				query = query + " and b.personalPhone like :PERSONALPHONE";
			} else if (UserSearchDTO.KEY_IDCARDNO.equals(key)) {
				query = query + " and b.idCardNo like :IDCARDNO";
			} else if (UserSearchDTO.KEY_LEVEL.equals(key)) {
				query = query + " and b.userLevelEntity.userLevelUuid = :LEVELUUID";
			} else if (UserSearchDTO.KEY_REGISTERDATE.equals(key)) {
				query = query
						+ " and b.registerDate >= :REGISTERDATESTART and b.registerDate <= :REGISTERDATEEND";
			}
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (UserSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							"%" + userSearchDTO.getName() + "%");
				} else if (UserSearchDTO.KEY_PERSONALPHONE.equals(key)) {
					typedQuery.setParameter("PERSONALPHONE", "%"
							+ userSearchDTO.getPersonalPhone() + "%");
				} else if (UserSearchDTO.KEY_IDCARDNO.equals(key)) {
					typedQuery.setParameter("IDCARDNO",
							"%" + userSearchDTO.getIdCardNo() + "%");
				} else if (UserSearchDTO.KEY_LEVEL.equals(key)) {
					typedQuery.setParameter("LEVELUUID", userSearchDTO.getUserLevelUuid());
				} else if (UserSearchDTO.KEY_REGISTERDATE.equals(key)) {
					typedQuery.setParameter("REGISTERDATESTART",
							userSearchDTO.getRegisterDateStart());
					typedQuery.setParameter("REGISTERDATEEND",
							userSearchDTO.getRegisterDateEnd());
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
	public int searchMerchantUserTotal(MerchantEntity merchantEntity, UserSearchDTO userSearchDTO) throws DAOException {
		Long total = Long.valueOf(0);
		String query = "select count(b) from UserEntity b, MerchantUserEntity c where b.userUuid = c.userEntity.userUuid and c.merchantEntity.merchantUuid = :MERCHANTUUID and b.isActive = :ISACTIVE and b.isDeleted=:isDeleted";
		String[] keyArray = userSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (UserSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.name like :NAME";
			} else if (UserSearchDTO.KEY_PERSONALPHONE.equals(key)) {
				query = query + " and b.personalPhone like :PERSONALPHONE";
			} else if (UserSearchDTO.KEY_LEVEL.equals(key)) {
				query = query + " and b.userLevelEntity.userLevelUuid = :LEVELUUID";
			} else if (UserSearchDTO.KEY_REGISTERDATE.equals(key)) {
				query = query
						+ " and b.registerDate >= :REGISTERDATESTART and b.registerDate <= :REGISTERDATEEND";
			}
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			typedQuery.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid());
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (UserSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							"%" + userSearchDTO.getName() + "%");
				} else if (UserSearchDTO.KEY_PERSONALPHONE.equals(key)) {
					typedQuery.setParameter("PERSONALPHONE", "%"
							+ userSearchDTO.getPersonalPhone() + "%");
				} else if (UserSearchDTO.KEY_LEVEL.equals(key)) {
					typedQuery.setParameter("LEVELUUID", userSearchDTO.getUserLevelUuid());
				} else if (UserSearchDTO.KEY_REGISTERDATE.equals(key)) {
					typedQuery.setParameter("REGISTERDATESTART",
							userSearchDTO.getRegisterDateStart());
					typedQuery.setParameter("REGISTERDATEEND",
							userSearchDTO.getRegisterDateEnd());
				}
			}
			total = (Long) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setParameter("ISACTIVE", Boolean.TRUE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total.intValue();
	}
	
	@Override
	public void executeInsertUpdateNativeSQL(String sql) throws DAOException {
		try{
			em.createNativeQuery(sql).executeUpdate();
		}catch(Exception e){
			throw new DAOException(e);
		}
		
	}
}
