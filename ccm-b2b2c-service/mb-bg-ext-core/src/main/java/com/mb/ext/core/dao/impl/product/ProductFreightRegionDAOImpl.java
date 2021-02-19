package com.mb.ext.core.dao.impl.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.product.ProductFreightRegionDAO;
import com.mb.ext.core.entity.product.ProductFreightEntity;
import com.mb.ext.core.entity.product.ProductFreightRegionEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("productFreightRegionDAO")
@Qualifier("productFreightRegionDAO")
public class ProductFreightRegionDAOImpl extends AbstractBaseDAO<ProductFreightRegionEntity> implements ProductFreightRegionDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public ProductFreightRegionDAOImpl()
	{
		super();
		this.setEntityClass(ProductFreightRegionEntity.class);
	}

	@Override
	public void createProductFreightRegion(ProductFreightRegionEntity productFreightRegionEntity)
			throws DAOException {
		save(productFreightRegionEntity);
		
	}

	@Override
	public void updateProductFreightRegion(ProductFreightRegionEntity productFreightRegionEntity)
			throws DAOException {
		update(productFreightRegionEntity);
		
	}

	@Override
	public void deleteProductFreightRegion(ProductFreightRegionEntity productFreightRegionEntity)
			throws DAOException {
		deletePhysical(productFreightRegionEntity);
		
	}

	@Override
	public List<ProductFreightRegionEntity> getRegionsByFreight(ProductFreightEntity freightEntity) throws DAOException {
		List<ProductFreightRegionEntity> regionEntityList = new ArrayList<ProductFreightRegionEntity>();;
		try {
			regionEntityList = em.createQuery("select b from ProductFreightRegionEntity b where b.productFreightEntity.productFreightUuid = :UUID and  b.isDeleted=:isDeleted",ProductFreightRegionEntity.class).setParameter("UUID", freightEntity.getProductFreightUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return regionEntityList;
	}
	
	@Override
	public List<ProductFreightRegionEntity> getRegionsByFreightAreaId(ProductFreightEntity freightEntity, String areaId) throws DAOException {
		List<ProductFreightRegionEntity> regionEntityList = new ArrayList<ProductFreightRegionEntity>();;
		try {
			regionEntityList = em.createQuery("select b from ProductFreightRegionEntity b where b.productFreightEntity.productFreightUuid = :UUID and b.areaIds like :AREAIDS and  b.isDeleted=:isDeleted",ProductFreightRegionEntity.class).setParameter("UUID", freightEntity.getProductFreightUuid()).setParameter("AREAIDS", "%"+areaId+"%").setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return regionEntityList;
	}
	
	@Override
	public ProductFreightRegionEntity getRegionByUuid(String uuid) throws DAOException {
		ProductFreightRegionEntity region = null;
		try {
			region = em.createQuery("select b from ProductFreightRegionEntity b where b.productFreightRegionUuid = :UUID",ProductFreightRegionEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return region;
	}

}
