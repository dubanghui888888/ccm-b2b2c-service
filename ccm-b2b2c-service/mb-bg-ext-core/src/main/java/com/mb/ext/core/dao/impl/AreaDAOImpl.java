package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AreaDAO;
import com.mb.ext.core.entity.AreaEntity;
import com.mb.ext.core.service.spec.AreaDTO;
import com.mb.framework.exception.DAOException;

@Repository("areaDAO")
@Qualifier("areaDAO")
public class AreaDAOImpl implements
		AreaDAO {
	
	@PersistenceContext
	protected EntityManager em;

	/**
	 * Initializing Entity.
	 */
	public AreaDAOImpl() {
		super();
	}

	@Override
	public List<AreaDTO> getAreas() throws DAOException {
		List<AreaDTO> areaList = new ArrayList<AreaDTO>();
		try {
			String sqlStr = "select area_id, name, depth, postal_code, sort, parent_id, first_character, abbr_character, full_character from area";
			List<Object> resultList = em.createNativeQuery(sqlStr).getResultList();
			for (Object object : resultList) {
				Object[] result = (Object[]) object;
				AreaDTO areDTO = new AreaDTO(
						result[0]==null?"":(String)result[0],
						result[1]==null?"":(String)result[1],
						result[2]==null?0:(Integer)result[2],
						result[3]==null?"":(String)result[3],
						result[4]==null?0:(Integer)result[4],
						result[5]==null?"":(String)result[5],
						result[6]==null?"":(String)result[6],
						result[7]==null?"":(String)result[7],
						result[8]==null?"":(String)result[8]);
				areaList.add(areDTO);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return areaList;
	}
	
	@Override
	public List<AreaDTO> getCitys() throws DAOException {
		List<AreaDTO> areaList = new ArrayList<AreaDTO>();
		try {
			String sqlStr = "select area_id, name, depth, postal_code, sort, parent_id, first_character, abbr_character, full_character from area where depth = 2";
			List<Object> resultList = em.createNativeQuery(sqlStr).getResultList();
			for (Object object : resultList) {
				Object[] result = (Object[]) object;
				AreaDTO areDTO = new AreaDTO(
						result[0]==null?"":(String)result[0],
						result[1]==null?"":(String)result[1],
						result[2]==null?0:(Integer)result[2],
						result[3]==null?"":(String)result[3],
						result[4]==null?0:(Integer)result[4],
						result[5]==null?"":(String)result[5],
						result[6]==null?"":(String)result[6],
						result[7]==null?"":(String)result[7],
						result[8]==null?"":(String)result[8]);
				areaList.add(areDTO);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return areaList;
	}

	@Override
	public AreaEntity getAreaById(String areaId) throws DAOException {
		AreaEntity areaEntity = null;
		try {
			areaEntity = em.createQuery("select b from AreaEntity b where  b.areaId = :ID", AreaEntity.class).setParameter("ID", areaId).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return areaEntity;
	}

	@Override
	public AreaEntity getAreaByNameAndDepth(String areaName, int depth) throws DAOException {
		AreaEntity areaEntity = null;
		try {
			areaEntity = em.createQuery("select b from AreaEntity b where  b.name = :NAME and b.depth = :DEPTH", AreaEntity.class).setParameter("NAME", areaName).setParameter("DEPTH", depth).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return areaEntity;
	}

}
