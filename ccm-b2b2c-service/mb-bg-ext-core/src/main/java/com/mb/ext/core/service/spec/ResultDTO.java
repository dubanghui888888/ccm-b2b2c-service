
package com.mb.ext.core.service.spec;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResultDTO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7287560658113210249L;

	private HeaderDTO header = new HeaderDTO();
	
	private BodyDTO body = new BodyDTO();

	
	public HeaderDTO getHeader() {
		return header;
	}

	public void setHeader(HeaderDTO header) {
		this.header = header;
	}

	public BodyDTO getBody() {
		return body;
	}

	public void setBody(BodyDTO body) {
		this.body = body;
	}

}
