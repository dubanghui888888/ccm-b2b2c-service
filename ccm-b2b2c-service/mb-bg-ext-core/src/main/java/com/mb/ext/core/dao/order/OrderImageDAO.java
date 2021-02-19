package com.mb.ext.core.dao.order;

import com.mb.ext.core.entity.order.OrderImageEntity;
import com.mb.framework.exception.DAOException;



public interface OrderImageDAO
{
	void createOrderImage(OrderImageEntity orderImageEntity) throws DAOException;
	
	void updateOrderImage(OrderImageEntity orderImageEntity) throws DAOException;
	
	void deleteOrderImage(OrderImageEntity orderImageEntity) throws DAOException;
	
	OrderImageEntity getApplicationImageByUuid(String uuid) throws DAOException;
}
