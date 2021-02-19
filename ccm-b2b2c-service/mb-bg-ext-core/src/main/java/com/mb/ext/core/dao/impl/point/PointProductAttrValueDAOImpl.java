package com.mb.ext.core.dao.impl.point;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.point.PointProductAttrValueDAO;
import com.mb.ext.core.entity.point.PointProductAttrValueEntity;
import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("pointProductAttrValueDAO")
@Qualifier("pointProductAttrValueDAO")
public class PointProductAttrValueDAOImpl extends AbstractBaseDAO<PointProductAttrValueEntity> implements
PointProductAttrValueDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public PointProductAttrValueDAOImpl() {
		super();
		this.setEntityClass(PointProductAttrValueEntity.class);
	}

	@Override
	public void addProductAttr(PointProductAttrValueEntity productAttrEntity) throws DAOException {
		
		save(productAttrEntity);
		
	}


	@Override
	public void deleteProductAttr(PointProductAttrValueEntity productAttrEntity) throws DAOException {
		
		delete(productAttrEntity);
		
	}

	@Override
	public void updateProductAttr(PointProductAttrValueEntity productAttrEntity) throws DAOException {
		
		update(productAttrEntity);
		
	}


	@Override
	public PointProductAttrValueEntity getProductAttrByUuid(String uuid)
			throws DAOException {
		PointProductAttrValueEntity productAttrValueEntity = null;
		try {
			productAttrValueEntity = (PointProductAttrValueEntity)em.createQuery("select b from PointProductAttrValueEntity b where  b.productAttrValueUuid = :UUID",PointProductAttrValueEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product : "+uuid);
		}
		return productAttrValueEntity;
	}

	@Override
	public List<PointProductAttrValueEntity> getProductAttrByProduct(
			PointProductEntity productEntity) throws DAOException {
		List<PointProductAttrValueEntity> productAttrEntityList = new ArrayList<PointProductAttrValueEntity>();
		try {
			productAttrEntityList = em.createQuery("select b from PointProductAttrValueEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",PointProductAttrValueEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product attr: "+productEntity.getProductUuid());
		}
		return productAttrEntityList;
	}
	
}
