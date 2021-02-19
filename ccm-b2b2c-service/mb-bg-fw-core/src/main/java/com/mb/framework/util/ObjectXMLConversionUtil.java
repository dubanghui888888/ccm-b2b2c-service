package com.mb.framework.util;

import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;

@Component
public class ObjectXMLConversionUtil
{

	
	/**
	 * 
	 * This method is used to convert object to xml string.
	 * 
	 * @param Object
	 * @return String
	 * 
	 */
	public String convertObjectToXML(final Object obj)
	{
		String resultString = null;
		final XStream xs = new XStream();
		xs.autodetectAnnotations(true);

		resultString = xs.toXML(obj);
		if (null != resultString)
		{
			resultString = resultString.replaceAll("\\r|\\n|\\s", "");

		}

		return resultString;
	}

	/**
	 * 
	 * This method is used to convert xml string to object.
	 * 
	 * @param String
	 * @return Object
	 * 
	 */
	public Object convertXMLToObject(final String xmlString)
	{
	
		final XStream xs = new XStream();
		xs.autodetectAnnotations(true);
		final Object obj = xs.fromXML(xmlString);

		return obj;
	}

}
