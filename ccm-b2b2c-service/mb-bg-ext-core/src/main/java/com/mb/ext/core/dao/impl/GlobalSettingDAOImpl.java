package com.mb.ext.core.dao.impl;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.GlobalSettingDAO;
import com.mb.ext.core.entity.GlobalSettingEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("globalSettingDAO")
@Qualifier("globalSettingDAO")
public class GlobalSettingDAOImpl extends AbstractBaseDAO<GlobalSettingEntity> implements GlobalSettingDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public GlobalSettingDAOImpl()
	{
		super();
		this.setEntityClass(GlobalSettingEntity.class);
	}

	@Override
	public void addGlobalSetting(GlobalSettingEntity globalSettingEntity) throws DAOException {
		
		save(globalSettingEntity);
		
	}

	@Override
	public GlobalSettingEntity getGlobalSetting() throws DAOException {
		
		GlobalSettingEntity globalSettingEntity = null;
		try {
			globalSettingEntity = (GlobalSettingEntity)em.createQuery("select b from GlobalSettingEntity b ",GlobalSettingEntity.class).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for global setting");
		}
		return globalSettingEntity;
	}

	@Override
	public void updateGlobalSetting(GlobalSettingEntity globalSettingEntity) throws DAOException {

		update(globalSettingEntity);
		
	}

}
