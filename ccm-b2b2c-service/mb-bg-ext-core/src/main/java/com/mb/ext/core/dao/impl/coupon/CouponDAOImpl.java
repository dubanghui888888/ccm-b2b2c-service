package com.mb.ext.core.dao.impl.coupon;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.coupon.CouponDAO;
import com.mb.ext.core.entity.coupon.CouponEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.coupon.CouponSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("couponDAO")
@Qualifier("couponDAO")
public class CouponDAOImpl extends AbstractBaseDAO<CouponEntity> implements CouponDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public CouponDAOImpl()
	{
		super();
		this.setEntityClass(CouponEntity.class);
	}

	@Override
	public void createCoupon(CouponEntity couponEntity) throws DAOException {
		this.save(couponEntity);
		
	}

	@Override
	public void updateCoupon(CouponEntity couponEntity) throws DAOException {
		this.update(couponEntity);
		
	}

	@Override
	public void deleteCoupon(CouponEntity couponEntity) throws DAOException {
		this.delete(couponEntity);
		
	}

	@Override
	public CouponEntity getCouponByName(String name) throws DAOException {
		CouponEntity couponEntity = null;
		try {
			couponEntity = (CouponEntity) em
					.createQuery(
							"select b from CouponEntity b where b.name = :NAME and b.isDeleted=:isDeleted",
							CouponEntity.class)
					.setParameter("NAME", name)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for coupon: " + name);
		}
		return couponEntity;
	}

	@Override
	public CouponEntity getCouponByUuid(String couponUuid) throws DAOException {
//		String entId = getEntId();
		CouponEntity couponEntity = null;
		try {
			couponEntity = (CouponEntity) em
					.createQuery(
							"select b from CouponEntity b where b.couponUuid = :COUPONUUID and b.isDeleted=:isDeleted")
					.setParameter("COUPONUUID", couponUuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for coupon: " + couponUuid);
		}
		return couponEntity;
	}

	@Override
	public List<CouponEntity> getAllCoupons() throws DAOException {
//		String entId = getEntId();
		List<CouponEntity> couponEntityList = new ArrayList<CouponEntity>();
		try {
			couponEntityList =  em
					.createQuery(
							"select b from CouponEntity b where b.isDeleted=:isDeleted",
							CouponEntity.class)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for coupon: ");
		}
		return couponEntityList;
	}

	@Override
	public List<CouponEntity> getActiveCoupons() throws DAOException {
		List<CouponEntity> couponEntityList = new ArrayList<CouponEntity>();
		try {
			couponEntityList =  em
					.createQuery(
							"select b from CouponEntity b where b.isActive is true and b.isDeleted=:isDeleted",
							CouponEntity.class)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for coupon: ");
		}
		return couponEntityList;
	}
	
	@Override
	public List<CouponEntity> getCouponByMerchant(MerchantEntity merchantEntity) throws DAOException {
		List<CouponEntity> couponEntityList = new ArrayList<CouponEntity>();
		try {
			couponEntityList =  em
					.createQuery(
							"select b from CouponEntity b where   b.merchantEntity.merchantUuid = :UUID and b.isDeleted=:isDeleted",
							CouponEntity.class)
					.setParameter("UUID", merchantEntity.getMerchantUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for coupon: ");
		}
		return couponEntityList;
	}

	@Override
	public List<CouponEntity> searchCoupon(CouponSearchDTO couponSearchDTO) throws DAOException {

		List<CouponEntity> couponEntityList = new ArrayList<CouponEntity>();
		String query = "select b from CouponEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = couponSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (CouponSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.name like :NAME";
			} else if (CouponSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (CouponSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (CouponSearchDTO.KEY_MERCHANT_MOBILE_NO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo = :MERCHANTMOBILENO";
			} else if (CouponSearchDTO.KEY_TYPE.equals(key)) {
				query = query + " and b.type = :TYPE";
			} else if (CouponSearchDTO.KEY_ACTIVE.equals(key)) {
				query = query + " and b.isActive= :ISACTIVE";
			} else if (CouponSearchDTO.KEY_PRODUCT.equals(key)) {
				query = query + " and (b.benefitType = '0' or :PRODUCTUUID in (select c.productEntity.productUuid from CouponProductEntity c where c.couponEntity.couponUuid = b.couponUuid))";
			}
		}
		query = query + " order by b.createDate desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, CouponEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (CouponSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							"%" + couponSearchDTO.getName() + "%");
				} else if (CouponSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							couponSearchDTO.getMerchantUuid());
				} else if (CouponSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%"+couponSearchDTO.getMerchantName()+"%");
				} else if (CouponSearchDTO.KEY_MERCHANT_MOBILE_NO.equals(key)) {
					typedQuery.setParameter("MERCHANTMOBILENO",
							couponSearchDTO.getMerchantMobileNo());
				} else if (CouponSearchDTO.KEY_TYPE.equals(key)) {
					typedQuery.setParameter("TYPE",
							couponSearchDTO.getType());
				} else if (CouponSearchDTO.KEY_ACTIVE.equals(key)) {
					typedQuery.setParameter("ISACTIVE",
							couponSearchDTO.isActive());
				} else if (CouponSearchDTO.KEY_PRODUCT.equals(key)) {
					typedQuery.setParameter("PRODUCTUUID",
							couponSearchDTO.getProductUuid());
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
	public int searchCouponTotal(CouponSearchDTO couponSearchDTO) throws DAOException {

		Long total = null;
		String query = "select count(b) from CouponEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = couponSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (CouponSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.name like :NAME";
			} else if (CouponSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (CouponSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (CouponSearchDTO.KEY_MERCHANT_MOBILE_NO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo = :MERCHANTMOBILENO";
			} else if (CouponSearchDTO.KEY_TYPE.equals(key)) {
				query = query + " and b.type = :TYPE";
			} else if (CouponSearchDTO.KEY_ACTIVE.equals(key)) {
				query = query + " and b.isActive= :ISACTIVE";
			} else if (CouponSearchDTO.KEY_PRODUCT.equals(key)) {
				query = query + " and (b.benefitType = '0' or :PRODUCTUUID in (select c.productEntity.productUuid from CouponProductEntity c where c.couponEntity.couponUuid = b.couponUuid))";
			}
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (CouponSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							"%" + couponSearchDTO.getName() + "%");
				} else if (CouponSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							couponSearchDTO.getMerchantUuid());
				} else if (CouponSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%"+couponSearchDTO.getMerchantName()+"%");
				} else if (CouponSearchDTO.KEY_MERCHANT_MOBILE_NO.equals(key)) {
					typedQuery.setParameter("MERCHANTMOBILENO",
							couponSearchDTO.getMerchantMobileNo());
				} else if (CouponSearchDTO.KEY_TYPE.equals(key)) {
					typedQuery.setParameter("TYPE",
							couponSearchDTO.getType());
				} else if (CouponSearchDTO.KEY_ACTIVE.equals(key)) {
					typedQuery.setParameter("ISACTIVE",
							couponSearchDTO.isActive());
				} else if (CouponSearchDTO.KEY_PRODUCT.equals(key)) {
					typedQuery.setParameter("PRODUCTUUID",
							couponSearchDTO.getProductUuid());
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
