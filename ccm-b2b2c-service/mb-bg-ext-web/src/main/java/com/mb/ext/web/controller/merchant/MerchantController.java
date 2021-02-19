package com.mb.ext.web.controller.merchant;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
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
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.AuthenticationService;
import com.mb.ext.core.service.CouponService;
import com.mb.ext.core.service.FundService;
import com.mb.ext.core.service.LogService;
import com.mb.ext.core.service.MerchantService;
import com.mb.ext.core.service.OSSService;
import com.mb.ext.core.service.OrderAfterSaleService;
import com.mb.ext.core.service.OrderService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.SettingService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.VerificationService;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.MerchantAssignSearchDTO;
import com.mb.ext.core.service.spec.MerchantChargeSearchDTO;
import com.mb.ext.core.service.spec.MerchantStatementSearchDTO;
import com.mb.ext.core.service.spec.OrderAfterSaleSearchDTO;
import com.mb.ext.core.service.spec.OrderSearchDTO;
import com.mb.ext.core.service.spec.ProductCommentSearchDTO;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.SettingDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.UserDTOList;
import com.mb.ext.core.service.spec.UserSearchDTO;
import com.mb.ext.core.service.spec.WithdrawSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantAssignDTO;
import com.mb.ext.core.service.spec.merchant.MerchantAssignDTOList;
import com.mb.ext.core.service.spec.merchant.MerchantChargeDTO;
import com.mb.ext.core.service.spec.merchant.MerchantChargeDTOList;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTOList;
import com.mb.ext.core.service.spec.merchant.MerchantShopperDTO;
import com.mb.ext.core.service.spec.merchant.MerchantShopperDTOList;
import com.mb.ext.core.service.spec.merchant.MerchantStatementDTO;
import com.mb.ext.core.service.spec.merchant.MerchantStatementDTOList;
import com.mb.ext.core.service.spec.order.DashboardDTO;
import com.mb.ext.core.service.spec.order.OperationStatisticDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.service.spec.order.OrderProductDTO;
import com.mb.ext.core.util.MailSenderUtil;
import com.mb.ext.core.util.OSSUtil;
import com.mb.ext.core.util.PaymentUtil;
import com.mb.ext.core.util.WechatUtil;
import com.mb.ext.msg.WXJSONAccessToken;
import com.mb.ext.msg.WXJSONUserInfo;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**商家业务处理类
 * @author B2B2C商城
 *
 */
@Controller
public class MerchantController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());
	
	@Autowired
	private WechatUtil wechatUtil;
	
	@Autowired
	private PaymentUtil paymentUtil;

	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderAfterSaleService orderAfterSaleService;
	
	@Autowired
	private FundService fundService;
	
	@Autowired
	private SettingService settingService;
	
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
	private TokenCheckUtil tokenCheckUtil;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private VerificationService verificationService;
	
	@Autowired
	private OSSUtil ossUtil;

	@Autowired
	private OSSService ossService;
	
	/**商家登录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/login", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO login(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		MerchantDTO merchantDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(merchantDTO ==null){
				throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_PARAMETER_ERROR);
			}
			String tokenId = merchantService.login(merchantDTO);
			merchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
			merchantService.setMerchantProfile(merchantDTO);
			UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			try{
				logService.addSysLog(LogConstants.LOGCATEGORY_ACCOUNT, merchantDTO.getMobileNo()+"在"+new Date()+"登录商家后台");
			}catch(Exception e){
				logger.error("记录系统日志错误:"+e.getMessage());
			}
			merchantDTO = merchantService.getMerchantByMobileNo(merchantDTO.getMobileNo());
			resultDTO.getBody().setData(merchantDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
			resultDTO.getHeader().setTokenId(tokenId);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}
		return resultDTO;
	}

	/**商家退出登录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/logout", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO logout(@RequestBody RequestDTO<MerchantDTO> requestDTO) {
		
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		
		try {
			merchantService.logout(requestDTO.getHeader().getTokenId());
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}
		
		return resultDTO;
	}
	
	/**商家修改密码
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/changePassword", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changePassword(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		MerchantDTO merchantDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		String tokenId = requestDTO.getHeader().getTokenId();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			merchantService.changePassword(merchantDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**商家忘记密码
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/forgetPassword",method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO forgetPassword(@RequestBody RequestDTO<MerchantDTO> requestDTO){
		
		MerchantDTO merchantDTO = requestDTO.getBody();
		String mobileNo = merchantDTO.getMobileNo();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			//验证码验证码
			if(verificationService.verifyCodeBySMS("86", mobileNo, merchantDTO.getVerificationCode())){
				merchantService.forgetPassword(merchantDTO);
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
	
	/**修改交易密码
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/changeTranPassword", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeTranPassword(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		MerchantDTO merchantDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		String tokenId = requestDTO.getHeader().getTokenId();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			merchantService.changeTranPassword(merchantDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**验证交易密码
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/validateTranPassword", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO validateTranPassword(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		MerchantDTO merchantDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		String tokenId = requestDTO.getHeader().getTokenId();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			merchantService.validateTranPassword(merchantDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**查询交易密码过期时间(分钟)
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/inquiryTranPasswordExpireDuration", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryTranPasswordExpireDuration(@RequestBody RequestDTO<SettingDTO> requestDTO) {

		ResultDTO resultDTO = new ResultDTO();
		String tokenId = requestDTO.getHeader().getTokenId();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			SettingDTO settingDTO = settingService.getSettingByName("TRAN_PASSWORD_EXPIRE_DURATION");
			resultDTO.getBody().setData(settingDTO);
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
	@RequestMapping(value = "/merchant/dashboard", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO dashboard(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		MerchantDTO merchantDTO = requestDTO.getBody();
		
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			
			UserSearchDTO searchDTO = new UserSearchDTO();
			searchDTO.setKeyArray(new String[] {});
			Date today = new Date();
			Date yesterday = DateUtils.addDays(today, -1);
			Date day7Before = DateUtils.addDays(today, -6);
			Date dayLongBefore = DateUtils.addYears(today, -10);
			DashboardDTO dashboardDTO = new DashboardDTO();
			
			//会员总数
			int totalUserCount = userService.getIncrementUserCountByMerchantDate(merchantDTO,dayLongBefore, today);
			dashboardDTO.setTotalUserCount(totalUserCount);
			
			//商品总数
			ProductSearchDTO productSearchDTO = new ProductSearchDTO();
			productSearchDTO.setKeyArray(new String[] {ProductSearchDTO.KEY_MERCHANT});
			productSearchDTO.setMerchantUuid(merchantDTO.getMerchantUuid());
			int totalProductCount = productService.searchProductTotal(productSearchDTO);
			dashboardDTO.setTotalProductCount(totalProductCount);
			
			//今日会员新增
			int userCount = userService.getIncrementUserCountByMerchantDate(merchantDTO,today, today);
			dashboardDTO.setUserCount(userCount);
			//计算增长率
			int userCountYesterday = userService.getIncrementUserCountByMerchantDate(merchantDTO,yesterday, yesterday);
			int userGrowthCount = userCount - userCountYesterday;
			float userGrowthRate = userGrowthCount/(userCountYesterday==0?1:userCountYesterday);
			dashboardDTO.setUserCountGrowthRate(userGrowthRate);
			
			//今日订单总数
			OrderSearchDTO orderSearchDTO = new OrderSearchDTO();
			orderSearchDTO.setKeyArray(new String[] { "ORDERDATE", "MERCHANT" });
			orderSearchDTO.setOrderDateStart(today);
			orderSearchDTO.setOrderDateEnd(today);
			orderSearchDTO.setMerchantUuid(merchantDTO.getMerchantUuid());
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
			BigDecimal orderAmount = orderService.getOrderProductAmountTotalByMerchant(merchantDTO, today, today);
			dashboardDTO.setOrderAmount(orderAmount);
			
			//近7日订单金额
			orderSearchDTO.setOrderDateStart(day7Before);
			orderSearchDTO.setOrderDateEnd(today);
			BigDecimal orderAmountDay7 = orderService.searchOrderTotalAmount(orderSearchDTO);
			dashboardDTO.setOrderAmountDay7(orderAmountDay7);
			
			//计算增长率
			BigDecimal orderAmountYesterday = orderService.getOrderProductAmountTotalByMerchant(merchantDTO, yesterday, yesterday);
			BigDecimal orderAmountGrowth = orderAmount.subtract(orderAmountYesterday);
			BigDecimal orderAmountGrowthRate = orderAmountGrowth.divide(orderAmountYesterday.compareTo(BigDecimal.valueOf(0))==0?BigDecimal.valueOf(1):orderAmountYesterday, 2, BigDecimal.ROUND_HALF_UP);
			dashboardDTO.setOrderAmountGrowthRate(orderAmountGrowthRate.floatValue());
			
			//商家提现中金额
			WithdrawSearchDTO withdrawSearchDTO = new WithdrawSearchDTO();
			withdrawSearchDTO.setKeyArray(new String[] {"WITHDRAWSTATUS","MERCHANT"});
			String[] withdrawStatusArray = {FundConstants.WITHDRAW_STATUS_APPLICATED,FundConstants.WITHDRAW_STATUS_APPROVED};
			withdrawSearchDTO.setWithdrawStatusArray(withdrawStatusArray);
			withdrawSearchDTO.setMerchantUuid(merchantDTO.getMerchantUuid());
			BigDecimal merchantWithdrawAmount = fundService.searchMerchantWithdrawTotalAmount(withdrawSearchDTO);
			dashboardDTO.setMerchantWithdrawAmount(merchantWithdrawAmount);
			
			//订单总数量
			orderSearchDTO.setOrderDateStart(dayLongBefore);
			orderSearchDTO.setOrderDateEnd(today);
			int totalOrderCount = orderService.searchOrderTotal(orderSearchDTO);
			dashboardDTO.setTotalOrderCount(totalOrderCount);
			
			//订单总金额
			orderSearchDTO.setOrderDateStart(dayLongBefore);
			orderSearchDTO.setOrderDateEnd(today);
			BigDecimal totalOrderAmount = orderService.searchOrderTotalAmount(orderSearchDTO);
			dashboardDTO.setTotalOrderAmount(totalOrderAmount);
			
			//今日爆款Top 5
			List<OrderProductDTO> productList = orderService.getTopxMerchantProductByUnit(merchantDTO, today, today, 5);
			dashboardDTO.setTopUnitProductList(productList);
			
			orderSearchDTO.setKeyArray(new String[] {"MERCHANT", "ORDERDATE","ORDERSTATUS" });
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
			orderAfterSaleSearchDTO.setKeyArray(new String[] { "STATUS","MERCHANT"});
			orderAfterSaleSearchDTO.setStatus(OrderConstants.ORDER_AFTER_SALE_STATUS_CREATE);
			orderAfterSaleSearchDTO.setMerchantUuid(merchantDTO.getMerchantUuid());
			int orderCreateAfterSaleCount = orderAfterSaleService.searchOrderAfterSaleTotal(orderAfterSaleSearchDTO);
			dashboardDTO.setToReviewAfterSaleCount(orderCreateAfterSaleCount);
			//待收货退款
			orderAfterSaleSearchDTO.setStatus(OrderConstants.ORDER_AFTER_SALE_STATUS_WAITING_CONFIRM_COURIER);
			int orderConfirmAfterSaleCount = orderAfterSaleService.searchOrderAfterSaleTotal(orderAfterSaleSearchDTO);
			dashboardDTO.setToConfirmAfterSaleCount(orderConfirmAfterSaleCount);
			
			//近7日退款金额
			orderAfterSaleSearchDTO.setKeyArray(new String[] {"MERCHANT", "APPLICATION_TIME"});
			orderAfterSaleSearchDTO.setApplicationDateStart(day7Before);
			orderAfterSaleSearchDTO.setApplicationDateEnd(today);
			BigDecimal afterSaleAmountDay7 = orderAfterSaleService.searchOrderAfterSaleAmount(orderAfterSaleSearchDTO);
			dashboardDTO.setAfterSaleAmountDay7(afterSaleAmountDay7);
			
			//待回复商品评论
			ProductCommentSearchDTO commentSearchDTO = new ProductCommentSearchDTO();
			commentSearchDTO.setKeyArray(new String[] {"NOTREPLIED","MERCHANT"});
			commentSearchDTO.setMerchantUuid(merchantDTO.getMerchantUuid());
			int toReplyProductCommentCount = productService.searchProductCommentTotal(commentSearchDTO, false);
			dashboardDTO.setToReplyProductCommentCount(toReplyProductCommentCount);
			
			//待审核提现申请
			WithdrawSearchDTO mVWithdrawSearchDTO = new WithdrawSearchDTO();
			mVWithdrawSearchDTO.setKeyArray(new String[] {"WITHDRAWSTATUS","MERCHANT"});
			mVWithdrawSearchDTO.setWithdrawStatusArray(new String[] {FundConstants.WITHDRAW_STATUS_APPLICATED});
			mVWithdrawSearchDTO.setMerchantUuid(merchantDTO.getMerchantUuid());
			int toVerifyMerchantWithdrawCount = fundService.searchMerchantWithdrawTotal(mVWithdrawSearchDTO);
			dashboardDTO.setToVerifyMerchantWithdrawCount(toVerifyMerchantWithdrawCount);
			
			//待审核提现申请
			WithdrawSearchDTO mPWithdrawSearchDTO = new WithdrawSearchDTO();
			mPWithdrawSearchDTO.setKeyArray(new String[] {"WITHDRAWSTATUS","MERCHANT"});
			mPWithdrawSearchDTO.setWithdrawStatusArray(new String[] {FundConstants.WITHDRAW_STATUS_APPROVED});
			mPWithdrawSearchDTO.setMerchantUuid(merchantDTO.getMerchantUuid());
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
	
	/**商家后台运营统计分析
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/statistic", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO statistic(@RequestBody RequestDTO<OperationStatisticDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OperationStatisticDTO operationDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		String merchantUuid = operationDTO.getMerchantUuid();
		MerchantDTO merchantDTO = new MerchantDTO();
		merchantDTO.setMerchantUuid(merchantUuid);
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			
			Date startDate = operationDTO.getStartDate();
			Date endDate = operationDTO.getEndDate();
			
			UserSearchDTO searchDTO = new UserSearchDTO();
			searchDTO.setKeyArray(new String[] {});
			
			//会员总数
			int userCount = userService.getIncrementUserCountByMerchantDate(merchantDTO, startDate, endDate);
			operationDTO.setUserCount(userCount);
			
			//订单总数
			OrderSearchDTO orderSearchDTO = new OrderSearchDTO();
			orderSearchDTO.setKeyArray(new String[] { "ORDERDATE","MERCHANT"});
			orderSearchDTO.setOrderDateStart(startDate);
			orderSearchDTO.setOrderDateEnd(endDate);
			orderSearchDTO.setMerchantUuid(merchantUuid);
			int orderCount = orderService.searchOrderTotal(orderSearchDTO);
			operationDTO.setOrderCount(orderCount);
			
			//订单金额
			BigDecimal orderAmount = orderService.getOrderProductAmountTotalByMerchant(merchantDTO, startDate, endDate);
			operationDTO.setOrderAmount(orderAmount);
			
			//今日销量Top 5
			List<OrderProductDTO> unitProductList = orderService.getTopxMerchantProductByUnit(merchantDTO, startDate, endDate, 5);
			operationDTO.setTopUnitProductList(unitProductList);
			
			//今日销售额Top 5
			List<OrderProductDTO> amountProductList = orderService.getTopxMerchantProductByAmount(merchantDTO, startDate, endDate, 5);
			operationDTO.setTopAmountProductList(amountProductList);
			
			// 获取会员增长曲线
			List<ChartDTO> userChart = userService.getIncrementUserCountChartByMerchantDate(merchantDTO, startDate, endDate);
			operationDTO.setUserCountChart(userChart);
			
			// 获取订单数量增长曲线
			List<ChartDTO> orderCountChart = orderService.getIncrementOrderCountChartByMerchant(merchantDTO, startDate, endDate);
			operationDTO.setOrderCountChart(orderCountChart);
			
			// 获取订单金额增长曲线
			List<ChartDTO> productAmountChart = orderService.getIncrementOrderAmountChartByMerchant(merchantDTO, startDate, endDate);
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
	
	/**更新商家积分兑换比例
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/updateExchangeRate", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO updateExchangeRate(@RequestBody RequestDTO<MerchantDTO> requestDTO) {
		MerchantDTO merchantDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		String tokenId = requestDTO.getHeader().getTokenId();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			merchantService.updateMerchantField(merchantDTO, "EXCHANGE_RATE");
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
	@RequestMapping(value = "/merchant/changeMerchant", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeMerchant(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		MerchantDTO merchantDTO = requestDTO.getBody();
		String actionType = merchantDTO.getActionType();
		String tokenId = requestDTO.getHeader().getTokenId();
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
			
			if("ADD".equals(actionType))	//添加商户账户
				merchantService.createMerchant(merchantDTO);
			else if("MODIFY".equals(actionType))	//修改商户账户
				merchantService.mUpdateMerchant(merchantDTO);
			else if("DELETE".equals(actionType))	//删除商户账户
				merchantService.deleteMerchant(merchantDTO);
			else if("CLOSE".equals(actionType))	//关闭商户账户
				merchantService.closeMerchant(merchantDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**更新商家单个字段信息
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/updateMerchantField", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO updateMerchantField(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

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
			merchantService.updateMerchantField(merchantDTO, merchantDTO.getActionType());
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
	@RequestMapping(value = "/merchant/inquiryUser", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryUser(@RequestBody RequestDTO<UserDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserDTO userDTO = requestDTO.getBody();
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
			List<UserDTO> userList = new ArrayList<UserDTO>();
			if(!StringUtils.isEmpty(userDTO.getUserUuid())){
				userDTO = userService.getUserDTO(userDTO);
				resultDTO.getBody().setData(userDTO);
			}else if(!StringUtils.isEmpty(userDTO.getPersonalPhone())){
				userDTO = userService.getUserByMobileNo("86",userDTO.getPersonalPhone());
				resultDTO.getBody().setData(userDTO);
			}
			else if(userDTO.getMerchantDTO()!=null){	//查询所有消费过的会员
				userList = userService.getUserByMerchant(userDTO.getMerchantDTO());
				UserDTOList userDTOList = new UserDTOList();
				userDTOList.setUserList(userList);
				resultDTO.getBody().setData(userDTOList);
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
	@RequestMapping(value = "/merchant/searchUser", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchUser(@RequestBody RequestDTO<UserSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserSearchDTO userSearchDTO = requestDTO.getBody();
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
			MerchantDTO merchantDTO = userSearchDTO.getMerchantDTO();
			List<UserDTO> userList = merchantService.searchMerchantUser(merchantDTO, userSearchDTO);
			int total = merchantService.searchMerchantUserTotal(merchantDTO, userSearchDTO);
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
	
	
	/**搜索资金明细记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/searchStatement", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchStatement(@RequestBody RequestDTO<MerchantStatementSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantStatementSearchDTO searchDTO = requestDTO.getBody();
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
	
	/**搜索商家充值记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/searchCharge", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchCharge(@RequestBody RequestDTO<MerchantChargeSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantChargeSearchDTO searchDTO = requestDTO.getBody();
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
	
	/**搜索商家划拨记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/searchAssign", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchAssign(@RequestBody RequestDTO<MerchantAssignSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantAssignSearchDTO searchDTO = requestDTO.getBody();
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
	
	/**注册会员
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/registerUser", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO registerUser(@RequestBody RequestDTO<UserDTO> requestDTO) {

		UserDTO userDTO = requestDTO.getBody();
		String personalPhone = userDTO.getPersonalPhone();
		String personalPhoneCountryCode = userDTO.getPersonalPhoneCountryCode();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		String tokenId = requestDTO.getHeader().getTokenId();
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			userService.registerUser(userDTO);
			userDTO = userService.getUserByMobileNo(personalPhoneCountryCode, personalPhone);
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
	
	/**查询商家
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/inquiryMerchant", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryMerchant(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		MerchantDTO merchantDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		MerchantDTOList list = new MerchantDTOList();
		try {
			List<MerchantDTO> merchantDTOList = new ArrayList<MerchantDTO>();
			if(!StringUtils.isEmpty(merchantDTO.getMerchantUuid())){
				merchantDTO = merchantService.getMerchantByUuid(merchantDTO.getMerchantUuid());
				resultDTO.getBody().setData(merchantDTO);
			}else if(!StringUtils.isEmpty(merchantDTO.getMobileNo())){
				merchantDTO = merchantService.getMerchantByMobileNo(merchantDTO.getMobileNo());
				resultDTO.getBody().setData(merchantDTO);
			}else{
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
	
	/**修改商家配送员信息
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/changeMerchantShopper", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeMerchantShopper(@RequestBody RequestDTO<MerchantShopperDTO> requestDTO) {

		MerchantShopperDTO merchantShopperDTO = requestDTO.getBody();
		String actionType = merchantShopperDTO.getActionType();
		String tokenId = requestDTO.getHeader().getTokenId();
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
			
			if("ADD".equals(actionType))	//添加配送员
				merchantService.createMerchantShopper(merchantShopperDTO);
			else if("MODIFY".equals(actionType))	//修改配送员
				merchantService.updateMerchantShopper(merchantShopperDTO);
			else if("DELETE".equals(actionType))	//删除配送员
				merchantService.deleteMerchantShopper(merchantShopperDTO);
			else if("ENABLE".equals(actionType))	//启用配送员
				merchantService.enableMerchantShopper(merchantShopperDTO);
			else if("DISABLE".equals(actionType))	//停用配送员
				merchantService.disableMerchantShopper(merchantShopperDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}
		return resultDTO;
	}
	
	/**查询商家配送员
	 * @param requestDTO
	 * @return 商家配送员列表
	 */
	@RequestMapping(value = "/merchant/inquiryMerchantShopper", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryMerchantShopper(@RequestBody RequestDTO<MerchantShopperDTO> requestDTO) {

		MerchantShopperDTO merchantShopperDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!StringUtils.isEmpty(merchantShopperDTO.getMerchantShopperUuid())) {
				merchantShopperDTO = merchantService.getShopperByUuid(merchantShopperDTO.getMerchantShopperUuid());
				resultDTO.getBody().setData(merchantShopperDTO);
			}else if(merchantShopperDTO.getMerchantDTO() != null) {
				List<MerchantShopperDTO> shopperDTOList = merchantService.getShoppersByMerchant(merchantShopperDTO.getMerchantDTO());
				MerchantShopperDTOList list = new MerchantShopperDTOList();
				list.setShoppers(shopperDTOList);
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
	
	
	/**商家管理后台首页
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/home", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO home(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		
//		MerchantDTO MerchantDTO = requestDTO.getBody();
//		Date startDate = MerchantDTO.getOrderDateStart();
//		Date endDate = MerchantDTO.getOrderDateEnd();
		
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
			MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
			
			UserSearchDTO searchDTO = new UserSearchDTO();
			searchDTO.setKeyArray(new String[] {});
			//会员总数
			int userCount = merchantService.searchMerchantUserTotal(nMerchantDTO, searchDTO);
			nMerchantDTO.setUserCount(userCount);
			
			//充值总金额
			MerchantChargeSearchDTO chargeSearchDTO = new MerchantChargeSearchDTO();
			String[] chargeKeyArray = new String[] {"MERCHANT"};
			chargeSearchDTO.setKeyArray(chargeKeyArray);
			chargeSearchDTO.setMerchantUuid(nMerchantDTO.getMerchantUuid());
			BigDecimal chargeAmount = merchantService.searchMerchantChargeTotalAmount(chargeSearchDTO);
			nMerchantDTO.setTotalBalance(chargeAmount);
			
			//划拨总积分
			MerchantAssignSearchDTO assignSearchDTO = new MerchantAssignSearchDTO();
			String[] assignKeyArray = new String[] {"MERCHANT"};
			assignSearchDTO.setKeyArray(assignKeyArray);
			assignSearchDTO.setMerchantUuid(nMerchantDTO.getMerchantUuid());
			int assignPoint = merchantService.searchMerchantAssignTotalPoint(assignSearchDTO);
			nMerchantDTO.setTotalPoint(assignPoint);
			//可用积分
//			nMerchantDTO.getAvailablePoint();
			
			resultDTO.getBody().setData(nMerchantDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**商家管理后台首页查询商家总金额
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/inquiryMerchantSummary", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryMerchantSummary(@RequestBody RequestDTO<OrderSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		
		OrderSearchDTO orderSearchDTO = requestDTO.getBody();
		Date startDate = orderSearchDTO.getOrderDateStart();
		Date endDate = orderSearchDTO.getOrderDateEnd();
		
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
			MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
			
			UserSearchDTO searchDTO = new UserSearchDTO();
			searchDTO.setKeyArray(new String[] {});
			//会员总数
//			int userCount = merchantService.searchMerchantUserTotal(nMerchantDTO, searchDTO);
//			nMerchantDTO.setUserCount(userCount);
			
			//充值总金额
			MerchantChargeSearchDTO chargeSearchDTO = new MerchantChargeSearchDTO();
			
			String[] chargeKeyArray = new String[] {"MERCHANT","ASSIGNDATE"};
			chargeSearchDTO.setKeyArray(chargeKeyArray);
			chargeSearchDTO.setChargeDateStart(startDate);
			chargeSearchDTO.setChargeDateEnd(endDate);
			chargeSearchDTO.setMerchantUuid(nMerchantDTO.getMerchantUuid());
			BigDecimal chargeAmount = merchantService.searchMerchantChargeTotalAmount(chargeSearchDTO);
			nMerchantDTO.setTotalBalance(chargeAmount);
			
			//划拨总积分
			MerchantAssignSearchDTO assignSearchDTO = new MerchantAssignSearchDTO();
			String[] assignKeyArray = new String[] {"MERCHANT","ASSIGNDATE"};
			assignSearchDTO.setKeyArray(assignKeyArray);
			assignSearchDTO.setAssignDateStart(startDate);
			assignSearchDTO.setAssignDateEnd(endDate);
			assignSearchDTO.setMerchantUuid(nMerchantDTO.getMerchantUuid());
			int assignPoint = merchantService.searchMerchantAssignTotalPoint(assignSearchDTO);
			nMerchantDTO.setTotalPoint(assignPoint);
			//可用积分
//			nMerchantDTO.getAvailablePoint();
			
			resultDTO.getBody().setData(nMerchantDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	
	/**商家收款二维码地址
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/payQrCode", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO payQrCode(@RequestBody RequestDTO<MerchantDTO> requestDTO) {
		MerchantDTO merchantDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		String tokenId = requestDTO.getHeader().getTokenId();
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
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
	
	/**重定向商家充值链接地址
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/getChargeRedirectUrl", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getPaymentRedirectUrl(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		MerchantDTO merchantDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			MerchantDTO xMerchantDTO = new MerchantDTO();
			String chargeRedirectUrl = paymentUtil.getWechatOauth2Redirect().replace("REDIRECTURL", URLEncoder.encode("http://"+ paymentUtil.getDomainName()+"/charge?mobileNo="+merchantDTO.getMobileNo()));
			xMerchantDTO.setChargeRedirectUrl(chargeRedirectUrl);
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
	
	/**通过code换取公众号open id
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/getOpenIdByCode4OfficialAccount", method = RequestMethod.POST)
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
			
			//获取微信其他信息
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
	
	/**上传文件
	 * @param files
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/merchant/uploadMediaFile", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject uploadFile(@RequestParam("file") MultipartFile[] files, HttpServletRequest request,
			HttpServletResponse response) {
		String tokenId = request.getParameter("tokenId");
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		MerchantDTO merchantDTO = null;
		// 检查token id
		try {
			merchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
			if (merchantDTO == null) {
				logger.error("Invalid token " + tokenId);
				jsonObject.put("errno", 1);
				return jsonObject;
			} else {
				merchantService.setMerchantProfile(merchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
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
}
