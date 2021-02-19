package com.mb.ext.web.controller.supplier;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

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
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.AdminService;
import com.mb.ext.core.service.AuthenticationService;
import com.mb.ext.core.service.DeliveryService;
import com.mb.ext.core.service.FundService;
import com.mb.ext.core.service.LogService;
import com.mb.ext.core.service.MerchantService;
import com.mb.ext.core.service.OSSService;
import com.mb.ext.core.service.OrderService;
import com.mb.ext.core.service.PlatformService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.SettingService;
import com.mb.ext.core.service.SupplierService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.MerchantChargeSearchDTO;
import com.mb.ext.core.service.spec.MerchantSearchDTO;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.merchant.MerchantChargeDTO;
import com.mb.ext.core.service.spec.merchant.MerchantChargeDTOList;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTOList;
import com.mb.ext.core.util.MailSenderUtil;
import com.mb.ext.msg.HttpClientHelper;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

@Controller
public class SupplierController {
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());
	
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
	private UserService userService;
	
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
	
	/**
	 * 
	 * login
	 */
	@RequestMapping(value = "/supplier/login", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO login(@RequestBody RequestDTO<AdminDTO> requestDTO) {

		AdminDTO adminDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		
		try {
			if(adminDTO ==null){
				throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_PARAMETER_ERROR);
			}
			String url="http://www.junengshop.com:8080/api/user/verifyuser";
			String mobileNo = adminDTO.getMobileNo();
			String password = adminDTO.getPassword();
			Map map = new HashMap();
			map.put("phone", mobileNo);
			map.put("password", password);
			JSONObject json = JSONObject.fromObject(HttpClientHelper.sendGet(url, map, "utf-8"));
			if(json.getString("code").equals("18")){
				resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
	    		resultDTO.getBody().getStatus().setErrorDesc("该手机号码不是经销商号码！");
			}else if(json.getString("code").equals("2")){
				resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
	    		resultDTO.getBody().getStatus().setErrorDesc("密码错误");
			}else{
				resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
			}
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}
		return resultDTO;
	}
	
	/*
	 * 翻页查询商家
	 */
	@RequestMapping(value = "/supplier/searchMerchant", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchMerchant(@RequestBody RequestDTO<MerchantSearchDTO> requestDTO) {

		MerchantSearchDTO merchantSearchDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			List<MerchantDTO> merchantList = merchantService.searchMerchants(merchantSearchDTO);
			int total = merchantService.searchMerchantTotal(merchantSearchDTO);
			MerchantDTOList merchantDTOList = new MerchantDTOList();
			merchantDTOList.setMerchants(merchantList);
			merchantDTOList.setTotal(total);
			resultDTO.getBody().setData(merchantDTOList);
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
	 * 修改商家备注
	 * 
	 */
	@RequestMapping(value = "/supplier/updateMerchantField", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO updateMerchantField(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantDTO merchantDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			merchantService.updateMerchantField(merchantDTO,merchantDTO.getActionType());
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**
	 * change merchant
	 */
	@RequestMapping(value = "/supplier/changeMerchant", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeMerchant(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		MerchantDTO merchantDTO = requestDTO.getBody();
		String actionType = merchantDTO.getActionType();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {	
			if("ADD".equals(actionType))	//添加商户账户
				merchantService.createMerchant(merchantDTO);
			else if("MODIFY".equals(actionType))	//修改商户账户
				merchantService.updateMerchant(merchantDTO);
			else if("DELETE".equals(actionType))	//删除商户账户
				merchantService.deleteMerchant(merchantDTO);
			else if("CLOSE".equals(actionType)) {	//关闭商户账户
				merchantService.closeMerchant(merchantDTO);
				merchantDTO = merchantService.getMerchantByUuid(merchantDTO.getMerchantUuid());
//				try{
//					logService.addSysLog(LogConstants.LOGCATEGORY_MERCHANT, "关闭商家"+merchantDTO.getMerchantName()+"("+merchantDTO.getMobileNo()+")");
//				}catch(Exception e){
//					logger.error("记录系统日志错误:"+e.getMessage());
//				}
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
	
	/*
	 * 搜索充值记录
	 */
	@RequestMapping(value = "/supplier/searchCharge", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchCharge(@RequestBody RequestDTO<MerchantChargeSearchDTO> requestDTO) {

		MerchantChargeSearchDTO searchDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
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

}
