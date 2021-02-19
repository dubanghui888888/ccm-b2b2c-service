package com.mb.ext.core.dao.impl.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.product.ProductCollectDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.product.ProductCollectEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("productCollectDAO")
@Qualifier("productCollectDAO")
public class ProductCollectDAOImpl extends AbstractBaseDAO<ProductCollectEntity> implements
		ProductCollectDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public ProductCollectDAOImpl() {
		super();
		this.setEntityClass(ProductCollectEntity.class);
	}

	@Override
	public void addProductCollect(ProductCollectEntity productCollectEntity) throws DAOException {
		
		save(productCollectEntity);
		
	}


	@Override
	public void deleteProductCollect(ProductCollectEntity productCollectEntity) throws DAOException {
		
		deletePhysical(productCollectEntity);
		
	}

	@Override
	public List<ProductCollectEntity> getProductCollectByUser(
			UserEntity userEntity) throws DAOException {
		List<ProductCollectEntity> productCollectEntityList = new ArrayList<ProductCollectEntity>();
		try {
			productCollectEntityList = em.createQuery("select b from ProductCollectEntity b where  b.userEntity.userUuid = :UUID and b.isDeleted=:isDeleted",ProductCollectEntity.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product collect: "+userEntity.getUserUuid());
		}
		return productCollectEntityList;
	}
	
	@Override
	public ProductCollectEntity getProductCollectByUserProduct(
			UserEntity userEntity, ProductEntity productEntity) throws DAOException {
		ProductCollectEntity productCollectEntity = null;
		try {
			productCollectEntity = em.createQuery("select b from ProductCollectEntity b where b.productEntity.productUuid = :PRODUCTUUID and  b.userEntity.userUuid = :USERUUID and b.isDeleted=:isDeleted",ProductCollectEntity.class).setParameter("USERUUID", userEntity.getUserUuid()).setParameter("PRODUCTUUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product collect: "+userEntity.getUserUuid());
		}
		return productCollectEntity;
	}

}
