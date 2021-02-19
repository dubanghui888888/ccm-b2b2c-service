package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.UserWithdrawDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserWithdrawEntity;
import com.mb.ext.core.service.spec.WithdrawSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("userWithdrawDAO")
@Qualifier("userWithdrawDAO")
public class UserWithdrawDAOImpl extends AbstractBaseDAO<UserWithdrawEntity> implements UserWithdrawDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public UserWithdrawDAOImpl()
	{
		super();
		this.setEntityClass(UserWithdrawEntity.class);
	}

	@Override
	public void createUserWithdraw(UserWithdrawEntity userWithdrawEntity)
			throws DAOException {
		save(userWithdrawEntity);
		
	}

	@Override
	public void updateUserWithdraw(UserWithdrawEntity userWithdrawEntity)
			throws DAOException {
		update(userWithdrawEntity);
		
	}

	@Override
	public void deleteUserWithdraw(UserWithdrawEntity userWithdrawEntity)
			throws DAOException {
		deletePhysical(userWithdrawEntity);
		
	}

	@Override
	public List<UserWithdrawEntity> getWithdrawByUser(UserEntity userEntity) throws DAOException {
		List<UserWithdrawEntity> withdrawEntityList = new ArrayList<UserWithdrawEntity>();
		try {
			withdrawEntityList = em.createQuery("select b from UserWithdrawEntity b where b.userEntity.userUuid = :UUID and  b.isDeleted=:isDeleted order by b.createDate desc",UserWithdrawEntity.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user industry");
		}
		return withdrawEntityList;
	}
	
	@Override
	public List<UserWithdrawEntity> getWithdrawByStatus(String status) throws DAOException {
		List<UserWithdrawEntity> withdrawEntityList = new ArrayList<UserWithdrawEntity>();
		try {
			withdrawEntityList = em.createQuery("select b from UserWithdrawEntity b where b.withdrawStatus = :STATUS and  b.isDeleted=:isDeleted order by b.createDate desc",UserWithdrawEntity.class).setParameter("STATUS", status).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user industry");
		}
		return withdrawEntityList;
	}
	
	@Override
	public List<UserWithdrawEntity> getWithdraws() throws DAOException {
		List<UserWithdrawEntity> withdrawEntityList = new ArrayList<UserWithdrawEntity>();
		try {
			withdrawEntityList = em.createQuery("select b from UserWithdrawEntity b where  b.isDeleted=:isDeleted order by b.createDate desc",UserWithdrawEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user industry");
		}
		return withdrawEntityList;
	}
	
	@Override
	public UserWithdrawEntity getWithdrawByUuid(String uuid) throws DAOException {
		UserWithdrawEntity withdrawEntity = new UserWithdrawEntity();
		try {
			withdrawEntity = em.createQuery("select b from UserWithdrawEntity b where b.userWithdrawUuid = :UUID and  b.isDeleted=:isDeleted",UserWithdrawEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user industry");
		}
		return withdrawEntity;
	}
	
	@Override
	public List<UserWithdrawEntity> searchUserWithdraw(WithdrawSearchDTO withdrawSearchDTO,
			int startIndex, int pageSize) throws DAOException {
		List<UserWithdrawEntity> userWithdrawEntityList = new ArrayList<UserWithdrawEntity>();
		String query = "select b from UserWithdrawEntity b where  b.isDeleted=:isDeleted";
		String[] keyArray = withdrawSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (WithdrawSearchDTO.KEY_STATUS.equals(key)) {
				query = query + " and b.withdrawStatus in :WITHDRAWSTATUS";
			}else if (WithdrawSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.userEntity.name = :NAME";
			}else if (WithdrawSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid = :USERUUID";
			}else if (WithdrawSearchDTO.KEY_WITHDRAWDATE.equals(key)) {
				query = query
						+ " and date(b.withdrawTime)>= date(:WITHDRAWDATESTART) and date(b.withdrawTime) <= date(:WITHDRAWDATEEND)";
			}
		}
		query = query + " order by b.withdrawTime desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, UserWithdrawEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (WithdrawSearchDTO.KEY_STATUS.equals(key)) {
					List<String> statusList = Arrays.asList(withdrawSearchDTO.getWithdrawStatusArray());
					typedQuery.setParameter("WITHDRAWSTATUS",
							statusList);
				}else if (WithdrawSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							withdrawSearchDTO.getName());
				}else if (WithdrawSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							withdrawSearchDTO.getUserUuid());
				} else if (WithdrawSearchDTO.KEY_WITHDRAWDATE.equals(key)) {
					typedQuery.setParameter("WITHDRAWDATESTART",
							withdrawSearchDTO.getWithdrawDateStart());
					typedQuery.setParameter("WITHDRAWDATEEND",
							withdrawSearchDTO.getWithdrawDateEnd());
				}
			}
			userWithdrawEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(startIndex).setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return userWithdrawEntityList;
	}
	
	@Override
	public int searchUserWithdrawTotal(WithdrawSearchDTO withdrawSearchDTO) throws DAOException {
		Long total = new Long(0);
		String query = "select count(b) from UserWithdrawEntity b where  b.isDeleted=:isDeleted";
		String[] keyArray = withdrawSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (WithdrawSearchDTO.KEY_STATUS.equals(key)) {
				query = query + " and b.withdrawStatus in :WITHDRAWSTATUS";
			}else if (WithdrawSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid = :USERUUID";
			} else if (WithdrawSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.userEntity.name = :NAME";
			} else if (WithdrawSearchDTO.KEY_WITHDRAWDATE.equals(key)) {
				query = query
						+ " and date(b.withdrawTime) >= date(:WITHDRAWDATESTART) and date(b.withdrawTime) <= date(:WITHDRAWDATEEND)";
			}
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (WithdrawSearchDTO.KEY_STATUS.equals(key)) {
					List<String> statusList = Arrays.asList(withdrawSearchDTO.getWithdrawStatusArray());
					typedQuery.setParameter("WITHDRAWSTATUS",
							statusList);
				} else if (WithdrawSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							withdrawSearchDTO.getName());
				}else if (WithdrawSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							withdrawSearchDTO.getUserUuid());
				} else if (WithdrawSearchDTO.KEY_WITHDRAWDATE.equals(key)) {
					typedQuery.setParameter("WITHDRAWDATESTART",
							withdrawSearchDTO.getWithdrawDateStart());
					typedQuery.setParameter("WITHDRAWDATEEND",
							withdrawSearchDTO.getWithdrawDateEnd());
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
