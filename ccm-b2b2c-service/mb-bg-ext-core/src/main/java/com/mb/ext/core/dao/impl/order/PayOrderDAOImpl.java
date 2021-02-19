package com.mb.ext.core.dao.impl.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.order.PayOrderDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.order.PayOrderEntity;
import com.mb.ext.core.service.spec.PayOrderSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("payOrderDAO")
@Qualifier("payOrderDAO")
public class PayOrderDAOImpl extends AbstractBaseDAO<PayOrderEntity> implements
		PayOrderDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public PayOrderDAOImpl() {
		super();
		this.setEntityClass(PayOrderEntity.class);
	}


	@Override
	public void addOrder(PayOrderEntity orderEntity) throws DAOException {
		
		save(orderEntity);
		
	}


	@Override
	public void deleteOrder(PayOrderEntity orderEntity) throws DAOException {
		
		deletePhysical(orderEntity);
		
	}

	@Override
	public void updateOrder(PayOrderEntity orderEntity) throws DAOException {
		
		update(orderEntity);
		
	}

	@Override
	public PayOrderEntity getOrderByUuid(String uuid)
			throws DAOException {
		PayOrderEntity orderEntity = null;
		try {
			orderEntity = (PayOrderEntity)em.createQuery("select b from PayOrderEntity b where  b.payOrderUuid = :UUID and b.isDeleted=:isDeleted",PayOrderEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : "+uuid);
		}
		return orderEntity;
	}
	
	@Override
	public PayOrderEntity getOrderByOrderNo(String orderNo)
			throws DAOException {
		PayOrderEntity orderEntity = null;
		try {
			orderEntity = (PayOrderEntity)em.createQuery("select b from PayOrderEntity b where  b.orderNo = :ORDERNO and b.isDeleted=:isDeleted",PayOrderEntity.class).setParameter("ORDERNO", orderNo).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : "+orderNo);
		}
		return orderEntity;
	}
	
	@Override
	public List<PayOrderEntity> getOrdersByUser(UserEntity userEntity)
			throws DAOException {
		List<PayOrderEntity> orderEntityList = new ArrayList<PayOrderEntity>();
		try {
			orderEntityList = em.createQuery("select b from PayOrderEntity b where  b.userEntity.userUuid = :UUID and b.isDeleted=:isDeleted",PayOrderEntity.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for order : "+userEntity.getUserUuid());
		}
		return orderEntityList;
	}

	@Override
	public List<PayOrderEntity> getOrdersByMerchant(MerchantEntity merchantEntity)
			throws DAOException {
		List<PayOrderEntity> orderEntityList = new ArrayList<PayOrderEntity>();
		try {
			orderEntityList = em.createQuery("select b from PayOrderEntity b where  b.merchantEntity.merchantUuid = :UUID and b.isDeleted=:isDeleted",PayOrderEntity.class).setParameter("UUID", merchantEntity.getMerchantUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
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
			userEntityList = em.createQuery("select b.userEntity from PayOrderEntity b where  b.merchantEntity.merchantUuid = :UUID and b.isDeleted=:isDeleted",UserEntity.class).setParameter("UUID", merchantEntity.getMerchantUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for order : "+merchantEntity.getMerchantUuid());
		}
		return userEntityList;
	}


	@Override
	public List<PayOrderEntity> searchPayOrder(PayOrderSearchDTO searchDTO)
			throws DAOException {
		List<PayOrderEntity> payOrderEntityList = new ArrayList<PayOrderEntity>();
		String query = "select b from PayOrderEntity b where b.isDeleted=:isDeleted and b.orderStatus = '1'";
		String[] keyArray = searchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (PayOrderSearchDTO.KEY_PERSONAL_PHONE.equals(key)) {
				query = query + " and b.userEntity.personalPhone like :PHONE";
			} else if (PayOrderSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo like :MOBILENO";
			}  else if (PayOrderSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (PayOrderSearchDTO.KEY_DATE.equals(key)) {
				query = query
						+ " and date(b.orderTime) >= :ORDERDATESTART and date(b.orderTime) <= :ORDERDATEEND";
			} 
		}
		if(!StringUtils.isEmpty(searchDTO.getSortBy())) {
			query = query + " order by b."+searchDTO.getSortBy();
		}else {
			query = query + " order by b.createDate desc";
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, PayOrderEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (PayOrderSearchDTO.KEY_PERSONAL_PHONE.equals(key)) {
					typedQuery.setParameter("PHONE",
							"%" + searchDTO.getPhone() + "%");
				} else if (PayOrderSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" +searchDTO.getMobileNo()+ "%");
				} else if (PayOrderSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%"+searchDTO.getMerchantName()+"%");
				} else if (PayOrderSearchDTO.KEY_DATE.equals(key)) {
					typedQuery.setParameter("ORDERDATESTART",
							searchDTO.getOrderDateStart());
					typedQuery.setParameter("ORDERDATEEND",
							searchDTO.getOrderDateEnd());
				} 
			}
			payOrderEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(searchDTO.getStartIndex()).setMaxResults(searchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for payOrder");
		}
		return payOrderEntityList;
	}


	@Override
	public int getPayOrderTotal(PayOrderSearchDTO searchDTO)
			throws DAOException {
		Long total = null;
		String query = "select count(b) from PayOrderEntity b where b.isDeleted=:isDeleted and b.orderStatus = '1'";
		String[] keyArray = searchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (PayOrderSearchDTO.KEY_PERSONAL_PHONE.equals(key)) {
				query = query + " and b.userEntity.personalPhone like :PHONE";
			} else if (PayOrderSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo like :MOBILENO";
			}  else if (PayOrderSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (PayOrderSearchDTO.KEY_DATE.equals(key)) {
				query = query
						+ " and date(b.orderTime) >= :ORDERDATESTART and date(b.orderTime) <= :ORDERDATEEND";
			} 
		}
		if(!StringUtils.isEmpty(searchDTO.getSortBy())) {
			query = query + " order by b."+searchDTO.getSortBy();
		}else {
			query = query + " order by b.createDate desc";
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (PayOrderSearchDTO.KEY_PERSONAL_PHONE.equals(key)) {
					typedQuery.setParameter("PHONE",
							"%" + searchDTO.getPhone() + "%");
				} else if (PayOrderSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" +searchDTO.getMobileNo()+ "%");
				} else if (PayOrderSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%"+searchDTO.getMerchantName()+"%");
				} else if (PayOrderSearchDTO.KEY_DATE.equals(key)) {
					typedQuery.setParameter("ORDERDATESTART",
							searchDTO.getOrderDateStart());
					typedQuery.setParameter("ORDERDATEEND",
							searchDTO.getOrderDateEnd());
				} 
			}
			total = (Long) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for payOrder");
		}
		return total == null?0:total.intValue();
	}


	@Override
	public BigDecimal getAsinfoPlatformTotal(PayOrderSearchDTO searchDTO)
			throws DAOException {
		BigDecimal total = null;
		String query = "select sum(b.asinfoPlatform) from PayOrderEntity b where b.isDeleted=:isDeleted and b.orderStatus = '1'";
		String[] keyArray = searchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (PayOrderSearchDTO.KEY_PERSONAL_PHONE.equals(key)) {
				query = query + " and b.userEntity.personalPhone like :PHONE";
			} else if (PayOrderSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo like :MOBILENO";
			}  else if (PayOrderSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (PayOrderSearchDTO.KEY_DATE.equals(key)) {
				query = query
						+ " and date(b.orderTime) >= :ORDERDATESTART and date(b.orderTime) <= :ORDERDATEEND";
			} 
		}
		if(!StringUtils.isEmpty(searchDTO.getSortBy())) {
			query = query + " order by b."+searchDTO.getSortBy();
		}else {
			query = query + " order by b.createDate desc";
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, BigDecimal.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (PayOrderSearchDTO.KEY_PERSONAL_PHONE.equals(key)) {
					typedQuery.setParameter("PHONE",
							"%" + searchDTO.getPhone() + "%");
				} else if (PayOrderSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" +searchDTO.getMobileNo()+ "%");
				} else if (PayOrderSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%"+searchDTO.getMerchantName()+"%");
				} else if (PayOrderSearchDTO.KEY_DATE.equals(key)) {
					typedQuery.setParameter("ORDERDATESTART",
							searchDTO.getOrderDateStart());
					typedQuery.setParameter("ORDERDATEEND",
							searchDTO.getOrderDateEnd());
				} 
			}
			total = (BigDecimal) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for payOrder");
		}
		if(total == null) total = new BigDecimal(0);
		return total;
	}


	@Override
	public BigDecimal getAsinfoMerchantTotal(PayOrderSearchDTO searchDTO)
			throws DAOException {
		BigDecimal total = null;
		String query = "select sum(b.asinfoMerchant) from PayOrderEntity b where b.isDeleted=:isDeleted and b.orderStatus = '1'";
		String[] keyArray = searchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (PayOrderSearchDTO.KEY_PERSONAL_PHONE.equals(key)) {
				query = query + " and b.userEntity.personalPhone like :PHONE";
			} else if (PayOrderSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo like :MOBILENO";
			}  else if (PayOrderSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (PayOrderSearchDTO.KEY_DATE.equals(key)) {
				query = query
						+ " and date(b.orderTime) >= :ORDERDATESTART and date(b.orderTime) <= :ORDERDATEEND";
			} 
		}
		if(!StringUtils.isEmpty(searchDTO.getSortBy())) {
			query = query + " order by b."+searchDTO.getSortBy();
		}else {
			query = query + " order by b.createDate desc";
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, BigDecimal.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (PayOrderSearchDTO.KEY_PERSONAL_PHONE.equals(key)) {
					typedQuery.setParameter("PHONE",
							"%" + searchDTO.getPhone() + "%");
				} else if (PayOrderSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" +searchDTO.getMobileNo()+ "%");
				} else if (PayOrderSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%"+searchDTO.getMerchantName()+"%");
				} else if (PayOrderSearchDTO.KEY_DATE.equals(key)) {
					typedQuery.setParameter("ORDERDATESTART",
							searchDTO.getOrderDateStart());
					typedQuery.setParameter("ORDERDATEEND",
							searchDTO.getOrderDateEnd());
				} 
			}
			total = (BigDecimal) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for payOrder");
		}
		if(total == null) total = new BigDecimal(0);
		return total;
	}
}
 