package com.mb.ext.web.controller.consumer;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

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
import com.mb.ext.core.constant.LocationConstants;
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.AuthenticationService;
import com.mb.ext.core.service.CouponService;
import com.mb.ext.core.service.DeliveryService;
import com.mb.ext.core.service.FundService;
import com.mb.ext.core.service.GroupBuyService;
import com.mb.ext.core.service.LoginService;
import com.mb.ext.core.service.MerchantService;
import com.mb.ext.core.service.OSSService;
import com.mb.ext.core.service.PlatformService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.SettingService;
import com.mb.ext.core.service.ShareService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.VerificationService;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.content.PosterDTO;
import com.mb.ext.core.service.spec.group.GroupBuyDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.sybpay.SybPayService;
import com.mb.ext.core.util.MailSenderUtil;
import com.mb.ext.core.util.PosterUtil;
import com.mb.ext.core.util.QRCodeGenerator;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**会员内容处理类
 * @author B2B2C商城
 *
 */
@Controller
public class ConsumerContentController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private SybPayService<?> sybPayService;
	
	@Autowired
	private ShareService shareService;
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private FundService fundService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private GroupBuyService groupBuyService;
	
	@Autowired
	private DeliveryService deliveryService;
	
	@Autowired
	private VerificationService verificationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CouponService couponService;
	
	@Autowired
	private MailSenderUtil mailSenderUtil;
	
	@Autowired
	private PosterUtil posterUtil;

	@Autowired
	private QRCodeGenerator qrCodeGenerator;

	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;
	
	@Autowired
	private OSSService ossService;
	
	@Autowired
	private PlatformService platformService;
	
	/**生成海报
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/createPoster", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO createProductPoster(@RequestBody RequestDTO<PosterDTO> requestDTO){
		PosterDTO posterDTO = requestDTO.getBody();
		String shareHref = posterDTO.getShareHref();
		int hrefType = posterDTO.getHrefType();
		String tokenId = requestDTO.getHeader().getTokenId();
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
			int shareType = posterDTO.getShareType();
			//邀请码
			if(shareType==1) {
				UserDTO userDTO = userService.getUserByUuid(posterDTO.getUserUuid());
				if(hrefType == 1) {
					BufferedImage qrCodeImage = posterUtil.generateQrCode(shareHref, 230, 230);
					String posterUrl = posterUtil.generateUserPoster(settingService.getGlobalApplicationSetting().getApplicationName(), userDTO.getPhotoUrl(), userDTO.getName(), qrCodeImage);
					posterDTO.setPosterUrl(posterUrl);
				}
				else if(hrefType == 2) {
					BufferedImage qrCodeImage = qrCodeGenerator.getWXAppCodImg("pages/public/register", posterDTO.getUserUuid(), 230);
					String outputFileName = System.currentTimeMillis()+".jpg";
					File outputFile = new File(LocationConstants.TMP_PATH+"/"+outputFileName);
					ImageIO.write(qrCodeImage, "jpg", outputFile);
					String posterUrl = posterUtil.generateUserPoster(settingService.getGlobalApplicationSetting().getApplicationName(), userDTO.getPhotoUrl(), userDTO.getName(), qrCodeImage);
					posterDTO.setPosterUrl(posterUrl);
				}
			}
			//商品二维码
			else if(shareType==3) {
				ProductDTO productDTO = productService.getProductByUuid(posterDTO.getProductUuid());
				String logoUrl = settingService.getGlobalApplicationSetting().getApplicationLogo();
				if(hrefType == 1) {
					BufferedImage qrCodeImage = posterUtil.generateQrCode(shareHref, 230, 230);
					String posterUrl = posterUtil.generateProductPoster(logoUrl, productDTO.getProductName(), productDTO.getUnitPrice(), productDTO.getUnitPriceStandard(), productDTO.getProductMainImage().getUrl(), qrCodeImage);
					posterDTO.setPosterUrl(posterUrl);
				}
				else if(hrefType == 2) {
					BufferedImage qrCodeImage = qrCodeGenerator.getWXAppCodImg("pages/product/product", posterDTO.getProductUuid(), 230);
					String posterUrl = posterUtil.generateProductPoster(logoUrl, productDTO.getProductName(), productDTO.getUnitPrice(), productDTO.getUnitPriceStandard(), productDTO.getProductMainImage().getUrl(), qrCodeImage);
					posterDTO.setPosterUrl(posterUrl);
				}
			}
			//团购二维码
			else if(shareType==4) {
				GroupBuyDTO groupBuyDTO = groupBuyService.groupBuyDetail(posterDTO.getGroupBuyUuid());
				String logoUrl = settingService.getGlobalApplicationSetting().getApplicationLogo();
				if(hrefType == 1) {
					BufferedImage qrCodeImage = posterUtil.generateQrCode(shareHref, 230, 230);
					String posterUrl = posterUtil.generateProductPoster(logoUrl, groupBuyDTO.getGroupBuyProductDTO().getProductDTO().getProductName(), groupBuyDTO.getGroupBuyProductDTO().getUnitPrice(), groupBuyDTO.getGroupBuyProductDTO().getProductDTO().getUnitPrice(), groupBuyDTO.getGroupBuyProductDTO().getProductDTO().getProductMainImage().getUrl(), qrCodeImage);
					posterDTO.setPosterUrl(posterUrl);
				}else if(hrefType == 2) {
					BufferedImage qrCodeImage = qrCodeGenerator.getWXAppCodImg("pages/product/groupbuy", groupBuyDTO.getGroupBuyUuid(), 230);
					String outputFileName = System.currentTimeMillis()+".jpg";
					File outputFile = new File(LocationConstants.TMP_PATH+"/"+outputFileName);
					ImageIO.write(qrCodeImage, "jpg", outputFile);
					String posterUrl = posterUtil.generateProductPoster(logoUrl, groupBuyDTO.getGroupBuyProductDTO().getProductDTO().getProductName(), groupBuyDTO.getGroupBuyProductDTO().getUnitPrice(), groupBuyDTO.getGroupBuyProductDTO().getProductDTO().getUnitPrice(), groupBuyDTO.getGroupBuyProductDTO().getProductDTO().getProductMainImage().getUrl(), qrCodeImage);
					posterDTO.setPosterUrl(posterUrl);
				}
			}
			resultDTO.getBody().setData(posterDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		} catch (Exception e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(BusinessErrorCode.COMMON_SYSTEM_ERROR);
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, BusinessErrorCode.COMMON_SYSTEM_ERROR);
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}
}
