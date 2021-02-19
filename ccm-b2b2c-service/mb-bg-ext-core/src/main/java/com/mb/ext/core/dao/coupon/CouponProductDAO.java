package com.mb.ext.core.dao.coupon;

import java.util.List;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.coupon.CouponEntity;
import com.mb.ext.core.entity.coupon.CouponProductEntity;
import com.mb.framework.exception.DAOException;



public interface CouponProductDAO
{
	void createCouponProduct(CouponProductEntity couponProductEntity) throws DAOException;
	
	void updateCouponProduct(CouponProductEntity couponProductEntity) throws DAOException;
	
	void deleteCouponProduct(CouponProductEntity couponProductEntity) throws DAOException;
	
	List<CouponProductEntity> getCouponByUser(UserEntity userEntity) throws DAOException;
	
	List<CouponProductEntity> getCouponProductByCoupon(CouponEntity couponEntity) throws DAOException;
	
	CouponProductEntity getCouponProductByUuid(String uuid) throws DAOException;
	
}
