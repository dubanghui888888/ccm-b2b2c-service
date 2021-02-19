package com.mb.ext.core.dao.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.OrgRelDAO;
import com.mb.ext.core.entity.OrgRelEntity;
import com.mb.framework.dao.AbstractDAO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("orgRelDAO")
@Qualifier("orgRelDAO")
public class OrgRelDAOImpl extends AbstractDAO<OrgRelEntity> implements
		OrgRelDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public OrgRelDAOImpl() {
		super();
		this.setEntityClass(OrgRelEntity.class);
	}

	

	@Override
	public void addOrgRel(OrgRelEntity orgRelEntity) throws DAOException {
		
		save(orgRelEntity);
		
	}

	@Override
	public void updateOrgRel(OrgRelEntity orgRelEntity) throws DAOException {
		
		update(orgRelEntity);
		
	}

	@Override
	public void deleteOrgRel(OrgRelEntity orgRelEntity) throws DAOException {
		
		deletePhysical(orgRelEntity);
		
	}

}
