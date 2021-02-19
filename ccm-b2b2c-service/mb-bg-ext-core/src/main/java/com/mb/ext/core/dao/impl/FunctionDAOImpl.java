package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.FunctionDAO;
import com.mb.ext.core.entity.FunctionEntity;
import com.mb.framework.dao.AbstractDAO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("functionDAO")
@Qualifier("functionDAO")
public class FunctionDAOImpl extends AbstractDAO<FunctionEntity> implements FunctionDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public FunctionDAOImpl()
	{
		super();
		this.setEntityClass(FunctionEntity.class);
	}

	@Override
	public void addFunction(FunctionEntity functionEntity)
			throws DAOException {
		
		save(functionEntity);
		
	}

	@Override
	public FunctionEntity getFunctionByCode(String code) throws DAOException {
		
		FunctionEntity functionEntity = null;
		try {
			functionEntity = (FunctionEntity) em
					.createQuery(
							"select b from FunctionEntity b where b.code = :CODE and b.isDeleted=:isDeleted",
							FunctionEntity.class)
					.setParameter("CODE", code)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for function: " + code);
		}
		return functionEntity;
	}

	@Override
	public List<FunctionEntity> getFunctions() throws DAOException {
		List<FunctionEntity> functionEntityList = new ArrayList<FunctionEntity>();
		try {
			functionEntityList = em
					.createQuery(
							"select b from FunctionEntity b where b.isDeleted=:isDeleted",
							FunctionEntity.class)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return functionEntityList;
	}

	

}
