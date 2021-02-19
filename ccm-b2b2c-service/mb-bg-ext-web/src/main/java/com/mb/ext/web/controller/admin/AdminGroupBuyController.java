package com.mb.ext.web.controller.admin;

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
import com.mb.ext.core.service.AdminService;
import com.mb.ext.core.service.GroupBuyService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.GroupBuySearchDTO;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.group.GroupBuyDTO;
import com.mb.ext.core.service.spec.group.GroupBuyDefDTO;
import com.mb.ext.core.service.spec.group.GroupBuyDefDTOList;
import com.mb.ext.core.service.spec.order.GroupBuyDTOList;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**后台团购管理类
 * @author B2B2C商城
 *
 */
@Controller
public class AdminGroupBuyController {

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
	private GroupBuyService groupBuyService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;

	
	/**查询团购活动
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryGroupBuy", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryGroupBuy(@RequestBody RequestDTO<GroupBuyDefDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		GroupBuyDefDTO groupProductDTO = requestDTO.getBody();
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
			if(!StringUtils.isEmpty(groupProductDTO.getGroupBuyDefUuid())){
				groupProductDTO = groupBuyService.getGroupBuyDef(groupProductDTO.getGroupBuyDefUuid());
				resultDTO.getBody().setData(groupProductDTO);
			}else{
				List<GroupBuyDefDTO> groupDTOList = groupBuyService.getGroupBuyDefs();
				GroupBuyDefDTOList list = new GroupBuyDefDTOList();
				list.setGroupBuys(groupDTOList);
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
	
	/**分页查询团购活动
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchGroupBuyDef", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchGroupBuyDef(@RequestBody RequestDTO<ProductSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		ProductSearchDTO productSearchDTO = requestDTO.getBody();
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
			List<GroupBuyDefDTO> groupDTOList = groupBuyService.searchGroupBuyDef(productSearchDTO);
			int total = groupBuyService.searchGroupBuyDefTotal(productSearchDTO);
			GroupBuyDefDTOList list = new GroupBuyDefDTOList();
			list.setGroupBuys(groupDTOList);
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
	
	/**修改团购活动
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeGroupBuy", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeCoupon(@RequestBody RequestDTO<GroupBuyDefDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		GroupBuyDefDTO groupProductDTO = requestDTO.getBody();
		String actionType = groupProductDTO.getActionType();
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
			if("ADD".equals(actionType)){
				groupBuyService.addGroupBuyDef(groupProductDTO);
			}else if("MODIFY".equals(actionType)){
				groupBuyService.updateGroupBuyDef(groupProductDTO);
			}else if("DELETE".equals(actionType)){
				groupBuyService.deleteGroupBuyDef(groupProductDTO);
			}else if("DISABLE".equals(actionType)){
				groupBuyService.disableGroupBuyDef(groupProductDTO);
			}else if("ENABLE".equals(actionType)){
				groupBuyService.enableGroupBuyDef(groupProductDTO);
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
	
	/**分页搜索拼团单
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchGroupBuy", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchGroupBuy(@RequestBody RequestDTO<GroupBuySearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		GroupBuySearchDTO groupBuySearchDTO = requestDTO.getBody();
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
			List<GroupBuyDTO> groupBuyDTOList = groupBuyService.searchGroupBuy(groupBuySearchDTO);
			GroupBuyDTOList list = new GroupBuyDTOList();
			list.setGroupBuys(groupBuyDTOList);
			int total = groupBuyService.searchGroupBuyTotal(groupBuySearchDTO);
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
	
	/**团购订单详情
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/groupBuyDetail", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO groupBuyDetail(@RequestBody RequestDTO<GroupBuyDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		GroupBuyDTO groupBuyDTO = requestDTO.getBody();
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
			groupBuyDTO = groupBuyService.groupBuyDetail(groupBuyDTO.getGroupBuyUuid());
			resultDTO.getBody().setData(groupBuyDTO);
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
