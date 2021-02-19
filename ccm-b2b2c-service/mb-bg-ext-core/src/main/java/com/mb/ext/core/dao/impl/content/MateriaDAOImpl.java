package com.mb.ext.core.dao.impl.content;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.content.MateriaDAO;
import com.mb.ext.core.entity.content.MateriaEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("materiaDAO")
@Qualifier("materiaDAO")
public class MateriaDAOImpl extends AbstractBaseDAO<MateriaEntity> implements
		MateriaDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public MateriaDAOImpl() {
		super();
		this.setEntityClass(MateriaEntity.class);
	}

	@Override
	public MateriaEntity getMateriaByUuid(String uuid) throws DAOException {
		MateriaEntity materiaEntity = null;
		try {
			materiaEntity = (MateriaEntity) em
					.createQuery(
							"select b from MateriaEntity b where b.materiaUuid = :UUID and b.isDeleted=:isDeleted",
							MateriaEntity.class)
					.setParameter("UUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for materia: " + uuid);
		}
		return materiaEntity;
	}

	@Override
	public List<MateriaEntity> getMateriasByType(String type) throws DAOException {
		List<MateriaEntity> materiaEntityList = new ArrayList<MateriaEntity>();
		try {
			materiaEntityList = em
					.createQuery(
							"select b from MateriaEntity b where b.type= :TYPE and b.isDeleted=:isDeleted order by b.createDate desc",
							MateriaEntity.class)
							.setParameter("TYPE", type)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for materia" );
		}
		return materiaEntityList;
	}
	
	@Override
	public List<MateriaEntity> getMaterias() throws DAOException {
		List<MateriaEntity> materiaEntityList = new ArrayList<MateriaEntity>();
		try {
			materiaEntityList = em
					.createQuery(
							"select b from MateriaEntity b where b.isDeleted=:isDeleted order by b.createDate desc",
							MateriaEntity.class)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for materia" );
		}
		return materiaEntityList;
	}

	@Override
	public void addMateria(MateriaEntity materiaEntity) throws DAOException {
		
		save(materiaEntity);
		
	}


	@Override
	public void deleteMateria(MateriaEntity materiaEntity) throws DAOException {
		
		deletePhysical(materiaEntity);
		
	}

	@Override
	public void updateMateria(MateriaEntity materiaEntity) throws DAOException {
		
		update(materiaEntity);
		
	}
}
