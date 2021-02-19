package com.mb.ext.core.dao.impl.coupon;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.coupon.CouponOrderDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.coupon.CouponEntity;
import com.mb.ext.core.entity.coupon.CouponOrderEntity;
import com.mb.ext.core.service.spec.CouponOrderSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("couponOrderDAO")
@Qualifier("couponOrderDAO")
public class CouponOrderDAOImpl extends AbstractBaseDAO<CouponOrderEntity> implements CouponOrderDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public CouponOrderDAOImpl()
	{
		super();
		this.setEntityClass(CouponOrderEntity.class);
	}
	
	@Override
	public void createCouponOrder(CouponOrderEntity couponOrderEntity)
			throws DAOException {
		this.save(couponOrderEntity);
	}

	@Override
	public void updateCouponOrder(CouponOrderEntity couponOrderEntity)
			throws DAOException {
		this.update(couponOrderEntity);
	}

	@Override
	public void deleteCouponOrder(CouponOrderEntity couponOrderEntity)
			throws DAOException {
		this.delete(couponOrderEntity);
	}

	@Override
	public List<CouponOrderEntity> getAllCouponOrders() throws DAOException {
		List<CouponOrderEntity> couponOrderEntityList = new ArrayList<CouponOrderEntity>();
		try {
			couponOrderEntityList =  em
					.createQuery(
							"select b from CouponOrderEntity b where b.isDeleted=:isDeleted",
							CouponOrderEntity.class)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for couponOrder: ");
		}
		return couponOrderEntityList;
	}

	@Override
	public List<CouponOrderEntity> searchCouponOrder(CouponOrderSearchDTO searchDTO)
			throws DAOException {
		List<CouponOrderEntity> couponOrderEntityList = new ArrayList<CouponOrderEntity>();
		String query = "select b from CouponOrderEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = searchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if(CouponOrderSearchDTO.KEY_TYPE.equals(key)) {
				query = query + " and b.type like :TYPE";
			} else if (CouponOrderSearchDTO.KEY_USERUUID.equals(key)) {
				query = query + " and b.userEntity.userUuid = :USERUUID";
			} else if (CouponOrderSearchDTO.KEY_PHONE.equals(key)) {
				query = query + " and b.userEntity.personalPhone like :PHONE";
			} else if (CouponOrderSearchDTO.KEY_DATE.equals(key)) {
				query = query
						+ " and date(b.couponOrderTime) >= :ORDERDATESTART and date(b.couponOrderTime) <= :ORDERDATEEND";
			} 
		}
		if(!StringUtils.isEmpty(searchDTO.getSortBy())) {
			query = query + " order by b."+searchDTO.getSortBy();
		}else {
			query = query + " order by b.createDate desc";
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, CouponOrderEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (CouponOrderSearchDTO.KEY_TYPE.equals(key)) {
					typedQuery.setParameter("TYPE",
							"%"+searchDTO.getType()+"%");
				} else if (CouponOrderSearchDTO.KEY_USERUUID.equals(key)) {
					typedQuery.setParameter("USERUUID",
							searchDTO.getUserUuid());
				} else if (CouponOrderSearchDTO.KEY_PHONE.equals(key)) {
					typedQuery.setParameter("PHONE",
							"%"+searchDTO.getPhone()+"%");
				} else if (CouponOrderSearchDTO.KEY_DATE.equals(key)) {
					typedQuery.setParameter("ORDERDATESTART",
							searchDTO.getOrderDateStart());
					typedQuery.setParameter("ORDERDATEEND",
							searchDTO.getOrderDateEnd());
				} 
			}
			couponOrderEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(searchDTO.getStartIndex()).setMaxResults(searchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for couponOrder");
		}
		return couponOrderEntityList;
	}
	
	@Override
	public int getCouponOrderTotal(CouponOrderSearchDTO searchDTO)
			throws DAOException {
		Long total = null;
		String query = "select count(b) from CouponOrderEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = searchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if(CouponOrderSearchDTO.KEY_TYPE.equals(key)) {
				query = query + " and b.type like :TYPE";
			} else if (CouponOrderSearchDTO.KEY_PHONE.equals(key)) {
				query = query + " and b.userEntity.personalPhone like :PHONE";
			} else if (CouponOrderSearchDTO.KEY_USERUUID.equals(key)) {
				query = query + " and b.userEntity.userUuid = :USERUUID";
			} else if (CouponOrderSearchDTO.KEY_DATE.equals(key)) {
				query = query
						+ " and date(b.couponOrderTime) >= :ORDERDATESTART and date(b.couponOrderTime) <= :ORDERDATEEND";
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
				if (CouponOrderSearchDTO.KEY_TYPE.equals(key)) {
					typedQuery.setParameter("TYPE",
							"%"+searchDTO.getType()+"%");
				} else if (CouponOrderSearchDTO.KEY_PHONE.equals(key)) {
					typedQuery.setParameter("PHONE",
							"%"+searchDTO.getPhone()+"%");
				} else if (CouponOrderSearchDTO.KEY_USERUUID.equals(key)) {
					typedQuery.setParameter("USERUUID",
							searchDTO.getUserUuid());
				} else if (CouponOrderSearchDTO.KEY_DATE.equals(key)) {
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
			logger.info("No record found for couponOrder");
		}
		return total == null?0:total.intValue();
	}
	
	@Override
	public int getCouponOrderTotalPoint(CouponOrderSearchDTO searchDTO)
			throws DAOException {
		Long total = null;
		String query = "select sum(b.actualPoint) from CouponOrderEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = searchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if(CouponOrderSearchDTO.KEY_TYPE.equals(key)) {
				query = query + " and b.type like :TYPE";
			} else if (CouponOrderSearchDTO.KEY_PHONE.equals(key)) {
				query = query + " and b.userEntity.personalPhone like :PHONE";
			} else if (CouponOrderSearchDTO.KEY_USERUUID.equals(key)) {
				query = query + " and b.userEntity.userUuid = :USERUUID";
			} else if (CouponOrderSearchDTO.KEY_DATE.equals(key)) {
				query = query
						+ " and date(b.couponOrderTime) >= :ORDERDATESTART and date(b.couponOrderTime) <= :ORDERDATEEND";
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
				if (CouponOrderSearchDTO.KEY_TYPE.equals(key)) {
					typedQuery.setParameter("TYPE",
							"%"+searchDTO.getType()+"%");
				} else if (CouponOrderSearchDTO.KEY_PHONE.equals(key)) {
					typedQuery.setParameter("PHONE",
							"%"+searchDTO.getPhone()+"%");
				} else if (CouponOrderSearchDTO.KEY_USERUUID.equals(key)) {
					typedQuery.setParameter("USERUUID",
							searchDTO.getUserUuid());
				} else if (CouponOrderSearchDTO.KEY_DATE.equals(key)) {
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
			logger.info("No record found for couponOrder");
		}
		return total == null?0:total.intValue();
	}

	@Override
	public List<CouponOrderEntity> getCouponOrderByUser(UserEntity userEntity)
			throws DAOException {
		List<CouponOrderEntity> couponOrderEntityList = new ArrayList<CouponOrderEntity>();
		try {
			couponOrderEntityList =  em
					.createQuery(
							"select b from CouponOrderEntity b where b.userEntity.userUuid = :USERUUID and b.isDeleted=:isDeleted",
							CouponOrderEntity.class)
					.setParameter("USERUUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for couponOrder: userUuid=" + userEntity.getUserUuid());
		}
		return couponOrderEntityList;
	}

	@Override
	public List<CouponOrderEntity> getCouponOrderByCoupon(
			CouponEntity couponEntity) throws DAOException {
		List<CouponOrderEntity> couponOrderEntityList = new ArrayList<CouponOrderEntity>();
		try{
			couponOrderEntityList = em.createQuery("select b from CouponOrderEntity b where b.couponEntity.couponUuid = :COUPONUUID and b.isDeleted=:isDeleted", CouponOrderEntity.class)
				.setParameter("COUPONUUID", couponEntity.getCouponUuid())
				.setParameter("isDeleted", Boolean.FALSE).getResultList();
		}catch(NoResultException e){
			logger.info("No record found for couponOrder: couponUuid=" + couponEntity.getCouponUuid());
		}
		return couponOrderEntityList;
	}

	@Override
	public CouponOrderEntity getCouponOrderByUuid(String uuid)
			throws DAOException {
		CouponOrderEntity couponOrderEntity = new CouponOrderEntity();
		try{
			couponOrderEntity = (CouponOrderEntity) em.createQuery("select b from CouponOrderEntity b where b.couponOrderUuid = :COUPONORDERUUID and b.isDeleted=:isDeleted",CouponOrderEntity.class)
				.setParameter("COUPONORDERUUID", uuid)
				.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		}catch(NoResultException e){
			logger.info("No record found for couponOrder: couponOrderUuid=" + uuid);
		}
		return couponOrderEntity;
	}

	@Override
	public int getCouponOrderPointByUser(UserEntity userEntity)
			throws DAOException {
		Long total = null;
		try {
			total = em.createQuery("select sum(b.couponPoint) from CouponOrderEntity b where b.userEntity.userUuid = :USERUUID and b.isDeleted = :isDeleted group by b.userEntity.userUuid",Long.class)
					.setParameter("USERUUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for couponOrder : ");
		}
		return total==null?0:total.intValue();
	}
}
