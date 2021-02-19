package com.mb.ext.core.dao.impl.coupon;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.coupon.MerchantCouponDAO;
import com.mb.ext.core.entity.coupon.CouponEntity;
import com.mb.ext.core.entity.coupon.MerchantCouponEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.coupon.CouponSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("merchantCouponDAO")
@Qualifier("merchantCouponDAO")
public class MerchantCouponDAOImpl extends AbstractBaseDAO<MerchantCouponEntity> implements MerchantCouponDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public MerchantCouponDAOImpl()
	{
		super();
		this.setEntityClass(MerchantCouponEntity.class);
	}

	@Override
	public void createMerchantCoupon(MerchantCouponEntity merchantCouponEntity)
			throws DAOException {
		this.save(merchantCouponEntity);
	}

	@Override
	public void updateMerchantCoupon(MerchantCouponEntity merchantCouponEntity)
			throws DAOException {
		this.update(merchantCouponEntity);
	}

	@Override
	public void deleteMerchantCoupon(MerchantCouponEntity merchantCouponEntity)
			throws DAOException {
		this.delete(merchantCouponEntity);
	}

	@Override
	public List<MerchantCouponEntity> searchMerchantCouponEntity(
			CouponSearchDTO searchDTO) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MerchantCouponEntity> getMerchantCouponByCoupon(
			CouponEntity couponEntity) throws DAOException {
		List<MerchantCouponEntity> merchantCouponEntityList = new ArrayList<MerchantCouponEntity>();
		try {
			merchantCouponEntityList =  em
					.createQuery(
							"select b from MerchantCouponEntity b where b.couponEntity.couponUuid= :COUPONUUID and b.isDeleted=:isDeleted",
							MerchantCouponEntity.class)
					.setParameter("COUPONUUID", couponEntity.getCouponUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchantCoupon: couponUuid==" + couponEntity.getCouponUuid());
		}
		return merchantCouponEntityList;
	}

	@Override
	public List<MerchantCouponEntity> getMerchantCouponByMerchant(
			MerchantEntity merchantEntity) throws DAOException {
		List<MerchantCouponEntity> merchantCouponEntityList = new ArrayList<MerchantCouponEntity>();
		try {
			merchantCouponEntityList =  em
					.createQuery(
							"select b from MerchantCouponEntity b where b.merchantEntity.merchantUuid= :MERCHANTUUID and b.isDeleted=:isDeleted",
							MerchantCouponEntity.class)
					.setParameter("COUPONUUID", merchantEntity.getMerchantUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchantCoupon: merchantUuid==" + merchantEntity.getMerchantUuid());
		}
		return merchantCouponEntityList;
	}

	@Override
	public MerchantCouponEntity getMerchantCouponByUuid(String uuid)
			throws DAOException {
		MerchantCouponEntity merchantCouponEntity = null;
		try {
			merchantCouponEntity = (MerchantCouponEntity) em
					.createQuery(
							"select b from MerchantCouponEntity b where b.merchantCouponUuid = :UUID and b.isDeleted=:isDeleted",MerchantCouponEntity.class)
					.setParameter("UUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchantCoupon: merchantCouponUuid=" + uuid);
		}
		return merchantCouponEntity;
	}

	@Override
	public MerchantCouponEntity getMerchantCouponBy2Uuid(String merchantUuid,
			String couponUuid) throws DAOException {
		MerchantCouponEntity merchantCouponEntity = null;
		try {
			merchantCouponEntity = (MerchantCouponEntity) em
					.createQuery(
							"select b from MerchantCouponEntity b where b.merchantEntity.merchantUuid = :MERCHANTUUID and b.couponEntity.couponUuid = :COUPONUUID and b.isDeleted=:isDeleted",MerchantCouponEntity.class)
					.setParameter("MERCHANTUUID", merchantUuid)
					.setParameter("COUPONUUID", couponUuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchantCoupon: merchantUuid、couponUuid" + merchantUuid+"  "+couponUuid);
		}
		return merchantCouponEntity;
	}
	
}
