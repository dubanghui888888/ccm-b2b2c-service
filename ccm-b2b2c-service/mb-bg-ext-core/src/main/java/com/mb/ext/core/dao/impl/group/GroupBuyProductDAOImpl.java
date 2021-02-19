package com.mb.ext.core.dao.impl.group;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.group.GroupBuyProductDAO;
import com.mb.ext.core.entity.group.GroupBuyProductEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("groupBuyProductDAO")
@Qualifier("groupBuyProductDAO")
public class GroupBuyProductDAOImpl extends AbstractBaseDAO<GroupBuyProductEntity> implements GroupBuyProductDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public GroupBuyProductDAOImpl() {
		super();
		this.setEntityClass(GroupBuyProductEntity.class);
	}

	@Override
	public void addGroupBuy(GroupBuyProductEntity groupBuyProductEntity) throws DAOException {

		this.save(groupBuyProductEntity);

	}

	@Override
	public void updateGroupBuy(GroupBuyProductEntity groupBuyProductEntity) throws DAOException {

		this.update(groupBuyProductEntity);

	}

	@Override
	public void deleteGroupBuy(GroupBuyProductEntity groupBuyProductEntity) throws DAOException {

		this.delete(groupBuyProductEntity);

	}

	@Override
	public GroupBuyProductEntity getGroupBuyByUuid(String uuid) throws DAOException {
		GroupBuyProductEntity groupBuyProductEntity = null;
		try {
			groupBuyProductEntity = (GroupBuyProductEntity) em.createQuery(
					"select b from GroupBuyProductEntity b where  b.groupBuyProductUuid = :UUID and b.isDeleted=:isDeleted",
					GroupBuyProductEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product : " + uuid);
		}
		return groupBuyProductEntity;
	}

	@Override
	public List<GroupBuyProductEntity> getBeingGroupBuyProducts() throws DAOException {
		List<GroupBuyProductEntity> groupBuyProductEntityList = new ArrayList<GroupBuyProductEntity>();
		try {
			groupBuyProductEntityList = em.createQuery(
					"select b from GroupBuyProductEntity b where b.groupBuyDefEntity.status = :STATUS and b.groupBuyDefEntity.startTime < CURRENT_TIME() and b.groupBuyDefEntity.endTime > CURRENT_TIME() and b.isDeleted=:isDeleted order by b.createDate desc",
					GroupBuyProductEntity.class)
					.setParameter("STATUS", "1")
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for sec kill");
		}
		return groupBuyProductEntityList;
	}
	
	@Override
	public List<GroupBuyProductEntity> getComingGroupBuyProducts() throws DAOException {
		List<GroupBuyProductEntity> groupBuyProductEntityList = new ArrayList<GroupBuyProductEntity>();
		try {
			groupBuyProductEntityList = em.createQuery(
					"select b from GroupBuyProductEntity b where b.groupBuyDefEntity.status = :STATUS and b.groupBuyDefEntity.startTime > CURRENT_TIME() and b.isDeleted=:isDeleted order by b.createDate desc",
					GroupBuyProductEntity.class)
					.setParameter("STATUS", "1")
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for sec kill");
		}
		return groupBuyProductEntityList;
	}
	
	@Override
	public List<GroupBuyProductEntity> getAllGroupBuyProducts() throws DAOException {
		List<GroupBuyProductEntity> groupBuyProductEntityList = new ArrayList<GroupBuyProductEntity>();
		try {
			groupBuyProductEntityList = em.createQuery(
					"select b from GroupBuyProductEntity b where b.groupBuyDefEntity.status = :STATUS and b.isDeleted=:isDeleted order by b.createDate desc",
					GroupBuyProductEntity.class)
					.setParameter("STATUS", "1")
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for sec kill");
		}
		return groupBuyProductEntityList;
	}
	
	@Override
	public List<GroupBuyProductEntity> getGroupBuysByMerchant(MerchantEntity merchantEntity) throws DAOException {
		List<GroupBuyProductEntity> groupBuyProductEntityList = new ArrayList<GroupBuyProductEntity>();
		try {
			groupBuyProductEntityList = em.createQuery("select b from GroupBuyProductEntity b where b.merchantEntity.merchantUuid = :MERCHANTUUID and b.isDeleted=:isDeleted order by b.createDate desc",GroupBuyProductEntity.class)
					.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid())
					.setParameter("isDeleted", Boolean.FALSE)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for sec kill");
		}
		return groupBuyProductEntityList;
	}
	
	@Override
	public void decrStock(String groupBuyProductUuid, int unit) throws DAOException {
		try {
			String nativeSql = "update group_buy_product set stock = stock - "+unit+", sold_unit = sold_unit + "+unit+" where group_buy_product_uuid = '" + groupBuyProductUuid + "'";
			em.createNativeQuery(nativeSql).executeUpdate();
		} catch (Exception e) {
			throw new DAOException("更新团购商品库存失败");
		}
	}
	
	@Override
	public void incrStock(String groupBuyProductUuid, int unit) throws DAOException {
		try {
			String nativeSql = "update group_buy_product set stock = stock + "+unit+", sold_unit = sold_unit - "+unit+" where group_buy_product_uuid = '" + groupBuyProductUuid + "'";
			em.createNativeQuery(nativeSql).executeUpdate();
		} catch (Exception e) {
			throw new DAOException("更新团购商品库存失败");
		}
	}
}
