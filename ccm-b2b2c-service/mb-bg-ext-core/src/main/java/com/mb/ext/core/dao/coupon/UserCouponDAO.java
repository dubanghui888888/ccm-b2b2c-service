package com.mb.ext.core.dao.coupon;

import java.util.List;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.coupon.CouponEntity;
import com.mb.ext.core.entity.coupon.UserCouponEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.coupon.CouponSearchDTO;
import com.mb.framework.exception.DAOException;



public interface UserCouponDAO
{
	void createUserCoupon(UserCouponEntity userCouponEntity) throws DAOException;
	
	void updateUserCoupon(UserCouponEntity userCouponEntity) throws DAOException;
	
	void deleteUserCoupon(UserCouponEntity userCouponEntity) throws DAOException;
	
	List<UserCouponEntity> getCouponByUser(UserEntity userEntity) throws DAOException;
	
	List<UserCouponEntity> getCouponByUserMerchant(UserEntity userEntity, MerchantEntity merchantEntity) throws DAOException;
	
	List<UserCouponEntity> getUserCouponByCoupon(CouponEntity couponEntity) throws DAOException;
	
	List<UserCouponEntity> getUserCouponByUserCoupon(UserEntity userEntity, CouponEntity couponEntity) throws DAOException;
	
	UserCouponEntity getUserCouponByUuid(String uuid) throws DAOException;
	
	List<UserCouponEntity> searchUserCoupon(CouponSearchDTO searchDTO ) throws DAOException;
	
	int searchUserCouponTotal(CouponSearchDTO searchDTO ) throws DAOException;
}
