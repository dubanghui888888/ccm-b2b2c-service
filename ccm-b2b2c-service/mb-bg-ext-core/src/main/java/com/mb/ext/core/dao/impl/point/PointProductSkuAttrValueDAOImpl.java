package com.mb.ext.core.dao.impl.point;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.point.PointProductSkuAttrValueDAO;
import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.ext.core.entity.point.PointProductSkuAttrEntity;
import com.mb.ext.core.entity.point.PointProductSkuAttrValueEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("pointProductSkuAttrValueDAO")
@Qualifier("pointProductSkuAttrValueDAO")
public class PointProductSkuAttrValueDAOImpl extends AbstractBaseDAO<PointProductSkuAttrValueEntity> implements
PointProductSkuAttrValueDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public PointProductSkuAttrValueDAOImpl() {
		super();
		this.setEntityClass(PointProductSkuAttrValueEntity.class);
	}

	@Override
	public void addProductAttrValue(PointProductSkuAttrValueEntity productAttrEntity) throws DAOException {
		
		save(productAttrEntity);
		
	}


	@Override
	public void deleteProductAttrValue(PointProductSkuAttrValueEntity productAttrEntity) throws DAOException {
		
		delete(productAttrEntity);
		
	}

	@Override
	public void updateProductAttrValue(PointProductSkuAttrValueEntity productAttrEntity) throws DAOException {
		
		update(productAttrEntity);
		
	}


	@Override
	public PointProductSkuAttrValueEntity getProductSkuAttrValueByUuid(String uuid)
			throws DAOException {
		PointProductSkuAttrValueEntity productAttrEntity = null;
		try {
			productAttrEntity = (PointProductSkuAttrValueEntity)em.createQuery("select b from PointProductSkuAttrValueEntity b where  b.productSkuAttrValueUuid = :UUID",PointProductSkuAttrValueEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product : "+uuid);
		}
		return productAttrEntity;
	}
	
	@Override
	public PointProductSkuAttrValueEntity getProductSkuAttrValuesByNameValue(PointProductEntity productEntity,String name, String value)
			throws DAOException {
		PointProductSkuAttrValueEntity productAttrEntity = null;
		try {
			productAttrEntity = (PointProductSkuAttrValueEntity)em.createQuery("select b from PointProductSkuAttrValueEntity b where b.productEntity.productUuid = :UUID and b.productSkuAttrEntity.skuAttrName = :NAME and b.skuAttrValue = :VALUE and b.isDeleted=:isDeleted",PointProductSkuAttrValueEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("NAME", name).setParameter("VALUE", value).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for sku attr name value : "+name+":"+value);
		}
		return productAttrEntity;
	}
	
	@Override
	public PointProductSkuAttrValueEntity getProductSkuAttrValuesByAttrNameUuidAndValue(PointProductEntity productEntity,String uuid, String value)
			throws DAOException {
		PointProductSkuAttrValueEntity productAttrEntity = null;
		try {
			productAttrEntity = (PointProductSkuAttrValueEntity)em.createQuery("select b from PointProductSkuAttrValueEntity b where b.productEntity.productUuid = :PRODUCTUUID and  b.productSkuAttrEntity.productSkuAttrUuid = :UUID and b.skuAttrValue = :VALUE and b.isDeleted=:isDeleted",PointProductSkuAttrValueEntity.class).setParameter("PRODUCTUUID", productEntity.getProductUuid()).setParameter("UUID", uuid).setParameter("VALUE", value).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for sku attr name value : "+uuid+":"+value);
		}
		return productAttrEntity;
	}

	@Override
	public List<PointProductSkuAttrValueEntity> getProductSkuAttrValuesByAttr(
			PointProductSkuAttrEntity attrEntity) throws DAOException {
		List<PointProductSkuAttrValueEntity> productAttrEntityList = new ArrayList<PointProductSkuAttrValueEntity>();
		try {
			productAttrEntityList = em.createQuery("select b from PointProductSkuAttrValueEntity b where  b.productSkuAttrEntity.productSkuAttrUuid = :UUID and b.isDeleted=:isDeleted",PointProductSkuAttrValueEntity.class).setParameter("UUID", attrEntity.getProductSkuAttrUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product attr: "+attrEntity.getProductSkuAttrUuid());
		}
		return productAttrEntityList;
	}
	
	@Override
	public List<PointProductSkuAttrValueEntity> getProductSkuAttrValuesByProduct(
			PointProductEntity productEntity) throws DAOException {
		List<PointProductSkuAttrValueEntity> productAttrEntityList = new ArrayList<PointProductSkuAttrValueEntity>();
		try {
			productAttrEntityList = em.createQuery("select b from PointProductSkuAttrValueEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",PointProductSkuAttrValueEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product attr: "+productEntity.getProductUuid());
		}
		return productAttrEntityList;
	}
	
}
