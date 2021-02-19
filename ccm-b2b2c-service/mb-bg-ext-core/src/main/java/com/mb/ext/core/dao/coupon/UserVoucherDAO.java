package com.mb.ext.core.dao.coupon;

import java.util.List;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.coupon.UserVoucherEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.order.OrderEntity;
import com.mb.ext.core.service.spec.coupon.VoucherSearchDTO;
import com.mb.framework.exception.DAOException;



public interface UserVoucherDAO
{
	void createUserVoucher(UserVoucherEntity userVoucherEntity) throws DAOException;
	
	void updateUserVoucher(UserVoucherEntity userVoucherEntity) throws DAOException;
	
	void deleteUserVoucher(UserVoucherEntity userVoucherEntity) throws DAOException;
	
	List<UserVoucherEntity> getVoucherByUser(UserEntity userEntity) throws DAOException;
	
	List<UserVoucherEntity> getVoucherByUserMerchant(UserEntity userEntity, MerchantEntity merchantEntity) throws DAOException;
	
	List<UserVoucherEntity> getUserVoucherByOrder(OrderEntity orderEntity) throws DAOException;
	
	UserVoucherEntity getUserVoucherByUuid(String uuid) throws DAOException;
	
	UserVoucherEntity getUserVoucherByCode(String voucherCode) throws DAOException;
	
	List<UserVoucherEntity> searchUserVoucher(VoucherSearchDTO searchDTO ) throws DAOException;
	
	int searchUserVoucherTotal(VoucherSearchDTO searchDTO ) throws DAOException;
}
