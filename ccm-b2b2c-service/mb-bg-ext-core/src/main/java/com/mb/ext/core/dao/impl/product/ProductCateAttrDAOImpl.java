package com.mb.ext.core.dao.impl.product;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.product.ProductCateAttrDAO;
import com.mb.ext.core.entity.product.ProductCateAttrEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("productCateAttrDAO")
@Qualifier("productCateAttrDAO")
public class ProductCateAttrDAOImpl extends AbstractBaseDAO<ProductCateAttrEntity> implements
		ProductCateAttrDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public ProductCateAttrDAOImpl() {
		super();
		this.setEntityClass(ProductCateAttrEntity.class);
	}

	@Override
	public void addProductCateAttr(ProductCateAttrEntity productCateAttrEntity) throws DAOException {
		
		save(productCateAttrEntity);
		
	}


	@Override
	public void deleteProductCateAttr(ProductCateAttrEntity productCateAttrEntity) throws DAOException {
		
		deletePhysical(productCateAttrEntity);
		
	}

	@Override
	public void updateProductCateAttr(ProductCateAttrEntity productCateAttrEntity) throws DAOException {
		
		update(productCateAttrEntity);
		
	}


	@Override
	public ProductCateAttrEntity getProductCateAttrByUuid(String uuid)
			throws DAOException {
		ProductCateAttrEntity productCateAttrEntity = null;
		try {
			productCateAttrEntity = (ProductCateAttrEntity)em.createQuery("select b from ProductCateAttrEntity b where  b.productCateAttrUuid = :UUID",ProductCateAttrEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product cate: "+uuid);
		}
		return productCateAttrEntity;
	}

}
