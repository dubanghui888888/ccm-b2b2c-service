package com.mb.ext.core.dao.impl.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.product.ProductSkuDAO;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductSkuEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("productSkuDAO")
@Qualifier("productSkuDAO")
public class ProductSkuDAOImpl extends AbstractBaseDAO<ProductSkuEntity> implements
		ProductSkuDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public ProductSkuDAOImpl() {
		super();
		this.setEntityClass(ProductSkuEntity.class);
	}

	@Override
	public void addProductSku(ProductSkuEntity productSkuEntity) throws DAOException {
		
		save(productSkuEntity);
		
	}


	@Override
	public void deleteProductSku(ProductSkuEntity productSkuEntity) throws DAOException {
		
		delete(productSkuEntity);
		
	}

	@Override
	public void updateProductSku(ProductSkuEntity productSkuEntity) throws DAOException {
		
		update(productSkuEntity);
		
	}


	@Override
	public ProductSkuEntity getProductSkuByUuid(String uuid)
			throws DAOException {
		ProductSkuEntity productSkuEntity = null;
		try {
			productSkuEntity = (ProductSkuEntity)em.createQuery("select b from ProductSkuEntity b where  b.productSkuUuid = :UUID",ProductSkuEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product : "+uuid);
		}
		return productSkuEntity;
	}
	
	@Override
	public ProductSkuEntity getProductSkuByAttrValueUuids(ProductEntity productEntity, String skuAttrValueUuids)
			throws DAOException {
		ProductSkuEntity productSkuEntity = null;
		try {
			productSkuEntity = (ProductSkuEntity)em.createQuery("select b from ProductSkuEntity b where  b.skuAttrValueUuids = :UUID and b.productEntity.productUuid = :PRODUCTUUID and b.isDeleted=:isDeleted",ProductSkuEntity.class).setParameter("PRODUCTUUID", productEntity.getProductUuid()).setParameter("UUID", skuAttrValueUuids).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product : "+skuAttrValueUuids);
		}
		return productSkuEntity;
	}

	@Override
	public List<ProductSkuEntity> getProductSkuByProduct(
			ProductEntity productEntity) throws DAOException {
		List<ProductSkuEntity> productSkuEntityList = new ArrayList<ProductSkuEntity>();
		try {
			productSkuEntityList = em.createQuery("select b from ProductSkuEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",ProductSkuEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product sku: "+productEntity.getProductUuid());
		}
		return productSkuEntityList;
	}

	@Override
	public int getMinUnitPointByProduct(ProductEntity productEntity)
			throws DAOException {
		Integer maxUnitPoint = null;
		try {
			maxUnitPoint = em.createQuery("select min(b.skuUnitPoint) from ProductSkuEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",Integer.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product sku: "+productEntity.getProductUuid());
		}
		return maxUnitPoint==null?0:maxUnitPoint.intValue();
	}
	
	@Override
	public BigDecimal getMinUnitPriceByProduct(ProductEntity productEntity)
			throws DAOException {
		BigDecimal maxUnitPoint = null;
		try {
			maxUnitPoint = em.createQuery("select min(b.skuUnitPrice) from ProductSkuEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",BigDecimal.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product sku: "+productEntity.getProductUuid());
		}
		return maxUnitPoint==null?new BigDecimal(0):maxUnitPoint;
	}
	
	@Override
	public int getMinUnitPointStandardByProduct(ProductEntity productEntity)
			throws DAOException {
		Integer maxUnitPoint = null;
		try {
			maxUnitPoint = em.createQuery("select min(b.skuUnitPointStandard) from ProductSkuEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",Integer.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product sku: "+productEntity.getProductUuid());
		}
		return maxUnitPoint==null?0:maxUnitPoint.intValue();
	}
	
	@Override
	public BigDecimal getMinUnitPriceStandardByProduct(ProductEntity productEntity)
			throws DAOException {
		BigDecimal maxUnitPoint = null;
		try {
			maxUnitPoint = em.createQuery("select min(b.skuUnitPriceStandard) from ProductSkuEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",BigDecimal.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product sku: "+productEntity.getProductUuid());
		}
		return maxUnitPoint==null?new BigDecimal(0):maxUnitPoint;
	}

	@Override
	public int getMaxUnitPointByProduct(ProductEntity productEntity)
			throws DAOException {
		Integer maxUnitPoint = null;
		try {
			maxUnitPoint = em.createQuery("select max(b.skuUnitPoint) from ProductSkuEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",Integer.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product sku: "+productEntity.getProductUuid());
		}
		return maxUnitPoint==null?0:maxUnitPoint.intValue();
	}
	
	@Override
	public BigDecimal getMaxUnitPriceByProduct(ProductEntity productEntity)
			throws DAOException {
		BigDecimal maxUnitPoint = null;
		try {
			maxUnitPoint = em.createQuery("select max(b.skuUnitPrice) from ProductSkuEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",BigDecimal.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product sku: "+productEntity.getProductUuid());
		}
		return maxUnitPoint==null?new BigDecimal(0):maxUnitPoint;
	}
	
	@Override
	public int getMaxUnitPointStandardByProduct(ProductEntity productEntity)
			throws DAOException {
		Integer maxUnitPoint = null;
		try {
			maxUnitPoint = em.createQuery("select max(b.skuUnitPointStandard) from ProductSkuEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",Integer.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product sku: "+productEntity.getProductUuid());
		}
		return maxUnitPoint==null?0:maxUnitPoint.intValue();
	}
	
	@Override
	public BigDecimal getMaxUnitPriceStandardByProduct(ProductEntity productEntity)
			throws DAOException {
		BigDecimal maxUnitPoint = null;
		try {
			maxUnitPoint = em.createQuery("select max(b.skuUnitPriceStandard) from ProductSkuEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",BigDecimal.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product sku: "+productEntity.getProductUuid());
		}
		return maxUnitPoint==null?new BigDecimal(0):maxUnitPoint;
	}

}
