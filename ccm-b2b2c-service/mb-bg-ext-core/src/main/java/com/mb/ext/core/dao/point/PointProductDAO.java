package com.mb.ext.core.dao.point;

import java.util.List;

import com.mb.ext.core.entity.point.PointProductCateEntity;
import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.ext.core.entity.product.ProductBrandEntity;
import com.mb.ext.core.entity.supplier.SupplierEntity;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.framework.exception.DAOException;



public interface PointProductDAO
{
	
	void addProduct(PointProductEntity productEntity) throws DAOException;
	
	void updateProduct(PointProductEntity productEntity) throws DAOException;
	
	void deleteProduct(PointProductEntity productEntity) throws DAOException;
	
	PointProductEntity getProductByUuid(String uuid) throws DAOException;
	
	List<PointProductEntity> getProductByCate(PointProductCateEntity cateEntity) throws DAOException;
	
	List<PointProductEntity> getProductByType(String productType) throws DAOException;
	
	List<PointProductEntity> searchProduct(ProductSearchDTO searchDTO) throws DAOException;
	
	int searchProductTotal(ProductSearchDTO searchDTO) throws DAOException;
	
	int getSoldUnitTotalBySupplier(SupplierEntity supplierEntity)throws DAOException;
	
	void deleteProductByBrand(ProductBrandEntity productBrandEntity)throws DAOException;
	
}
