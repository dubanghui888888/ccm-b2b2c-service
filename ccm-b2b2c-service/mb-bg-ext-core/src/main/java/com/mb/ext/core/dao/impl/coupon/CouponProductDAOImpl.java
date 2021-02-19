package com.mb.ext.core.dao.impl.coupon;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.coupon.CouponProductDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.coupon.CouponEntity;
import com.mb.ext.core.entity.coupon.CouponProductEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("couponProductDAO")
@Qualifier("couponProductDAO")
public class CouponProductDAOImpl extends AbstractBaseDAO<CouponProductEntity> implements CouponProductDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public CouponProductDAOImpl()
	{
		super();
		this.setEntityClass(CouponProductEntity.class);
	}

	@Override
	public void createCouponProduct(CouponProductEntity couponProductEntity) throws DAOException {
		this.save(couponProductEntity);
		
	}

	@Override
	public void updateCouponProduct(CouponProductEntity couponProductEntity) throws DAOException {
		this.update(couponProductEntity);
		
	}

	@Override
	public void deleteCouponProduct(CouponProductEntity couponProductEntity) throws DAOException {
		this.deletePhysical(couponProductEntity);
		
	}

	@Override
	public List<CouponProductEntity> getCouponByUser(UserEntity userEntity) throws DAOException {
		List<CouponProductEntity> couponProductEntityList = new ArrayList<CouponProductEntity>();
		try {
			couponProductEntityList =  em
					.createQuery(
							"select b from CouponEntity b where b.userEntity.userUuid = :USERUUID and b.isDeleted=:isDeleted",
							CouponProductEntity.class)
					.setParameter("USERUUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for coupon: " + userEntity.getUserUuid());
		}
		return couponProductEntityList;
	}
	
	@Override
	public List<CouponProductEntity> getCouponProductByCoupon(CouponEntity couponEntity) throws DAOException {
		List<CouponProductEntity> couponProductEntityList = new ArrayList<CouponProductEntity>();
		try {
			couponProductEntityList =  em
					.createQuery(
							"select b from CouponEntity b whereand b.couponEntity.couponUuid = :COUPONUUID and b.isDeleted=:isDeleted",
							CouponProductEntity.class)
					.setParameter("COUPONUUID", couponEntity.getCouponUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for coupon: " + couponEntity.getCouponUuid());
		}
		return couponProductEntityList;
	}
	
	@Override
	public CouponProductEntity getCouponProductByUuid(String uuid) throws DAOException {
		CouponProductEntity couponProductEntity = null;
		try {
			couponProductEntity =  em
					.createQuery(
							"select b from CouponEntity b where b.couponProductUuid = :UUID and b.isDeleted=:isDeleted",
							CouponProductEntity.class)
					.setParameter("UUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for coupon: " + uuid);
		}
		return couponProductEntity;
	}

}
