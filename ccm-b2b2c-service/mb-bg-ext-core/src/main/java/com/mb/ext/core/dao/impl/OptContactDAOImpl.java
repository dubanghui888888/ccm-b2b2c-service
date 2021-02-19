package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.OptContactDAO;
import com.mb.ext.core.entity.OptContactEntity;
import com.mb.framework.dao.AbstractDAO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("optContactDAO")
@Qualifier("optContactDAO")
public class OptContactDAOImpl extends AbstractDAO<OptContactEntity> implements OptContactDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public OptContactDAOImpl()
	{
		super();
		this.setEntityClass(OptContactEntity.class);
	}

	@Override
	public void addOptContact(OptContactEntity optContactEntity) throws DAOException {
		
		save(optContactEntity);
		
	}

	@Override
	public OptContactEntity getOptContactByTypeAndNo(String contactType, String contactNo) throws DAOException {
		
		OptContactEntity optContactEntity = null;
		try {
			optContactEntity = (OptContactEntity)em.createQuery("select b from OptContactEntity b where b.contactType = :CONTACTTYPE and b.contactNo = :CONTACTNO and b.isDeleted=:isDeleted",OptContactEntity.class).setParameter("CONTACTTYPE", contactType).setParameter("CONTACTNO", contactNo).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for optContact: "+contactNo);
		}
		return optContactEntity;
	}

	@Override
	public void updateOptContact(OptContactEntity optContactEntity) throws DAOException {

		update(optContactEntity);
		
	}
	
	@Override
	public void deleteOptContact(OptContactEntity optContactEntity) throws DAOException {

		update(optContactEntity);
		
	}

	@Override
	public List<OptContactEntity> getOptContacts() throws DAOException {
		
		List<OptContactEntity> optContactEntityList = new ArrayList<OptContactEntity>();
		try {
			optContactEntityList = em.createQuery("select b from OptContactEntity b where b.isDeleted=:isDeleted",OptContactEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for optContact: ");
		}
		return optContactEntityList;
	}

	

}
