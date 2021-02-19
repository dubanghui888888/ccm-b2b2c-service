package com.mb.ext.core.dao.order;

import java.math.BigDecimal;
import java.util.List;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.order.PayOrderEntity;
import com.mb.ext.core.service.spec.PayOrderSearchDTO;
import com.mb.framework.exception.DAOException;



public interface PayOrderDAO
{
	
	void addOrder(PayOrderEntity orderEntity) throws DAOException;
	
	void updateOrder(PayOrderEntity orderEntity) throws DAOException;
	
	void deleteOrder(PayOrderEntity orderEntity) throws DAOException;
	
	PayOrderEntity getOrderByUuid(String uuid) throws DAOException;
	
	PayOrderEntity getOrderByOrderNo(String orderNo) throws DAOException;
	
	List<PayOrderEntity> getOrdersByUser(UserEntity userEntity) throws DAOException;
	
	List<PayOrderEntity> getOrdersByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	List<UserEntity> getOrderUsersByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	List<PayOrderEntity> searchPayOrder(PayOrderSearchDTO searchDTO)throws DAOException;
	
	int getPayOrderTotal(PayOrderSearchDTO searchDTO)throws DAOException;
	
	BigDecimal getAsinfoPlatformTotal(PayOrderSearchDTO searchDTO)throws DAOException;
	
	BigDecimal getAsinfoMerchantTotal(PayOrderSearchDTO searchDTO)throws DAOException;
}
