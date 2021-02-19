package com.mb.framework.application;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringApplicationContext implements ApplicationContextAware
{

	private ApplicationContext applicationContext;
	/**
	 * 
	 */
	public SpringApplicationContext()
	{
	}

	/**
	 * This method is used for 
	 * @param applicationContext
	 * @throws BeansException
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		this.applicationContext = applicationContext;
	}

	/**
	 * @return the context
	 */
	public final ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}

}
