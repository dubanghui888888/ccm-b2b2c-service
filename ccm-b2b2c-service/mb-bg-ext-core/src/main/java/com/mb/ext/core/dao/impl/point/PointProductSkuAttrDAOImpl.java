package com.mb.ext.core.dao.impl.point;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.point.PointProductSkuAttrDAO;
import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.ext.core.entity.point.PointProductSkuAttrEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("pointProductSkuAttrDAO")
@Qualifier("pointProductSkuAttrDAO")
public class PointProductSkuAttrDAOImpl extends AbstractBaseDAO<PointProductSkuAttrEntity> implements
PointProductSkuAttrDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public PointProductSkuAttrDAOImpl() {
		super();
		this.setEntityClass(PointProductSkuAttrEntity.class);
	}

	@Override
	public void addProductAttr(PointProductSkuAttrEntity productAttrEntity) throws DAOException {
		
		save(productAttrEntity);
		
	}


	@Override
	public void deleteProductAttr(PointProductSkuAttrEntity productAttrEntity) throws DAOException {
		
		delete(productAttrEntity);
		
	}

	@Override
	public void updateProductAttr(PointProductSkuAttrEntity productAttrEntity) throws DAOException {
		
		update(productAttrEntity);
		
	}


	@Override
	public PointProductSkuAttrEntity getProductAttrByUuid(String uuid)
			throws DAOException {
		PointProductSkuAttrEntity productAttrEntity = null;
		try {
			productAttrEntity = (PointProductSkuAttrEntity)em.createQuery("select b from PointProductSkuAttrEntity b where  b.productSkuAttrUuid = :UUID",PointProductSkuAttrEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product : "+uuid);
		}
		return productAttrEntity;
	}

	@Override
	public List<PointProductSkuAttrEntity> getProductAttrByProduct(
			PointProductEntity productEntity) throws DAOException {
		List<PointProductSkuAttrEntity> productAttrEntityList = new ArrayList<PointProductSkuAttrEntity>();
		try {
			productAttrEntityList = em.createQuery("select b from PointProductSkuAttrEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",PointProductSkuAttrEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product attr: "+productEntity.getProductUuid());
		}
		return productAttrEntityList;
	}
	
}
