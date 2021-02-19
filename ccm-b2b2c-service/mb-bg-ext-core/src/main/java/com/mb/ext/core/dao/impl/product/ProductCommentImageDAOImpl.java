package com.mb.ext.core.dao.impl.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.product.ProductCommentImageDAO;
import com.mb.ext.core.entity.product.ProductCommentEntity;
import com.mb.ext.core.entity.product.ProductCommentImageEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("productCommentImageDAO")
@Qualifier("productCommentImageDAO")
public class ProductCommentImageDAOImpl extends AbstractBaseDAO<ProductCommentImageEntity> implements
		ProductCommentImageDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public ProductCommentImageDAOImpl() {
		super();
		this.setEntityClass(ProductCommentImageEntity.class);
	}

	@Override
	public void addProductCommentImage(ProductCommentImageEntity productCommentImageEntity) throws DAOException {
		
		save(productCommentImageEntity);
		
	}


	@Override
	public void deleteProductCommentImage(ProductCommentImageEntity productCommentImageEntity) throws DAOException {
		
		deletePhysical(productCommentImageEntity);
		
	}

	@Override
	public void updateProductCommentImage(ProductCommentImageEntity productCommentImageEntity) throws DAOException {
		
		update(productCommentImageEntity);
		
	}

	@Override
	public List<ProductCommentImageEntity> getProductCommentImagesByProduct(
			ProductEntity productEntity) throws DAOException {
		List<ProductCommentImageEntity> productCommentImageEntityList = new ArrayList<ProductCommentImageEntity>();
		try {
			productCommentImageEntityList = em.createQuery("select b from ProductCommentImageEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",ProductCommentImageEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product comment: "+productEntity.getProductUuid());
		}
		return productCommentImageEntityList;
	}
	
	@Override
	public List<ProductCommentImageEntity> getProductCommentImagesByProductComment(
			ProductCommentEntity productCommentEntity) throws DAOException {
		List<ProductCommentImageEntity> productCommentImageEntityList = new ArrayList<ProductCommentImageEntity>();
		try {
			productCommentImageEntityList = em.createQuery("select b from ProductCommentImageEntity b where  b.productCommentEntity.productCommentUuid = :UUID and b.isDeleted=:isDeleted",ProductCommentImageEntity.class).setParameter("UUID", productCommentEntity.getProductCommentUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product comment: "+productCommentEntity.getProductCommentUuid());
		}
		return productCommentImageEntityList;
	}
	
}
