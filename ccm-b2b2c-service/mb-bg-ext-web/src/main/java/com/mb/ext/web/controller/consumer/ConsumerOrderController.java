package com.mb.ext.web.controller.consumer;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
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

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.mb.ext.core.constant.AlipayConstants;
import com.mb.ext.core.constant.AuthenticationConstants;
import com.mb.ext.core.constant.AuthorizationConstants;
import com.mb.ext.core.constant.OrderConstants;
import com.mb.ext.core.constant.WechatConstants;
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.FundService;
import com.mb.ext.core.service.GroupBuyService;
import com.mb.ext.core.service.OrderAfterSaleService;
import com.mb.ext.core.service.OrderService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.SecKillService;
import com.mb.ext.core.service.ShoppingCartService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.GroupBuySearchDTO;
import com.mb.ext.core.service.spec.OrderSearchDTO;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.ShoppingCartDTO;
import com.mb.ext.core.service.spec.ShoppingCartDTOList;
import com.mb.ext.core.service.spec.SignatureDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.WechatPayRequest;
import com.mb.ext.core.service.spec.WechatPayResponse;
import com.mb.ext.core.service.spec.WechatPayResult;
import com.mb.ext.core.service.spec.WechatRefundResult;
import com.mb.ext.core.service.spec.group.GroupBuyDTO;
import com.mb.ext.core.service.spec.order.AlipayFormDTO;
import com.mb.ext.core.service.spec.order.CourierDTO;
import com.mb.ext.core.service.spec.order.CourierResultDTO;
import com.mb.ext.core.service.spec.order.GroupBuyDTOList;
import com.mb.ext.core.service.spec.order.GroupBuyOrderDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.service.spec.order.OrderDTOList;
import com.mb.ext.core.service.spec.order.PointOrderDTO;
import com.mb.ext.core.service.spec.order.SecKillOrderDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.service.spec.product.ProductSkuDTO;
import com.mb.ext.core.util.AESUtil;
import com.mb.ext.core.util.PaymentUtil;
import com.mb.ext.core.util.WXJSAPISDKUtility;
import com.mb.ext.core.util.WechatUtil;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**会员订单处理类
 * @author B2B2C商城
 *
 */
@Controller
public class ConsumerOrderController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FundService fundService;

	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderAfterSaleService orderAfterSaleService;
	
	@Autowired
	private GroupBuyService groupBuyService;
	
	@Autowired
	private SecKillService secKillService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;
	
	@Autowired
	private PaymentUtil paymentUtil;
	
	@Autowired
	private WechatUtil wechatUtil;
	
	@Autowired
	private WXJSAPISDKUtility wxJsApiSdkUtility;

	/**获取购物车商品数量
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/getCartProductNum", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getCartProductNum(@RequestBody RequestDTO<UserDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserDTO userDTO = requestDTO.getBody();
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
			int num = shoppingCartService.getCartNum(userDTO);
			ShoppingCartDTO cartDTO = new ShoppingCartDTO();
			cartDTO.setUnit(num);
			resultDTO.getBody().setData(cartDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/** 添加商品到购物车
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/addProductToCart", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO addProductToCart(@RequestBody RequestDTO<ShoppingCartDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		ShoppingCartDTO shoppingCartDTO = requestDTO.getBody();
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
			ProductDTO productDTO = shoppingCartDTO.getProductDTO();
			ProductSkuDTO productSkuDTO = shoppingCartDTO.getProductSkuDTO();
			UserDTO userDTO = shoppingCartDTO.getUserDTO();
			int unit = shoppingCartDTO.getUnit();
			if(productSkuDTO != null && !StringUtils.isEmpty(productSkuDTO.getProductSkuUuid())) {
				String shoppingCartUuid = shoppingCartService.addProductSkuToShoppingCart(productSkuDTO, userDTO, unit);
				shoppingCartDTO.setShoppingCartUuid(shoppingCartUuid);
			}else if(productDTO != null && !StringUtils.isEmpty(productDTO.getProductUuid())) {
				String shoppingCartUuid = shoppingCartService.addProductToShoppingCart(productDTO, userDTO, unit);
				shoppingCartDTO.setShoppingCartUuid(shoppingCartUuid);
			}
			resultDTO.getBody().setData(shoppingCartDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**从购物车移除商品
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/removeProductFromCart", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO removeProductFromCart(@RequestBody RequestDTO<ShoppingCartDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		ShoppingCartDTO shoppingCartDTO = requestDTO.getBody();
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
			ProductDTO productDTO = shoppingCartDTO.getProductDTO();
			ProductSkuDTO productSkuDTO = shoppingCartDTO.getProductSkuDTO();
			UserDTO userDTO = shoppingCartDTO.getUserDTO();
			if(productSkuDTO != null && !StringUtils.isEmpty(productSkuDTO.getProductSkuUuid())) {
				shoppingCartService.removeProductSkuFromShoppingCart(productSkuDTO, userDTO);
			}else if(productDTO != null && !StringUtils.isEmpty(productDTO.getProductUuid())) {
				shoppingCartService.removeProductFromShoppingCart(productDTO, userDTO);
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
	
	/**修改购物车某个商品数量
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/updateShoppingCartProductNum", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO setShoppingCartProductNum(@RequestBody RequestDTO<ShoppingCartDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		ShoppingCartDTO shoppingCartDTO = requestDTO.getBody();
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
			shoppingCartService.updateShoppingCartProductNum(shoppingCartDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}

		return resultDTO;
	}
	
	/** 将商品从购物车删除
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/deleteProductFromShoppingCart", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO deleteProductFromShoppingCart(@RequestBody RequestDTO<ShoppingCartDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		ShoppingCartDTO shoppingCartDTO = requestDTO.getBody();
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
			shoppingCartService.deleteProductFromShoppingCart(shoppingCartDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}

		return resultDTO;
	}
	
	/**将所有商品从购物车删除
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/clearShoppingCart", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO clearShoppingCart(@RequestBody RequestDTO<UserDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserDTO userDTO = requestDTO.getBody();
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
			shoppingCartService.clearShoppingCart(userDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}

		return resultDTO;
	}
	
	/**查询购物车商品
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryCartProduct", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryCartProduct(@RequestBody RequestDTO<UserDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserDTO userDTO = requestDTO.getBody();
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
			List<ShoppingCartDTO> cartDTOList = shoppingCartService.getProducts(userDTO);
			ShoppingCartDTOList list = new ShoppingCartDTOList();
			list.setCarts(cartDTOList);
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
	
	/**根据id查询购物车商品
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryCartProductByIds", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryCartProductByIds(@RequestBody RequestDTO<List<String>> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		List<String> uuids = requestDTO.getBody();
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
			List<ShoppingCartDTO> cartDTOList = shoppingCartService.getShoppingCartByUuids(uuids);
			ShoppingCartDTOList list = new ShoppingCartDTOList();
			list.setCarts(cartDTOList);
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
	
	
	/** 查询订单
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryOrder(@RequestBody RequestDTO<OrderDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTO orderDTO = requestDTO.getBody();
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
			if(!StringUtils.isEmpty(orderDTO.getOrderNo())){
				orderDTO = orderService.getOrderByOrderNo(orderDTO.getOrderNo());
				resultDTO.getBody().setData(orderDTO);
			}else if(!StringUtils.isEmpty(orderDTO.getOrderUuid())){
				orderDTO = orderService.getOrderByOrderUuid(orderDTO.getOrderUuid());
				resultDTO.getBody().setData(orderDTO);
			}else if(orderDTO.getUserDTO()!=null){
				List<OrderDTO> orderDTOList = orderService.getOrderByUser(orderDTO.getUserDTO());
				OrderDTOList list = new OrderDTOList();
				list.setOrders(orderDTOList);
				resultDTO.getBody().setData(list);
			}else if(orderDTO.getMerchantDTO()!=null){
				List<OrderDTO> orderDTOList = orderService.getOrderByMerchant(orderDTO.getMerchantDTO());
				OrderDTOList list = new OrderDTOList();
				list.setOrders(orderDTOList);
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
	
	/**获取订单支付金额(使用拆分后的主订单号查询)
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryPaymentAmount", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryPaymentAmount(@RequestBody RequestDTO<OrderDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTO orderDTO = requestDTO.getBody();
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
			String pOrderNo = orderDTO.getpOrderNo();
			BigDecimal actualAmount = orderService.getActualAmountByPOrderNo(pOrderNo);
			orderDTO.setActualAmount(actualAmount);
			resultDTO.getBody().setData(orderDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**分页查询订单
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/searchOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchOrder(@RequestBody RequestDTO<OrderSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderSearchDTO orderSearchDTO = requestDTO.getBody();
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
			List<OrderDTO> orderDTOList = orderService.searchOrders(orderSearchDTO, orderSearchDTO.getStartIndex(), orderSearchDTO.getPageSize());
			OrderDTOList list = new OrderDTOList();
			list.setOrders(orderDTOList);
			int total = orderService.searchOrderTotal(orderSearchDTO);
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
	@RequestMapping(value = "/consumer/groupBuyDetail", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO groupBuyDetail(@RequestBody RequestDTO<GroupBuyDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		GroupBuyDTO groupBuyDTO = requestDTO.getBody();
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
	
	/**分页搜索拼团单
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/searchGroupBuy", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchGroupBuy(@RequestBody RequestDTO<GroupBuySearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		GroupBuySearchDTO groupBuySearchDTO = requestDTO.getBody();
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
	
	/**查询订单数量
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/searchOrderTotal", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchOrderTotal(@RequestBody RequestDTO<OrderSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderSearchDTO orderSearchDTO = requestDTO.getBody();
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
			OrderDTOList list = new OrderDTOList();
			int total = orderService.searchOrderTotal(orderSearchDTO);
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
	
	/**添加，删除订单
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/changeOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeOrder(@RequestBody RequestDTO<OrderDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTO orderDTO = requestDTO.getBody();
		String actionType = orderDTO.getActionType();
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
				List<OrderDTO> sOrderList = orderService.splitOrder(orderDTO);
				for (OrderDTO sOrder : sOrderList) {
					orderDTO.setShoppingCartUuidList(sOrder.getShoppingCartUuidList());
					orderService.addOrder(orderDTO);
				}
				OrderDTO rOrder = new OrderDTO();
				rOrder.setpOrderNo(orderDTO.getpOrderNo());
				resultDTO.getBody().setData(rOrder);
			}else if("DELETE".equals(actionType)){
				orderService.deleteOrder(orderDTO);
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
	
	/**秒杀下单
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/addSecKillOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO addSecKillOrder(@RequestBody RequestDTO<SecKillOrderDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		SecKillOrderDTO orderDTO = requestDTO.getBody();
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
			String orderNo = orderService.addSecKillOrder(orderDTO);
			OrderDTO rOrder = new OrderDTO();
			rOrder.setOrderNo(orderNo);
			resultDTO.getBody().setData(rOrder);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**团购下单
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/addGroupBuyOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO addGroupBuyOrder(@RequestBody RequestDTO<GroupBuyOrderDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		GroupBuyOrderDTO orderDTO = requestDTO.getBody();
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
			String orderNo = orderService.addGroupBuyOrder(orderDTO);
			OrderDTO rOrder = new OrderDTO();
			rOrder.setOrderNo(orderNo);
			resultDTO.getBody().setData(rOrder);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**积分兑换下单
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/addPointOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO addPointOrder(@RequestBody RequestDTO<PointOrderDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		PointOrderDTO orderDTO = requestDTO.getBody();
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
			String orderNo = orderService.addPointOrder(orderDTO);
			OrderDTO rOrder = new OrderDTO();
			rOrder.setOrderNo(orderNo);
			resultDTO.getBody().setData(rOrder);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/** 确认收货
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/confirmOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO confirmOrder(@RequestBody RequestDTO<OrderDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTO orderDTO = requestDTO.getBody();
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
			orderService.confirmOrder(orderDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**取消订单
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/cancelOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO cancelOrder(@RequestBody RequestDTO<OrderDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTO orderDTO = requestDTO.getBody();
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
        	orderService.cancelOrder(orderDTO);
        	resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**客户端点击秒杀按钮用以获取秒杀资格ID(拥有资格ID后续才能下单)
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/seckillQuanId", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO seckillQuanId(@RequestBody RequestDTO<SecKillOrderDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		SecKillOrderDTO secKillOrderDTO = requestDTO.getBody();
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
			String quanId = secKillService.getSecKillQuanId(secKillOrderDTO);
			secKillOrderDTO.setQuanlificationId(quanId);
			resultDTO.getBody().setData(secKillOrderDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}

	/**微信公众号支付统一下单接口
	 * @param requestDTO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/consumer/initWechatPayH5", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO initWechatPayH5(@RequestBody RequestDTO<OrderDTO> requestDTO, HttpServletRequest request) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTO orderDTO = requestDTO.getBody();
		String pOrderNo = orderDTO.getpOrderNo();
		String orderNo = orderDTO.getOrderNo();
		String openId = orderDTO.getOpenId();

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
			if(StringUtils.isEmpty(openId)) {
				String code = orderDTO.getCode();
				openId = wechatUtil.getOpenId(code).getOpenId();
			}

			//获取订单详情
			BigDecimal actualAmount = null;
			String out_trade_no = null;
			if(!StringUtils.isEmpty(pOrderNo)) {
				actualAmount = orderService.getActualAmountByPOrderNo(pOrderNo);
				out_trade_no = pOrderNo;
			}
			else if(!StringUtils.isEmpty(orderNo)) {
				orderDTO = orderService.getOrderByOrderNo(orderNo);
				actualAmount = orderDTO.getActualAmount();
				out_trade_no = orderNo;
			}


			//初始化微信统一下单接口
			WechatPayRequest payRequest = new WechatPayRequest();
			payRequest.setAppid(paymentUtil.getWechatAppIdOfficialAccount());
			payRequest.setMch_id(paymentUtil.getWechatMerchantId());
			payRequest.setNonce_str(RandomStringUtils.randomAlphanumeric(32));
			payRequest.setOut_trade_no(out_trade_no);
			payRequest.setBody(WechatConstants.PRODUCT_NAME);
			payRequest.setTotal_fee(actualAmount.multiply(new BigDecimal(100))
					.intValue());
			payRequest.setSpbill_create_ip(request.getRemoteAddr());
			payRequest.setNotify_url(paymentUtil.getWechatNotifyUrl());
			payRequest.setTrade_type("JSAPI");
			payRequest.setAttach(WechatConstants.PRODUCT_NAME);
			payRequest.setProduct_id(WechatConstants.PRODUCT_ID);
			payRequest.setOpenid(openId);
			payRequest.setSign(wechatUtil.getSign(payRequest));
			String requestXML = wechatUtil.toXML(payRequest);
			requestXML = new String(requestXML.getBytes("utf-8"),"iso-8859-1");
			logger.info("Wechat unified order request: "+requestXML);
			//下单
			String wechatResponseStr = wechatUtil.postWechatUnifiedOrder(requestXML);
			logger.info("Wechat unified order Response: "+wechatResponseStr);
			WechatPayResponse payResponse = (WechatPayResponse)wechatUtil.fromXML2WechatResponse(WechatPayResponse.class,wechatResponseStr);
			if("SUCCESS".equals(payResponse.getReturn_code()) && "SUCCESS".equals(payResponse.getResult_code())){

				SignatureDTO signatureDTO = wxJsApiSdkUtility.getWXPaySignatureH5(payResponse.getPrepay_id());
				signatureDTO.setOut_trade_no(payRequest.getOut_trade_no());
				resultDTO.getBody().setData(signatureDTO);
				resultDTO.getBody().getStatus().setStatusCode("0");
			}else{
				resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
				resultDTO.getBody().getStatus().setErrorCode("1");
				resultDTO.getBody().getStatus().setErrorDesc("调用微信支付异常, 请稍后重试");
			}
		} catch (Exception e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode("1");
			resultDTO.getBody().getStatus().setErrorDesc("创建支付二维码异常, 请稍后重试");
			return resultDTO;
		}

		return resultDTO;
	}
	
	/**微信APP支付统一下单接口
	 * @param requestDTO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/consumer/initWechatPayApp", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO initWechatPayApp(@RequestBody RequestDTO<OrderDTO> requestDTO, HttpServletRequest request) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTO orderDTO = requestDTO.getBody();
		String pOrderNo = orderDTO.getpOrderNo();
		String orderNo = orderDTO.getOrderNo();

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
			//获取订单详情
			BigDecimal actualAmount = null;
			String out_trade_no = null;
			if(!StringUtils.isEmpty(pOrderNo)) {
				actualAmount = orderService.getActualAmountByPOrderNo(pOrderNo);
				out_trade_no = pOrderNo;
			}
			else if(!StringUtils.isEmpty(orderNo)) {
				orderDTO = orderService.getOrderByOrderNo(orderNo);
				actualAmount = orderDTO.getActualAmount();
				out_trade_no = orderNo;
			}


			//初始化微信统一下单接口
			WechatPayRequest payRequest = new WechatPayRequest();
			payRequest.setAppid(paymentUtil.getWechatAppIdOpen());
			payRequest.setMch_id(paymentUtil.getWechatMerchantId());
			payRequest.setNonce_str(RandomStringUtils.randomAlphanumeric(32));
			payRequest.setOut_trade_no(out_trade_no);
			payRequest.setBody(WechatConstants.PRODUCT_NAME);
			payRequest.setTotal_fee(actualAmount.multiply(new BigDecimal(100))
					.intValue());
			payRequest.setSpbill_create_ip(request.getRemoteAddr());
			payRequest.setNotify_url(paymentUtil.getWechatNotifyUrl());
			payRequest.setTrade_type("APP");
			payRequest.setAttach(WechatConstants.PRODUCT_NAME);
			payRequest.setProduct_id(WechatConstants.PRODUCT_ID);
			payRequest.setSign(wechatUtil.getAppSign(payRequest));
			String requestXML = wechatUtil.toXML(payRequest);
			requestXML = new String(requestXML.getBytes("utf-8"),"iso-8859-1");
			logger.info("Wechat unified order request: "+requestXML);
			//下单
			String wechatResponseStr = wechatUtil.postWechatUnifiedOrder(requestXML);
			logger.info("Wechat unified order Response: "+wechatResponseStr);
			WechatPayResponse payResponse = (WechatPayResponse)wechatUtil.fromXML2WechatResponse(WechatPayResponse.class,wechatResponseStr);
			if("SUCCESS".equals(payResponse.getReturn_code()) && "SUCCESS".equals(payResponse.getResult_code())){

				SignatureDTO signatureDTO = wxJsApiSdkUtility.getWXPaySignatureApp(payResponse.getPrepay_id());
				signatureDTO.setOut_trade_no(payRequest.getOut_trade_no());
				resultDTO.getBody().setData(signatureDTO);
				resultDTO.getBody().getStatus().setStatusCode("0");
			}else{
				resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
				resultDTO.getBody().getStatus().setErrorCode("1");
				resultDTO.getBody().getStatus().setErrorDesc("调用微信支付异常, 请稍后重试");
			}
		} catch (Exception e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode("1");
			resultDTO.getBody().getStatus().setErrorDesc("创建支付二维码异常, 请稍后重试");
			return resultDTO;
		}

		return resultDTO;
	}
	
	/**微信小程序支付统一下单接口
	 * @param requestDTO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/consumer/initWechatPay", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO initWechatPay(@RequestBody RequestDTO<OrderDTO> requestDTO, HttpServletRequest request) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTO orderDTO = requestDTO.getBody();
		String pOrderNo = orderDTO.getpOrderNo();
		String orderNo = orderDTO.getOrderNo();
		String openId = orderDTO.getOpenId();
		
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
			if(StringUtils.isEmpty(openId)) {
				String code = orderDTO.getCode();
				 openId = wechatUtil.getOpenId(code).getOpenId();
			}
			
			//获取订单详情
			BigDecimal actualAmount = null;
			String out_trade_no = null;
			if(!StringUtils.isEmpty(pOrderNo)) {
				actualAmount = orderService.getActualAmountByPOrderNo(pOrderNo);
				out_trade_no = pOrderNo;
			}
			else if(!StringUtils.isEmpty(orderNo)) {
				orderDTO = orderService.getOrderByOrderNo(orderNo);
				actualAmount = orderDTO.getActualAmount();
				out_trade_no = orderNo;
			}
				
			
			//初始化微信统一下单接口
			WechatPayRequest payRequest = new WechatPayRequest();
			payRequest.setAppid(paymentUtil.getWechatAppIdMiniProgram());
			payRequest.setMch_id(paymentUtil.getWechatMerchantId());
			payRequest.setNonce_str(RandomStringUtils.randomAlphanumeric(32));
			payRequest.setOut_trade_no(out_trade_no);
			payRequest.setBody(WechatConstants.PRODUCT_NAME);
			payRequest.setTotal_fee(actualAmount.multiply(new BigDecimal(100))
					.intValue());
			payRequest.setSpbill_create_ip(request.getRemoteAddr());
			payRequest.setNotify_url(paymentUtil.getWechatNotifyUrl());
			payRequest.setTrade_type("JSAPI");
			payRequest.setAttach(WechatConstants.PRODUCT_NAME);
			payRequest.setProduct_id(WechatConstants.PRODUCT_ID);
			payRequest.setOpenid(openId);
			payRequest.setSign(wechatUtil.getSign(payRequest));
			String requestXML = wechatUtil.toXML(payRequest);
			requestXML = new String(requestXML.getBytes("utf-8"),"iso-8859-1");
			logger.info("Wechat unified order request: "+requestXML);
			//下单
			String wechatResponseStr = wechatUtil.postWechatUnifiedOrder(requestXML);
			logger.info("Wechat unified order Response: "+wechatResponseStr);
			WechatPayResponse payResponse = (WechatPayResponse)wechatUtil.fromXML2WechatResponse(WechatPayResponse.class,wechatResponseStr);
			if("SUCCESS".equals(payResponse.getReturn_code()) && "SUCCESS".equals(payResponse.getResult_code())){
				
				SignatureDTO signatureDTO = wxJsApiSdkUtility.getWXPaySignature(payResponse.getPrepay_id());
				signatureDTO.setOut_trade_no(payRequest.getOut_trade_no());
				resultDTO.getBody().setData(signatureDTO);
				resultDTO.getBody().getStatus().setStatusCode("0");
			}else{
				resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
				resultDTO.getBody().getStatus().setErrorCode("1");
				resultDTO.getBody().getStatus().setErrorDesc("调用微信支付异常, 请稍后重试");
			}
		} catch (Exception e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode("1");
			resultDTO.getBody().getStatus().setErrorDesc("创建支付二维码异常, 请稍后重试");
			return resultDTO;
		}

		return resultDTO;
	}
	
	/**支付宝手机Wap支付统一下单接口
	 * @param requestDTO
	 * @param request
	 * @param httpResponse
	 * @return
	 */
	@RequestMapping(value = "/consumer/initAlipay", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO initAlipay(@RequestBody RequestDTO<OrderDTO> requestDTO, HttpServletRequest request, HttpServletResponse httpResponse) {

		OrderDTO orderDTO = requestDTO.getBody();
		String orderNo = orderDTO.getOrderNo();
		String pOrderNo = orderDTO.getpOrderNo();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		DecimalFormat df = new DecimalFormat("##0.00"); 
		try {
			//获取订单详情
			BigDecimal actualAmount = null;
			String out_trade_no = null;
			if(!StringUtils.isEmpty(pOrderNo)) {
				actualAmount = orderService.getActualAmountByPOrderNo(pOrderNo);
				out_trade_no = pOrderNo;
			}
			else if(!StringUtils.isEmpty(orderNo)) {
				orderDTO = orderService.getOrderByOrderNo(orderNo);
				actualAmount = orderDTO.getActualAmount();
				out_trade_no = orderNo;
			}
			
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConstants.ALIPAY_URL, paymentUtil.getAlipayAppId(),paymentUtil.getAlipayPrivateKey(), AlipayConstants.ALIPAY_FORMAT,AlipayConstants.ALIPAY_CHARSET, paymentUtil.getAlipayPublicKey(), AlipayConstants.ALIPAY_SIGNTYPE);
			//创建API对应的request
			AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
			//在公共参数中设置回跳和通知地址
	        alipayRequest.setReturnUrl(paymentUtil.getAlipayReturnUrl());
	        //异步回调一定是要在外网 不然支付宝服务器无法访问
	        alipayRequest.setNotifyUrl(paymentUtil.getAlipayNotifyUrl());
	        //填充业务参数
	        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
	        		+ "\"total_amount\":\""+ df.format(actualAmount) +"\"," 
	    			+ "\"subject\":\""+ AlipayConstants.ALIPAY_PRODUCT_NAME +"\"," 
	    			+ "\"product_code\":\""+AlipayConstants.ALIPAY_PRODUCT_CODE_QUICK_WAP_WAY+"\"}");
	        logger.info("Alipay order request: "+alipayRequest.getBizContent());
	        String form = alipayClient.pageExecute(alipayRequest).getBody(); 
	        logger.info("Alipay order response: "+form);
	        AlipayFormDTO alipayForm = new AlipayFormDTO(); 
	        alipayForm.setAlipayForm(form);
	        resultDTO.getBody().setData(alipayForm);
	        resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		}catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}catch (Exception e) {
			logger.error(e.getMessage());
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(BusinessErrorCode.COMMON_SYSTEM_ERROR);
			resultDTO.getBody().getStatus().setErrorDesc(MessageHelper.getMessageByErrorId(messageSource, BusinessErrorCode.COMMON_SYSTEM_ERROR));
			return resultDTO;
		}
		return resultDTO;
	}
	
	/**支付宝手机支付统一下单接口
	 * @param requestDTO
	 * @param request
	 * @param httpResponse
	 * @return
	 */
	@RequestMapping(value = "/consumer/initAlipayApp", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO initAlipayApp(@RequestBody RequestDTO<OrderDTO> requestDTO, HttpServletRequest request, HttpServletResponse httpResponse) {

		OrderDTO orderDTO = requestDTO.getBody();
		String orderNo = orderDTO.getOrderNo();
		String pOrderNo = orderDTO.getpOrderNo();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		DecimalFormat df = new DecimalFormat("##0.00"); 
		try {
			//获取订单详情
			BigDecimal actualAmount = null;
			String out_trade_no = null;
			if(!StringUtils.isEmpty(pOrderNo)) {
				actualAmount = orderService.getActualAmountByPOrderNo(pOrderNo);
				out_trade_no = pOrderNo;
			}
			else if(!StringUtils.isEmpty(orderNo)) {
				orderDTO = orderService.getOrderByOrderNo(orderNo);
				actualAmount = orderDTO.getActualAmount();
				out_trade_no = orderNo;
			}
			
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConstants.ALIPAY_URL, paymentUtil.getAlipayAppId(),paymentUtil.getAlipayPrivateKey(), AlipayConstants.ALIPAY_FORMAT,AlipayConstants.ALIPAY_CHARSET, paymentUtil.getAlipayPublicKey(), AlipayConstants.ALIPAY_SIGNTYPE);
			//创建API对应的request
			AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
			//在公共参数中设置回跳和通知地址
	        alipayRequest.setReturnUrl(paymentUtil.getAlipayReturnUrl());
	        //异步回调一定是要在外网 不然支付宝服务器无法访问
	        alipayRequest.setNotifyUrl(paymentUtil.getAlipayNotifyUrl());
	        //填充业务参数
	        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
	        		+ "\"total_amount\":\""+ df.format(actualAmount) +"\"," 
	    			+ "\"subject\":\""+ AlipayConstants.ALIPAY_PRODUCT_NAME +"\"," 
	    			+ "\"product_code\":\""+AlipayConstants.ALIPAY_PRODUCT_CODE_QUICK_MSECURITY_WAY+"\"}");
	        logger.info("Alipay order request: "+alipayRequest.getBizContent());
	        String form = alipayClient.sdkExecute(alipayRequest).getBody(); 
	        logger.info("Alipay order response: "+form);
	        AlipayFormDTO alipayForm = new AlipayFormDTO(); 
	        alipayForm.setAlipayForm(form);
	        resultDTO.getBody().setData(alipayForm);
	        resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		}catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}catch (Exception e) {
			logger.error(e.getMessage());
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(BusinessErrorCode.COMMON_SYSTEM_ERROR);
			resultDTO.getBody().getStatus().setErrorDesc(MessageHelper.getMessageByErrorId(messageSource, BusinessErrorCode.COMMON_SYSTEM_ERROR));
			return resultDTO;
		}
		return resultDTO;
	}
	
	/**余额支付
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/balancePay", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO balancePay(@RequestBody RequestDTO<OrderDTO> requestDTO) {
		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTO orderDTO = requestDTO.getBody();
		String paymentMethod = orderDTO.getPaymentMethod();
		String pOrderNo = orderDTO.getpOrderNo();
		String orderNo = orderDTO.getOrderNo();
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
			//拆分订单模式
			if(!StringUtils.isEmpty(pOrderNo)) {
				List<OrderDTO> orderList = orderService.getOrderByPOrderNo(pOrderNo);
				for (OrderDTO sOrder : orderList) {
					sOrder.setPaymentMethod(paymentMethod);
					orderService.payOrder(sOrder);
				}
			}else if(!StringUtils.isEmpty(orderNo)) {
				orderDTO = orderService.getOrderByOrderNo(orderNo);
				orderDTO.setPaymentMethod(paymentMethod);
				orderService.payOrder(orderDTO);
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
	 * 查询快递物流信息
	 */
	@RequestMapping(value = "/consumer/getCourierInfo", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getCourierInfo(@RequestBody RequestDTO<CourierResultDTO> requestDTO) {
		String tokenId = requestDTO.getHeader().getTokenId();
		CourierResultDTO courierResultDTO = requestDTO.getBody();
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
        	CourierDTO courierDTO = orderService.getCourierInfoByNo(courierResultDTO.getNumber(), courierResultDTO.getType());
        	resultDTO.getBody().setData(courierDTO);
        	resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**微信支付回调接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/wechatPayResponse", method = RequestMethod.POST)
	@ResponseBody
	public String wechatPayResponse(HttpServletRequest request, HttpServletResponse response) {
		int length = request.getContentLength();
		byte[] bytes = new byte[length];
		InputStream inputStream;
		try {
			inputStream = request.getInputStream();
			inputStream.read(bytes);
			String responseStr = new String(bytes, "UTF-8");
			logger.info("Wechat Response Callback: "+responseStr);
			
			WechatPayResult payResultResponse = (WechatPayResult)wechatUtil.fromXML2WechatResult(WechatPayResult.class,responseStr,"xml");
			String out_trade_no = payResultResponse.getOut_trade_no();
			String transactionId = payResultResponse.getTransaction_id();
			if("SUCCESS".equals(payResultResponse.getReturn_code())&&"SUCCESS".equals(payResultResponse.getResult_code())){
				logger.info("支付订单号: "+ out_trade_no);
				//合并支付订单
				if(out_trade_no.startsWith(OrderConstants.ORDER_TYPE_MN)) {
					List<OrderDTO> orderList = orderService.getOrderByPOrderNo(out_trade_no);
					for (OrderDTO orderDTO : orderList) {
						//可能会多次收到微信的异步通信, 如果是已支付状态就不需要再做一次
						if(OrderConstants.ORDER_STATUS_NOT_PAYED.equals(orderDTO.getOrderStatus())){
							orderDTO.setPaymentMethod(OrderConstants.ORDER_PAYMENT_METHOD_WECHAT);
							orderDTO.setTransactionId(transactionId);
							orderService.payOrder(orderDTO);
						}
					}
				}
				//非合并支付订单
				else if(out_trade_no.startsWith(OrderConstants.ORDER_TYPE_GM)) {
					OrderDTO orderDTO = orderService.getOrderByOrderNo(out_trade_no);
					//可能会多次收到微信的异步通信, 如果是已支付状态就不需要再做一次
					if(OrderConstants.ORDER_STATUS_NOT_PAYED.equals(orderDTO.getOrderStatus())){
						orderDTO.setPaymentMethod(OrderConstants.ORDER_PAYMENT_METHOD_WECHAT);
						orderDTO.setTransactionId(transactionId);
						orderService.payOrder(orderDTO);
					}
				}
			}else{
				response.sendError(500);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			try {
				response.sendError(500);
			} catch (IOException e1) {
				logger.error(e1.getMessage());
			}
		}
		return "";
	}
	
	/**微信退款回调接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/wechatRefundResponse", method = RequestMethod.POST)
	@ResponseBody
	public String wechatRefundResponse(HttpServletRequest request, HttpServletResponse response) {
		int length = request.getContentLength();
		byte[] bytes = new byte[length];
		InputStream inputStream;
		try {
			inputStream = request.getInputStream();
			inputStream.read(bytes);
			String responseStr = new String(bytes, "UTF-8");
			logger.info("Wechat Refund Callback: "+responseStr);
			
			WechatRefundResult refundResultResponse = (WechatRefundResult)wechatUtil.fromXML2WechatResult(WechatRefundResult.class,responseStr,"xml");
			String req_info = refundResultResponse.getReq_info();
			logger.info("解密前req_info:"+req_info);
			String req_info_decr = AESUtil.decryptData(req_info, paymentUtil.getWechatApiKey());
			logger.info("解密后req_info:"+req_info_decr);
			WechatRefundResult req_info_obj = (WechatRefundResult)wechatUtil.fromXML2WechatResult(WechatRefundResult.class,req_info_decr,"root");
			String orderNo = req_info_obj.getOut_refund_no();
			String refundStatus = req_info_obj.getRefund_status();
			logger.info("Out refund no: "+ orderNo);
			logger.info("Refund status: "+ refundStatus);
			OrderDTO orderDTO = orderService.getOrderByOrderNo(orderNo);
			if("SUCCESS".equals(refundResultResponse.getReturn_code()) && "SUCCESS".equals(refundStatus)){
				if(!StringUtils.isEmpty(orderDTO.getAfterSaleNo()))
					//售后退款
					orderAfterSaleService.updateRefundStatus(orderDTO.getAfterSaleNo(), "微信退款成功");
				else
					//取消订单退款
					orderService.updateRefundStatus(orderNo, "微信退款成功");
			}else{
				if(!StringUtils.isEmpty(orderDTO.getAfterSaleNo()))
					//售后退款
					orderAfterSaleService.updateRefundStatus(orderDTO.getAfterSaleNo(), "微信退款失败");
				else
					//取消订单退款
					orderService.updateRefundStatus(orderNo, "微信退款失败");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			try {
				response.sendError(500);
			} catch (IOException e1) {
				logger.error(e1.getMessage());
			}
		}
		return "";
	}
	
	/**支付宝回调接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/alipayResponse", method = RequestMethod.POST)
	@ResponseBody
	public String alipayResponse(HttpServletRequest request,  HttpServletResponse response) {
		try {
			Map<String,String> params = new HashMap<String,String>();
			Map<String,String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用
//				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			logger.info("接收支付宝notify_url回调:"+params.toString());
			//调用SDK验证签名
			boolean signVerified = AlipaySignature.rsaCheckV1(params, paymentUtil.getAlipayPublicKey(), AlipayConstants.ALIPAY_CHARSET, AlipayConstants.ALIPAY_SIGNTYPE); 

			//——请在这里编写您的程序（以下代码仅作参考）——
			
			/* 实际验证过程建议商户务必添加以下校验：
			1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
			2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
			3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
			4、验证app_id是否为该商户本身。
			*/
			if(signVerified) {//验证成功
				logger.info("接收支付宝notify_url回调验证成功");
				//商户订单号
				String out_trade_no = params.get("out_trade_no");
				logger.info("out_trade_no:" + out_trade_no);
			
				//支付宝交易号
				String trade_no = params.get("trade_no");
				logger.info("trade_no:" + trade_no);
			
				//交易状态
				String trade_status = params.get("trade_status");
				logger.info("trade_status:" + trade_status);
				
				if(trade_status.equals("TRADE_FINISHED")||trade_status.equals("TRADE_SUCCESS")){
					logger.info("支付订单号: "+ out_trade_no);
					//合并支付订单
					if(out_trade_no.startsWith(OrderConstants.ORDER_TYPE_MN)) {
						List<OrderDTO> orderList = orderService.getOrderByPOrderNo(out_trade_no);
						for (OrderDTO orderDTO : orderList) {
							//可能会多次收到微信的异步通信, 如果是已支付状态就不需要再做一次
							if(OrderConstants.ORDER_STATUS_NOT_PAYED.equals(orderDTO.getOrderStatus())){
								orderDTO.setPaymentMethod(OrderConstants.ORDER_PAYMENT_METHOD_ALIPAY);
								orderDTO.setTransactionId(trade_no);
								orderService.payOrder(orderDTO);
							}
						}
					}
					//非合并支付订单
					else if(out_trade_no.startsWith(OrderConstants.ORDER_TYPE_GM)) {
						OrderDTO orderDTO = orderService.getOrderByOrderNo(out_trade_no);
						//可能会多次收到微信的异步通信, 如果是已支付状态就不需要再做一次
						if(OrderConstants.ORDER_STATUS_NOT_PAYED.equals(orderDTO.getOrderStatus())){
							orderDTO.setPaymentMethod(OrderConstants.ORDER_PAYMENT_METHOD_ALIPAY);
							orderDTO.setTransactionId(trade_no);
							orderService.payOrder(orderDTO);
						}
					}
				}
			}else {
				logger.error("接收支付宝notify_url回调验证失败");
				return "failure";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "failure";
		}
		return "success";
	}
}
