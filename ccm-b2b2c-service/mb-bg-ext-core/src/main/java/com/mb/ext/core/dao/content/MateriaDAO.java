package com.mb.ext.core.dao.content;

import java.util.List;

import com.mb.ext.core.entity.content.MateriaEntity;
import com.mb.framework.exception.DAOException;



public interface MateriaDAO
{
	MateriaEntity getMateriaByUuid(String uuid) throws DAOException;
	
	List<MateriaEntity> getMateriasByType(String type) throws DAOException;
	
	List<MateriaEntity> getMaterias() throws DAOException;
	
	void addMateria(MateriaEntity materiaEntity) throws DAOException;
	
	void updateMateria(MateriaEntity materiaEntity) throws DAOException;
	
	void deleteMateria(MateriaEntity materiaEntity) throws DAOException;
}
