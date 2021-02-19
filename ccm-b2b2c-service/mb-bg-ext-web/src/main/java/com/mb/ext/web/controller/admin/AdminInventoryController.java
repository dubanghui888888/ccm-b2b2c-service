package com.mb.ext.web.controller.admin;

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
import com.mb.ext.core.service.AdminService;
import com.mb.ext.core.service.InventoryService;
import com.mb.ext.core.service.MerchantService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.service.spec.product.ProductDTOList;
import com.mb.ext.core.service.spec.product.ProductInventoryDTOList;
import com.mb.ext.core.service.spec.product.ProductInventoryInboundDTO;
import com.mb.ext.core.service.spec.product.ProductInventoryOutboundDTO;
import com.mb.ext.core.service.spec.product.ProductInventoryTakingDTO;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**商品库存处理类
 * @author B2B2C商城
 *
 */
@Controller
public class AdminInventoryController {

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
	private InventoryService inventoryService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;

	
	/**商品入库
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/createInventoryInbound", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO createInventoryInbound(@RequestBody RequestDTO<ProductInventoryDTOList> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		ProductInventoryDTOList inventoryList = requestDTO.getBody();
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
			List<ProductInventoryInboundDTO> inboundDTOList = inventoryList.getInboundList();
			for (ProductInventoryInboundDTO inboundDTO : inboundDTOList) {
				inventoryService.addProductInventoryInbound(inboundDTO);
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
	
	/**商品入出库
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/createInventoryOutbound", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO createInventoryOutbound(@RequestBody RequestDTO<ProductInventoryDTOList> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		ProductInventoryDTOList inventoryList = requestDTO.getBody();
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
			List<ProductInventoryOutboundDTO> outboundDTOList = inventoryList.getOutboundList();
			for (ProductInventoryOutboundDTO outboundDTO : outboundDTOList) {
				inventoryService.addProductInventoryOutbound(outboundDTO);
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
	
	/**商品盘点
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/createInventoryTaking", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO createInventoryTaking(@RequestBody RequestDTO<ProductInventoryDTOList> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		ProductInventoryDTOList inventoryList = requestDTO.getBody();
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
			List<ProductInventoryTakingDTO> takingDTOList = inventoryList.getTakingList();
			for (ProductInventoryTakingDTO takingDTO : takingDTOList) {
				inventoryService.addProductInventoryTaking(takingDTO);
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
	
	/**查询入库记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryInventoryInbound", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryInventoryInbound(@RequestBody RequestDTO<ProductInventoryInboundDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		ProductInventoryInboundDTO inboundDTO = requestDTO.getBody();
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
			
			inboundDTO = inventoryService.getProducctInventoryInboundByUuid(inboundDTO.getProductInventoryInboundUuid());
			resultDTO.getBody().setData(inboundDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}

		return resultDTO;
	}
	
	/**查询出库记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryInventoryOutbound", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryInventoryOutbound(@RequestBody RequestDTO<ProductInventoryOutboundDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		ProductInventoryOutboundDTO outboundDTO = requestDTO.getBody();
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
			
			outboundDTO = inventoryService.getProducctInventoryOutboundByUuid(outboundDTO.getProductInventoryOutboundUuid());
			resultDTO.getBody().setData(outboundDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}

		return resultDTO;
	}
	
	/**查询盘点记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryInventoryTaking", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryInventoryTaking(@RequestBody RequestDTO<ProductInventoryTakingDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		ProductInventoryTakingDTO takingDTO = requestDTO.getBody();
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
			
			takingDTO = inventoryService.getProducctInventoryTakingByUuid(takingDTO.getProductInventoryTakingUuid());
			resultDTO.getBody().setData(takingDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}

		return resultDTO;
	}
	
	/**按照条件搜索入库记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchInventoryInbound", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchCoupon(@RequestBody RequestDTO<ProductSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		ProductSearchDTO productSearchDTO = requestDTO.getBody();
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
			List<ProductInventoryInboundDTO> inboundDTOList = inventoryService.searchProducctInventoryInbound(productSearchDTO);
			int total = inventoryService.searchProducctInventoryInboundTotal(productSearchDTO);
			ProductInventoryDTOList list = new ProductInventoryDTOList();
			list.setInboundList(inboundDTOList);
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
	
	/**按照条件搜索出库记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchInventoryOutbound", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchInventoryOutbound(@RequestBody RequestDTO<ProductSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		ProductSearchDTO productSearchDTO = requestDTO.getBody();
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
			List<ProductInventoryOutboundDTO> outboundDTOList = inventoryService.searchProducctInventoryOutbound(productSearchDTO);
			int total = inventoryService.searchProducctInventoryOutboundTotal(productSearchDTO);
			ProductInventoryDTOList list = new ProductInventoryDTOList();
			list.setOutboundList(outboundDTOList);
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
	
	/**按照条件搜索盘点记录
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchInventoryTaking", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchInventoryTaking(@RequestBody RequestDTO<ProductSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		ProductSearchDTO productSearchDTO = requestDTO.getBody();
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
			List<ProductInventoryTakingDTO> takingDTOList = inventoryService.searchProducctInventoryTaking(productSearchDTO);
			int total = inventoryService.searchProducctInventoryTakingTotal(productSearchDTO);
			ProductInventoryDTOList list = new ProductInventoryDTOList();
			list.setTakingList(takingDTOList);
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
	
	/**分页查询商品
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchInventoryProduct", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchInventoryProduct(@RequestBody RequestDTO<ProductSearchDTO> requestDTO) {

		ProductSearchDTO productSearchDTO = requestDTO.getBody();
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
			List<ProductDTO> dtoList = inventoryService.searchProduct(productSearchDTO);
			int total = productService.searchProductTotal(productSearchDTO);
			ProductDTOList list = new ProductDTOList();
			list.setProducts(dtoList);
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
