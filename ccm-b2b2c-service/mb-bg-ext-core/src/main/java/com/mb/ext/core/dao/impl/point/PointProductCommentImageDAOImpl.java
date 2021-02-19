package com.mb.ext.core.dao.impl.point;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.point.PointProductCommentImageDAO;
import com.mb.ext.core.entity.point.PointProductCommentEntity;
import com.mb.ext.core.entity.point.PointProductCommentImageEntity;
import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("pointProductCommentImageDAO")
@Qualifier("pointProductCommentImageDAO")
public class PointProductCommentImageDAOImpl extends AbstractBaseDAO<PointProductCommentImageEntity> implements
PointProductCommentImageDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public PointProductCommentImageDAOImpl() {
		super();
		this.setEntityClass(PointProductCommentImageEntity.class);
	}

	@Override
	public void addProductCommentImage(PointProductCommentImageEntity productCommentImageEntity) throws DAOException {
		
		save(productCommentImageEntity);
		
	}


	@Override
	public void deleteProductCommentImage(PointProductCommentImageEntity productCommentImageEntity) throws DAOException {
		
		deletePhysical(productCommentImageEntity);
		
	}

	@Override
	public void updateProductCommentImage(PointProductCommentImageEntity productCommentImageEntity) throws DAOException {
		
		update(productCommentImageEntity);
		
	}

	@Override
	public List<PointProductCommentImageEntity> getProductCommentImagesByProduct(
			PointProductEntity productEntity) throws DAOException {
		List<PointProductCommentImageEntity> productCommentImageEntityList = new ArrayList<PointProductCommentImageEntity>();
		try {
			productCommentImageEntityList = em.createQuery("select b from PointProductCommentImageEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",PointProductCommentImageEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product comment: "+productEntity.getProductUuid());
		}
		return productCommentImageEntityList;
	}
	
	@Override
	public List<PointProductCommentImageEntity> getProductCommentImagesByProductComment(
			PointProductCommentEntity productCommentEntity) throws DAOException {
		List<PointProductCommentImageEntity> productCommentImageEntityList = new ArrayList<PointProductCommentImageEntity>();
		try {
			productCommentImageEntityList = em.createQuery("select b from PointProductCommentImageEntity b where  b.productCommentEntity.productCommentUuid = :UUID and b.isDeleted=:isDeleted",PointProductCommentImageEntity.class).setParameter("UUID", productCommentEntity.getProductCommentUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product comment: "+productCommentEntity.getProductCommentUuid());
		}
		return productCommentImageEntityList;
	}
	
}
