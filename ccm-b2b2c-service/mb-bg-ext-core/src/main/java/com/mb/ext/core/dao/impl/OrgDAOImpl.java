package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.OrgDAO;
import com.mb.ext.core.entity.OrgEntity;
import com.mb.framework.dao.AbstractDAO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("orgDAO")
@Qualifier("orgDAO")
public class OrgDAOImpl extends AbstractDAO<OrgEntity> implements
		OrgDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public OrgDAOImpl() {
		super();
		this.setEntityClass(OrgEntity.class);
	}

	@Override
	public OrgEntity getOrgByName(String name) throws DAOException {
		OrgEntity orgEntity = null;
		try {
			orgEntity = (OrgEntity) em
					.createQuery(
							"select b from OrgEntity b where b.name = :NAME and b.isDeleted=:isDeleted",
							OrgEntity.class)
					.setParameter("NAME", name)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for org: " + name);
		}
		return orgEntity;
	}

	@Override
	public List<OrgEntity> getOrgs() throws DAOException {
		List<OrgEntity> orgEntityList = new ArrayList<OrgEntity>();
		try {
			orgEntityList = em
					.createQuery(
							"select b from OrgEntity b where b.isDeleted=:isDeleted",
							OrgEntity.class)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for ent: ");
		}
		return orgEntityList;
	}

	@Override
	public void addOrg(OrgEntity orgEntity) throws DAOException {
		
		save(orgEntity);
		
	}

	@Override
	public void updateOrg(OrgEntity orgEntity) throws DAOException {
		
		update(orgEntity);
		
	}

	@Override
	public List<OrgEntity> getTopOrgs() throws DAOException {
		List<OrgEntity> orgEntityList = new ArrayList<OrgEntity>();
		try {
			orgEntityList = em
					.createQuery(
							"select DISTINCT b from OrgEntity b where b.isDeleted=:isDeleted and b.orgUuid NOT IN (select c.childOrgEntity from OrgRelEntity c where c.entEntity.id = :ENTID)",
							OrgEntity.class)
					.setParameter("isDeleted", Boolean.FALSE)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for ent" );
		}
		return orgEntityList;
	}

	@Override
	public void deleteOrg(OrgEntity orgEntity) throws DAOException {
		
		deletePhysical(orgEntity);
		
	}

	@Override
	public OrgEntity getOrgByNameEntId(String name, String entId)
			throws DAOException {
		OrgEntity orgEntity = null;
		try {
			orgEntity = (OrgEntity) em
					.createQuery(
							"select b from OrgEntity b where  b.entEntity.id = :ENTID and b.name = :NAME and b.isDeleted=:isDeleted",
							OrgEntity.class).setParameter("ENTID", entId)
					.setParameter("NAME", name)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for org: " + name);
		}
		return orgEntity;
	}

	@Override
	public OrgEntity getOrgByManager(String userUuid) throws DAOException {
		OrgEntity orgEntity = null;
		try {
			orgEntity = (OrgEntity) em
					.createQuery(
							"select b from OrgEntity b where b.managerUserEntity.userUuid = :USERUUID and b.isDeleted=:isDeleted",
							OrgEntity.class)
					.setParameter("USERUUID", userUuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for org: " + userUuid);
		}
		return orgEntity;
	}

	@Override
	public List<OrgEntity> getBottomOrgs() throws DAOException {
		List<OrgEntity> orgEntityList = new ArrayList<OrgEntity>();
		try {
			orgEntityList = em
					.createQuery(
							"select DISTINCT b from OrgEntity b where b.isDeleted=:isDeleted and b.orgUuid NOT IN (select c.parentOrgEntity from OrgRelEntity c where c.entEntity.id = :ENTID)",
							OrgEntity.class)
					.setParameter("isDeleted", Boolean.FALSE)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for ent: ");
		}
		return orgEntityList;
	}

}
