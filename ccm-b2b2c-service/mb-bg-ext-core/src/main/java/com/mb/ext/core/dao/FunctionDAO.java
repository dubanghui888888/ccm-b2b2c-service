package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.FunctionEntity;
import com.mb.framework.exception.DAOException;



public interface FunctionDAO
{
	void addFunction(FunctionEntity functionEntity) throws DAOException;
	
	FunctionEntity getFunctionByCode(String code)  throws DAOException;
	
	List<FunctionEntity> getFunctions() throws DAOException;
	
}
