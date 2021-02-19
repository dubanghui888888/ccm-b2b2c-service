package com.mb.ext.core.dao.coupon;

import java.util.List;

import com.mb.ext.core.entity.coupon.CouponEntity;
import com.mb.ext.core.entity.coupon.MerchantCouponEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.coupon.CouponSearchDTO;
import com.mb.framework.exception.DAOException;



public interface MerchantCouponDAO
{
	void createMerchantCoupon(MerchantCouponEntity merchantCouponEntity) throws DAOException;
	
	void updateMerchantCoupon(MerchantCouponEntity merchantCouponEntity) throws DAOException;
	
	void deleteMerchantCoupon(MerchantCouponEntity merchantCouponEntity) throws DAOException;
	
	List<MerchantCouponEntity> searchMerchantCouponEntity(CouponSearchDTO searchDTO) throws DAOException;
	
	List<MerchantCouponEntity> getMerchantCouponByCoupon(CouponEntity couponEntity) throws DAOException;
	
	List<MerchantCouponEntity> getMerchantCouponByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	MerchantCouponEntity getMerchantCouponByUuid(String uuid) throws DAOException;
	
	MerchantCouponEntity getMerchantCouponBy2Uuid(String merchantUuid,String couponUuid)throws DAOException;
	
}
