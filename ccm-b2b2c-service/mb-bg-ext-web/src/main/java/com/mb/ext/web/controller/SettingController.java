package com.mb.ext.web.controller;

import java.util.List;

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

import com.mb.ext.core.service.AuthenticationService;
import com.mb.ext.core.service.LoginService;
import com.mb.ext.core.service.SettingService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.SettingDTO;
import com.mb.ext.core.service.spec.SettingDTOList;
import com.mb.ext.core.util.MailSenderUtil;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**系统参数
 * @author B2B2C商城
 *
 */
@Controller
public class SettingController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	@Autowired
	private LoginService loginService;
	
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
	private SettingService settingService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;

	/**查询系统参数
	 * @param requestDTO - 请求数据
	 * @return - 返回数据
	 */
	@RequestMapping(value = "/inquiryParameter", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryParameter(@RequestBody RequestDTO<SettingDTO> requestDTO) {

		SettingDTO settingDTO = requestDTO.getBody();
		SettingDTOList settings = new SettingDTOList();
		String name = settingDTO.getName();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!StringUtils.isEmpty(name)){
				SettingDTO nSettingDTO = settingService.getSettingByName(name);
				resultDTO.getBody().setData(nSettingDTO);
			}else{
				List<SettingDTO> settingDTOList = settingService.getSysSettings();
				settings.setSettings(settingDTOList);
				resultDTO.getBody().setData(settings);
			}
			resultDTO.getBody().getStatus().setStatusCode("0");
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode("1");
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	

}
