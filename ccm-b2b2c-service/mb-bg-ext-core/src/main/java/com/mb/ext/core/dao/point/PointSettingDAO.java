package com.mb.ext.core.dao.point;

import com.mb.ext.core.entity.point.PointSettingEntity;
import com.mb.framework.exception.DAOException;


public interface PointSettingDAO
{
	void addPointSetting(PointSettingEntity pointSettingEntity) throws DAOException;
	
	void updatePointSetting(PointSettingEntity pointSettingEntity) throws DAOException;
	
	public PointSettingEntity getPointSetting() throws DAOException;
}
