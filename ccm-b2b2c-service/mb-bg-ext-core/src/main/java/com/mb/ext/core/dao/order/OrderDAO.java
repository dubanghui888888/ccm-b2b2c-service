package com.mb.ext.core.dao.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.order.OrderEntity;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.OrderSearchDTO;
import com.mb.framework.exception.DAOException;



public interface OrderDAO
{
	
	void addOrder(OrderEntity orderEntity) throws DAOException;
	
	void updateOrder(OrderEntity orderEntity) throws DAOException;
	
	void deleteOrder(OrderEntity orderEntity) throws DAOException;
	
	OrderEntity getOrderByUuid(String uuid) throws DAOException;
	
	OrderEntity getOrderByOrderNo(String orderNo) throws DAOException;
	
	List<OrderEntity> getOrdersByUser(UserEntity userEntity) throws DAOException;
	
	List<OrderEntity> getOrdersByPOrderNo(String pOrderNo) throws DAOException;
	
	BigDecimal getActualAmountByPOrderNo(String pOrderNo) throws DAOException;
	
	List<OrderEntity> getOrdersByUserOrderType(UserEntity userEntity, String orderType) throws DAOException;
	
	List<OrderEntity> getOrdersByUserOrderTypeStatus(UserEntity userEntity, String orderType, String orderStatus) throws DAOException;
	
	List<OrderEntity> getOrdersByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	List<UserEntity> getOrderUsersByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	List<OrderEntity> searchOrders(OrderSearchDTO orderSearchDTO, int startIndex, int pageSize) throws DAOException;
	
	int searchOrdersTotal(OrderSearchDTO orderSearchDTO) throws DAOException;
	
	int searchOrdersTotalPoint(OrderSearchDTO orderSearchDTO) throws DAOException;
	
	BigDecimal searchOrdersTotalAmount(OrderSearchDTO orderSearchDTO) throws DAOException;
	
	int getOrderProductUnitTotal(Date startDate, Date endDate) throws DAOException;
	
	int getOrderProductUnitTotalByMerchant(MerchantEntity merchantEntity, Date startDate, Date endDate) throws DAOException;
	
	int getOrderProductUnitTotalByUser(Date startDate, Date endDate, UserEntity userEntity) throws DAOException;
	
	int getOrderProductUnitTotalByOrderType(Date startDate, Date endDate, String orderType) throws DAOException;
	
	int getOrderProductUnitTotalByOrderTypeAndMerchant(MerchantEntity merchantEntity, Date startDate, Date endDate, String orderType) throws DAOException;
	
	BigDecimal getOrderProductAmountTotal(Date startDate, Date endDate) throws DAOException;
	
	BigDecimal getOrderProductAmountTotalByMerchant(MerchantEntity merchantEntity, Date startDate, Date endDate) throws DAOException;
	
	BigDecimal getOrderProductAmountTotalByUser(Date startDate, Date endDate, UserEntity userEntity) throws DAOException;
	
	int getOrderProductPointTotalByUser(Date startDate, Date endDate, UserEntity userEntity) throws DAOException;
	
	BigDecimal getOrderProductAmountTotalByOrderType(Date startDate, Date endDate, String orderType) throws DAOException;
	
	BigDecimal getOrderProductAmountTotalByOrderTypeAndMerchant(MerchantEntity merchantEntity, Date startDate, Date endDate, String orderType) throws DAOException;
	
	List<ChartDTO> getIncrementOrderAmountChart(Date startDate, Date endDate) throws DAOException;
	
	List<ChartDTO> getIncrementOrderAmountChartByMerchant(MerchantEntity merchantEntity, Date startDate, Date endDate) throws DAOException;
	
	List<ChartDTO> getIncrementOrderCountChart(Date startDate, Date endDate) throws DAOException;
	
	List<ChartDTO> getIncrementOrderCountChartByMerchant(MerchantEntity merchantEntity, Date startDate, Date endDate) throws DAOException;
	
	List<ChartDTO> getIncrementOrderUnitChart(Date startDate, Date endDate) throws DAOException;
	
	List<ChartDTO> getIncrementOrderPointChart(Date startDate, Date endDate) throws DAOException;
	
	int getOrderActualPoint(UserEntity userEntity)throws DAOException;
	
	public void executeInsertUpdateNativeSQL(String sql) throws DAOException;
}
