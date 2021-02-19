package com.mb.ext.core.dao.impl.product;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.product.ProductCateDAO;
import com.mb.ext.core.entity.product.ProductCateEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("productCateDAO")
@Qualifier("productCateDAO")
public class ProductCateDAOImpl extends AbstractBaseDAO<ProductCateEntity> implements
		ProductCateDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public ProductCateDAOImpl() {
		super();
		this.setEntityClass(ProductCateEntity.class);
	}


	@Override
	public void addProductCate(ProductCateEntity productCateEntity) throws DAOException {
		
		save(productCateEntity);
		
	}


	@Override
	public void deleteProductCate(ProductCateEntity productCateEntity) throws DAOException {
		
		delete(productCateEntity);
		
	}

	@Override
	public void updateProductCate(ProductCateEntity productCateEntity) throws DAOException {
		
		update(productCateEntity);
		
	}

	@Override
	public ProductCateEntity getProductCateByUuid(String uuid)
			throws DAOException {
		ProductCateEntity productCateEntity = null;
		try {
			productCateEntity = (ProductCateEntity)em.createQuery("select b from ProductCateEntity b where  b.productCateUuid = :UUID",ProductCateEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product cate: "+uuid);
		}
		return productCateEntity;
	}
	
	@Override
	public List<ProductCateEntity> getRootProductCate()
			throws DAOException {
		List<ProductCateEntity> productCateEntityList = null;
		try {
			productCateEntityList = em.createQuery("select b from ProductCateEntity b where  b.parentCateEntity IS NULL and b.isDeleted=:isDeleted order by b.sortNumber",ProductCateEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product cate: ");
		}
		return productCateEntityList;
	}
	
	@Override
	public List<ProductCateEntity> getHomeProductCate()
			throws DAOException {
		List<ProductCateEntity> productCateEntityList = null;
		try {
			productCateEntityList = em.createQuery("select b from ProductCateEntity b where  b.isDisplayedHome = :ISDISPLAYEDHOME and b.isDeleted=:isDeleted order by b.sortNumber",ProductCateEntity.class).setParameter("ISDISPLAYEDHOME", Boolean.TRUE).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product cate: ");
		}
		return productCateEntityList;
	}
	
	@Override
	public List<ProductCateEntity> getLeafProductCate()
			throws DAOException {
		List<ProductCateEntity> productCateEntityList = null;
		try {
			productCateEntityList = em.createQuery("select b from ProductCateEntity b where  b.isLeaf = :ISLEAF and b.isDeleted=:isDeleted",ProductCateEntity.class).setParameter("ISLEAF", true).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product cate: ");
		}
		return productCateEntityList;
	}
	
	@Override
	public List<ProductCateEntity> getChildProductCate(ProductCateEntity productCateEntity)
			throws DAOException {
		List<ProductCateEntity> productCateEntityList = null;
		try {
			productCateEntityList = em.createQuery("select b from ProductCateEntity b where  b.parentCateEntity.productCateUuid = :UUID and b.isDeleted=:isDeleted order by b.sortNumber",ProductCateEntity.class).setParameter("UUID", productCateEntity.getProductCateUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product cate: "+productCateEntity.getProductCateUuid());
		}
		return productCateEntityList;
	}

}
