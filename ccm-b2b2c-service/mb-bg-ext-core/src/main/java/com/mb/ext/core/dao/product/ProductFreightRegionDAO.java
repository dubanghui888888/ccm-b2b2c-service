package com.mb.ext.core.dao.product;

import java.util.List;

import com.mb.ext.core.entity.product.ProductFreightEntity;
import com.mb.ext.core.entity.product.ProductFreightRegionEntity;
import com.mb.framework.exception.DAOException;



public interface ProductFreightRegionDAO
{
	void createProductFreightRegion(ProductFreightRegionEntity productFreightRegionEntity) throws DAOException;
	
	void updateProductFreightRegion(ProductFreightRegionEntity productFreightRegionEntity) throws DAOException;
	
	void deleteProductFreightRegion(ProductFreightRegionEntity productFreightRegionEntity) throws DAOException;
	
	List<ProductFreightRegionEntity> getRegionsByFreight(ProductFreightEntity freightEntity) throws DAOException;
	
	List<ProductFreightRegionEntity> getRegionsByFreightAreaId(ProductFreightEntity freightEntity, String areaId) throws DAOException;
	
	ProductFreightRegionEntity getRegionByUuid(String uuid) throws DAOException;
	
}
