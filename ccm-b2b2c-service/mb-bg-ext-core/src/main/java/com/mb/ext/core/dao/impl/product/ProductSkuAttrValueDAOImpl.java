package com.mb.ext.core.dao.impl.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.product.ProductSkuAttrValueDAO;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductSkuAttrEntity;
import com.mb.ext.core.entity.product.ProductSkuAttrValueEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("productSkuAttrValueDAO")
@Qualifier("productSkuAttrValueDAO")
public class ProductSkuAttrValueDAOImpl extends AbstractBaseDAO<ProductSkuAttrValueEntity> implements
		ProductSkuAttrValueDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public ProductSkuAttrValueDAOImpl() {
		super();
		this.setEntityClass(ProductSkuAttrValueEntity.class);
	}

	@Override
	public void addProductAttrValue(ProductSkuAttrValueEntity productAttrEntity) throws DAOException {
		
		save(productAttrEntity);
		
	}


	@Override
	public void deleteProductAttrValue(ProductSkuAttrValueEntity productAttrEntity) throws DAOException {
		
		delete(productAttrEntity);
		
	}

	@Override
	public void updateProductAttrValue(ProductSkuAttrValueEntity productAttrEntity) throws DAOException {
		
		update(productAttrEntity);
		
	}


	@Override
	public ProductSkuAttrValueEntity getProductSkuAttrValueByUuid(String uuid)
			throws DAOException {
		ProductSkuAttrValueEntity productAttrEntity = null;
		try {
			productAttrEntity = (ProductSkuAttrValueEntity)em.createQuery("select b from ProductSkuAttrValueEntity b where  b.productSkuAttrValueUuid = :UUID",ProductSkuAttrValueEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product : "+uuid);
		}
		return productAttrEntity;
	}
	
	@Override
	public ProductSkuAttrValueEntity getProductSkuAttrValuesByNameValue(ProductEntity productEntity,String name, String value)
			throws DAOException {
		ProductSkuAttrValueEntity productAttrEntity = null;
		try {
			productAttrEntity = (ProductSkuAttrValueEntity)em.createQuery("select b from ProductSkuAttrValueEntity b where b.productEntity.productUuid = :UUID and b.productSkuAttrEntity.skuAttrName = :NAME and b.skuAttrValue = :VALUE and b.isDeleted=:isDeleted",ProductSkuAttrValueEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("NAME", name).setParameter("VALUE", value).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for sku attr name value : "+name+":"+value);
		}
		return productAttrEntity;
	}
	
	@Override
	public ProductSkuAttrValueEntity getProductSkuAttrValuesByAttrNameUuidAndValue(ProductEntity productEntity,String uuid, String value)
			throws DAOException {
		ProductSkuAttrValueEntity productAttrEntity = null;
		try {
			productAttrEntity = (ProductSkuAttrValueEntity)em.createQuery("select b from ProductSkuAttrValueEntity b where b.productEntity.productUuid = :PRODUCTUUID and  b.productSkuAttrEntity.productSkuAttrUuid = :UUID and b.skuAttrValue = :VALUE and b.isDeleted=:isDeleted",ProductSkuAttrValueEntity.class).setParameter("PRODUCTUUID", productEntity.getProductUuid()).setParameter("UUID", uuid).setParameter("VALUE", value).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for sku attr name value : "+uuid+":"+value);
		}
		return productAttrEntity;
	}

	@Override
	public List<ProductSkuAttrValueEntity> getProductSkuAttrValuesByAttr(
			ProductSkuAttrEntity attrEntity) throws DAOException {
		List<ProductSkuAttrValueEntity> productAttrEntityList = new ArrayList<ProductSkuAttrValueEntity>();
		try {
			productAttrEntityList = em.createQuery("select b from ProductSkuAttrValueEntity b where  b.productSkuAttrEntity.productSkuAttrUuid = :UUID and b.isDeleted=:isDeleted",ProductSkuAttrValueEntity.class).setParameter("UUID", attrEntity.getProductSkuAttrUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product attr: "+attrEntity.getProductSkuAttrUuid());
		}
		return productAttrEntityList;
	}
	
	@Override
	public List<ProductSkuAttrValueEntity> getProductSkuAttrValuesByProduct(
			ProductEntity productEntity) throws DAOException {
		List<ProductSkuAttrValueEntity> productAttrEntityList = new ArrayList<ProductSkuAttrValueEntity>();
		try {
			productAttrEntityList = em.createQuery("select b from ProductSkuAttrValueEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",ProductSkuAttrValueEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product attr: "+productEntity.getProductUuid());
		}
		return productAttrEntityList;
	}
	
}
