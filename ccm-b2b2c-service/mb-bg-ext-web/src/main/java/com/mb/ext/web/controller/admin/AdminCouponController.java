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
import com.mb.ext.core.service.CouponService;
import com.mb.ext.core.service.MerchantService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.coupon.CouponDTO;
import com.mb.ext.core.service.spec.coupon.CouponDTOList;
import com.mb.ext.core.service.spec.coupon.CouponSearchDTO;
import com.mb.ext.core.service.spec.coupon.UserCouponDTO;
import com.mb.ext.core.service.spec.coupon.UserCouponDTOList;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**后台优惠券管理
 * @author B2B2C商城
 *
 */
@Controller
public class AdminCouponController {

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

	/**分页查询优惠券
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchCoupon", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchCoupon(@RequestBody RequestDTO<CouponSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		CouponSearchDTO couponSearchDTO = requestDTO.getBody();
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
	
	/**查询优惠券
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryCoupon", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryCoupon(@RequestBody RequestDTO<CouponDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		CouponDTO couponDTO = requestDTO.getBody();
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
			if(!StringUtils.isEmpty(couponDTO.getCouponUuid())){
				couponDTO = couponService.getCoupon(couponDTO.getCouponUuid());
				resultDTO.getBody().setData(couponDTO);
			}else if(couponDTO.getMerchantDTO()!=null){
				List<CouponDTO> couponDTOList = couponService.getCouponByMerchant(couponDTO.getMerchantDTO());
				CouponDTOList list = new CouponDTOList();
				list.setCoupons(couponDTOList);
				resultDTO.getBody().setData(list);
			}else{
				List<CouponDTO> couponDTOList = couponService.getCoupons();
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
	
	/**增删改优惠券
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeCoupon", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeCoupon(@RequestBody RequestDTO<CouponDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		CouponDTO couponDTO = requestDTO.getBody();
		String actionType = couponDTO.getActionType();
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
				couponService.addCoupon(couponDTO);
			}else if("MODIFY".equals(actionType)){
				couponService.updateCoupon(couponDTO);
			}else if("DELETE".equals(actionType)){
				couponService.deleteCoupon(couponDTO);
			}else if("DISABLE".equals(actionType)){
				couponService.disableCoupon(couponDTO.getCouponUuid());
			}else if("ENABLE".equals(actionType)){
				couponService.enableCoupon(couponDTO.getCouponUuid());
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
	
	/**查询会员优惠券
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchUserCoupon", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchUserCoupon(@RequestBody RequestDTO<CouponSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		CouponSearchDTO couponSearchDTO = requestDTO.getBody();
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
}
