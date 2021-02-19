
package com.mb.ext.core.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.SupplierSearchDTO;
import com.mb.ext.core.service.spec.supplier.SupplierDTO;
import com.mb.framework.exception.BusinessException;

@Transactional
public interface SupplierService<T extends BodyDTO>
{

	/**新增供应商
	 * @param supplierDTO - 供应商
	 * @return
	 * @throws BusinessException
	 */
	String createSupplier(SupplierDTO supplierDTO) throws BusinessException;
	
	/**删除供应商
	 * @param supplierDTO - 供应商
	 * @return
	 * @throws BusinessException
	 */
	void deleteSupplier(SupplierDTO supplierDTO) throws BusinessException;
	
	/**更新供应商
	 * @param supplierDTO - 供应商
	 * @return
	 * @throws BusinessException
	 */
	void updateSupplier(SupplierDTO supplierDTO) throws BusinessException;
	

	/**关闭供应商
	 * @param supplierDTO - 供应商
	 * @return
	 * @throws BusinessException
	 */
	void closeSupplier(SupplierDTO supplierDTO) throws BusinessException;
	
	/**根据唯一编号查询供应商
	 * @param uuid - 供应商Id
	 * @return
	 * @throws BusinessException
	 */
	SupplierDTO getSupplierByUuid(String uuid) throws BusinessException;
	
	/**根据手机号码查询供应商
	 * @param mobileNo - 供应商手机号码
	 * @return
	 * @throws BusinessException
	 */
	SupplierDTO getSupplierByMobileNo(String mobileNo) throws BusinessException;
	
	/**查询所有供应商, 慎用
	 * @return 供应商列表
	 * @throws BusinessException
	 */
	List<SupplierDTO> getSuppliers() throws BusinessException;
	
	/**分页搜索供应商
	 * @param supplierSearchDTO - 搜索条件
	 * @return 供应商列表
	 * @throws BusinessException
	 */
	List<SupplierDTO> searchSuppliers(SupplierSearchDTO supplierSearchDTO) throws BusinessException;
	
	/**根据条件查询供应商数量
	 * @param supplierSearchDTO - 搜索条件
	 * @return 供应商数量
	 * @throws BusinessException
	 */
	int searchSupplierTotal(SupplierSearchDTO supplierSearchDTO) throws BusinessException;
	
	/**查询供应商数量
	 * @return 供应商数量
	 * @throws BusinessException
	 */
	int getSupplierCount() throws BusinessException;
		
	/**查询某个时间段新增供应商总数
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 供应商总数
	 * @throws BusinessException
	 */
	int getIncrementSupplierCountByDate(Date startDate, Date endDate) throws BusinessException;
	
	/**查询某个时间段新增供应商曲线图数据
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return
	 * @throws BusinessException
	 */
	List<ChartDTO> getIncrementSupplierCountChartByDate(Date startDate, Date endDate) throws BusinessException;
}
