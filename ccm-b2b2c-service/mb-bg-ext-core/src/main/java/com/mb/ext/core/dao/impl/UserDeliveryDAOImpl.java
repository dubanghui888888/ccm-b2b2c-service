package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.constant.DeliveryConstants;
import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.UserDeliveryDAO;
import com.mb.ext.core.entity.UserDeliveryEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.UserDeliverySearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("userDeliveryDAO")
@Qualifier("userDeliveryDAO")
public class UserDeliveryDAOImpl extends AbstractBaseDAO<UserDeliveryEntity> implements UserDeliveryDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public UserDeliveryDAOImpl()
	{
		super();
		this.setEntityClass(UserDeliveryEntity.class);
	}

	@Override
	public void addUserDelivery(UserDeliveryEntity userDeliveryEntity)
			throws DAOException {
		
		save(userDeliveryEntity);

	}

	@Override
	public void deleteUserDelivery(UserDeliveryEntity userDeliveryEntity)
			throws DAOException {
		deletePhysical(userDeliveryEntity);
		
	}
	
	@Override
	public void updateUserDelivery(UserDeliveryEntity userDeliveryEntity)
			throws DAOException {
		
		update(userDeliveryEntity);
		
	}

	@Override
	public UserDeliveryEntity findByUuid(String uuid) throws DAOException {
		
		UserDeliveryEntity UserDeliveryEntity = null;
		try {
			UserDeliveryEntity = (UserDeliveryEntity)em.createQuery("select b from UserDeliveryEntity b where b.userDeliveryUuid = :UUID and b.isDeleted=:isDeleted",UserDeliveryEntity.class)
					.setParameter("UUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+uuid);
		}
		return UserDeliveryEntity;
	}
	
	@Override
	public List<UserDeliveryEntity> findByUser(UserEntity userEntity) throws DAOException {
		
		List<UserDeliveryEntity> UserDeliveryEntityList = new ArrayList<UserDeliveryEntity>();;
		try {
			UserDeliveryEntityList = em.createQuery("select b from UserDeliveryEntity b where b.userEntity.userUuid = :UUID and b.isDeleted=:isDeleted",UserDeliveryEntity.class)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+userEntity.getUserUuid());
		}
		return UserDeliveryEntityList;
	}
	
	@Override
	public List<UserDeliveryEntity> findByMerchant(MerchantEntity merchantEntity) throws DAOException {
		
		List<UserDeliveryEntity> UserDeliveryEntityList = new ArrayList<UserDeliveryEntity>();;
		try {
			UserDeliveryEntityList = em.createQuery("select b from UserDeliveryEntity b where b.userEntity.merchantEntity.merchantUuid = :UUID and b.isDeleted=:isDeleted",UserDeliveryEntity.class)
					.setParameter("UUID", merchantEntity.getMerchantUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+merchantEntity.getMerchantUuid());
		}
		return UserDeliveryEntityList;
	}


	@Override
	public List<UserDeliveryEntity> findAll() throws DAOException {
		List<UserDeliveryEntity> UserDeliveryEntityList = new ArrayList<UserDeliveryEntity>();;
		try {
			UserDeliveryEntityList = em.createQuery("select b from UserDeliveryEntity b where  b.isDeleted=:isDeleted",UserDeliveryEntity.class)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: ");
		}
		return UserDeliveryEntityList;
	}

	@Override
	public List<UserDeliveryEntity> searchUserDelivery(UserDeliverySearchDTO userDeliverySearchDTO,
			int startIndex, int pageSize) throws DAOException {
		List<UserDeliveryEntity> userDeliveryEntityList = new ArrayList<UserDeliveryEntity>();
		String query = "select b from UserDeliveryEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = userDeliverySearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (UserDeliverySearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.userEntity.name like :NAME";
			} else if (UserDeliverySearchDTO.KEY_DELIVERYSTATUS.equals(key)) {
				query = query + " and b.deliveryStatus = :DELIVERYSTATUS";
			} else if (UserDeliverySearchDTO.KEY_APPLICATIONDATE.equals(key)) {
				query = query
						+ " and date(b.applicationTime) >= date(:APPLICATIONDATESTART) and date(b.applicationTime) <= date(:APPLICATIONDATEEND)";
			}
		}
		 query = query + " order by b.applicationTime desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, UserDeliveryEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (UserDeliverySearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							"%" + userDeliverySearchDTO.getName() + "%");
				} else if (UserDeliverySearchDTO.KEY_DELIVERYSTATUS.equals(key)) {
					typedQuery.setParameter("DELIVERYSTATUS",
							userDeliverySearchDTO.getDeliveryStatus());
				} else if (UserDeliverySearchDTO.KEY_APPLICATIONDATE.equals(key)) {
					typedQuery.setParameter("APPLICATIONDATESTART",
							userDeliverySearchDTO.getApplicationDateStart());
					typedQuery.setParameter("APPLICATIONDATEEND",
							userDeliverySearchDTO.getApplicationDateEnd());
				}
			}
			userDeliveryEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(startIndex).setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return userDeliveryEntityList;
	}
	
	@Override
	public int searchUserDeliveryTotal(UserDeliverySearchDTO userDeliverySearchDTO) throws DAOException {
		Long total = Long.valueOf(0);
		String query = "select count(b) from UserDeliveryEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = userDeliverySearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (UserDeliverySearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.userEntity.name like :NAME";
			} else if (UserDeliverySearchDTO.KEY_DELIVERYSTATUS.equals(key)) {
				query = query + " and b.deliveryStatus = :DELIVERYSTATUS";
			} else if (UserDeliverySearchDTO.KEY_APPLICATIONDATE.equals(key)) {
				query = query
						+ " and date(b.applicationTime) >= date(:APPLICATIONDATESTART) and date(b.applicationTime) <= date(:APPLICATIONDATEEND)";
			}
		}
		// String sortBy = userSearchDTO.getSortBy();
		// if("默认排序".equals(sortBy)){
		// query = query + " order by b.createDate desc";
		// }else if("车价最高".equals(sortBy)){
		// query = query + " order by b.priceSale desc";
		// }
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (UserDeliverySearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							"%" + userDeliverySearchDTO.getName() + "%");
				} else if (UserDeliverySearchDTO.KEY_DELIVERYSTATUS.equals(key)) {
					typedQuery.setParameter("DELIVERYSTATUS",
							userDeliverySearchDTO.getDeliveryStatus());
				} else if (UserDeliverySearchDTO.KEY_APPLICATIONDATE.equals(key)) {
					typedQuery.setParameter("APPLICATIONDATESTART",
							userDeliverySearchDTO.getApplicationDateStart());
					typedQuery.setParameter("APPLICATIONDATEEND",
							userDeliverySearchDTO.getApplicationDateEnd());
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
	public int getPendingDeliveryQuantity() throws DAOException {
		Long total = null;
		try {
			total = em.createQuery("select sum(b.deliveryQuantity) from UserDeliveryEntity b where b.deliveryStatus = :DELIVERYSTATUS and b.isDeleted=:isDeleted",Long.class)
					.setParameter("DELIVERYSTATUS", DeliveryConstants.DELIVERY_STATUS_CREATED)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: ");
		}
		if(total == null)	total = new Long(0);
		return total.intValue();
	}

}
