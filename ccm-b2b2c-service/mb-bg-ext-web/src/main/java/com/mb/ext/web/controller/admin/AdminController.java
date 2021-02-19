package com.mb.ext.web.controller.admin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.mb.ext.core.constant.AuthenticationConstants;
import com.mb.ext.core.constant.AuthorizationConstants;
import com.mb.ext.core.constant.FundConstants;
import com.mb.ext.core.constant.LogConstants;
import com.mb.ext.core.constant.OrderConstants;
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.dao.SettingDAO;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.AdminService;
import com.mb.ext.core.service.AuthenticationService;
import com.mb.ext.core.service.DeliveryService;
import com.mb.ext.core.service.FundService;
import com.mb.ext.core.service.LogService;
import com.mb.ext.core.service.MerchantService;
import com.mb.ext.core.service.OSSService;
import com.mb.ext.core.service.OrderAfterSaleService;
import com.mb.ext.core.service.OrderService;
import com.mb.ext.core.service.PlatformService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.SettingService;
import com.mb.ext.core.service.SupplierService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.AdminDTOList;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.LogCategoryDTOList;
import com.mb.ext.core.service.spec.MerchantAssignSearchDTO;
import com.mb.ext.core.service.spec.MerchantChargeSearchDTO;
import com.mb.ext.core.service.spec.MerchantSearchDTO;
import com.mb.ext.core.service.spec.OrderAfterSaleSearchDTO;
import com.mb.ext.core.service.spec.OrderSearchDTO;
import com.mb.ext.core.service.spec.ProductCommentSearchDTO;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.SettingDTO;
import com.mb.ext.core.service.spec.SettingDTOList;
import com.mb.ext.core.service.spec.SupplierSearchDTO;
import com.mb.ext.core.service.spec.SysLogDTO;
import com.mb.ext.core.service.spec.SysLogDTOList;
import com.mb.ext.core.service.spec.SysLogSearchDTO;
import com.mb.ext.core.service.spec.UserAwardDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.UserDTOList;
import com.mb.ext.core.service.spec.UserDeliveryDTO;
import com.mb.ext.core.service.spec.UserDeliveryDTOList;
import com.mb.ext.core.service.spec.UserDeliverySearchDTO;
import com.mb.ext.core.service.spec.UserSearchDTO;
import com.mb.ext.core.service.spec.UserStatementDTO;
import com.mb.ext.core.service.spec.UserStatementDTOList;
import com.mb.ext.core.service.spec.UserStatementSearchDTO;
import com.mb.ext.core.service.spec.WithdrawSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantAssignDTO;
import com.mb.ext.core.service.spec.merchant.MerchantAssignDTOList;
import com.mb.ext.core.service.spec.merchant.MerchantChargeDTO;
import com.mb.ext.core.service.spec.merchant.MerchantChargeDTOList;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTOList;
import com.mb.ext.core.service.spec.merchant.PlatformAccountDTO;
import com.mb.ext.core.service.spec.merchant.PlatformAccountDTOList;
import com.mb.ext.core.service.spec.order.DashboardDTO;
import com.mb.ext.core.service.spec.order.OperationStatisticDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.service.spec.order.OrderDTOList;
import com.mb.ext.core.service.spec.order.OrderProductDTO;
import com.mb.ext.core.service.spec.product.ProductBrandDTO;
import com.mb.ext.core.service.spec.product.ProductBrandDTOList;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.service.spec.supplier.SupplierDTO;
import com.mb.ext.core.service.spec.supplier.SupplierDTOList;
import com.mb.ext.core.util.MailSenderUtil;
import com.mb.ext.core.util.OSSUtil;
import com.mb.ext.core.util.SMSSenderUtil;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

/**后台管理类
 * @author B2B2C商城
 *
 */
@Controller
public class AdminController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	@Autowired
	private AdminService adminService;

	@Autowired
	private PlatformService platformService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OSSService ossService;

	@Autowired
	private SettingService settingService;

	@Autowired
	private SettingDAO setttingDAO;

	@Autowired
	private LogService logService;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private DeliveryService deliveryService;

	@Autowired
	private FundService fundService;

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderAfterSaleService orderAfterSaleService;

	@Autowired
	private UserService userService;

	@Autowired
	private MailSenderUtil mailSenderUtil;
	
	@Autowired
	private SMSSenderUtil smsSenderUtil;

	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private TokenCheckUtil tokenCheckUtil;
	
	@Autowired
	private OSSUtil ossUtil;
	
	/**后台登录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/login", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO login(@RequestBody RequestDTO<AdminDTO> requestDTO) {

		AdminDTO adminDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (adminDTO == null) {
				throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_PARAMETER_ERROR);
			}
			String tokenId = adminService.login(adminDTO);
			adminDTO = adminService.getAdminDTOByTokenId(tokenId);
			adminService.setAdminProfile(adminDTO);
			UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			try {
				logService.addSysLog(LogConstants.LOGCATEGORY_ACCOUNT, adminDTO.getId() + "在" + new Date() + "登录系统");
			} catch (Exception e) {
				logger.error("记录系统日志错误:" + e.getMessage());
			}

			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);

			resultDTO.getHeader().setTokenId(tokenId);
			/*
			 * try{ UserDTO nUserDTO = userService.getUserDTO(userDTO);
			 * resultDTO.getBody().setData(nUserDTO); }catch(Exception e){
			 * logger.error(e.getMessage()); }
			 */
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}
		return resultDTO;
	}

	/**后台退出登录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/logout", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO logout(@RequestBody RequestDTO<AdminDTO> requestDTO) {

		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		/*
		 * if(!tokenCheckUtil.checkToken(requestDTO, resultDTO)){ return resultDTO; }
		 */

//		UserDTO userDTO = requestDTO.getBody();

		try {
//			if(userDTO ==null || !userDTO.isIdentified()){
//				throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_PARAMETER_ERROR);
//			}
			adminService.logout(requestDTO.getHeader().getTokenId());
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}

		return resultDTO;
	}

	/**重置后台用户密码
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/resetPassword", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO resetPassword(@RequestBody RequestDTO<AdminDTO> requestDTO) {

		AdminDTO adminDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (adminDTO == null) {
				throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_PARAMETER_ERROR);
			}
			adminService.resetPassword(adminDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}

	/**修改后台用户密码
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changePassword", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changePassword(@RequestBody RequestDTO<AdminDTO> requestDTO) {

		AdminDTO adminDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				adminService.changePassword(adminDTO);
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

	/**修改后台用户信息
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeAdmin", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeAdmin(@RequestBody RequestDTO<AdminDTO> requestDTO) {

		AdminDTO adminDTO = requestDTO.getBody();
		String actionType = adminDTO.getActionType();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				if ("ADD".equals(actionType)) // 添加管理员账户
					adminService.createAdmin(adminDTO);
				else if ("MODIFY".equals(actionType)) // 修改管理员账户
					adminService.updateAdmin(adminDTO);
				else if ("DELETE".equals(actionType)) // 删除管理员账户
					adminService.deleteAdmin(adminDTO);
				else if ("ENABLE".equals(actionType)) // 启用管理用账户
					adminService.enableAdmin(adminDTO);
				else if ("DISABLE".equals(actionType)) // 停用管理用账户
					adminService.disableAdmin(adminDTO);
				else if ("UNLOCK".equals(actionType)) // 解锁管理用账户
					adminService.unlockAdmin(adminDTO);
				else if ("LOCK".equals(actionType)) // 锁定管理用账户
					adminService.lockAdmin(adminDTO);
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

	/**修改后台用户角色
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeAdminRole", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeAdminRole(@RequestBody RequestDTO<AdminDTO> requestDTO) {

		AdminDTO adminDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				adminService.changeAdminRole(adminDTO);
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

	/**查询后台用户
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryAdmin", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryAdmin(@RequestBody RequestDTO<AdminDTO> requestDTO) {

		AdminDTO adminDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		AdminDTOList list = new AdminDTOList();
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				List<AdminDTO> adminDTOList = new ArrayList<AdminDTO>();
				if (!StringUtils.isEmpty(adminDTO.getId())) {
					adminDTO = adminService.getAdminById(adminDTO.getId());
					adminDTOList.add(adminDTO);
				} else {
					adminDTOList = adminService.getAdmins();
				}
				list.setAdmins(adminDTOList);
			}
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

	/**查询供应商
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquirySupplier", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquirySupplier(@RequestBody RequestDTO<SupplierDTO> requestDTO) {

		SupplierDTO supplierDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		SupplierDTOList list = new SupplierDTOList();
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			List<SupplierDTO> supplierDTOList = new ArrayList<SupplierDTO>();
			if (!StringUtils.isEmpty(supplierDTO.getSupplierUuid())) {
				supplierDTO = supplierService.getSupplierByUuid(supplierDTO.getSupplierUuid());
				resultDTO.getBody().setData(supplierDTO);
			} else {
				supplierDTOList = supplierService.getSuppliers();
				list.setSuppliers(supplierDTOList);
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

	/**查询品牌
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryBrand", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryBrand(@RequestBody RequestDTO<ProductBrandDTO> requestDTO) {

		ProductBrandDTO brandDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		ProductBrandDTOList list = new ProductBrandDTOList();
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			List<ProductBrandDTO> brandDTOList = new ArrayList<ProductBrandDTO>();
			if (!StringUtils.isEmpty(brandDTO.getProductBrandUuid())) {
				brandDTO = productService.getProductBrandByUuid(brandDTO.getProductBrandUuid());
				resultDTO.getBody().setData(brandDTO);
			} else {
				brandDTOList = productService.getProductBrands();
				list.setBrands(brandDTOList);
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

	

	/**查询平台银行账户
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryPlatformAccount", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryPlatformAccount(@RequestBody RequestDTO<PlatformAccountDTO> requestDTO) {

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
			List<PlatformAccountDTO> accountDTOList = platformService.inquiryPlatformAccount();
			PlatformAccountDTOList list = new PlatformAccountDTOList();
			list.setAccounts(accountDTOList);
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

	/**更新平台银行账号
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/updatePlatformAccount", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO updatePlatformAccount(@RequestBody RequestDTO<PlatformAccountDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		PlatformAccountDTO accountDTO = requestDTO.getBody();
		String actionType = accountDTO.getActionType();
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
			if ("ADD".equals(actionType)) {
				platformService.addPlatformAccount(accountDTO);
			} else if ("MODIFY".equals(actionType)) {
				platformService.updatePlatformAccount(accountDTO);
			} else if ("DELETE".equals(actionType)) {
				platformService.deletePlatformAccount(accountDTO);
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

	

	/**分页查询会员
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchUser", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchUser(@RequestBody RequestDTO<UserSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserSearchDTO userSearchDTO = requestDTO.getBody();
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
			int startIndex = userSearchDTO.getStartIndex();
			int pageSize = userSearchDTO.getPageSize();
			List<UserDTO> userList = userService.searchUsers(userSearchDTO, startIndex, pageSize);
			int total = userService.searchUserTotal(userSearchDTO);
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

	/**查询未激活会员
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryInactiveUser", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryInactiveUser(@RequestBody RequestDTO<UserDTO> requestDTO) {

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
			List<UserDTO> userList = userService.getInactiveUsers();
			UserDTOList userDTOList = new UserDTOList();
			userDTOList.setUserList(userList);
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
	
	

	/**分页查询供应商
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchSupplier", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchSupplier(@RequestBody RequestDTO<SupplierSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		SupplierSearchDTO supplierSearchDTO = requestDTO.getBody();
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
			List<SupplierDTO> supplierList = supplierService.searchSuppliers(supplierSearchDTO);
			int total = supplierService.searchSupplierTotal(supplierSearchDTO);
			SupplierDTOList supplierDTOList = new SupplierDTOList();
			supplierDTOList.setSuppliers(supplierList);
			supplierDTOList.setTotal(total);
			resultDTO.getBody().setData(supplierDTOList);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}

	/**分页查询商家充值记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchCharge", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchCharge(@RequestBody RequestDTO<MerchantChargeSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantChargeSearchDTO searchDTO = requestDTO.getBody();
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
			List<MerchantChargeDTO> chargeList = merchantService.searchMerchantCharge(searchDTO);
			int total = merchantService.searchMerchantChargeTotal(searchDTO);
			int totalPoint = merchantService.searchMerchantChargeTotalPoint(searchDTO);
			BigDecimal totalAmount = merchantService.searchMerchantChargeTotalAmount(searchDTO);
			MerchantChargeDTOList chargeDTOList = new MerchantChargeDTOList();
			chargeDTOList.setCharges(chargeList);
			chargeDTOList.setTotal(total);
			chargeDTOList.setTotalPoint(totalPoint);
			chargeDTOList.setTotalAmount(totalAmount);
			resultDTO.getBody().setData(chargeDTOList);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}

	/**分页查询会员资金明细
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchUserStatement", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchUserStatement(@RequestBody RequestDTO<UserStatementSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserStatementSearchDTO searchDTO = requestDTO.getBody();
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
			List<UserStatementDTO> dtoList = userService.searchStatement(searchDTO);
			int total = userService.searchUserStatementTotal(searchDTO);
			UserStatementDTOList list = new UserStatementDTOList();
			list.setStatements(dtoList);
			list.setTotal(total);

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

	/**分页查询积分划拨记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchAssign", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchAssign(@RequestBody RequestDTO<MerchantAssignSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantAssignSearchDTO searchDTO = requestDTO.getBody();
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
			List<MerchantAssignDTO> assignList = merchantService.searchMerchantAssign(searchDTO);
			int total = merchantService.searchMerchantAssignTotal(searchDTO);
			int totalPoint = merchantService.searchMerchantAssignTotalPoint(searchDTO);
			BigDecimal totalAmount = merchantService.searchMerchantAssignTotalAmount(searchDTO);
			MerchantAssignDTOList assignDTOList = new MerchantAssignDTOList();
			assignDTOList.setAssignList(assignList);
			assignDTOList.setTotal(total);
			assignDTOList.setTotalPoint(totalPoint);
			assignDTOList.setTotalAmount(totalAmount);
			resultDTO.getBody().setData(assignDTOList);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}

	/**设置提现手续费
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/setCommissionFee", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO setCommissionFee(@RequestBody RequestDTO<SettingDTOList> requestDTO) {

		String entId = requestDTO.getHeader().getEntId();
		String tokenId = requestDTO.getHeader().getTokenId();
		SettingDTOList settingDTOList = requestDTO.getBody();
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
			settingService.updateSettingList(settingDTOList);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}

	/**查询提现手续费
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryCommissionFee", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryCommissionFee(@RequestBody RequestDTO<SettingDTO> requestDTO) {

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
			SettingDTO settingDTO1 = settingService.getSettingByName("COMMISSIONFEE_PLATFORM_PERCENTAGE");

			SettingDTO settingDTO2 = settingService.getSettingByName("COMMISSIONFEE_MERCHANT_PERCENTAGE");

			List<SettingDTO> list = new ArrayList<SettingDTO>();
			if (settingDTO1 != null)
				list.add(settingDTO1);
			if (settingDTO2 != null)
				list.add(settingDTO2);
			SettingDTOList dtoList = new SettingDTOList();
			dtoList.setSettings(list);
			resultDTO.getBody().setData(dtoList);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}

	/**添加商家
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/registerMerchant", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO registerMerchant(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		MerchantDTO merchantDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		String tokenId = requestDTO.getHeader().getTokenId();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			merchantDTO.setReferrer("聚能积分平台");
			String merchantUuid = merchantService.createMerchant(merchantDTO);

			try {
				logService.addSysLog(LogConstants.LOGCATEGORY_MERCHANT,
						"注册商家为" + "名称:" + merchantDTO.getMerchantName() + ";联系人:" + merchantDTO.getContactName()
								+ ";联系电话:" + merchantDTO.getMobileNo() + ";地址:" + merchantDTO.getMerchantAddress()
								+ ";推荐人:" + merchantDTO.getReferrer());
			} catch (Exception e) {
				logger.error("记录系统日志错误:" + e.getMessage());
			}

			merchantDTO.setMerchantUuid(merchantUuid);
			resultDTO.getBody().setData(merchantDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}
		return resultDTO;
	}

	/**修改供应商
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeSupplier", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeSupplier(@RequestBody RequestDTO<SupplierDTO> requestDTO) {

		SupplierDTO supplierDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		String tokenId = requestDTO.getHeader().getTokenId();
		resultDTO.setHeader(requestDTO.getHeader());
		String actionType = supplierDTO.getActionType();
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if ("ADD".equals(actionType)) {
				supplierService.createSupplier(supplierDTO);
				try {
					logService.addSysLog(LogConstants.LOGCATEGORY_SUPPLIER,
							"注册供应商为" + "名称:" + supplierDTO.getSupplierName() + ";联系人:" + supplierDTO.getContactName()
									+ ";联系电话:" + supplierDTO.getMobileNo() + ";地址:" + supplierDTO.getSupplierAddress());
				} catch (Exception e) {
					logger.error("记录系统日志错误:" + e.getMessage());
				}
			} else if ("MODIFY".equals(actionType)) {
				supplierService.updateSupplier(supplierDTO);
				try {
					logService.addSysLog(LogConstants.LOGCATEGORY_SUPPLIER,
							"修改供应商为" + "名称:" + supplierDTO.getSupplierName() + ";联系人:" + supplierDTO.getContactName()
									+ ";联系电话:" + supplierDTO.getMobileNo() + ";地址:" + supplierDTO.getSupplierAddress());
				} catch (Exception e) {
					logger.error("记录系统日志错误:" + e.getMessage());
				}
			} else if ("DELETE".equals(actionType)) {
				supplierDTO = supplierService.getSupplierByUuid(supplierDTO.getSupplierUuid());
				supplierService.deleteSupplier(supplierDTO);
				try {
					logService.addSysLog(LogConstants.LOGCATEGORY_SUPPLIER,
							"删除供应商" + "名称:" + supplierDTO.getSupplierName() + ";联系人:" + supplierDTO.getContactName()
									+ ";联系电话:" + supplierDTO.getMobileNo() + ";地址:" + supplierDTO.getSupplierAddress());
				} catch (Exception e) {
					logger.error("记录系统日志错误:" + e.getMessage());
				}
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

	/**修改商家信息
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeMerchant", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeMerchant(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		MerchantDTO merchantDTO = requestDTO.getBody();
		String actionType = merchantDTO.getActionType();
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

			if ("ADD".equals(actionType)) // 添加商户账户
				merchantService.createMerchant(merchantDTO);
			else if ("MODIFY".equals(actionType)) // 修改商户账户
				merchantService.updateMerchant(merchantDTO);
			else if ("DELETE".equals(actionType)) // 删除商户账户
				merchantService.deleteMerchant(merchantDTO);
			else if ("CLOSE".equals(actionType)) { // 关闭商户账户
				merchantService.closeMerchant(merchantDTO);
				merchantDTO = merchantService.getMerchantByUuid(merchantDTO.getMerchantUuid());
				try {
					logService.addSysLog(LogConstants.LOGCATEGORY_MERCHANT,
							"关闭商家" + merchantDTO.getMerchantName() + "(" + merchantDTO.getMobileNo() + ")");
				} catch (Exception e) {
					logger.error("记录系统日志错误:" + e.getMessage());
				}
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

	/**搜索会员提货记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchUserDelivery", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchUserDelivery(@RequestBody RequestDTO<UserDeliverySearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserDeliverySearchDTO userDeliverySearchDTO = requestDTO.getBody();
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

			List<UserDeliveryDTO> userDeliveryDTOList = deliveryService.searchUserDelivery(userDeliverySearchDTO,
					userDeliverySearchDTO.getStartIndex(), userDeliverySearchDTO.getPageSize());
			UserDeliveryDTOList list = new UserDeliveryDTOList();
			list.setDeliveryList(userDeliveryDTOList);
			int total = deliveryService.searchUserDeliveryTotal(userDeliverySearchDTO);
			list.setTotal(total);
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

	/**提货发货
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeUserDelivery", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeUserDelivery(@RequestBody RequestDTO<UserDeliveryDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserDeliveryDTO userDeliveryDTO = requestDTO.getBody();
		String actionType = userDeliveryDTO.getActionType();
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
			if ("SEND".equals(actionType)) {
				deliveryService.sendUserDelivery(userDeliveryDTO);
			} else if ("COURIER".equals(actionType)) {
				deliveryService.updateUserDeliveryCourier(userDeliveryDTO);
			} else if ("COMMENT".equals(actionType)) {
				deliveryService.commentUserDelivery(userDeliveryDTO);
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

	/**修改会员奖金
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeUserAward", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeUserAward(@RequestBody RequestDTO<UserAwardDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserAwardDTO userAwardDTO = requestDTO.getBody();
		String actionType = userAwardDTO.getActionType();
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
			if ("COMMENT".equals(actionType)) {
				fundService.commentUserAward(userAwardDTO);
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

	/**分页查询会员订单
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchUserOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchUserOrder(@RequestBody RequestDTO<UserDTO> requestDTO) {

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
			OrderSearchDTO searchDTO = new OrderSearchDTO();
			searchDTO.setUserUuid(userDTO.getUserUuid());
			String[] keyArray = new String[] { "USER" };
			searchDTO.setKeyArray(keyArray);
			List<OrderDTO> orderDTOList = orderService.searchOrders(searchDTO, userDTO.getStartIndex(),
					userDTO.getPageSize());
			OrderDTOList list = new OrderDTOList();
			list.setOrders(orderDTOList);
			int total = orderService.searchOrderTotal(searchDTO);
			list.setTotal(total);
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

	/**搜索会员所属的商家
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchUserMerchant", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchUserMerchant(@RequestBody RequestDTO<UserDTO> requestDTO) {

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
			List<MerchantDTO> merchantDTOList = merchantService.searchMerchantByUser(userDTO, userDTO.getStartIndex(),
					userDTO.getPageSize());
			MerchantDTOList list = new MerchantDTOList();
			list.setMerchants(merchantDTOList);
			int total = merchantService.searchMerchantByUserTotal(userDTO);
			list.setTotal(total);
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
	
	/**管理后台仪表盘
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/dashboard", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO dashboard(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

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
			
			UserSearchDTO searchDTO = new UserSearchDTO();
			searchDTO.setKeyArray(new String[] {});
			Date today = new Date();
			Date yesterday = DateUtils.addDays(today, -1);
			Date day7Before = DateUtils.addDays(today, -6);
			Date dayLongBefore = DateUtils.addYears(today, -10);
			DashboardDTO dashboardDTO = new DashboardDTO();
			
			//会员总数
			int totalUserCount = userService.searchUserTotal(new UserSearchDTO());
			dashboardDTO.setTotalUserCount(totalUserCount);
			
			//商品总数
			int totalProductCount = productService.searchProductTotal(new ProductSearchDTO());
			dashboardDTO.setTotalProductCount(totalProductCount);
			
			//商家总数
			int totalMerchantCount = merchantService.searchMerchantTotal(new MerchantSearchDTO());
			dashboardDTO.setTotalMerchantCount(totalMerchantCount);
			
			//今日会员总数
			int userCount = userService.getIncrementUserCountByDate(today, today);
			dashboardDTO.setUserCount(userCount);
			//计算增长率
			int userCountYesterday = userService.getIncrementUserCountByDate(yesterday, yesterday);
			int userGrowthCount = userCount - userCountYesterday;
			float userGrowthRate = userGrowthCount/(userCountYesterday==0?1:userCountYesterday);
			dashboardDTO.setUserCountGrowthRate(userGrowthRate);
			
			//今日商家总数
			int merchantCount = merchantService.getIncrementMerchantCountByDate(today, today);
			dashboardDTO.setMerchantCount(merchantCount);
			//计算增长率
			int merchantCountYesterday = merchantService.getIncrementMerchantCountByDate(yesterday, yesterday);
			int merchantGrowthCount = merchantCount - merchantCountYesterday;
			float merchantGrowthRate = merchantGrowthCount/(merchantCountYesterday==0?1:merchantCountYesterday);
			dashboardDTO.setMerchantCountGrowthRate(merchantGrowthRate);
			
			//今日订单总数
			List<String> orderStatusList = new ArrayList<String>();
			orderStatusList.add(OrderConstants.ORDER_STATUS_NOT_DELIVERIED);
			orderStatusList.add(OrderConstants.ORDER_STATUS_DELIVERIED);
			orderStatusList.add(OrderConstants.ORDER_STATUS_COMPLETED);
			orderStatusList.add(OrderConstants.ORDER_STATUS_EVALUATED);
			OrderSearchDTO orderSearchDTO = new OrderSearchDTO();
			orderSearchDTO.setKeyArray(new String[] { "ORDERDATE","ORDER_STATUS_LIST" });
			orderSearchDTO.setOrderDateStart(today);
			orderSearchDTO.setOrderDateEnd(today);
			orderSearchDTO.setOrderStatusList(orderStatusList);
			int orderCount = orderService.searchOrderTotal(orderSearchDTO);
			dashboardDTO.setOrderCount(orderCount);
			
			//计算增长率
			orderSearchDTO.setOrderDateStart(yesterday);
			orderSearchDTO.setOrderDateEnd(yesterday);
			int orderCountYesterday = orderService.searchOrderTotal(orderSearchDTO);
			int orderGrowthCount = orderCount - orderCountYesterday;
			float orderCountGrowthRate = orderGrowthCount/(orderCountYesterday==0?1:orderCountYesterday);
			dashboardDTO.setOrderCountGrowthRate(orderCountGrowthRate);
			
			//近7日订单数
			orderSearchDTO.setOrderDateStart(day7Before);
			orderSearchDTO.setOrderDateEnd(today);
			int orderCountDay7 = orderService.searchOrderTotal(orderSearchDTO);
			dashboardDTO.setOrderCountDay7(orderCountDay7);
			
			//今日订单金额
			orderSearchDTO.setOrderDateStart(today);
			orderSearchDTO.setOrderDateEnd(today);
			BigDecimal orderAmount = orderService.searchOrderTotalAmount(orderSearchDTO);
			dashboardDTO.setOrderAmount(orderAmount);
			//计算增长率
			orderSearchDTO.setOrderDateStart(yesterday);
			orderSearchDTO.setOrderDateEnd(yesterday);
			BigDecimal orderAmountYesterday = orderService.searchOrderTotalAmount(orderSearchDTO);
			BigDecimal orderAmountGrowth = orderAmount.subtract(orderAmountYesterday);
			BigDecimal orderAmountGrowthRate = orderAmountGrowth.divide(orderAmountYesterday.compareTo(BigDecimal.valueOf(0))==0?BigDecimal.valueOf(1):orderAmountYesterday, 2, BigDecimal.ROUND_HALF_UP);
			dashboardDTO.setOrderAmountGrowthRate(orderAmountGrowthRate.floatValue());
			
			//近7日订单金额
			orderSearchDTO.setOrderDateStart(day7Before);
			orderSearchDTO.setOrderDateEnd(today);
			BigDecimal orderAmountDay7 = orderService.searchOrderTotalAmount(orderSearchDTO);
			dashboardDTO.setOrderAmountDay7(orderAmountDay7);
			
			//奖金金额
			BigDecimal awardAmount = fundService.getAwardAmountByDate(today, today);
			dashboardDTO.setAwardAmount(awardAmount);
			
			//计算增长率
			BigDecimal awardAmountYesterday = fundService.getAwardAmountByDate(yesterday, yesterday);
			BigDecimal awardAmountGrowth = awardAmount.subtract(awardAmountYesterday);
			BigDecimal awardAmountGrowthRate = awardAmountGrowth.divide(awardAmountYesterday.compareTo(BigDecimal.valueOf(0))==0?BigDecimal.valueOf(1):awardAmountYesterday, 2, BigDecimal.ROUND_HALF_UP);
			dashboardDTO.setAwardAmountGrowthRate(awardAmountGrowthRate.floatValue());
			
			//商家提现中金额
			WithdrawSearchDTO withdrawSearchDTO = new WithdrawSearchDTO();
			withdrawSearchDTO.setKeyArray(new String[] {"WITHDRAWSTATUS"});
			String[] withdrawStatusArray = {FundConstants.WITHDRAW_STATUS_APPLICATED,FundConstants.WITHDRAW_STATUS_APPROVED};
			withdrawSearchDTO.setWithdrawStatusArray(withdrawStatusArray);
			BigDecimal merchantWithdrawAmount = fundService.searchMerchantWithdrawTotalAmount(withdrawSearchDTO);
			dashboardDTO.setMerchantWithdrawAmount(merchantWithdrawAmount);
			
			//会员总佣金 
			BigDecimal userAwardAmount = fundService.getAwardAmountByDate(DateUtils.addYears(today, -10), today);
			dashboardDTO.setUserAwardAmount(userAwardAmount);
			
			//订单总金额
			orderSearchDTO.setKeyArray(new String[] {"ORDER_STATUS_LIST" });
			BigDecimal totalOrderAmount = orderService.searchOrderTotalAmount(orderSearchDTO);
			dashboardDTO.setTotalOrderAmount(totalOrderAmount);
			
			//今日爆款Top 5
			List<OrderProductDTO> productList = orderService.getTopxProductByUnit(today, today, 5);
			dashboardDTO.setTopUnitProductList(productList);
			
			orderSearchDTO.setKeyArray(new String[] { "ORDERDATE","ORDERSTATUS" });
			orderSearchDTO.setOrderDateStart(dayLongBefore);
			orderSearchDTO.setOrderDateEnd(today);
			//待付款订单
			orderSearchDTO.setOrderStatus(OrderConstants.ORDER_STATUS_NOT_PAYED);
			int toPayOrderCount = orderService.searchOrderTotal(orderSearchDTO);
			dashboardDTO.setToPayOrderCount(toPayOrderCount);
			//待发货订单
			orderSearchDTO.setOrderStatus(OrderConstants.ORDER_STATUS_NOT_DELIVERIED);
			int toDeliverOrderCount = orderService.searchOrderTotal(orderSearchDTO);
			dashboardDTO.setToDeliverOrderCount(toDeliverOrderCount);
			//待审核退款
			OrderAfterSaleSearchDTO orderAfterSaleSearchDTO = new OrderAfterSaleSearchDTO();
			orderAfterSaleSearchDTO.setKeyArray(new String[] { "STATUS"});
			orderAfterSaleSearchDTO.setStatus(OrderConstants.ORDER_AFTER_SALE_STATUS_CREATE);
			int orderCreateAfterSaleCount = orderAfterSaleService.searchOrderAfterSaleTotal(orderAfterSaleSearchDTO);
			dashboardDTO.setToReviewAfterSaleCount(orderCreateAfterSaleCount);
			//待收货退款
			orderAfterSaleSearchDTO.setStatus(OrderConstants.ORDER_AFTER_SALE_STATUS_WAITING_CONFIRM_COURIER);
			int orderConfirmAfterSaleCount = orderAfterSaleService.searchOrderAfterSaleTotal(orderAfterSaleSearchDTO);
			dashboardDTO.setToConfirmAfterSaleCount(orderConfirmAfterSaleCount);
			
			//近7日退款金额
			orderAfterSaleSearchDTO.setKeyArray(new String[] { "APPLICATION_TIME"});
			orderAfterSaleSearchDTO.setApplicationDateStart(day7Before);
			orderAfterSaleSearchDTO.setApplicationDateEnd(today);
			BigDecimal afterSaleAmountDay7 = orderAfterSaleService.searchOrderAfterSaleAmount(orderAfterSaleSearchDTO);
			dashboardDTO.setAfterSaleAmountDay7(afterSaleAmountDay7);
			
			//待回复商品评论
			ProductCommentSearchDTO commentSearchDTO = new ProductCommentSearchDTO();
			commentSearchDTO.setKeyArray(new String[] {"NOTREPLIED"});
			int toReplyProductCommentCount = productService.searchProductCommentTotal(commentSearchDTO, false);
			dashboardDTO.setToReplyProductCommentCount(toReplyProductCommentCount);
			
			//待审核会员提现申请
			WithdrawSearchDTO vWithdrawSearchDTO = new WithdrawSearchDTO();
			vWithdrawSearchDTO.setKeyArray(new String[] {"WITHDRAWSTATUS"});
			vWithdrawSearchDTO.setWithdrawStatusArray(new String[] {FundConstants.WITHDRAW_STATUS_APPLICATED});
			int toVerifyWithdrawCount = fundService.searchUserWithdrawTotal(vWithdrawSearchDTO);
			dashboardDTO.setToVerifyWithdrawCount(toVerifyWithdrawCount);
			
			//待打款会员提现申请
			WithdrawSearchDTO pWithdrawSearchDTO = new WithdrawSearchDTO();
			pWithdrawSearchDTO.setKeyArray(new String[] {"WITHDRAWSTATUS"});
			pWithdrawSearchDTO.setWithdrawStatusArray(new String[] {FundConstants.WITHDRAW_STATUS_APPROVED});
			int toPayWithdrawCount = fundService.searchUserWithdrawTotal(pWithdrawSearchDTO);
			dashboardDTO.setToPayWithdrawCount(toPayWithdrawCount);
					
			//待审核商家提现申请
			WithdrawSearchDTO mVWithdrawSearchDTO = new WithdrawSearchDTO();
			mVWithdrawSearchDTO.setKeyArray(new String[] {"WITHDRAWSTATUS"});
			mVWithdrawSearchDTO.setWithdrawStatusArray(new String[] {FundConstants.WITHDRAW_STATUS_APPLICATED});
			int toVerifyMerchantWithdrawCount = fundService.searchMerchantWithdrawTotal(mVWithdrawSearchDTO);
			dashboardDTO.setToVerifyMerchantWithdrawCount(toVerifyMerchantWithdrawCount);
			
			//待打款商家提现申请
			WithdrawSearchDTO mPWithdrawSearchDTO = new WithdrawSearchDTO();
			mPWithdrawSearchDTO.setKeyArray(new String[] {"WITHDRAWSTATUS"});
			mPWithdrawSearchDTO.setWithdrawStatusArray(new String[] {FundConstants.WITHDRAW_STATUS_APPROVED});
			int toPayMerchantWithdrawCount = fundService.searchMerchantWithdrawTotal(mPWithdrawSearchDTO);
			dashboardDTO.setToPayMerchantWithdrawCount(toPayMerchantWithdrawCount);
			
			resultDTO.getBody().setData(dashboardDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**管理后台运营统计分析
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/operation/statistic", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO statistic(@RequestBody RequestDTO<OperationStatisticDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OperationStatisticDTO operationDTO = requestDTO.getBody();
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
			
			Date startDate = operationDTO.getStartDate();
			Date endDate = operationDTO.getEndDate();
			
			UserSearchDTO searchDTO = new UserSearchDTO();
			searchDTO.setKeyArray(new String[] {});
			
			//会员总数
			int userCount = userService.getIncrementUserCountByDate(startDate, endDate);
			operationDTO.setUserCount(userCount);
			
			//商家总数
			int merchantCount = merchantService.getIncrementMerchantCountByDate(startDate, endDate);
			operationDTO.setMerchantCount(merchantCount);
			
			//订单总数
			OrderSearchDTO orderSearchDTO = new OrderSearchDTO();
			orderSearchDTO.setKeyArray(new String[] { "ORDERDATE" });
			orderSearchDTO.setOrderDateStart(startDate);
			orderSearchDTO.setOrderDateEnd(endDate);
			int orderCount = orderService.searchOrderTotal(orderSearchDTO);
			operationDTO.setOrderCount(orderCount);
			
			//订单金额
			BigDecimal orderAmount = orderService.getOrderProductAmountTotal(startDate, endDate);
			operationDTO.setOrderAmount(orderAmount);
			
			//奖金支出金额
			BigDecimal awardAmount = fundService.getAwardAmountByDate(startDate, endDate);
			operationDTO.setAwardAmount(awardAmount);
			
			//商品销量Top 5
			List<OrderProductDTO> unitProductList = orderService.getTopxProductByUnit(startDate, endDate, 5);
			operationDTO.setTopUnitProductList(unitProductList);
			
			//商品销售额Top 5
			List<OrderProductDTO> amountProductList = orderService.getTopxProductByAmount(startDate, endDate, 5);
			operationDTO.setTopAmountProductList(amountProductList);
			
			//商家订单数量Top 5
			OrderSearchDTO merchantSearchDTO = new OrderSearchDTO();
			merchantSearchDTO.setKeyArray(new String[] {"ORDERDATE"});
			merchantSearchDTO.setOrderDateStart(startDate);
			merchantSearchDTO.setOrderDateEnd(endDate);
			merchantSearchDTO.setStartIndex(0);
			merchantSearchDTO.setPageSize(5);
			List<MerchantDTO> unitMerchantList = orderService.searchTopxMerchantByUnit(merchantSearchDTO);
			operationDTO.setTopUnitMerchantList(unitMerchantList);
			
			//商家订单金额Top 5
			List<MerchantDTO> amountMerchantList = orderService.searchTopxMerchantByAmount(merchantSearchDTO);
			operationDTO.setTopAmountMerchantList(amountMerchantList);
			
			// 获取会员增长曲线
			List<ChartDTO> userChart = userService.getIncrementUserCountChartByDate(startDate, endDate);
			operationDTO.setUserCountChart(userChart);
			
			// 获取商家增长曲线
			List<ChartDTO> merchantChart = merchantService.getIncrementMerchantCountChartByDate(startDate, endDate);
			operationDTO.setMerchantCountChart(merchantChart);
			
			// 获取订单数量增长曲线
			List<ChartDTO> orderCountChart = orderService.getIncrementOrderCountChart(startDate, endDate);
			operationDTO.setOrderCountChart(orderCountChart);
			
			// 获取订单金额增长曲线
			List<ChartDTO> productAmountChart = orderService.getIncrementOrderAmountChart(startDate, endDate);
			operationDTO.setOrderAmountChart(productAmountChart);
			resultDTO.getBody().setData(operationDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**增删改日志
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeSysLog", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeSysLog(@RequestBody RequestDTO<SysLogDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		SysLogDTO sysLogDTO = requestDTO.getBody();
		String actionType = sysLogDTO.getActionType();
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
			if ("ADD".equals(actionType)) {
				logService.addMannualLog(sysLogDTO);
			} else if ("DELETE".equals(actionType)) {
				logService.deleteSysLog(sysLogDTO);
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

	/**更新商品
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/updateProduct", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO updateProduct(@RequestBody RequestDTO<ProductDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		ProductDTO productDTO = requestDTO.getBody();
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
			productService.updateProduct(productDTO);
			logService.addSysLog(LogConstants.LOGCATEGORY_PRODUCT, "修改了商品");
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}

	/**查询日志类型
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchSysLog", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchSysLog(@RequestBody RequestDTO<SysLogSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		SysLogSearchDTO sysLogSearchDTO = requestDTO.getBody();
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
			int startIndex = sysLogSearchDTO.getStartIndex();
			int pageSize = sysLogSearchDTO.getPageSize();
			List<SysLogDTO> logList = logService.searchSysLog(sysLogSearchDTO, startIndex, pageSize);
			int total = logService.searchSysLogTotal(sysLogSearchDTO);
			SysLogDTOList logDTOList = new SysLogDTOList();
			logDTOList.setLogs(logList);
			logDTOList.setTotal(total);
			resultDTO.getBody().setData(logDTOList);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}

	/**查询日志类型
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquirySysLogCategory", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquirySysLogCategory(@RequestBody RequestDTO<SysLogSearchDTO> requestDTO) {

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

			String[] logCategories = new String[] { LogConstants.LOGCATEGORY_ACCOUNT,
					LogConstants.LOGCATEGORY_PERMISSION, LogConstants.LOGCATEGORY_SETTING,
					LogConstants.LOGCATEGORY_MEMBER, LogConstants.LOGCATEGORY_MERCHANT,
					LogConstants.LOGCATEGORY_SUPPLIER, LogConstants.LOGCATEGORY_ORDER, };
			LogCategoryDTOList categories = new LogCategoryDTOList();
			categories.setCategories(logCategories);
			resultDTO.getBody().setData(categories);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}

	/**修改会员积分
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeUserPoint", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeUserPoint(@RequestBody RequestDTO<UserDTO> requestDTO) {

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
				try {
					logService.addSysLog(LogConstants.LOGCATEGORY_ACCOUNT,
							adminDTO.getId() + "将会员(" + userDTO.getPersonalPhone() + ")的积分"
									+ "修改为"
									+ userDTO.getUserBalanceDTO().getAvailablePoint());
				} catch (Exception e) {
					logger.error("记录系统日志错误:" + e.getMessage());
				}
			}
			userService.updateUserPoint(userDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}
		return resultDTO;
	}

	/**修改会员资金余额
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeUserBalance", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeUserBalance(@RequestBody RequestDTO<UserDTO> requestDTO) {

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
			userService.updateUserBalance(userDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}
		return resultDTO;
	}

	/**上传文件
	 * @param files
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/uploadMediaFile", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject uploadFile(@RequestParam("file") MultipartFile[] files, HttpServletRequest request,
			HttpServletResponse response) {
		String tokenId = request.getParameter("tokenId");
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		AdminDTO adminDTO = null;
		// 检查token id
		try {
			adminDTO = adminService.getAdminDTOByTokenId(tokenId);
			if (adminDTO == null) {
				logger.error("Invalid token " + tokenId);
				jsonObject.put("errno", 1);
				return jsonObject;
			} else {
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			jsonObject.put("errno", 1);
			return jsonObject;
		}
		try {
			OSSClient client = new OSSClient(ossUtil.getOssEndPoint(), ossUtil.getOssAccessId(), ossUtil.getOssAccessKey());
			if (files != null && files.length > 0) {
				// 循环获取file数组中得文件
				for (int i = 0; i < files.length; i++) {
					MultipartFile file = files[i];
					// 保存文件
					InputStream inputStream = file.getInputStream();
					String key = "admin/" + UUID.randomUUID() + '_' + file.getName();
					client.putObject(ossUtil.getOssBucketName(), key, inputStream);
					String url = ossService.getUrl(key);
					jsonArray.add(url);
					inputStream.close();
				}
			}
			client.shutdown();
			jsonObject.put("data", jsonArray);
			jsonObject.put("errno", 0);
			return jsonObject;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonObject.put("errno", 1);
			return jsonObject;
		}
	}

	/**导入订单物流信息
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/admin/importLogistics", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO importLogistics(@RequestParam("orderFile") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		ResultDTO resultDTO = new ResultDTO();
		try (CSVReader csvReader = new CSVReaderBuilder(
				new BufferedReader(new InputStreamReader(file.getInputStream(), "utf-8"))).build()) {
			Iterator<String[]> iterator = csvReader.iterator();
			String errorDesc = "";
			int i = 0;
			while (iterator.hasNext()) {
				String[] line = iterator.next();
				if (line.length != 3 && i == 0) {
					resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
					resultDTO.getBody().getStatus().setErrorDesc("表格数据列数不符合标准");
					break;
				} else if (i != 0 && line.length == 3) {
					String orderNo = line[0].trim();
					String courierName = line[1].trim();
					String courierNo = line[2].trim();
					OrderDTO orderDTO = new OrderDTO();
					orderDTO.setOrderNo(orderNo);
					orderDTO.setCourierName(courierName);
					orderDTO.setCourierNo(courierNo);
					try {
						orderService.importLogistics(orderDTO);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.error("记录系统日志错误:" + e.getMessage() + orderNo);
						errorDesc = errorDesc + orderNo + ",";
						resultDTO.getBody().getStatus().setErrorDesc("未找到这些订单号：" + errorDesc);
					}
					resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
				}
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultDTO;
	}

	/**获取商家收款二维码地址
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/payQrCode", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO payQrCode(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		MerchantDTO merchantDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		String tokenId = requestDTO.getHeader().getTokenId();
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			MerchantDTO xMerchantDTO = new MerchantDTO();
			String qrCode = merchantService.generateMerchantPaymentQrCode(merchantDTO);
			xMerchantDTO.setPaymentQrCode(qrCode);
			resultDTO.getBody().setData(xMerchantDTO);
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
