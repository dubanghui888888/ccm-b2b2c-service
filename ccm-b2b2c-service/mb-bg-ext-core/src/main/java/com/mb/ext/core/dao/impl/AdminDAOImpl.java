package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.AdminDAO;
import com.mb.ext.core.entity.AdminEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("adminDAO")
@Qualifier("adminDAO")
public class AdminDAOImpl extends AbstractBaseDAO<AdminEntity> implements AdminDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public AdminDAOImpl()
	{
		super();
		this.setEntityClass(AdminEntity.class);
	}

	/**
	 * This method is used for inserting admin information.
	 * 
	 * @param admin
	 */
	@Override
	public void addAdmin(AdminEntity adminEntity) throws DAOException
	{
		save(adminEntity);
	}

	@Override
	public void updateAdmin(AdminEntity adminEntity) throws DAOException {
		update(adminEntity);
		
	}

	@Override
	public void deleteAdmin(AdminEntity adminEntity) throws DAOException {
		
		deletePhysical(adminEntity);
		
	}
	
	@Override
	public AdminEntity getAdminById(String id) throws DAOException {
		AdminEntity adminEntity = null;
		try {
			adminEntity = (AdminEntity)em.createQuery("select b from AdminEntity b where  b.id = :ID and b.isDeleted=:isDeleted",AdminEntity.class).setParameter("ID", id).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+id);
		}
		return adminEntity;
	}

	@Override
	public AdminEntity getAdminByUuid(String uuid) throws DAOException {
		AdminEntity adminEntity = null;
		try {
			adminEntity = (AdminEntity)em.createQuery("select b from AdminEntity b where  b.adminUuid = :UUID and b.isDeleted=:isDeleted",AdminEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+uuid);
		}
		return adminEntity;
	}

	@Override
	public List<AdminEntity> getAdmins() throws DAOException {
		List<AdminEntity> adminEntityList = new ArrayList<AdminEntity>();;
		try {
			adminEntityList = em.createQuery("select b from AdminEntity b where  b.isDeleted=:isDeleted",AdminEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for admin");
		}
		return adminEntityList;
	}

	@Override
	public AdminEntity getAdminByTokenId(String tokenId) throws DAOException {
		AdminEntity adminEntity = null;
		try {
			adminEntity = (AdminEntity)em.createQuery("select b from AdminEntity b where  b.adminTokenEntity.tokenId = :TOKENID and b.isDeleted=:isDeleted",AdminEntity.class).setParameter("TOKENID", tokenId).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: "+tokenId);
		}
		return adminEntity;
	}

}
