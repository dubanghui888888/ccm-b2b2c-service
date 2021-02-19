package com.mb.ext.web.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import com.mb.ext.core.service.DeliveryService;
import com.mb.ext.core.service.LogService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.GroupDTO;
import com.mb.ext.core.service.spec.MerchantSearchDTO;
import com.mb.ext.core.service.spec.ProductBrandSearchDTO;
import com.mb.ext.core.service.spec.ProductCommentSearchDTO;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.product.GroupDTOList;
import com.mb.ext.core.service.spec.product.ProductBrandDTO;
import com.mb.ext.core.service.spec.product.ProductBrandDTOList;
import com.mb.ext.core.service.spec.product.ProductCateAttrDTO;
import com.mb.ext.core.service.spec.product.ProductCateDTO;
import com.mb.ext.core.service.spec.product.ProductCateDTOList;
import com.mb.ext.core.service.spec.product.ProductCommentDTO;
import com.mb.ext.core.service.spec.product.ProductCommentDTOList;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.service.spec.product.ProductDTOList;
import com.mb.ext.core.service.spec.product.ProductDeliveryDTO;
import com.mb.ext.core.service.spec.product.ProductDeliveryDTOList;
import com.mb.ext.core.service.spec.product.ProductFreightDTO;
import com.mb.ext.core.service.spec.product.ProductFreightDTOList;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**后台商品管理类
 * @author B2B2C商城
 *
 */
@Controller
public class AdminProductController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	
	@Autowired
	private AdminService adminService;

	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private DeliveryService deliveryService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;

	/**查询商品类目
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryProductCate", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryProductCate(@RequestBody RequestDTO<ProductCateDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		ProductCateDTO productCateDTO = requestDTO.getBody();
		String actionType = productCateDTO.getActionType();
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
			if("SINGLE".equals(actionType)){
				productCateDTO = productService.getProductCateByUuid(productCateDTO.getProductCateUuid());
				resultDTO.getBody().setData(productCateDTO);
			}else if("ROOT".equals(actionType)){
				List<ProductCateDTO> productCateDTOList = productService.getRootProductCate();
				ProductCateDTOList list = new ProductCateDTOList();
				list.setCates(productCateDTOList);
				resultDTO.getBody().setData(list);
			}else if("LEAF".equals(actionType)){
				List<ProductCateDTO> productCateDTOList = productService.getLeafProductCate();
				ProductCateDTOList list = new ProductCateDTOList();
				list.setCates(productCateDTOList);
				resultDTO.getBody().setData(list);
			}else if("CHILD".equals(actionType)){
				List<ProductCateDTO> productCateDTOList = productService.getChildProductCate(productCateDTO);
				ProductCateDTOList list = new ProductCateDTOList();
				list.setCates(productCateDTOList);
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
	
	/**增删改商品类目
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeProductCate", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeProductCate(@RequestBody RequestDTO<ProductCateDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		ProductCateDTO productCateDTO = requestDTO.getBody();
		String actionType = productCateDTO.getActionType();
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
				productService.addProductCate(productCateDTO);
			}else if("MODIFY".equals(actionType)){
				productService.updateProductCate(productCateDTO);
			}else if("DELETE".equals(actionType)){
				productService.deleteProductCate(productCateDTO);
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
	
	/**增删改商品品牌
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeProductBrand", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeProductBrand(@RequestBody RequestDTO<ProductBrandDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		ProductBrandDTO productBrandDTO = requestDTO.getBody();
		String actionType = productBrandDTO.getActionType();
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
				productService.addProductBrand(productBrandDTO);
			}else if("MODIFY".equals(actionType)){
				productService.updateProductBrand(productBrandDTO);
			}else if("DELETE".equals(actionType)){
				productService.deleteProductBrand(productBrandDTO);
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
	
	/**添加, 删除, 更新商品品类属性
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeProductCateAttr", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeProductCateAttr(@RequestBody RequestDTO<ProductCateAttrDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		ProductCateAttrDTO productCateAttrDTO = requestDTO.getBody();
		String actionType = productCateAttrDTO.getActionType();
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
				productService.addProductCateAttr(productCateAttrDTO);
			}else if("MODIFY".equals(actionType)){
				productService.updateProductCateAttr(productCateAttrDTO);
			}else if("DELETE".equals(actionType)){
				productService.deleteProductCateAttr(productCateAttrDTO);
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
	
	/**增删改商品
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeProduct", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeProduct(@RequestBody RequestDTO<ProductDTO> requestDTO) {

		ProductDTO productDTO = requestDTO.getBody();
		String actionType = productDTO.getActionType();
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
				try{
					logService.addSysLog(LogConstants.LOGCATEGORY_PRODUCT, adminDTO.getId()+"在"+new Date()+"修改了商品:"+productDTO.getProductName());
				}catch(Exception e){
					logger.error("记录系统日志错误:"+e.getMessage());
				}
			}
			
			if("ADD".equals(actionType))	
				productService.addProduct(productDTO);
			else if("MODIFY".equals(actionType))	
				productService.updateProduct(productDTO);
			else if("DELETE".equals(actionType))
				productService.deleteProduct(productDTO);
			else if("ENABLE".equals(actionType))	//上架
				productService.enableProduct(productDTO);
			else if("DISABLE".equals(actionType))	//下架
				productService.disableProduct(productDTO);
			else if("APPROVE".equals(actionType))	//商品审核通过
				productService.approveProduct(productDTO);
			else if("REJECT".equals(actionType))	//商品审核未通过
				productService.rejectProduct(productDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**查询商品
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryProduct", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryProduct(@RequestBody RequestDTO<ProductDTO> requestDTO) {

		ProductDTO productDTO = requestDTO.getBody();
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
			
			if(!StringUtils.isEmpty(productDTO.getProductUuid())){
				productDTO = productService.getProductByUuid(productDTO.getProductUuid());
				resultDTO.getBody().setData(productDTO);
			}else if(productDTO.getSupplierDTO() != null && !StringUtils.isEmpty(productDTO.getSupplierDTO().getSupplierUuid())){
				List<ProductDTO> dtoList = productService.getProductBySupplier(productDTO.getSupplierDTO());
				ProductDTOList list = new ProductDTOList();
				list.setProducts(dtoList);
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
	
	/** 根据多个商品ID查出商品详情
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryMultipleProduct", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryMultipleProduct(@RequestBody RequestDTO<ProductDTOList> requestDTO) {

		ProductDTOList productDTOList = requestDTO.getBody();
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
			List<ProductDTO> resultList = new ArrayList<ProductDTO>();
			List<ProductDTO> productList = productDTOList.getProducts();
			for (Iterator<ProductDTO> iterator = productList.iterator(); iterator.hasNext();) {
				ProductDTO productDTO = iterator.next();
				productDTO = productService.getProductByUuid(productDTO.getProductUuid());
				resultList.add(productDTO);
			}
			productDTOList.setProducts(resultList);
			resultDTO.getBody().setData(productDTOList);
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
	@RequestMapping(value = "/admin/searchProduct", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchProduct(@RequestBody RequestDTO<ProductSearchDTO> requestDTO) {

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
			List<ProductDTO> dtoList = productService.searchProduct(productSearchDTO);
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
	
	/**分页查询商品品牌
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchBrand", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchBrand(@RequestBody RequestDTO<ProductBrandSearchDTO> requestDTO) {

		ProductBrandSearchDTO productBrandSearchDTO = requestDTO.getBody();
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
			List<ProductBrandDTO> dtoList = productService.searchProductBrand(productBrandSearchDTO);
			int total = productService.searchProductBrandTotal(productBrandSearchDTO);
			ProductBrandDTOList list = new ProductBrandDTOList();
			list.setBrands(dtoList);
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
	
	/**回复商品评论
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/replayProductComment", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO replayProductComment(@RequestBody RequestDTO<ProductCommentDTO> requestDTO){
		ProductCommentDTO productCommentDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		String actionType = productCommentDTO.getActionType();
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
			if("ADD".equals(actionType))	//回复评论
				productService.replayProductComment(productCommentDTO);
			else if("MODIFY".equals(actionType))	//修改回复评论
				productService.updateProductCommentReply(productCommentDTO);
			else if("DELETE".equals(actionType))	//删除评论
				productService.deleteProductComment(productCommentDTO);
			else if("SHOW".equals(actionType))	//删除评论
				productService.setProductCommentShowStatus(productCommentDTO,true);
			else if("HIDE".equals(actionType))	//删除评论
				productService.setProductCommentShowStatus(productCommentDTO,false);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**查询商品评论
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryProductComment", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryProductComment(@RequestBody RequestDTO<ProductDTO> requestDTO){
		ProductDTO productDTO = requestDTO.getBody();
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
			List<ProductCommentDTO> dtoList = productService.getProductComments(productDTO);
			int total = productService.getProductCommentTotal(productDTO);
			ProductCommentDTOList list = new ProductCommentDTOList();
			list.setCommentList(dtoList);
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
	
	/**分页查询商品评论
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchProductComment", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchProductComment(@RequestBody RequestDTO<ProductCommentSearchDTO> requestDTO){
		ProductCommentSearchDTO searchDTO = requestDTO.getBody();
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
			
			List<ProductCommentDTO> dtoList = productService.searchProductComment(searchDTO,false);
			int total = productService.searchProductCommentTotal(searchDTO,false);
			ProductCommentDTOList list = new ProductCommentDTOList();
			list.setCommentList(dtoList);
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
	
	/**增删改商品组
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeProductGroup", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeProductGroup(@RequestBody RequestDTO<GroupDTO> requestDTO){
		GroupDTO groupDTO = requestDTO.getBody();
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
			
			if("ADD".equals(groupDTO.getActionType()))
				productService.addProductGroup(groupDTO);
			else if("MODIFY".equals(groupDTO.getActionType()))
				productService.updateProductGroup(groupDTO);
			else if("DELETE".equals(groupDTO.getActionType()))
				productService.deleteProductGroup(groupDTO);
			else if("HOME".equals(groupDTO.getActionType()))
				productService.setProductGroupDisplayedHome(groupDTO);
			else if("CANCEL_HOME".equals(groupDTO.getActionType()))
				productService.cancelProductGroupDisplayedHome(groupDTO);
			else if("REGISTER".equals(groupDTO.getActionType()))
				productService.setProductGroupForRegister(groupDTO);
			else if("CANCEL_REGISTER".equals(groupDTO.getActionType()))
				productService.cancelProductGroupForRegister(groupDTO);
			
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**查询商品组
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryProductGroup", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryProductGroup(@RequestBody RequestDTO<GroupDTO> requestDTO){
		GroupDTO gruopDTO = requestDTO.getBody();
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
			
			if(!StringUtils.isEmpty(gruopDTO.getGroupUuid())) {
				GroupDTO groupDTO = productService.getProductGroupByUuid(gruopDTO.getGroupUuid());
				resultDTO.getBody().setData(groupDTO);
			}else if(!StringUtils.isEmpty(gruopDTO.getGroupName())) {
				GroupDTO groupDTO = productService.getProductGroupByName(gruopDTO.getGroupName());
				resultDTO.getBody().setData(groupDTO);
			}else{
				List<GroupDTO> groupDTOList = productService.getProductGroups();
				GroupDTOList list = new GroupDTOList();
				list.setGroups(groupDTOList);
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
	
	/**增删改运费模板
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeProductFreight", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeProductFreight(@RequestBody RequestDTO<ProductFreightDTO> requestDTO){
		ProductFreightDTO freightDTO = requestDTO.getBody();
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
			
			if("ADD".equals(freightDTO.getActionType()))
				productService.addProductFreight(freightDTO);
			else if("MODIFY".equals(freightDTO.getActionType()))
				productService.updateProductFreight(freightDTO);
			else if("DELETE".equals(freightDTO.getActionType()))
				productService.deleteProductFreight(freightDTO);
			else if("DEFAULT".equals(freightDTO.getActionType()))
				productService.defaultProductFreight(freightDTO);
			else if("UNDEFAULT".equals(freightDTO.getActionType()))
				productService.cancelDefaultProductFreight(freightDTO);
			else if("ENABLE".equals(freightDTO.getActionType()))
				productService.enableProductFreight(freightDTO);
			else if("DISABLE".equals(freightDTO.getActionType()))
				productService.disableProductFreight(freightDTO);
			
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}
		return resultDTO;
	}
	
	/**查询运费模板
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryProductFreight", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryProductFreight(@RequestBody RequestDTO<ProductFreightDTO> requestDTO){
		ProductFreightDTO freightDTO = requestDTO.getBody();
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
			if(!StringUtils.isEmpty(freightDTO.getProductFreightUuid())) {
				freightDTO = productService.getProductFreightByUuid(freightDTO.getProductFreightUuid());
				resultDTO.getBody().setData(freightDTO);
			}else if(freightDTO.getMerchantDTO() != null && !StringUtils.isEmpty(freightDTO.getMerchantDTO().getMerchantUuid())){
				List<ProductFreightDTO> freightDTOList = productService.getProductFreightsByMerchant(freightDTO.getMerchantDTO());
				ProductFreightDTOList list = new ProductFreightDTOList();
				list.setFreights(freightDTOList);
				resultDTO.getBody().setData(list);
			}else{
				List<ProductFreightDTO> freightDTOList = productService.getProductFreights();
				ProductFreightDTOList list = new ProductFreightDTOList();
				list.setFreights(freightDTOList);
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

	/**查询运费模板
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchProductFreight", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchProductFreight(@RequestBody RequestDTO<MerchantSearchDTO> requestDTO){
		MerchantSearchDTO searchDTO = requestDTO.getBody();
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
			List<ProductFreightDTO> freightDTOList = productService.searchProductFreight(searchDTO);
			int total = productService.searchProductFreightTotal(searchDTO);
			ProductFreightDTOList list = new ProductFreightDTOList();
			list.setFreights(freightDTOList);
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
	
	/**增删改同城配送模板
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeProductDelivery", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeProductDelivery(@RequestBody RequestDTO<ProductDeliveryDTO> requestDTO){
		ProductDeliveryDTO deliveryDTO = requestDTO.getBody();
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
			
			if("ADD".equals(deliveryDTO.getActionType()))
				deliveryService.addProductDelivery(deliveryDTO);
			else if("MODIFY".equals(deliveryDTO.getActionType()))
				deliveryService.updateProductDelivery(deliveryDTO);
			else if("DELETE".equals(deliveryDTO.getActionType()))
				deliveryService.deleteProductDelivery(deliveryDTO);
			else if("DEFAULT".equals(deliveryDTO.getActionType()))
				deliveryService.defaultProductDelivery(deliveryDTO);
			else if("UNDEFAULT".equals(deliveryDTO.getActionType()))
				deliveryService.cancelDefaultProductDelivery(deliveryDTO);
			else if("ENABLE".equals(deliveryDTO.getActionType()))
				deliveryService.enableProductDelivery(deliveryDTO);
			else if("DISABLE".equals(deliveryDTO.getActionType()))
				deliveryService.disableProductDelivery(deliveryDTO);
			
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}
		return resultDTO;
	}
	
	/**查询同城配送模板
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryProductDelivery", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryProductDelivery(@RequestBody RequestDTO<ProductDeliveryDTO> requestDTO){
		ProductDeliveryDTO deliveryDTO = requestDTO.getBody();
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
			if(!StringUtils.isEmpty(deliveryDTO.getProductDeliveryUuid())) {
				deliveryDTO = deliveryService.getProductDeliveryByUuid(deliveryDTO.getProductDeliveryUuid());
				resultDTO.getBody().setData(deliveryDTO);
			}else if(deliveryDTO.getMerchantDTO() != null && !StringUtils.isEmpty(deliveryDTO.getMerchantDTO().getMerchantUuid())) {
				List<ProductDeliveryDTO> deliveryDTOList =  deliveryService.getProductDeliverysByMerchant(deliveryDTO.getMerchantDTO());
				ProductDeliveryDTOList list = new ProductDeliveryDTOList();
				list.setDeliverys(deliveryDTOList);
				resultDTO.getBody().setData(list);
			}else{
				List<ProductDeliveryDTO> deliveryDTOList = deliveryService.getProductDeliveries();
				ProductDeliveryDTOList list = new ProductDeliveryDTOList();
				list.setDeliverys(deliveryDTOList);
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
	
	/**查询同城配送模板
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchProductDelivery", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchProductDelivery(@RequestBody RequestDTO<MerchantSearchDTO> requestDTO){
		MerchantSearchDTO searchDTO = requestDTO.getBody();
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
			List<ProductDeliveryDTO> deliveryDTOList = deliveryService.searchProductDelivery(searchDTO);
			int total = deliveryService.searchProductDeliveryTotal(searchDTO);
			ProductDeliveryDTOList list = new ProductDeliveryDTOList();
			list.setDeliverys(deliveryDTOList);
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
