package com.mb.ext.core.dao.impl.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.product.ProductVideoDAO;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductVideoEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("productVideoDAO")
@Qualifier("productVideoDAO")
public class ProductVideoDAOImpl extends AbstractBaseDAO<ProductVideoEntity> implements ProductVideoDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public ProductVideoDAOImpl()
	{
		super();
		this.setEntityClass(ProductVideoEntity.class);
	}

	@Override
	public void createProductVideo(ProductVideoEntity productVideoEntity)
			throws DAOException {
		save(productVideoEntity);
		
	}

	@Override
	public void updateProductVideo(ProductVideoEntity productVideoEntity)
			throws DAOException {
		update(productVideoEntity);
		
	}

	@Override
	public void deleteProductVideo(ProductVideoEntity productVideoEntity)
			throws DAOException {
		delete(productVideoEntity);
		
	}

	@Override
	public List<ProductVideoEntity> getVideosByProduct(ProductEntity productEntity) throws DAOException {
		List<ProductVideoEntity> videoEntityList = new ArrayList<ProductVideoEntity>();;
		try {
			videoEntityList = em.createQuery("select b from ProductVideoEntity b where b.productEntity.productUuid = :UUID and  b.isDeleted=:isDeleted",ProductVideoEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return videoEntityList;
	}

	@Override
	public ProductVideoEntity getVideoByUuid(String uuid) throws DAOException {
		ProductVideoEntity videoEntity = new ProductVideoEntity();
		try {
			videoEntity = (ProductVideoEntity)em.createQuery("select b from ProductVideoEntity b where b.productVideoUuid = :UUID",ProductVideoEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return videoEntity;
	}

}
