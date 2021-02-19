package com.mb.ext.core.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mb.ext.core.service.SettingService;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;

@Component
public class OSSUtil {
	
	@Autowired
	@Qualifier("settingService")
	private SettingService settingService;
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());
	
    
    public String getOssAccessId() {
    	try {
    		return settingService.getGlobalOssSetting().getOssAccessId();
    	}catch(BusinessException e) {
    		logger.error("获取支付参数发生异常: "+e.getMessage());
    		return "";
    	}
    }
    
    public String getOssAccessKey() {
    	try {
    		return settingService.getGlobalOssSetting().getOssAccessKey();
    	}catch(BusinessException e) {
    		logger.error("获取支付参数发生异常: "+e.getMessage());
    		return "";
    	}
    }
    
    public String getOssBucketName() {
    	try {
    		return settingService.getGlobalOssSetting().getOssBucketName();
    	}catch(BusinessException e) {
    		logger.error("获取支付参数发生异常: "+e.getMessage());
    		return "";
    	}
    }
    
    public String getOssEndPoint() {
    	try {
    		return settingService.getGlobalOssSetting().getOssEndPoint();
    	}catch(BusinessException e) {
    		logger.error("获取支付参数发生异常: "+e.getMessage());
    		return "";
    	}
    }
}
