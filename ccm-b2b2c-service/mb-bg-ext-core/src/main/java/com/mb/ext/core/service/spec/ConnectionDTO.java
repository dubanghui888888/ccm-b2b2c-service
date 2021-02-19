package com.mb.ext.core.service.spec;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
public class ConnectionDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3143086495469222238L;

	/**
	 * version
	 */
	private String version;
	
	private String hostName;

	private String port;

	private String accessTime;

	private String message;

	/**
	 * @return the hostName
	 */
	public String getHostName()
	{
		return hostName;
	}

	/**
	 * @param hostName the hostName to set
	 */
	public void setHostName(String hostName)
	{
		this.hostName = hostName;
	}

	/**
	 * @return the port
	 */
	public String getPort()
	{
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(String port)
	{
		this.port = port;
	}

	/**
	 * @return the accessTime
	 */
	public String getAccessTime()
	{
		return accessTime;
	}

	/**
	 * @param accessTime the accessTime to set
	 */
	public void setAccessTime(String accessTime)
	{
		this.accessTime = accessTime;
	}

	/**
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * @return the version
	 */
	public String getVersion()
	{
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version)
	{
		this.version = version;
	}
	

}
