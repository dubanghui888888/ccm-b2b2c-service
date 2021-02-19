package com.mb.ext.core.service.spec.oss;

import java.util.ArrayList;
import java.util.List;
public class OSSPolicy{

	private String expiration;
	
	private List conditions = new ArrayList();

	public String getExpiration() {
		return expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}

	public List getConditions() {
		return conditions;
	}

	public void setConditions(List conditions) {
		this.conditions = conditions;
	}
	

}
