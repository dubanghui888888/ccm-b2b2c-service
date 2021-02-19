package com.mb.ext.core.dao.impl;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.PrintSettingDAO;
import com.mb.ext.core.entity.PrintSettingEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("printSettingDAO")
@Qualifier("printSettingDAO")
public class PrintSettingDAOImpl extends AbstractBaseDAO<PrintSettingEntity> implements PrintSettingDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public PrintSettingDAOImpl()
	{
		super();
		this.setEntityClass(PrintSettingEntity.class);
	}

	@Override
	public void addPrintSetting(PrintSettingEntity settingEntity) throws DAOException {
		
		save(settingEntity);
		
	}
	
	@Override
	public void updatePrintSetting(PrintSettingEntity settingEntity) throws DAOException {

		update(settingEntity);
		
	}

	@Override
	public PrintSettingEntity getPrintSetting() throws DAOException {
		
		PrintSettingEntity printSettingEntity = null;
		try {
			printSettingEntity = (PrintSettingEntity)em.createQuery("select b from PrintSettingEntity b where b.merchantEntity is null and b.isDeleted=:isDeleted",PrintSettingEntity.class).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for print setting");
		}
		return printSettingEntity;
	}
	
	@Override
	public PrintSettingEntity getPrintSettingByMerchant(MerchantEntity merchantEntity) throws DAOException {
		
		PrintSettingEntity printSettingEntity = null;
		try {
			printSettingEntity = (PrintSettingEntity)em.createQuery("select b from PrintSettingEntity b where b.merchantEntity.merchantUuid = :MERCHANTUUID and b.isDeleted=:isDeleted",PrintSettingEntity.class).setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for print setting");
		}
		return printSettingEntity;
	}
	
	@Override
	public PrintSettingEntity getPrintSettingByUuid(String uuid) throws DAOException {
		
		PrintSettingEntity printSettingEntity = null;
		try {
			printSettingEntity = (PrintSettingEntity)em.createQuery("select b from PrintSettingEntity b where b.printSettingUuid = :UUID and b.isDeleted=:isDeleted",PrintSettingEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for print setting");
		}
		return printSettingEntity;
	}

}
