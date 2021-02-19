package com.mb.framework.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class StringUtil
{

	/**
	 * This method is used for checking string start with prefix
	 * 
	 * @param str
	 * @param prefix
	 * @return
	 */
	public static boolean startWith(String str, String prefix)
	{
		return StringUtils.startsWith(str, prefix);
	}

	/**
	 * This method is used for checking whether string is empty
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmtpy(String str)
	{
		return StringUtils.isEmpty(str);
	}

	/**
	 * This method is used for splitting string by separator
	 * @param str
	 * @param separator
	 * @return
	 */
	public static String[] split(String str, String separator)
	{
		return StringUtils.split(str, separator);
	}

	/**
	 * This method is used for converting object to string
	 * @param obj
	 * @return
	 */
	public static String objectToString(Object obj)
	{
		return ReflectionToStringBuilder.toString(obj);
	}
	
	/**
	 * 
	 * This method is used for replace required character with new new character
	 * @param inputStr
	 * @param requiredStr
	 * @param replacementStr
	 * @return String
	 */
	public static String replaceAll(String inputStr, String requiredStr, String replacementStr)
	{
		String outputStr =null;
		if(null != inputStr && null != requiredStr && null != replacementStr)
		{
			outputStr = inputStr.replaceAll(requiredStr, replacementStr);
		}
		return outputStr;
	}

}
