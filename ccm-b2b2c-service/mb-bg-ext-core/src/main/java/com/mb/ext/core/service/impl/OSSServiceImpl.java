package com.mb.ext.core.service.impl;

import java.net.URL;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.ServiceSignature;
import com.mb.ext.core.service.OSSService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.oss.OSSDTO;
import com.mb.ext.core.service.spec.oss.OSSPolicy;
import com.mb.ext.core.util.OSSUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.service.AbstractService;

import net.sf.json.JSONObject;

@Service
@Qualifier("OSSService")
public class OSSServiceImpl extends AbstractService implements
		OSSService<BodyDTO> {

	@Autowired
	private OSSUtil ossUtil;
	
	@Override
	public OSSPolicy getPolicy() throws BusinessException {
		OSSPolicy ossPolicy = new OSSPolicy();
		ossPolicy.setExpiration("2100-01-01T12:00:00.000Z");
		Object[] fileSize = new Object[]{"content-length-range",0,1048576000};
		ossPolicy.getConditions().add(fileSize);
		return ossPolicy;
	}
	
	@Override
	public String getEncodedPolicy() throws BusinessException {
		OSSPolicy ossPolicy = getPolicy();
		JSONObject jsonObject = JSONObject.fromObject(ossPolicy);
		String policyStr = jsonObject.toString();
		String encodedPolicy = new Base64().encodeToString(policyStr.getBytes());
		return encodedPolicy;
	}

	@Override
	public String getAccessId() throws BusinessException {
		return ossUtil.getOssAccessId();
	}

	@Override
	public String getAccessKey() throws BusinessException {
		return ossUtil.getOssAccessKey();
	}

	@Override
	public String getSignature() throws BusinessException {
		String encodedPolicy = getEncodedPolicy();
		String signature = ServiceSignature.create().computeSignature(getAccessKey(), encodedPolicy);
		return signature;
	}

	@Override
	public OSSDTO getOSSInfo() throws BusinessException {
		OSSDTO ossDTO = new OSSDTO();
		ossDTO.setOSSAccessKeyId(getAccessId());
		ossDTO.setPolicy(getEncodedPolicy());
		ossDTO.setSignature(getSignature());
		return ossDTO;
	}

	@Override
	public String getUrl(String key) throws BusinessException {
		// 设置URL过期时间为50年  3600l* 1000*24*365*50
	    Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 50);
	    // 生成URL
	    OSSClient ossClient = new OSSClient(ossUtil.getOssEndPoint(), ossUtil.getOssAccessId(), ossUtil.getOssAccessKey());
	    URL url = ossClient.generatePresignedUrl(ossUtil.getOssBucketName(), key, expiration);
	    if (url != null) {
	      return url.toString();
	    }
	    return null;
	}

}
