package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.GroupDAO;
import com.mb.ext.core.entity.GroupEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("groupDAO")
@Qualifier("groupDAO")
public class GroupDAOImpl extends AbstractBaseDAO<GroupEntity> implements GroupDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public GroupDAOImpl()
	{
		super();
		this.setEntityClass(GroupEntity.class);
	}

	@Override
	public void addGroup(GroupEntity groupEntity) throws DAOException {
		
		this.save(groupEntity);
		
	}

	@Override
	public void updateGroup(GroupEntity groupEntity) throws DAOException {
		
		this.update(groupEntity);
		
	}

	@Override
	public void deleteGroup(GroupEntity groupEntity) throws DAOException {
		
		this.delete(groupEntity);
		
	}

	@Override
	public GroupEntity getGroupByUuid(String uuid) throws DAOException {
		GroupEntity groupEntity = null;
		try {
			groupEntity = (GroupEntity)em.createQuery("select b from GroupEntity b where b.groupUuid = :UUID and b.isDeleted=:isDeleted",GroupEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for group: "+uuid);
		}
		return groupEntity;
	}
	
	@Override
	public GroupEntity getGroupByName(String name) throws DAOException {
		GroupEntity groupEntity = null;
		try {
			groupEntity = (GroupEntity)em.createQuery("select b from GroupEntity b where b.groupName = :NAME and b.isDeleted=:isDeleted",GroupEntity.class).setParameter("NAME", name).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for group: "+name);
		}
		return groupEntity;
	}
	
	@Override
	public GroupEntity getGroupForRegister() throws DAOException {
		GroupEntity groupEntity = null;
		try {
			groupEntity = (GroupEntity)em.createQuery("select b from GroupEntity b where b.isRegister = :ISREGISTER and b.isDeleted=:isDeleted",GroupEntity.class).setParameter("ISREGISTER", Boolean.TRUE).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for group");
		}
		return groupEntity;
	}

	@Override
	public List<GroupEntity> getGroups() throws DAOException {
		List<GroupEntity> groupEntityList = new ArrayList<GroupEntity>();;
		try {
			groupEntityList = em.createQuery("select b from GroupEntity b where  b.isDeleted=:isDeleted order by b.sortNumber",GroupEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for group: "+groupEntityList);
		}
		return groupEntityList;
	}
	
	@Override
	public List<GroupEntity> getGroupsByMerchant(MerchantEntity merchantEntity) throws DAOException {
		List<GroupEntity> groupEntityList = new ArrayList<GroupEntity>();;
		try {
			groupEntityList = em.createQuery("select b from GroupEntity b where b.merchantEntity.merchantUuid = :MERCHANTUUID and b.isDeleted=:isDeleted order by b.sortNumber",GroupEntity.class)
					.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid())
					.setParameter("isDeleted", Boolean.FALSE)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for group: "+groupEntityList);
		}
		return groupEntityList;
	}

	@Override
	public List<GroupEntity> getHomeGroups() throws DAOException {
		List<GroupEntity> groupEntityList = new ArrayList<GroupEntity>();;
		try {
			groupEntityList = em.createQuery("select b from GroupEntity b where b.isDisplayedHome=:ISDISPLAYEDHOME and  b.isDeleted=:isDeleted order by b.sortNumber",GroupEntity.class).setParameter("ISDISPLAYEDHOME", Boolean.TRUE).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for group: "+groupEntityList);
		}
		return groupEntityList;
	}

	

}
