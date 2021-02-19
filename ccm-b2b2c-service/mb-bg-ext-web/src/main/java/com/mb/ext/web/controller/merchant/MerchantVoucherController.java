package com.mb.ext.web.controller.merchant;

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
import com.mb.ext.core.service.MerchantService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.VoucherService;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.coupon.UserVoucherDTO;
import com.mb.ext.core.service.spec.coupon.VoucherDTOList;
import com.mb.ext.core.service.spec.coupon.VoucherSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**商家电子卡券处理类
 * @author B2B2C商城
 *
 */
@Controller
public class MerchantVoucherController {

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
	private VoucherService voucherService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;

	
	/**查询电子卡券
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/inquiryVoucher", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryVoucher(@RequestBody RequestDTO<UserVoucherDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserVoucherDTO voucherDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			if(!StringUtils.isEmpty(voucherDTO.getUserVoucherUuid())){
				voucherDTO = voucherService.getUserVoucherByUuid(voucherDTO.getUserVoucherUuid());
				resultDTO.getBody().setData(voucherDTO);
			}else if(!StringUtils.isEmpty(voucherDTO.getVoucherCode())){
				voucherDTO = voucherService.getUserVoucherByCode(voucherDTO.getVoucherCode());
				resultDTO.getBody().setData(voucherDTO);
			}else if(voucherDTO.getOrderDTO()!=null){
				List<UserVoucherDTO> voucherDTOList = voucherService.getVoucherByOrder(voucherDTO.getOrderDTO());
				VoucherDTOList list = new VoucherDTOList();
				list.setVouchers(voucherDTOList);
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
	
	/**商家分页查询电子卡券
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/searchVoucher", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchVoucher(@RequestBody RequestDTO<VoucherSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		VoucherSearchDTO voucherSearchDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			List<UserVoucherDTO> list = voucherService.searchUserVoucher(voucherSearchDTO);
			int total = voucherService.searchUserVoucherTotal(voucherSearchDTO);
			VoucherDTOList dtoList = new VoucherDTOList();
			dtoList.setVouchers(list);
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
	
	/**核销电子卡券
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/writeOffVoucher", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO writeOffVoucher(@RequestBody RequestDTO<UserVoucherDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserVoucherDTO voucherDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			voucherService.writeOffVoucher(voucherDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}

		return resultDTO;
	}
	
	/**延长电子卡券有效期
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/extendVoucher", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO extendVoucher(@RequestBody RequestDTO<UserVoucherDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		UserVoucherDTO voucherDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			voucherService.extendVoucher(voucherDTO);
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
