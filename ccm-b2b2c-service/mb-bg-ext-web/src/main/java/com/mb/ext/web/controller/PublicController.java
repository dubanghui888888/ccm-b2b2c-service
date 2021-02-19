package com.mb.ext.web.controller;

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

import com.mb.ext.core.constant.AdConstants;
import com.mb.ext.core.constant.AuthenticationConstants;
import com.mb.ext.core.constant.ContentConstants;
import com.mb.ext.core.constant.ProfitConstants;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.AdminService;
import com.mb.ext.core.service.AreaService;
import com.mb.ext.core.service.AuthenticationService;
import com.mb.ext.core.service.ContentService;
import com.mb.ext.core.service.CouponService;
import com.mb.ext.core.service.GroupBuyService;
import com.mb.ext.core.service.LoginService;
import com.mb.ext.core.service.MerchantService;
import com.mb.ext.core.service.NoteService;
import com.mb.ext.core.service.OSSService;
import com.mb.ext.core.service.OrderService;
import com.mb.ext.core.service.PointService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.RoleService;
import com.mb.ext.core.service.SecKillService;
import com.mb.ext.core.service.SettingService;
import com.mb.ext.core.service.ShareService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.VerificationService;
import com.mb.ext.core.service.spec.AdDTO;
import com.mb.ext.core.service.spec.AdDTOList;
import com.mb.ext.core.service.spec.AreaDTO;
import com.mb.ext.core.service.spec.AreaDTOList;
import com.mb.ext.core.service.spec.AwardTypeDTOList;
import com.mb.ext.core.service.spec.GlobalSettingDTO;
import com.mb.ext.core.service.spec.GroupDTO;
import com.mb.ext.core.service.spec.MerchantSearchDTO;
import com.mb.ext.core.service.spec.ProductCommentSearchDTO;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.SettingDTO;
import com.mb.ext.core.service.spec.SwiperDTO;
import com.mb.ext.core.service.spec.SwiperDTOList;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.UserLevelDTO;
import com.mb.ext.core.service.spec.UserLevelDTOList;
import com.mb.ext.core.service.spec.WechatLiveRoomsResponse;
import com.mb.ext.core.service.spec.consumer.ConsumerHomeDTO;
import com.mb.ext.core.service.spec.content.ArticleDTO;
import com.mb.ext.core.service.spec.content.ArticleDTOList;
import com.mb.ext.core.service.spec.content.MateriaDTO;
import com.mb.ext.core.service.spec.content.MateriaDTOList;
import com.mb.ext.core.service.spec.content.PosterDTO;
import com.mb.ext.core.service.spec.content.PosterDTOList;
import com.mb.ext.core.service.spec.content.QaDTO;
import com.mb.ext.core.service.spec.content.QaDTOList;
import com.mb.ext.core.service.spec.content.SceneDTO;
import com.mb.ext.core.service.spec.content.SceneDTOList;
import com.mb.ext.core.service.spec.content.ShareDTO;
import com.mb.ext.core.service.spec.content.ShareDTOList;
import com.mb.ext.core.service.spec.content.TagDTO;
import com.mb.ext.core.service.spec.content.TagDTOList;
import com.mb.ext.core.service.spec.coupon.CouponDTO;
import com.mb.ext.core.service.spec.coupon.CouponDTOList;
import com.mb.ext.core.service.spec.coupon.CouponSearchDTO;
import com.mb.ext.core.service.spec.group.GroupBuyDTO;
import com.mb.ext.core.service.spec.group.GroupBuyProductDTO;
import com.mb.ext.core.service.spec.group.GroupBuyProductDTOList;
import com.mb.ext.core.service.spec.merchant.MerchantApplicationDTO;
import com.mb.ext.core.service.spec.merchant.MerchantApplicationDTOList;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTOList;
import com.mb.ext.core.service.spec.order.GroupBuyDTOList;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.service.spec.point.PointProductDTO;
import com.mb.ext.core.service.spec.point.PointProductDTOList;
import com.mb.ext.core.service.spec.point.SignSettingDTO;
import com.mb.ext.core.service.spec.product.GroupDTOList;
import com.mb.ext.core.service.spec.product.ProductBrandDTO;
import com.mb.ext.core.service.spec.product.ProductCateDTO;
import com.mb.ext.core.service.spec.product.ProductCateDTOList;
import com.mb.ext.core.service.spec.product.ProductCommentDTO;
import com.mb.ext.core.service.spec.product.ProductCommentDTOList;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.service.spec.product.ProductDTOList;
import com.mb.ext.core.service.spec.seckill.SecKillProductDTO;
import com.mb.ext.core.service.spec.seckill.SecKillProductDTOList;
import com.mb.ext.core.util.QRCodeGenerator;
import com.mb.ext.core.util.WechatUtil;
import com.mb.ext.msg.WXJSONAccessToken;
import com.mb.ext.web.util.MessageHelper;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**公开访问接口, 无权限控制
 * @author B2B2C商城
 *
 */
@Controller
public class PublicController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private OSSService ossService;
	
	@Autowired
	private CouponService couponService;
	
	@Autowired
	private SettingService settingService;

	@Autowired
	private ContentService contentService;

	@Autowired
	private UserService userService;

	@Autowired
	private NoteService noteService;

	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private VerificationService verificationService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private PointService pointService;
	
	@Autowired
	private GroupBuyService groupBuyService;

	@Autowired
	private SecKillService secKillService;
	
	@Autowired
	private ShareService shareService;

	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private QRCodeGenerator qrCodeGenerator;
	
	@Autowired
	private WechatUtil wechatUtil;

	/**移动端首页数据
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/home", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO home(@RequestBody RequestDTO<UserDTO> requestDTO) {

		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			//轮播图
			ConsumerHomeDTO homeDTO = new ConsumerHomeDTO();
			List<SwiperDTO> swiperDTOList = contentService.getSwipers();
			homeDTO.setSwipers(swiperDTOList);
			//首页广告
			List<AdDTO> adDTOList = contentService.getAdsByLocation(AdConstants.LOCATION_HOME);
			homeDTO.setAds(adDTOList);
			//首页显示的类目
			List<ProductCateDTO> rootCateList =  productService.getHomeProductCate();
			homeDTO.setCates(rootCateList);
			//秒杀活动
			List<SecKillProductDTO> secKillDTOList = secKillService.getActiveSecKills();
			homeDTO.setSecKills(secKillDTOList);
			//团购活动
			List<GroupBuyProductDTO> groupBuyDTOList = groupBuyService.getBeingGroupBuyProducts();
			homeDTO.setGroupBuys(groupBuyDTOList);
			//首页显示的商品组
			List<GroupDTO> groupDTOList = productService.getHomeProductGroups();
			homeDTO.setGroups(groupDTOList);
			//图文, 音视频
			List<ArticleDTO> articleDTOList = contentService.getPublishedArticles();
			homeDTO.setArticles(articleDTOList);
			
			resultDTO.getBody().setData(homeDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (Exception e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(BusinessErrorCode.COMMON_SYSTEM_ERROR);
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, BusinessErrorCode.COMMON_SYSTEM_ERROR);
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**根据微信code获取open id
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/getOpenIdByCode", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getOpenIdByCode(@RequestBody RequestDTO<OrderDTO> requestDTO) {

		OrderDTO orderDTO = requestDTO.getBody();
		String code = orderDTO.getCode();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			WXJSONAccessToken accessToken = wechatUtil.getOpenId(code);
			String openId = accessToken.getOpenId();
			UserDTO userDTO = new UserDTO();
			userDTO.setOpenId(openId);
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
	
	/**微信端通过open id登录
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/wechatLogin", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO wechatLogin(@RequestBody RequestDTO<UserDTO> requestDTO) {

		UserDTO userDTO = requestDTO.getBody();
		String openId = userDTO.getOpenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			userDTO = userService.getUserByOpenId(openId);
			String tokenId = loginService.wechatLogin(userDTO);
			resultDTO.getHeader().setTokenId(tokenId);
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
	
	/**微信端通过手机号码登录, 如果是新用户则注册
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/smsLogin", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO smsLogin(@RequestBody RequestDTO<UserDTO> requestDTO) {

		UserDTO userDTO = requestDTO.getBody();
		String mobileNo = userDTO.getPersonalPhone();
		String tokenId = null;
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			//验证码验证码
			if(verificationService.verifyCodeBySMS("86", mobileNo, userDTO.getVerificationCode())){
				//如果手机号码对应的用户存在则登录, 不存在则注册
				if(userService.getUserByMobileNo("86", mobileNo) == null) {
					userService.registerUser(userDTO);
				}
				tokenId = loginService.smsLogin(userDTO);
				//如果传入open id, 则与会员绑定
				if(!StringUtils.isEmpty(userDTO.getOpenId()))
					userService.bindWechat(userDTO);
				userDTO = userService.getUserDTO(userDTO);
				resultDTO.getHeader().setTokenId(tokenId);			
				resultDTO.getBody().setData(userDTO);
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
	
	/** 查询商品类目(共2级)
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/getProductCate", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getProductCate(@RequestBody RequestDTO<ProductCateDTO> requestDTO) {

		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			List<ProductCateDTO> rootCateList =  productService.getRootProductCate();
			ProductCateDTOList list = new ProductCateDTOList();
			list.setCates(rootCateList);
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
	
	/**查询商品类目对应的品牌和广告
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/getProductCateAddInfo", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getChildProductCate(@RequestBody RequestDTO<ProductCateDTO> requestDTO) {
		ProductCateDTO rootProductCate = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			List<ProductBrandDTO> productBrandList = productService.getProductBrandByProductCate(rootProductCate);
			rootProductCate.setBrandList(productBrandList);
			List<AdDTO> ads = contentService.getAdsByLocationProductCate(AdConstants.LOCATION_PRODUCT_CATEGORY,rootProductCate.getProductCateUuid());
			rootProductCate.setAdList(ads);
			resultDTO.getBody().setData(rootProductCate);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**首页显示商品类目数据
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/getHomeProductCate", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getHomeProductCate(@RequestBody RequestDTO<ProductCateDTO> requestDTO) {

		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			List<ProductCateDTO> rootCateList =  productService.getHomeProductCate();
			ProductCateDTOList list = new ProductCateDTOList();
			list.setCates(rootCateList);
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
	
	/**查询秒杀商品
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquirySecKill", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquirySecKill(@RequestBody RequestDTO<SecKillProductDTO> requestDTO) {

		SecKillProductDTO secKillProductDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!StringUtils.isEmpty(secKillProductDTO.getSecKillProductUuid())){
				secKillProductDTO = secKillService.getSecKill(secKillProductDTO.getSecKillProductUuid());
				resultDTO.getBody().setData(secKillProductDTO);
			}else{
				List<SecKillProductDTO> secKillDTOList = secKillService.getActiveSecKills();
				SecKillProductDTOList list = new SecKillProductDTOList();
				list.setSecKills(secKillDTOList);
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
	
	/**查询首页显示商品组
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquiryProductGroup", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryProductGroup(@RequestBody RequestDTO<GroupDTO> requestDTO){
		GroupDTO gruopDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			
			if(!StringUtils.isEmpty(gruopDTO.getGroupUuid())) {
				GroupDTO groupDTO = productService.getProductGroupByUuid(gruopDTO.getGroupUuid());
				resultDTO.getBody().setData(groupDTO);
			}else if(!StringUtils.isEmpty(gruopDTO.getGroupName())) {
				GroupDTO groupDTO = productService.getProductGroupByName(gruopDTO.getGroupName());
				resultDTO.getBody().setData(groupDTO);
			}else{
				List<GroupDTO> groupDTOList = productService.getHomeProductGroups();
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
	
	/**查询团购商品
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquiryGroupBuy", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryGroupBuy(@RequestBody RequestDTO<GroupBuyProductDTO> requestDTO) {

		GroupBuyProductDTO groupProductDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!StringUtils.isEmpty(groupProductDTO.getGroupBuyProductUuid())){
				groupProductDTO = groupBuyService.getGroupBuyProduct(groupProductDTO.getGroupBuyProductUuid());
				resultDTO.getBody().setData(groupProductDTO);
			}else{
				List<GroupBuyProductDTO> groupDTOList = groupBuyService.getBeingGroupBuyProducts();
				GroupBuyProductDTOList list = new GroupBuyProductDTOList();
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
	
	/**查询积分商品
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquiryPointProduct", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryPointProduct(@RequestBody RequestDTO<PointProductDTO> requestDTO) {

		PointProductDTO pointProductDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!StringUtils.isEmpty(pointProductDTO.getProductUuid())){
				pointProductDTO = pointService.getProductByUuid(pointProductDTO.getProductUuid());
				resultDTO.getBody().setData(pointProductDTO);
			}else{
				List<PointProductDTO> pointDTOList = new ArrayList<PointProductDTO>();
				PointProductDTOList list = new PointProductDTOList();
				list.setProducts(pointDTOList);
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
	
	/** 搜索产品(公开方法, 无权限控制)
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/searchProduct", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchProduct(@RequestBody RequestDTO<ProductSearchDTO> requestDTO) {

		ProductSearchDTO productSearchDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
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

	/** 搜索商家(公开方法, 无权限控制)
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/searchMerchant", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchMerchant(@RequestBody RequestDTO<MerchantSearchDTO> requestDTO) {

		MerchantSearchDTO merchantSearchDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			List<MerchantDTO> dtoList = merchantService.searchPublicMerchants(merchantSearchDTO);
			int total = merchantService.searchMerchantTotal(merchantSearchDTO);
			MerchantDTOList list = new MerchantDTOList();
			list.setMerchants(dtoList);
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

	/** 搜索商家优惠券(公开方法, 无权限控制)
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/searchMerchantCoupon", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchMerchantCoupon(@RequestBody RequestDTO<MerchantSearchDTO> requestDTO) {

		MerchantSearchDTO merchantSearchDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			List<MerchantDTO> dtoList = merchantService.searchMerchantCoupons(merchantSearchDTO);
			int total = merchantService.searchMerchantTotal(merchantSearchDTO);
			MerchantDTOList list = new MerchantDTOList();
			list.setMerchants(dtoList);
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

	/** 根据商品搜索拼团活动(公开方法, 无权限控制)
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/getGroupBuyByProduct", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getGroupBuyByProduct(@RequestBody RequestDTO<GroupBuyProductDTO> requestDTO) {

		GroupBuyProductDTO groupBuyProductDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			List<GroupBuyDTO> dtoList = groupBuyService.getActiveGroupBuysByGroupBuyProduct(groupBuyProductDTO.getGroupBuyProductUuid());
			GroupBuyDTOList list = new GroupBuyDTOList();
			list.setGroupBuys(dtoList);
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
	
	/**搜索积分商品(公开方法, 无权限控制)
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/searchPointProduct", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchPointProduct(@RequestBody RequestDTO<ProductSearchDTO> requestDTO) {

		ProductSearchDTO productSearchDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			List<PointProductDTO> dtoList = pointService.searchPointProduct(productSearchDTO);
			int total = pointService.searchPointProductTotal(productSearchDTO);
			PointProductDTOList list = new PointProductDTOList();
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
	
	
	/**搜索商品评论
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/searchProductComment", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchProductComment(@RequestBody RequestDTO<ProductCommentSearchDTO> requestDTO){
		ProductCommentSearchDTO searchDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			
			List<ProductCommentDTO> dtoList = productService.searchProductComment(searchDTO,true);
			int total = productService.searchProductCommentTotal(searchDTO,true);
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
	
	/**查询单个产品详情
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquiryProductDetail", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryProductDetail(@RequestBody RequestDTO<ProductDTO> requestDTO) {

		ProductDTO productDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			productDTO = productService.getProductByUuid(productDTO.getProductUuid());
			resultDTO.getBody().setData(productDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**按照条件搜索优惠券
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/searchCoupon", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchCoupon(@RequestBody RequestDTO<CouponSearchDTO> requestDTO) {

		CouponSearchDTO couponSearchDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			List<CouponDTO> couponDTOList = couponService.searchCoupon(couponSearchDTO);
			int total = couponService.searchCouponTotal(couponSearchDTO);
			CouponDTOList list = new CouponDTOList();
			list.setCoupons(couponDTOList);
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
	
	/**商家入驻
	 * @param requestDTO - 请求数据
	 * @return - 返回数据
	 */
	@RequestMapping(value = "/public/registerMerchant", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO registerMerchant(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		MerchantDTO merchantDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			String merchantUuid = merchantService.createMerchant(merchantDTO);
			merchantDTO.setMerchantUuid(merchantUuid);
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
	
	/**查询商家
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquiryMerchant", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryMerchant(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		MerchantDTO merchantDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!StringUtils.isEmpty(merchantDTO.getMerchantUuid()))
				merchantDTO = merchantService.getMerchantByUuid(merchantDTO.getMerchantUuid());
			else if(!StringUtils.isEmpty(merchantDTO.getMobileNo())){
				merchantDTO = merchantService.getMerchantByMobileNo(merchantDTO.getMobileNo());
			}
			int followTotal = merchantService.getFollowsTotal(merchantDTO);
			merchantDTO.setFollowTotal(followTotal);
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
	
	/**查询轮播图
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/inquirySwiper", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquirySwiper(@RequestBody RequestDTO<SwiperDTO> requestDTO) {
		SwiperDTO swiperDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!StringUtils.isEmpty(swiperDTO.getSwiperUuid())){
				swiperDTO = contentService.getSwiperByUuid(swiperDTO.getSwiperUuid());
				resultDTO.getBody().setData(swiperDTO);
			}else{
				List<SwiperDTO> swiperDTOList = contentService.getSwipers();
				SwiperDTOList list = new SwiperDTOList();
				list.setSwipers(swiperDTOList);
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
	
	/**查询广告
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/inquiryAd", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryAd(@RequestBody RequestDTO<AdDTO> requestDTO) {
		AdDTO adDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!StringUtils.isEmpty(adDTO.getAdUuid())){
				adDTO = contentService.getAdByUuid(adDTO.getAdUuid());
				resultDTO.getBody().setData(adDTO);
			}else if(!StringUtils.isEmpty(adDTO.getLocation())){
				List<AdDTO> adDTOList = contentService.getAdsByLocation(adDTO.getLocation());
				AdDTOList list = new AdDTOList();
				list.setAds(adDTOList);
				resultDTO.getBody().setData(list);
			}else if(adDTO.getProductCateDTO()!=null){
				List<AdDTO> adDTOList = contentService.getAdsByProductCate(adDTO.getProductCateDTO().getProductCateUuid());
				AdDTOList list = new AdDTOList();
				list.setAds(adDTOList);
				resultDTO.getBody().setData(list);
			}else{
				List<AdDTO> adDTOList = contentService.getActiveAds();
				AdDTOList list = new AdDTOList();
				list.setAds(adDTOList);
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

	/**公开查询商品
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/inquiryProduct", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryProduct(
			@RequestBody RequestDTO<ProductDTO> requestDTO) {
		ProductDTO productDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			productService.getProductByUuid(productDTO.getProductUuid());
			resultDTO.getBody().setData(productDTO);
			resultDTO.getBody().getStatus()
					.setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus()
					.setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource,
					e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}
		return resultDTO;
	}
	
	/**查询会员注册礼包专用商品组
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquiryProductGroupForRegister", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryProductGroupForRegister(
			@RequestBody RequestDTO<GroupDTO> requestDTO) {
		GroupDTO groupDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			groupDTO = productService.getProductGroupForRegister();
			resultDTO.getBody().setData(groupDTO);
			resultDTO.getBody().getStatus()
					.setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus()
					.setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource,
					e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}
		return resultDTO;
	}
	
	/**查询用户等级
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/getUserLevel", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getUserLevels(
			@RequestBody RequestDTO<UserLevelDTO> requestDTO) {
		UserLevelDTO userLevelDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!StringUtils.isEmpty(userLevelDTO.getUserLevelUuid())) {
				userLevelDTO = userService.getUserLevelByUuid(userLevelDTO.getUserLevelUuid());
				resultDTO.getBody().setData(userLevelDTO);
			}else {
				List<UserLevelDTO> levels = userService.getUserLevels();
				UserLevelDTOList list= new UserLevelDTOList();
				list.setLevels(levels);
				resultDTO.getBody().setData(list);
			}
			resultDTO.getBody().getStatus()
					.setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus()
					.setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource,
					e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}
		return resultDTO;
	}

	/**查询标签
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquiryTag", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryTag(@RequestBody RequestDTO<TagDTO> requestDTO) {

		TagDTO tagDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			List<TagDTO> tagDTOList = new ArrayList<TagDTO>();
			if(ContentConstants.TAG_TYPE_POSTER.equals(tagDTO.getTagType()))
				tagDTOList = contentService.getTagsByType(ContentConstants.TAG_TYPE_POSTER);
			else if(ContentConstants.TAG_TYPE_SCENE.equals(tagDTO.getTagType()))
				tagDTOList = contentService.getTagsByType(ContentConstants.TAG_TYPE_SCENE);
			else if(ContentConstants.TAG_TYPE_ARTICLE.equals(tagDTO.getTagType()))
				tagDTOList = contentService.getTagsByType(ContentConstants.TAG_TYPE_ARTICLE);
			else if(ContentConstants.TAG_TYPE_QA.equals(tagDTO.getTagType()))
				tagDTOList = contentService.getTagsByType(ContentConstants.TAG_TYPE_QA);
			TagDTOList list = new TagDTOList();
			list.setTags(tagDTOList);
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
	
	/**查询热门问题
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquiryHotQa", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryHotQa(@RequestBody RequestDTO<QaDTO> requestDTO) {

		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			List<QaDTO> qaDTOList = contentService.getHotQas();
			QaDTOList list = new QaDTOList();
			list.setQas(qaDTOList);
			resultDTO.getBody().setData(list);
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
	
	/**查询海报
	 * @param requestDTO - 请求接口
	 * @return 返回接口
	 */
	@RequestMapping(value = "/public/inquiryPoster", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryPoster(@RequestBody RequestDTO<PosterDTO> requestDTO) {

		PosterDTO posterDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(posterDTO.getTagDTO() != null && !StringUtils.isEmpty(posterDTO.getTagDTO().getTagName())){
				List<PosterDTO> posterDTOList = contentService.getPostersByTagName(posterDTO.getTagDTO().getTagName());
				PosterDTOList list = new PosterDTOList();
				list.setPosters(posterDTOList);
				resultDTO.getBody().setData(list);
			}else{
				List<PosterDTO> posterDTOList = contentService.getPosters();
				PosterDTOList list = new PosterDTOList();
				list.setPosters(posterDTOList);
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
	
	/**查询场景
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquiryScene", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryScene(@RequestBody RequestDTO<SceneDTO> requestDTO) {

		SceneDTO sceneDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!StringUtils.isEmpty(sceneDTO.getSceneUuid())){
				sceneDTO = contentService.getSceneByUuid(sceneDTO.getSceneUuid());
				resultDTO.getBody().setData(sceneDTO);
			}
			else if(sceneDTO.getTagDTO() != null && !StringUtils.isEmpty(sceneDTO.getTagDTO().getTagName())){
				List<SceneDTO> sceneDTOList = contentService.getScenesByTagName(sceneDTO.getTagDTO().getTagName());
				SceneDTOList list = new SceneDTOList();
				list.setScenes(sceneDTOList);
				resultDTO.getBody().setData(list);
			}else{
				List<SceneDTO> sceneDTOList = contentService.getScenes();
				SceneDTOList list = new SceneDTOList();
				list.setScenes(sceneDTOList);
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
	
	/**查询素材
	 * @param requestDTO - 请求数据
	 * @return - 返回数据
	 */
	@RequestMapping(value = "/public/inquiryMateria", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryMateria(@RequestBody RequestDTO<MateriaDTO> requestDTO) {

		MateriaDTO materiaDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!StringUtils.isEmpty(materiaDTO.getType())){
				List<MateriaDTO> materiaDTOList = contentService.getMateriasByType(materiaDTO.getType());
				MateriaDTOList list = new MateriaDTOList();
				list.setMaterias(materiaDTOList);
				resultDTO.getBody().setData(list);
			}else{
				List<MateriaDTO> materiaDTOList = contentService.getMaterias();
				MateriaDTOList list = new MateriaDTOList();
				list.setMaterias(materiaDTOList);
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
	
	
	/**查询文案
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquiryArticle", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryArticle(@RequestBody RequestDTO<ArticleDTO> requestDTO) {

		ArticleDTO articleDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!StringUtils.isEmpty(articleDTO.getArticleUuid())){
				articleDTO = contentService.getArticleByUuid(articleDTO.getArticleUuid());
				resultDTO.getBody().setData(articleDTO);
			}
			else if(articleDTO.getTagDTO() != null && !StringUtils.isEmpty(articleDTO.getTagDTO().getTagName())){
				List<ArticleDTO> articleDTOList = contentService.getPublishedArticlesByTagName(articleDTO.getTagDTO().getTagName());
				ArticleDTOList list = new ArticleDTOList();
				list.setArticles(articleDTOList);
				resultDTO.getBody().setData(list);
			}else if(!StringUtils.isEmpty(articleDTO.getArticleType())){
				List<ArticleDTO> articleDTOList = contentService.getPublishedArticlesByType(articleDTO.getArticleType());
				ArticleDTOList list = new ArticleDTOList();
				list.setArticles(articleDTOList);
				resultDTO.getBody().setData(list);
			}else{
				List<ArticleDTO> articleDTOList = contentService.getPublishedArticles();
				ArticleDTOList list = new ArticleDTOList();
				list.setArticles(articleDTOList);
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
	
	/**查询文案统计
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquiryArticleSummary", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryArticleSummary(@RequestBody RequestDTO<ArticleDTO> requestDTO) {

		ArticleDTO articleDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			int articleCountImage = contentService.getArticleCountByType(ContentConstants.ARTICLE_TYPE_IMAGE);
			int articleCountAudio = contentService.getArticleCountByType(ContentConstants.ARTICLE_TYPE_AUDIO);
			int articleCountVideo = contentService.getArticleCountByType(ContentConstants.ARTICLE_TYPE_VEDIO);
			articleDTO.setArticleCountImage(articleCountImage);
			articleDTO.setArticleCountAudio(articleCountAudio);
			articleDTO.setArticleCountVideo(articleCountVideo);
			resultDTO.getBody().setData(articleDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**查询常见问题
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquiryQa", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryQa(@RequestBody RequestDTO<QaDTO> requestDTO) {

		QaDTO qaDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!StringUtils.isEmpty(qaDTO.getQaUuid())){
				qaDTO = contentService.getQaByUuid(qaDTO.getQaUuid());
				resultDTO.getBody().setData(qaDTO);
			}
			else if(qaDTO.getTagDTO() != null && !StringUtils.isEmpty(qaDTO.getTagDTO().getTagName())){
				List<QaDTO> qaDTOList = contentService.getPublishedQaByTagName(qaDTO.getTagDTO().getTagName());
				QaDTOList list = new QaDTOList();
				list.setQas(qaDTOList);
				resultDTO.getBody().setData(list);
			}else{
				List<QaDTO> qaDTOList = contentService.getPublishedQas();
				QaDTOList list = new QaDTOList();
				list.setQas(qaDTOList);
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
	
	/**搜索常见问题
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/searchQa", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchQa(@RequestBody RequestDTO<QaDTO> requestDTO) {

		QaDTO qaDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			List<QaDTO> qaDTOList = contentService.searchQas(qaDTO.getTitle());
			QaDTOList list = new QaDTOList();
			list.setQas(qaDTOList);
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
	
	/**分页查询文案
	 * @param requestDTO - 请求数据
	 * @return - 返回数据
	 */
	@RequestMapping(value = "/public/inquiryArticlePagination", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryArticlePagination(@RequestBody RequestDTO<ArticleDTO> requestDTO) {

		ArticleDTO articleDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(articleDTO.getTagDTO() != null && !StringUtils.isEmpty(articleDTO.getTagDTO().getTagName())){
				String tagName = articleDTO.getTagDTO().getTagName();
				int totalCount = contentService.getPublishedArticleCountByTagName(tagName);
				int startIndex = articleDTO.getStartIndex();
				int pageSize = articleDTO.getPageSize();
				List<ArticleDTO> articleDTOList = contentService.getPublishedArticlesByTagNamePage(tagName, startIndex, pageSize);
				ArticleDTOList list = new ArticleDTOList();
				list.setArticles(articleDTOList);
				list.setTotalCount(totalCount);
				resultDTO.getBody().setData(list);
			}else if(!StringUtils.isEmpty(articleDTO.getActionType())){
				String articleType = articleDTO.getArticleType();
				int totalCount = contentService.getPublishedArticleCountByType(articleType);
				int startIndex = articleDTO.getStartIndex();
				int pageSize = articleDTO.getPageSize();
				List<ArticleDTO> articleDTOList = contentService.getPublishedArticlesByTypePage(articleType, startIndex, pageSize);
				ArticleDTOList list = new ArticleDTOList();
				list.setArticles(articleDTOList);
				list.setTotalCount(totalCount);
				resultDTO.getBody().setData(list);
			}else{
				int totalCount = contentService.getPublishedArticleCount();
				int startIndex = articleDTO.getStartIndex();
				int pageSize = articleDTO.getPageSize();
				List<ArticleDTO> articleDTOList = contentService.getPublishedArticlesByPage(startIndex, pageSize);
				ArticleDTOList list = new ArticleDTOList();
				list.setArticles(articleDTOList);
				list.setTotalCount(totalCount);
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
	

	/**分页查询常见问题
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquiryQaPagination", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryQaPagination(@RequestBody RequestDTO<QaDTO> requestDTO) {

		QaDTO qaDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(qaDTO.getTagDTO() != null && !StringUtils.isEmpty(qaDTO.getTagDTO().getTagName())){
				String tagName = qaDTO.getTagDTO().getTagName();
				int totalCount = contentService.getPublishedQaCountByTagName(tagName);
				int startIndex = qaDTO.getStartIndex();
				int pageSize = qaDTO.getPageSize();
				List<QaDTO> qaDTOList = contentService.getPublishedQasByTagNamePage(tagName, startIndex, pageSize);
				QaDTOList list = new QaDTOList();
				list.setQas(qaDTOList);
				list.setTotalCount(totalCount);
				resultDTO.getBody().setData(list);
			}else{
				int totalCount = contentService.getPublishedQaCount();
				int startIndex = qaDTO.getStartIndex();
				int pageSize = qaDTO.getPageSize();
				List<QaDTO> qaDTOList = contentService.getPublishedQasByPage(startIndex, pageSize);
				QaDTOList list = new QaDTOList();
				list.setQas(qaDTOList);
				list.setTotalCount(totalCount);
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
	
	/**发布海报
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/publishPoster", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO publishPoster(@RequestBody RequestDTO<PosterDTO> requestDTO) {

		PosterDTO posterDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			contentService.publishPoster(posterDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**根据日期查询朋友圈文案
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquiryShareByDate", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryShareByDate(@RequestBody RequestDTO<ShareDTO> requestDTO) {

		ShareDTO shareDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			List<ShareDTO> shareDTOList = shareService.getSharesByCreateDate(shareDTO.getSearchDate());
			ShareDTOList list = new ShareDTOList();
			list.setShares(shareDTOList);
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
	
	/**查询朋友圈文案
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquiryShare", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryShare(@RequestBody RequestDTO<ShareDTO> requestDTO) {

		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			List<ShareDTO> shareDTOList = shareService.getShares();
			ShareDTOList list = new ShareDTOList();
			list.setShares(shareDTOList);
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
	
	/**查询从公众号导入的朋友圈文案
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquiryOfficialShare", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryOfficialShare(@RequestBody RequestDTO<UserDTO> requestDTO) {

		UserDTO userDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			//官方动态发布号码
			SettingDTO settingDTO = settingService.getSettingByName("OFFICIAL_SHARE_MOBILENO");
			if(settingDTO != null){
				String mobileNo = settingDTO.getValue();
				userDTO = userService.getUserByMobileNo("86", mobileNo);
				List<ShareDTO> shareDTOList = shareService.getSharesByUser(userDTO);
				ShareDTOList list = new ShareDTOList();
				list.setShares(shareDTOList);
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
	
	/**分页查询朋友圈文案
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquirySharePagination", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquirySharePagination(@RequestBody RequestDTO<ShareDTO> requestDTO) {

		ShareDTO shareDTO = requestDTO.getBody();
		int startIndex = shareDTO.getStartIndex();
		int pageSize = shareDTO.getPageSize();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			List<ShareDTO> shareDTOList = shareService.getPublishedSharesByPage(startIndex, pageSize);
			int total = shareService.getPublishedShareCount();
			ShareDTOList list = new ShareDTOList();
			list.setShares(shareDTOList);
			list.setTotalCount(total);
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

	
	/**查询省市区
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquiryAreas", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryAreas(@RequestBody RequestDTO<AreaDTO> requestDTO) {

		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			List<AreaDTO> areas = areaService.getAreas();
			AreaDTOList list = new AreaDTOList();
			list.setAreas(areas);
			resultDTO.getBody().setData(list);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);}
		return resultDTO;
	}
	
	/**查询城市
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/searchCity", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchCity(@RequestBody RequestDTO<AreaDTO> requestDTO) {

		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			List<AreaDTO> areas = areaService.getCitys();
			AreaDTOList list = new AreaDTOList();
			list.setAreas(areas);
			resultDTO.getBody().setData(list);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);}
		return resultDTO;
	}
	
	/** 获取全局应用设置
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/getGlobalApplicationSetting", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getGlobalApplicationSetting(@RequestBody RequestDTO<GlobalSettingDTO> requestDTO) {

		GlobalSettingDTO settingDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			settingDTO = settingService.getGlobalApplicationSetting();
			GlobalSettingDTO wechatSetting = settingService.getGlobalWechatSetting();
			settingDTO.setWechatAppIdOfficialAccount(wechatSetting.getWechatAppIdOfficialAccount());
			settingDTO.setWechatAppIdMiniProgram(wechatSetting.getWechatAppIdMiniProgram());
			settingDTO.setWechatAppIdOpen(wechatSetting.getWechatAppIdOpen());
			settingDTO.setAmapWebJsapiKey(wechatSetting.getAmapWebJsapiKey());
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
	
	/**获取积分签到设置
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/public/getSignSetting", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getSignSetting(@RequestBody RequestDTO<SignSettingDTO> requestDTO) {

		SignSettingDTO settingDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			settingDTO = settingService.getSignSetting();
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
	
	/**查询奖金类型
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/inquiryAwardType", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryAwardType(
			@RequestBody RequestDTO<UserLevelDTO> requestDTO) {
		UserLevelDTO userLevelDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {

			if (!StringUtils.isEmpty(userLevelDTO.getName())) {
				userLevelDTO = userService.getUserLevelByName(userLevelDTO
						.getName());
				resultDTO.getBody().setData(userLevelDTO);
			} else {
				List<String> dtoList = new ArrayList<String>();
				dtoList.add(ProfitConstants.AWARD_TRANTYPE_RECRUIT);
				dtoList.add(ProfitConstants.AWARD_TRANTYPE_SALE);
				dtoList.add(ProfitConstants.AWARD_TRANTYPE_PERFORMANCE);
				dtoList.add(ProfitConstants.AWARD_TRANTYPE_TRAINER);
				AwardTypeDTOList list = new AwardTypeDTOList();
				list.setAwardTypes(dtoList);
				resultDTO.getBody().setData(list);
			}
			resultDTO.getBody().getStatus()
					.setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus()
					.setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource,
					e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}
		return resultDTO;
	}
	
	/**获取微信订阅消息模板
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquirySuscribeMsg", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquirySuscribeMsg(
			@RequestBody RequestDTO<UserDTO> requestDTO) {
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			SettingDTO payDTO = settingService.getSettingByName("WX_SUSCRIBE_MSG_ORDER_PAY");
			SettingDTO deliverDTO = settingService.getSettingByName("WX_SUSCRIBE_MSG_ORDER_DELIVERED");
			SettingDTO returnDTO = settingService.getSettingByName("WX_SUSCRIBE_MSG_ORDER_RETURN");
			SettingDTO awardDTO = settingService.getSettingByName("WX_SUSCRIBE_MSG_AWARD");
			String payId = payDTO.getValue();
			String deliverId = deliverDTO.getValue();
			String returnId = returnDTO.getValue();
			String awardId = awardDTO.getValue();
			OrderDTO orderDTO = new OrderDTO();
			List<String> suscribeMsgList = new ArrayList<String>();
			suscribeMsgList.add(returnId);
			suscribeMsgList.add(deliverId);
			suscribeMsgList.add(payId);
			suscribeMsgList.add(awardId);
			orderDTO.setSuscribeMsgList(suscribeMsgList);
			resultDTO.getBody().setData(orderDTO);
			resultDTO.getBody().getStatus()
					.setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus()
					.setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource,
					e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}
		return resultDTO;
	}
	
	/**商家申请
	 * @param requestDTO 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/applyMerchant", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO applyMerchant(@RequestBody RequestDTO<MerchantApplicationDTO> requestDTO) {

		MerchantApplicationDTO applicationDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			merchantService.applyMerchantApplication(applicationDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}
		return resultDTO;
	}
	
	/**申请入驻记录查询
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquiryMerchantApplication", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryMerchantApplication(@RequestBody RequestDTO<MerchantApplicationDTO> requestDTO) {

		MerchantApplicationDTO applicationDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			List<MerchantApplicationDTO> dtoList = merchantService.inquiryApplicationByMerchant(applicationDTO.getMobileNo());
			MerchantApplicationDTOList list = new MerchantApplicationDTOList();
			list.setApplications(dtoList);
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
	
	/**获取微信直播间
	 * @param requestDTO - 请求数据
	 * @return 返回数据
	 */
	@RequestMapping(value = "/public/inquiryWechatLiveRooms", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryWechatLiveRooms(
			@RequestBody RequestDTO<UserDTO> requestDTO) {
		UserDTO userDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			WechatLiveRoomsResponse response = wechatUtil.getLiveRooms(userDTO.getStartIndex(), userDTO.getPageSize());
			resultDTO.getBody().setData(response);
			resultDTO.getBody().getStatus()
					.setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (Exception e) {
			resultDTO.getBody().getStatus()
					.setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(BusinessErrorCode.COMMON_SYSTEM_ERROR);
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource,
					BusinessErrorCode.COMMON_SYSTEM_ERROR);
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}
		return resultDTO;
	}
	
}
