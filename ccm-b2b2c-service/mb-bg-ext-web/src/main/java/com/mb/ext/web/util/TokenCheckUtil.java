package com.mb.ext.web.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.constant.AuthenticationConstants;
import com.mb.ext.core.dao.UserTokenDAO;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.AdminAuthenticationService;
import com.mb.ext.core.service.AuthenticationService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;

@Repository
@Component
public class TokenCheckUtil {

	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private AdminAuthenticationService adminAuthenticationService;
	
	@Autowired
	private UserTokenDAO userTokenDAO;
	
	@Autowired
	private UserService userService;

	@Autowired
	private MessageSource messageSource;

	public boolean checkToken(RequestDTO requestDTO, ResultDTO resultDTO) {

		boolean checkResult = true;
		String tokenId = requestDTO.getHeader().getTokenId();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (StringUtils.isEmpty(tokenId)) {
				resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
				resultDTO.getBody().getStatus()
						.setErrorCode(BusinessErrorCode.COMMON_TOKEN_EMPTY);
				String errorDesc = MessageHelper.getMessageByErrorId(
						messageSource, BusinessErrorCode.COMMON_TOKEN_EMPTY);
				resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
				checkResult = false;
			} else if (!authenticationService.isValidToken(tokenId)) {
				resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
				resultDTO.getBody().getStatus()
						.setErrorCode(BusinessErrorCode.COMMON_TOKEN_INVALID);
				String errorDesc = MessageHelper.getMessageByErrorId(
						messageSource, BusinessErrorCode.COMMON_TOKEN_INVALID);
				resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
				checkResult = false;
			} else if (authenticationService.isTokenExpired(tokenId)) {
				resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
				resultDTO.getBody().getStatus()
						.setErrorCode(BusinessErrorCode.COMMON_TOKEN_EXPIRED);
				String errorDesc = MessageHelper.getMessageByErrorId(
						messageSource, BusinessErrorCode.COMMON_TOKEN_EXPIRED);
				resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
				checkResult = false;
			}
		} catch (Exception e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus()
					.setErrorCode(BusinessErrorCode.COMMON_SYSTEM_ERROR);
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource,
					BusinessErrorCode.COMMON_SYSTEM_ERROR);
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			checkResult = false;
		}
		

		return checkResult;
	}
	
	public boolean checkAdminToken(RequestDTO requestDTO, ResultDTO resultDTO) {

		boolean checkResult = true;
		String tokenId = requestDTO.getHeader().getTokenId();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (StringUtils.isEmpty(tokenId)) {
				resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
				resultDTO.getBody().getStatus()
						.setErrorCode(BusinessErrorCode.COMMON_TOKEN_EMPTY);
				String errorDesc = MessageHelper.getMessageByErrorId(
						messageSource, BusinessErrorCode.COMMON_TOKEN_EMPTY);
				resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
				checkResult = false;
			} else if (!adminAuthenticationService.isValidToken(tokenId)) {
				resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
				resultDTO.getBody().getStatus()
						.setErrorCode(BusinessErrorCode.COMMON_TOKEN_INVALID);
				String errorDesc = MessageHelper.getMessageByErrorId(
						messageSource, BusinessErrorCode.COMMON_TOKEN_INVALID);
				resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
				checkResult = false;
			} else if (adminAuthenticationService.isTokenExpired(tokenId)) {
				resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
				resultDTO.getBody().getStatus()
						.setErrorCode(BusinessErrorCode.COMMON_TOKEN_EXPIRED);
				String errorDesc = MessageHelper.getMessageByErrorId(
						messageSource, BusinessErrorCode.COMMON_TOKEN_EXPIRED);
				resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
				checkResult = false;
			}
		} catch (Exception e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus()
					.setErrorCode(BusinessErrorCode.COMMON_SYSTEM_ERROR);
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource,
					BusinessErrorCode.COMMON_SYSTEM_ERROR);
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			checkResult = false;
		}
		

		return checkResult;
	}
	
	public boolean checkMerchantToken(RequestDTO requestDTO, ResultDTO resultDTO) {

		boolean checkResult = true;
		String tokenId = requestDTO.getHeader().getTokenId();
		resultDTO.setHeader(requestDTO.getHeader());
		try {	
			if (StringUtils.isEmpty(tokenId)) {
				resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
				resultDTO.getBody().getStatus()
						.setErrorCode(BusinessErrorCode.COMMON_TOKEN_EMPTY);
				String errorDesc = MessageHelper.getMessageByErrorId(
						messageSource, BusinessErrorCode.COMMON_TOKEN_EMPTY);
				resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
				checkResult = false;
			} 
		} catch (Exception e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus()
					.setErrorCode(BusinessErrorCode.COMMON_SYSTEM_ERROR);
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource,
					BusinessErrorCode.COMMON_SYSTEM_ERROR);
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			checkResult = false;
		}
		

		return checkResult;
	}
}
