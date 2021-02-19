package com.mb.ext.web.controller.admin;

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
import com.mb.ext.core.constant.LogConstants;
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.service.AdminService;
import com.mb.ext.core.service.CouponService;
import com.mb.ext.core.service.LogService;
import com.mb.ext.core.service.MerchantService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.MerchantSearchDTO;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.merchant.MerchantApplicationDTO;
import com.mb.ext.core.service.spec.merchant.MerchantApplicationDTOList;
import com.mb.ext.core.service.spec.merchant.MerchantApplicationSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTOList;
import com.mb.ext.core.service.spec.merchant.MerchantShopperDTO;
import com.mb.ext.core.service.spec.merchant.MerchantShopperDTOList;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**后台商家管理
 * @author B2B2C商城
 *
 */
@Controller
public class AdminMerchantController {

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
	private MerchantService merchantService;
	
	@Autowired
	private CouponService couponService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;
	
	@Autowired
	private LogService logService;

	/**分页查询商家入驻申请
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchMerchantApplication", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchMerchantApplication(@RequestBody RequestDTO<MerchantApplicationSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantApplicationSearchDTO searchDTO = requestDTO.getBody();
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
			
			List<MerchantApplicationDTO> orderDTOList = merchantService.searchApplication(searchDTO);
			MerchantApplicationDTOList list = new MerchantApplicationDTOList();
			list.setApplications(orderDTOList);
			int total = merchantService.searchApplicationTotal(searchDTO);
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
	
	/**审核商家入驻申请
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/auditMerchantApplication", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO auditMerchantApplication(@RequestBody RequestDTO<MerchantApplicationDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantApplicationDTO applicationDTO = requestDTO.getBody();
		String actionType = applicationDTO.getActionType();
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
			
			if("APPROVE".equals(actionType)) {
				merchantService.approveMerchantApplication(applicationDTO);
			}else if("REJECT".equals(actionType)) {
				merchantService.rejectMerchantApplication(applicationDTO);
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
	
	/**查询商家
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryMerchant", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryMerchant(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		MerchantDTO merchantDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		MerchantDTOList list = new MerchantDTOList();
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			List<MerchantDTO> merchantDTOList = new ArrayList<MerchantDTO>();
			if (!StringUtils.isEmpty(merchantDTO.getMerchantUuid())) {
				merchantDTO = merchantService.getMerchantByUuid(merchantDTO.getMerchantUuid());
				resultDTO.getBody().setData(merchantDTO);
			} else {
				merchantDTOList = merchantService.getMerchants();
				list.setMerchants(merchantDTOList);
				resultDTO.getBody().setData(merchantDTO);
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

	/**分页查询商家
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchMerchant", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchMerchant(@RequestBody RequestDTO<MerchantSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantSearchDTO merchantSearchDTO = requestDTO.getBody();
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
	
	/**更新商家基本信息
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/updateMerchantField", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO updateMerchantField(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantDTO merchantDTO = requestDTO.getBody();
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

	/**更新商家信息
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/updateMerchant", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO updateMerchant(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantDTO merchantDTO = requestDTO.getBody();
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
			merchantService.updateMerchant(merchantDTO);
			try {
				logService.addSysLog(LogConstants.LOGCATEGORY_MERCHANT,
						"修改商家为" + "名称:" + merchantDTO.getMerchantName() + ";联系人:" + merchantDTO.getContactName()
								+ ";联系电话:" + merchantDTO.getMobileNo() + ";地址:" + merchantDTO.getMerchantAddress()
								+ ";推荐人:" + merchantDTO.getReferrer() + ";可用积分:" + merchantDTO.getAvailablePoint());
			} catch (Exception e) {
				logger.error("记录系统日志错误:" + e.getMessage());
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
	
	/**分页查询商家配送员
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchMerchantShopper", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchMerchantShopper(@RequestBody RequestDTO<MerchantSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantSearchDTO merchantSearchDTO = requestDTO.getBody();
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
			List<MerchantShopperDTO> merchantShopperList = merchantService.searchMerchantShopper(merchantSearchDTO);
			int total = merchantService.searchMerchantShopperTotal(merchantSearchDTO);
			MerchantShopperDTOList merchantShopperDTOList = new MerchantShopperDTOList();
			merchantShopperDTOList.setShoppers(merchantShopperList);
			merchantShopperDTOList.setTotal(total);
			resultDTO.getBody().setData(merchantShopperDTOList);
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
	@RequestMapping(value = "/admin/inquiryMerchantShopper", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryMerchantShopper(@RequestBody RequestDTO<MerchantShopperDTO> requestDTO) {

		MerchantShopperDTO merchantShopperDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		MerchantShopperDTOList list = new MerchantShopperDTOList();
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			List<MerchantShopperDTO> merchantShopperDTOList = new ArrayList<MerchantShopperDTO>();
			if (!StringUtils.isEmpty(merchantShopperDTO.getMerchantShopperUuid())) {
				merchantShopperDTO = merchantService.getShopperByUuid(merchantShopperDTO.getMerchantShopperUuid());
				resultDTO.getBody().setData(merchantShopperDTO);
			}else if(merchantShopperDTO.getMerchantDTO() != null) {
				merchantShopperDTOList = merchantService.getShoppersByMerchant(merchantShopperDTO.getMerchantDTO());
				list.setShoppers(merchantShopperDTOList);
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
	@RequestMapping(value = "/admin/changeMerchantShopper", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeMerchantShopper(@RequestBody RequestDTO<MerchantShopperDTO> requestDTO) {

		MerchantShopperDTO merchantShopperDTO = requestDTO.getBody();
		String actionType = merchantShopperDTO.getActionType();
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
}
