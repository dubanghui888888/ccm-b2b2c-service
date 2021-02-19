package com.mb.ext.core.dao;

import com.mb.ext.core.entity.GlobalSettingEntity;
import com.mb.framework.exception.DAOException;



public interface GlobalSettingDAO
{
	void addGlobalSetting(GlobalSettingEntity globalSettingEntity) throws DAOException;
	
	void updateGlobalSetting(GlobalSettingEntity globalSettingEntity) throws DAOException;
	
	public GlobalSettingEntity getGlobalSetting() throws DAOException;
	
}
