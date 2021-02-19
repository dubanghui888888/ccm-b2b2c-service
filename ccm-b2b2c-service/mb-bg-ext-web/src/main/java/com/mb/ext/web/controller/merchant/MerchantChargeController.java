package com.mb.ext.web.controller.merchant;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
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
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.mb.ext.core.constant.AlipayConstants;
import com.mb.ext.core.constant.AuthenticationConstants;
import com.mb.ext.core.constant.AuthorizationConstants;
import com.mb.ext.core.constant.WechatConstants;
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.FundService;
import com.mb.ext.core.service.MerchantService;
import com.mb.ext.core.service.SettingService;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.SignatureDTO;
import com.mb.ext.core.service.spec.WechatPayRequest;
import com.mb.ext.core.service.spec.WechatPayResponse;
import com.mb.ext.core.service.spec.merchant.MerchantChargeDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.order.AlipayFormDTO;
import com.mb.ext.core.util.PaymentUtil;
import com.mb.ext.core.util.WXJSAPISDKUtility;
import com.mb.ext.core.util.WechatUtil;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;


/**商家充值处理类
 * @author B2B2C商城
 *
 */
@Controller
public class MerchantChargeController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private FundService fundService;
	
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;
	
	@Autowired
	private PaymentUtil paymentUtil;
	
	@Autowired
	private WechatUtil wechatUtil;
	
	@Autowired
	private WXJSAPISDKUtility wxJsApiSdkUtility;
	
	/**商家PC端微信充值(生成支付二维码)时统一下单
	 * @param requestDTO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/initWechatPay", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO initWechatPay(@RequestBody RequestDTO<MerchantChargeDTO> requestDTO, HttpServletRequest request) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantChargeDTO merchantChargeDTO = requestDTO.getBody();
		String chargeNo = merchantChargeDTO.getChargeNo();
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
			//获取商家充值详情
			merchantChargeDTO = fundService.getMerchantChargeByChargeNo(chargeNo);
			
			//初始化微信统一下单接口
			WechatPayRequest payRequest = new WechatPayRequest();
			payRequest.setAppid(paymentUtil.getWechatAppIdMiniProgram());
			payRequest.setMch_id(paymentUtil.getWechatMerchantId());
			payRequest.setNonce_str(RandomStringUtils.randomAlphanumeric(32));
			payRequest.setOut_trade_no(chargeNo);
			payRequest.setBody(WechatConstants.PRODUCT_NAME);
			payRequest.setTotal_fee(merchantChargeDTO.getChargeAmount().multiply(new BigDecimal(100))
					.intValue());
			payRequest.setSpbill_create_ip(request.getRemoteAddr());
			payRequest.setNotify_url(paymentUtil.getWechatNotifyUrl());
			payRequest.setTrade_type("NATIVE");
			payRequest.setAttach(WechatConstants.PRODUCT_NAME);
			payRequest.setProduct_id(WechatConstants.PRODUCT_ID);
			payRequest.setSign(wechatUtil.getSign(payRequest));
			String requestXML = wechatUtil.toXML(payRequest);
			requestXML = new String(requestXML.getBytes("utf-8"),"iso-8859-1");
			logger.info("Wechat unified order request: "+requestXML);
			//下单
			String wechatResponseStr = wechatUtil.postWechatUnifiedOrder(requestXML);
			logger.info("Wechat unified order Response: "+wechatResponseStr);
			WechatPayResponse payResponse = (WechatPayResponse)wechatUtil.fromXML2WechatResponse(WechatPayResponse.class,wechatResponseStr);
			if("SUCCESS".equals(payResponse.getReturn_code()) && "SUCCESS".equals(payResponse.getResult_code())){
				//微信支付二维码链接, 需要转换成二维码图片
				String codeUrl = payResponse.getCode_url();
				WechatPayResponse result = new WechatPayResponse();
				result.setCode_url(codeUrl);
				resultDTO.getBody().setData(result);
				resultDTO.getBody().getStatus().setStatusCode("0");
			}else{
				resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
				resultDTO.getBody().getStatus().setErrorCode("1");
				resultDTO.getBody().getStatus().setErrorDesc("创建支付二维码异常, 请稍后重试");
			}
		} catch (Exception e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode("1");
			resultDTO.getBody().getStatus().setErrorDesc("创建支付二维码异常, 请稍后重试");
			return resultDTO;
		}

		return resultDTO;
	}
	
	/**商家手机端(H5)微信充值(直接调起微信支付)时统一下单
	 * @param requestDTO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/merchant/initWechatPayMobile", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO initWechatPayMobile(@RequestBody RequestDTO<MerchantChargeDTO> requestDTO, HttpServletRequest request) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantChargeDTO merchantChargeDTO = requestDTO.getBody();
		String chargeNo = merchantChargeDTO.getChargeNo();
		String openId = merchantChargeDTO.getOpenId();
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
			//获取商家充值详情
			merchantChargeDTO = fundService.getMerchantChargeByChargeNo(chargeNo);
			
			//初始化微信统一下单接口
			WechatPayRequest payRequest = new WechatPayRequest();
			payRequest.setAppid(paymentUtil.getWechatAppIdOfficialAccount());
			payRequest.setMch_id(paymentUtil.getWechatMerchantId());
			payRequest.setNonce_str(RandomStringUtils.randomAlphanumeric(32));
			payRequest.setOut_trade_no(chargeNo);
			payRequest.setBody(WechatConstants.PRODUCT_NAME);
			payRequest.setTotal_fee(merchantChargeDTO.getChargeAmount().multiply(new BigDecimal(100))
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
	
	/**商家手机端(H5)调起微信支付时获取签名
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/getSignature", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getSignature(@RequestBody RequestDTO<SignatureDTO> requestDTO) {

		SignatureDTO signatureDTO = requestDTO.getBody();
		String url = signatureDTO.getUrl();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			Map map = wxJsApiSdkUtility.getWXJSSignature(url,paymentUtil.getWechatOauth2Redirect(),paymentUtil.getWechatAppSecretOfficialAccount());
			signatureDTO.setAppId(paymentUtil.getWechatOauth2Redirect());
			signatureDTO.setJsapi_ticket((String) map.get("jsapi_ticket"));
			signatureDTO.setSignature((String) map.get("signature"));
			signatureDTO.setNonceStr((String) map.get("nonceStr"));
			signatureDTO.setTimestamp((String) map.get("timestamp"));
			resultDTO.getBody().setData(signatureDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(BusinessErrorCode.COMMON_SYSTEM_ERROR);
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, BusinessErrorCode.COMMON_SYSTEM_ERROR);
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	/*
	 * 商家微信充值接收回调
	 */
	/*@RequestMapping(value = "/wechatPayResponse", method = RequestMethod.POST)
	@ResponseBody
	public String wechatPayResponse(HttpServletRequest request, HttpServletResponse response) {
		int length = request.getContentLength();
		byte[] bytes = new byte[length];
		InputStream inputStream;
		try {
			inputStream = request.getInputStream();
			inputStream.read(bytes);
			String responseStr = new String(bytes, "UTF-8");
			logger.info("Wechat Callback: "+responseStr);
			
			WechatPayResult payResultResponse = wechatUtil.fromXML2WechatResult(responseStr);
			String chargeNo = payResultResponse.getOut_trade_no();
			if("SUCCESS".equals(payResultResponse.getReturn_code())&&"SUCCESS".equals(payResultResponse.getResult_code())){
				logger.info("Charge No: "+ chargeNo);
				MerchantChargeDTO merchantChargeDTO = fundService.getMerchantChargeByChargeNo(chargeNo);
				//可能会多次收到微信的异步通信, 如果是已支付状态就不需要再做一次
				if(!FundConstants.CHARGE_STATUS_COMPLETED.equals(merchantChargeDTO.getChargeStatus())){
					//1. 更新支付状态
					fundService.completeMerchantCharge(merchantChargeDTO);
					//2. 给商户充值积分
					merchantService.chargePoint(merchantChargeDTO);
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
	}*/
	
	/**商家PC端打开支付宝公用支付页面充值
	 * @param requestDTO
	 * @param request
	 * @param httpResponse
	 * @return
	 */
	@RequestMapping(value = "/initAlipay", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO initAlipay(@RequestBody RequestDTO<MerchantChargeDTO> requestDTO, HttpServletRequest request, HttpServletResponse httpResponse) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantChargeDTO merchantChargeDTO = requestDTO.getBody();
		String chargeNo = merchantChargeDTO.getChargeNo();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		DecimalFormat df = new DecimalFormat(",##0.00"); 
		OutputStream os = null;
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			//获取商家充值详情
			merchantChargeDTO = fundService.getMerchantChargeByChargeNo(chargeNo);
			
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConstants.ALIPAY_URL, paymentUtil.getAlipayAppId(), paymentUtil.getAlipayPrivateKey(), AlipayConstants.ALIPAY_FORMAT,AlipayConstants.ALIPAY_CHARSET, paymentUtil.getAlipayPublicKey(), AlipayConstants.ALIPAY_SIGNTYPE);
			//创建API对应的request
			AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
			//在公共参数中设置回跳和通知地址
	        alipayRequest.setReturnUrl(paymentUtil.getAlipayReturnUrl());
	        //异步回调一定是要在外网 不然支付宝服务器无法访问
	        alipayRequest.setNotifyUrl(paymentUtil.getAlipayNotifyUrl());
	      //填充业务参数
	        alipayRequest.setBizContent("{\"out_trade_no\":\""+ chargeNo +"\"," 
	    			+ "\"total_amount\":\""+ df.format(merchantChargeDTO.getChargeAmount()) +"\"," 
	    			+ "\"subject\":\""+ AlipayConstants.ALIPAY_PRODUCT_NAME +"\"," 
	    			+ "\"product_code\":\""+AlipayConstants.ALIPAY_PRODUCT_CODE+"\"}");
	        logger.info("Alipay order request: "+alipayRequest.getBizContent());
	        //调用SDK生成表单
	        String form = alipayClient.pageExecute(alipayRequest).getBody(); 
	        logger.info("Alipay order response: "+form);
	        AlipayFormDTO alipayForm = new AlipayFormDTO(); 
	        alipayForm.setAlipayForm(form);
	        resultDTO.getBody().setData(alipayForm);
	        resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		}catch (Exception e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode("1");
			resultDTO.getBody().getStatus().setErrorDesc("创建支付二维码异常, 请稍后重试");
			return resultDTO;
		}finally{
			if(os != null){
				try{
					os.close();
				}catch(IOException ioe){
					logger.error(ioe.getMessage());
				}
			}
				
		}
		return resultDTO;
	}
	
	/**商家H5端直接调起支付宝支付
	 * @param requestDTO
	 * @param request
	 * @param httpResponse
	 * @return
	 */
	@RequestMapping(value = "/merchant/initAlipayMobile", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO initAlipayMobile(@RequestBody RequestDTO<MerchantChargeDTO> requestDTO, HttpServletRequest request, HttpServletResponse httpResponse) {

		String tokenId = requestDTO.getHeader().getTokenId();
		MerchantChargeDTO merchantChargeDTO = requestDTO.getBody();
		String chargeNo = merchantChargeDTO.getChargeNo();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		DecimalFormat df = new DecimalFormat(",##0.00"); 
		OutputStream os = null;
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			//获取商家充值详情
			merchantChargeDTO = fundService.getMerchantChargeByChargeNo(chargeNo);
			
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConstants.ALIPAY_URL, paymentUtil.getAlipayAppId(), paymentUtil.getAlipayPrivateKey(), AlipayConstants.ALIPAY_FORMAT,AlipayConstants.ALIPAY_CHARSET, paymentUtil.getAlipayPublicKey(), AlipayConstants.ALIPAY_SIGNTYPE);
			//创建API对应的request
			AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
			//在公共参数中设置回跳和通知地址
	        alipayRequest.setReturnUrl(paymentUtil.getAlipayReturnUrl().replace("OUTTRADENO", chargeNo));//手机端页面回调地址
	        //异步回调一定是要在外网 不然支付宝服务器无法访问
	        alipayRequest.setNotifyUrl(paymentUtil.getAlipayNotifyUrl());
	        //填充业务参数
	        alipayRequest.setBizContent("{\"out_trade_no\":\""+ chargeNo +"\"," 
	        		+ "\"total_amount\":\""+ df.format(merchantChargeDTO.getChargeAmount()) +"\"," 
	    			+ "\"subject\":\""+ AlipayConstants.ALIPAY_PRODUCT_NAME +"\"," 
	    			+ "\"product_code\":\""+AlipayConstants.ALIPAY_PRODUCT_CODE_QUICK_WAP_WAY+"\"}");
	        logger.info("Alipay order request: "+alipayRequest.getBizContent());
	        //调用SDK生成表单
	        String form = alipayClient.pageExecute(alipayRequest).getBody(); 
	        logger.info("Alipay order response: "+form);
	        AlipayFormDTO alipayForm = new AlipayFormDTO(); 
	        alipayForm.setAlipayForm(form);
	        resultDTO.getBody().setData(alipayForm);
	        resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		}catch (Exception e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode("1");
			resultDTO.getBody().getStatus().setErrorDesc("创建支付二维码异常, 请稍后重试");
			return resultDTO;
		}finally{
			if(os != null){
				try{
					os.close();
				}catch(IOException ioe){
					logger.error(ioe.getMessage());
				}
			}
				
		}
		return resultDTO;
	}
}
