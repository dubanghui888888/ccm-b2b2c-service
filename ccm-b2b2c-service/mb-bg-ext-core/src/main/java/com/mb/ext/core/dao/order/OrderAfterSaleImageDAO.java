package com.mb.ext.core.dao.order;

import com.mb.ext.core.entity.order.OrderAfterSaleImageEntity;
import com.mb.framework.exception.DAOException;



public interface OrderAfterSaleImageDAO
{
	void createOrderAfterSaleImage(OrderAfterSaleImageEntity orderAfterSaleImageEntity) throws DAOException;
	
	void updateOrderAfterSaleImage(OrderAfterSaleImageEntity orderAfterSaleImageEntity) throws DAOException;
	
	void deleteOrderAfterSaleImage(OrderAfterSaleImageEntity orderAfterSaleImageEntity) throws DAOException;
	
	OrderAfterSaleImageEntity getApplicationImageByUuid(String uuid) throws DAOException;
}
