package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.SettingDAO;
import com.mb.ext.core.entity.SettingEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("settingDAO")
@Qualifier("settingDAO")
public class SettingDAOImpl extends AbstractBaseDAO<SettingEntity> implements SettingDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public SettingDAOImpl()
	{
		super();
		this.setEntityClass(SettingEntity.class);
	}

	@Override
	public void addSetting(SettingEntity settingEntity) throws DAOException {
		
		save(settingEntity);
		
	}

	@Override
	public SettingEntity getSettingByName(String name) throws DAOException {
		
		SettingEntity settingEntity = null;
		try {
			settingEntity = (SettingEntity)em.createQuery("select b from SettingEntity b where b.name = :NAME and b.isDeleted=:isDeleted",SettingEntity.class).setParameter("NAME", name).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for setting: "+name);
		}
		return settingEntity;
	}

	@Override
	public void updateSetting(SettingEntity settingEntity) throws DAOException {

		update(settingEntity);
		
	}

	@Override
	public List<SettingEntity> getSettings() throws DAOException {
		
		List<SettingEntity> settingEntityList = new ArrayList<SettingEntity>();
		try {
			settingEntityList = em.createQuery("select b from SettingEntity b where   b.isDeleted=:isDeleted",SettingEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for setting: ");
		}
		return settingEntityList;
	}

	

}
