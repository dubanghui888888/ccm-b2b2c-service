package com.mb.ext.core.dao.impl.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.order.OrderAfterSaleDAO;
import com.mb.ext.core.entity.order.OrderAfterSaleEntity;
import com.mb.ext.core.service.spec.OrderAfterSaleSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("orderAfterSaleDAO")
@Qualifier("orderAfterSaleDAO")
public class OrderAfterSaleDAOImpl extends AbstractBaseDAO<OrderAfterSaleEntity> implements
		OrderAfterSaleDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public OrderAfterSaleDAOImpl() {
		super();
		this.setEntityClass(OrderAfterSaleEntity.class);
	}


	@Override
	public void addOrderAfterSale(OrderAfterSaleEntity orderAfterSaleEntity) throws DAOException {
		
		save(orderAfterSaleEntity);
		
	}


	@Override
	public void deleteOrderAfterSale(OrderAfterSaleEntity orderAfterSaleEntity) throws DAOException {
		
		deletePhysical(orderAfterSaleEntity);
		
	}

	@Override
	public void updateOrderAfterSale(OrderAfterSaleEntity orderAfterSaleEntity) throws DAOException {
		
		update(orderAfterSaleEntity);
		
	}

	@Override
	public OrderAfterSaleEntity getOrderAfterSaleByOrderNo(String orderNo)
			throws DAOException {
		OrderAfterSaleEntity orderAfterSaleEntity = null;
		try {
			orderAfterSaleEntity = em.createQuery("select b from OrderAfterSaleEntity b where  b.orderEntity.orderNo = :ORDERNO and b.isDeleted=:isDeleted",OrderAfterSaleEntity.class).setParameter("ORDERNO", orderNo).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order afterSale : "+orderNo);
		}
		return orderAfterSaleEntity;
	}
	@Override
	public OrderAfterSaleEntity getOrderAfterSaleBySaleNo(String saleNo)
			throws DAOException {
		OrderAfterSaleEntity orderAfterSaleEntity = null;
		try {
			orderAfterSaleEntity = em.createQuery("select b from OrderAfterSaleEntity b where  b.saleNo = :SALENO and b.isDeleted=:isDeleted",OrderAfterSaleEntity.class).setParameter("SALENO", saleNo).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order afterSale : "+saleNo);
		}
		return orderAfterSaleEntity;
	}
	@Override
	public OrderAfterSaleEntity getOrderAfterSaleByOrderUuid(String uuid)
			throws DAOException {
		OrderAfterSaleEntity orderAfterSaleEntity = null;
		try {
			orderAfterSaleEntity = em.createQuery("select b from OrderAfterSaleEntity b where  b.orderEntity.orderUuid = :UUID and b.isDeleted=:isDeleted",OrderAfterSaleEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order afterSale : "+uuid);
		}
		return orderAfterSaleEntity;
	}


	@Override
	public List<OrderAfterSaleEntity> searchOrderAfterSale(OrderAfterSaleSearchDTO searchDTO) throws DAOException {
		List<OrderAfterSaleEntity> orderEntityList = new ArrayList<OrderAfterSaleEntity>();
		String query = "select b from OrderAfterSaleEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = searchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (OrderAfterSaleSearchDTO.KEY_ORDERNO.equals(key)) {
				query = query + " and b.orderEntity.orderNo = :ORDERNO";
			} else if (OrderAfterSaleSearchDTO.KEY_SALENO.equals(key)) {
				query = query + " and b.saleNo = :SALENO";
			} else if (OrderAfterSaleSearchDTO.KEY_SALETYPE.equals(key)) {
				query = query + " and b.afterSaleType = :SALETYPE";
			} else if (OrderAfterSaleSearchDTO.KEY_STATUS.equals(key)) {
				query = query + " and b.status = :STATUS";
			} else if (OrderAfterSaleSearchDTO.KEY_STATUS_LIST.equals(key)) {
				query = query + " and b.status in :STATUSLIST";
			} else if (OrderAfterSaleSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.orderEntity.userEntity.userUuid = :USERUUID";
			} else if (OrderAfterSaleSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (OrderAfterSaleSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.orderEntity.userEntity.name = :NAME";
			} else if (OrderAfterSaleSearchDTO.KEY_PERSONALPHONE.equals(key)) {
				query = query + " and b.orderEntity.userEntity.personalPhone = :PERSONALPHONE";
			} else if (OrderAfterSaleSearchDTO.KEY_APPLICATION_TIME.equals(key)) {
				query = query
						+ " and date(b.timeApplication) >= date(:APPLICATIONDATESTART) and date(b.timeApplication) <= date(:APPLICATIONDATEEND)";
			}
		}
		query = query + " order by b.timeApplication desc";	//按订单时间降序排列
		try {
			TypedQuery typedQuery = em.createQuery(query, OrderAfterSaleEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (OrderAfterSaleSearchDTO.KEY_ORDERNO.equals(key)) {
					typedQuery.setParameter("ORDERNO",
							searchDTO.getOrderNo());
				} else if (OrderAfterSaleSearchDTO.KEY_SALENO.equals(key)) {
					typedQuery.setParameter("SALENO",
							searchDTO.getSaleNo());
				} else if (OrderAfterSaleSearchDTO.KEY_SALETYPE.equals(key)) {
					typedQuery.setParameter("SALETYPE",
							searchDTO.getSaleType());
				} else if (OrderAfterSaleSearchDTO.KEY_STATUS.equals(key)) {
					typedQuery.setParameter("STATUS",
							searchDTO.getStatus());
				} else if (OrderAfterSaleSearchDTO.KEY_STATUS_LIST.equals(key)) {
					typedQuery.setParameter("STATUSLIST", 
							searchDTO.getStatusList());
				} else if (OrderAfterSaleSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							searchDTO.getUserUuid());
				} else if (OrderAfterSaleSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							searchDTO.getMerchantUuid());
				} else if (OrderAfterSaleSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME", 
							searchDTO.getName());
				} else if (OrderAfterSaleSearchDTO.KEY_PERSONALPHONE.equals(key)) {
					typedQuery.setParameter("PERSONALPHONE", 
							searchDTO.getPersonalPhone());
				} else if (OrderAfterSaleSearchDTO.KEY_APPLICATION_TIME.equals(key)) {
					typedQuery.setParameter("APPLICATIONDATESTART",
							searchDTO.getApplicationDateStart());
					typedQuery.setParameter("APPLICATIONDATEEND",
							searchDTO.getApplicationDateEnd());
				}
			}
			orderEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(searchDTO.getStartIndex()).setMaxResults(searchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return orderEntityList;
	}


	@Override
	public int searchOrderAfterSaleTotal(OrderAfterSaleSearchDTO searchDTO) throws DAOException {
		Long total = null;
		String query = "select count(b) from OrderAfterSaleEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = searchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (OrderAfterSaleSearchDTO.KEY_ORDERNO.equals(key)) {
				query = query + " and b.orderEntity.orderNo = :ORDERNO";
			} else if (OrderAfterSaleSearchDTO.KEY_SALENO.equals(key)) {
				query = query + " and b.saleNo = :SALENO";
			} else if (OrderAfterSaleSearchDTO.KEY_SALETYPE.equals(key)) {
				query = query + " and b.afterSaleType = :SALETYPE";
			} else if (OrderAfterSaleSearchDTO.KEY_STATUS.equals(key)) {
				query = query + " and b.status = :STATUS";
			} else if (OrderAfterSaleSearchDTO.KEY_STATUS_LIST.equals(key)) {
				query = query + " and b.status in :STATUSLIST";
			} else if (OrderAfterSaleSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.orderEntity.userEntity.userUuid = :USERUUID";
			} else if (OrderAfterSaleSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (OrderAfterSaleSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.orderEntity.userEntity.name = :NAME";
			} else if (OrderAfterSaleSearchDTO.KEY_PERSONALPHONE.equals(key)) {
				query = query + " and b.orderEntity.userEntity.personalPhone = :PERSONALPHONE";
			} else if (OrderAfterSaleSearchDTO.KEY_APPLICATION_TIME.equals(key)) {
				query = query
						+ " and date(b.timeApplication) >= date(:APPLICATIONDATESTART) and date(b.timeApplication) <= date(:APPLICATIONDATEEND)";
			}
		}
		query = query + " order by b.timeApplication desc";	//按订单时间降序排列
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (OrderAfterSaleSearchDTO.KEY_ORDERNO.equals(key)) {
					typedQuery.setParameter("ORDERNO",
							searchDTO.getOrderNo());
				}else if (OrderAfterSaleSearchDTO.KEY_SALENO.equals(key)) {
					typedQuery.setParameter("SALENO",
							searchDTO.getSaleNo());
				}else if (OrderAfterSaleSearchDTO.KEY_SALETYPE.equals(key)) {
					typedQuery.setParameter("SALETYPE",
							searchDTO.getSaleType());
				} else if (OrderAfterSaleSearchDTO.KEY_STATUS.equals(key)) {
					typedQuery.setParameter("STATUS",
							searchDTO.getStatus());
				} else if (OrderAfterSaleSearchDTO.KEY_STATUS_LIST.equals(key)) {
					typedQuery.setParameter("STATUSLIST", 
							searchDTO.getStatusList());
				} else if (OrderAfterSaleSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							searchDTO.getUserUuid());
				} else if (OrderAfterSaleSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							searchDTO.getMerchantUuid());
				} else if (OrderAfterSaleSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME", 
							searchDTO.getName());
				} else if (OrderAfterSaleSearchDTO.KEY_PERSONALPHONE.equals(key)) {
					typedQuery.setParameter("PERSONALPHONE", 
							searchDTO.getPersonalPhone());
				} else if (OrderAfterSaleSearchDTO.KEY_APPLICATION_TIME.equals(key)) {
					typedQuery.setParameter("APPLICATIONDATESTART",
							searchDTO.getApplicationDateStart());
					typedQuery.setParameter("APPLICATIONDATEEND",
							searchDTO.getApplicationDateEnd());
				}
			}
			total = (Long) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total==null?0:total.intValue();
	}
	@Override
	public BigDecimal searchOrderAfterSaleTotalAmount(OrderAfterSaleSearchDTO searchDTO) throws DAOException {
		BigDecimal total = null;
		String query = "select sum(b.afterSaleAmount) from OrderAfterSaleEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = searchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (OrderAfterSaleSearchDTO.KEY_ORDERNO.equals(key)) {
				query = query + " and b.orderEntity.orderNo = :ORDERNO";
			} else if (OrderAfterSaleSearchDTO.KEY_ORDERTYPE.equals(key)) {
				query = query + " and b.orderEntity.orderType = :ORDERTYPE";
			} else if (OrderAfterSaleSearchDTO.KEY_SALENO.equals(key)) {
				query = query + " and b.saleNo = :SALENO";
			} else if (OrderAfterSaleSearchDTO.KEY_SALETYPE.equals(key)) {
				query = query + " and b.afterSaleType = :SALETYPE";
			} else if (OrderAfterSaleSearchDTO.KEY_STATUS.equals(key)) {
				query = query + " and b.status = :STATUS";
			} else if (OrderAfterSaleSearchDTO.KEY_STATUS_LIST.equals(key)) {
				query = query + " and b.status in :STATUSLIST";
			} else if (OrderAfterSaleSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.orderEntity.userEntity.userUuid = :USERUUID";
			} else if (OrderAfterSaleSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (OrderAfterSaleSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.orderEntity.userEntity.name = :NAME";
			} else if (OrderAfterSaleSearchDTO.KEY_PERSONALPHONE.equals(key)) {
				query = query + " and b.orderEntity.userEntity.personalPhone = :PERSONALPHONE";
			} else if (OrderAfterSaleSearchDTO.KEY_APPLICATION_TIME.equals(key)) {
				query = query
						+ " and date(b.timeApplication) >= date(:APPLICATIONDATESTART) and date(b.timeApplication) <= date(:APPLICATIONDATEEND)";
			}
		}
		query = query + " order by b.timeApplication desc";	//按订单时间降序排列
		try {
			TypedQuery typedQuery = em.createQuery(query, BigDecimal.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (OrderAfterSaleSearchDTO.KEY_ORDERNO.equals(key)) {
					typedQuery.setParameter("ORDERNO",
							searchDTO.getOrderNo());
				}else if (OrderAfterSaleSearchDTO.KEY_ORDERTYPE.equals(key)) {
					typedQuery.setParameter("ORDERTYPE",
							searchDTO.getOrderType());
				}else if (OrderAfterSaleSearchDTO.KEY_SALENO.equals(key)) {
					typedQuery.setParameter("SALENO",
							searchDTO.getSaleNo());
				}else if (OrderAfterSaleSearchDTO.KEY_SALETYPE.equals(key)) {
					typedQuery.setParameter("SALETYPE",
							searchDTO.getSaleType());
				} else if (OrderAfterSaleSearchDTO.KEY_STATUS.equals(key)) {
					typedQuery.setParameter("STATUS",
							searchDTO.getStatus());
				} else if (OrderAfterSaleSearchDTO.KEY_STATUS_LIST.equals(key)) {
					typedQuery.setParameter("STATUSLIST", 
							searchDTO.getStatusList());
				} else if (OrderAfterSaleSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							searchDTO.getUserUuid());
				} else if (OrderAfterSaleSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							searchDTO.getMerchantUuid());
				} else if (OrderAfterSaleSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME", 
							searchDTO.getName());
				} else if (OrderAfterSaleSearchDTO.KEY_PERSONALPHONE.equals(key)) {
					typedQuery.setParameter("PERSONALPHONE", 
							searchDTO.getPersonalPhone());
				} else if (OrderAfterSaleSearchDTO.KEY_APPLICATION_TIME.equals(key)) {
					typedQuery.setParameter("APPLICATIONDATESTART",
							searchDTO.getApplicationDateStart());
					typedQuery.setParameter("APPLICATIONDATEEND",
							searchDTO.getApplicationDateEnd());
				}
			}
			total = (BigDecimal) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total==null?BigDecimal.valueOf(0):total;
	}
}
