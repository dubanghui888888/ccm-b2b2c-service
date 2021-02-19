package com.mb.ext.core.dao.impl.product;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.product.SupplierProductDAO;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.SupplierProductEntity;
import com.mb.ext.core.entity.supplier.SupplierEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("supplierProductDAO")
@Qualifier("supplierProductDAO")
public class SupplierProductDAOImpl extends AbstractBaseDAO<SupplierProductEntity> implements SupplierProductDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public SupplierProductDAOImpl()
	{
		super();
		this.setEntityClass(SupplierProductEntity.class);
	}

	@Override
	public void createSupplierProduct(SupplierProductEntity supplierProductEntity)
			throws DAOException {
		save(supplierProductEntity);
		
	}

	@Override
	public void updateSupplierProduct(SupplierProductEntity supplierProductEntity)
			throws DAOException {
		update(supplierProductEntity);
		
	}

	@Override
	public void deleteSupplierProduct(SupplierProductEntity supplierProductEntity)
			throws DAOException {
		deletePhysical(supplierProductEntity);
		
	}

	@Override
	public List<ProductEntity> getProductsBySupplier(SupplierEntity supplierEntity) throws DAOException {
		List<ProductEntity> productEntityList = null;
		try {
			productEntityList = em.createQuery("select b.productEntity from SupplierProductEntity b where b.supplierEntity.supplierUuid = :UUID and  b.isDeleted=:isDeleted",ProductEntity.class).setParameter("UUID", supplierEntity.getSupplierUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for supplier product");
		}
		return productEntityList;
	}
	
	@Override
	public List<SupplierEntity> searchSupplierByProduct(ProductEntity productEntity, int startIndex, int pageSize) throws DAOException {
		List<SupplierEntity> supplierEntityList = null;
		try {
			supplierEntityList = em.createQuery("select b.supplierEntity from SupplierProductEntity b where b.productEntity.productUuid = :UUID and  b.isDeleted=:isDeleted",SupplierEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(startIndex)
					.setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for supplier");
		}
		return supplierEntityList;
	}
	
	@Override
	public int searchSupplierByProductTotal(ProductEntity productEntity) throws DAOException {
		Long total = null;
		try {
			total = em.createQuery("select count(b.supplierEntity) from SupplierProductEntity b where b.productEntity.productUuid = :UUID and  b.isDeleted=:isDeleted",Long.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for supplier");
		}
		return total==null?0:total.intValue();
	}
	
	@Override
	public SupplierProductEntity getSupplierProduct(SupplierEntity supplierEntity, ProductEntity productEntity) throws DAOException {
		SupplierProductEntity supplierProductEntity = null;
		try {
			supplierProductEntity = em.createQuery("select b from SupplierProductEntity b where b.supplierEntity.supplierUuid = :MERCHANTUUID and b.productEntity.productUuid = :PRODUCTUUID and b.isDeleted=:isDeleted",SupplierProductEntity.class).setParameter("MERCHANTUUID", supplierEntity.getSupplierUuid()).setParameter("PRODUCTUUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for supplier product");
		}
		return supplierProductEntity;
	}

}
