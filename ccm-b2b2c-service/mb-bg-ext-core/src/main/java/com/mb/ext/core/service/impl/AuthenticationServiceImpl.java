
package com.mb.ext.core.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.UserAuthDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.UserTokenDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserTokenEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.AuthenticationService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.SecurityUtil;
import com.mb.framework.util.StringUtil;
import com.mb.framework.util.log.LogHelper;


@Service
@Qualifier("AuthenticationService")
public class AuthenticationServiceImpl extends AbstractService implements AuthenticationService<BodyDTO>
{
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;
	
	@Autowired
	@Qualifier("sequenceDAO")
	private SequenceDAO sequenceDAO;
	
	@Autowired
	@Qualifier("userAuthDAO")
	private UserAuthDAO userAuthDAO;
	
	@Autowired
	@Qualifier("userTokenDAO")
	private UserTokenDAO userTokenDAO;
	
	@Autowired
	private MessageSource messageSource;
	
	
	
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());




	@Override
	public boolean isValidToken(String tokenId) throws BusinessException {
		
		boolean isValidToken = false;
		
		try {
			UserTokenEntity userTokenEntity = userTokenDAO.findByTokenId(tokenId);
			if(userTokenEntity != null){
				isValidToken = true;
			}
		} catch (DAOException e) {
			logger.error("error happened when query user token by id");
		}
		
		return isValidToken;
		
	}




	@Override
	public boolean isTokenExpired(String tokenId) throws BusinessException {
		
		boolean isTokenExpired = true;
		
		try {
			UserTokenEntity userTokenEntity = userTokenDAO.findByTokenId(tokenId);
			if(userTokenEntity != null){
				if(new Date().before(userTokenEntity.getExpireTime()))
					isTokenExpired = false;
			}
		} catch (DAOException e) {
			logger.error("error happened when query user token by id");
		}
		
		return isTokenExpired;
	}




	@Override
	public boolean validatePassword(UserDTO userDTO) throws BusinessException {
		
		try{
			UserEntity userEntity = null;
			String mobileNo = userDTO.getPersonalPhone();
			String countryCode = userDTO.getPersonalPhoneCountryCode();
			
			if(!StringUtil.isEmtpy(mobileNo)){
				userEntity = userDAO.getUserByMobileNo(countryCode, mobileNo);
			}
			
			if(userEntity == null){
				throw new BusinessException(BusinessErrorCode.LOGIN_ID_PASSWORD_INCORRECT);
			}
			
			
			//2. Check whether password is valid
			String srcPassword = userDTO.getPassword();
			String srcEncryptedPassword = SecurityUtil.encryptMd5(srcPassword);
			String descPassword = userEntity.getUserAuthEntity().getPassword();
			if(!srcEncryptedPassword.equals(descPassword)){
				throw new BusinessException(BusinessErrorCode.LOGIN_ID_PASSWORD_INCORRECT);
			}
		}catch(DAOException e){
			throw new BusinessException (BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (BusinessException e) {
			throw e;
		}catch (Exception e) {
			throw new BusinessException (BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return true;
	}


	
}






