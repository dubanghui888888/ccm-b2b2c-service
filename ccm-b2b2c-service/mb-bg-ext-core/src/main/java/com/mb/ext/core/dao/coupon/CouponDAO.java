package com.mb.ext.core.dao.coupon;

import java.util.List;

import com.mb.ext.core.entity.coupon.CouponEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.coupon.CouponSearchDTO;
import com.mb.framework.exception.DAOException;



public interface CouponDAO
{
	void createCoupon(CouponEntity couponEntity) throws DAOException;
	
	void updateCoupon(CouponEntity couponEntity) throws DAOException;
	
	void deleteCoupon(CouponEntity couponEntity) throws DAOException;
	
	List<CouponEntity> getAllCoupons() throws DAOException;

	List<CouponEntity> getActiveCoupons() throws DAOException;
	
	List<CouponEntity> getCouponByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	CouponEntity getCouponByName(String name) throws DAOException;
	
	CouponEntity getCouponByUuid(String couponUuid) throws DAOException;
	
	List<CouponEntity> searchCoupon(CouponSearchDTO searchDTO) throws DAOException;
	
	int searchCouponTotal(CouponSearchDTO searchDTO) throws DAOException;
	
}
