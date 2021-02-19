package com.mb.ext.core.dao.impl;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.PermissionDAO;
import com.mb.ext.core.entity.PermissionEntity;
import com.mb.framework.dao.AbstractDAO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("permissionDAO")
@Qualifier("permissionDAO")
public class PermissionDAOImpl extends AbstractDAO<PermissionEntity> implements
		PermissionDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public PermissionDAOImpl() {
		super();
		this.setEntityClass(PermissionEntity.class);
	}

	@Override
	public void addPermission(PermissionEntity permissionEntity)
			throws DAOException {

		save(permissionEntity);

	}

	@Override
	public void deletePermission(PermissionEntity permissionEntity)
			throws DAOException {

		deletePhysical(permissionEntity);

	}

	@Override
	public PermissionEntity getPermissionByRoleFunction(String roleName,
			String functionCode) throws DAOException {

		PermissionEntity permissionEntity = null;
		try {
			permissionEntity = (PermissionEntity) em
					.createQuery(
							"select b from PermissionEntity b where  b.roleEntity.name= :ROLENAME and b.functionEntity.code = :FUNCTIONCODE and b.isDeleted=:isDeleted",
							PermissionEntity.class)
					.setParameter("ROLENAME", roleName)
					.setParameter("FUNCTIONCODE", functionCode)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for role and function: " + roleName
					+ ":" + functionCode);
		}
		return permissionEntity;

	}

}
