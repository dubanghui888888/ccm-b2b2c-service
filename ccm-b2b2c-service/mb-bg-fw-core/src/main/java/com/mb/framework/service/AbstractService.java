package com.mb.framework.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mb.framework.util.property.PropertyRepository;

public abstract class AbstractService
{
	
	@Autowired
	protected PropertyRepository propertyRepository;
	
}
