package com.mb.ext.core.dao.impl.point;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.point.PointProductCollectDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.point.PointProductCollectEntity;
import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("pointProductCollectDAO")
@Qualifier("pointProductCollectDAO")
public class PointProductCollectDAOImpl extends AbstractBaseDAO<PointProductCollectEntity> implements
PointProductCollectDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public PointProductCollectDAOImpl() {
		super();
		this.setEntityClass(PointProductCollectEntity.class);
	}

	@Override
	public void addProductCollect(PointProductCollectEntity productCollectEntity) throws DAOException {
		
		save(productCollectEntity);
		
	}


	@Override
	public void deleteProductCollect(PointProductCollectEntity productCollectEntity) throws DAOException {
		
		deletePhysical(productCollectEntity);
		
	}

	@Override
	public List<PointProductCollectEntity> getProductCollectByUser(
			UserEntity userEntity) throws DAOException {
		List<PointProductCollectEntity> productCollectEntityList = new ArrayList<PointProductCollectEntity>();
		try {
			productCollectEntityList = em.createQuery("select b from PointProductCollectEntity b where  b.userEntity.userUuid = :UUID and b.isDeleted=:isDeleted",PointProductCollectEntity.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product collect: "+userEntity.getUserUuid());
		}
		return productCollectEntityList;
	}
	
	@Override
	public PointProductCollectEntity getProductCollectByUserProduct(
			UserEntity userEntity, PointProductEntity productEntity) throws DAOException {
		PointProductCollectEntity productCollectEntity = null;
		try {
			productCollectEntity = em.createQuery("select b from PointProductCollectEntity b where b.productEntity.productUuid = :PRODUCTUUID and  b.userEntity.userUuid = :USERUUID and b.isDeleted=:isDeleted",PointProductCollectEntity.class).setParameter("USERUUID", userEntity.getUserUuid()).setParameter("PRODUCTUUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product collect: "+userEntity.getUserUuid());
		}
		return productCollectEntity;
	}

}
