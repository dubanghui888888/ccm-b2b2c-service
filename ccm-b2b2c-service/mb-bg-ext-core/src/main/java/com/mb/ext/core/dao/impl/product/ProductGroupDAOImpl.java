package com.mb.ext.core.dao.impl.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.product.ProductGroupDAO;
import com.mb.ext.core.entity.supplier.SupplierEntity;
import com.mb.ext.core.entity.product.ProductGroupEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("productGroupDAO")
@Qualifier("productGroupDAO")
public class ProductGroupDAOImpl extends AbstractBaseDAO<ProductGroupEntity> implements ProductGroupDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public ProductGroupDAOImpl()
	{
		super();
		this.setEntityClass(ProductGroupEntity.class);
	}

	@Override
	public void createProductGroup(ProductGroupEntity productGroupEntity)
			throws DAOException {
		save(productGroupEntity);
		
	}

	@Override
	public void updateProductGroup(ProductGroupEntity productGroupEntity)
			throws DAOException {
		update(productGroupEntity);
		
	}

	@Override
	public void deleteProductGroup(ProductGroupEntity productGroupEntity)
			throws DAOException {
		deletePhysical(productGroupEntity);
		
	}

	@Override
	public List<ProductGroupEntity> getGroupBySupplier(SupplierEntity supplierEntity) throws DAOException {
		List<ProductGroupEntity> groupEntityList = new ArrayList<ProductGroupEntity>();;
		try {
			groupEntityList = em.createQuery("select b from ProductGroupEntity b where b.supplierEntity.supplierUuid = :UUID and b.isDeleted=:isDeleted",ProductGroupEntity.class).setParameter("UUID", supplierEntity.getSupplierUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for supplier industry");
		}
		return groupEntityList;
	}

	@Override
	public ProductGroupEntity getGroupByUuid(String uuid) throws DAOException {
		ProductGroupEntity groupEntity = new ProductGroupEntity();
		try {
			groupEntity = (ProductGroupEntity)em.createQuery("select b from ProductGroupEntity b where b.productGroupUuid = :UUID",ProductGroupEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for supplier industry");
		}
		return groupEntity;
	}
	
	@Override
	public ProductGroupEntity getGroupByName(String name) throws DAOException {
		ProductGroupEntity groupEntity = null;
		try {
			groupEntity = (ProductGroupEntity)em.createQuery("select b from ProductGroupEntity b where b.name = :NAME and b.isDeleted=:isDeleted",ProductGroupEntity.class).setParameter("NAME", name).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for supplier industry");
		}
		return groupEntity;
	}

}
