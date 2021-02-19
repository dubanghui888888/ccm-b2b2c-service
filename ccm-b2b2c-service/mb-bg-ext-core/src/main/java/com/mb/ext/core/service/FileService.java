
package com.mb.ext.core.service;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.FileDTO;
import com.mb.framework.exception.BusinessException;

/**文件接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface FileService<T extends BodyDTO>
{

	/**添加文件
	 * @param fileDTO - 文件
	 * @throws BusinessException
	 */
	void createFile(FileDTO fileDTO) throws BusinessException;
	
	/** 更新文件
	 * @param fileDTO - 文件
	 * @throws BusinessException
	 */
	void updateFile(FileDTO fileDTO) throws BusinessException;
	
	/**删除文件
	 * @param fileDTO - 文件
	 * @throws BusinessException
	 */
	void deleteFile(FileDTO fileDTO) throws BusinessException;
	
	/**根据id查询文件
	 * @param fileUuid - 文件id
	 * @return
	 * @throws BusinessException
	 */
	FileDTO getFileByUuid(String fileUuid) throws BusinessException;
	

	
}
