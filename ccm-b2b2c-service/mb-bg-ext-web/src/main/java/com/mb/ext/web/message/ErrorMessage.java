package com.mb.ext.web.message;

import java.io.Serializable;

public class ErrorMessage implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String message;
	
	public ErrorMessage(String errorCode, String message)
	{
		super();
		this.errorCode = errorCode;
		this.message = message;
	}
	
	public ErrorMessage()
	{
		super();
	}
	public String getMessage()
	{
		return message;
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
	public String getErrorCode()
	{
		return errorCode;
	}
	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

}
