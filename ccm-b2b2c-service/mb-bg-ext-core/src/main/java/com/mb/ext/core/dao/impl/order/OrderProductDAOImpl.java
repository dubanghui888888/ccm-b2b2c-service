package com.mb.ext.core.dao.impl.order;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.order.OrderProductDAO;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.order.OrderProductEntity;
import com.mb.ext.core.service.spec.OrderSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.order.OrderProductDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("orderProductDAO")
@Qualifier("orderProductDAO")
public class OrderProductDAOImpl extends AbstractBaseDAO<OrderProductEntity> implements
		OrderProductDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public OrderProductDAOImpl() {
		super();
		this.setEntityClass(OrderProductEntity.class);
	}


	@Override
	public void addOrderProduct(OrderProductEntity orderProductEntity) throws DAOException {
		
		save(orderProductEntity);
		
	}


	@Override
	public void deleteOrderProduct(OrderProductEntity orderProductEntity) throws DAOException {
		
		delete(orderProductEntity);
		
	}

	@Override
	public void updateOrderProduct(OrderProductEntity orderProductEntity) throws DAOException {
		
		update(orderProductEntity);
		
	}

	@Override
	public OrderProductEntity getOrderProductByUuid(String uuid)
			throws DAOException {
		OrderProductEntity orderProductEntity = null;
		try {
			orderProductEntity = (OrderProductEntity)em.createQuery("select b from OrderProductEntity b where  b.orderProductUuid = :UUID and b.isDeleted=:isDeleted",OrderProductEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : "+uuid);
		}
		return orderProductEntity;
	}
	
	@Override
	public List<OrderProductEntity> getOrderProductByOrderNo(String orderNo)
			throws DAOException {
		List<OrderProductEntity> orderProductEntityList = new ArrayList<OrderProductEntity>();
		try {
			orderProductEntityList = em.createQuery("select b from OrderProductEntity b where  b.orderEntity.orderNo = :ORDERNO and b.isDeleted=:isDeleted",OrderProductEntity.class).setParameter("ORDERNO", orderNo).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for order product : "+orderNo);
		}
		return orderProductEntityList;
	}
	@Override
	public List<OrderProductEntity> getOrderProductByOrderUuid(String uuid)
			throws DAOException {
		List<OrderProductEntity> orderProductEntityList = new ArrayList<OrderProductEntity>();
		try {
			orderProductEntityList = em.createQuery("select b from OrderProductEntity b where  b.orderEntity.orderUuid = :UUID and b.isDeleted=:isDeleted",OrderProductEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for order product : "+uuid);
		}
		return orderProductEntityList;
	}


	@Override
	public List<OrderProductDTO> getTopxProductByUnit(Date startDate, Date endDate, int x) throws DAOException {

		List<OrderProductDTO> dtoList = new ArrayList<OrderProductDTO>();
		try {
			List<Object> resultList = new ArrayList<Object>();
			String sqlStr = "SELECT PRODUCT_NAME, SUM(PRODUCT_UNIT) as u FROM order_product where date(date_create)>=date(:STARTDATE) and date(date_create)<=date(:ENDDATE) GROUP BY PRODUCT_NAME ORDER BY u desc limit 0, :X";
			resultList = em.createNativeQuery(sqlStr)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("X", x)
					.getResultList();
			for (int i=0; i<resultList.size();i++){
				Object[] result = (Object[]) resultList.get(i);
				String productName = String.valueOf(result[0]);
				BigDecimal productUnit = (BigDecimal) result[1];
				OrderProductDTO orderProductDTO = new OrderProductDTO();
				orderProductDTO.setProductName(productName);
				orderProductDTO.setProductUnit(productUnit.intValue());
				dtoList.add(orderProductDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return dtoList;
	}
	
	@Override
	public List<OrderProductDTO> getTopxMerchantProductByUnit(MerchantEntity merchantEntity, Date startDate, Date endDate, int x) throws DAOException {

		List<OrderProductDTO> dtoList = new ArrayList<OrderProductDTO>();
		try {
			List<Object> resultList = new ArrayList<Object>();
			String sqlStr = "SELECT PRODUCT_NAME, SUM(op.PRODUCT_UNIT) as u FROM order_product op, `order` o where op.ORDER_UUID = o.ORDER_UUID and date(o.date_create)>=date(:STARTDATE) and date(o.date_create)<=date(:ENDDATE) and o.MERCHANT_UUID = :MERCHANTUUID GROUP BY PRODUCT_NAME ORDER BY u desc limit 0, :X";
			resultList = em.createNativeQuery(sqlStr)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid())
					.setParameter("X", x)
					.getResultList();
			for (int i=0; i<resultList.size();i++){
				Object[] result = (Object[]) resultList.get(i);
				String productName = String.valueOf(result[0]);
				BigDecimal productUnit = (BigDecimal) result[1];
				OrderProductDTO orderProductDTO = new OrderProductDTO();
				orderProductDTO.setProductName(productName);
				orderProductDTO.setProductUnit(productUnit.intValue());
				dtoList.add(orderProductDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return dtoList;
	}
	
	@Override
	public List<MerchantDTO> getTopxMerchantByAmount(OrderSearchDTO searchDTO) throws DAOException {

		List<MerchantDTO> dtoList = new ArrayList<MerchantDTO>();
		try {
			String[] keyArray = searchDTO.getKeyArray();
			
			String orderSql = "SELECT m.merchantName, SUM(o.product_amount) as orderAmount FROM MERCHANT as m, `order` as o WHERE o.order_status in ('1','2','3','5') and o.merchant_uuid = m.merchant_uuid";
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (OrderSearchDTO.KEY_ORDERDATE.equals(key)) {
					orderSql = orderSql
							+ " and date(o.order_time) >= date(:ORDERDATESTART) and date(o.order_time) <= date(:ORDERDATEEND)";
				}else if (OrderSearchDTO.KEY_MERCHANT.equals(key)) {
					orderSql = orderSql
							+ " and o.merchant_uuid = :MERCHANTUUID";
				}
			}
			orderSql = orderSql + " group by m.merchant_uuid";
			
			String returnSql = "SELECT m.merchantName, SUM(oas.after_sale_amount) as returnAmount FROM MERCHANT as m, `order` as o, order_after_sale as oas WHERE o.after_sale_no = oas.sale_no and o.is_after_sale = '1' and o.order_status in ('1','2','3','5') and o.merchant_uuid = m.merchant_uuid";
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (OrderSearchDTO.KEY_ORDERDATE.equals(key)) {
					returnSql = returnSql
							+ " and date(o.order_time) >= date(:ORDERDATESTART) and date(o.order_time) <= date(:ORDERDATEEND)";
				}else if (OrderSearchDTO.KEY_MERCHANT.equals(key)) {
					returnSql = returnSql
							+ " and o.merchant_uuid = :MERCHANTUUID";
				}
			}
			returnSql = returnSql + " group by m.merchant_uuid";
			
			String sqlStr = "select a.merchantName,ifnull(a.orderAmount,0.00), ifnull(b.returnAmount,0.00) from " + "(" + orderSql +") as a left join " + "(" + returnSql + ") as b on a.merchantName = b.merchantName ORDER BY a.orderAmount desc LIMIT :STARTINDEX, :PAGESIZE";
			
			Query query = em.createNativeQuery(sqlStr);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (OrderSearchDTO.KEY_ORDERDATE.equals(key)) {
					query.setParameter("ORDERDATESTART", searchDTO.getOrderDateStart());
					query.setParameter("ORDERDATEEND", searchDTO.getOrderDateEnd());
				}else if (OrderSearchDTO.KEY_MERCHANT.equals(key)) {
					query.setParameter("MERCHANTUUID", searchDTO.getMerchantUuid());
				}
			}
			query.setParameter("STARTINDEX", searchDTO.getStartIndex());
			query.setParameter("PAGESIZE", searchDTO.getPageSize());
			
			List<Object> resultList = new ArrayList<Object>();
			resultList = query.getResultList();
			for (int i=0; i<resultList.size();i++){
				Object[] result = (Object[]) resultList.get(i);
				String merchantName = String.valueOf(result[0]);
				BigDecimal orderAmount = (BigDecimal) result[1];
				BigDecimal orderReturnAmount = (BigDecimal) result[2];
				MerchantDTO merchantDTO = new MerchantDTO();
				merchantDTO.setMerchantName(merchantName);
				merchantDTO.setOrderAmount(orderAmount);
				merchantDTO.setOrderReturnAmount(orderReturnAmount);
				dtoList.add(merchantDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return dtoList;
	}
	
	@Override
	public List<MerchantDTO> getTopxMerchantByUnit(OrderSearchDTO searchDTO) throws DAOException {

		List<MerchantDTO> dtoList = new ArrayList<MerchantDTO>();
		try {
			String[] keyArray = searchDTO.getKeyArray();
			
			String orderSql = "SELECT m.merchantName, COUNT(o.merchant_uuid) as orderUnit FROM MERCHANT as m, `order` as o WHERE o.order_status in ('1','2','3','5') and o.merchant_uuid = m.merchant_uuid";
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (OrderSearchDTO.KEY_ORDERDATE.equals(key)) {
					orderSql = orderSql
							+ " and date(o.order_time) >= date(:ORDERDATESTART) and date(o.order_time) <= date(:ORDERDATEEND)";
				}else if (OrderSearchDTO.KEY_MERCHANT.equals(key)) {
					orderSql = orderSql
							+ " and o.merchant_uuid = :MERCHANTUUID";
				}
			}
			orderSql = orderSql + " group by m.merchant_uuid";
			
			String returnSql = "SELECT m.merchantName, COUNT(o.merchant_uuid) as returnUnit FROM MERCHANT as m, `order` as o WHERE o.is_after_sale = '1' and o.order_status in ('1','2','3','5') and o.merchant_uuid = m.merchant_uuid";
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (OrderSearchDTO.KEY_ORDERDATE.equals(key)) {
					returnSql = returnSql
							+ " and date(o.order_time) >= date(:ORDERDATESTART) and date(o.order_time) <= date(:ORDERDATEEND)";
				}else if (OrderSearchDTO.KEY_MERCHANT.equals(key)) {
					returnSql = returnSql
							+ " and o.merchant_uuid = :MERCHANTUUID";
				}
			}
			returnSql = returnSql + " group by m.merchant_uuid";
			
			String sqlStr = "select a.merchantName,ifnull(a.orderUnit,0), ifnull(b.returnUnit,0) from " + "(" + orderSql +") as a left join " + "(" + returnSql + ") as b on a.merchantName = b.merchantName ORDER BY a.orderUnit desc LIMIT :STARTINDEX, :PAGESIZE";
			
			Query query = em.createNativeQuery(sqlStr);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (OrderSearchDTO.KEY_ORDERDATE.equals(key)) {
					query.setParameter("ORDERDATESTART", searchDTO.getOrderDateStart());
					query.setParameter("ORDERDATEEND", searchDTO.getOrderDateEnd());
				}else if (OrderSearchDTO.KEY_MERCHANT.equals(key)) {
					query.setParameter("MERCHANTUUID", searchDTO.getMerchantUuid());
				}
			}
			query.setParameter("STARTINDEX", searchDTO.getStartIndex());
			query.setParameter("PAGESIZE", searchDTO.getPageSize());
			
			List<Object> resultList = new ArrayList<Object>();
			resultList = query.getResultList();
			for (int i=0; i<resultList.size();i++){
				Object[] result = (Object[]) resultList.get(i);
				String merchantName = String.valueOf(result[0]);
				BigInteger orderUnit = (BigInteger) result[1];
				BigInteger orderReturnUnit = (BigInteger) result[2];
				MerchantDTO merchantDTO = new MerchantDTO();
				merchantDTO.setMerchantName(merchantName);
				merchantDTO.setOrderUnit(orderUnit.intValue());
				merchantDTO.setOrderReturnUnit(orderReturnUnit.intValue());
				dtoList.add(merchantDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return dtoList;
	}

	@Override
	public List<OrderProductDTO> getTopxProductByAmount(Date startDate, Date endDate, int x) throws DAOException {

		List<OrderProductDTO> dtoList = new ArrayList<OrderProductDTO>();
		try {
			List<Object> resultList = new ArrayList<Object>();
			String sqlStr = "SELECT PRODUCT_NAME, SUM(PRODUCT_AMOUNT) as u FROM order_product where date(date_create)>=date(:STARTDATE) and date(date_create)<=date(:ENDDATE) GROUP BY PRODUCT_NAME ORDER BY u desc limit 0, :X";
			resultList = em.createNativeQuery(sqlStr)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("X", x)
					.getResultList();
			for (int i=0; i<resultList.size();i++){
				Object[] result = (Object[]) resultList.get(i);
				String productName = String.valueOf(result[0]);
				BigDecimal productAmount = (BigDecimal) result[1];
				OrderProductDTO orderProductDTO = new OrderProductDTO();
				orderProductDTO.setProductName(productName);
				orderProductDTO.setProductAmount(productAmount);
				dtoList.add(orderProductDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return dtoList;
	}
	
	@Override
	public List<OrderProductDTO> getTopxMerchantProductByAmount(MerchantEntity merchantEntity, Date startDate, Date endDate, int x) throws DAOException {

		List<OrderProductDTO> dtoList = new ArrayList<OrderProductDTO>();
		try {
			List<Object> resultList = new ArrayList<Object>();
			String sqlStr = "SELECT PRODUCT_NAME, SUM(op.PRODUCT_AMOUNT) as u FROM order_product op, `order` o where op.ORDER_UUID = o.ORDER_UUID and date(o.date_create)>=date(:STARTDATE) and date(o.date_create)<=date(:ENDDATE) and o.MERCHANT_UUID = :MERCHANTUUID GROUP BY PRODUCT_NAME ORDER BY u desc limit 0, :X";
			resultList = em.createNativeQuery(sqlStr)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid())
					.setParameter("X", x)
					.getResultList();
			for (int i=0; i<resultList.size();i++){
				Object[] result = (Object[]) resultList.get(i);
				String productName = String.valueOf(result[0]);
				BigDecimal productAmount = (BigDecimal) result[1];
				OrderProductDTO orderProductDTO = new OrderProductDTO();
				orderProductDTO.setProductName(productName);
				orderProductDTO.setProductAmount(productAmount);
				dtoList.add(orderProductDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return dtoList;
	}
}
