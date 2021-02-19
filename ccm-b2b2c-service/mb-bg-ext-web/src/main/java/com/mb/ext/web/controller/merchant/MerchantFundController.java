package com.mb.ext.web.controller.merchant;

import java.math.BigDecimal;
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
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.FundService;
import com.mb.ext.core.service.MerchantService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.WithdrawSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantAccountDTO;
import com.mb.ext.core.service.spec.merchant.MerchantAccountDTOList;
import com.mb.ext.core.service.spec.merchant.MerchantChargeDTO;
import com.mb.ext.core.service.spec.merchant.MerchantChargeDTOList;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.merchant.MerchantStatementDTO;
import com.mb.ext.core.service.spec.merchant.MerchantStatementDTOList;
import com.mb.ext.core.service.spec.merchant.MerchantWithdrawDTO;
import com.mb.ext.core.service.spec.merchant.MerchantWithdrawDTOList;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.util.WechatUtil;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**商家资金处理类
 * @author B2B2C商城
 *
 */
@Controller
public class MerchantFundController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WechatUtil wechatUtil;

	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private FundService fundService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;

	
	
	/**商家申请提现
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/applyWithdraw", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO applyWithdraw(@RequestBody RequestDTO<MerchantWithdrawDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantWithdrawDTO withdrawDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			fundService.applyMerchantWithdraw(withdrawDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**分页查询提现记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/searchMerchantWithdraw", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchMerchantWithdraw(@RequestBody RequestDTO<WithdrawSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		WithdrawSearchDTO withdrawSearchDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}

			List<MerchantWithdrawDTO> merchantWithdrawDTOList = fundService.searchMerchantWithdraw(withdrawSearchDTO,
					withdrawSearchDTO.getStartIndex(), withdrawSearchDTO.getPageSize());
			MerchantWithdrawDTOList list = new MerchantWithdrawDTOList();
			list.setWithdraws(merchantWithdrawDTOList);
			int total = fundService.searchMerchantWithdrawTotal(withdrawSearchDTO);
			list.setTotal(total);
			BigDecimal totalAmount = fundService.searchMerchantWithdrawTotalAmount(withdrawSearchDTO);
			list.setTotalAmount(totalAmount);
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
	
	/**添加，修改，删除银行账户
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/changeFinAccount", method = RequestMethod.POST)
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
	@RequestMapping(value = "/merchant/inquiryFinAccount", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryFinAccount(@RequestBody RequestDTO<MerchantAccountDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantAccountDTO accountDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
/*			if(!tokenCheckUtil.checkToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}*/
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
	
	/**查询提现记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/inquiryWithdraw", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryWithdraw(@RequestBody RequestDTO<MerchantWithdrawDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantWithdrawDTO withdrawDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			if(!StringUtils.isEmpty(withdrawDTO.getMerchantWithdrawUuid())){
				withdrawDTO = fundService.getMerchantWithdrawByUuid(withdrawDTO.getMerchantWithdrawUuid());
				resultDTO.getBody().setData(withdrawDTO);
			}else{
				List<MerchantWithdrawDTO> dtoList =  fundService.getMerchantWithdrawByMerchant(withdrawDTO.getMerchantDTO());;
				MerchantWithdrawDTOList list = new MerchantWithdrawDTOList();
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
	
	/**查询充值记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/inquiryCharge", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryCharge(@RequestBody RequestDTO<MerchantWithdrawDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantWithdrawDTO withdrawDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
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
	
	/**查询商家资金余额
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/inquiryBalance", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryBalance(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantDTO merchantDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			merchantDTO = fundService.getMerchantBalance(merchantDTO);
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
	
	/**查询会员资金明细记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/inquiryStatement", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryStatement(@RequestBody RequestDTO<MerchantStatementDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantStatementDTO merchantStatementDTO = requestDTO.getBody();
		String transactionType = merchantStatementDTO.getTransactionType();
		MerchantDTO merchantDTO = merchantStatementDTO.getMerchantDTO();
		Date startDate = merchantStatementDTO.getStartDate();
		Date endDate = merchantStatementDTO.getEndDate();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			MerchantStatementDTOList dtoList = new MerchantStatementDTOList();
			if(!StringUtils.isEmpty(transactionType)){
				List<MerchantStatementDTO> list = fundService.getMerchantStatementByType(merchantDTO, transactionType);
				dtoList.setStatements(list);
			}
			else if(startDate != null && endDate != null){
				List<MerchantStatementDTO> list = fundService.getMerchantStatementByDate(merchantDTO, startDate, endDate);
				dtoList.setStatements(list);
			}
			else{
				List<MerchantStatementDTO> list = fundService.getMerchantStatement(merchantDTO);
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
	
	/**根据微信code换取open id
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/getOpenIdByCode", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getOpenIdByCode(@RequestBody RequestDTO<OrderDTO> requestDTO) {

		OrderDTO orderDTO = requestDTO.getBody();
		String code = orderDTO.getCode();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			String openId = wechatUtil.getOpenId(code).getOpenId();
			UserDTO userDTO = new UserDTO();
			userDTO.setOpenId(openId);
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
	
	}
