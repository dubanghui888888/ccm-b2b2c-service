package com.mb.ext.core.dao.impl.point;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.point.PointProductImageDAO;
import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.ext.core.entity.point.PointProductImageEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("pointProductImageDAO")
@Qualifier("pointProductImageDAO")
public class PointProductImageDAOImpl extends AbstractBaseDAO<PointProductImageEntity> implements PointProductImageDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public PointProductImageDAOImpl()
	{
		super();
		this.setEntityClass(PointProductImageEntity.class);
	}

	@Override
	public void createProductImage(PointProductImageEntity productImageEntity)
			throws DAOException {
		save(productImageEntity);
		
	}

	@Override
	public void updateProductImage(PointProductImageEntity productImageEntity)
			throws DAOException {
		update(productImageEntity);
		
	}

	@Override
	public void deleteProductImage(PointProductImageEntity productImageEntity)
			throws DAOException {
		delete(productImageEntity);
		
	}

	@Override
	public List<PointProductImageEntity> getImagesByProduct(PointProductEntity productEntity) throws DAOException {
		List<PointProductImageEntity> imageEntityList = new ArrayList<PointProductImageEntity>();;
		try {
			imageEntityList = em.createQuery("select b from PointProductImageEntity b where b.productEntity.productUuid = :UUID and  b.isDeleted=:isDeleted",PointProductImageEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return imageEntityList;
	}

	@Override
	public PointProductImageEntity getImageByUuid(String uuid) throws DAOException {
		PointProductImageEntity imageEntity = new PointProductImageEntity();
		try {
			imageEntity = (PointProductImageEntity)em.createQuery("select b from PointProductImageEntity b where b.productImageUuid = :UUID",PointProductImageEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return imageEntity;
	}

}
