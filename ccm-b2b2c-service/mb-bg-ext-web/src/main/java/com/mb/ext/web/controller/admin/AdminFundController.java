package com.mb.ext.web.controller.admin;

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
import com.mb.ext.core.constant.LogConstants;
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.service.AdminService;
import com.mb.ext.core.service.FundService;
import com.mb.ext.core.service.LogService;
import com.mb.ext.core.service.MerchantService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.AwardSearchDTO;
import com.mb.ext.core.service.spec.MerchantStatementSearchDTO;
import com.mb.ext.core.service.spec.PerformanceSearchDTO;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.UserAwardDTO;
import com.mb.ext.core.service.spec.UserAwardDTOList;
import com.mb.ext.core.service.spec.UserPerformanceDTO;
import com.mb.ext.core.service.spec.UserPerformanceDTOList;
import com.mb.ext.core.service.spec.UserWithdrawDTO;
import com.mb.ext.core.service.spec.UserWithdrawDTOList;
import com.mb.ext.core.service.spec.WithdrawSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantChargeDTO;
import com.mb.ext.core.service.spec.merchant.MerchantChargeDTOList;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.merchant.MerchantStatementDTO;
import com.mb.ext.core.service.spec.merchant.MerchantStatementDTOList;
import com.mb.ext.core.service.spec.merchant.MerchantWithdrawDTO;
import com.mb.ext.core.service.spec.merchant.MerchantWithdrawDTOList;
import com.mb.ext.core.service.spec.merchant.PlatformStatementDTO;
import com.mb.ext.core.service.spec.merchant.PlatformStatementDTOList;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**后台资金管理类
 * @author B2B2C商城
 *
 */
@Controller
public class AdminFundController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	
	@Autowired
	private AdminService adminService;

	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private FundService fundService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;
	
	@Autowired
	private MerchantService merchantService;

	/**审批会员提现
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeUserWithdraw", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeUserWithdraw(@RequestBody RequestDTO<UserWithdrawDTO> requestDTO) {

		UserWithdrawDTO withdrawDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		String actionType = withdrawDTO.getActionType();
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
			
			if("APPROVE".equals(actionType)){
				fundService.approveUserWithdraw(withdrawDTO);
				try{
					withdrawDTO = fundService.getUserWithdrawByUuid(withdrawDTO.getUserWithdrawUuid());
					logService.addSysLog(LogConstants.LOGCATEGORY_FUND, "同意了"+withdrawDTO.getUserDTO().getName()+"的提现申请");
				}catch(Exception e){
					logger.error("记录系统日志错误:"+e.getMessage());
				}
			}else if("REJECT".equals(actionType)){
				fundService.rejectUserWithdraw(withdrawDTO);
				try{
					withdrawDTO = fundService.getUserWithdrawByUuid(withdrawDTO.getUserWithdrawUuid());
					logService.addSysLog(LogConstants.LOGCATEGORY_FUND, "拒绝了"+withdrawDTO.getUserDTO().getName()+"的提现申请,原因是"+withdrawDTO.getRejectReason());
				}catch(Exception e){
					logger.error("记录系统日志错误:"+e.getMessage());
				}
			}else if("SUCCESS".equals(actionType)){
				fundService.successUserWithdraw(withdrawDTO);
				try{
					withdrawDTO = fundService.getUserWithdrawByUuid(withdrawDTO.getUserWithdrawUuid());
					logService.addSysLog(LogConstants.LOGCATEGORY_FUND, withdrawDTO.getUserDTO().getName()+"的提现申请打款成功");
				}catch(Exception e){
					logger.error("记录系统日志错误:"+e.getMessage());
				}
			}else if("FAIL".equals(actionType)){
				fundService.failUserWithdraw(withdrawDTO);
				try{
					withdrawDTO = fundService.getUserWithdrawByUuid(withdrawDTO.getUserWithdrawUuid());
					logService.addSysLog(LogConstants.LOGCATEGORY_FUND, withdrawDTO.getUserDTO().getName()+"的提现申请打款失败, 原因是"+withdrawDTO.getFailReason());
				}catch(Exception e){
					logger.error("记录系统日志错误:"+e.getMessage());
				}
			}else if("COMMENT".equals(actionType)){
				fundService.commentUserWithdraw(withdrawDTO);
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
	
	/**分页查询会员提现记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchUserWithdraw", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchUserWithdraw(@RequestBody RequestDTO<WithdrawSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		WithdrawSearchDTO withdrawSearchDTO = requestDTO.getBody();
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
	
	/**审批商家提现
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeMerchantWithdraw", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeMerchantWithdraw(@RequestBody RequestDTO<MerchantWithdrawDTO> requestDTO) {

		MerchantWithdrawDTO withdrawDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		String actionType = withdrawDTO.getActionType();
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
			
			if("APPROVE".equals(actionType)){
				fundService.approveMerchantWithdraw(withdrawDTO);
				try{
					withdrawDTO = fundService.getMerchantWithdrawByUuid(withdrawDTO.getMerchantWithdrawUuid());
					logService.addSysLog(LogConstants.LOGCATEGORY_FUND, "同意了"+withdrawDTO.getMerchantDTO().getMerchantName()+"的提现申请");
				}catch(Exception e){
					logger.error("记录系统日志错误:"+e.getMessage());
				}
			}else if("REJECT".equals(actionType)){
				fundService.rejectMerchantWithdraw(withdrawDTO);
				try{
					withdrawDTO = fundService.getMerchantWithdrawByUuid(withdrawDTO.getMerchantWithdrawUuid());
					logService.addSysLog(LogConstants.LOGCATEGORY_FUND, "拒绝了"+withdrawDTO.getMerchantDTO().getMerchantName()+"的提现申请,原因是"+withdrawDTO.getRejectReason());
				}catch(Exception e){
					logger.error("记录系统日志错误:"+e.getMessage());
				}
			}else if("SUCCESS".equals(actionType)){
				fundService.successMerchantWithdraw(withdrawDTO);
				try{
					withdrawDTO = fundService.getMerchantWithdrawByUuid(withdrawDTO.getMerchantWithdrawUuid());
					logService.addSysLog(LogConstants.LOGCATEGORY_FUND, withdrawDTO.getMerchantDTO().getMerchantName()+"的提现申请打款成功");
				}catch(Exception e){
					logger.error("记录系统日志错误:"+e.getMessage());
				}
			}else if("FAIL".equals(actionType)){
				fundService.failMerchantWithdraw(withdrawDTO);
				try{
					withdrawDTO = fundService.getMerchantWithdrawByUuid(withdrawDTO.getMerchantWithdrawUuid());
					logService.addSysLog(LogConstants.LOGCATEGORY_FUND, withdrawDTO.getMerchantDTO().getMerchantName()+"的提现申请打款失败, 原因是"+withdrawDTO.getFailReason());
				}catch(Exception e){
					logger.error("记录系统日志错误:"+e.getMessage());
				}
			}else if("COMMENT".equals(actionType)){
				fundService.commentMerchantWithdraw(withdrawDTO);
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
	
	/**分页查询商家提现记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchMerchantWithdraw", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchMerchantWithdraw(@RequestBody RequestDTO<WithdrawSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		WithdrawSearchDTO withdrawSearchDTO = requestDTO.getBody();
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

			List<MerchantWithdrawDTO> merchantWithdrawDTOList = fundService.searchMerchantWithdraw(withdrawSearchDTO,
					withdrawSearchDTO.getStartIndex(), withdrawSearchDTO.getPageSize());
			MerchantWithdrawDTOList list = new MerchantWithdrawDTOList();
			list.setWithdraws(merchantWithdrawDTOList);
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
	
	/**搜索商家资金明细记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchMerchantStatement", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchMerchantStatement(@RequestBody RequestDTO<MerchantStatementSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantStatementSearchDTO searchDTO = requestDTO.getBody();
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
			List<MerchantStatementDTO> statementList = merchantService.searchMerchantStatement(searchDTO);
			int total = merchantService.searchMerchantStatementTotal(searchDTO);
			int totalPoint = merchantService.searchMerchantStatementTotalPoint(searchDTO);
			BigDecimal totalAmount = merchantService.searchMerchantStatementTotalAmount(searchDTO);
			MerchantStatementDTOList statementDTOList = new MerchantStatementDTOList();
			statementDTOList.setStatements(statementList);
			statementDTOList.setTotal(total);
			statementDTOList.setTotalPoint(totalPoint);
			statementDTOList.setTotalAmount(totalAmount);
			resultDTO.getBody().setData(statementDTOList);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**查询商家提现
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryWithdraw", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryWithdraw(@RequestBody RequestDTO<MerchantWithdrawDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantWithdrawDTO withdrawDTO = requestDTO.getBody();
		String actionType =withdrawDTO.getActionType(); 
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
			if(!StringUtils.isEmpty(withdrawDTO.getMerchantWithdrawUuid())){
				withdrawDTO = fundService.getMerchantWithdrawByUuid(withdrawDTO.getMerchantWithdrawUuid());
				resultDTO.getBody().setData(withdrawDTO);
			}else if(withdrawDTO.getMerchantDTO() != null){
				List<MerchantWithdrawDTO> withdrawDTOList = fundService.getMerchantWithdrawByMerchant(withdrawDTO.getMerchantDTO());
				MerchantWithdrawDTOList list = new MerchantWithdrawDTOList(); 
				list.setWithdraws(withdrawDTOList);
				resultDTO.getBody().setData(list);
			}else if("PENDING".equals(actionType)){
				List<MerchantWithdrawDTO> withdrawDTOList = fundService.getPendingVerifyMerchantWithdraw();
				MerchantWithdrawDTOList list = new MerchantWithdrawDTOList(); 
				list.setWithdraws(withdrawDTOList);
				resultDTO.getBody().setData(list);
			}else if("APPROVE".equals(actionType)){
				List<MerchantWithdrawDTO> withdrawDTOList = fundService.getPendingCompleteMerchantWithdraw();
				MerchantWithdrawDTOList list = new MerchantWithdrawDTOList(); 
				list.setWithdraws(withdrawDTOList);
				resultDTO.getBody().setData(list);
			}else if("REJECTED".equals(actionType)){
				List<MerchantWithdrawDTO> withdrawDTOList = fundService.getRejectMerchantWithdraw();
				MerchantWithdrawDTOList list = new MerchantWithdrawDTOList(); 
				list.setWithdraws(withdrawDTOList);
				resultDTO.getBody().setData(list);
			}else if("SUCCESS".equals(actionType)){
				List<MerchantWithdrawDTO> withdrawDTOList = fundService.getSuccessMerchantWithdraw();
				MerchantWithdrawDTOList list = new MerchantWithdrawDTOList(); 
				list.setWithdraws(withdrawDTOList);
				resultDTO.getBody().setData(list);
			}else if("FAIL".equals(actionType)){
				List<MerchantWithdrawDTO> withdrawDTOList = fundService.getFailMerchantWithdraw();
				MerchantWithdrawDTOList list = new MerchantWithdrawDTOList(); 
				list.setWithdraws(withdrawDTOList);
				resultDTO.getBody().setData(list);
			}else{
				List<MerchantWithdrawDTO> withdrawDTOList = fundService.getMerchantWithdraws();
				MerchantWithdrawDTOList list = new MerchantWithdrawDTOList(); 
				list.setWithdraws(withdrawDTOList);
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
	@RequestMapping(value = "/admin/inquiryCharge", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryCharge(@RequestBody RequestDTO<MerchantChargeDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantChargeDTO chargeDTO = requestDTO.getBody();
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
			if(!StringUtils.isEmpty(chargeDTO.getMerchantChargeUuid())){
				chargeDTO = fundService.getMerchantChargeByUuid(chargeDTO.getMerchantChargeUuid());
				resultDTO.getBody().setData(chargeDTO);
			}else if(chargeDTO.getMerchantDTO() != null){
				List<MerchantChargeDTO> chargeDTOList = fundService.getMerchantChargeByMerchant(chargeDTO.getMerchantDTO());
				MerchantChargeDTOList list = new MerchantChargeDTOList(); 
				list.setCharges(chargeDTOList);
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
	
	/**查询商家资金明细
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryStatement", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryStatement(@RequestBody RequestDTO<MerchantStatementDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantStatementDTO merchantStatementDTO = requestDTO.getBody();
		MerchantDTO merchantDTO = merchantStatementDTO.getMerchantDTO();
		Date startDate = merchantStatementDTO.getStartDate();
		Date endDate = merchantStatementDTO.getEndDate();
		String type = merchantStatementDTO.getTransactionType();
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
			MerchantStatementDTOList dtoList = new MerchantStatementDTOList();
			if(merchantDTO != null){
				if(startDate != null && endDate != null){
					List<MerchantStatementDTO> list = fundService.getMerchantStatementByDate(merchantDTO, startDate, endDate);
					dtoList.setStatements(list);
				}
				else{
					List<MerchantStatementDTO> list = fundService.getMerchantStatement(merchantDTO);
					dtoList.setStatements(list);
				}
			}else{
				List<MerchantStatementDTO> list = fundService.getMerchantStatementByTypeDate(type, startDate, endDate);
				BigDecimal tranAmount = fundService.getTranAmountByDateType(startDate, endDate, type);
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
	
	/**查询平台资金明细
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryPlatformStatement", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryPlatformStatement(@RequestBody RequestDTO<PlatformStatementDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		PlatformStatementDTO platformStatementDTO = requestDTO.getBody();
		Date startDate = platformStatementDTO.getStartDate();
		Date endDate = platformStatementDTO.getEndDate();
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
			PlatformStatementDTOList dtoList = new PlatformStatementDTOList();
			List<PlatformStatementDTO> list = fundService.getPlatformStatementByDate(startDate, endDate);
			BigDecimal tranAmount = fundService.getPlatformTranAmountByDate(startDate, endDate);
			dtoList.setStatements(list);
			dtoList.setTotalTranAmount(tranAmount);
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
	
	/**商家资金余额查询
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryBalance", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryBalance(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantDTO merchantDTO = requestDTO.getBody();
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
	
	
	/**分页查询会员奖金明细
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchUserAward", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchUserAward(@RequestBody RequestDTO<AwardSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		AwardSearchDTO awardSearchDTO = requestDTO.getBody();
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
			
			List<UserAwardDTO> userAwardDTOList = fundService.searchUserAward(awardSearchDTO, awardSearchDTO.getStartIndex(), awardSearchDTO.getPageSize());
			UserAwardDTOList list = new UserAwardDTOList();
			list.setAwards(userAwardDTOList);
			int total = fundService.searchUserAwardTotal(awardSearchDTO);
			BigDecimal totalTranAmount = fundService.searchUserAwardAmount(awardSearchDTO);
			list.setTotal(total);
			list.setTotalTranAmount(totalTranAmount);
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
	
	/**分页查询会员团队业绩
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchUserTotalPerformance", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchUserTotalPerformance(@RequestBody RequestDTO<PerformanceSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		PerformanceSearchDTO performanceSearchDTO = requestDTO.getBody();
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
			
			List<UserPerformanceDTO> userPerformanceDTOList = fundService.searchUserTotalPerformance(performanceSearchDTO,performanceSearchDTO.getStartIndex(), performanceSearchDTO.getPageSize());
			BigDecimal totalPerformanceAmount = fundService.searchUserTotalPerformanceAmount(performanceSearchDTO);
			BigDecimal totalPerformanceAward = fundService.searchUserTotalPerformanceAward(performanceSearchDTO);
			UserPerformanceDTOList list = new UserPerformanceDTOList();
			list.setPerformances(userPerformanceDTOList);
			int total = fundService.searchUserTotalPerformanceTotal(performanceSearchDTO);
			list.setTotal(total);
			list.setPerformanceAmount(totalPerformanceAmount);
			list.setPerformanceAward(totalPerformanceAward);
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
