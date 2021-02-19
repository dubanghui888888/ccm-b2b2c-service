package com.mb.ext.core.dao.impl.point;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.point.PointProductDescImageDAO;
import com.mb.ext.core.entity.point.PointProductDescImageEntity;
import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("pointProductDescImageDAO")
@Qualifier("pointProductDescImageDAO")
public class PointProductDescImageDAOImpl extends AbstractBaseDAO<PointProductDescImageEntity> implements PointProductDescImageDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public PointProductDescImageDAOImpl()
	{
		super();
		this.setEntityClass(PointProductDescImageEntity.class);
	}

	@Override
	public void createProductDescImage(PointProductDescImageEntity productDescImageEntity)
			throws DAOException {
		save(productDescImageEntity);
		
	}

	@Override
	public void updateProductDescImage(PointProductDescImageEntity productDescImageEntity)
			throws DAOException {
		update(productDescImageEntity);
		
	}

	@Override
	public void deleteProductDescImage(PointProductDescImageEntity productDescImageEntity)
			throws DAOException {
		delete(productDescImageEntity);
		
	}

	@Override
	public List<PointProductDescImageEntity> getDescImagesByProduct(PointProductEntity productEntity) throws DAOException {
		List<PointProductDescImageEntity> descImageEntityList = new ArrayList<PointProductDescImageEntity>();;
		try {
			descImageEntityList = em.createQuery("select b from PointProductDescImageEntity b where b.productEntity.productUuid = :UUID and  b.isDeleted=:isDeleted",PointProductDescImageEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return descImageEntityList;
	}

	@Override
	public PointProductDescImageEntity getDescImageByUuid(String uuid) throws DAOException {
		PointProductDescImageEntity descImageEntity = new PointProductDescImageEntity();
		try {
			descImageEntity = (PointProductDescImageEntity)em.createQuery("select b from PointProductDescImageEntity b where b.productDescImageUuid = :UUID",PointProductDescImageEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return descImageEntity;
	}

}
