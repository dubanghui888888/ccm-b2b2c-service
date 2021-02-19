
package com.mb.ext.core.service.impl;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.constant.SettingConstants;
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.SettingDAO;
import com.mb.ext.core.dao.UserAuthDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.UserTokenDAO;
import com.mb.ext.core.dao.UserWechatDAO;
import com.mb.ext.core.entity.SettingEntity;
import com.mb.ext.core.entity.UserAuthEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserTokenEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.LoginService;
import com.mb.ext.core.service.NoteService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.VerificationService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.util.MailSenderUtil;
import com.mb.ext.core.util.SMSSenderUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.SecurityUtil;
import com.mb.framework.util.StringUtil;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;


@Service
@Qualifier("LoginService")
public class LoginServiceImpl extends AbstractService implements LoginService<BodyDTO>
{
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("sequenceDAO")
	private SequenceDAO sequenceDAO;
	
	@Autowired
	@Qualifier("userTokenDAO")
	private UserTokenDAO userTokenDAO;
	
	@Autowired
	@Qualifier("VerificationService")
	private VerificationService verificationService;
	
	@Autowired
	@Qualifier("userAuthDAO")
	private UserAuthDAO userAuthDAO;
	
	@Autowired
	@Qualifier("settingDAO")
	private SettingDAO settingDAO;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("userWechatDAO")
	private UserWechatDAO userWechatDAO;
	
	@Autowired
	@Qualifier("NoteService")
	private NoteService noteService;
	
	@Autowired
	private MailSenderUtil mailSenderUtil;
	
	@Autowired
	private SMSSenderUtil smsSenderUtil;
	
	@Autowired
	private PropertyRepository propertyRepository;
	
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());




	@Override
	public String login(UserDTO userDTO) throws BusinessException {
		
		String tokenNo = null;
		
		//1. Check whether it is valid user
		String mobileNo = userDTO.getPersonalPhone();
		String countryCode = userDTO.getPersonalPhoneCountryCode();
		try{
			
			UserEntity userEntity = null;
			
			if(!StringUtil.isEmtpy(mobileNo)){
				userEntity = userDAO.getUserByMobileNo(countryCode, mobileNo);
			}
			
			if(userEntity == null){
				throw new BusinessException(BusinessErrorCode.LOGIN_ID_PASSWORD_INCORRECT);
			}
			
			//Check PLATFORM service is due or not, 改为在激活员工账户时, 访问各功能模块时检查
			/*ServiceDueDTO serviceDueDTO = buyService.getServiceDue(userEntity.getEntEntity().getId(), ServiceConstants.SERVICE_TYPE_PLATFORM);
			if(serviceDueDTO != null && serviceDueDTO.getDueDate().before(new Date())){
				throw new BusinessException(BusinessErrorCode.ENT_PLATFORM_EXPIRED);
			}*/
			
			//if user locked, then stop here and throw exception
			UserAuthEntity userAuthEntity = userEntity.getUserAuthEntity();
			if(userAuthEntity.isLocked()){
				throw new BusinessException(BusinessErrorCode.LOGIN_ID_LOCKED);
			}
			if(!userAuthEntity.isActivated()){
				throw new BusinessException(BusinessErrorCode.LOGIN_USER_DISABLED);
			}
			//2. Check whether password is valid
			String srcPassword = userDTO.getPassword();
			String srcEncryptedPassword = SecurityUtil.encryptMd5(srcPassword);
			String descPassword = userEntity.getUserAuthEntity().getPassword();
			if(!srcEncryptedPassword.equals(descPassword)){
				//update failed count + 1
				
				int failedCount = userAuthEntity.getFailCount();
				String maxFailedCount = settingDAO.getSettingByName(SettingConstants.LOGIN_MAX_FAILED_COUNT).getValue();
				if(failedCount + 1 >= new Integer(maxFailedCount))
					userAuthEntity.setLocked(true);
				userAuthEntity.setFailCount(failedCount+1);
				
				throw new BusinessException(BusinessErrorCode.LOGIN_ID_PASSWORD_INCORRECT);
			}
			
			//if already login, then stop here
			if(userEntity.getUserTokenEntity() != null){
				//Token expired, then delete the expired token
				if(new Date().after(userEntity.getUserTokenEntity().getExpireTime())){
					UserTokenEntity userTokenEntity = userEntity.getUserTokenEntity();
					userEntity.setUseTokenEntity(null);
					userTokenDAO.deleteUserToken(userTokenEntity);
				}else{
					//Considering success login
					int successCount = userAuthEntity.getSuccessCount();
					userAuthEntity.setSuccessCount(successCount+1);
					userAuthDAO.updateUserAuth(userAuthEntity);
					
					UserDTO nUserDTO = userService.getUserDTO(userDTO);
					userService.setUserProfile(nUserDTO);
					return userEntity.getUserTokenEntity().getTokenId();
				}
			}
			//3. Add record in UserToken table
			tokenNo = RandomStringUtils.randomAlphanumeric(32);
			UserTokenEntity userTokenEntity = new UserTokenEntity();
			userTokenEntity.setTokenId(tokenNo);
			Date loginTime = new Date();
			userTokenEntity.setLoginTime(loginTime);
			userTokenEntity.setCreateBy("Admin");
			userTokenEntity.setUpdateBy("Admin");
			
//			int loginExpireDuration = new Integer(settingDAO.getSettingByName(SettingConstants.LOGIN_EXPIRE_DURATION).getValue());
			//用户端不设置超时
			int loginExpireDuration = 60*24*30;
			Date expireTime = DateUtils.addMinutes(loginTime, loginExpireDuration);
			userTokenEntity.setExpireTime(expireTime);
			userTokenEntity.setUserEntity(userEntity);
			
			userTokenDAO.addUserToken(userTokenEntity);
			
			//4. Update success count
			int successCount = userAuthEntity.getSuccessCount();
			userAuthEntity.setSuccessCount(successCount+1);
			userAuthDAO.updateUserAuth(userAuthEntity);
			
			//5. Set UserProfile to context
			UserDTO nUserDTO = userService.getUserDTO(userDTO);
			userService.setUserProfile(nUserDTO);
			
			
		}catch(DAOException e){
			throw new BusinessException (BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(BusinessException e){
			throw e;
		}catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return tokenNo;
		
	}
	

	@Override
	public void logout(UserDTO userDTO) throws BusinessException {
		
		try{
			UserEntity userEntity = userService.getUser(userDTO);
			if(userEntity != null){
				UserTokenEntity userTokenEntity = userEntity.getUserTokenEntity();
				if(userTokenEntity != null){
					//to physically delete userToken, must clear the reference. Otherwise, physically delete will fail
					userEntity.setUseTokenEntity(null);
					userTokenDAO.deleteUserToken(userTokenEntity);
				}
			}
			
			UserContext.set("UserProfile", null);
		}catch(DAOException e){
			throw new BusinessException("SYSTEM_MESSAGE_0001",e);
		}
		
	}

	@Override
	public void resetPassword(UserDTO userDTO) throws BusinessException {
		try{
			UserEntity userEntity = userService.getUser(userDTO);
//			String newPassword = RandomStringUtils.randomAlphanumeric(8);
			String newPassword = userDTO.getNewPassword();
			UserAuthEntity userAuthEntity = userEntity.getUserAuthEntity();
			userAuthEntity.setPassword(SecurityUtil.encryptMd5(newPassword));
			userAuthEntity.setLocked(false);
			userAuthEntity.setFailCount(0);
			userAuthDAO.updateUserAuth(userAuthEntity);
			//send email and SMS to notify
			noteService.sendNotification(userDTO, "resetpassword.success", new String[]{newPassword});
			/*if(!StringUtil.isEmtpy(userEntity.getPersonalEmail())){
				String emailSubject = propertyRepository.getProperty("mail.resetpassword.subject");
				String emailBody = propertyRepository.getProperty("mail.resetpassword.body");
				String replacedEmailBody = emailBody.replace("{1}", newPassword);
				String sentTo = userEntity.getPersonalEmail();
				mailSenderUtil.sendMail(emailSubject, replacedEmailBody, sentTo, "", null);
			}
			if(!StringUtil.isEmtpy(userEntity.getPersonalPhone())){
				String smsBody = propertyRepository.getProperty("sms.resetpassword.body");
				String replacedSMSBody = smsBody.replace("{1}", newPassword);
				String countryCode = userEntity.getPersonalPhoneCountryCode();
				String mobileNo = userEntity.getPersonalPhone();
				smsSenderUtil.sendSMS(replacedSMSBody, countryCode, mobileNo);
			}*/
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void changePassword(UserDTO userDTO) throws BusinessException {
		try{
			UserEntity userEntity = userService.getUser(userDTO);
			if(userEntity == null){
				throw new BusinessException(BusinessErrorCode.LOGIN_ID_PASSWORD_INCORRECT);
			}
			UserAuthEntity userAuthEntity = userEntity.getUserAuthEntity();
			String descPassword = userAuthEntity.getPassword();
			String srcPassword = userDTO.getPassword();
			String srcEncryptedPassword = SecurityUtil.encryptMd5(srcPassword);
			if(!srcEncryptedPassword.equals(descPassword)){
				throw new BusinessException(BusinessErrorCode.LOGIN_ID_PASSWORD_INCORRECT);
			}
			
			String newPassword = userDTO.getNewPassword();
			userAuthEntity.setPassword(SecurityUtil.encryptMd5(newPassword));
			userAuthEntity.setLocked(false);
			userAuthEntity.setFailCount(0);
			userAuthDAO.updateUserAuth(userAuthEntity);
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}


	@Override
	public void logout(String tokenId) throws BusinessException {
		try{
			UserTokenEntity userTokenEntity = userTokenDAO.findByTokenId(tokenId);
			if(userTokenEntity != null){
				userTokenDAO.deleteUserToken(userTokenEntity);
			}
			UserContext.set("UserProfile", null);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}


	@Override
	public String smsLogin(UserDTO userDTO) throws BusinessException {
		
		String tokenNo = null;
		
		//1. Check whether it is valid user
		String mobileNo = userDTO.getPersonalPhone();
		String countryCode = userDTO.getPersonalPhoneCountryCode();
		try{
			
			UserEntity userEntity = null;
			
			if(!StringUtil.isEmtpy(mobileNo)){
				userEntity = userDAO.getUserByMobileNo(countryCode, mobileNo);
			}
			
			if(userEntity == null){
				throw new BusinessException(BusinessErrorCode.LOGIN_ID_PASSWORD_INCORRECT);
			}
			if(!userEntity.isActive()){
				throw new BusinessException(BusinessErrorCode.LOGIN_USER_DISABLED);
			}
			//2. Check whether sms code is valid
			SettingEntity settingEntity = settingDAO.getSettingByName("IS_IGNORE_LOGIN_VERIFICATION");
			if(settingEntity != null && settingEntity.getValue().equals("0"))
			verificationService.verifyCodeBySMS("86", userDTO.getPersonalPhone(), userDTO.getVerificationCode());
			
			//if already login, then stop here
			if(userEntity.getUserTokenEntity() != null){
				//Token expired, then delete the expired token
				if(new Date().after(userEntity.getUserTokenEntity().getExpireTime())){
					UserTokenEntity userTokenEntity = userEntity.getUserTokenEntity();
					userEntity.setUseTokenEntity(null);
					userTokenDAO.deleteUserToken(userTokenEntity);
				}else{
					//Considering success login
/*					int successCount = userAuthEntity.getSuccessCount();
					userAuthEntity.setSuccessCount(successCount+1);
					userAuthDAO.updateUserAuth(userAuthEntity);*/
					
					UserDTO nUserDTO = userService.getUserDTO(userDTO);
					userService.setUserProfile(nUserDTO);
					return userEntity.getUserTokenEntity().getTokenId();
				}
			}
			//3. Add record in UserToken table
			tokenNo = RandomStringUtils.randomAlphanumeric(32);
			UserTokenEntity userTokenEntity = new UserTokenEntity();
			userTokenEntity.setTokenId(tokenNo);
			Date loginTime = new Date();
			userTokenEntity.setLoginTime(loginTime);
			userTokenEntity.setCreateBy("Admin");
			userTokenEntity.setUpdateBy("Admin");
			
			int loginExpireDuration = new Integer(settingDAO.getSettingByName(SettingConstants.LOGIN_EXPIRE_DURATION).getValue());
			Date expireTime = DateUtils.addMinutes(loginTime,
					1000000);	//默认不超时
//			Date expireTime = DateUtils.addMinutes(loginTime, loginExpireDuration);
			userTokenEntity.setExpireTime(expireTime);
			userTokenEntity.setUserEntity(userEntity);
			
			userTokenDAO.addUserToken(userTokenEntity);
			
			//4. Update success count
//			int successCount = userAuthEntity.getSuccessCount();
//			userAuthEntity.setSuccessCount(successCount+1);
//			userAuthDAO.updateUserAuth(userAuthEntity);
			
			//5. update wechat info
//			userEntity.setName(userDTO.getName());
//			userEntity.setPhotoUrl(userDTO.getPhotoUrl());
//			userEntity.setSex(userDTO.getSex());
			userDAO.updateUser(userEntity);
		}catch(DAOException e){
			throw new BusinessException (BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(BusinessException e){
			throw e;
		}catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return tokenNo;
		
	}
	
	@Override
	public String smsLoginWithoutVerification(UserDTO userDTO) throws BusinessException {
		
		String tokenNo = null;
		
		//1. Check whether it is valid user
		String mobileNo = userDTO.getPersonalPhone();
		String countryCode = userDTO.getPersonalPhoneCountryCode();
		try{
			
			UserEntity userEntity = null;
			
			if(!StringUtil.isEmtpy(mobileNo)){
				userEntity = userDAO.getUserByMobileNo(countryCode, mobileNo);
			}
			
			if(userEntity == null){
				throw new BusinessException(BusinessErrorCode.LOGIN_ID_PASSWORD_INCORRECT);
			}
			if(!userEntity.isActive()){
				throw new BusinessException(BusinessErrorCode.LOGIN_USER_DISABLED);
			}
			
			//if already login, then stop here
			if(userEntity.getUserTokenEntity() != null){
				//Token expired, then delete the expired token
				if(new Date().after(userEntity.getUserTokenEntity().getExpireTime())){
					UserTokenEntity userTokenEntity = userEntity.getUserTokenEntity();
					userEntity.setUseTokenEntity(null);
					userTokenDAO.deleteUserToken(userTokenEntity);
				}else{
					UserDTO nUserDTO = userService.getUserDTO(userDTO);
					userService.setUserProfile(nUserDTO);
					return userEntity.getUserTokenEntity().getTokenId();
				}
			}
			//3. Add record in UserToken table
			tokenNo = RandomStringUtils.randomAlphanumeric(32);
			UserTokenEntity userTokenEntity = new UserTokenEntity();
			userTokenEntity.setTokenId(tokenNo);
			Date loginTime = new Date();
			userTokenEntity.setLoginTime(loginTime);
			userTokenEntity.setCreateBy("Admin");
			userTokenEntity.setUpdateBy("Admin");
			
			int loginExpireDuration = new Integer(settingDAO.getSettingByName(SettingConstants.LOGIN_EXPIRE_DURATION).getValue());
			Date expireTime = DateUtils.addMinutes(loginTime,
					1000000);	//默认不超时
			userTokenEntity.setExpireTime(expireTime);
			userTokenEntity.setUserEntity(userEntity);
			
			userTokenDAO.addUserToken(userTokenEntity);
			
			userDAO.updateUser(userEntity);
		}catch(DAOException e){
			throw new BusinessException (BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(BusinessException e){
			throw e;
		}catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return tokenNo;
		
	}
	
	@Override
	public String wechatLogin(UserDTO userDTO) throws BusinessException {
		
		String tokenNo = null;
		try{
//			UserWechatEntity userWechatEntity = userWechatDAO.getUserWechatByOpenId(userDTO.getOpenId());
//			if(userWechatEntity == null)
//				throw new BusinessException(BusinessErrorCode.USER_WECHAT_NOT_BIND);
			UserEntity userEntity = userDAO.getUserByOpenId(userDTO.getOpenId());
			
			//if already login, then stop here
			if(userEntity.getUserTokenEntity() != null){
				//Token expired, then delete the expired token
				if(new Date().after(userEntity.getUserTokenEntity().getExpireTime())){
					UserTokenEntity userTokenEntity = userEntity.getUserTokenEntity();
					userEntity.setUseTokenEntity(null);
					userTokenDAO.deleteUserToken(userTokenEntity);
				}else{
					UserDTO nUserDTO = userService.getUserByUuid(userEntity.getUserUuid());
					userService.setUserProfile(nUserDTO);
					return userEntity.getUserTokenEntity().getTokenId();
				}
			}
			//3. Add record in UserToken table
			tokenNo = RandomStringUtils.randomAlphanumeric(32);
			UserTokenEntity userTokenEntity = new UserTokenEntity();
			userTokenEntity.setTokenId(tokenNo);
			Date loginTime = new Date();
			userTokenEntity.setLoginTime(loginTime);
			userTokenEntity.setCreateBy("Admin");
			userTokenEntity.setUpdateBy("Admin");
			
			int loginExpireDuration = new Integer(settingDAO.getSettingByName(SettingConstants.LOGIN_EXPIRE_DURATION).getValue());
			Date expireTime = DateUtils.addMinutes(loginTime,
					loginExpireDuration);	//默认不超时
			userTokenEntity.setExpireTime(expireTime);
			userTokenEntity.setUserEntity(userEntity);
			userTokenDAO.addUserToken(userTokenEntity);
		}catch(DAOException e){
			throw new BusinessException (BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(BusinessException e){
			throw e;
		}catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return tokenNo;
		
	}

}






