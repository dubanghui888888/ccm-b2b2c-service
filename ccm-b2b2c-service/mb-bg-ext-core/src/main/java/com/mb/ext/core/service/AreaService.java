
package com.mb.ext.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.AreaDTO;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.framework.exception.BusinessException;

/**省市区区域接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface AreaService<T extends BodyDTO>
{

	/**获取所有省市区数据
	 * @return
	 * @throws BusinessException
	 */
	List<AreaDTO> getAreas() throws BusinessException;
	
	/**搜索城市
	 * @return
	 * @throws BusinessException
	 */
	List<AreaDTO> getCitys() throws BusinessException;
	
}
