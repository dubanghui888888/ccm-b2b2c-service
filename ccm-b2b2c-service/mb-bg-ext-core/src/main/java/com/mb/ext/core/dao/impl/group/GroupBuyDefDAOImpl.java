package com.mb.ext.core.dao.impl.group;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.group.GroupBuyDefDAO;
import com.mb.ext.core.entity.group.GroupBuyDefEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("groupBuyDefDAO")
@Qualifier("groupBuyDefDAO")
public class GroupBuyDefDAOImpl extends AbstractBaseDAO<GroupBuyDefEntity> implements GroupBuyDefDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public GroupBuyDefDAOImpl() {
		super();
		this.setEntityClass(GroupBuyDefEntity.class);
	}

	@Override
	public void addGroupBuy(GroupBuyDefEntity groupBuyDefEntity) throws DAOException {

		this.save(groupBuyDefEntity);

	}

	@Override
	public void updateGroupBuy(GroupBuyDefEntity groupBuyDefEntity) throws DAOException {

		this.update(groupBuyDefEntity);

	}

	@Override
	public void deleteGroupBuy(GroupBuyDefEntity groupBuyDefEntity) throws DAOException {

		this.delete(groupBuyDefEntity);

	}

	@Override
	public GroupBuyDefEntity getGroupBuyByUuid(String uuid) throws DAOException {
		GroupBuyDefEntity groupBuyDefEntity = null;
		try {
			groupBuyDefEntity = (GroupBuyDefEntity) em.createQuery(
					"select b from GroupBuyDefEntity b where  b.groupBuyDefUuid = :UUID and b.isDeleted=:isDeleted",
					GroupBuyDefEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for def : " + uuid);
		}
		return groupBuyDefEntity;
	}

	@Override
	public List<GroupBuyDefEntity> getActiveGroupBuys() throws DAOException {
		List<GroupBuyDefEntity> groupBuyDefEntityList = new ArrayList<GroupBuyDefEntity>();
		try {
			groupBuyDefEntityList = em.createQuery(
					"select b from GroupBuyDefEntity b where b.status = :STATUS and b.isDeleted=:isDeleted order by b.createDate desc",
					GroupBuyDefEntity.class)
					.setParameter("STATUS", "1")
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for sec kill");
		}
		return groupBuyDefEntityList;
	}
	
	@Override
	public List<GroupBuyDefEntity> getGroupBuysByMerchant(MerchantEntity merchantEntity) throws DAOException {
		List<GroupBuyDefEntity> groupBuyDefEntityList = new ArrayList<GroupBuyDefEntity>();
		try {
			groupBuyDefEntityList = em.createQuery(
					"select b from GroupBuyDefEntity b where b.merchantEntity.merchantUuid = :MERCHANTUUID and b.isDeleted=:isDeleted order by b.createDate desc",
					GroupBuyDefEntity.class)
					.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for sec kill");
		}
		return groupBuyDefEntityList;
	}

	@Override
	public List<GroupBuyDefEntity> getAllGroupBuys() throws DAOException {
		List<GroupBuyDefEntity> groupBuyDefEntityList = new ArrayList<GroupBuyDefEntity>();
		try {
			groupBuyDefEntityList = em.createQuery("select b from GroupBuyDefEntity b where  b.isDeleted=:isDeleted order by b.createDate desc",GroupBuyDefEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for sec kill");
		}
		return groupBuyDefEntityList;
	}

	@Override
	public List<GroupBuyDefEntity> searchGroupBuy(ProductSearchDTO productSearchDTO) throws DAOException {
		List<GroupBuyDefEntity> groupBuyDefEntityList = new ArrayList<GroupBuyDefEntity>();
		String query = "select b from GroupBuyDefEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = productSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (ProductSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			}
		}
		if(!StringUtils.isEmpty(productSearchDTO.getSortBy())) {
			query = query + " order by b."+productSearchDTO.getSortBy()+" "+productSearchDTO.getSort()+"";
		}else {
			query = query + " order by b.createDate desc";
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, GroupBuyDefEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (ProductSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							productSearchDTO.getMerchantUuid());
				}
			}
			groupBuyDefEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(productSearchDTO.getStartIndex()).setMaxResults(productSearchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product");
		}
		return groupBuyDefEntityList;
	}

	@Override
	public int searchGroupBuyTotal(ProductSearchDTO productSearchDTO) throws DAOException {
		Long total = null;
		String query = "select count(b) from GroupBuyDefEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = productSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (ProductSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			}
		}
		if(!StringUtils.isEmpty(productSearchDTO.getSortBy())) {
			query = query + " order by b."+productSearchDTO.getSortBy()+" "+productSearchDTO.getSort()+"";
		}else {
			query = query + " order by b.createDate desc";
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (ProductSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							productSearchDTO.getMerchantUuid());
				}
			}
			total = (Long)typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product");
		}
		return total==null?0:total.intValue();
	}
}
