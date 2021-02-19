package com.mb.ext.core.dao;

import java.util.Date;
import java.util.List;

import com.mb.ext.core.entity.supplier.SupplierEntity;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.SupplierSearchDTO;
import com.mb.framework.exception.DAOException;


public interface SupplierDAO
{
	/**
	 * 
	 * This method is used to add supplier to enterprise.
	 * 
	 * @param supplierEntity
	 */
	void addSupplier(SupplierEntity supplierEntity) throws DAOException;
	
	void updateSupplier(SupplierEntity supplierEntity) throws DAOException;
	
	void deleteSupplier(SupplierEntity supplierEntity) throws DAOException;
	
	void closeSupplier(SupplierEntity supplierEntity) throws DAOException;
	
	public SupplierEntity getSupplierByMobileNo(String mobileNo) throws DAOException;
	
	public SupplierEntity getSupplierByUuid(String uuid) throws DAOException;
	
	public List<SupplierEntity> getSuppliers() throws DAOException;
	
	public SupplierEntity getSupplierByTokenId(String tokenId) throws DAOException;
	
	List<SupplierEntity> searchSuppliers(SupplierSearchDTO supplierSearchDTO) throws DAOException;
	
	List<Object> searchSupplierSoldUnit(SupplierSearchDTO supplierSearchDTO) throws DAOException;
	
	int searchSupplierTotal(SupplierSearchDTO supplierSearchDTO) throws DAOException;
	
	public int getIncrementSupplierCountByDate(Date startDate, Date endDate) throws DAOException;
	
	List<ChartDTO> getIncrementSupplierChart(Date startDate, Date endDate) throws DAOException;
	
	
}
