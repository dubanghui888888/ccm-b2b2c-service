package com.mb.framework.util.property;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.mb.framework.util.CollectionUtil;
import com.mb.framework.util.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


/**
 * @author tkyaw
 * to allow read only accesss to all the properties file configured under PropertiesFactoryBean
 * 
 */
public class PropertyRepository 
{
	protected Properties repository;
	
	/**
	 * This method is used for 
	 * @param repository
	 */
	public void setRepository(Properties repository) {
		this.repository = repository;
	}
	
	/**
	 * This method is used for getting properties by key prefix
	 * @param keyPrefix
	 * @return
	 */
	public Map<String, String> getProperties(String keyPrefix) 
	{
		Map<String, String> propertyMap = new HashMap<String, String>();
		
		@SuppressWarnings("unchecked")
		List<String> propertyNames = CollectionUtil.toList(repository.propertyNames());
		
		for(String property: propertyNames)
		{
			if(StringUtil.startWith(property, keyPrefix))
			{
				propertyMap.put(property, repository.getProperty(property));
			}
		}
		return propertyMap;
	}
	
	/**
	 * This method is used for getting property by key
	 * @param key
	 * @return
	 */
	public String getProperty(String key)
	{
		return repository.getProperty(key);
	}
	
	/**
	 * 
	 * This method is used for setting value by key
	 * @param key
	 * @param value
	 */
	public void setProperty(String key,String value){
		repository.setProperty(key, value);
	}
	

}
