package com.mb.ext.core.dao.impl.group;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.group.GroupBuyUserDAO;
import com.mb.ext.core.entity.group.GroupBuyEntity;
import com.mb.ext.core.entity.group.GroupBuyUserEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("groupBuyUserDAO")
@Qualifier("groupBuyUserDAO")
public class GroupBuyUserDAOImpl extends AbstractBaseDAO<GroupBuyUserEntity> implements GroupBuyUserDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public GroupBuyUserDAOImpl() {
		super();
		this.setEntityClass(GroupBuyUserEntity.class);
	}

	@Override
	public void addGroupBuyUser(GroupBuyUserEntity groupBuyUserEntity) throws DAOException {

		this.save(groupBuyUserEntity);

	}

	@Override
	public void updateGroupBuyUser(GroupBuyUserEntity groupBuyUserEntity) throws DAOException {

		this.update(groupBuyUserEntity);

	}

	@Override
	public void deleteGroupBuyUser(GroupBuyUserEntity groupBuyUserEntity) throws DAOException {

		this.delete(groupBuyUserEntity);

	}

	@Override
	public GroupBuyUserEntity getGroupBuyUserByUuid(String uuid) throws DAOException {
		GroupBuyUserEntity groupBuyUserEntity = null;
		try {
			groupBuyUserEntity = (GroupBuyUserEntity) em.createQuery(
					"select b from GroupBuyUserEntity b where  b.groupBuyUserProductUuid = :UUID and b.isDeleted=:isDeleted",
					GroupBuyUserEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product : " + uuid);
		}
		return groupBuyUserEntity;
	}

	@Override
	public List<GroupBuyUserEntity> getUsersBuyGroupBuy(GroupBuyEntity groupBuyEntity) throws DAOException {
		List<GroupBuyUserEntity> groupBuyUserEntityList = new ArrayList<GroupBuyUserEntity>();
		try {
			groupBuyUserEntityList = em.createQuery(
					"select b from GroupBuyUserEntity b where b.groupBuyEntity.groupBuyUuid = :UUID and b.isDeleted=:isDeleted order by b.createDate desc",
					GroupBuyUserEntity.class)
					.setParameter("UUID", groupBuyEntity.getGroupBuyUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for sec kill");
		}
		return groupBuyUserEntityList;
	}
	
	@Override
	public GroupBuyUserEntity getGroupBuyUserByOrder(String orderNo) throws DAOException {
		GroupBuyUserEntity groupBuyUserEntity = null;
		try {
			groupBuyUserEntity = (GroupBuyUserEntity) em.createQuery(
					"select b from GroupBuyUserEntity b where  b.orderEntity.orderNo = :ORDERNO and b.isDeleted=:isDeleted",
					GroupBuyUserEntity.class).setParameter("ORDERNO", orderNo).setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for group buy user : " + orderNo);
		}
		return groupBuyUserEntity;
	}
	
	@Override
	public int getGroupBuyUserCount(GroupBuyEntity groupBuyEntity) throws DAOException {
		Long total = null;
		try {
			total = em.createQuery(
					"select count(b) from GroupBuyUserEntity b where b.groupBuyEntity.groupBuyUuid = :UUID and b.orderEntity.orderStatus in ('1','2','3','5') and b.isDeleted=:isDeleted order by b.createDate desc",
					Long.class)
					.setParameter("UUID", groupBuyEntity.getGroupBuyUuid())
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for sec kill");
		}
		return total==null?0:total.intValue();
	}

}
