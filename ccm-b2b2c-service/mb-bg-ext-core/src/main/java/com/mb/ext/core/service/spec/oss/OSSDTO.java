package com.mb.ext.core.service.spec.oss;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class OSSDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;

	private String OSSAccessKeyId;
	
	public String getOSSAccessKeyId() {
		return OSSAccessKeyId;
	}

	public void setOSSAccessKeyId(String oSSAccessKeyId) {
		OSSAccessKeyId = oSSAccessKeyId;
	}


	public String getSignature() {
		return Signature;
	}

	public void setSignature(String signature) {
		Signature = signature;
	}

	private String policy;
	
	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	private String Signature;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private String key;
	
	private String url;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
