package com.mb.ext.core.dao.impl.coupon;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.coupon.UserCouponDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.coupon.CouponEntity;
import com.mb.ext.core.entity.coupon.UserCouponEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.coupon.CouponSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("userCouponDAO")
@Qualifier("userCouponDAO")
public class UserCouponDAOImpl extends AbstractBaseDAO<UserCouponEntity> implements UserCouponDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public UserCouponDAOImpl()
	{
		super();
		this.setEntityClass(UserCouponEntity.class);
	}

	@Override
	public void createUserCoupon(UserCouponEntity userCouponEntity) throws DAOException {
		this.save(userCouponEntity);
		
	}

	@Override
	public void updateUserCoupon(UserCouponEntity userCouponEntity) throws DAOException {
		this.update(userCouponEntity);
		
	}

	@Override
	public void deleteUserCoupon(UserCouponEntity userCouponEntity) throws DAOException {
		this.deletePhysical(userCouponEntity);
		
	}

	@Override
	public List<UserCouponEntity> getCouponByUser(UserEntity userEntity) throws DAOException {
		List<UserCouponEntity> userCouponEntityList = new ArrayList<UserCouponEntity>();
		try {
			userCouponEntityList =  em
					.createQuery(
							"select b from UserCouponEntity b where b.userEntity.userUuid = :USERUUID and b.isDeleted=:isDeleted",
							UserCouponEntity.class)
					.setParameter("USERUUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for coupon: " + userEntity.getUserUuid());
		}
		return userCouponEntityList;
	}
	
	@Override
	public List<UserCouponEntity> getCouponByUserMerchant(UserEntity userEntity, MerchantEntity merchantEntity) throws DAOException {
		List<UserCouponEntity> couponEntityList = new ArrayList<UserCouponEntity>();
		try {
			couponEntityList =  em
					.createQuery(
							"select b from UserCouponEntity b where b.userEntity.userUuid = :USERUUID and b.couponEntity.merchantEntity.merchantUuid = :MERCHANTUUID and b.isDeleted=:isDeleted",
							UserCouponEntity.class)
					.setParameter("USERUUID", userEntity.getUserUuid())
					.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for coupon: " + userEntity.getUserUuid());
		}
		return couponEntityList;
	}
	
	@Override
	public List<UserCouponEntity> getUserCouponByCoupon(CouponEntity couponEntity) throws DAOException {
		List<UserCouponEntity> userCouponEntityList = new ArrayList<UserCouponEntity>();
		try {
			userCouponEntityList =  em
					.createQuery(
							"select b from UserCouponEntity b where b.couponEntity.couponUuid = :COUPONUUID and b.isDeleted=:isDeleted",
							UserCouponEntity.class)
					.setParameter("COUPONUUID", couponEntity.getCouponUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for coupon: " + couponEntity.getCouponUuid());
		}
		return userCouponEntityList;
	}
	
	@Override
	public List<UserCouponEntity> getUserCouponByUserCoupon(UserEntity userEntity, CouponEntity couponEntity) throws DAOException {
		List<UserCouponEntity> userCouponEntityList = new ArrayList<UserCouponEntity>();
		try {
			userCouponEntityList =  em
					.createQuery(
							"select b from UserCouponEntity b where b.couponEntity.couponUuid = :COUPONUUID and b.userEntity.userUuid = :USERUUID and b.isDeleted=:isDeleted",
							UserCouponEntity.class)
					.setParameter("COUPONUUID", couponEntity.getCouponUuid())
					.setParameter("USERUUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for coupon: " + couponEntity.getCouponUuid());
		}
		return userCouponEntityList;
	}
	
	@Override
	public UserCouponEntity getUserCouponByUuid(String uuid) throws DAOException {
		UserCouponEntity userCouponEntity = null;
		try {
			userCouponEntity =  em
					.createQuery(
							"select b from UserCouponEntity b where b.userCouponUuid = :UUID and b.isDeleted=:isDeleted",
							UserCouponEntity.class)
					.setParameter("UUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for coupon: " + uuid);
		}
		return userCouponEntity;
	}

	@Override
	public List<UserCouponEntity> searchUserCoupon(CouponSearchDTO couponSearchDTO) throws DAOException {

		List<UserCouponEntity> couponEntityList = new ArrayList<UserCouponEntity>();
		String query = "select b from UserCouponEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = couponSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (CouponSearchDTO.KEY_COUPON_USER_NAME.equals(key)) {
				query = query + " and b.userEntity.name like :USER_NAME";
			} else if (CouponSearchDTO.KEY_COUPON_USER_MOBILENO.equals(key)) {
				query = query + " and b.userEntity.personalPhone like :USER_MOBILENO";
			} else if (CouponSearchDTO.KEY_COUPON_UUID.equals(key)) {
				query = query + " and b.couponEntity.couponUuid= :COUPON_UUID";
			} else if (CouponSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid= :USER_UUID";
			} else if (CouponSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.couponEntity.merchantEntity.merchantUuid= :MERCHANT_UUID";
			} else if (CouponSearchDTO.KEY_COUPON_ISUSED.equals(key)) {
				query = query + " and b.isUsed = :IS_USED";
			} else if (CouponSearchDTO.KEY_COUPON_EXPIRED.equals(key) && couponSearchDTO.isExpired()) {
				query = query + " and date(b.endDate) < date(current_date())";
			} else if (CouponSearchDTO.KEY_COUPON_EXPIRED.equals(key) && !couponSearchDTO.isExpired()) {
				query = query + " and date(b.endDate) >= date(current_date())";
			} else if (CouponSearchDTO.KEY_COUPON_STARTED.equals(key) && couponSearchDTO.isStarted()) {
				query = query + " and date(b.startDate) <= date(current_date())";
			} else if (CouponSearchDTO.KEY_COUPON_STARTED.equals(key) && !couponSearchDTO.isStarted()) {
				query = query + " and date(b.startDate) > date(current_date())";
			}
		}
		query = query + " order by b.createDate desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, UserCouponEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (CouponSearchDTO.KEY_COUPON_USER_NAME.equals(key)) {
					typedQuery.setParameter("USER_NAME",
							"%" + couponSearchDTO.getUserName() + "%");
				} else if (CouponSearchDTO.KEY_COUPON_USER_MOBILENO.equals(key)) {
					typedQuery.setParameter("USER_MOBILENO",
							"%" + couponSearchDTO.getUserMobileNo() + "%");
				} else if (CouponSearchDTO.KEY_COUPON_UUID.equals(key)) {
					typedQuery.setParameter("COUPON_UUID",
							couponSearchDTO.getCouponUuid());
				} else if (CouponSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USER_UUID",
							couponSearchDTO.getUserUuid());
				} else if (CouponSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANT_UUID",
							couponSearchDTO.getMerchantUuid());
				} else if (CouponSearchDTO.KEY_COUPON_ISUSED.equals(key)) {
					typedQuery.setParameter("IS_USED",
							couponSearchDTO.isUsed());
				}
			}
			couponEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(couponSearchDTO.getStartIndex()).setMaxResults(couponSearchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for coupon");
		}
		return couponEntityList;
	
	}

	@Override
	public int searchUserCouponTotal(CouponSearchDTO couponSearchDTO) throws DAOException {

		Long total = null;
		String query = "select count(b) from UserCouponEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = couponSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (CouponSearchDTO.KEY_COUPON_USER_NAME.equals(key)) {
				query = query + " and b.userEntity.name like :USER_NAME";
			} else if (CouponSearchDTO.KEY_COUPON_USER_MOBILENO.equals(key)) {
				query = query + " and b.userEntity.personalPhone like :USER_MOBILENO";
			} else if (CouponSearchDTO.KEY_COUPON_UUID.equals(key)) {
				query = query + " and b.couponEntity.couponUuid= :COUPON_UUID";
			} else if (CouponSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid= :USER_UUID";
			} else if (CouponSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.couponEntity.merchantEntity.merchantUuid= :MERCHANT_UUID";
			} else if (CouponSearchDTO.KEY_COUPON_ISUSED.equals(key)) {
				query = query + " and b.isUsed = :IS_USED";
			} else if (CouponSearchDTO.KEY_COUPON_EXPIRED.equals(key) && couponSearchDTO.isExpired()) {
				query = query + " and date(b.endDate) < date(current_date())";
			} else if (CouponSearchDTO.KEY_COUPON_EXPIRED.equals(key) && !couponSearchDTO.isExpired()) {
				query = query + " and date(b.endDate) >= date(current_date())";
			} else if (CouponSearchDTO.KEY_COUPON_STARTED.equals(key) && couponSearchDTO.isStarted()) {
				query = query + " and date(b.startDate) <= date(current_date())";
			} else if (CouponSearchDTO.KEY_COUPON_STARTED.equals(key) && !couponSearchDTO.isStarted()) {
				query = query + " and date(b.startDate) > date(current_date())";
			}
		}
		query = query + " order by b.createDate desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (CouponSearchDTO.KEY_COUPON_USER_NAME.equals(key)) {
					typedQuery.setParameter("USER_NAME",
							"%" + couponSearchDTO.getUserName() + "%");
				} else if (CouponSearchDTO.KEY_COUPON_USER_MOBILENO.equals(key)) {
					typedQuery.setParameter("USER_MOBILENO",
							"%" + couponSearchDTO.getUserMobileNo() + "%");
				} else if (CouponSearchDTO.KEY_COUPON_UUID.equals(key)) {
					typedQuery.setParameter("COUPON_UUID",
							couponSearchDTO.getCouponUuid());
				} else if (CouponSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USER_UUID",
							couponSearchDTO.getUserUuid());
				} else if (CouponSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANT_UUID",
							couponSearchDTO.getMerchantUuid());
				} else if (CouponSearchDTO.KEY_COUPON_ISUSED.equals(key)) {
					typedQuery.setParameter("IS_USED",
							couponSearchDTO.isUsed());
				}
			}
			total = (Long)typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for coupon");
		}
		return total == null?0:total.intValue();
	
	}

}
