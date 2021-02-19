
package com.mb.ext.core.service.impl;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.dao.SettingDAO;
import com.mb.ext.core.dao.VerificationCodeDAO;
import com.mb.ext.core.entity.SettingEntity;
import com.mb.ext.core.entity.VerificationCodeEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.VerificationService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.util.MailSenderUtil;
import com.mb.ext.core.util.SMSSenderUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.property.PropertyRepository;


@Service
@Qualifier("VerificationService")
public class VerificationServiceImpl extends AbstractService implements VerificationService<BodyDTO>
{
	
	@Autowired
	private MailSenderUtil mailSenderUtil;
	
	@Autowired
	private SMSSenderUtil smsSenderUtil;
	
	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private VerificationCodeDAO verificationCodeDAO;
	
	@Autowired
	@Qualifier("settingDAO")
	private SettingDAO settingDAO;

	@Override
	public void sendCodeBySMS(String countryCode, String mobileNo) throws BusinessException {
		String duration = propertyRepository.getProperty("verificationcode.expire.duration");
		
		String verificationCode = RandomStringUtils.randomNumeric(6);
		
		try{
			smsSenderUtil.sendVerificationCode(verificationCode, countryCode, mobileNo);
			VerificationCodeEntity verificationCodeEntity = new VerificationCodeEntity();
			verificationCodeEntity.setMobileCountryCode(countryCode);
			verificationCodeEntity.setMobileNo(mobileNo);
			verificationCodeEntity.setType("1"); //1 for SMS
			verificationCodeEntity.setCode(verificationCode);
			verificationCodeEntity.setUpdateBy("System");
			verificationCodeEntity.setCreateBy("System");
			Date expireTime = DateUtils.addMinutes(new Date(), new Integer(duration));
			verificationCodeEntity.setExpireTime(expireTime);
			verificationCodeDAO.addVerificationCode(verificationCodeEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
		
	}

	@Override
	public void sendCodeByEmail(String emailId) throws BusinessException {
		
		String duration = propertyRepository.getProperty("verificationcode.expire.duration");
		String subject = propertyRepository.getProperty("mail.sendVerificationCode.subject");
		String bodyTemplate = propertyRepository.getProperty("mail.sendVerificationCode.body");
		
		String verificationCode = RandomStringUtils.randomNumeric(6);
		String body = bodyTemplate.replace("{1}", verificationCode);
		
		try{
			mailSenderUtil.sendMail(subject, body, emailId, null, null);
			
			VerificationCodeEntity verificationCodeEntity = new VerificationCodeEntity();
			verificationCodeEntity.setEmail(emailId);
			verificationCodeEntity.setType("2"); //2 for Email
			verificationCodeEntity.setCode(verificationCode);
			verificationCodeEntity.setUpdateBy("System");
			verificationCodeEntity.setCreateBy("System");
			
			Date expireTime = DateUtils.addMinutes(new Date(), new Integer(duration));
			verificationCodeEntity.setExpireTime(expireTime);
			verificationCodeDAO.addVerificationCode(verificationCodeEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(BusinessException e1){
			throw e1;
		}catch(Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
		
	}

	@Override
	public boolean verifyCodeBySMS(String countryCode, String mobileNo, String code) throws BusinessException {
		
		try{
			SettingEntity settingEntity = settingDAO.getSettingByName("IS_IGNORE_LOGIN_VERIFICATION");
			if(settingEntity != null && settingEntity.getValue().equals("0")) {
				VerificationCodeEntity verificationCodeEntity = verificationCodeDAO.getVerificationCodeEntityByMobileNo(countryCode, mobileNo, code);
				if(verificationCodeEntity == null){
					throw new BusinessException(BusinessErrorCode.VERIFICATION_INCORRECT);
				}else if(verificationCodeEntity.getExpireTime().before(new Date())){
					throw new BusinessException(BusinessErrorCode.VERIFICATION_EXPIRE);
				}
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(BusinessException e1){
			throw e1;
		}catch(Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
		return true;
	}

	@Override
	public boolean verifyCodeByEmail(String emailId, String code)
			throws BusinessException {
		try{
			VerificationCodeEntity verificationCodeEntity = verificationCodeDAO.getVerificationCodeEntityByEmail(emailId, code);
			if(verificationCodeEntity == null){
				throw new BusinessException(BusinessErrorCode.VERIFICATION_INCORRECT);
			}else if(verificationCodeEntity.getExpireTime().before(new Date())){
				throw new BusinessException(BusinessErrorCode.VERIFICATION_EXPIRE);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(BusinessException e1){
			throw e1;
		}catch(Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
		return true;
		
	}
	

	
}






