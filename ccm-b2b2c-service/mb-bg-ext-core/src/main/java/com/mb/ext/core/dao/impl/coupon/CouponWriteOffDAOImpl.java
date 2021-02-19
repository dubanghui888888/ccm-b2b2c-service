package com.mb.ext.core.dao.impl.coupon;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.coupon.CouponWriteOffDAO;
import com.mb.ext.core.entity.coupon.CouponWriteOffEntity;
import com.mb.ext.core.entity.coupon.UserCouponEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.CouponWriteOffSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("couponWriteOffDAO")
@Qualifier("couponWriteOffDAO")
public class CouponWriteOffDAOImpl extends AbstractBaseDAO<CouponWriteOffEntity> implements CouponWriteOffDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public CouponWriteOffDAOImpl()
	{
		super();
		this.setEntityClass(CouponWriteOffEntity.class);
	}

	@Override
	public void createCouponWriteOff(CouponWriteOffEntity couponWriteOffEntity)
			throws DAOException {
		this.save(couponWriteOffEntity);
	}

	@Override
	public void updateCouponWriteOff(CouponWriteOffEntity couponWriteOffEntity)
			throws DAOException {
		this.update(couponWriteOffEntity);
	}

	@Override
	public void deleteCouponWriteOff(CouponWriteOffEntity couponWriteOffEntity)
			throws DAOException {
		this.delete(couponWriteOffEntity);
	}

	@Override
	public List<CouponWriteOffEntity> searchCouponWriteOff(
			CouponWriteOffSearchDTO searchDTO) throws DAOException {
		List<CouponWriteOffEntity> couponWriteOffEntityList = new ArrayList<CouponWriteOffEntity>();
		String query = "select b from CouponWriteOffEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = searchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if(CouponWriteOffSearchDTO.KEY_TYPE.equals(key)) {
				query = query + " and b.userCouponEntity.type like :TYPE";
			} else if (CouponWriteOffSearchDTO.KEY_USER_UUID.equals(key)) {
				query = query + " and b.userCouponEntity.userEntity.userUuid = :USERUUID";
			} else if (CouponWriteOffSearchDTO.KEY_PHONE.equals(key)) {
				query = query + " and b.userCouponEntity.userEntity.personalPhone like :PHONE";
			} else if (CouponWriteOffSearchDTO.KEY_DATE.equals(key)) {
				query = query
						+ " and date(b.couponWriteOffTime) >= :WRITEOFFDATESTART and date(b.couponWriteOffTime) <= :WRITEOFFDATEEND";
			} else if (CouponWriteOffSearchDTO.KEY_MERCHANT_UUID.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (CouponWriteOffSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (CouponWriteOffSearchDTO.KEY_MERCHANT_PHONE.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo like :MERCHANTPHONE";
			} 
		}
		if(!StringUtils.isEmpty(searchDTO.getSortBy())) {
			query = query + " order by b."+searchDTO.getSortBy();
		}else {
			query = query + " order by b.createDate desc";
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, CouponWriteOffEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (CouponWriteOffSearchDTO.KEY_TYPE.equals(key)) {
					typedQuery.setParameter("TYPE",
							"%"+searchDTO.getType()+"%");
				} else if (CouponWriteOffSearchDTO.KEY_USER_UUID.equals(key)) {
					typedQuery.setParameter("USERUUID",
							searchDTO.getUserUuid());
				} else if (CouponWriteOffSearchDTO.KEY_PHONE.equals(key)) {
					typedQuery.setParameter("PHONE",
							"%"+searchDTO.getPhone()+"%");
				} else if (CouponWriteOffSearchDTO.KEY_MERCHANT_UUID.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							searchDTO.getMerchantUuid());
				} else if (CouponWriteOffSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%"+searchDTO.getMerchantName()+"%");
				} else if (CouponWriteOffSearchDTO.KEY_MERCHANT_PHONE.equals(key)) {
					typedQuery.setParameter("MERCHANTPHONE",
							"%"+searchDTO.getMerchantPhone()+"%");
				} else if (CouponWriteOffSearchDTO.KEY_DATE.equals(key)) {
					typedQuery.setParameter("WRITEOFFDATESTART",
							searchDTO.getWriteOffDateStart());
					typedQuery.setParameter("WRITEOFFDATEEND",
							searchDTO.getWriteOffDateEnd());
				} 
			}
			couponWriteOffEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(searchDTO.getStartIndex()).setMaxResults(searchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for couponWriteOff");
		}
		return couponWriteOffEntityList;
	}
	
	@Override
	public int getCouponWriteOffTotal(CouponWriteOffSearchDTO searchDTO)
			throws DAOException {
		Long total = null;
		String query = "select count(b) from CouponWriteOffEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = searchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if(CouponWriteOffSearchDTO.KEY_TYPE.equals(key)) {
				query = query + " and b.userCouponEntity.type like :TYPE";
			} else if (CouponWriteOffSearchDTO.KEY_USER_UUID.equals(key)) {
				query = query + " and b.userCouponEntity.userEntity.userUuid = :USERUUID";
			} else if (CouponWriteOffSearchDTO.KEY_DATE.equals(key)) {
				query = query
						+ " and date(b.couponWriteOffTime) >= :WRITEOFFDATESTART and date(b.couponWriteOffTime) <= :WRITEOFFDATEEND";
			} else if (CouponWriteOffSearchDTO.KEY_MERCHANT_UUID.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (CouponWriteOffSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (CouponWriteOffSearchDTO.KEY_PHONE.equals(key)) {
				query = query + " and b.userCouponEntity.userEntity.personalPhone like :PHONE";
			} else if (CouponWriteOffSearchDTO.KEY_MERCHANT_PHONE.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo like :MERCHANTPHONE";
			} 
		}
		if(!StringUtils.isEmpty(searchDTO.getSortBy())) {
			query = query + " order by b."+searchDTO.getSortBy();
		}else {
			query = query + " order by b.createDate desc";
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (CouponWriteOffSearchDTO.KEY_TYPE.equals(key)) {
					typedQuery.setParameter("TYPE",
							"%"+searchDTO.getType()+"%");
				} else if (CouponWriteOffSearchDTO.KEY_USER_UUID.equals(key)) {
					typedQuery.setParameter("USERUUID",
							searchDTO.getUserUuid());
				} else if (CouponWriteOffSearchDTO.KEY_MERCHANT_UUID.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							searchDTO.getMerchantUuid());
				} else if (CouponWriteOffSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%"+searchDTO.getMerchantName()+"%");
				} else if (CouponWriteOffSearchDTO.KEY_DATE.equals(key)) {
					typedQuery.setParameter("WRITEOFFDATESTART",
							searchDTO.getWriteOffDateStart());
					typedQuery.setParameter("WRITEOFFDATEEND",
							searchDTO.getWriteOffDateEnd());
				} else if (CouponWriteOffSearchDTO.KEY_MERCHANT_PHONE.equals(key)) {
					typedQuery.setParameter("MERCHANTPHONE",
							"%"+searchDTO.getMerchantPhone()+"%");
				} else if (CouponWriteOffSearchDTO.KEY_PHONE.equals(key)) {
					typedQuery.setParameter("PHONE",
							"%"+searchDTO.getPhone()+"%");
				}
			}
			total = (Long) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for couponWriteOff");
		}
		return total == null?0:total.intValue();
	}

	@Override
	public CouponWriteOffEntity getCouponWriteOffByUuid(String uuid)
			throws DAOException {
		CouponWriteOffEntity couponWriteOffEntity = null;
		try {
			couponWriteOffEntity = (CouponWriteOffEntity) em
					.createQuery(
							"select b from CouponWriteOffEntity b where b.couponWriteOffUuid = :COUPONWRITEOFFUUID and b.isDeleted=:isDeleted",CouponWriteOffEntity.class)
					.setParameter("COUPONWRITEOFFUUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for couponWriteOff: couponWriteOffUuid=" + uuid);
		}
		return couponWriteOffEntity;
	}

	@Override
	public List<CouponWriteOffEntity> getCouponWriteOffByUserCoupon(
			UserCouponEntity userCouponEntity) throws DAOException {
		List<CouponWriteOffEntity> couponWriteOffEntityList = new ArrayList<CouponWriteOffEntity>();
		try {
			couponWriteOffEntityList =  em
					.createQuery(
							"select b from CouponWriteOffEntity b where b.userCouponEntity.userCouponUuid= :USERCOUPONUUID and b.isDeleted=:isDeleted",
							CouponWriteOffEntity.class)
					.setParameter("USERCOUPONUUID", userCouponEntity.getUserCouponUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for couponWriteOff: userCouponUuid=" + userCouponEntity.getUserCouponUuid());
		}
		return couponWriteOffEntityList;
	}

	@Override
	public List<CouponWriteOffEntity> getCouponWriteOffByMerchant(
			MerchantEntity merchantEntity) throws DAOException {
		// TODO Auto-generated method stub
		List<CouponWriteOffEntity> couponWriteOffEntityList = new ArrayList<CouponWriteOffEntity>();
		try {
			couponWriteOffEntityList =  em
					.createQuery(
							"select b from CouponWriteOffEntity b where b.merchantEntity= :MERCHANTUUID and b.isDeleted=:isDeleted",
							CouponWriteOffEntity.class)
					.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for couponWriteOff: merchantUuid=" + merchantEntity.getMerchantUuid());
		}
		return couponWriteOffEntityList;
	}
	
}
