package com.mb.ext.core.dao.impl;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.AdminTokenDAO;
import com.mb.ext.core.entity.AdminTokenEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("adminTokenDAO")
@Qualifier("adminTokenDAO")
public class AdminTokenDAOImpl extends AbstractBaseDAO<AdminTokenEntity> implements AdminTokenDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public AdminTokenDAOImpl()
	{
		super();
		this.setEntityClass(AdminTokenEntity.class);
	}

	@Override
	public void addAdminToken(AdminTokenEntity adminTokenEntity)
			throws DAOException {
		
		save(adminTokenEntity);

	}

	@Override
	public void deleteAdminToken(AdminTokenEntity adminTokenEntity)
			throws DAOException {
		deletePhysical(adminTokenEntity);
		
	}

	@Override
	public AdminTokenEntity findByAdminId(String adminId) throws DAOException {
		
		AdminTokenEntity adminSessionEntity = null;
		try {
			adminSessionEntity = (AdminTokenEntity)em.createQuery("select b from AdminTokenEntity b where b.adminEntity.id = :ID and b.isDeleted=:isDeleted",AdminTokenEntity.class).setParameter("ID", adminId).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for admin: "+adminId);
		}
		return adminSessionEntity;
		
	}

	@Override
	public AdminTokenEntity findByTokenId(String tokenId) throws DAOException {
		AdminTokenEntity adminSessionEntity = null;
		try {
			adminSessionEntity = (AdminTokenEntity)em.createQuery("select b from AdminTokenEntity b where b.tokenId = :TOKENID and b.isDeleted=:isDeleted",AdminTokenEntity.class).setParameter("TOKENID", tokenId).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for admin: "+tokenId);
		}
		return adminSessionEntity;
	}

	@Override
	public AdminTokenEntity findByTokenIdEntId(String tokenId, String entId)
			throws DAOException {
		AdminTokenEntity adminSessionEntity = null;
		try {
			adminSessionEntity = (AdminTokenEntity)em.createQuery("select b from AdminTokenEntity b where b.entEntity.id = :ENTID and b.tokenId = :TOKENID and b.isDeleted=:isDeleted",AdminTokenEntity.class).setParameter("ENTID", entId).setParameter("TOKENID", tokenId).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for admin: "+tokenId);
		}
		return adminSessionEntity;
	}


}
