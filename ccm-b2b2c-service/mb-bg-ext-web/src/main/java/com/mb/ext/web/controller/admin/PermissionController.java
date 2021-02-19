package com.mb.ext.web.controller.admin;

import java.util.Iterator;
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
import com.mb.ext.core.constant.LogConstants;
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.AdminAuthenticationService;
import com.mb.ext.core.service.AdminAuthorizationService;
import com.mb.ext.core.service.AdminService;
import com.mb.ext.core.service.LogService;
import com.mb.ext.core.service.LoginService;
import com.mb.ext.core.service.RoleService;
import com.mb.ext.core.service.VerificationService;
import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.EntDTO;
import com.mb.ext.core.service.spec.FunctionDTO;
import com.mb.ext.core.service.spec.FunctionDTOList;
import com.mb.ext.core.service.spec.PermissionDTO;
import com.mb.ext.core.service.spec.PermissionDTOList;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.RoleDTO;
import com.mb.ext.core.service.spec.RoleListDTO;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**后台权限管理类
 * @author B2B2C商城
 *
 */
@Controller
public class PermissionController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private AdminService adminService;

	@Autowired
	private VerificationService verificationService;

	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private AdminAuthorizationService adminAuthorizationService;
	
	@Autowired
	private AdminAuthenticationService adminAuthenticationService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;

	/**查询角色
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryRole", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryRole(@RequestBody RequestDTO<EntDTO> requestDTO) {

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
			List<PermissionDTO> permissionDTOList = adminAuthorizationService.getAllPermissions();
			PermissionDTOList listDTO = new PermissionDTOList();
			listDTO.setRoles(permissionDTOList);
			resultDTO.getBody().setData(listDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**查询系统功能
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryFunction", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryFunction(@RequestBody RequestDTO<PermissionDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		PermissionDTO permissionDTO = requestDTO.getBody();
		String roleName = permissionDTO.getRoleName();
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
			FunctionDTOList listDTO = new FunctionDTOList();
			if(StringUtils.isEmpty(roleName)){
				List<FunctionDTO> functionDTOList = adminAuthorizationService.getFunctions();
				listDTO.setFunctionList(functionDTOList);
			}else{
				permissionDTO = adminAuthorizationService.getPermissionByRoleName(roleName);
				listDTO.setFunctionList(permissionDTO.getFunctionList());
			}
			
			resultDTO.getBody().setData(listDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**查询后台会员的授权功能
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryAdminFunction", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryAdminFunction(@RequestBody RequestDTO<AdminDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		AdminDTO adminDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			FunctionDTOList listDTO = new FunctionDTOList();
			List<FunctionDTO> functions = adminAuthorizationService.getPermissions(adminDTO);
			listDTO.setFunctionList(functions);
			resultDTO.getBody().setData(listDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**删除角色
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/deleteRoles", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO deleteRoles(@RequestBody RequestDTO<RoleListDTO> requestDTO) {

		RoleListDTO roleListDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		List<PermissionDTO> permissonDTOList = roleListDTO.getRoles();
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
			for(Iterator<PermissionDTO> iter = permissonDTOList.iterator();iter.hasNext();){
				PermissionDTO permissionDTO = iter.next();
				String roleName = permissionDTO.getRoleName();
				RoleDTO roleDTO = new RoleDTO();
				roleDTO.setName(roleName);
				roleService.deleteRole(roleDTO);
			}
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
	
	/**修改角色
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeRole", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeRole(@RequestBody RequestDTO<PermissionDTO> requestDTO) {

		PermissionDTO permissionDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		String actionType = permissionDTO.getActionType();
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setName(permissionDTO.getRoleName());
		roleDTO.setDesc(permissionDTO.getRoleDesc());
		roleDTO.setNewName(permissionDTO.getNewRoleName());
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
			if("ADD".equals(actionType)){
				roleService.addRole(roleDTO);
//				adminAuthorizationService.grantPermissions(permissionDTO);
			}else if("MODIFY".equals(actionType)){
				roleService.updateRole(roleDTO);
//				permissionDTO.setRoleName(roleDTO.getNewName());
//				adminAuthorizationService.grantPermissions(permissionDTO);
			}else if("DELETE".equals(actionType)){
				roleService.deleteRole(roleDTO);
			}
			
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
	
	/**修改角色授权
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changePermission", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changePermission(@RequestBody RequestDTO<PermissionDTO> requestDTO) {

		PermissionDTO permissionDTO = requestDTO.getBody();
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
			adminAuthorizationService.grantPermissions(permissionDTO);
			
			try{
				logService.addSysLog(LogConstants.LOGCATEGORY_PERMISSION, "修改了系统权限");
			}catch(Exception e){
				logger.error("记录系统日志错误:"+e.getMessage());
			}
			
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
	
	

}
