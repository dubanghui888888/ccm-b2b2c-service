
package com.mb.ext.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.TrainerDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.UserSearchDTO;
import com.mb.framework.exception.BusinessException;

/**培训师接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface TrainerService<T extends BodyDTO>
{

	/**根据条件搜索培训师
	 * @param searchDTO - 搜索条件
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return 培训师列表
	 * @throws BusinessException
	 */
	List<TrainerDTO> searchTrainers(UserSearchDTO searchDTO, int startIndex, int pageSize) throws BusinessException;
	
	/**根据条件查询培训师数量
	 * @param searchDTO - 搜索条件
	 * @return 培训师数量
	 * @throws BusinessException
	 */
	int searchTrainerTotal(UserSearchDTO searchDTO) throws BusinessException;
	
	/** 添加培训师
	 * @param trainerDTO - 培训师
	 * @throws BusinessException
	 */
	void addTrainer(TrainerDTO trainerDTO) throws BusinessException;
	
	/** 删除培训师
	 * @param trainerDTO - 培训师
	 * @throws BusinessException
	 */
	void deleteTrainer(TrainerDTO trainerDTO) throws BusinessException;
	
	/**根据id查询培训师
	 * @param trainerUuid - 培训师id
	 * @return
	 * @throws BusinessException
	 */
	TrainerDTO getTrainerByUuid(String trainerUuid) throws BusinessException;
	
	/**查询会员培训师
	 * @param userDTO - 会员
	 * @return 培训师
	 * @throws BusinessException
	 */
	TrainerDTO getTrainerByUser(UserDTO userDTO) throws BusinessException;
	
	/**查询某个等级的培训师列表
	 * @param trainerLevel - 培训师等级
	 * @return 培训师列表
	 * @throws BusinessException
	 */
	List<TrainerDTO> getTrainerByLevel(String trainerLevel) throws BusinessException;
	
	/**查询某个等级的培训师数量
	 * @param trainerLevel - 培训师等级
	 * @return 培训师数量
	 * @throws BusinessException
	 */
	int getTrainerCountByLevel(String trainerLevel) throws BusinessException;
	
	/**升级会员为培训师
	 * @param trainerDTO - 培训师
	 * @throws BusinessException
	 */
	void upgradeUserToTrainer(TrainerDTO trainerDTO) throws BusinessException;
	
	/**取消会员培训师资格
	 * @param trainerDTO - 培训师
	 * @throws BusinessException
	 */
	void cancelUserFromTrainer(TrainerDTO trainerDTO) throws BusinessException;
	
	/**分配上级培训师
	 * @param trainerDTO - 上级培训师
	 * @throws BusinessException
	 */
	void assignParentTrainer(TrainerDTO trainerDTO) throws BusinessException;
	
	/**为会员分配培训师
	 * @param userDTO - 会员
	 * @param trainerDTO - 培训师
	 * @throws BusinessException
	 */
	void assignUserToTrainer(UserDTO userDTO, TrainerDTO trainerDTO) throws BusinessException;
	
	/**将会员从培训师服务列表中移除
	 * @param userDTO - 会员
	 * @param trainerDTO - 培训师
	 * @throws BusinessException
	 */
	void removeUserFromTrainer(UserDTO userDTO, TrainerDTO trainerDTO) throws BusinessException;
	
	/**分页查询培训师服务的所有会员
	 * @param trainerDTO - 培训师
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return 会员列表
	 * @throws BusinessException
	 */
	List<UserDTO> searchTrainerUsers(TrainerDTO trainerDTO,int startIndex, int pageSize) throws BusinessException;
	
	/**查询培训师服务的所有会员数量
	 * @param trainerDTO - 培训师
	 * @return 会员数量
	 * @throws BusinessException
	 */
	int searchTrainerUsersTotal(TrainerDTO trainerDTO) throws BusinessException;
}
