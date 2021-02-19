package com.mb.ext.core.dao.impl;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.VerificationCodeDAO;
import com.mb.ext.core.entity.VerificationCodeEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("verificationCodeDAO")
@Qualifier("verificationCodeDAO")
public class VerificationDAOImpl extends AbstractBaseDAO<VerificationCodeEntity> implements VerificationCodeDAO
{
	@SuppressWarnings("unused")
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public VerificationDAOImpl()
	{
		super();
		this.setEntityClass(VerificationCodeEntity.class);
	}

	@Override
	public void addVerificationCode(
			VerificationCodeEntity verificationCodeEntity) throws DAOException {
		save(verificationCodeEntity);
		
	}

	@Override
	public VerificationCodeEntity getVerificationCodeEntityByMobileNo(String countryCode,
			String mobileNo, String verificationCode) throws DAOException{
		VerificationCodeEntity verificationCodeEntity = null;
		try {
			verificationCodeEntity = (VerificationCodeEntity)em.createQuery("select b from VerificationCodeEntity b where b.mobileCountryCode = :COUNTRYCODE and b.mobileNo = :MOBILENO and b.code = :CODE and b.isDeleted=:isDeleted",VerificationCodeEntity.class).setParameter("COUNTRYCODE", countryCode).setParameter("MOBILENO", mobileNo).setParameter("CODE", verificationCode).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			//logger.info("No record found for enterprise: "+id);
		} catch(Exception e){
			throw new DAOException(e);
		}
		return verificationCodeEntity;
	}

	@Override
	public VerificationCodeEntity getVerificationCodeEntityByEmail(
			String email, String verificationCode)  throws DAOException{
		VerificationCodeEntity verificationCodeEntity = null;
		try {
			verificationCodeEntity = (VerificationCodeEntity)em.createQuery("select b from VerificationCodeEntity b where b.email = :EMAIL and b.code = :CODE and b.isDeleted=:isDeleted",VerificationCodeEntity.class).setParameter("EMAIL", email).setParameter("CODE", verificationCode).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			//logger.info("No record found for enterprise: "+id);
		} catch(Exception e){
			throw new DAOException(e);
		}
		return verificationCodeEntity;
	}

	

}
