package com.mb.ext.core.dao.impl.point;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.point.PointProductCateAttrDAO;
import com.mb.ext.core.entity.point.PointProductCateAttrEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("pointProductCateAttrDAO")
@Qualifier("pointProductCateAttrDAO")
public class PointProductCateAttrDAOImpl extends AbstractBaseDAO<PointProductCateAttrEntity> implements
PointProductCateAttrDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public PointProductCateAttrDAOImpl() {
		super();
		this.setEntityClass(PointProductCateAttrEntity.class);
	}

	@Override
	public void addProductCateAttr(PointProductCateAttrEntity productCateAttrEntity) throws DAOException {
		
		save(productCateAttrEntity);
		
	}


	@Override
	public void deleteProductCateAttr(PointProductCateAttrEntity productCateAttrEntity) throws DAOException {
		
		deletePhysical(productCateAttrEntity);
		
	}

	@Override
	public void updateProductCateAttr(PointProductCateAttrEntity productCateAttrEntity) throws DAOException {
		
		update(productCateAttrEntity);
		
	}


	@Override
	public PointProductCateAttrEntity getProductCateAttrByUuid(String uuid)
			throws DAOException {
		PointProductCateAttrEntity productCateAttrEntity = null;
		try {
			productCateAttrEntity = (PointProductCateAttrEntity)em.createQuery("select b from PointProductCateAttrEntity b where  b.productCateAttrUuid = :UUID",PointProductCateAttrEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product cate: "+uuid);
		}
		return productCateAttrEntity;
	}

}
