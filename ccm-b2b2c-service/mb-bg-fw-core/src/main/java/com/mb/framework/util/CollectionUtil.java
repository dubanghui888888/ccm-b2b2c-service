/**
 * 
 */
package com.mb.framework.util;

import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.EnumerationUtils;

/**
 * @author tkyaw
 * 
 */
public class CollectionUtil
{
	/**
	 * This method is used for converting Enumeration object to List
	 * @param e
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List toList(Enumeration e)
	{
		return EnumerationUtils.toList(e);
	}

	/**
	 * This method is used for checking whether collection is empty 
	 * @param c
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Collection c)
	{
		return CollectionUtils.isEmpty(c);
	}

}
