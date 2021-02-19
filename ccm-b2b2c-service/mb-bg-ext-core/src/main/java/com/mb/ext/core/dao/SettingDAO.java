package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.SettingEntity;
import com.mb.framework.exception.DAOException;



public interface SettingDAO
{
	void addSetting(SettingEntity settingEntity) throws DAOException;
	
	void updateSetting(SettingEntity settingEntity) throws DAOException;
	
	public SettingEntity getSettingByName(String name) throws DAOException;
	
	public List<SettingEntity> getSettings() throws DAOException;
}
