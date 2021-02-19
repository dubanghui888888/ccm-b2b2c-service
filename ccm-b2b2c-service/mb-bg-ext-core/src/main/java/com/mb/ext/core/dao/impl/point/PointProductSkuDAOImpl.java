package com.mb.ext.core.dao.impl.point;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.point.PointProductSkuDAO;
import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.ext.core.entity.point.PointProductSkuEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("pointProductSkuDAO")
@Qualifier("pointProductSkuDAO")
public class PointProductSkuDAOImpl extends AbstractBaseDAO<PointProductSkuEntity> implements
PointProductSkuDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public PointProductSkuDAOImpl() {
		super();
		this.setEntityClass(PointProductSkuEntity.class);
	}

	@Override
	public void addProductSku(PointProductSkuEntity productSkuEntity) throws DAOException {
		
		save(productSkuEntity);
		
	}


	@Override
	public void deleteProductSku(PointProductSkuEntity productSkuEntity) throws DAOException {
		
		delete(productSkuEntity);
		
	}

	@Override
	public void updateProductSku(PointProductSkuEntity productSkuEntity) throws DAOException {
		
		update(productSkuEntity);
		
	}


	@Override
	public PointProductSkuEntity getProductSkuByUuid(String uuid)
			throws DAOException {
		PointProductSkuEntity productSkuEntity = null;
		try {
			productSkuEntity = (PointProductSkuEntity)em.createQuery("select b from PointProductSkuEntity b where  b.productSkuUuid = :UUID",PointProductSkuEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product : "+uuid);
		}
		return productSkuEntity;
	}
	
	@Override
	public PointProductSkuEntity getProductSkuByAttrValueUuids(PointProductEntity productEntity, String skuAttrValueUuids)
			throws DAOException {
		PointProductSkuEntity productSkuEntity = null;
		try {
			productSkuEntity = (PointProductSkuEntity)em.createQuery("select b from PointProductSkuEntity b where  b.skuAttrValueUuids = :UUID and b.productEntity.productUuid = :PRODUCTUUID and b.isDeleted=:isDeleted",PointProductSkuEntity.class).setParameter("PRODUCTUUID", productEntity.getProductUuid()).setParameter("UUID", skuAttrValueUuids).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product : "+skuAttrValueUuids);
		}
		return productSkuEntity;
	}

	@Override
	public List<PointProductSkuEntity> getProductSkuByProduct(
			PointProductEntity productEntity) throws DAOException {
		List<PointProductSkuEntity> productSkuEntityList = new ArrayList<PointProductSkuEntity>();
		try {
			productSkuEntityList = em.createQuery("select b from PointProductSkuEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",PointProductSkuEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product sku: "+productEntity.getProductUuid());
		}
		return productSkuEntityList;
	}

	@Override
	public int getMinUnitPointByProduct(PointProductEntity productEntity)
			throws DAOException {
		Integer maxUnitPoint = null;
		try {
			maxUnitPoint = em.createQuery("select min(b.skuUnitPoint) from PointProductSkuEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",Integer.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product sku: "+productEntity.getProductUuid());
		}
		return maxUnitPoint==null?0:maxUnitPoint.intValue();
	}
	
	@Override
	public BigDecimal getMinUnitPriceByProduct(PointProductEntity productEntity)
			throws DAOException {
		BigDecimal maxUnitPoint = null;
		try {
			maxUnitPoint = em.createQuery("select min(b.skuUnitPrice) from PointProductSkuEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",BigDecimal.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product sku: "+productEntity.getProductUuid());
		}
		return maxUnitPoint==null?new BigDecimal(0):maxUnitPoint;
	}
	
	@Override
	public int getMinUnitPointStandardByProduct(PointProductEntity productEntity)
			throws DAOException {
		Integer maxUnitPoint = null;
		try {
			maxUnitPoint = em.createQuery("select min(b.skuUnitPointStandard) from PointProductSkuEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",Integer.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product sku: "+productEntity.getProductUuid());
		}
		return maxUnitPoint==null?0:maxUnitPoint.intValue();
	}
	
	@Override
	public BigDecimal getMinUnitPriceStandardByProduct(PointProductEntity productEntity)
			throws DAOException {
		BigDecimal maxUnitPoint = null;
		try {
			maxUnitPoint = em.createQuery("select min(b.skuUnitPriceStandard) from PointProductSkuEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",BigDecimal.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product sku: "+productEntity.getProductUuid());
		}
		return maxUnitPoint==null?new BigDecimal(0):maxUnitPoint;
	}

	@Override
	public int getMaxUnitPointByProduct(PointProductEntity productEntity)
			throws DAOException {
		Integer maxUnitPoint = null;
		try {
			maxUnitPoint = em.createQuery("select max(b.skuUnitPoint) from PointProductSkuEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",Integer.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product sku: "+productEntity.getProductUuid());
		}
		return maxUnitPoint==null?0:maxUnitPoint.intValue();
	}
	
	@Override
	public BigDecimal getMaxUnitPriceByProduct(PointProductEntity productEntity)
			throws DAOException {
		BigDecimal maxUnitPoint = null;
		try {
			maxUnitPoint = em.createQuery("select max(b.skuUnitPrice) from PointProductSkuEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",BigDecimal.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product sku: "+productEntity.getProductUuid());
		}
		return maxUnitPoint==null?new BigDecimal(0):maxUnitPoint;
	}
	
	@Override
	public int getMaxUnitPointStandardByProduct(PointProductEntity productEntity)
			throws DAOException {
		Integer maxUnitPoint = null;
		try {
			maxUnitPoint = em.createQuery("select max(b.skuUnitPointStandard) from PointProductSkuEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",Integer.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product sku: "+productEntity.getProductUuid());
		}
		return maxUnitPoint==null?0:maxUnitPoint.intValue();
	}
	
	@Override
	public BigDecimal getMaxUnitPriceStandardByProduct(PointProductEntity productEntity)
			throws DAOException {
		BigDecimal maxUnitPoint = null;
		try {
			maxUnitPoint = em.createQuery("select max(b.skuUnitPriceStandard) from PointProductSkuEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",BigDecimal.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product sku: "+productEntity.getProductUuid());
		}
		return maxUnitPoint==null?new BigDecimal(0):maxUnitPoint;
	}

}
