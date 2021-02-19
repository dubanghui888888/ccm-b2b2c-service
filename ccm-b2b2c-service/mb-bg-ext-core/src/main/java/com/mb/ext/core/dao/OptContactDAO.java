package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.OptContactEntity;
import com.mb.framework.exception.DAOException;



public interface OptContactDAO
{
	void addOptContact(OptContactEntity optContactEntity) throws DAOException;
	
	void updateOptContact(OptContactEntity optContactEntity) throws DAOException;
	
	void deleteOptContact(OptContactEntity optContactEntity) throws DAOException;
	
	public OptContactEntity getOptContactByTypeAndNo(String contactType, String contactNo) throws DAOException;
	
	public List<OptContactEntity> getOptContacts() throws DAOException;
}
