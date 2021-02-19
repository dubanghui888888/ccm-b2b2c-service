package com.mb.ext.core.dao.impl.point;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.point.PointProductVideoDAO;
import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.ext.core.entity.point.PointProductVideoEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("pointProductVideoDAO")
@Qualifier("pointProductVideoDAO")
public class PointProductVideoDAOImpl extends AbstractBaseDAO<PointProductVideoEntity> implements PointProductVideoDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public PointProductVideoDAOImpl()
	{
		super();
		this.setEntityClass(PointProductVideoEntity.class);
	}

	@Override
	public void createProductVideo(PointProductVideoEntity productVideoEntity)
			throws DAOException {
		save(productVideoEntity);
		
	}

	@Override
	public void updateProductVideo(PointProductVideoEntity productVideoEntity)
			throws DAOException {
		update(productVideoEntity);
		
	}

	@Override
	public void deleteProductVideo(PointProductVideoEntity productVideoEntity)
			throws DAOException {
		delete(productVideoEntity);
		
	}

	@Override
	public List<PointProductVideoEntity> getVideosByProduct(PointProductEntity productEntity) throws DAOException {
		List<PointProductVideoEntity> videoEntityList = new ArrayList<PointProductVideoEntity>();;
		try {
			videoEntityList = em.createQuery("select b from PointProductVideoEntity b where b.productEntity.productUuid = :UUID and  b.isDeleted=:isDeleted",PointProductVideoEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return videoEntityList;
	}

	@Override
	public PointProductVideoEntity getVideoByUuid(String uuid) throws DAOException {
		PointProductVideoEntity videoEntity = new PointProductVideoEntity();
		try {
			videoEntity = (PointProductVideoEntity)em.createQuery("select b from PointProductVideoEntity b where b.productVideoUuid = :UUID",PointProductVideoEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return videoEntity;
	}

}
