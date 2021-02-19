package com.mb.ext.core.dao.order;

import java.util.Date;
import java.util.List;

import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.order.OrderProductEntity;
import com.mb.ext.core.service.spec.OrderSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.order.OrderProductDTO;
import com.mb.framework.exception.DAOException;



public interface OrderProductDAO
{
	
	void addOrderProduct(OrderProductEntity orderProductEntity) throws DAOException;
	
	void updateOrderProduct(OrderProductEntity orderProductEntity) throws DAOException;
	
	void deleteOrderProduct(OrderProductEntity orderProductEntity) throws DAOException;
	
	List<OrderProductEntity> getOrderProductByOrderNo(String orderNo) throws DAOException;
	
	List<OrderProductEntity> getOrderProductByOrderUuid(String orderUuid) throws DAOException;
	
	OrderProductEntity getOrderProductByUuid(String orderUuid) throws DAOException;
	//某段时间内商品按销售金额top x
	List<OrderProductDTO> getTopxProductByAmount(Date startDate, Date endDate, int x) throws DAOException;
	//商家某段时间内商品按销售金额top x
	List<OrderProductDTO> getTopxMerchantProductByAmount(MerchantEntity merchantEntity, Date startDate, Date endDate, int x) throws DAOException;
	//某段时间内商品按销售数量top x
	List<OrderProductDTO> getTopxProductByUnit(Date startDate, Date endDate, int x) throws DAOException;
	//商家某段时间内商品按销售数量top x
	List<OrderProductDTO> getTopxMerchantProductByUnit(MerchantEntity merchantEntity, Date startDate, Date endDate, int x) throws DAOException;
	//商家订单数量列表
	List<MerchantDTO> getTopxMerchantByUnit(OrderSearchDTO searchDTO) throws DAOException;
	//商家订单金额列表
	List<MerchantDTO> getTopxMerchantByAmount(OrderSearchDTO searchDTO) throws DAOException;
	
}
