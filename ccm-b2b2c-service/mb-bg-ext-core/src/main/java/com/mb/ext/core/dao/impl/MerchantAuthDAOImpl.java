package com.mb.ext.core.dao.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.MerchantAuthDAO;
import com.mb.ext.core.entity.MerchantAuthEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("merchantAuthDAO")
@Qualifier("merchantAuthDAO")
public class MerchantAuthDAOImpl extends AbstractBaseDAO<MerchantAuthEntity> implements MerchantAuthDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public MerchantAuthDAOImpl()
	{
		super();
		this.setEntityClass(MerchantAuthEntity.class);
	}

	@Override
	public void addMerchantAuth(MerchantAuthEntity merchantAuthEntity) throws DAOException {
		
		save(merchantAuthEntity);
		
	}

	@Override
	public void updateMerchantAuth(MerchantAuthEntity merchantAuthEntity)
			throws DAOException {
		update(merchantAuthEntity);
		
	}

	@Override
	public void deleteMerchantAuth(MerchantAuthEntity merchantAuthEntity)
			throws DAOException {
		deletePhysical(merchantAuthEntity);
		
	}

	@Override
	public void deletePhysicalMerchantAuth(MerchantAuthEntity merchantAuthEntity)
			throws DAOException {
		
		deletePhysical(merchantAuthEntity);
		
	}

	

}
