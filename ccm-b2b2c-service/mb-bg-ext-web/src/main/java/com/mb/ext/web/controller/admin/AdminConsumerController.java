package com.mb.ext.web.controller.admin;

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

import com.mb.ext.core.constant.AuthenticationConstants;
import com.mb.ext.core.constant.AuthorizationConstants;
import com.mb.ext.core.constant.UserConstants;
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.service.AdminService;
import com.mb.ext.core.service.LogService;
import com.mb.ext.core.service.TrainerService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.TrainerDTO;
import com.mb.ext.core.service.spec.TrainerDTOList;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.UserDTOList;
import com.mb.ext.core.service.spec.UserLevelDTO;
import com.mb.ext.core.service.spec.UserLevelDTOList;
import com.mb.ext.core.service.spec.UserSearchDTO;
import com.mb.ext.core.util.OSSUtil;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**后台管理会员类
 * @author B2B2C商城
 *
 */
@Controller
public class AdminConsumerController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	@Autowired
	private AdminService adminService;

	@Autowired
	private TrainerService trainerService;

	@Autowired
	private LogService logService;

	@Autowired
	private UserService userService;

	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private TokenCheckUtil tokenCheckUtil;

	@Autowired
	private OSSUtil ossUtil;

	/**导入会员信息
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/admin/importUser", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO importUser(@RequestBody RequestDTO<UserDTO> requestDTO) {

		UserDTO userDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			userService.importUser(userDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}
		return resultDTO;
	}
	
	/**查询直属会员
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/admin/inquiryL1User", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryL1User(@RequestBody RequestDTO<UserDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserDTO userDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			List<UserDTO> userDTOList = userService.getL1ChildUsers(userDTO);
			UserDTOList list = new UserDTOList();
			list.setUserList(userDTOList);
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
	
	/**查询下两级会员
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/admin/inquiryL2User", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryL2User(@RequestBody RequestDTO<UserDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserDTO userDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			List<UserDTO> userDTOList = userService.getL1AndL2ChildUsers(userDTO);
			UserDTOList list = new UserDTOList();
			list.setUserList(userDTOList);
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
	
	/**增删改会员等级
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/admin/changeUserLevel", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeUserLevel(@RequestBody RequestDTO<UserLevelDTO> requestDTO) {

		UserLevelDTO userLevelDTO = requestDTO.getBody();
		String actionType = userLevelDTO.getActionType();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if ("ADD".equals(actionType)) // 新增会员等级
				userService.addUserLevel(userLevelDTO);
			else if ("MODIFY".equals(actionType)) // 修改会员等级
				userService.updateUserLevel(userLevelDTO);
			else if ("DELETE".equals(actionType)) // 删除会员等级
				userService.deleteUserLevel(userLevelDTO);
			else if ("DEFAULT".equals(actionType)) // 设置默认会员等级
				userService.defaultUserLevel(userLevelDTO);
			else if ("UNDEFAULT".equals(actionType)) // 取消设置会员等级
				userService.cancelDefaultUserLevel(userLevelDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}
		return resultDTO;
	}

	/**查询会员等级
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/admin/inquiryUserLevel", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryUserLevel(@RequestBody RequestDTO<UserLevelDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserLevelDTO userLevelDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if (!StringUtils.isEmpty(userLevelDTO.getUserLevelUuid())) {
				userLevelDTO = userService.getUserLevelByUuid(userLevelDTO.getUserLevelUuid());
				resultDTO.getBody().setData(userLevelDTO);
			} else if (!StringUtils.isEmpty(userLevelDTO.getName())) {
				userLevelDTO = userService.getUserLevelByUuid(userLevelDTO.getName());
				resultDTO.getBody().setData(userLevelDTO);
			} else {
				List<UserLevelDTO> userLevelDTOList = userService.getUserLevels();
				UserLevelDTOList list = new UserLevelDTOList();
				list.setLevels(userLevelDTOList);
				resultDTO.getBody().setData(list);
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

	/**增删改及其他会员信息修改
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/admin/changeUser", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeUser(@RequestBody RequestDTO<UserDTO> requestDTO) {

		UserDTO userDTO = requestDTO.getBody();
		String actionType = userDTO.getActionType();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if ("DISABLE".equals(actionType)) // 停用账户
				userService.disableUser(userDTO);
			else if ("ENABLE".equals(actionType)) // 启用账户
				userService.enableUser(userDTO);
			else if ("MEMO".equals(actionType)) // 备注账户
				userService.memoUser(userDTO);
			else if ("INFO".equals(actionType)) { // 更新基本信息
				userService.updateUserField(userDTO, "NAME");
				userService.updateUserField(userDTO, "PERSONALPHONE");
				userService.updateUserField(userDTO, "IDCARDNO");
				userService.updateUserField(userDTO, "USERLEVEL");
			}else if("SUPERVISOR".equals(actionType)) {	
				userService.changeSupervisor(userDTO,userDTO.getSupervisorL1());
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

	/**查询会员
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/admin/inquiryUser", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryUser(@RequestBody RequestDTO<UserDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserDTO userDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if (!StringUtils.isEmpty(userDTO.getUserUuid())) {
				userDTO = userService.getUserByUuid(userDTO.getUserUuid());
				resultDTO.getBody().setData(userDTO);
			} else if (userDTO.getMerchantDTO() != null) {
				List<UserDTO> userList = userService.getUserByMerchant(userDTO.getMerchantDTO());
				UserDTOList list = new UserDTOList();
				list.setUserList(userList);
				resultDTO.getBody().setData(list);
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
	
	/**查询培训师
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/admin/inquiryTrainer", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryTrainer(@RequestBody RequestDTO<TrainerDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		TrainerDTO trainerDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			trainerDTO = trainerService.getTrainerByUuid(trainerDTO.getTrainerUuid());
			resultDTO.getBody().setData(trainerDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**根据条件分页查询培训师
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/admin/searchTrainer", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchTrainer(@RequestBody RequestDTO<UserSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserSearchDTO userSearchDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			int startIndex = userSearchDTO.getStartIndex();
			int pageSize = userSearchDTO.getPageSize();
			List<TrainerDTO> trainerList = trainerService.searchTrainers(userSearchDTO, startIndex, pageSize);
			int total = trainerService.searchTrainerTotal(userSearchDTO);
			TrainerDTOList trainerDTOList = new TrainerDTOList();
			trainerDTOList.setTrainerList(trainerList);
			trainerDTOList.setTotal(total);
			resultDTO.getBody().setData(trainerDTOList);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**搜索培训师服务的会员
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/admin/searchTrainerUsers", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchTrainerUsers(@RequestBody RequestDTO<TrainerDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		TrainerDTO trainerDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			int startIndex = trainerDTO.getStartIndex();
			int pageSize = trainerDTO.getPageSize();
			List<UserDTO> userList = trainerService.searchTrainerUsers(trainerDTO, startIndex, pageSize);
			int total = trainerService.searchTrainerUsersTotal(trainerDTO);
			UserDTOList userDTOList = new UserDTOList();
			userDTOList.setUserList(userList);
			userDTOList.setTotal(total);
			resultDTO.getBody().setData(userDTOList);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**将会员分配给培训师
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/admin/assignUsersToTrainer", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO assignUsersToTrainer(@RequestBody RequestDTO<TrainerDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		TrainerDTO trainerDTO = requestDTO.getBody();
		List<UserDTO> traineeList = trainerDTO.getTraineeList();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			for (UserDTO trainee : traineeList) {
				trainerService.assignUserToTrainer(trainee, trainerDTO);
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
	
	/**将会员从培训师服务列表中移除
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/admin/removeUsersToTrainer", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO removeUsersToTrainer(@RequestBody RequestDTO<TrainerDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		TrainerDTO trainerDTO = requestDTO.getBody();
		List<UserDTO> traineeList = trainerDTO.getTraineeList();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			for (UserDTO trainee : traineeList) {
				trainerService.removeUserFromTrainer(trainee, trainerDTO);
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
	
	/**将会员升级为培训师
	 * @param requestDTO  - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/admin/upgradeUserToTrainer", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeUserTrainerLevel(@RequestBody RequestDTO<TrainerDTOList> requestDTO) {

		TrainerDTOList trainerDTOList = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			List<TrainerDTO> trainers =  trainerDTOList.getTrainerList();
			for (TrainerDTO trainerDTO : trainers) {
				trainerDTO.setTrainerLevel(UserConstants.TRAINER_LEVEL);
				trainerService.upgradeUserToTrainer(trainerDTO);
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
	
	/**取消会员培训师资格
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/admin/cancelUserFromTrainer", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO cancelUserFromTrainer(@RequestBody RequestDTO<TrainerDTO> requestDTO) {

		TrainerDTO trainerDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			trainerService.cancelUserFromTrainer(trainerDTO);
			
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}
		return resultDTO;
	}
	
	/**分配上级培训师
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/admin/assignParentTrainer", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO assignParentTrainer(@RequestBody RequestDTO<TrainerDTO> requestDTO) {

		TrainerDTO trainerDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			trainerService.assignParentTrainer(trainerDTO);
			
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
