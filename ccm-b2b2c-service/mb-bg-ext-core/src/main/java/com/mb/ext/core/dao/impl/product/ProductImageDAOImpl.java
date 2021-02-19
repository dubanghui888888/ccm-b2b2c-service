package com.mb.ext.core.dao.impl.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.product.ProductImageDAO;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductImageEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("productImageDAO")
@Qualifier("productImageDAO")
public class ProductImageDAOImpl extends AbstractBaseDAO<ProductImageEntity> implements ProductImageDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public ProductImageDAOImpl()
	{
		super();
		this.setEntityClass(ProductImageEntity.class);
	}

	@Override
	public void createProductImage(ProductImageEntity productImageEntity)
			throws DAOException {
		save(productImageEntity);
		
	}

	@Override
	public void updateProductImage(ProductImageEntity productImageEntity)
			throws DAOException {
		update(productImageEntity);
		
	}

	@Override
	public void deleteProductImage(ProductImageEntity productImageEntity)
			throws DAOException {
		delete(productImageEntity);
		
	}

	@Override
	public List<ProductImageEntity> getImagesByProduct(ProductEntity productEntity) throws DAOException {
		List<ProductImageEntity> imageEntityList = new ArrayList<ProductImageEntity>();;
		try {
			imageEntityList = em.createQuery("select b from ProductImageEntity b where b.productEntity.productUuid = :UUID and  b.isDeleted=:isDeleted",ProductImageEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return imageEntityList;
	}

	@Override
	public ProductImageEntity getImageByUuid(String uuid) throws DAOException {
		ProductImageEntity imageEntity = new ProductImageEntity();
		try {
			imageEntity = (ProductImageEntity)em.createQuery("select b from ProductImageEntity b where b.productImageUuid = :UUID",ProductImageEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return imageEntity;
	}

}
