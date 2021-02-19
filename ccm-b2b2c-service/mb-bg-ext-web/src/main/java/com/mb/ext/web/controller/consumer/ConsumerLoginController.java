package com.mb.ext.web.controller.consumer;

import org.apache.commons.lang3.StringUtils;
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
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.AuthenticationService;
import com.mb.ext.core.service.LoginService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.VerificationService;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.util.MailSenderUtil;
import com.mb.ext.core.util.WechatUtil;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**会员登录类
 * @author B2B2C商城
 *
 */
@Controller
public class ConsumerLoginController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private VerificationService verificationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailSenderUtil mailSenderUtil;

	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;
	
	@Autowired
	private WechatUtil wechatUtil;

	/**会员登录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/login", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO login(@RequestBody RequestDTO<UserDTO> requestDTO) {

		UserDTO userDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		String countryCode = userDTO.getPersonalPhoneCountryCode();
		String personalPhone = userDTO.getPersonalPhone();
//		String verificationCode = userDTO.getVerificationCode();
		try {
			/*
			if(StringUtils.isEmpty(personalPhone)){
				throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_PARAMETER_ERROR);
			}
			UserDTO oUserDTO = userService.getUserByMobileNo(countryCode, personalPhone);
			if(oUserDTO == null){
				verificationService.verifyCodeBySMS(countryCode, personalPhone, verificationCode);
				userService.registerUser(userDTO);
			}*/
			
			String tokenId = null;
			if("PASSWORD".equals(userDTO.getVerifyType())){
				tokenId = loginService.login(userDTO);
				//返回用户详细信息
				userDTO = userService.getUserByMobileNo(countryCode, personalPhone);
			}else if("SMS".equals(userDTO.getVerifyType())){
				//手机验证码登录
				tokenId = loginService.smsLogin(userDTO);
				//返回用户详细信息
				userDTO = userService.getUserByMobileNo(countryCode, personalPhone);
			}else if("WECHAT".equals(userDTO.getVerifyType())){
				//微信授权登录
				String code = userDTO.getCode();
				String openId = userDTO.getOpenId();
				if(StringUtils.isEmpty(openId)){
					openId = wechatUtil.getOpenId(code).getOpenId();
					userDTO.setOpenId(openId);
				}
				UserDTO nUserDTO = userService.getUserByOpenId(openId);
				if(nUserDTO == null) {
					//新微信会员, 需要先注册
					userService.registerUser(userDTO);
					tokenId = loginService.wechatLogin(userDTO);
					userDTO = userService.getUserByOpenId(openId);
				}else {
					tokenId = loginService.wechatLogin(userDTO);
					userDTO = nUserDTO;
				}
			}
			resultDTO.getBody().setData(userDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
			resultDTO.getHeader().setTokenId(tokenId);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}
		return resultDTO;
	}

	/**会员退出登录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/logout", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO logout(@RequestBody RequestDTO<UserDTO> requestDTO) {
		
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
	
		try {

			loginService.logout(requestDTO.getHeader().getTokenId());
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}
		
		return resultDTO;
	}
	
	/**会员重置密码
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO resetPassword(@RequestBody RequestDTO<UserDTO> requestDTO) {

		UserDTO userDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(userDTO ==null){
				throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_PARAMETER_ERROR);
			}
			loginService.resetPassword(userDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**会员修改密码
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changePassword(@RequestBody RequestDTO<UserDTO> requestDTO) {

		String entId = requestDTO.getHeader().getEntId();
		String tokenId = requestDTO.getHeader().getTokenId();
		UserDTO userDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
/*			if(userDTO ==null || !userDTO.isIdentified()){
				throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_PARAMETER_ERROR);
			}*/
			if(!tokenCheckUtil.checkToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				nUserDTO.setPassword(userDTO.getPassword());
				nUserDTO.setNewPassword(userDTO.getNewPassword());
				loginService.changePassword(nUserDTO);
			}
			
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	

}
