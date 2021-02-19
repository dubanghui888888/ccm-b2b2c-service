package com.mb.ext.web.controller.consumer;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.AuthenticationService;
import com.mb.ext.core.service.CouponService;
import com.mb.ext.core.service.DeliveryService;
import com.mb.ext.core.service.FundService;
import com.mb.ext.core.service.LoginService;
import com.mb.ext.core.service.MerchantService;
import com.mb.ext.core.service.NoteService;
import com.mb.ext.core.service.OSSService;
import com.mb.ext.core.service.PlatformService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.SettingService;
import com.mb.ext.core.service.ShareService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.VerificationService;
import com.mb.ext.core.service.spec.MerchantAssignSearchDTO;
import com.mb.ext.core.service.spec.PublicPaymentInitDTO;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.SignatureDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.UserDTOList;
import com.mb.ext.core.service.spec.UserDeliveryDTO;
import com.mb.ext.core.service.spec.UserDeliveryDTOList;
import com.mb.ext.core.service.spec.UserGivePointDTO;
import com.mb.ext.core.service.spec.UserLevelDTO;
import com.mb.ext.core.service.spec.UserSearchDTO;
import com.mb.ext.core.service.spec.UserStatementDTO;
import com.mb.ext.core.service.spec.UserStatementDTOList;
import com.mb.ext.core.service.spec.UserStatementSearchDTO;
import com.mb.ext.core.service.spec.coupon.CouponSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantAssignDTO;
import com.mb.ext.core.service.spec.merchant.MerchantAssignDTOList;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTOList;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.service.spec.order.UserDeliveryAddressDTO;
import com.mb.ext.core.service.spec.order.UserDeliveryAddressDTOList;
import com.mb.ext.core.service.spec.product.ProductCommentDTO;
import com.mb.ext.core.service.spec.product.ProductCommentDTOList;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.sybpay.SybConstants;
import com.mb.ext.core.sybpay.SybPayService;
import com.mb.ext.core.util.MailSenderUtil;
import com.mb.ext.core.util.OSSUtil;
import com.mb.ext.core.util.PaymentUtil;
import com.mb.ext.core.util.WXJSAPISDKUtility;
import com.mb.ext.core.util.WechatUtil;
import com.mb.ext.msg.WXJSONAccessToken;
import com.mb.ext.msg.WXJSONUserInfo;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**会员业务处理类
 * @author B2B2C商城
 *
 */
@Controller
public class ConsumerController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	@Autowired
	private LoginService loginService;

	@Autowired
	private SybPayService<?> sybPayService;

	@Autowired
	private ShareService shareService;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private FundService fundService;

	@Autowired
	private ProductService productService;

	@Autowired
	private DeliveryService deliveryService;

	@Autowired
	private VerificationService verificationService;

	@Autowired
	private UserService userService;

	@Autowired
	private CouponService couponService;

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
	private NoteService noteService;

	@Autowired
	private TokenCheckUtil tokenCheckUtil;

	@Autowired
	private OSSService ossService;

	@Autowired
	private PlatformService platformService;
	
	@Autowired
	private WechatUtil wechatUtil;

	@Autowired
	private PaymentUtil paymentUtil;
	
	@Autowired
	private WXJSAPISDKUtility wxJsApiSdkUtility;
	
	@Autowired
	private OSSUtil ossUtil;
	
	/**会员注册
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/registerUser", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO registerUser(@RequestBody RequestDTO<UserDTO> requestDTO) {

		UserDTO userDTO = requestDTO.getBody();
		String personalPhone = userDTO.getPersonalPhone();
		String personalPhoneCountryCode = userDTO.getPersonalPhoneCountryCode();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			String code = userDTO.getCode();
			if(!StringUtils.isEmpty(code)) {
				String openId = wechatUtil.getOpenId(code).getOpenId();
				userDTO.setOpenId(openId);
			}
			userService.registerUser(userDTO);
			UserDTO nUserDTO = userService.getUserByMobileNo(personalPhoneCountryCode, personalPhone);

			resultDTO.getBody().setData(nUserDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}

	/**修改会员
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/modifyUser", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO modifyUser(@RequestBody RequestDTO<UserDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserDTO userDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			userService.updateUser(userDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}

	/**更新会员信息
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/updateUserField", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO updateUser(@RequestBody RequestDTO<UserDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserDTO userDTO = requestDTO.getBody();
		String actionType = userDTO.getActionType();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				if(StringUtils.isEmpty(nUserDTO.getPersonalPhone())) {
					nUserDTO.setPersonalPhone(userDTO.getPersonalPhone());
				}
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
				// 通过token id得到用户ID, 然后再更新用户
				userDTO.setUserUuid(nUserDTO.getUserUuid());
				userService.updateUserField(userDTO, actionType);
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

	/**打开消息推送
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/enableNotification", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO enabledNotification(@RequestBody RequestDTO<UserDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserDTO userDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			userService.enableUserNotification(userDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}

	/**关闭消息推送
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/disableNotification", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO disableNotification(@RequestBody RequestDTO<UserDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserDTO userDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			userService.disableUserNotification(userDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}

	/**查询会员信息
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryUser", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryUser(@RequestBody RequestDTO<UserDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserDTO userDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			if (!StringUtils.isEmpty(userDTO.getUserUuid())) {
				userDTO = userService.getUserByUuid(userDTO.getUserUuid());
			}
			else if (!StringUtils.isEmpty(userDTO.getId()))
				userDTO = userService.getUserById(userDTO.getId());
			else if (!StringUtils.isEmpty(userDTO.getPersonalPhone()))
				userDTO = userService.getUserByMobileNo(userDTO.getPersonalPhoneCountryCode(),
						userDTO.getPersonalPhone());
			else if (!StringUtils.isEmpty(userDTO.getTokenId()))
				userDTO = userService.getUserDTOByTokenId(userDTO.getTokenId());
			
			//查询会员有效的优惠券
			CouponSearchDTO searchDTO = new CouponSearchDTO();
			searchDTO.setKeyArray(new String[] {"USER","IS_STARTED","IS_EXPIRED","IS_USED"});
			searchDTO.setUserUuid(userDTO.getUserUuid());
			searchDTO.setStarted(true);
			searchDTO.setExpired(false);
			searchDTO.setUsed(false);
			int couponCount = couponService.searchUserCouponTotal(searchDTO);
			userDTO.setCouponCount(couponCount);
			
			resultDTO.getBody().setData(userDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}

	/**查询商家服务的会员
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryMerchantUser", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryMerchantUser(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantDTO merchantDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			List<UserDTO> userList = userService.getUserByMerchant(merchantDTO);
			UserDTOList list = new UserDTOList();
			list.setUserList(userList);
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

	/**分页查询会员
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/searchUser", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchUser(@RequestBody RequestDTO<UserSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserSearchDTO userSearchDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			int startIndex = userSearchDTO.getStartIndex();
			int pageSize = userSearchDTO.getPageSize();
			List<UserDTO> userList = userService.searchUsers(userSearchDTO, startIndex, pageSize);
			int total = userService.searchUserTotal(userSearchDTO);
			UserDTOList userDTOList = new UserDTOList();
			userDTOList.setUserList(userList);
			userDTOList.setTotal(total);
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

	/**分页查询会员积分划拨
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/searchAssign", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchAssign(@RequestBody RequestDTO<MerchantAssignSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantAssignSearchDTO searchDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
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

	/**分页查询会员资金明细
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/searchStatement", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchStatement(@RequestBody RequestDTO<UserStatementSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserStatementSearchDTO searchDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			List<UserStatementDTO> statementList = userService.searchStatement(searchDTO);
			UserStatementDTOList userStatementDTOList = new UserStatementDTOList();
			userStatementDTOList.setStatements(statementList);
			resultDTO.getBody().setData(userStatementDTOList);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}

	/**会员积分赠送
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/givePoint", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO givePoint(@RequestBody RequestDTO<UserGivePointDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserGivePointDTO userGivePointDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			userService.givePoint(userGivePointDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}
		return resultDTO;
	}

	/**会员提货处理
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/changeUserDelivery", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeUserDelivery(@RequestBody RequestDTO<UserDeliveryDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserDeliveryDTO userDeliveryDTO = requestDTO.getBody();
		String actionType = userDeliveryDTO.getActionType();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			if ("SEND".equals(actionType)) {
				deliveryService.sendUserDelivery(userDeliveryDTO);
			} else if ("COURIER".equals(actionType)) {
				deliveryService.updateUserDeliveryCourier(userDeliveryDTO);
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

	/**修改会员收货地址
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/changeUserDeliveryAddress", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeUserDeliveryAddress(@RequestBody RequestDTO<UserDeliveryAddressDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserDeliveryAddressDTO userDeliveryAddressDTO = requestDTO.getBody();
		String actionType = userDeliveryAddressDTO.getActionType();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			if ("ADD".equals(actionType)) {
				deliveryService.addUserDeliveryAddress(userDeliveryAddressDTO);
			} else if ("MODIFY".equals(actionType)) {
				deliveryService.updateUserDeliveryAddress(userDeliveryAddressDTO);
			} else if ("DELETE".equals(actionType)) {
				deliveryService.deleteUserDeliveryAddress(userDeliveryAddressDTO);
			} else if ("DEFAULT".equals(actionType)) {
				deliveryService.setDefaultUserDeliveryAddress(userDeliveryAddressDTO);
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

	/**查询会员提货记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryUserDelivery", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryUserDelivery(@RequestBody RequestDTO<UserDeliveryDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserDeliveryDTO userDeliveryDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			if (!StringUtils.isEmpty(userDeliveryDTO.getUserDeliveryUuid())) {
				userDeliveryDTO = deliveryService.getDeliveryByUuid(userDeliveryDTO.getUserDeliveryUuid());
				resultDTO.getBody().setData(userDeliveryDTO);
			} else if (userDeliveryDTO.getUserDTO() != null) {
				List<UserDeliveryDTO> userDeliveryDTOList = deliveryService
						.getDeliveryByUser(userDeliveryDTO.getUserDTO());
				UserDeliveryDTOList list = new UserDeliveryDTOList();
				list.setDeliveryList(userDeliveryDTOList);
				resultDTO.getBody().setData(list);
			} else if (userDeliveryDTO.getMerchantDTO() != null) {
				List<UserDeliveryDTO> userDeliveryDTOList = deliveryService
						.getDeliveryByMerchant(userDeliveryDTO.getMerchantDTO());
				UserDeliveryDTOList list = new UserDeliveryDTOList();
				list.setDeliveryList(userDeliveryDTOList);
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

	/**查询附近的商家
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryNearMerchant", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryNearMerchant(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		MerchantDTO merchantDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			List<MerchantDTO> dtoList = merchantService.inquiryNearMerchant(merchantDTO);
			MerchantDTOList list = new MerchantDTOList();
			list.setMerchants(dtoList);
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

	/**商品评论
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/changeProductComment", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeProductComment(@RequestBody RequestDTO<ProductCommentDTOList> requestDTO) {
		ProductCommentDTOList productCommentDTOList = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		String actionType = productCommentDTOList.getActionType();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			List<ProductCommentDTO> commentList = productCommentDTOList.getCommentList();
			for (Iterator<ProductCommentDTO> iterator = commentList.iterator(); iterator.hasNext();) {
				ProductCommentDTO productCommentDTO = iterator.next();
				if ("ADD".equals(actionType)) // 添加评论
					productService.addProductComment(productCommentDTO);
				else if ("MODIFY".equals(actionType)) // 修改评论
					productService.updateProductComment(productCommentDTO);
				else if ("DELETE".equals(actionType)) // 删除评论
					productService.deleteProductComment(productCommentDTO);
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

	/**查询商品评论
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryProductComment", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryProductComment(@RequestBody RequestDTO<ProductDTO> requestDTO) {
		ProductDTO productDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			List<ProductCommentDTO> dtoList = productService.getProductComments(productDTO);
			int total = productService.getProductCommentTotal(productDTO);
			ProductCommentDTOList list = new ProductCommentDTOList();
			list.setCommentList(dtoList);
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

	/**查询会员收货地址
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryUserDeliveryAddress", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryUserDeliveryAddress(@RequestBody RequestDTO<UserDeliveryAddressDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserDeliveryAddressDTO userDeliveryAddressDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			if (!StringUtils.isEmpty(userDeliveryAddressDTO.getUserDeliveryAddressUuid())) {
				userDeliveryAddressDTO = deliveryService
						.inquiryUserDeliveryAddressByUuid(userDeliveryAddressDTO.getUserDeliveryAddressUuid());
				resultDTO.getBody().setData(userDeliveryAddressDTO);
			} else if (userDeliveryAddressDTO.getUserDTO() != null) {
				List<UserDeliveryAddressDTO> userDeliveryDTOList = deliveryService
						.inquiryUserDeliveryAddress(userDeliveryAddressDTO.getUserDTO());
				UserDeliveryAddressDTOList list = new UserDeliveryAddressDTOList();
				list.setAddresses(userDeliveryDTOList);
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

	/**重定向支付地址
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/getPaymentRedirectUrl", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getPaymentRedirectUrl(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		MerchantDTO merchantDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			MerchantDTO xMerchantDTO = new MerchantDTO();
			String paymentRedirectUrl = paymentUtil.getWechatOauth2Redirect().replace("REDIRECTURL", URLEncoder.encode(
					"http://" + paymentUtil.getDomainName() + "/pay?m_id=" + merchantDTO.getMerchantUuid()));
			xMerchantDTO.setPaymentRedirectUrl(paymentRedirectUrl);
			resultDTO.getBody().setData(xMerchantDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (Exception e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(BusinessErrorCode.COMMON_SYSTEM_ERROR);
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, BusinessErrorCode.COMMON_SYSTEM_ERROR);
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}

	/**用户扫码支付时获取用户及商户信息
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/scanPayment", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO scanPayment(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		MerchantDTO merchantDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		String tokenId = requestDTO.getHeader().getTokenId();
		PublicPaymentInitDTO initDTO = new PublicPaymentInitDTO();
		try {
			if (!StringUtils.isEmpty(tokenId)) {
				UserDTO userDTO = userService.getUserDTOByTokenId(tokenId);
				initDTO.setUserDTO(userDTO);
			}
			merchantDTO = merchantService.getMerchantByUuid(merchantDTO.getMerchantUuid());
			initDTO.setMerchantDTO(merchantDTO);
			resultDTO.getBody().setData(initDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}
		return resultDTO;
	}

	/**通过code换取公众号open id
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/getOpenIdByCode4OfficialAccount", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getOpenIdByCode4OfficialAccount(@RequestBody RequestDTO<OrderDTO> requestDTO) {

		OrderDTO orderDTO = requestDTO.getBody();
		String code = orderDTO.getCode();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			WXJSONAccessToken accessToken = wechatUtil.getOpenIdByCode4OfficialAccount(code);
			String openId = accessToken.getOpenId();
			String access_token = accessToken.getAccess_token();

			UserDTO userDTO = new UserDTO();
			userDTO.setOpenId(openId);

			// 获取微信其他信息
			WXJSONUserInfo userInfo = wechatUtil.getWechatUserInfo(access_token, openId);
			userDTO.setPhotoUrl(userInfo.getHeadimgurl());
			userDTO.setName(userInfo.getNickname());
			userDTO.setSex(userInfo.getSex());
			resultDTO.getBody().setData(userDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (Exception e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(BusinessErrorCode.COMMON_SYSTEM_ERROR);
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, BusinessErrorCode.COMMON_SYSTEM_ERROR);
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}
	/*
	 * 聚合支付-微信支付(原生)
	 */
	/*
	 * @RequestMapping(value = "/consumer/initOfflineWechatPay", method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseStatus(HttpStatus.CREATED)
	 * 
	 * @ResponseBody public ResultDTO initOfflineWechatPay(@RequestBody
	 * RequestDTO<PayOrderDTO> requestDTO, HttpServletRequest request) {
	 * 
	 * PayOrderDTO orderDTO = requestDTO.getBody(); ResultDTO resultDTO = new
	 * ResultDTO(); resultDTO.setHeader(requestDTO.getHeader()); String code =
	 * orderDTO.getCode(); String openId = orderDTO.getOpenId(); try {
	 * if(StringUtils.isEmpty(openId)){ WXJSONAccessToken token =
	 * WechatUtil.getOpenIdByCode4OfficialAccount(code); if(token != null){ openId =
	 * token.getOpenId(); } } //创建订单
	 * orderDTO.setPaymentMethod(OrderConstants.ORDER_PAYMENT_METHOD_WECHAT);
	 * orderDTO.setWechatPayUuid(openId); String orderNo =
	 * payOrderService.addPayOrder(orderDTO); orderDTO.setOrderNo(orderNo);
	 * 
	 * //获取最终订单详情 orderDTO = payOrderService.getPayOrderByOrderNo(orderNo);
	 * 
	 * //初始化微信统一下单接口 WechatPayRequest payRequest = new WechatPayRequest();
	 * payRequest.setAppid(WXServiceInfo.APPID_OFFICIAL_ACCOUNT);
	 * payRequest.setMch_id(WXServiceInfo.MERCHANT_ID);
	 * payRequest.setNonce_str(RandomStringUtils.randomAlphanumeric(32));
	 * payRequest.setOut_trade_no(orderDTO.getOrderNo());
	 * payRequest.setBody(WXServiceInfo.PRODUCT_NAME_PAY);
	 * payRequest.setTotal_fee(orderDTO.getActualAmount().multiply(new
	 * BigDecimal(100)) .intValue());
	 * payRequest.setSpbill_create_ip(request.getRemoteAddr());
	 * payRequest.setNotify_url(WXServiceInfo.WECHAT_NOTIFY_URL);
	 * payRequest.setTrade_type("JSAPI");
	 * payRequest.setAttach(WXServiceInfo.PRODUCT_NAME_PAY);
	 * payRequest.setProduct_id(WXServiceInfo.PRODUCT_ID);
	 * payRequest.setOpenid(openId);
	 * payRequest.setSign(WechatUtil.getSign(payRequest)); String requestXML =
	 * WechatUtil.toXML(payRequest); requestXML = new
	 * String(requestXML.getBytes("utf-8"),"iso-8859-1");
	 * logger.info("Wechat unified order request: "+requestXML); //下单 String
	 * wechatResponseStr = WechatUtil.postWechatUnifiedOrder(requestXML);
	 * logger.info("Wechat unified order Response: "+wechatResponseStr);
	 * WechatPayResponse payResponse =
	 * WechatUtil.fromXML2WechatResponse(wechatResponseStr);
	 * if("SUCCESS".equals(payResponse.getReturn_code()) &&
	 * "SUCCESS".equals(payResponse.getResult_code())){
	 * 
	 * SignatureDTO signatureDTO =
	 * WXJSAPISDKUtility.getWXPaySignature(payResponse.getPrepay_id());
	 * signatureDTO.setOut_trade_no(payRequest.getOut_trade_no());
	 * resultDTO.getBody().setData(signatureDTO);
	 * resultDTO.getBody().getStatus().setStatusCode("0"); }else{
	 * resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.
	 * STATUS_CODE_ERROR); resultDTO.getBody().getStatus().setErroCode("1");
	 * resultDTO.getBody().getStatus().setErrorDesc("调用微信支付异常, 请稍后重试"); } } catch
	 * (BusinessException e) {
	 * resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.
	 * STATUS_CODE_ERROR);
	 * resultDTO.getBody().getStatus().setErroCode(e.getErrorCode()); String
	 * errorDesc = MessageHelper.getMessageByErrorId(messageSource,
	 * e.getErrorCode()); resultDTO.getBody().getStatus().setErrorDesc(errorDesc); }
	 * catch (Exception e) { e.printStackTrace(); logger.error(e.getMessage());
	 * resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.
	 * STATUS_CODE_ERROR); resultDTO.getBody().getStatus().setErroCode("1");
	 * resultDTO.getBody().getStatus().setErrorDesc("调用微信支付异常, 请稍后重试"); return
	 * resultDTO; } return resultDTO; }
	 */


	/**获取微信支付签名
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/getSignature", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getSignature(@RequestBody RequestDTO<SignatureDTO> requestDTO) {

		SignatureDTO signatureDTO = requestDTO.getBody();
		String url = signatureDTO.getUrl();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			Map map = wxJsApiSdkUtility.getWXJSSignature(url, paymentUtil.getWechatAppIdOfficialAccount(),
					paymentUtil.getWechatAppSecretOfficialAccount());
			signatureDTO.setAppId(paymentUtil.getWechatAppIdOfficialAccount());
			signatureDTO.setJsapi_ticket((String) map.get("jsapi_ticket"));
			signatureDTO.setSignature((String) map.get("signature"));
			signatureDTO.setNonceStr((String) map.get("nonceStr"));
			signatureDTO.setTimestamp((String) map.get("timestamp"));
			resultDTO.getBody().setData(signatureDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(BusinessErrorCode.COMMON_SYSTEM_ERROR);
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, BusinessErrorCode.COMMON_SYSTEM_ERROR);
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}

	/**获取通联支付的签名
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/getSybSignature", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getSybSignature(@RequestBody RequestDTO<SignatureDTO> requestDTO) {

		SignatureDTO signatureDTO = requestDTO.getBody();
		String url = signatureDTO.getUrl();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			Map map = wxJsApiSdkUtility.getWXJSSignature(url, SybConstants.SYB_APPID, SybConstants.SYB_APPKEY);
			signatureDTO.setAppId(SybConstants.SYB_APPID);
			signatureDTO.setJsapi_ticket((String) map.get("jsapi_ticket"));
			signatureDTO.setSignature((String) map.get("signature"));
			signatureDTO.setNonceStr((String) map.get("nonceStr"));
			signatureDTO.setTimestamp((String) map.get("timestamp"));
			resultDTO.getBody().setData(signatureDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(BusinessErrorCode.COMMON_SYSTEM_ERROR);
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, BusinessErrorCode.COMMON_SYSTEM_ERROR);
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}

	/**上传文件
	 * @param files
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/uploadMediaFile", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject uploadMediaFile(@RequestParam("files") MultipartFile[] files, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			OSSClient client = new OSSClient(ossUtil.getOssEndPoint(), ossUtil.getOssAccessId(), ossUtil.getOssAccessKey());
			if (files != null && files.length > 0) {
				// 循环获取file数组中得文件
				for (int i = 0; i < files.length; i++) {
					MultipartFile file = files[i];
					// 保存文件
					InputStream inputStream = file.getInputStream();
					String key = "share/" + UUID.randomUUID() + '_' + file.getName();
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
			jsonObject.put("errno", 1);
			return jsonObject;
		}
	}
	//上传单个文件, 返回文件地址
	@RequestMapping(value = "/uploadSingleFile", method = RequestMethod.POST)
	@ResponseBody
	public String uploadSingleFile(@RequestParam("files") MultipartFile[] files, HttpServletRequest request,
			HttpServletResponse response) {
		String url = "";
		try {
			OSSClient client = new OSSClient(ossUtil.getOssEndPoint(), ossUtil.getOssAccessId(), ossUtil.getOssAccessKey());
			if (files != null && files.length > 0) {
				MultipartFile file = files[0];
				// 保存文件
				InputStream inputStream = file.getInputStream();
				String key = "share/" + UUID.randomUUID() + '_' + file.getName();
				client.putObject(ossUtil.getOssBucketName(), key, inputStream);
				url = ossService.getUrl(key);
				inputStream.close();
			}
			client.shutdown();

		} catch (Exception e) {
			logger.error("上传文件失败");
		}
		return url;
	}

	
	/**查询会员当前会员及销售数据以及升级到下个等级的数据差额
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/nextUserLevelData", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO nextUserLevelData(@RequestBody RequestDTO<UserDTO> requestDTO) {
		String tokenId = requestDTO.getHeader().getTokenId();
		UserDTO userDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			//直接邀请会员
			int directUserCount = userService.getInvitedUserCount(userDTO);
			//团队会员数
			int teamUserCount = userService.getTeamUserCount(userDTO);
			//个人销售额
			BigDecimal directSaleAmount = userService.getPersonalSaleAmount(userDTO);
			//团队销售额
			BigDecimal teamSaleAmount = userService.getTeamSaleAmount(userDTO);
			
			userDTO = userService.getUserByUuid(userDTO.getUserUuid());
			//下个会员等级
			UserLevelDTO nextUserLevel = userService.getParentUserLevel(userDTO.getUserLevelDTO());
			
			userDTO.setDirectUserCount(directUserCount);
			userDTO.setTeamUserCount(teamUserCount);
			userDTO.setDirectSaleAmount(directSaleAmount);
			userDTO.setTeamSaleAmount(teamSaleAmount);
			userDTO.setUserLevelDTO(nextUserLevel);
			
			resultDTO.getBody().setData(userDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}

		return resultDTO;
	}
	
	/**获取邀请的会员
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryInvitedUsers", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryInvitedUsers(@RequestBody RequestDTO<UserDTO> requestDTO) {
		String tokenId = requestDTO.getHeader().getTokenId();
		UserDTO userDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			List<UserDTO> userDTOList = userService.getInvitedUsers(userDTO);
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

	/*
	 * @RequestMapping(value = "/consumer/uploadMediaFile", method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseBody public JSONObject uploadMediaFile(@RequestParam("file")
	 * MultipartFile[] files, HttpServletRequest request, HttpServletResponse
	 * response) { JSONObject jsonObject=new JSONObject(); JSONArray jsonArray=new
	 * JSONArray(); OutputStream os = null; InputStream inputStream = null; try{
	 * if(files!=null&&files.length>0){ MultipartFile file = files[0]; String folder
	 * = OSSConstants.MEDIA_FOLDER; // 2、保存到临时文件 // 1K的数据缓冲 byte[] bs = new
	 * byte[1024]; // 读取到的数据长度 int len; // 输出的文件流保存到本地文件
	 * 
	 * File tempFile = new File(folder); if (!tempFile.exists()) {
	 * tempFile.mkdirs(); } //保存文件 inputStream = file.getInputStream(); String
	 * fileName = file.getOriginalFilename(); fileName =
	 * UUID.randomUUID().toString()+"_"+fileName; String path = folder+fileName; os
	 * = new FileOutputStream(path); // 开始读取 while ((len = inputStream.read(bs)) !=
	 * -1) { os.write(bs, 0, len); } //根据部署的环境选择domain name String url =
	 * OSSConstants.PRODUCTION_DOMAIN_HTTP+OSSConstants.CONTEXT_NAME+OSSConstants.
	 * MEDIA+fileName; jsonArray.add(url); } jsonObject.put("data",jsonArray);
	 * jsonObject.put("errno",0); return jsonObject; }catch(Exception e){
	 * logger.error(e.getMessage()); e.printStackTrace(); jsonObject.put("errno",1);
	 * return jsonObject; }finally { if(os != null) { try { os.close();
	 * }catch(Exception e) { logger.error(e.getMessage()); e.printStackTrace(); } }
	 * if(inputStream != null) { try { inputStream.close(); }catch(Exception e) {
	 * logger.error(e.getMessage()); e.printStackTrace(); } } } }
	 */
	private TreeMap<String, String> getParams(HttpServletRequest request) {
		TreeMap<String, String> map = new TreeMap<String, String>();
		Map reqMap = request.getParameterMap();
		for (Object key : reqMap.keySet()) {
			String value = ((String[]) reqMap.get(key))[0];
			System.out.println(key + ":" + value);
			logger.info(key + ":" + value);
			map.put(key.toString(), value);
		}
		return map;
	}
}
