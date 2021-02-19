package com.mb.ext.web.controller.consumer;

import java.util.Date;
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
import com.mb.ext.core.service.spec.UserPointStatementDTO;
import com.mb.ext.core.service.spec.UserPointStatementDTOList;
import com.mb.ext.core.service.spec.UserSignDTO;
import com.mb.ext.core.service.spec.UserSignDTOList;
import com.mb.ext.core.service.spec.UserStatementSearchDTO;
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
public class ConsumerPointController {

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
	
	/**积分签到
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/signPoint", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO signPoint(@RequestBody RequestDTO<UserDTO> requestDTO){
		UserDTO userDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
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
			int point = pointService.signPoint(userDTO);
			UserSignDTO signDTO = new UserSignDTO();
			signDTO.setSignPoint(point);
			resultDTO.getBody().setData(signDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**判断用户某天是否签到
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/isPointSigned", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO isPointSigned(@RequestBody RequestDTO<UserSignDTO> requestDTO){
		UserSignDTO userSignDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
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
			boolean isSigned = pointService.isUserSigned(userSignDTO.getUserDTO(), userSignDTO.getSignTime());
			userSignDTO.setSigned(isSigned);
			resultDTO.getBody().setData(userSignDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**查询用户已经连续第几天签到
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/getSignDateNum", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getSignNum(@RequestBody RequestDTO<UserDTO> requestDTO){
		UserDTO userDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
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
			int num = pointService.getSignedDateNum(userDTO,new Date());
			userDTO.setSignDateNum(num);
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
	
	/**查询用户签到记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/getUserSign", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getUserSign(@RequestBody RequestDTO<UserDTO> requestDTO){
		UserDTO userDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
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
			List<UserSignDTO> userSignList = pointService.getSignByUser(userDTO);
			UserSignDTOList list = new UserSignDTOList();
			list.setSigns(userSignList);
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
	
	/**查询用户积分明细
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/searchUserPointStatement", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchUserPointStatement(@RequestBody RequestDTO<UserStatementSearchDTO> requestDTO){
		UserStatementSearchDTO searchDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
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
			List<UserPointStatementDTO> userStatementList = pointService.searchUserPointStatement(searchDTO);
			int total = pointService.searchUserPointStatementTotal(searchDTO);
			UserPointStatementDTOList list = new UserPointStatementDTOList();
			list.setStatements(userStatementList);
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
	
}
