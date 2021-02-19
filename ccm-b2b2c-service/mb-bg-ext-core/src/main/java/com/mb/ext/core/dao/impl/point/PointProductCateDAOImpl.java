package com.mb.ext.core.dao.impl.point;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.point.PointProductCateDAO;
import com.mb.ext.core.entity.point.PointProductCateEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("pointProductCateDAO")
@Qualifier("pointProductCateDAO")
public class PointProductCateDAOImpl extends AbstractBaseDAO<PointProductCateEntity> implements
PointProductCateDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public PointProductCateDAOImpl() {
		super();
		this.setEntityClass(PointProductCateEntity.class);
	}


	@Override
	public void addProductCate(PointProductCateEntity productCateEntity) throws DAOException {
		
		save(productCateEntity);
		
	}


	@Override
	public void deleteProductCate(PointProductCateEntity productCateEntity) throws DAOException {
		
		deletePhysical(productCateEntity);
		
	}

	@Override
	public void updateProductCate(PointProductCateEntity productCateEntity) throws DAOException {
		
		update(productCateEntity);
		
	}

	@Override
	public PointProductCateEntity getProductCateByUuid(String uuid)
			throws DAOException {
		PointProductCateEntity productCateEntity = null;
		try {
			productCateEntity = (PointProductCateEntity)em.createQuery("select b from PointProductCateEntity b where  b.productCateUuid = :UUID",PointProductCateEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product cate: "+uuid);
		}
		return productCateEntity;
	}
	
	@Override
	public List<PointProductCateEntity> getRootProductCate()
			throws DAOException {
		List<PointProductCateEntity> productCateEntityList = null;
		try {
			productCateEntityList = em.createQuery("select b from PointProductCateEntity b where  b.parentCateEntity IS NULL and b.isDeleted=:isDeleted order by b.sortNumber",PointProductCateEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product cate: ");
		}
		return productCateEntityList;
	}
	
	@Override
	public List<PointProductCateEntity> getHomeProductCate()
			throws DAOException {
		List<PointProductCateEntity> productCateEntityList = null;
		try {
			productCateEntityList = em.createQuery("select b from PointProductCateEntity b where  b.isDisplayedHome = :ISDISPLAYEDHOME and b.isDeleted=:isDeleted order by b.sortNumber",PointProductCateEntity.class).setParameter("ISDISPLAYEDHOME", Boolean.TRUE).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product cate: ");
		}
		return productCateEntityList;
	}
	
	@Override
	public List<PointProductCateEntity> getLeafProductCate()
			throws DAOException {
		List<PointProductCateEntity> productCateEntityList = null;
		try {
			productCateEntityList = em.createQuery("select b from PointProductCateEntity b where  b.isLeaf = :ISLEAF and b.isDeleted=:isDeleted",PointProductCateEntity.class).setParameter("ISLEAF", true).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product cate: ");
		}
		return productCateEntityList;
	}
	
	@Override
	public List<PointProductCateEntity> getChildProductCate(PointProductCateEntity productCateEntity)
			throws DAOException {
		List<PointProductCateEntity> productCateEntityList = null;
		try {
			productCateEntityList = em.createQuery("select b from PointProductCateEntity b where  b.parentCateEntity.productCateUuid = :UUID and b.isDeleted=:isDeleted order by b.sortNumber",PointProductCateEntity.class).setParameter("UUID", productCateEntity.getProductCateUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product cate: "+productCateEntity.getProductCateUuid());
		}
		return productCateEntityList;
	}

}
