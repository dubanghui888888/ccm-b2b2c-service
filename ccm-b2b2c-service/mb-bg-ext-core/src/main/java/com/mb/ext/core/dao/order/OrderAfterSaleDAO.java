package com.mb.ext.core.dao.order;

import java.math.BigDecimal;
import java.util.List;

import com.mb.ext.core.entity.order.OrderAfterSaleEntity;
import com.mb.ext.core.service.spec.OrderAfterSaleSearchDTO;
import com.mb.framework.exception.DAOException;



public interface OrderAfterSaleDAO
{
	
	void addOrderAfterSale(OrderAfterSaleEntity orderAfterSaleEntity) throws DAOException;
	
	void updateOrderAfterSale(OrderAfterSaleEntity orderAfterSaleEntity) throws DAOException;
	
	void deleteOrderAfterSale(OrderAfterSaleEntity orderAfterSaleEntity) throws DAOException;
	
	OrderAfterSaleEntity getOrderAfterSaleByOrderNo(String orderNo) throws DAOException;
	
	OrderAfterSaleEntity getOrderAfterSaleBySaleNo(String saleNo) throws DAOException;
	
	OrderAfterSaleEntity getOrderAfterSaleByOrderUuid(String orderUuid) throws DAOException;
	
	List<OrderAfterSaleEntity> searchOrderAfterSale(OrderAfterSaleSearchDTO searchDTO) throws DAOException;
	
	int searchOrderAfterSaleTotal(OrderAfterSaleSearchDTO searchDTO) throws DAOException;
	
	BigDecimal searchOrderAfterSaleTotalAmount(OrderAfterSaleSearchDTO searchDTO) throws DAOException;
	
}
