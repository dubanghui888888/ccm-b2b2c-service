
package com.mb.ext.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.SysLogDTO;
import com.mb.ext.core.service.spec.SysLogSearchDTO;
import com.mb.framework.exception.BusinessException;

/**日志接口, 日志相关操作
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface LogService<T extends BodyDTO>
{

	/**记录系统日志
	 * @param logCategory - 日志类型
	 * @param logDetail - 日志详情
	 * @throws BusinessException
	 */
	void addSysLog(String logCategory, String logDetail) throws BusinessException;
	
	/**记录人工日志
	 * @param sysLogDTO - 人工日志数据
	 * @throws BusinessException
	 */
	void addMannualLog(SysLogDTO sysLogDTO) throws BusinessException;
	
	/**删除系统日志
	 * @param sysLogDTO - 人工日志数据
	 * @throws BusinessException
	 */
	void deleteSysLog(SysLogDTO sysLogDTO) throws BusinessException;
	
	/**根据条件搜索系统日志
	 * @param searchDTO - 查询数据
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return - 日志记录
	 * @throws BusinessException
	 */
	List<SysLogDTO> searchSysLog(SysLogSearchDTO searchDTO, int startIndex, int pageSize) throws BusinessException;
	
	/**根据条件搜索系统日志数量
	 * @param searchDTO - 查询数据
	 * @return 日志数量
	 * @throws BusinessException
	 */
	int searchSysLogTotal(SysLogSearchDTO searchDTO)throws BusinessException;

}
