package com.mb.ext.core.util;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.mb.ext.core.service.SettingService;
import com.mb.ext.core.service.spec.GlobalSettingDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;

import net.sf.json.JSONObject;

@Component
public class SMSSenderUtil {
	
	@Autowired
	private SettingService settingService;
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	
	public void sendVerificationCode(String verificationCode, final String countryCode,
			final String mobileNo) throws BusinessException {
	
		GlobalSettingDTO settingDTO = settingService.getGlobalSmsSetting();
		
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", settingDTO.getSmsAccessId(), settingDTO.getSmsAccessKey());
	    IAcsClient client = new DefaultAcsClient(profile);
	
	    CommonRequest request = new CommonRequest();
	    request.setMethod(MethodType.POST);
	    request.setDomain("dysmsapi.aliyuncs.com");
	    request.setVersion("2017-05-25");
	    request.setAction("SendSms");
	    request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", mobileNo);
        request.putQueryParameter("SignName", settingDTO.getSmsSignName());
        request.putQueryParameter("TemplateCode", settingDTO.getSmsTemplateCode());
        HashMap<String,String> map=new HashMap<>();
        map.put("code",verificationCode);
        JSONObject jsonObject = JSONObject.fromObject(map);
        request.putQueryParameter("TemplateParam", jsonObject.toString());
	    try {
	        CommonResponse response = client.getCommonResponse(request);
	        logger.info(response.getData());
	    } catch (ServerException e) {
	        e.printStackTrace();
	        logger.error(e.getMessage());
	    } catch (ClientException e) {
	    	logger.error(e.getMessage());
	    }
	}
	
	public void merchantApplicationApproved(String merchantName, final String applicationName,
			final String mobileNo, final String password) throws BusinessException {
	
		GlobalSettingDTO settingDTO = settingService.getGlobalSmsSetting();
		
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", settingDTO.getSmsAccessId(), settingDTO.getSmsAccessKey());
	    IAcsClient client = new DefaultAcsClient(profile);
	
	    CommonRequest request = new CommonRequest();
	    request.setMethod(MethodType.POST);
	    request.setDomain("dysmsapi.aliyuncs.com");
	    request.setVersion("2017-05-25");
	    request.setAction("SendSms");
	    request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", mobileNo);
        request.putQueryParameter("SignName", settingDTO.getSmsSignName());
        request.putQueryParameter("TemplateCode", settingDTO.getSmsTemplateCodeApplicationApproved());
        JSONObject param = new JSONObject();
        param.put("merchantName", merchantName);
        param.put("applicationName", applicationName);
        param.put("mobileNo", mobileNo);
        param.put("password", password);
        request.putQueryParameter("TemplateParam", param.toString());
	    try {
	        CommonResponse response = client.getCommonResponse(request);
	        logger.info(response.getData());
	    } catch (ServerException e) {
	        e.printStackTrace();
	        logger.error(e.getMessage());
	    } catch (ClientException e) {
	    	logger.error(e.getMessage());
	    }
	}
	
	public void merchantApplicationRejected(String merchantName, final String applicationName,final String mobileNo,
			final String memo) throws BusinessException {
	
		GlobalSettingDTO settingDTO = settingService.getGlobalSmsSetting();
		
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", settingDTO.getSmsAccessId(), settingDTO.getSmsAccessKey());
	    IAcsClient client = new DefaultAcsClient(profile);
	
	    CommonRequest request = new CommonRequest();
	    request.setMethod(MethodType.POST);
	    request.setDomain("dysmsapi.aliyuncs.com");
	    request.setVersion("2017-05-25");
	    request.setAction("SendSms");
	    request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", mobileNo);
        request.putQueryParameter("SignName", settingDTO.getSmsSignName());
        request.putQueryParameter("TemplateCode", settingDTO.getSmsTemplateCodeApplicationRejected());
        JSONObject param = new JSONObject();
        param.put("merchantName", merchantName);
        param.put("applicationName", applicationName);
        param.put("memo", memo);
        request.putQueryParameter("TemplateParam", param.toString());
	    try {
	        CommonResponse response = client.getCommonResponse(request);
	        logger.info(response.getData());
	    } catch (ServerException e) {
	        e.printStackTrace();
	        logger.error(e.getMessage());
	    } catch (ClientException e) {
	    	logger.error(e.getMessage());
	    }
	}

	public static void main(String[] args) {
		
		try {
//			new SMSSenderUtil().sendVerificationCode("123456","86","18428396582");
//			new SMSSenderUtil().sendVerificationCode("338988", "", "18428396582");
			JSONObject param = new JSONObject();
	        param.put("merchantName", "商户");
	        param.put("applicationName", "测试");
	        param.put("mobileNo", "18428396582");
	        param.put("password", "876544");
	        System.out.print(param.toString());
		} catch (Exception e) {
			System.out.println("注册序列号失败: "+e.getMessage());
		}


	}
}
