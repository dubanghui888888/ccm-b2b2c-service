package com.mb.ext.core.dao;

import java.util.Date;

import com.mb.framework.exception.DAOException;


public interface SequenceDAO
{
	/**
	 * 
	 * This method is used to get next number by sequence name and enterprise id.
	 * 
	 * @param name
	 */
	int next(String name, String entId) throws DAOException;
	
	void updateNext(String name, String entId) throws DAOException;
	
	int nextByDate(String name, Date tranDate, String entId) throws DAOException;
	
	void updateNextByDate(String name, Date tranDate, String entId) throws DAOException;
	
}
