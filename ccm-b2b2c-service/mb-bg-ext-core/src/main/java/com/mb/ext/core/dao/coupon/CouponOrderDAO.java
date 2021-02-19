package com.mb.ext.core.dao.coupon;

import java.util.List;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.coupon.CouponEntity;
import com.mb.ext.core.entity.coupon.CouponOrderEntity;
import com.mb.ext.core.service.spec.CouponOrderSearchDTO;
import com.mb.framework.exception.DAOException;



public interface CouponOrderDAO
{
	void createCouponOrder(CouponOrderEntity couponOrderEntity) throws DAOException;
	
	void updateCouponOrder(CouponOrderEntity couponOrderEntity) throws DAOException;
	
	void deleteCouponOrder(CouponOrderEntity couponOrderEntity) throws DAOException;
	
	List<CouponOrderEntity> getAllCouponOrders() throws DAOException;
	
	List<CouponOrderEntity> searchCouponOrder(CouponOrderSearchDTO searchDTO) throws DAOException;
	
	List<CouponOrderEntity> getCouponOrderByUser(UserEntity userEntity) throws DAOException;
	
//	List<UserCouponEntity> getCouponByUserSupplier(UserEntity userEntity, SupplierEntity supplierEntity) throws DAOException;
	
	List<CouponOrderEntity> getCouponOrderByCoupon(CouponEntity couponEntity) throws DAOException;
	
	CouponOrderEntity getCouponOrderByUuid(String uuid) throws DAOException;

	int getCouponOrderTotal(CouponOrderSearchDTO searchDTO)throws DAOException;
	
	int getCouponOrderTotalPoint(CouponOrderSearchDTO searchDTO)throws DAOException;
	
	int getCouponOrderPointByUser(UserEntity userEntity) throws DAOException;
	
}
