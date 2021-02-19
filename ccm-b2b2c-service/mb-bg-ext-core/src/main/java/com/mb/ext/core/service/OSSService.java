
package com.mb.ext.core.service;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.oss.OSSDTO;
import com.mb.ext.core.service.spec.oss.OSSPolicy;
import com.mb.framework.exception.BusinessException;

/**阿里云OSS接口, 以下方法容易理解，未描述方法
 * @author B2B2C商城
 *
 * @param <T>
 */
public interface OSSService<T extends BodyDTO>
{
	OSSPolicy getPolicy() throws BusinessException;
	
	String getAccessId() throws BusinessException;
	
	String getAccessKey() throws BusinessException;
	
	String getSignature() throws BusinessException;
	
	OSSDTO getOSSInfo() throws BusinessException;

	String getEncodedPolicy() throws BusinessException;
	
	String getUrl(String key) throws BusinessException;
}
