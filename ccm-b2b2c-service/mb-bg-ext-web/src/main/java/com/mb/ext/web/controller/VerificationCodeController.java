package com.mb.ext.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mb.ext.core.constant.AuthenticationConstants;
import com.mb.ext.core.constant.UserConstants;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.AuthenticationService;
import com.mb.ext.core.service.LoginService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.VerificationService;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.VerificationCodeDTO;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;


/**验证码
 * @author B2B2C商城
 *
 */
@Controller
public class VerificationCodeController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private VerificationService verificationService;

	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;

	/**发送验证码
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/sendVerificationCode", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO sendVerificationCode(@RequestBody RequestDTO<VerificationCodeDTO> requestDTO) {

		VerificationCodeDTO verificationCodeDTO = requestDTO.getBody();
		String type = verificationCodeDTO.getType();
		String email = verificationCodeDTO.getEmail();
		String mobileNo = verificationCodeDTO.getMobileNo();
		String mobileCountryCode = verificationCodeDTO.getMobileCountryCode();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(UserConstants.TYPE_EMAIL.equals(type))
				verificationService.sendCodeByEmail(email);
			else if(UserConstants.TYPE_MOBILE.equals(type)){
				verificationService.sendCodeBySMS(mobileCountryCode, mobileNo);
			}
			else
				throw new BusinessException(BusinessErrorCode.VERIFICATION_TYPE_INCORRECT);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		} catch (Exception e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(BusinessErrorCode.COMMON_SYSTEM_ERROR);
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, BusinessErrorCode.COMMON_SYSTEM_ERROR);
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}

		return resultDTO;
	}

	/**校验验证码
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/verifyCode", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO verifyCode(@RequestBody RequestDTO<VerificationCodeDTO> requestDTO) {

		VerificationCodeDTO verificationCodeDTO = requestDTO.getBody();
		String type = verificationCodeDTO.getType();
		String email = verificationCodeDTO.getEmail();
		String mobileNo = verificationCodeDTO.getMobileNo();
		String mobileCountryCode = verificationCodeDTO.getMobileCountryCode();
		String verificationCode = verificationCodeDTO.getVerificationCode();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			boolean verificationResult = true;
			if(UserConstants.TYPE_EMAIL.equals(type)){
				if(!verificationService.verifyCodeByEmail(email, verificationCode))
					verificationResult =false;
			}else if(UserConstants.TYPE_MOBILE.equals(type)){
				if(!verificationService.verifyCodeBySMS(mobileCountryCode, mobileNo, verificationCode))
					verificationResult =false;
			}else
				throw new BusinessException(BusinessErrorCode.VERIFICATION_TYPE_INCORRECT);
			if(verificationResult){
				resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
			}else{
				resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
				resultDTO.getBody().getStatus().setErrorCode(BusinessErrorCode.VERIFICATION_INCORRECT);
				String errorDesc = MessageHelper.getMessageByErrorId(messageSource, BusinessErrorCode.VERIFICATION_INCORRECT);
				resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			}
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		} catch (Exception e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(BusinessErrorCode.COMMON_SYSTEM_ERROR);
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, BusinessErrorCode.COMMON_SYSTEM_ERROR);
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}

		return resultDTO;
	}
	
	

}
