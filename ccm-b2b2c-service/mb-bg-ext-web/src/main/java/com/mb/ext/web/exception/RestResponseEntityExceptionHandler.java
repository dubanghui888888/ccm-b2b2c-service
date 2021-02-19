package com.mb.ext.web.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.web.util.MessageHelper;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{
	@Autowired
	private MessageSource messageSource;

	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	public RestResponseEntityExceptionHandler()
	{
		super();
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleBusinessException(final BusinessException ex, final WebRequest request)
	{
		logger.error("Exception thrown in RestresponseEntity Handler is" + ex);
//		final Errors errors = MessageHelper.createError(ex.getErrorCode(), MessageHelper.getMessageByErrorId(messageSource, ex.getErrorCode()));
		final ResultDTO errors = MessageHelper.createErrorBody(MessageHelper.getDefaultErrorCode(), MessageHelper.getDefaultMessage(messageSource));
		return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.EXPECTATION_FAILED, request);
	}

}
