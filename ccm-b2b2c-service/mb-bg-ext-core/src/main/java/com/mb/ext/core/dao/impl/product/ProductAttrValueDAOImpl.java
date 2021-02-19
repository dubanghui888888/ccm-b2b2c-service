package com.mb.ext.core.dao.impl.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.product.ProductAttrValueDAO;
import com.mb.ext.core.entity.product.ProductAttrValueEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("productAttrValueDAO")
@Qualifier("productAttrValueDAO")
public class ProductAttrValueDAOImpl extends AbstractBaseDAO<ProductAttrValueEntity> implements
		ProductAttrValueDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public ProductAttrValueDAOImpl() {
		super();
		this.setEntityClass(ProductAttrValueEntity.class);
	}

	@Override
	public void addProductAttr(ProductAttrValueEntity productAttrEntity) throws DAOException {
		
		save(productAttrEntity);
		
	}


	@Override
	public void deleteProductAttr(ProductAttrValueEntity productAttrEntity) throws DAOException {
		
		delete(productAttrEntity);
		
	}

	@Override
	public void updateProductAttr(ProductAttrValueEntity productAttrEntity) throws DAOException {
		
		update(productAttrEntity);
		
	}


	@Override
	public ProductAttrValueEntity getProductAttrByUuid(String uuid)
			throws DAOException {
		ProductAttrValueEntity productAttrValueEntity = null;
		try {
			productAttrValueEntity = (ProductAttrValueEntity)em.createQuery("select b from ProductAttrValueEntity b where  b.productAttrValueUuid = :UUID",ProductAttrValueEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product : "+uuid);
		}
		return productAttrValueEntity;
	}

	@Override
	public List<ProductAttrValueEntity> getProductAttrByProduct(
			ProductEntity productEntity) throws DAOException {
		List<ProductAttrValueEntity> productAttrEntityList = new ArrayList<ProductAttrValueEntity>();
		try {
			productAttrEntityList = em.createQuery("select b from ProductAttrValueEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",ProductAttrValueEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product attr: "+productEntity.getProductUuid());
		}
		return productAttrEntityList;
	}
	
}
