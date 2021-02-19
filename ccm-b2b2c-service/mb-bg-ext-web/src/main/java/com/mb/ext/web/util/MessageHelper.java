package com.mb.ext.web.util;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.mb.ext.core.constant.AuthenticationConstants;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.web.message.Errors;
import com.mb.framework.service.spec.StatusDTO;


public class MessageHelper
{
	public static Errors createError(String errorCode, String message)
	{
		Errors errors = new Errors();
		errors.appendError(errorCode, message);
		return errors;
	}
	public static ResultDTO createErrorBody(String errorCode, String message)
	{
		ResultDTO resultDTO = new ResultDTO();
		BodyDTO bodyDTO = new BodyDTO();
		StatusDTO statusDTO = new StatusDTO();
		statusDTO.setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
		statusDTO.setErrorCode(errorCode);
		statusDTO.setErrorDesc(message);
		bodyDTO.setStatus(statusDTO);
		resultDTO.setBody(bodyDTO);
		return resultDTO;
	}

	public static String getMessageByErrorId(MessageSource messageSource,String errorCode)
	{
		Locale currentLocale = LocaleContextHolder.getLocale();
		if (StringUtils.isNotBlank(errorCode))
		{
			return messageSource.getMessage(errorCode, null, currentLocale);
		}

		return getDefaultMessage(messageSource);
	}

	public static String getDefaultMessage(MessageSource messageSource)
	{
		return messageSource.getMessage(getDefaultErrorCode(), null, LocaleContextHolder.getLocale());
	}

	public static String getDefaultMessage(MessageSource messageSource, Locale locale)
	{
		return messageSource.getMessage(getDefaultErrorCode(), null, locale);
	}

	public static String getDefaultErrorCode()
	{
//		return ErrorCodes.FM00001;
		return BusinessErrorCode.COMMON_SYSTEM_ERROR;
	}
}
