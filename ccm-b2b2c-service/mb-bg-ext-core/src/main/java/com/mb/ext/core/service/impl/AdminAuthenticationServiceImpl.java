
package com.mb.ext.core.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.mb.ext.core.dao.AdminDAO;
import com.mb.ext.core.dao.AdminTokenDAO;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.entity.AdminEntity;
import com.mb.ext.core.entity.AdminTokenEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.AdminAuthenticationService;
import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.SecurityUtil;
import com.mb.framework.util.log.LogHelper;


@Service
@Qualifier("AdminAuthenticationService")
public class AdminAuthenticationServiceImpl extends AbstractService implements AdminAuthenticationService<BodyDTO>
{
	@Autowired
	@Qualifier("adminDAO")
	private AdminDAO adminDAO;
	
	
	@Autowired
	@Qualifier("sequenceDAO")
	private SequenceDAO sequenceDAO;
	
	
	@Autowired
	@Qualifier("adminTokenDAO")
	private AdminTokenDAO adminTokenDAO;
	
	@Autowired
	private MessageSource messageSource;
	
	
	
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());




	@Override
	public boolean isValidToken(String tokenId) throws BusinessException {
		
		boolean isValidToken = false;
		
		try {
			AdminTokenEntity adminTokenEntity = adminTokenDAO.findByTokenId(tokenId);
			if(adminTokenEntity != null){
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
			AdminTokenEntity adminTokenEntity = adminTokenDAO.findByTokenId(tokenId);
			if(adminTokenEntity != null){
				if(new Date().before(adminTokenEntity.getExpireTime()))
					isTokenExpired = false;
			}
		} catch (DAOException e) {
			logger.error("error happened when query user token by id");
		}
		
		return isTokenExpired;
	}




	@Override
	public boolean validatePassword(AdminDTO adminDTO) throws BusinessException {
		
		try{
			AdminEntity adminEntity = null;
			String id = adminDTO.getId();
			
			adminEntity= adminDAO.getAdminById(id);
			
			if(adminEntity == null){
				throw new BusinessException(BusinessErrorCode.LOGIN_ID_PASSWORD_INCORRECT);
			}
			
			
			//2. Check whether password is valid
			String srcPassword = adminDTO.getPassword();
			String srcEncryptedPassword = SecurityUtil.encryptMd5(srcPassword);
			String descPassword = adminEntity.getPassword();
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






