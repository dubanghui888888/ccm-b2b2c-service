package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.AdDAO;
import com.mb.ext.core.entity.AdEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("adDAO")
@Qualifier("adDAO")
public class AdDAOImpl extends AbstractBaseDAO<AdEntity> implements AdDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public AdDAOImpl() {
		super();
		this.setEntityClass(AdEntity.class);
	}

	@Override
	public AdEntity getAdByUuid(String uuid) throws DAOException {
		AdEntity adEntity = null;
		try {
			adEntity = (AdEntity) em
					.createQuery("select b from AdEntity b where b.adUuid = :UUID and b.isDeleted=:isDeleted",
							AdEntity.class)
					.setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for ad: " + uuid);
		}
		return adEntity;
	}

	@Override
	public List<AdEntity> getAllAds() throws DAOException {
		List<AdEntity> adEntityList = new ArrayList<AdEntity>();
		try {
			adEntityList = em.createQuery("select b from AdEntity b where b.isDeleted=:isDeleted", AdEntity.class)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for ad");
		}
		return adEntityList;
	}

	@Override
	public List<AdEntity> getActiveAds() throws DAOException {
		List<AdEntity> adEntityList = new ArrayList<AdEntity>();
		try {
			adEntityList = em
					.createQuery("select b from AdEntity b where b.isActive = :ACTIVE and b.isDeleted=:isDeleted",
							AdEntity.class)
					.setParameter("ACTIVE", Boolean.TRUE).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for ad");
		}
		return adEntityList;
	}

	@Override
	public List<AdEntity> getAdsByLocation(String location) throws DAOException {
		List<AdEntity> adEntityList = new ArrayList<AdEntity>();
		try {
			adEntityList = em.createQuery(
					"select b from AdEntity b where b.isActive = :ACTIVE and b.location=:LOCATION and b.isDeleted=:isDeleted",
					AdEntity.class).setParameter("ACTIVE", Boolean.TRUE).setParameter("LOCATION", location)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for ad");
		}
		return adEntityList;
	}

	@Override
	public List<AdEntity> getAdsByProductCate(String productCateUuid) throws DAOException {
		List<AdEntity> adEntityList = new ArrayList<AdEntity>();
		try {
			adEntityList = em.createQuery(
					"select b from AdEntity b where b.isActive = :ACTIVE and b.productCateEntity.productCateUuid=:UUID and b.isDeleted=:isDeleted",
					AdEntity.class).setParameter("ACTIVE", Boolean.TRUE).setParameter("UUID", productCateUuid)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for ad");
		}
		return adEntityList;
	}
	
	@Override
	public List<AdEntity> getAdsByLocationProductCate(String location,String productCateUuid) throws DAOException {
		List<AdEntity> adEntityList = new ArrayList<AdEntity>();
		try {
			adEntityList = em.createQuery(
					"select b from AdEntity b where b.isActive = :ACTIVE and b.location=:LOCATION and b.productCateEntity.productCateUuid=:UUID and b.isDeleted=:isDeleted",
					AdEntity.class).setParameter("ACTIVE", Boolean.TRUE).setParameter("LOCATION", location).setParameter("UUID", productCateUuid)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for ad");
		}
		return adEntityList;
	}

	@Override
	public void addAd(AdEntity adEntity) throws DAOException {

		save(adEntity);

	}

	@Override
	public void deleteAd(AdEntity adEntity) throws DAOException {

		deletePhysical(adEntity);

	}

	@Override
	public void updateAd(AdEntity adEntity) throws DAOException {

		update(adEntity);

	}
}
