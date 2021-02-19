package com.mb.ext.core.dao.impl.order;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.constant.OrderConstants;
import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.order.OrderDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.order.OrderEntity;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.OrderSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("orderDAO")
@Qualifier("orderDAO")
public class OrderDAOImpl extends AbstractBaseDAO<OrderEntity> implements
		OrderDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public OrderDAOImpl() {
		super();
		this.setEntityClass(OrderEntity.class);
	}


	@Override
	public void addOrder(OrderEntity orderEntity) throws DAOException {
		
		save(orderEntity);
		
	}


	@Override
	public void deleteOrder(OrderEntity orderEntity) throws DAOException {
		
		delete(orderEntity);
		
	}

	@Override
	public void updateOrder(OrderEntity orderEntity) throws DAOException {
		
		update(orderEntity);
		
	}

	@Override
	public OrderEntity getOrderByUuid(String uuid)
			throws DAOException {
		OrderEntity orderEntity = null;
		try {
			orderEntity = (OrderEntity)em.createQuery("select b from OrderEntity b where  b.orderUuid = :UUID and b.isDeleted=:isDeleted",OrderEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : "+uuid);
		}
		return orderEntity;
	}
	
	@Override
	public OrderEntity getOrderByOrderNo(String orderNo)
			throws DAOException {
		OrderEntity orderEntity = null;
		try {
			orderEntity = (OrderEntity)em.createQuery("select b from OrderEntity b where  b.orderNo = :ORDERNO and b.isDeleted=:isDeleted",OrderEntity.class).setParameter("ORDERNO", orderNo).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : "+orderNo);
		}
		return orderEntity;
	}
	
	@Override
	public List<OrderEntity> getOrdersByPOrderNo(String pOrderNo)
			throws DAOException {
		List<OrderEntity> orderEntityList = new ArrayList<OrderEntity>();
		try {
			orderEntityList = em.createQuery("select b from OrderEntity b where  b.pOrderNo = :PORDERNO and b.isDeleted=:isDeleted order by b.orderTime desc",OrderEntity.class).setParameter("PORDERNO", pOrderNo).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for order : "+pOrderNo);
		}
		return orderEntityList;
	}
	
	@Override
	public BigDecimal getActualAmountByPOrderNo(String pOrderNo)
			throws DAOException {
		BigDecimal actualAmount = null;
		try {
			actualAmount = em.createQuery("select sum(b.actualAmount) from OrderEntity b where  b.pOrderNo = :PORDERNO and b.isDeleted=:isDeleted order by b.orderTime desc",BigDecimal.class).setParameter("PORDERNO", pOrderNo).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : "+pOrderNo);
		}
		return actualAmount == null ? BigDecimal.valueOf(0):actualAmount;
	}
	
	@Override
	public List<OrderEntity> getOrdersByUser(UserEntity userEntity)
			throws DAOException {
		List<OrderEntity> orderEntityList = new ArrayList<OrderEntity>();
		try {
			orderEntityList = em.createQuery("select b from OrderEntity b where  b.userEntity.userUuid = :UUID and b.isDeleted=:isDeleted order by b.orderTime desc",OrderEntity.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for order : "+userEntity.getUserUuid());
		}
		return orderEntityList;
	}
	
	@Override
	public List<OrderEntity> getOrdersByUserOrderType(UserEntity userEntity, String orderType)
			throws DAOException {
		List<OrderEntity> orderEntityList = new ArrayList<OrderEntity>();
		try {
			orderEntityList = em.createQuery("select b from OrderEntity b where  b.userEntity.userUuid = :UUID and b.orderType = :ORDERTYPE and b.isDeleted=:isDeleted",OrderEntity.class)
					.setParameter("ORDERTYPE", orderType)
					.setParameter("UUID", userEntity.getUserUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for order : "+userEntity.getUserUuid());
		}
		return orderEntityList;
	}
	
	@Override
	public List<OrderEntity> getOrdersByUserOrderTypeStatus(UserEntity userEntity, String orderType, String status)
			throws DAOException {
		List<OrderEntity> orderEntityList = new ArrayList<OrderEntity>();
		try {
			orderEntityList = em.createQuery("select b from OrderEntity b where  b.userEntity.userUuid = :UUID and b.orderType = :ORDERTYPE and b.orderStatus = :ORDERSTATUS and b.isDeleted=:isDeleted",OrderEntity.class)
					.setParameter("ORDERSTATUS", status)
					.setParameter("ORDERTYPE", orderType)
					.setParameter("UUID", userEntity.getUserUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for order : "+userEntity.getUserUuid());
		}
		return orderEntityList;
	}

	@Override
	public List<OrderEntity> getOrdersByMerchant(MerchantEntity merchantEntity)
			throws DAOException {
		List<OrderEntity> orderEntityList = new ArrayList<OrderEntity>();
		try {
			orderEntityList = em.createQuery("select b from OrderEntity b where  b.userEntity.merchantEntity.merchantUuid = :UUID and b.isDeleted=:isDeleted",OrderEntity.class).setParameter("UUID", merchantEntity.getMerchantUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for order : "+merchantEntity.getMerchantUuid());
		}
		return orderEntityList;
	}
	
	@Override
	public List<UserEntity> getOrderUsersByMerchant(MerchantEntity merchantEntity)
			throws DAOException {
		List<UserEntity> userEntityList = new ArrayList<UserEntity>();
		try {
			userEntityList = em.createQuery("select b.userEntity from OrderEntity b where  b.merchantEntity.merchantUuid = :UUID and b.isDeleted=:isDeleted",UserEntity.class).setParameter("UUID", merchantEntity.getMerchantUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for order : "+merchantEntity.getMerchantUuid());
		}
		return userEntityList;
	}
	
	@Override
	public List<OrderEntity> searchOrders(OrderSearchDTO orderSearchDTO,
			int startIndex, int pageSize) throws DAOException {
		List<OrderEntity> orderEntityList = new ArrayList<OrderEntity>();
		String query = "select b from OrderEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = orderSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (OrderSearchDTO.KEY_ORDERNO.equals(key)) {
				query = query + " and b.orderNo = :ORDERNO";
			} else if (OrderSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (OrderSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid = :USERUUID";
			} else if (OrderSearchDTO.KEY_SUPPLIER.equals(key)) {
				query = query + " and b.supplierEntity.supplierUuid = :SUPPLIERUUID";
			} else if (OrderSearchDTO.KEY_SUPPLIERNAME.equals(key)) {
				query = query + " and b.supplierEntity.supplierName = :SUPPLIERNAME";
			} else if (OrderSearchDTO.KEY_ORDERTYPE.equals(key)) {
				query = query + " and b.orderType = :ORDERTYPE";
			} else if (OrderSearchDTO.KEY_ORDER_TYPE_LIST.equals(key)) {
				query = query + " and b.orderType in :ORDERTYPELIST";
			} else if (OrderSearchDTO.KEY_DELIVERY_TYPE_LIST.equals(key)) {
				query = query + " and b.deliveryType in :DELIVERYTYPELIST";
			} else if (OrderSearchDTO.KEY_ORDERSTATUS.equals(key)) {
				query = query + " and b.orderStatus like :ORDERSTATUS";
			} else if (OrderSearchDTO.KEY_ORDER_STATUS_LIST.equals(key)) {
				query = query + " and b.orderStatus in :ORDERSTATUSLIST";
			} else if (OrderSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.userEntity.name = :NAME";
			} else if (OrderSearchDTO.KEY_PERSONALPHONE.equals(key)) {
				query = query + " and b.userEntity.personalPhone = :PERSONALPHONE";
			} else if (OrderSearchDTO.KEY_IS_AFTER_SALE.equals(key)) {
				query = query + " and b.isAfterSale = :ISAFTERSALE";
			} else if (OrderSearchDTO.KEY_IS_ACCOUNTED.equals(key)) {
				query = query + " and b.isAccounted = :ISACCOUNTED";
			} else if (OrderSearchDTO.KEY_IS_AFTER_SALE_EXPRED.equals(key)) {
				query = query + " and b.afterSaleDeadLineTime < CURRENT_TIMESTAMP";
			} else if (OrderSearchDTO.KEY_ORDERDATE.equals(key)) {
				query = query
						+ " and date(b.orderTime) >= date(:ORDERDATESTART) and date(b.orderTime) <= date(:ORDERDATEEND)";
			} else if (OrderSearchDTO.KEY_DELIVERYDATE.equals(key)) {
				query = query
						+ " and date(b.deliveryTime) >= date(:DELIVERYDATESTART) and date(b.deliveryTime) <= date(:DELIVERYDATEEND)";
			} else if (OrderSearchDTO.KEY_SEC_KILL_PRODUCT.equals(key)) {
				query = query + " and b.orderUuid in (select c.orderEntity.orderUuid from OrderProductEntity c  where c.isDeleted=:isDeleted and c.secKillProductEntity.secKillProductUuid = :SEC_KILL_PRODUCT_UUID)";
			}
		}
		// String sortBy = orderSearchDTO.getSortBy();
		// if("默认排序".equals(sortBy)){
		// query = query + " order by b.createDate desc";
		// }else if("车价最高".equals(sortBy)){
		// query = query + " order by b.priceSale desc";
		// }
		query = query + " order by b.orderTime desc";	//按订单时间降序排列
		try {
			TypedQuery typedQuery = em.createQuery(query, OrderEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (OrderSearchDTO.KEY_ORDERNO.equals(key)) {
					typedQuery.setParameter("ORDERNO",
							orderSearchDTO.getOrderNo());
				} else if (OrderSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							orderSearchDTO.getMerchantUuid());
				} else if (OrderSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							orderSearchDTO.getUserUuid());
				} else if (OrderSearchDTO.KEY_SUPPLIER.equals(key)) {
					typedQuery.setParameter("SUPPLIERUUID",
							orderSearchDTO.getSupplierUuid());
				} else if (OrderSearchDTO.KEY_SUPPLIERNAME.equals(key)) {
					typedQuery.setParameter("SUPPLIERNAME", 
							orderSearchDTO.getSupplierName());
				} else if (OrderSearchDTO.KEY_ORDERTYPE.equals(key)) {
					typedQuery.setParameter("ORDERTYPE",
							orderSearchDTO.getOrderType());
				} else if (OrderSearchDTO.KEY_ORDER_TYPE_LIST.equals(key)) {
					typedQuery.setParameter("ORDERTYPELIST", 
							orderSearchDTO.getOrderTypeList());
				} else if (OrderSearchDTO.KEY_DELIVERY_TYPE_LIST.equals(key)) {
					typedQuery.setParameter("DELIVERYTYPELIST", 
							orderSearchDTO.getDeliveryTypeList());
				} else if (OrderSearchDTO.KEY_ORDERSTATUS.equals(key)) {
					typedQuery.setParameter("ORDERSTATUS", 
							orderSearchDTO.getOrderStatus());
				} else if (OrderSearchDTO.KEY_ORDER_STATUS_LIST.equals(key)) {
					typedQuery.setParameter("ORDERSTATUSLIST", 
							orderSearchDTO.getOrderStatusList());
				} else if (OrderSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME", 
							orderSearchDTO.getName());
				} else if (OrderSearchDTO.KEY_PERSONALPHONE.equals(key)) {
					typedQuery.setParameter("PERSONALPHONE", 
							orderSearchDTO.getPersonalPhone());
				} else if (OrderSearchDTO.KEY_IS_AFTER_SALE.equals(key)) {
					typedQuery.setParameter("ISAFTERSALE", 
							orderSearchDTO.isAfterSale());
				} else if (OrderSearchDTO.KEY_IS_ACCOUNTED.equals(key)) {
					typedQuery.setParameter("ISACCOUNTED", 
							orderSearchDTO.isAccounted());
				} else if (OrderSearchDTO.KEY_ORDERDATE.equals(key)) {
					typedQuery.setParameter("ORDERDATESTART",
							orderSearchDTO.getOrderDateStart());
					typedQuery.setParameter("ORDERDATEEND",
							orderSearchDTO.getOrderDateEnd());
				} else if (OrderSearchDTO.KEY_DELIVERYDATE.equals(key)) {
					typedQuery.setParameter("DELIVERYDATESTART",
							orderSearchDTO.getDeliveryDateStart());
					typedQuery.setParameter("DELIVERYDATEEND",
							orderSearchDTO.getDeliveryDateEnd());
				} else if (OrderSearchDTO.KEY_SEC_KILL_PRODUCT.equals(key)) {
					typedQuery.setParameter("SEC_KILL_PRODUCT_UUID",
							orderSearchDTO.getSecKillProductUuid());
				}
			}
			orderEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(startIndex).setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return orderEntityList;
	}
	
	@Override
	public int searchOrdersTotal(OrderSearchDTO orderSearchDTO) throws DAOException {
		Long total = Long.valueOf(0);
		String query = "select count(b) from OrderEntity b where  b.isDeleted=:isDeleted";
		String[] keyArray = orderSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (OrderSearchDTO.KEY_ORDERNO.equals(key)) {
				query = query + " and b.orderNo = :ORDERNO";
			} else if (OrderSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (OrderSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid = :USERUUID";
			} else if (OrderSearchDTO.KEY_SUPPLIER.equals(key)) {
				query = query + " and b.supplierEntity.supplierUuid = :SUPPLIERUUID";
			} else if (OrderSearchDTO.KEY_SUPPLIERNAME.equals(key)) {
				query = query + " and b.supplierEntity.supplierName = :SUPPLIERNAME";
			} else if (OrderSearchDTO.KEY_ORDERTYPE.equals(key)) {
				query = query + " and b.orderType = :ORDERTYPE";
			} else if (OrderSearchDTO.KEY_ORDER_TYPE_LIST.equals(key)) {
				query = query + " and b.orderType in :ORDERTYPELIST";
			} else if (OrderSearchDTO.KEY_DELIVERY_TYPE_LIST.equals(key)) {
				query = query + " and b.deliveryType in :DELIVERYTYPELIST";
			} else if (OrderSearchDTO.KEY_ORDERSTATUS.equals(key)) {
				query = query + " and b.orderStatus like :ORDERSTATUS";
			} else if (OrderSearchDTO.KEY_ORDER_STATUS_LIST.equals(key)) {
				query = query + " and b.orderStatus in :ORDERSTATUSLIST";
			} else if (OrderSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.userEntity.name = :NAME";
			} else if (OrderSearchDTO.KEY_PERSONALPHONE.equals(key)) {
				query = query + " and b.userEntity.personalPhone = :PERSONALPHONE";
			} else if (OrderSearchDTO.KEY_IS_AFTER_SALE.equals(key)) {
				query = query + " and b.isAfterSale = :ISAFTERSALE";
			} else if (OrderSearchDTO.KEY_IS_ACCOUNTED.equals(key)) {
				query = query + " and b.isAccounted = :ISACCOUNTED";
			} else if (OrderSearchDTO.KEY_IS_AFTER_SALE_EXPRED.equals(key)) {
				query = query + " and b.afterSaleDeadLineTime < CURRENT_TIMESTAMP";
			} else if (OrderSearchDTO.KEY_ORDERDATE.equals(key)) {
				query = query
						+ " and date(b.orderTime) >= date(:ORDERDATESTART) and date(b.orderTime) <= date(:ORDERDATEEND)";
			} else if (OrderSearchDTO.KEY_DELIVERYDATE.equals(key)) {
				query = query
						+ " and date(b.deliveryTime) >= date(:DELIVERYDATESTART) and date(b.deliveryTime) <= date(:DELIVERYDATEEND)";
			} else if (OrderSearchDTO.KEY_SEC_KILL_PRODUCT.equals(key)) {
				query = query + " and b.orderUuid in (select c.orderEntity.orderUuid from OrderProductEntity c  where c.isDeleted=:isDeleted and c.secKillProductEntity.secKillProductUuid = :SEC_KILL_PRODUCT_UUID)";
			}
		}
		// String sortBy = orderSearchDTO.getSortBy();
		// if("默认排序".equals(sortBy)){
		// query = query + " order by b.createDate desc";
		// }else if("车价最高".equals(sortBy)){
		// query = query + " order by b.priceSale desc";
		// }
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (OrderSearchDTO.KEY_ORDERNO.equals(key)) {
					typedQuery.setParameter("ORDERNO",
							orderSearchDTO.getOrderNo());
				} else if (OrderSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							orderSearchDTO.getMerchantUuid());
				} else if (OrderSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							orderSearchDTO.getUserUuid());
				} else if (OrderSearchDTO.KEY_SUPPLIER.equals(key)) {
					typedQuery.setParameter("SUPPLIERUUID",
							orderSearchDTO.getSupplierUuid());
				} else if (OrderSearchDTO.KEY_SUPPLIERNAME.equals(key)) {
					typedQuery.setParameter("SUPPLIERNAME", 
							orderSearchDTO.getSupplierName());
				} else if (OrderSearchDTO.KEY_ORDERTYPE.equals(key)) {
					typedQuery.setParameter("ORDERTYPE",
							orderSearchDTO.getOrderType());
				} else if (OrderSearchDTO.KEY_ORDER_TYPE_LIST.equals(key)) {
					typedQuery.setParameter("ORDERTYPELIST", 
							orderSearchDTO.getOrderTypeList());
				} else if (OrderSearchDTO.KEY_DELIVERY_TYPE_LIST.equals(key)) {
					typedQuery.setParameter("DELIVERYTYPELIST", 
							orderSearchDTO.getDeliveryTypeList());
				} else if (OrderSearchDTO.KEY_ORDERSTATUS.equals(key)) {
					typedQuery.setParameter("ORDERSTATUS", 
							orderSearchDTO.getOrderStatus());
				} else if (OrderSearchDTO.KEY_ORDER_STATUS_LIST.equals(key)) {
					typedQuery.setParameter("ORDERSTATUSLIST", 
							orderSearchDTO.getOrderStatusList());
				} else if (OrderSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME", 
							orderSearchDTO.getName());
				} else if (OrderSearchDTO.KEY_PERSONALPHONE.equals(key)) {
					typedQuery.setParameter("PERSONALPHONE", 
							orderSearchDTO.getPersonalPhone());
				} else if (OrderSearchDTO.KEY_IS_AFTER_SALE.equals(key)) {
					typedQuery.setParameter("ISAFTERSALE", 
							orderSearchDTO.isAfterSale());
				} else if (OrderSearchDTO.KEY_IS_ACCOUNTED.equals(key)) {
					typedQuery.setParameter("ISACCOUNTED", 
							orderSearchDTO.isAccounted());
				} else if (OrderSearchDTO.KEY_ORDERDATE.equals(key)) {
					typedQuery.setParameter("ORDERDATESTART",
							orderSearchDTO.getOrderDateStart());
					typedQuery.setParameter("ORDERDATEEND",
							orderSearchDTO.getOrderDateEnd());
				} else if (OrderSearchDTO.KEY_DELIVERYDATE.equals(key)) {
					typedQuery.setParameter("DELIVERYDATESTART",
							orderSearchDTO.getDeliveryDateStart());
					typedQuery.setParameter("DELIVERYDATEEND",
							orderSearchDTO.getDeliveryDateEnd());
				} else if (OrderSearchDTO.KEY_SEC_KILL_PRODUCT.equals(key)) {
					typedQuery.setParameter("SEC_KILL_PRODUCT_UUID",
							orderSearchDTO.getSecKillProductUuid());
				}
			}
			total = (Long) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total.intValue();
	}
	
	@Override
	public int searchOrdersTotalPoint(OrderSearchDTO orderSearchDTO) throws DAOException {
		Long total = Long.valueOf(0);
		String query = "select sum(b.productPoint) from OrderEntity b where  b.isDeleted=:isDeleted";
		String[] keyArray = orderSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (OrderSearchDTO.KEY_ORDERNO.equals(key)) {
				query = query + " and b.orderNo = :ORDERNO";
			} else if (OrderSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (OrderSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid = :USERUUID";
			} else if (OrderSearchDTO.KEY_SUPPLIER.equals(key)) {
				query = query + " and b.supplierEntity.supplierUuid = :SUPPLIERUUID";
			} else if (OrderSearchDTO.KEY_SUPPLIERNAME.equals(key)) {
				query = query + " and b.supplierEntity.supplierName = :SUPPLIERNAME";
			} else if (OrderSearchDTO.KEY_ORDERTYPE.equals(key)) {
				query = query + " and b.orderType = :ORDERTYPE";
			} else if (OrderSearchDTO.KEY_ORDER_TYPE_LIST.equals(key)) {
				query = query + " and b.orderType in :ORDERTYPELIST";
			} else if (OrderSearchDTO.KEY_ORDERSTATUS.equals(key)) {
				query = query + " and b.orderStatus like :ORDERSTATUS";
			} else if (OrderSearchDTO.KEY_ORDER_STATUS_LIST.equals(key)) {
				query = query + " and b.orderStatus in :ORDERSTATUSLIST";
			} else if (OrderSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.userEntity.name = :NAME";
			} else if (OrderSearchDTO.KEY_IS_AFTER_SALE.equals(key)) {
				query = query + " and b.isAfterSale = :ISAFTERSALE";
			} else if (OrderSearchDTO.KEY_IS_ACCOUNTED.equals(key)) {
				query = query + " and b.isAccounted = :ISACCOUNTED";
			} else if (OrderSearchDTO.KEY_IS_AFTER_SALE_EXPRED.equals(key)) {
				query = query + " and b.afterSaleDeadLineTime < CURRENT_TIMESTAMP";
			} else if (OrderSearchDTO.KEY_PERSONALPHONE.equals(key)) {
				query = query + " and b.userEntity.personalPhone = :PERSONALPHONE";
			} else if (OrderSearchDTO.KEY_ORDERDATE.equals(key)) {
				query = query
						+ " and date(b.orderTime) >= date(:ORDERDATESTART) and date(b.orderTime) <= date(:ORDERDATEEND)";
			} else if (OrderSearchDTO.KEY_DELIVERYDATE.equals(key)) {
				query = query
						+ " and date(b.deliveryTime) >= date(:DELIVERYDATESTART) and date(b.deliveryTime) <= date(:DELIVERYDATEEND)";
			} else if (OrderSearchDTO.KEY_SEC_KILL_PRODUCT.equals(key)) {
				query = query + " and b.orderUuid in (select c.orderEntity.orderUuid from OrderProductEntity c  where c.isDeleted=:isDeleted and c.secKillProductEntity.secKillProductUuid = :SEC_KILL_PRODUCT_UUID)";
			}
		}
		// String sortBy = orderSearchDTO.getSortBy();
		// if("默认排序".equals(sortBy)){
		// query = query + " order by b.createDate desc";
		// }else if("车价最高".equals(sortBy)){
		// query = query + " order by b.priceSale desc";
		// }
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (OrderSearchDTO.KEY_ORDERNO.equals(key)) {
					typedQuery.setParameter("ORDERNO",
							orderSearchDTO.getOrderNo());
				} else if (OrderSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							orderSearchDTO.getMerchantUuid());
				} else if (OrderSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							orderSearchDTO.getUserUuid());
				} else if (OrderSearchDTO.KEY_SUPPLIER.equals(key)) {
					typedQuery.setParameter("SUPPLIERUUID",
							orderSearchDTO.getSupplierUuid());
				} else if (OrderSearchDTO.KEY_SUPPLIERNAME.equals(key)) {
					typedQuery.setParameter("SUPPLIERNAME", 
							orderSearchDTO.getSupplierName());
				} else if (OrderSearchDTO.KEY_ORDERTYPE.equals(key)) {
					typedQuery.setParameter("ORDERTYPE",
							orderSearchDTO.getOrderType());
				} else if (OrderSearchDTO.KEY_ORDER_TYPE_LIST.equals(key)) {
					typedQuery.setParameter("ORDERTYPELIST", 
							orderSearchDTO.getOrderTypeList());
				} else if (OrderSearchDTO.KEY_ORDERSTATUS.equals(key)) {
					typedQuery.setParameter("ORDERSTATUS", 
							orderSearchDTO.getOrderStatus());
				} else if (OrderSearchDTO.KEY_ORDER_STATUS_LIST.equals(key)) {
					typedQuery.setParameter("ORDERSTATUSLIST", 
							orderSearchDTO.getOrderStatusList());
				}  else if (OrderSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME", 
							orderSearchDTO.getName());
				} else if (OrderSearchDTO.KEY_PERSONALPHONE.equals(key)) {
					typedQuery.setParameter("PERSONALPHONE", 
							orderSearchDTO.getPersonalPhone());
				} else if (OrderSearchDTO.KEY_IS_AFTER_SALE.equals(key)) {
					typedQuery.setParameter("ISAFTERSALE", 
							orderSearchDTO.isAfterSale());
				} else if (OrderSearchDTO.KEY_IS_ACCOUNTED.equals(key)) {
					typedQuery.setParameter("ISACCOUNTED", 
							orderSearchDTO.isAccounted());
				} else if (OrderSearchDTO.KEY_ORDERDATE.equals(key)) {
					typedQuery.setParameter("ORDERDATESTART",
							orderSearchDTO.getOrderDateStart());
					typedQuery.setParameter("ORDERDATEEND",
							orderSearchDTO.getOrderDateEnd());
				} else if (OrderSearchDTO.KEY_DELIVERYDATE.equals(key)) {
					typedQuery.setParameter("DELIVERYDATESTART",
							orderSearchDTO.getDeliveryDateStart());
					typedQuery.setParameter("DELIVERYDATEEND",
							orderSearchDTO.getDeliveryDateEnd());
				} else if (OrderSearchDTO.KEY_SEC_KILL_PRODUCT.equals(key)) {
					typedQuery.setParameter("SEC_KILL_PRODUCT_UUID",
							orderSearchDTO.getSecKillProductUuid());
				}
			}
			total = (Long) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
			if(total==null){
				total = Long.valueOf(0);
			}
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total.intValue();
	}
	
	@Override
	public BigDecimal searchOrdersTotalAmount(OrderSearchDTO orderSearchDTO) throws DAOException {
		BigDecimal total = null;
		String query = "select sum(b.productAmount) from OrderEntity b where  b.isDeleted=:isDeleted";
		String[] keyArray = orderSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (OrderSearchDTO.KEY_ORDERNO.equals(key)) {
				query = query + " and b.orderNo = :ORDERNO";
			} else if (OrderSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (OrderSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid = :USERUUID";
			} else if (OrderSearchDTO.KEY_SUPPLIER.equals(key)) {
				query = query + " and b.supplierEntity.supplierUuid = :SUPPLIERUUID";
			} else if (OrderSearchDTO.KEY_SUPPLIERNAME.equals(key)) {
				query = query + " and b.supplierEntity.supplierName = :SUPPLIERNAME";
			} else if (OrderSearchDTO.KEY_ORDERTYPE.equals(key)) {
				query = query + " and b.orderType = :ORDERTYPE";
			} else if (OrderSearchDTO.KEY_ORDER_TYPE_LIST.equals(key)) {
				query = query + " and b.orderType in :ORDERTYPELIST";
			} else if (OrderSearchDTO.KEY_DELIVERY_TYPE_LIST.equals(key)) {
				query = query + " and b.deliveryType in :DELIVERYTYPELIST";
			} else if (OrderSearchDTO.KEY_ORDERSTATUS.equals(key)) {
				query = query + " and b.orderStatus like :ORDERSTATUS";
			} else if (OrderSearchDTO.KEY_ORDER_STATUS_LIST.equals(key)) {
				query = query + " and b.orderStatus in :ORDERSTATUSLIST";
			} else if (OrderSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.userEntity.name = :NAME";
			} else if (OrderSearchDTO.KEY_PERSONALPHONE.equals(key)) {
				query = query + " and b.userEntity.personalPhone = :PERSONALPHONE";
			} else if (OrderSearchDTO.KEY_IS_AFTER_SALE.equals(key)) {
				query = query + " and b.isAfterSale = :ISAFTERSALE";
			} else if (OrderSearchDTO.KEY_IS_ACCOUNTED.equals(key)) {
				query = query + " and b.isAccounted = :ISACCOUNTED";
			} else if (OrderSearchDTO.KEY_IS_AFTER_SALE_EXPRED.equals(key)) {
				query = query + " and b.afterSaleDeadLineTime < CURRENT_TIMESTAMP";
			} else if (OrderSearchDTO.KEY_ORDERDATE.equals(key)) {
				query = query
						+ " and date(b.orderTime) >= date(:ORDERDATESTART) and date(b.orderTime) <= date(:ORDERDATEEND)";
			} else if (OrderSearchDTO.KEY_DELIVERYDATE.equals(key)) {
				query = query
						+ " and date(b.deliveryTime) >= date(:DELIVERYDATESTART) and date(b.deliveryTime) <= date(:DELIVERYDATEEND)";
			} else if (OrderSearchDTO.KEY_SEC_KILL_PRODUCT.equals(key)) {
				query = query + " and b.orderUuid in (select c.orderEntity.orderUuid from OrderProductEntity c  where c.isDeleted=:isDeleted and c.secKillProductEntity.secKillProductUuid = :SEC_KILL_PRODUCT_UUID)";
			}
		}
		// String sortBy = orderSearchDTO.getSortBy();
		// if("默认排序".equals(sortBy)){
		// query = query + " order by b.createDate desc";
		// }else if("车价最高".equals(sortBy)){
		// query = query + " order by b.priceSale desc";
		// }
		try {
			TypedQuery typedQuery = em.createQuery(query, BigDecimal.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (OrderSearchDTO.KEY_ORDERNO.equals(key)) {
					typedQuery.setParameter("ORDERNO",
							orderSearchDTO.getOrderNo());
				} else if (OrderSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							orderSearchDTO.getMerchantUuid());
				} else if (OrderSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							orderSearchDTO.getUserUuid());
				} else if (OrderSearchDTO.KEY_SUPPLIER.equals(key)) {
					typedQuery.setParameter("SUPPLIERUUID",
							orderSearchDTO.getSupplierUuid());
				} else if (OrderSearchDTO.KEY_SUPPLIERNAME.equals(key)) {
					typedQuery.setParameter("SUPPLIERNAME", 
							orderSearchDTO.getSupplierName());
				} else if (OrderSearchDTO.KEY_ORDERTYPE.equals(key)) {
					typedQuery.setParameter("ORDERTYPE",
							orderSearchDTO.getOrderType());
				} else if (OrderSearchDTO.KEY_ORDER_TYPE_LIST.equals(key)) {
					typedQuery.setParameter("ORDERTYPELIST", 
							orderSearchDTO.getOrderTypeList());
				} else if (OrderSearchDTO.KEY_DELIVERY_TYPE_LIST.equals(key)) {
					typedQuery.setParameter("DELIVERYTYPELIST", 
							orderSearchDTO.getDeliveryTypeList());
				} else if (OrderSearchDTO.KEY_ORDERSTATUS.equals(key)) {
					typedQuery.setParameter("ORDERSTATUS", 
							orderSearchDTO.getOrderStatus());
				} else if (OrderSearchDTO.KEY_ORDER_STATUS_LIST.equals(key)) {
					typedQuery.setParameter("ORDERSTATUSLIST", 
							orderSearchDTO.getOrderStatusList());
				} else if (OrderSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME", 
							orderSearchDTO.getName());
				} else if (OrderSearchDTO.KEY_IS_AFTER_SALE.equals(key)) {
					typedQuery.setParameter("ISAFTERSALE", 
							orderSearchDTO.isAfterSale());
				} else if (OrderSearchDTO.KEY_IS_ACCOUNTED.equals(key)) {
					typedQuery.setParameter("ISACCOUNTED", 
							orderSearchDTO.isAccounted());
				} else if (OrderSearchDTO.KEY_PERSONALPHONE.equals(key)) {
					typedQuery.setParameter("PERSONALPHONE", 
							orderSearchDTO.getPersonalPhone());
				} else if (OrderSearchDTO.KEY_ORDERDATE.equals(key)) {
					typedQuery.setParameter("ORDERDATESTART",
							orderSearchDTO.getOrderDateStart());
					typedQuery.setParameter("ORDERDATEEND",
							orderSearchDTO.getOrderDateEnd());
				} else if (OrderSearchDTO.KEY_DELIVERYDATE.equals(key)) {
					typedQuery.setParameter("DELIVERYDATESTART",
							orderSearchDTO.getDeliveryDateStart());
					typedQuery.setParameter("DELIVERYDATEEND",
							orderSearchDTO.getDeliveryDateEnd());
				} else if (OrderSearchDTO.KEY_SEC_KILL_PRODUCT.equals(key)) {
					typedQuery.setParameter("SEC_KILL_PRODUCT_UUID",
							orderSearchDTO.getSecKillProductUuid());
				}
			}
			total = (BigDecimal) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total == null?BigDecimal.valueOf(0):total;
	}


	@Override
	public int getOrderProductUnitTotal(Date startDate, Date endDate) throws DAOException {
		Long total = null;
		try {
			total = (Long)em.createQuery("select sum(b.productUnit) from OrderEntity b where date(b.orderTime)>=date(:STARTDATE) and date(b.orderTime)<=date(:ENDDATE) and b.orderStatus = :ORDERSTATUS and b.isDeleted=:isDeleted",Long.class)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("ORDERSTATUS", OrderConstants.ORDER_STATUS_DELIVERIED)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : ");
		}
		if(total == null)	total = new Long(0);
		return total.intValue();
	}
	
	@Override
	public int getOrderProductUnitTotalByMerchant(MerchantEntity merchantEntity, Date startDate, Date endDate) throws DAOException {
		Long total = null;
		try {
			total = (Long)em.createQuery("select sum(b.productUnit) from OrderEntity b where date(b.orderTime)>=date(:STARTDATE) and date(b.orderTime)<=date(:ENDDATE) and b.userEntity.merchantEntity.merchantUuid = :MERCHANTUUID and b.isDeleted=:isDeleted",Long.class)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid())
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : ");
		}
		if(total == null)	total = new Long(0);
		return total.intValue();
	}
	
	@Override
	public int getOrderProductUnitTotalByUser(Date startDate, Date endDate, UserEntity userEntity) throws DAOException {
		Long total = null;
		try {
			total = (Long)em.createQuery("select sum(b.productUnit) from OrderEntity b where b.userEntity.userUuid = :UUID and date(b.orderTime)>=date(:STARTDATE) and date(b.orderTime)<=date(:ENDDATE) and b.isDeleted=:isDeleted",Long.class)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : ");
		}
		if(total == null)	total = new Long(0);
		return total.intValue();
	}


	@Override
	public int getOrderProductUnitTotalByOrderType(Date startDate, Date endDate, String orderType)
			throws DAOException {
		Long total = null;
		try {
			total = (Long)em.createQuery("select sum(b.productUnit) from OrderEntity b where date(b.orderTime)>=date(:STARTDATE) and date(b.orderTime)<=date(:ENDDATE) and b.orderType = :ORDERTYPE and b.isDeleted=:isDeleted",Long.class)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("ORDERTYPE", orderType)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : ");
		}
		if(total == null)	total = new Long(0);
		return total.intValue();
	}
	
	@Override
	public int getOrderProductUnitTotalByOrderTypeAndMerchant(MerchantEntity merchantEntity, Date startDate, Date endDate, String orderType)
			throws DAOException {
		Long total = null;
		try {
			total = (Long)em.createQuery("select sum(b.productUnit) from OrderEntity b where date(b.orderTime)>=date(:STARTDATE) and date(b.orderTime)<=date(:ENDDATE) and b.orderType = :ORDERTYPE and b.userEntity.merchantEntity.merchantUuid = :MERCHANTUUID and b.isDeleted=:isDeleted",Long.class)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("ORDERTYPE", orderType)
					.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid())
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : ");
		}
		if(total == null)	total = new Long(0);
		return total.intValue();
	}


	@Override
	public BigDecimal getOrderProductAmountTotal(Date startDate, Date endDate) throws DAOException {
		BigDecimal total = null;
		try {
			total = em.createQuery("select sum(b.productAmount) from OrderEntity b where date(b.orderTime)>=date(:STARTDATE) and date(b.orderTime)<=date(:ENDDATE) and b.isDeleted=:isDeleted",BigDecimal.class)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : ");
		}
		if(total == null)	total = new BigDecimal(0);
		return total;
	}
	
	@Override
	public BigDecimal getOrderProductAmountTotalByMerchant(MerchantEntity merchantEntity, Date startDate, Date endDate) throws DAOException {
		BigDecimal total = null;
		try {
			total = em.createQuery("select sum(b.productAmount) from OrderEntity b where date(b.orderTime)>=date(:STARTDATE) and date(b.orderTime)<=date(:ENDDATE) and b.merchantEntity.merchantUuid = :MERCHANTUUID and b.isDeleted=:isDeleted",BigDecimal.class)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid())
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : ");
		}
		if(total == null)	total = new BigDecimal(0);
		return total;
	}
	
	@Override
	public BigDecimal getOrderProductAmountTotalByUser(Date startDate, Date endDate, UserEntity userEntity) throws DAOException {
		BigDecimal total = null;
		try {
			total = em.createQuery("select sum(b.productAmount) from OrderEntity b where b.userEntity.userUuid = :UUID and date(b.orderTime)>=date(:STARTDATE) and date(b.orderTime)<=date(:ENDDATE) and b.isDeleted=:isDeleted",BigDecimal.class)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : ");
		}
		if(total == null)	total = new BigDecimal(0);
		return total;
	}
	
	@Override
	public int getOrderProductPointTotalByUser(Date startDate, Date endDate, UserEntity userEntity) throws DAOException {
		Long total = null;
		try {
			total = em.createQuery("select sum(b.productPoint) from OrderEntity b where b.userEntity.userUuid = :UUID and date(b.orderTime)>=date(:STARTDATE) and date(b.orderTime)<=date(:ENDDATE) and b.isDeleted=:isDeleted",Long.class)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("UUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : ");
		}
		return total==null?0:total.intValue();
	}


	@Override
	public BigDecimal getOrderProductAmountTotalByOrderType(Date startDate, Date endDate, String orderType)
			throws DAOException {
		BigDecimal total = null;
		try {
			total = em.createQuery("select sum(b.productAmount) from OrderEntity b where date(b.orderTime)>=date(:STARTDATE) and date(b.orderTime)<=date(:ENDDATE) and b.orderType = :ORDERTYPE and b.isDeleted=:isDeleted",BigDecimal.class)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("ORDERTYPE", orderType)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : ");
		}
		if(total == null)	total = new BigDecimal(0);
		return total;
	}
	
	@Override
	public BigDecimal getOrderProductAmountTotalByOrderTypeAndMerchant(MerchantEntity merchantEntity, Date startDate, Date endDate, String orderType)
			throws DAOException {
		BigDecimal total = null;
		try {
			total = em.createQuery("select sum(b.productAmount) from OrderEntity b where date(b.orderTime)>=date(:STARTDATE) and date(b.orderTime)<=date(:ENDDATE) and b.orderType = :ORDERTYPE and b.userEntity.merchantEntity.merchantUuid = :MERCHANTUUID and b.isDeleted=:isDeleted",BigDecimal.class)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("ORDERTYPE", orderType)
					.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid())
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : ");
		}
		if(total == null)	total = new BigDecimal(0);
		return total;
	}
	
	@Override
	public List<ChartDTO> getIncrementOrderAmountChart(Date startDate, Date endDate)
			throws DAOException {
		List<ChartDTO> dtoList = new ArrayList<ChartDTO>();
		try {
			List<Object> resultList = new ArrayList<Object>();
			String sqlStr = "select t1.datelist, ifnull(t2.transactionamount,0) from summary_day as t1 left join (select order_time, sum(product_amount) as transactionamount from `order` where date(order_time)>=date(:STARTDATE) and date(order_time)<=date(:ENDDATE) group by date(order_time)) as t2 on date(t1.datelist) = date(t2.order_time) where date(t1.datelist)>=date(:STARTDATE) and date(t1.datelist)<=date(:ENDDATE) order by t1.datelist";
			resultList = em.createNativeQuery(sqlStr)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.getResultList();
			for (int i=0; i<resultList.size();i++){
				Object[] result = (Object[]) resultList.get(i);
				String dateStr = String.valueOf(result[0]);
				BigDecimal transactionAmount = (BigDecimal) result[1];
				ChartDTO chartDTO = new ChartDTO();
				chartDTO.setValueDate(dateStr);
				chartDTO.setValueDecimal(transactionAmount);
				dtoList.add(chartDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found for merchant: ");
		}
		return dtoList;
	}
	
	@Override
	public List<ChartDTO> getIncrementOrderAmountChartByMerchant(MerchantEntity merchantEntity, Date startDate, Date endDate)
			throws DAOException {
		List<ChartDTO> dtoList = new ArrayList<ChartDTO>();
		try {
			List<Object> resultList = new ArrayList<Object>();
			String sqlStr = "select t1.datelist, ifnull(t2.transactionamount,0) from summary_day as t1 left join (select merchant_uuid, order_time, sum(product_amount) as transactionamount from `order` where merchant_uuid = :MERCHANTUUID and date(order_time)>=date(:STARTDATE) and date(order_time)<=date(:ENDDATE) group by date(order_time)) as t2 on date(t1.datelist) = date(t2.order_time) where date(t1.datelist)>=date(:STARTDATE) and date(t1.datelist)<=date(:ENDDATE) order by t1.datelist";
			resultList = em.createNativeQuery(sqlStr)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid())
					.getResultList();
			for (int i=0; i<resultList.size();i++){
				Object[] result = (Object[]) resultList.get(i);
				String dateStr = String.valueOf(result[0]);
				BigDecimal transactionAmount = (BigDecimal) result[1];
				ChartDTO chartDTO = new ChartDTO();
				chartDTO.setValueDate(dateStr);
				chartDTO.setValueDecimal(transactionAmount);
				dtoList.add(chartDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found for merchant: ");
		}
		return dtoList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChartDTO> getIncrementOrderCountChart(Date startDate, Date endDate)
			throws DAOException {
		List<ChartDTO> dtoList = new ArrayList<ChartDTO>();
		try {
			List<Object> resultList = new ArrayList<Object>();
			String sqlStr = "select t1.datelist, ifnull(t2.orderCount,0) from summary_day as t1 left join (select order_time, count(order_uuid) as orderCount from `order` where date(order_time)>=date(:STARTDATE) and date(order_time)<=date(:ENDDATE) group by date(order_time)) as t2 on date(t1.datelist) = date(t2.order_time) where date(t1.datelist)>=date(:STARTDATE) and date(t1.datelist)<=date(:ENDDATE) order by t1.datelist";
			resultList = em.createNativeQuery(sqlStr)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.getResultList();
			for (int i=0; i<resultList.size();i++){
				Object[] result = (Object[]) resultList.get(i);
				String dateStr = String.valueOf(result[0]);
				BigInteger orderCount = (BigInteger) result[1];
				ChartDTO chartDTO = new ChartDTO();
				chartDTO.setValueDate(dateStr);
				chartDTO.setValueInt(orderCount.intValue());
				dtoList.add(chartDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found for merchant: ");
		}
		return dtoList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChartDTO> getIncrementOrderCountChartByMerchant(MerchantEntity merchantEntity, Date startDate, Date endDate)
			throws DAOException {
		List<ChartDTO> dtoList = new ArrayList<ChartDTO>();
		try {
			List<Object> resultList = new ArrayList<Object>();
			String sqlStr = "select t1.datelist, ifnull(t2.orderCount,0) from summary_day as t1 left join (select merchant_uuid, order_time, count(order_uuid) as orderCount from `order` where merchant_uuid = :MERCHANTUUID and date(order_time)>=date(:STARTDATE) and date(order_time)<=date(:ENDDATE) group by date(order_time)) as t2 on date(t1.datelist) = date(t2.order_time) where date(t1.datelist)>=date(:STARTDATE) and date(t1.datelist)<=date(:ENDDATE) order by t1.datelist";
			resultList = em.createNativeQuery(sqlStr)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid())
					.getResultList();
			for (int i=0; i<resultList.size();i++){
				Object[] result = (Object[]) resultList.get(i);
				String dateStr = String.valueOf(result[0]);
				BigInteger orderCount = (BigInteger) result[1];
				ChartDTO chartDTO = new ChartDTO();
				chartDTO.setValueDate(dateStr);
				chartDTO.setValueInt(orderCount.intValue());
				dtoList.add(chartDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found for merchant: ");
		}
		return dtoList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChartDTO> getIncrementOrderUnitChart(Date startDate, Date endDate)
			throws DAOException {
		List<ChartDTO> dtoList = new ArrayList<ChartDTO>();
		try {
			List<Object> resultList = new ArrayList<Object>();
			String sqlStr = "select t1.datelist, ifnull(t2.transactionamount,0) from summary_day as t1 left join (select order_time, sum(product_unit) as transactionamount from `order` where date(order_time)>=date(:STARTDATE) and date(order_time)<=date(:ENDDATE) group by date(order_time)) as t2 on date(t1.datelist) = date(t2.order_time) where date(t1.datelist)>=date(:STARTDATE) and date(t1.datelist)<=date(:ENDDATE) order by t1.datelist";
			resultList = em.createNativeQuery(sqlStr)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.getResultList();
			for (int i=0; i<resultList.size();i++){
				Object[] result = (Object[]) resultList.get(i);
				String dateStr = String.valueOf(result[0]);
				BigDecimal transactionAmount = (BigDecimal) result[1];
				ChartDTO chartDTO = new ChartDTO();
				chartDTO.setValueDate(dateStr);
				chartDTO.setValueInt(transactionAmount.intValue());
				dtoList.add(chartDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found for merchant: ");
		}
		return dtoList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChartDTO> getIncrementOrderPointChart(Date startDate, Date endDate)
			throws DAOException {
		List<ChartDTO> dtoList = new ArrayList<ChartDTO>();
		try {
			List<Object> resultList = new ArrayList<Object>();
			String sqlStr = "select t1.datelist, ifnull(t2.transactionamount,0) from summary_day as t1 left join (select order_time, sum(product_point) as transactionamount from `order` where date(order_time)>=date(:STARTDATE) and date(order_time)<=date(:ENDDATE) group by date(order_time)) as t2 on date(t1.datelist) = date(t2.order_time) where date(t1.datelist)>=date(:STARTDATE) and date(t1.datelist)<=date(:ENDDATE) order by t1.datelist";
			resultList = em.createNativeQuery(sqlStr)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.getResultList();
			for (int i=0; i<resultList.size();i++){
				Object[] result = (Object[]) resultList.get(i);
				String dateStr = String.valueOf(result[0]);
				BigDecimal transactionAmount = (BigDecimal) result[1];
				ChartDTO chartDTO = new ChartDTO();
				chartDTO.setValueDate(dateStr);
				chartDTO.setValueInt(transactionAmount.intValue());
				dtoList.add(chartDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found for merchant: ");
		}
		return dtoList;
	}


	@Override
	public int getOrderActualPoint(UserEntity userEntity) throws DAOException {
		Long total = null;
		try {
			total = em.createQuery("select sum(productPoint) from OrderEntity b where b.userEntity.userUuid = :USERUUID and b.orderStatus not in (:STATUS) and b.isDeleted = :isDeleted group by b.userEntity.userUuid",Long.class)
					.setParameter("USERUUID", userEntity.getUserUuid())
					.setParameter("STATUS", "3")
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : ");
		}
		return total==null?0:total.intValue();
	}
	
	@Override
	public void executeInsertUpdateNativeSQL(String sql) throws DAOException {
		try{
			em.createNativeQuery(sql).executeUpdate();
		}catch(Exception e){
			throw new DAOException(e);
		}
		
	}
}
