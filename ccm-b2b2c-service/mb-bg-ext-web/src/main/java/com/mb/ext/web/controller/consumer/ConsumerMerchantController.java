package com.mb.ext.web.controller.consumer;

import java.util.ArrayList;
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
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.service.AuthenticationService;
import com.mb.ext.core.service.CouponService;
import com.mb.ext.core.service.DeliveryService;
import com.mb.ext.core.service.FundService;
import com.mb.ext.core.service.LoginService;
import com.mb.ext.core.service.MerchantService;
import com.mb.ext.core.service.OSSService;
import com.mb.ext.core.service.PointService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.SettingService;
import com.mb.ext.core.service.ShareService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.VerificationService;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTOList;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.sybpay.SybPayService;
import com.mb.ext.core.util.MailSenderUtil;
import com.mb.ext.core.util.PosterUtil;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**会员商品处理类
 * @author 社区团购
 *
 */
@Controller
public class ConsumerMerchantController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

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
	private PosterUtil posterUtil;

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
	
	@Autowired
	private OSSService ossService;
	
	@Autowired
	private PointService pointService;

	/**查询商家信息
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryMerchant", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryMerchant(@RequestBody RequestDTO<MerchantDTO> requestDTO) {
		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantDTO merchantDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		MerchantDTOList list = new MerchantDTOList();
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			List<MerchantDTO> merchantDTOList = new ArrayList<MerchantDTO>();
			if (!StringUtils.isEmpty(merchantDTO.getMerchantUuid())) {
				merchantDTO = merchantService.getMerchantByUuid(merchantDTO.getMerchantUuid());
				resultDTO.getBody().setData(merchantDTO);
			} else {
				merchantDTOList = merchantService.getMerchants();
				list.setMerchants(merchantDTOList);
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

	/**关注商家
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/followMerchant", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO followMerchant(@RequestBody RequestDTO<OrderDTO> requestDTO) {
		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantDTO merchantDTO = requestDTO.getBody().getMerchantDTO();
		UserDTO userDTO = requestDTO.getBody().getUserDTO();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		MerchantDTOList list = new MerchantDTOList();
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			userService.followMerchant(merchantDTO,userDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}
		return resultDTO;
	}

	/**取消关注商家
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/cancelFollowMerchant", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO cancelFollowMerchant(@RequestBody RequestDTO<OrderDTO> requestDTO) {
		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantDTO merchantDTO = requestDTO.getBody().getMerchantDTO();
		UserDTO userDTO = requestDTO.getBody().getUserDTO();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		MerchantDTOList list = new MerchantDTOList();
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			userService.cancelFollowMerchant(merchantDTO,userDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}
		return resultDTO;
	}

	/**是否已关注商家
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/isMerchantFollowed", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO isMerchantFollowed(@RequestBody RequestDTO<OrderDTO> requestDTO) {
		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantDTO merchantDTO = requestDTO.getBody().getMerchantDTO();
		UserDTO userDTO = requestDTO.getBody().getUserDTO();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		MerchantDTOList list = new MerchantDTOList();
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			boolean isMerchantFollowed = userService.isMerchantFollowed(merchantDTO,userDTO);
			merchantDTO.setFollowed(isMerchantFollowed);
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

	/**查询我关注的商家
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryFollowedMerchant", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryFollowedMerchant(@RequestBody RequestDTO<UserDTO> requestDTO) {
		String tokenId = requestDTO.getHeader().getTokenId();
		UserDTO userDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		MerchantDTOList list = new MerchantDTOList();
		try {
			if (!tokenCheckUtil.checkToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			List<MerchantDTO> dtoList = merchantService.getFollowedMerchantByUser(userDTO);
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
	
}
