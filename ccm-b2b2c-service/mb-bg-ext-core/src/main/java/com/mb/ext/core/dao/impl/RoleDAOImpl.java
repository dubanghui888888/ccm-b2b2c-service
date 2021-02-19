package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.RoleDAO;
import com.mb.ext.core.entity.RoleEntity;
import com.mb.framework.dao.AbstractDAO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("roleDAO")
@Qualifier("roleDAO")
public class RoleDAOImpl extends AbstractDAO<RoleEntity> implements
		RoleDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public RoleDAOImpl() {
		super();
		this.setEntityClass(RoleEntity.class);
	}

	@Override
	public RoleEntity getRoleByName(String name) throws DAOException {
		RoleEntity roleEntity = null;
		try {
			roleEntity = (RoleEntity) em
					.createQuery(
							"select b from RoleEntity b where b.name = :NAME and b.isDeleted=:isDeleted",
							RoleEntity.class)
					.setParameter("NAME", name)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for org: " + name);
		}
		return roleEntity;
	}

	@Override
	public List<RoleEntity> getRoles() throws DAOException {
		List<RoleEntity> roleEntityList = new ArrayList<RoleEntity>();
		try {
			roleEntityList = em
					.createQuery(
							"select b from RoleEntity b where b.isDeleted=:isDeleted",
							RoleEntity.class)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for ent" );
		}
		return roleEntityList;
	}

	@Override
	public void addRole(RoleEntity roleEntity) throws DAOException {
		
		save(roleEntity);
		
	}


	@Override
	public void deleteRole(RoleEntity roleEntity) throws DAOException {
		
		deletePhysical(roleEntity);
		
	}

	@Override
	public void updateRole(RoleEntity roleEntity) throws DAOException {
		
		update(roleEntity);
		
	}

	@Override
	public RoleEntity getRoleByNameAndEnt(String name, String entId)
			throws DAOException {
		RoleEntity roleEntity = null;
		try {
			roleEntity = (RoleEntity) em
					.createQuery(
							"select b from RoleEntity b where  b.entEntity.id = :ENTID and b.name = :NAME and b.isDeleted=:isDeleted",
							RoleEntity.class).setParameter("ENTID", entId)
					.setParameter("NAME", name)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for org: " + name);
		}
		return roleEntity;
	}

	@Override
	public List<RoleEntity> getRolesByEnt(String entId) throws DAOException {
		List<RoleEntity> roleEntityList = new ArrayList<RoleEntity>();
		try {
			roleEntityList = em
					.createQuery(
							"select b from RoleEntity b where  b.entEntity.id = :ENTID and b.isDeleted=:isDeleted",
							RoleEntity.class).setParameter("ENTID", entId)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for ent: " + entId);
		}
		return roleEntityList;
	}

}
