package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.TrainerDAO;
import com.mb.ext.core.entity.TrainerEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.service.spec.UserSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("trainerDAO")
@Qualifier("trainerDAO")
public class TrainerDAOImpl extends AbstractBaseDAO<TrainerEntity> implements TrainerDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public TrainerDAOImpl() {
		super();
		this.setEntityClass(TrainerEntity.class);
	}

	/**
	 * This method is used for inserting trainer information.
	 * 
	 * @param trainer
	 */
	
	@Override
	public List<TrainerEntity> searchTrainers(UserSearchDTO userSearchDTO,
			int startIndex, int pageSize) throws DAOException {
		List<TrainerEntity> trainerEntityList = new ArrayList<TrainerEntity>();
		String query = "select b from TrainerEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = userSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (UserSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.userEntity.name like :NAME";
			} else if (UserSearchDTO.KEY_TRAINERLEVEL.equals(key)) {
				query = query + " and b.trainerLevel = :TRAINERLEVEL";
			} else if (UserSearchDTO.KEY_IDCARDNO.equals(key)) {
				query = query + " and b.userEntity.idCardNo like :IDCARDNO";
			} else if (UserSearchDTO.KEY_PERSONALPHONE.equals(key)) {
				query = query + " and b.userEntity.personalPhone like :PERSONALPHONE";
			} else if (UserSearchDTO.KEY_PARENTTRAINER.equals(key)) {
				query = query + " and b.parentTrainerEntity.trainerUuid like :PARENTTRAINERUUID";
			} else if (UserSearchDTO.KEY_EFFECTIVEDATE.equals(key)) {
				query = query
						+ " and b.effectiveDate >= :EFFECTIVESTART and b.effectiveDate <= :EFFECTIVEEND";
			}
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, TrainerEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (UserSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							"%" + userSearchDTO.getName() + "%");
				} else if (UserSearchDTO.KEY_TRAINERLEVEL.equals(key)) {
					typedQuery.setParameter("TRAINERLEVEL",
							userSearchDTO.getTrainerLevel());
				} else if (UserSearchDTO.KEY_IDCARDNO.equals(key)) {
					typedQuery.setParameter("IDCARDNO",
							"%" + userSearchDTO.getIdCardNo() + "%");
				} else if (UserSearchDTO.KEY_PERSONALPHONE.equals(key)) {
					typedQuery.setParameter("PERSONALPHONE", "%"
							+ userSearchDTO.getPersonalPhone() + "%");
				} else if (UserSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID", 
							 userSearchDTO.getMerchantUuid());
				} else if (UserSearchDTO.KEY_PARENTTRAINER.equals(key)) {
					typedQuery.setParameter("PARENTTRAINERUUID", 
							 userSearchDTO.getParentTrainerUuid());
				} else if (UserSearchDTO.KEY_EFFECTIVEDATE.equals(key)) {
					typedQuery.setParameter("EFFECTIVESTART",
							userSearchDTO.getEffectiveDateStart());
					typedQuery.setParameter("EFFECTIVEEND",
							userSearchDTO.getEffectiveDateEnd());
				}
			}
			trainerEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(startIndex).setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return trainerEntityList;
	}
	
	@Override
	public int searchTrainerTotal(UserSearchDTO userSearchDTO) throws DAOException {
		Long total = Long.valueOf(0);
		String query = "select count(b) from TrainerEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = userSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (UserSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.userEntity.name like :NAME";
			} else if (UserSearchDTO.KEY_TRAINERLEVEL.equals(key)) {
				query = query + " and b.trainerLevel = :TRAINERLEVEL";
			} else if (UserSearchDTO.KEY_IDCARDNO.equals(key)) {
				query = query + " and b.userEntity.idCardNo like :IDCARDNO";
			} else if (UserSearchDTO.KEY_PERSONALPHONE.equals(key)) {
				query = query + " and b.userEntity.personalPhone like :PERSONALPHONE";
			} else if (UserSearchDTO.KEY_PARENTTRAINER.equals(key)) {
				query = query + " and b.parentTrainerEntity.trainerUuid like :PARENTTRAINERUUID";
			} else if (UserSearchDTO.KEY_EFFECTIVEDATE.equals(key)) {
				query = query
						+ " and b.effectiveDate >= :EFFECTIVESTART and b.effectiveDate <= :EFFECTIVEEND";
			}
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (UserSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							"%" + userSearchDTO.getName() + "%");
				} else if (UserSearchDTO.KEY_TRAINERLEVEL.equals(key)) {
					typedQuery.setParameter("TRAINERLEVEL",
							userSearchDTO.getTrainerLevel());
				} else if (UserSearchDTO.KEY_IDCARDNO.equals(key)) {
					typedQuery.setParameter("IDCARDNO",
							"%" + userSearchDTO.getIdCardNo() + "%");
				} else if (UserSearchDTO.KEY_PERSONALPHONE.equals(key)) {
					typedQuery.setParameter("PERSONALPHONE", "%"
							+ userSearchDTO.getPersonalPhone() + "%");
				} else if (UserSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID", 
							 userSearchDTO.getMerchantUuid());
				} else if (UserSearchDTO.KEY_PARENTTRAINER.equals(key)) {
					typedQuery.setParameter("PARENTTRAINERUUID", 
							 userSearchDTO.getParentTrainerUuid());
				} else if (UserSearchDTO.KEY_EFFECTIVEDATE.equals(key)) {
					typedQuery.setParameter("EFFECTIVESTART",
							userSearchDTO.getEffectiveDateStart());
					typedQuery.setParameter("EFFECTIVEEND",
							userSearchDTO.getEffectiveDateEnd());
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
	public void addTrainer(TrainerEntity trainerEntity) throws DAOException {
		save(trainerEntity);
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */

	@Override
	public void updateTrainer(TrainerEntity trainerEntity) throws DAOException {
		this.update(trainerEntity);
		
	}

	@Override
	public void deleteTrainer(TrainerEntity trainerEntity) throws DAOException {
		this.deletePhysical(trainerEntity);
		
	}

	@Override
	public void deletePhysicalTrainer(TrainerEntity trainerEntity) throws DAOException {
		this.deletePhysical(trainerEntity);
		
	}

	@Override
	public TrainerEntity getTrainerByUuid(String trainerUuid) throws DAOException {
		TrainerEntity trainerEntity = null;
		try {
			trainerEntity = (TrainerEntity) em
					.createQuery(
							"select b from TrainerEntity b where b.trainerUuid = :UUID and b.isDeleted=:isDeleted",
							TrainerEntity.class).setParameter("UUID", trainerUuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for trainer: " + trainerUuid);
		}
		return trainerEntity;
	}

	@Override
	public TrainerEntity getTrainerByUser(UserEntity userEntity) throws DAOException {
		TrainerEntity trainerEntity = null;
		try {
			trainerEntity = (TrainerEntity) em
					.createQuery(
							"select b from TrainerEntity b where b.userEntity.userUuid = :UUID and b.isDeleted=:isDeleted",
							TrainerEntity.class).setParameter("UUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for trainer: " + userEntity.getUserUuid());
		}
		return trainerEntity;
	}

	@Override
	public List<TrainerEntity> getTrainerByLevel(String trainerLevel) throws DAOException {
		List<TrainerEntity> trainerEntityList = new ArrayList<TrainerEntity>();;
		try {
			trainerEntityList = em
					.createQuery(
							"select b from TrainerEntity b where b.trainerLevel = :LEVEL and b.isDeleted=:isDeleted",
							TrainerEntity.class).setParameter("LEVEL", trainerLevel)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for trainer: " + trainerLevel);
		}
		return trainerEntityList;
	}

	@Override
	public int getTrainerCountByLevel(String trainerLevel) throws DAOException {
		Long trainerCount = new Long(0);
		try {
			trainerCount = em
					.createQuery(
							"select count(b.trainerUuid) from TrainerEntity b where b.trainerLevel = :LEVEL and b.isDeleted=:isDeleted",
							Long.class).setParameter("LEVEL", trainerLevel)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for trainer: " + trainerLevel);
		}
		return trainerCount.intValue();
	}

	@Override
	public List<TrainerEntity> getChildTrainers(TrainerEntity trainerEntity) throws DAOException {
		List<TrainerEntity> trainerEntityList = new ArrayList<TrainerEntity>();;
		try {
			trainerEntityList = em
					.createQuery(
							"select b from TrainerEntity b where b.parentTrainerEntity.trainerUuid = :TRAINERUUID and b.isDeleted=:isDeleted",
							TrainerEntity.class).setParameter("TRAINERUUID", trainerEntity.getTrainerUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for trainer: " + trainerEntity.getTrainerUuid());
		}
		return trainerEntityList;
	}

	@Override
	public int getChildTrainerCount(TrainerEntity trainerEntity) throws DAOException {
		Long trainerCount = new Long(0);
		try {
			trainerCount = em
					.createQuery(
							"select count(b.trainerUuid) from TrainerEntity b where b.parentTrainerEntity.trainerUuid = :TRAINERUUID and b.isDeleted=:isDeleted",
							Long.class).setParameter("TRAINERUUID", trainerEntity.getTrainerUuid())
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for trainer: " + trainerEntity.getTrainerUuid());
		}
		return trainerCount.intValue();
	}
	
	@Override
	public List<UserEntity> searchTrainerUsers(TrainerEntity trainerEntity, int startIndex, int pageSize) throws DAOException {
		List<UserEntity> userList = new ArrayList<UserEntity>();;
		try {
			userList = em
					.createQuery(
							"select b from UserEntity b where b.trainerEntity.trainerUuid = :TRAINERUUID and b.isDeleted=:isDeleted",
							UserEntity.class).setParameter("TRAINERUUID", trainerEntity.getTrainerUuid())
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(startIndex).setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for trainer: " + trainerEntity.getTrainerUuid());
		}
		return userList;
	}
	
	@Override
	public int searchTrainerUsersTotal(TrainerEntity trainerEntity) throws DAOException {
		Long total = null;
		try {
			total = em
					.createQuery(
							"select count(b) from UserEntity b where b.trainerEntity.trainerUuid = :TRAINERUUID and b.isDeleted=:isDeleted",
							Long.class).setParameter("TRAINERUUID", trainerEntity.getTrainerUuid())
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for trainer: " + trainerEntity.getTrainerUuid());
		}
		return total==null?0:total.intValue();
	}
}
