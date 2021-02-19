package com.mb.ext.core.dao.impl.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.product.ProductSkuAttrDAO;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductSkuAttrEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("productSkuAttrDAO")
@Qualifier("productSkuAttrDAO")
public class ProductSkuAttrDAOImpl extends AbstractBaseDAO<ProductSkuAttrEntity> implements
		ProductSkuAttrDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public ProductSkuAttrDAOImpl() {
		super();
		this.setEntityClass(ProductSkuAttrEntity.class);
	}

	@Override
	public void addProductAttr(ProductSkuAttrEntity productAttrEntity) throws DAOException {
		
		save(productAttrEntity);
		
	}


	@Override
	public void deleteProductAttr(ProductSkuAttrEntity productAttrEntity) throws DAOException {
		
		delete(productAttrEntity);
		
	}

	@Override
	public void updateProductAttr(ProductSkuAttrEntity productAttrEntity) throws DAOException {
		
		update(productAttrEntity);
		
	}


	@Override
	public ProductSkuAttrEntity getProductAttrByUuid(String uuid)
			throws DAOException {
		ProductSkuAttrEntity productAttrEntity = null;
		try {
			productAttrEntity = (ProductSkuAttrEntity)em.createQuery("select b from ProductSkuAttrEntity b where  b.productSkuAttrUuid = :UUID",ProductSkuAttrEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product : "+uuid);
		}
		return productAttrEntity;
	}

	@Override
	public List<ProductSkuAttrEntity> getProductAttrByProduct(
			ProductEntity productEntity) throws DAOException {
		List<ProductSkuAttrEntity> productAttrEntityList = new ArrayList<ProductSkuAttrEntity>();
		try {
			productAttrEntityList = em.createQuery("select b from ProductSkuAttrEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",ProductSkuAttrEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product attr: "+productEntity.getProductUuid());
		}
		return productAttrEntityList;
	}
	
}
