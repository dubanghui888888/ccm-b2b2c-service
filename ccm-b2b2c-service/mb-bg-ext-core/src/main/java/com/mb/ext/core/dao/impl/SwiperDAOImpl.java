package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.SwiperDAO;
import com.mb.ext.core.entity.SwiperEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("swiperDAO")
@Qualifier("swiperDAO")
public class SwiperDAOImpl extends AbstractBaseDAO<SwiperEntity> implements
		SwiperDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public SwiperDAOImpl() {
		super();
		this.setEntityClass(SwiperEntity.class);
	}

	@Override
	public SwiperEntity getSwiperByUuid(String uuid) throws DAOException {
		SwiperEntity swiperEntity = null;
		try {
			swiperEntity = (SwiperEntity) em
					.createQuery(
							"select b from SwiperEntity b where b.swiperUuid = :UUID and b.isDeleted=:isDeleted",
							SwiperEntity.class)
					.setParameter("UUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for swiper: " + uuid);
		}
		return swiperEntity;
	}
	
	@Override
	public List<SwiperEntity> getSwipers() throws DAOException {
		List<SwiperEntity> swiperEntityList = new ArrayList<SwiperEntity>();
		try {
			swiperEntityList = em
					.createQuery(
							"select b from SwiperEntity b where b.isDeleted=:isDeleted",
							SwiperEntity.class)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for swiper" );
		}
		return swiperEntityList;
	}

	@Override
	public void addSwiper(SwiperEntity swiperEntity) throws DAOException {
		
		save(swiperEntity);
		
	}


	@Override
	public void deleteSwiper(SwiperEntity swiperEntity) throws DAOException {
		
		deletePhysical(swiperEntity);
		
	}

	@Override
	public void updateSwiper(SwiperEntity swiperEntity) throws DAOException {
		
		update(swiperEntity);
		
	}
}
