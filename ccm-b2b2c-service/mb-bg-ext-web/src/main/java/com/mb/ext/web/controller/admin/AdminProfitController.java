package com.mb.ext.web.controller.admin;

import java.util.ArrayList;
import java.util.List;

import com.mb.ext.core.service.spec.profit.*;
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
import com.mb.ext.core.dao.SettingDAO;
import com.mb.ext.core.service.AdminService;
import com.mb.ext.core.service.DeliveryService;
import com.mb.ext.core.service.FundService;
import com.mb.ext.core.service.LogService;
import com.mb.ext.core.service.MerchantService;
import com.mb.ext.core.service.OSSService;
import com.mb.ext.core.service.OrderAfterSaleService;
import com.mb.ext.core.service.OrderService;
import com.mb.ext.core.service.PlatformService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.ProfitService;
import com.mb.ext.core.service.SettingService;
import com.mb.ext.core.service.SupplierService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.util.MailSenderUtil;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**后台分润管理类
 * @author B2B2C商城
 *
 */
@Controller
public class AdminProfitController {

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
	private PropertyRepository propertyRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ProfitService profitService;

	@Autowired
	private TokenCheckUtil tokenCheckUtil;

	/**增删改推广收益参数
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeProfitRecruit", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeProfitRecruit(@RequestBody RequestDTO<ProfitRecruitDTO> requestDTO) {

		ProfitRecruitDTO profitRecruitDTO = requestDTO.getBody();
		String actionType = profitRecruitDTO.getActionType();
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
			
			if("ADD".equals(actionType)) {
				profitService.addProfitRecruit(profitRecruitDTO);
			}else if("MODIFY".equals(actionType)) {
				profitService.updateProfitRecruit(profitRecruitDTO);
			}else if("DELETE".equals(actionType)) {
				profitService.deleteProfitRecruit(profitRecruitDTO);
			}
			
			try{
				logService.addSysLog(LogConstants.LOGCATEGORY_SETTING, "修改了推广收益参数");
			}catch(Exception e){
				logger.error("记录系统日志错误:"+e.getMessage());
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
	
	/**增删改销售收益参数
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeProfitSale", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeProfitSale(@RequestBody RequestDTO<ProfitSaleDTO> requestDTO) {

		ProfitSaleDTO profitSaleDTO = requestDTO.getBody();
		String actionType = profitSaleDTO.getActionType();
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
			
			if("ADD".equals(actionType)) {
				profitService.addProfitSale(profitSaleDTO);
			}else if("MODIFY".equals(actionType)) {
				profitService.updateProfitSale(profitSaleDTO);
			}else if("DELETE".equals(actionType)) {
				profitService.deleteProfitSale(profitSaleDTO);
			}
			
			try{
				logService.addSysLog(LogConstants.LOGCATEGORY_SETTING, "修改了推广收益参数");
			}catch(Exception e){
				logger.error("记录系统日志错误:"+e.getMessage());
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
	
	/**增删改业绩奖金参数
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeProfitPerformance", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeProfitPerformance(@RequestBody RequestDTO<ProfitPerformanceDTOList> requestDTO) {

		ProfitPerformanceDTOList dtoList = requestDTO.getBody();
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
			
			profitService.updateProfitPerformance(dtoList.getProfitUserLevel(),dtoList.getPerformanceList());
			
			try{
				logService.addSysLog(LogConstants.LOGCATEGORY_SETTING, "修改了团队奖金业绩参数");
			}catch(Exception e){
				logger.error("记录系统日志错误:"+e.getMessage());
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
	
	/**修改培训师收益参数
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeProfitTrainer", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeProfitTrainer(@RequestBody RequestDTO<ProfitTrainerDTOList> requestDTO) {

		ProfitTrainerDTOList dtoList = requestDTO.getBody();
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
			
			profitService.updateProfitTrainer(dtoList.getProfitUserLevel(),dtoList.getTrainerList());
			
			try{
				logService.addSysLog(LogConstants.LOGCATEGORY_SETTING, "修改了团队奖金业绩参数");
			}catch(Exception e){
				logger.error("记录系统日志错误:"+e.getMessage());
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
	
	/**查询推广收益参数设置
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryProfitRecruit", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryProfitRecruit(@RequestBody RequestDTO<ProfitRecruitDTO> requestDTO) {

		ProfitRecruitDTO dto = requestDTO.getBody();
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
			if(!StringUtils.isEmpty(dto.getProfitRecruitUuid())){
				profitService.inquiryProfitRecruitByUuid(dto.getProfitRecruitUuid());
				resultDTO.getBody().setData(dto);
			}else{
				List<ProfitRecruitDTO> list = profitService.inquiryProfitRecruits();
				ProfitRecruitDTOList dtoList = new ProfitRecruitDTOList();
				dtoList.setRecruits(list);
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
	
	/**查询销售收益参数设置
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryProfitSale", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryProfitSale(@RequestBody RequestDTO<ProfitSaleDTO> requestDTO) {

		ProfitSaleDTO dto = requestDTO.getBody();
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
			if(!StringUtils.isEmpty(dto.getProfitSaleUuid())){
				profitService.inquiryProfitSaleByUuid(dto.getProfitSaleUuid());
				resultDTO.getBody().setData(dto);
			}else{
				List<ProfitSaleDTO> list = profitService.inquiryProfitSales();
				ProfitSaleDTOList dtoList = new ProfitSaleDTOList();
				dtoList.setSales(list);
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
	
	/**查询团队业绩奖金设置
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryProfitPerformance", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryProfitPerformance(@RequestBody RequestDTO<ProfitPerformanceDTO> requestDTO) {

		ProfitPerformanceDTO dto = requestDTO.getBody();
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
			List<ProfitPerformanceDTO> dtoList = new ArrayList<ProfitPerformanceDTO>();
			if(dto.getProfitUserLevel() != null) {
				dtoList = profitService.inquiryProfitPerformanceByUserLevel(dto.getProfitUserLevel());
			}else
				dtoList = profitService.inquiryProfitPerformance();
			ProfitPerformanceDTOList list = new ProfitPerformanceDTOList();
			list.setPerformanceList(dtoList);
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
	
	/**查询培训导师奖金设置
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryProfitTrainer", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryProfitTrainer(@RequestBody RequestDTO<ProfitTrainerDTO> requestDTO) {

		ProfitTrainerDTO dto = requestDTO.getBody();
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
			List<ProfitTrainerDTO> dtoList = new ArrayList<ProfitTrainerDTO>();
			if(dto.getProfitUserLevel() != null) {
				dtoList = profitService.inquiryProfitTrainerByUserLevel(dto.getProfitUserLevel());
			}else
				dtoList = profitService.inquiryProfitTrainer();
			ProfitTrainerDTOList list = new ProfitTrainerDTOList();
			list.setTrainerList(dtoList);
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
	
	/**查询分销设置
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryProfitDistribution", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryProfitDistribution(@RequestBody RequestDTO<ProfitDistributionDTO> requestDTO) {

		ProfitDistributionDTO dto = requestDTO.getBody();
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
			dto = profitService.inquiryProfitDistribution();
			resultDTO.getBody().setData(dto);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}
		return resultDTO;
	}

	/**查询新人福利设置
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryProfitWelfare", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryProfitWelfare(@RequestBody RequestDTO<ProfitWelfareDTO> requestDTO) {

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
			List<ProfitWelfareDTO> welfareDTOList = profitService.inquiryProfitWelfare();
			ProfitWelfareDTOList list = new ProfitWelfareDTOList();
			list.setWelfares(welfareDTOList);
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
	
	/**更新分销设置参数
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeProfitDistribution", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeProfitDistribution(@RequestBody RequestDTO<ProfitDistributionDTO> requestDTO) {

		ProfitDistributionDTO profitDistributionDTO = requestDTO.getBody();
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
			
			profitService.updateProfitDistribution(profitDistributionDTO);
			
			try{
				logService.addSysLog(LogConstants.LOGCATEGORY_SETTING, "修改了分销参数");
			}catch(Exception e){
				logger.error("记录系统日志错误:"+e.getMessage());
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

	/**更新新人福利设置参数
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeProfitWelfare", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeProfitWelfare(@RequestBody RequestDTO<ProfitWelfareDTOList> requestDTO) {

		ProfitWelfareDTOList welfareDTOList = requestDTO.getBody();
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
			profitService.updateProfitWelfare(welfareDTOList.getWelfares());
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
