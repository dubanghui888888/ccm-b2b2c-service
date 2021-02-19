package com.mb.framework.util;

import com.mb.framework.util.property.PropertyRepository;


public class ConfigurationManager extends PropertyRepository
{
	/**
	 * This method is used for getting default language
	 * @return
	 */
	public String getDefaultLanguage(){
		return repository.getProperty(ConfigConstants.DEFAULT_LANGUAGE);
	}
	
}
