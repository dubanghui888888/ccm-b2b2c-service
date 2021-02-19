package com.mb.ext.web.controller.consumer;

import java.math.BigDecimal;
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
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.service.CouponService;
import com.mb.ext.core.service.GroupBuyService;
import com.mb.ext.core.service.PointService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.SecKillService;
import com.mb.ext.core.service.ShoppingCartService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.coupon.CouponDTO;
import com.mb.ext.core.service.spec.coupon.CouponDTOList;
import com.mb.ext.core.service.spec.coupon.CouponSearchDTO;
import com.mb.ext.core.service.spec.coupon.UserCouponDTO;
import com.mb.ext.core.service.spec.coupon.UserCouponDTOList;
import com.mb.ext.core.service.spec.group.GroupBuyProductDTO;
import com.mb.ext.core.service.spec.order.GroupBuyOrderDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.service.spec.order.PointOrderDTO;
import com.mb.ext.core.service.spec.order.SecKillOrderDTO;
import com.mb.ext.core.service.spec.point.PointProductDTO;
import com.mb.ext.core.service.spec.point.PointProductSkuDTO;
import com.mb.ext.core.service.spec.seckill.SecKillProductDTO;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**会员优惠券类
 * @author B2B2C商城
 *
 */
@Controller
public class ConsumerCouponController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	
	@Autowired
	private UserService userService;

	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CouponService couponService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private SecKillService secKillService;
	
	@Autowired
	private GroupBuyService groupBuyService;
	
	@Autowired
	private PointService pointService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;
	
	/**查询会员优惠券
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryUserCoupon", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryUserCoupon(@RequestBody RequestDTO<CouponDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		CouponDTO couponDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		UserDTO nUserDTO = null;
		try {
			if(!tokenCheckUtil.checkToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			List<UserCouponDTO> couponDTOList = new ArrayList<UserCouponDTO>();
			if(couponDTO.getMerchantDTO()!=null)
				couponDTOList = couponService.getCouponByUserMerchant(nUserDTO, couponDTO.getMerchantDTO());
			else
				couponDTOList = couponService.getUserCoupon(nUserDTO);
			UserCouponDTOList list = new UserCouponDTOList();
			list.setUserCoupons(couponDTOList);
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
	
	/**查询优惠券
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryCoupon", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryCoupon(@RequestBody RequestDTO<CouponDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		CouponDTO couponDTO = requestDTO.getBody();
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
			if(!StringUtils.isEmpty(couponDTO.getCouponUuid())){
				couponDTO = couponService.getCoupon(couponDTO.getCouponUuid());
				resultDTO.getBody().setData(couponDTO);
			}else{
				List<CouponDTO> couponDTOList = couponService.getCouponByMerchant(couponDTO.getMerchantDTO());
				CouponDTOList list = new CouponDTOList();
				list.setCoupons(couponDTOList);
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
	
	/**修改优惠券
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/changeCoupon", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeCoupon(@RequestBody RequestDTO<UserCouponDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserCouponDTO userCouponDTO = requestDTO.getBody();
		CouponDTO couponDTO = userCouponDTO.getCouponDTO();
		UserDTO userDTO = userCouponDTO.getUserDTO();
		String actionType = userCouponDTO.getActionType();
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
			if("RECEIVE".equals(actionType)){
				couponService.receiveCoupon(couponDTO.getCouponUuid(), userDTO, userCouponDTO.getReceiveChannel());
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
	
	/**查询优惠券条形码
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryCouponBarCode", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryCouponBarCode(@RequestBody RequestDTO<UserCouponDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserCouponDTO userCouponDTO = requestDTO.getBody();
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
			String couponUuid = userCouponDTO.getUserCouponUuid();
			String url = couponService.generateCouponBarCode(couponUuid);
			userCouponDTO.setBarCodeUrl(url);
			resultDTO.getBody().setData(userCouponDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}

		return resultDTO;
	}
	
	/**分页查询会员优惠券
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/searchUserCoupon", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchUserCoupon(@RequestBody RequestDTO<CouponSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		CouponSearchDTO couponSearchDTO = requestDTO.getBody();
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
			List<UserCouponDTO> list = couponService.searchUserCoupon(couponSearchDTO);
			int total = couponService.searchUserCouponTotal(couponSearchDTO);
			UserCouponDTOList dtoList = new UserCouponDTOList();
			dtoList.setUserCoupons(list);
			dtoList.setTotal(total);
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
	
	/**计算优惠券优惠金额
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/calculateCouponAmount", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO calculateCouponAmount(@RequestBody RequestDTO<OrderDTO> requestDTO) {

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
			BigDecimal deductAmount = shoppingCartService.calculateOrderCouponAmount(orderDTO.getShoppingCartUuidList(), orderDTO.getUserCouponDTO().getCouponDTO().getCouponUuid());
			orderDTO.setDeductAmount(deductAmount);
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
	
	/**计算订单运费
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/calculateFreightAmount", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO calculateFreightAmount(@RequestBody RequestDTO<OrderDTO> requestDTO) {

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
			BigDecimal freightAmount = shoppingCartService.calculateOrderFreightAmount(orderDTO.getShoppingCartUuidList(),orderDTO.getDeliveryProvince(),orderDTO.getDeliveryCity(),orderDTO.getDeliveryArea());
			orderDTO.setFreightAmount(freightAmount);
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
	
	/**计算订单配送费
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/calculateDeliveryAmount", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO calculateDeliveryAmount(@RequestBody RequestDTO<OrderDTO> requestDTO) {

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
			BigDecimal deliveryAmount = shoppingCartService.calculateOrderDeliveryAmount(orderDTO.getShoppingCartUuidList(),orderDTO.getDeliveryLatitude(),orderDTO.getDeliveryLongitude());
			orderDTO.setDeliveryAmount(deliveryAmount);
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
	
	/** 秒杀商品计算运费
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/calculateSecKillFreightAmount", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO calculateSecKillFreightAmount(@RequestBody RequestDTO<SecKillOrderDTO> requestDTO) {

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
			String secKillUuid = secKillOrderDTO.getSecKillProduct().getSecKillProductUuid();
			SecKillProductDTO secKillProduct = secKillService.getSecKill(secKillUuid);
			BigDecimal freightAmount = shoppingCartService.calculateProductFreightAmount(secKillProduct.getProductDTO(),secKillProduct.getUnitPrice(),1,secKillOrderDTO.getOrderDTO().getDeliveryProvince(),secKillOrderDTO.getOrderDTO().getDeliveryCity(),secKillOrderDTO.getOrderDTO().getDeliveryArea());
			secKillOrderDTO.getOrderDTO().setFreightAmount(freightAmount);
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
	/** 秒杀商品计算同城配送费
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/calculateSecKillDeliveryAmount", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO calculateSecKillDeliveryAmount(@RequestBody RequestDTO<SecKillOrderDTO> requestDTO) {

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
			String secKillUuid = secKillOrderDTO.getSecKillProduct().getSecKillProductUuid();
			SecKillProductDTO secKillProduct = secKillService.getSecKill(secKillUuid);
			BigDecimal deliveryAmount = shoppingCartService.calculateProductDeliveryAmount(secKillProduct.getProductDTO(),secKillProduct.getUnitPrice(),1,secKillOrderDTO.getOrderDTO().getDeliveryLatitude(),secKillOrderDTO.getOrderDTO().getDeliveryLongitude());
			secKillOrderDTO.getOrderDTO().setDeliveryAmount(deliveryAmount);
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
	
	/**团购商品计算运费
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/calculateGroupBuyFreightAmount", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO calculateGroupBuyFreightAmount(@RequestBody RequestDTO<GroupBuyOrderDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		GroupBuyOrderDTO groupBuyOrderDTO = requestDTO.getBody();
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
			String groupBuyUuid = groupBuyOrderDTO.getGroupBuyProduct().getGroupBuyProductUuid();
			GroupBuyProductDTO groupBuyProduct = groupBuyService.getGroupBuy(groupBuyUuid);
			BigDecimal freightAmount = shoppingCartService.calculateProductFreightAmount(groupBuyProduct.getProductDTO(),groupBuyProduct.getUnitPrice(),groupBuyOrderDTO.getOrderDTO().getProductUnit(),groupBuyOrderDTO.getOrderDTO().getDeliveryProvince(),groupBuyOrderDTO.getOrderDTO().getDeliveryCity(),groupBuyOrderDTO.getOrderDTO().getDeliveryArea());
			groupBuyOrderDTO.getOrderDTO().setFreightAmount(freightAmount);
			resultDTO.getBody().setData(groupBuyOrderDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}

		return resultDTO;
	}
	/**团购商品计算配送费
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/calculateGroupBuyDeliveryAmount", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO calculateGroupBuyDeliveryAmount(@RequestBody RequestDTO<GroupBuyOrderDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		GroupBuyOrderDTO groupBuyOrderDTO = requestDTO.getBody();
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
			String groupBuyUuid = groupBuyOrderDTO.getGroupBuyProduct().getGroupBuyProductUuid();
			GroupBuyProductDTO groupBuyProduct = groupBuyService.getGroupBuy(groupBuyUuid);
			BigDecimal deliveryAmount = shoppingCartService.calculateProductDeliveryAmount(groupBuyProduct.getProductDTO(),groupBuyProduct.getUnitPrice(),groupBuyOrderDTO.getOrderDTO().getProductUnit(),groupBuyOrderDTO.getOrderDTO().getDeliveryLatitude(),groupBuyOrderDTO.getOrderDTO().getDeliveryLongitude());
			groupBuyOrderDTO.getOrderDTO().setDeliveryAmount(deliveryAmount);
			resultDTO.getBody().setData(groupBuyOrderDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}

		return resultDTO;
	}
	
	/**积分商品计算运费
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/calculatePointFreightAmount", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO calculatePointFreightAmount(@RequestBody RequestDTO<PointOrderDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		PointOrderDTO pointOrderDTO = requestDTO.getBody();
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
			String productUuid = pointOrderDTO.getProductDTO().getProductUuid();
			PointProductDTO pointProduct = pointService.getProductByUuid(productUuid);
			BigDecimal unitPrice = pointProduct.getUnitPrice();
			if(pointProduct.isSkuEnabled()) {
				PointProductSkuDTO productSkuDTO = pointOrderDTO.getProductSkuDTO();
				productSkuDTO = pointService.getProductSkuByUuid(productSkuDTO.getProductSkuUuid());
				unitPrice = productSkuDTO.getSkuUnitPrice();
			}
			BigDecimal freightAmount = shoppingCartService.calculatePointProductFreightAmount(pointProduct,unitPrice,pointOrderDTO.getOrderDTO().getProductUnit(),pointOrderDTO.getOrderDTO().getDeliveryProvince(),pointOrderDTO.getOrderDTO().getDeliveryCity(),pointOrderDTO.getOrderDTO().getDeliveryArea());
			pointOrderDTO.getOrderDTO().setFreightAmount(freightAmount);
			resultDTO.getBody().setData(pointOrderDTO);
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
