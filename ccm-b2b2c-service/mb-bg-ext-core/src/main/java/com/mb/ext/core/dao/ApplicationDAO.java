package com.mb.ext.core.dao;

import com.mb.ext.core.entity.ApplicationEntity;
import com.mb.framework.exception.DAOException;



public interface ApplicationDAO
{
	void addApplication(ApplicationEntity applicationEntity) throws DAOException;
}
