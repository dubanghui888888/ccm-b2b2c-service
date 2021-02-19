package com.mb.ext.core.dao.impl.promote;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.promote.ProductPromoteDAO;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.promote.ProductPromoteEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("productPromoteDAO")
@Qualifier("productPromoteDAO")
public class ProductPromoteDAOImpl extends AbstractBaseDAO<ProductPromoteEntity> implements ProductPromoteDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public ProductPromoteDAOImpl()
	{
		super();
		this.setEntityClass(ProductPromoteEntity.class);
	}

	@Override
	public void createProductPromote(ProductPromoteEntity productPromoteEntity)
			throws DAOException {
		save(productPromoteEntity);
		
	}

	@Override
	public void updateProductPromote(ProductPromoteEntity productPromoteEntity)
			throws DAOException {
		update(productPromoteEntity);
		
	}

	@Override
	public void deleteProductPromote(ProductPromoteEntity productPromoteEntity)
			throws DAOException {
		deletePhysical(productPromoteEntity);
		
	}

	@Override
	public List<ProductPromoteEntity> getPromoteByProduct(ProductEntity productEntity) throws DAOException {
		List<ProductPromoteEntity> promoteEntityList = new ArrayList<ProductPromoteEntity>();;
		try {
			promoteEntityList = em.createQuery("select b from ProductPromoteEntity b where b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",ProductPromoteEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return promoteEntityList;
	}

	@Override
	public ProductPromoteEntity getPromoteByUuid(String uuid) throws DAOException {
		ProductPromoteEntity promoteEntity = new ProductPromoteEntity();
		try {
			promoteEntity = (ProductPromoteEntity)em.createQuery("select b from ProductPromoteEntity b where b.productPromoteUuid = :UUID and b.isDeleted=:isDeleted",ProductPromoteEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return promoteEntity;
	}
	
	@Override
	public ProductPromoteEntity getPromoteByTypeAndUuid(ProductEntity productEntity, String promoteType, String uuid) throws DAOException {
		ProductPromoteEntity promoteEntity = null;
		try {
			promoteEntity = (ProductPromoteEntity)em.createQuery("select b from ProductPromoteEntity b where b.productEntity.productUuid = :UUID and b.promoteType = :PROMOTETYPE and b.promoteUuid = :PROMOTEUUID and b.isDeleted=:isDeleted",ProductPromoteEntity.class)
					.setParameter("UUID", productEntity.getProductUuid())
					.setParameter("PROMOTETYPE", promoteType)
					.setParameter("PROMOTEUUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return promoteEntity;
	}
	
	@Override
	public List<ProductPromoteEntity> getPromoteByType(ProductEntity productEntity, String promoteType) throws DAOException {
		List<ProductPromoteEntity> promoteEntityList = new ArrayList<ProductPromoteEntity>();
		try {
			promoteEntityList = em.createQuery("select b from ProductPromoteEntity b where b.productEntity.productUuid = :UUID and b.promoteType = :PROMOTETYPE and b.isDeleted=:isDeleted",ProductPromoteEntity.class)
					.setParameter("UUID", productEntity.getProductUuid())
					.setParameter("PROMOTETYPE", promoteType)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return promoteEntityList;
	}

}
