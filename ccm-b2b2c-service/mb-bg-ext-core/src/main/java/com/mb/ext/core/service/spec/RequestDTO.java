package com.mb.ext.core.service.spec;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RequestDTO<T> implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5069755318813643726L;

	private HeaderDTO header;

	private T body;

	/**
	 * @return the header
	 */
	public HeaderDTO getHeader()
	{
		return header;
	}

	/**
	 * @param header
	 *            the header to set
	 */
	public void setHeader(HeaderDTO header)
	{
		this.header = header;
	}

	/**
	 * @return the body
	 */
	public T getBody()
	{
		return body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(T body)
	{
		this.body = body;
	}

}
