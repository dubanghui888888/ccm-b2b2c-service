package com.mb.ext.web.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.mb.ext.core.constant.AuthorizationConstants;
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.service.AuthenticationService;
import com.mb.ext.core.service.LoginService;
import com.mb.ext.core.service.NoteService;
import com.mb.ext.core.service.RoleService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.VerificationService;
import com.mb.ext.core.service.spec.NoteDTO;
import com.mb.ext.core.service.spec.NoteDTOList;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**系统通知
 * @author B2B2C商城
 *
 */
@Controller
public class NoteController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private NoteService noteService;

	@Autowired
	private VerificationService verificationService;

	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;

	/**查询系统通知
	 * @param requestDTO - 请求数据
	 * @return
	 */
	@RequestMapping(value = "/inquiryNotes", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryNotes(@RequestBody RequestDTO<UserDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserDTO userDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			NoteDTOList list = new NoteDTOList();
			List<NoteDTO> noteDTOList = new ArrayList<NoteDTO>();
			noteDTOList = noteService.getNotes(userDTO);
			list.setNotes(noteDTOList);
			int unreadNoteCount = noteService.getUnreadNoteCount(userDTO);
			list.setUnreadCount(unreadNoteCount);
			resultDTO.getBody().setData(list);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**获取会员未读通知
	 * @param requestDTO - 请求数据
	 * @return - 返回数据
	 */
	@RequestMapping(value = "/getUnreadNoteCount", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getUnreadNoteCount(@RequestBody RequestDTO<UserDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserDTO userDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			NoteDTOList list = new NoteDTOList();
			int unreadNoteCount = noteService.getUnreadNoteCount(userDTO);
			list.setUnreadCount(unreadNoteCount);
			resultDTO.getBody().setData(list);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	
	
	/**标记通知已读
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/markNoteRead", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO markNoteRead(@RequestBody RequestDTO<NoteDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		NoteDTO noteDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			noteService.markRead(noteDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**标记通知未读
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/markUserNoteRead", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO markUserNoteRead(@RequestBody RequestDTO<UserDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserDTO userDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			noteService.markUserNoteRead(userDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**删除系统通知
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/deleteNote", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO deleteNote(@RequestBody RequestDTO<NoteDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		NoteDTO noteDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			noteService.deleteNote(noteDTO);
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
