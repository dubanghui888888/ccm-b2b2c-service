package com.mb.ext.core.dao.impl.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.product.ProductDescImageDAO;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductDescImageEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("productDescImageDAO")
@Qualifier("productDescImageDAO")
public class ProductDescImageDAOImpl extends AbstractBaseDAO<ProductDescImageEntity> implements ProductDescImageDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public ProductDescImageDAOImpl()
	{
		super();
		this.setEntityClass(ProductDescImageEntity.class);
	}

	@Override
	public void createProductDescImage(ProductDescImageEntity productDescImageEntity)
			throws DAOException {
		save(productDescImageEntity);
		
	}

	@Override
	public void updateProductDescImage(ProductDescImageEntity productDescImageEntity)
			throws DAOException {
		update(productDescImageEntity);
		
	}

	@Override
	public void deleteProductDescImage(ProductDescImageEntity productDescImageEntity)
			throws DAOException {
		delete(productDescImageEntity);
		
	}

	@Override
	public List<ProductDescImageEntity> getDescImagesByProduct(ProductEntity productEntity) throws DAOException {
		List<ProductDescImageEntity> descImageEntityList = new ArrayList<ProductDescImageEntity>();;
		try {
			descImageEntityList = em.createQuery("select b from ProductDescImageEntity b where b.productEntity.productUuid = :UUID and  b.isDeleted=:isDeleted",ProductDescImageEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return descImageEntityList;
	}

	@Override
	public ProductDescImageEntity getDescImageByUuid(String uuid) throws DAOException {
		ProductDescImageEntity descImageEntity = new ProductDescImageEntity();
		try {
			descImageEntity = (ProductDescImageEntity)em.createQuery("select b from ProductDescImageEntity b where b.productDescImageUuid = :UUID",ProductDescImageEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return descImageEntity;
	}

}
