package com.mb.ext.web.controller;

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
import com.mb.ext.core.service.AuthenticationService;
import com.mb.ext.core.service.LoginService;
import com.mb.ext.core.service.NoteService;
import com.mb.ext.core.service.OSSService;
import com.mb.ext.core.service.SettingService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.VerificationService;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**处理和微信接口的相关请求
 * @author B2B2C商城
 *
 */
@Controller
public class WechatController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OSSService ossService;
	
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private VerificationService verificationService;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;
	
	/**绑定微信
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/bindWechat", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO bindWechat(@RequestBody RequestDTO<UserDTO> requestDTO) {

		UserDTO userDTO = requestDTO.getBody();
		String actionType = userDTO.getActionType();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		String openId = requestDTO.getHeader().getOpenId();
		try {
			if("BIND".equals(actionType)){
				if(!StringUtils.isEmpty(userDTO.getVerificationCode())){
					//手机验证码登录
					if(verificationService.verifyCodeBySMS("86", userDTO.getPersonalPhone(), userDTO.getVerificationCode())){
						userService.bindWechat(userDTO);
					}
				}
				else if(authenticationService.validatePassword(userDTO))
					userService.bindWechat(userDTO);
				//密码登录
				
			}else if("UNBIND".equals(actionType)){
				userService.unbindWechat(openId);
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
