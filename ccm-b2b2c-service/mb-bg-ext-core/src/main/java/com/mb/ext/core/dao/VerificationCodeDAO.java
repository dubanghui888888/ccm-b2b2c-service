package com.mb.ext.core.dao;

import com.mb.ext.core.entity.VerificationCodeEntity;
import com.mb.framework.exception.DAOException;



public interface VerificationCodeDAO
{
	void addVerificationCode(VerificationCodeEntity verificationCodeEntity) throws DAOException;
	
	VerificationCodeEntity  getVerificationCodeEntityByMobileNo(String countryCode, String mobileNo, String verificationCode) throws DAOException;
	
	VerificationCodeEntity getVerificationCodeEntityByEmail(String email, String verificationCode) throws DAOException;
}
