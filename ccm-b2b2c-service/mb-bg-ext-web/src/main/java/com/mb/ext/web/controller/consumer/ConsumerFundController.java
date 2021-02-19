package com.mb.ext.web.controller.consumer;

import java.util.Date;
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
import com.mb.ext.core.service.FundService;
import com.mb.ext.core.service.MerchantService;
import com.mb.ext.core.service.PlatformService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.AwardSearchDTO;
import com.mb.ext.core.service.spec.PerformanceSearchDTO;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.UserAwardDTO;
import com.mb.ext.core.service.spec.UserAwardDTOList;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.UserPerformanceDTO;
import com.mb.ext.core.service.spec.UserPerformanceDTOList;
import com.mb.ext.core.service.spec.UserStatementDTO;
import com.mb.ext.core.service.spec.UserStatementDTOList;
import com.mb.ext.core.service.spec.UserStatementSearchDTO;
import com.mb.ext.core.service.spec.UserWithdrawDTO;
import com.mb.ext.core.service.spec.UserWithdrawDTOList;
import com.mb.ext.core.service.spec.WithdrawSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantAccountDTO;
import com.mb.ext.core.service.spec.merchant.MerchantAccountDTOList;
import com.mb.ext.core.service.spec.merchant.MerchantChargeDTO;
import com.mb.ext.core.service.spec.merchant.MerchantChargeDTOList;
import com.mb.ext.core.service.spec.merchant.MerchantWithdrawDTO;
import com.mb.ext.core.service.spec.merchant.PlatformAccountDTO;
import com.mb.ext.core.service.spec.merchant.PlatformAccountDTOList;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**会员资金业务类
 * @author B2B2C商城
 *
 */
@Controller
public class ConsumerFundController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private FundService fundService;
	
	@Autowired
	private PlatformService platformService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;

	/**添加，修改，删除银行账户
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/changeFinAccount", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeFinAccount(@RequestBody RequestDTO<MerchantAccountDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantAccountDTO accountDTO = requestDTO.getBody();
		String actionType = accountDTO.getActionType();
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
			if("ADD".equals(actionType)){
				fundService.addMerchantAccount(accountDTO);
			}else if("MODIFY".equals(actionType)){
				fundService.updateMerchantAccount(accountDTO);
			}else if("DELETE".equals(actionType)){
				fundService.deleteMerchantAccount(accountDTO);
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
	
	/**查询银行账户
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryFinAccount", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryFinAccount(@RequestBody RequestDTO<MerchantAccountDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantAccountDTO accountDTO = requestDTO.getBody();
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
			if(!StringUtils.isEmpty(accountDTO.getMerchantAccountUuid())){
				accountDTO = fundService.getMerchantAccountByUuid(accountDTO.getMerchantAccountUuid());
				resultDTO.getBody().setData(accountDTO);
			}else if(accountDTO.getMerchantDTO()!=null){
				List<MerchantAccountDTO> dtoList = fundService.getMerchantAccount(accountDTO.getMerchantDTO());
				MerchantAccountDTOList list = new MerchantAccountDTOList();
				list.setAccounts(dtoList);
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
	
	/**查询平台收款账号
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryPlatformAccount", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryPlatformAccount(@RequestBody RequestDTO<PlatformAccountDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		PlatformAccountDTO accountDTO = requestDTO.getBody();
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
			if(!StringUtils.isEmpty(accountDTO.getPlatformAccountUuid())){
				accountDTO = platformService.inquiryPlatformAccountByUid(accountDTO.getPlatformAccountUuid());
				resultDTO.getBody().setData(accountDTO);
			}else{
				List<PlatformAccountDTO> dtoList = platformService.inquiryPlatformAccount();
				PlatformAccountDTOList list = new PlatformAccountDTOList();
				list.setAccounts(dtoList);
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
	
	/**查询会员提现记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryUserWithdraw", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryUserWithdraw(@RequestBody RequestDTO<UserWithdrawDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserWithdrawDTO withdrawDTO = requestDTO.getBody();
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
			if(!StringUtils.isEmpty(withdrawDTO.getUserWithdrawUuid())){
				withdrawDTO = fundService.getUserWithdrawByUuid(withdrawDTO.getUserWithdrawUuid());
				resultDTO.getBody().setData(withdrawDTO);
			}else{
				List<UserWithdrawDTO> dtoList =  fundService.getUserWithdrawByUser(withdrawDTO.getUserDTO());;
				UserWithdrawDTOList list = new UserWithdrawDTOList();
				list.setWithdraws(dtoList);
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
	
	/**查询商家充值记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryCharge", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryCharge(@RequestBody RequestDTO<MerchantWithdrawDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantWithdrawDTO withdrawDTO = requestDTO.getBody();
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
			if(!StringUtils.isEmpty(withdrawDTO.getMerchantWithdrawUuid())){
				withdrawDTO = fundService.getMerchantWithdrawByUuid(withdrawDTO.getMerchantWithdrawUuid());
				resultDTO.getBody().setData(withdrawDTO);
			}else{
				List<MerchantChargeDTO> dtoList =  fundService.getMerchantChargeByMerchant(withdrawDTO.getMerchantDTO());;
				MerchantChargeDTOList list = new MerchantChargeDTOList();
				list.setCharges(dtoList);
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
	
	
	/**查询会员资金明细
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryStatement", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryStatement(@RequestBody RequestDTO<UserStatementDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserStatementDTO userStatementDTO = requestDTO.getBody();
		String transactionType = userStatementDTO.getTransactionType();
		UserDTO userDTO = userStatementDTO.getUserDTO();
		Date startDate = userStatementDTO.getStartDate();
		Date endDate = userStatementDTO.getEndDate();
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
			UserStatementDTOList dtoList = new UserStatementDTOList();
			if(!StringUtils.isEmpty(transactionType)){
				List<UserStatementDTO> list = fundService.getUserStatementByType(userDTO, transactionType);
				dtoList.setStatements(list);
			}
			else if(startDate != null && endDate != null){
				List<UserStatementDTO> list = fundService.getUserStatementByDate(userDTO, startDate, endDate);
				dtoList.setStatements(list);
			}
			else{
				List<UserStatementDTO> list = fundService.getUserStatement(userDTO);
				dtoList.setStatements(list);
			}
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
	
	/**查询会员奖金明细
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryUserAward", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryUserAward(@RequestBody RequestDTO<UserAwardDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserAwardDTO userAwardDTO = requestDTO.getBody();
		String transactionType = userAwardDTO.getTransactionType();
		UserDTO userDTO = userAwardDTO.getUserDTO();
		Date startDate = userAwardDTO.getStartDate();
		Date endDate = userAwardDTO.getEndDate();
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
			UserAwardDTOList dtoList = new UserAwardDTOList();
			if(!StringUtils.isEmpty(userAwardDTO.getUserAwardUuid())){
				userAwardDTO = fundService.getUserAwardByUuid(userAwardDTO.getUserAwardUuid());
				resultDTO.getBody().setData(userAwardDTO);
			}
			else if(!StringUtils.isEmpty(transactionType)){
				List<UserAwardDTO> list = fundService.getUserAwardByType(userDTO, transactionType);
				dtoList.setAwards(list);
				resultDTO.getBody().setData(dtoList);
			}
			else if(startDate != null && endDate != null){
				List<UserAwardDTO> list = fundService.getUserAwardByDate(userDTO, startDate, endDate);
				dtoList.setAwards(list);
				resultDTO.getBody().setData(dtoList);
			}
			else{
				List<UserAwardDTO> list = fundService.getUserAward(userDTO);
				dtoList.setAwards(list);
				resultDTO.getBody().setData(dtoList);
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
	
	/**分页查询会员奖金记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/searchUserAward", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchUserAward(@RequestBody RequestDTO<AwardSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		AwardSearchDTO awardSearchDTO = requestDTO.getBody();
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
			
			List<UserAwardDTO> userAwardDTOList = fundService.searchUserAward(awardSearchDTO, awardSearchDTO.getStartIndex(), awardSearchDTO.getPageSize());
			UserAwardDTOList list = new UserAwardDTOList();
			list.setAwards(userAwardDTOList);
			int total = fundService.searchUserAwardTotal(awardSearchDTO);
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
	
	/**分页查询会员余额明细
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/searchUserStatement", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchUserStatement(@RequestBody RequestDTO<UserStatementSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserStatementSearchDTO searchDTO = requestDTO.getBody();
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
			
			List<UserStatementDTO> userStatementDTOList = userService.searchStatement(searchDTO);
			UserStatementDTOList list = new UserStatementDTOList();
			list.setStatements(userStatementDTOList);
			int total = userService.searchUserStatementTotal(searchDTO);
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
	
	/**分页查询会员团队业绩月记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/searchUserPerformance", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchUserPerformance(@RequestBody RequestDTO<PerformanceSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		PerformanceSearchDTO performanceSearchDTO = requestDTO.getBody();
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
			
			List<UserPerformanceDTO> userPerformanceDTOList = fundService.searchUserPerformance(performanceSearchDTO,performanceSearchDTO.getStartIndex(), performanceSearchDTO.getPageSize());
			UserPerformanceDTOList list = new UserPerformanceDTOList();
			list.setPerformances(userPerformanceDTOList);
			int total = fundService.searchUserPerformanceTotal(performanceSearchDTO);
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
	
	/**分页查询会员提现记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/searchUserWithdraw", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchUserWithdraw(@RequestBody RequestDTO<WithdrawSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		WithdrawSearchDTO withdrawSearchDTO = requestDTO.getBody();
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

			List<UserWithdrawDTO> userWithdrawDTOList = fundService.searchUserWithdraw(withdrawSearchDTO,
					withdrawSearchDTO.getStartIndex(), withdrawSearchDTO.getPageSize());
			UserWithdrawDTOList list = new UserWithdrawDTOList();
			list.setWithdraws(userWithdrawDTOList);
			int total = fundService.searchUserWithdrawTotal(withdrawSearchDTO);
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
	
	/**会员申请提现
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/applyWithdraw", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO applyWithdraw(@RequestBody RequestDTO<UserWithdrawDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserWithdrawDTO withdrawDTO = requestDTO.getBody();
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
			fundService.applyUserWithdraw(withdrawDTO);
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
