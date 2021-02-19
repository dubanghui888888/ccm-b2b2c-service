package com.mb.ext.core.dao;

import com.mb.ext.core.entity.PrintSettingEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.framework.exception.DAOException;



public interface PrintSettingDAO
{
	void addPrintSetting(PrintSettingEntity printSettingEntity) throws DAOException;
	
	void updatePrintSetting(PrintSettingEntity printSettingEntity) throws DAOException;
	
	public PrintSettingEntity getPrintSetting() throws DAOException;
	
	public PrintSettingEntity getPrintSettingByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	public PrintSettingEntity getPrintSettingByUuid(String printSettingUuid) throws DAOException;
}
