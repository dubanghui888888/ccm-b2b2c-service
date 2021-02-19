package com.mb.ext.core.dao.point;

import com.mb.ext.core.entity.point.SignSettingEntity;
import com.mb.framework.exception.DAOException;



public interface SignSettingDAO
{
	void addSignSetting(SignSettingEntity signSettingEntity) throws DAOException;
	
	void updateSignSetting(SignSettingEntity signSettingEntity) throws DAOException;
	
	public SignSettingEntity getSignSetting() throws DAOException;
}
