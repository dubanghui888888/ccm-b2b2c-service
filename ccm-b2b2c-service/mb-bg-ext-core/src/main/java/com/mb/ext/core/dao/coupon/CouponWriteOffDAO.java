package com.mb.ext.core.dao.coupon;

import java.util.List;

import com.mb.ext.core.entity.coupon.CouponWriteOffEntity;
import com.mb.ext.core.entity.coupon.UserCouponEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.CouponWriteOffSearchDTO;
import com.mb.framework.exception.DAOException;



public interface CouponWriteOffDAO
{
	void createCouponWriteOff(CouponWriteOffEntity couponWriteOffEntity) throws DAOException;
	
	void updateCouponWriteOff(CouponWriteOffEntity couponWriteOffEntity) throws DAOException;
	
	void deleteCouponWriteOff(CouponWriteOffEntity couponWriteOffEntity) throws DAOException;
	
//	List<CouponWriteOffEntity> getAllCouponWriteOffs() throws DAOException;
	
	List<CouponWriteOffEntity> searchCouponWriteOff(CouponWriteOffSearchDTO searchDTO) throws DAOException;
	
	List<CouponWriteOffEntity> getCouponWriteOffByUserCoupon(UserCouponEntity userCouponEntity) throws DAOException;
	
//	List<UserCouponEntity> getCouponByUserSupplier(UserEntity userEntity, SupplierEntity supplierEntity) throws DAOException;
	
	List<CouponWriteOffEntity> getCouponWriteOffByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	CouponWriteOffEntity getCouponWriteOffByUuid(String uuid) throws DAOException;

	int getCouponWriteOffTotal(CouponWriteOffSearchDTO searchDTO) throws DAOException;
	
}
