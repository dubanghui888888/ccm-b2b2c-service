package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.LogisticsDAO;
import com.mb.ext.core.entity.LogisticsEntity;
import com.mb.framework.dao.AbstractDAO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("logisticsDAO")
@Qualifier("logisticsDAO")
public class LogisticsDAOImpl extends AbstractDAO<LogisticsEntity> implements
		LogisticsDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public LogisticsDAOImpl() {
		super();
		this.setEntityClass(LogisticsEntity.class);
	}

	@Override
	public List<LogisticsEntity> getLogistics() throws DAOException {
		List<LogisticsEntity> logisticsEntityList = new ArrayList<LogisticsEntity>();
		try {
			logisticsEntityList = em
					.createQuery(
							"select b from LogisticsEntity b where b.isDeleted=:isDeleted",
							LogisticsEntity.class)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for ent" );
		}
		return logisticsEntityList;
	}

	@Override
	public LogisticsEntity getLogisticsByUuid(String uuid) throws DAOException {
		LogisticsEntity logisticsEntity = new LogisticsEntity();
		try {
			logisticsEntity = em
					.createQuery(
							"select b from LogisticsEntity b where b.isDeleted=:isDeleted",
							LogisticsEntity.class)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for ent" );
		}
		return logisticsEntity;
	}


}
