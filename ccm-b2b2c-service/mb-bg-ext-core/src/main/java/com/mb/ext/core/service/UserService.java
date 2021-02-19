
package com.mb.ext.core.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.UserGivePointDTO;
import com.mb.ext.core.service.spec.UserLevelDTO;
import com.mb.ext.core.service.spec.UserSearchDTO;
import com.mb.ext.core.service.spec.UserStatementDTO;
import com.mb.ext.core.service.spec.UserStatementSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.framework.exception.BusinessException;

@Transactional
public interface UserService<T extends BodyDTO>
{
	
	/**添加会员等级
	 * @param userLevelDTO
	 * @throws BusinessException
	 */
	void addUserLevel(UserLevelDTO userLevelDTO) throws BusinessException;
	
	/**更新会员等级
	 * @param userLevelDTO
	 * @throws BusinessException
	 */
	void updateUserLevel(UserLevelDTO userLevelDTO) throws BusinessException;
	/**删除会员等级
	 * @param userLevelDTO
	 * @throws BusinessException
	 */
	void deleteUserLevel(UserLevelDTO userLevelDTO) throws BusinessException;
	/**设置默认会员等级
	 * @param userLevelDTO
	 * @throws BusinessException
	 */
	void defaultUserLevel(UserLevelDTO userLevelDTO) throws BusinessException;
	/**取消设置默认会员等级
	 * @param userLevelDTO
	 * @throws BusinessException
	 */
	void cancelDefaultUserLevel(UserLevelDTO userLevelDTO) throws BusinessException;
	
	/**获取默认会员等级
	 * @param uuid
	 * @throws BusinessException
	 */
	UserLevelDTO getDefaultUserLevel() throws BusinessException;
	/**根据ID查询会员等级
	 * @param uuid
	 * @throws BusinessException
	 */
	UserLevelDTO getUserLevelByUuid(String uuid) throws BusinessException;
	
	/**根据名称查询会员等级
	 * @param name
	 * @return
	 * @throws BusinessException
	 */
	UserLevelDTO getUserLevelByName(String name) throws BusinessException;
	
	/**查询所有会员等级
	 * @return
	 * @throws BusinessException
	 */
	List<UserLevelDTO> getUserLevels() throws BusinessException;
	
	/**根据会员等级深度查询
	 * @return
	 * @throws BusinessException
	 */
	UserLevelDTO getUserLevelByDepth(int depth) throws BusinessException;
	
	/**查询上级会员等级
	 * @return
	 * @throws BusinessException
	 */
	UserLevelDTO getParentUserLevel(UserLevelDTO userLevelDTO) throws BusinessException;
	
	/**查询下级会员等级
	 * @return
	 * @throws BusinessException
	 */
	UserLevelDTO getChildUserLevel(UserLevelDTO userLevelDTO) throws BusinessException;
	
	List<UserDTO> getChildUsers(UserDTO userDTO) throws BusinessException;
	
	List<UserDTO> getParentUsers(UserDTO userDTO) throws BusinessException;

	void registerUser (UserDTO userDTO) throws BusinessException;
	
	/**管理员从后台导入会员
	 * @param userDTO
	 * @throws BusinessException
	 */
	void importUser (UserDTO userDTO) throws BusinessException;
	
	public void buildUserTree(UserEntity userEntity) throws BusinessException;
	
	void entity2DTO(UserEntity userEntity, UserDTO userDTO) throws BusinessException;
	
	void updateUser (UserDTO userDTO) throws BusinessException;
	
	void updateUserPoint (UserDTO userDTO) throws BusinessException;
	
	void updateUserBalance (UserDTO userDTO) throws BusinessException;
	
	public void updateUserField(UserDTO userDTO, String fieldName) throws BusinessException;
	
	void enableUser (UserDTO userDTO) throws BusinessException;
	
	void enableUserNotification (UserDTO userDTO) throws BusinessException;
	
	void disableUserNotification (UserDTO userDTO) throws BusinessException;
	
	/**备注会员
	 * @param userDTO
	 * @throws BusinessException
	 */
	void memoUser (UserDTO userDTO) throws BusinessException;
	
	/**停用会员
	 * @param userDTO
	 * @throws BusinessException
	 */
	void disableUser (UserDTO userDTO) throws BusinessException;
	
	/**修改会员上级
	 * @param userDTO
	 * @param supervisorDTO
	 * @throws BusinessException
	 */
	void changeSupervisor(UserDTO userDTO, UserDTO supervisorDTO) throws BusinessException;
	
	/**查询下一级会员
	 * @param userDTO
	 * @return
	 * @throws BusinessException
	 */
	List<UserDTO> getL1ChildUsers(UserDTO userDTO) throws BusinessException;
	/**查询下二级会员
	 * @param userDTO
	 * @return
	 * @throws BusinessException
	 */
	List<UserDTO> getL1AndL2ChildUsers(UserDTO userDTO) throws BusinessException;
	
	/**获取直接邀请会员数量(具有推广权或者销售权)
	 * @param userDTO
	 * @return
	 * @throws BusinessException
	 */
	int getInvitedUserCount(UserDTO userDTO) throws BusinessException;
	
	/**获取直接邀请的会员
	 * @param userDTO
	 * @return
	 * @throws BusinessException
	 */
	List<UserDTO> getInvitedUsers(UserDTO userDTO) throws BusinessException;
	
	/**获团队邀请会员数量(具有推广权或者销售权)
	 * @param userDTO
	 * @return
	 * @throws BusinessException
	 */
	int getTeamUserCount(UserDTO userDTO) throws BusinessException;
	
	/**获取个人销售额
	 * @param userDTO
	 * @return
	 * @throws BusinessException
	 */
	BigDecimal getPersonalSaleAmount(UserDTO userDTO) throws BusinessException;
	
	/**获取团队销售额
	 * @param userDTO
	 * @return
	 * @throws BusinessException
	 */
	BigDecimal getTeamSaleAmount(UserDTO userDTO) throws BusinessException;
	
	/**根据主键id查询会员
	 * @param userUuid - 会员主键id
	 * @return 会员
	 * @throws BusinessException
	 */
	UserDTO getUserByUuid(String userUuid) throws BusinessException;
	
	/**根据编号查询会员
	 * @param id - 会员编号
	 * @return 会员
	 * @throws BusinessException
	 */
	UserDTO getUserById(String id) throws BusinessException;
	
	/**查询商家服务的会员
	 * @param merchantDTO - 商家
	 * @return 会员列表
	 * @throws BusinessException
	 */
	List<UserDTO> getUserByMerchant(MerchantDTO merchantDTO) throws BusinessException;

	/**关注商家
	 * @param merchantDTO - 商家
	 * @param userDTO - 会员
	 * @throws BusinessException
	 */
	void followMerchant(MerchantDTO merchantDTO, UserDTO userDTO) throws BusinessException;

	/**取消关注商家
	 * @param merchantDTO - 商家
	 * @param userDTO - 会员
	 * @throws BusinessException
	 */
	void cancelFollowMerchant(MerchantDTO merchantDTO, UserDTO userDTO) throws BusinessException;

	/**是否关注商家
	 * @param merchantDTO - 商家
	 * @return 关注数量
	 * @throws BusinessException
	 */
	boolean isMerchantFollowed(MerchantDTO merchantDTO, UserDTO userDTO) throws BusinessException;
	
	/**查询未激活会员
	 * @return 会员列表
	 * @throws BusinessException
	 */
	List<UserDTO> getInactiveUsers() throws BusinessException;
	
	/**分页搜索会员
	 * @param searchDTO - 搜索条件
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return
	 * @throws BusinessException
	 */
	List<UserDTO> searchUsers(UserSearchDTO searchDTO, int startIndex, int pageSize) throws BusinessException;
	
	/**查询会员数量
	 * @param searchDTO - 查询条件
	 * @return 会员数量
	 * @throws BusinessException
	 */
	int searchUserTotal(UserSearchDTO searchDTO) throws BusinessException;
	
	/**根据微信open id查询会员
	 * @param openId - 微信open id
	 * @return 会员
	 * @throws BusinessException
	 */
	UserDTO getUserByOpenId(String openId) throws BusinessException;
	
	/**根据手机号码查询会员
	 * @param countryCode - 手机号码国家代码
	 * @param mobileNo - 手机号码
	 * @return - 会员
	 * @throws BusinessException
	 */
	UserDTO getUserByMobileNo(String countryCode, String mobileNo) throws BusinessException;
	
	/**查询会员
	 * @param userDTO - 查询条件
	 * @return 会员
	 * @throws BusinessException
	 */
	UserEntity getUser(UserDTO userDTO) throws BusinessException;
	
	/**查询会员
	 * @param userDTO - 查询条件
	 * @return 会员
	 * @throws BusinessException
	 */
	UserDTO getUserDTO(UserDTO userDTO) throws BusinessException;
	
	/**查询会员详情
	 * @param userDTO -  - 查询条件
	 * @return 会员
	 * @throws BusinessException
	 */
	UserDTO getUserDetail(UserDTO userDTO) throws BusinessException;
	
	/**根据token id查询会员
	 * @param tokenId
	 * @return 会员
	 * @throws BusinessException
	 */
	UserDTO getUserDTOByTokenId(String tokenId) throws BusinessException;
	
	/**设置当前线程会员信息
	 * @param userDTO - 会员
	 * @throws BusinessException
	 */
	void setUserProfile(UserDTO userDTO) throws BusinessException;
	
	/**会员绑定微信
	 * @param userDTO - 会员
	 * @throws BusinessException
	 */
	void bindWechat(UserDTO userDTO) throws BusinessException;
	
	/**会员微信解绑
	 * @param openId - 微信open id
	 * @throws BusinessException
	 */
	void unbindWechat(String openId) throws BusinessException;
	
	/**查询会员open id
	 * @param userDTO - 会员
	 * @return 微信open id
	 * @throws BusinessException
	 */
	String getOpenIdByUser(UserDTO userDTO) throws BusinessException;
	
	/**获取商家服务的会员数量
	 * @param merchantDTO - 商家
	 * @return 会员数量
	 * @throws BusinessException
	 */
	int getUserCountByMerchant(MerchantDTO merchantDTO) throws BusinessException;
	
	/**获取会员总数
	 * @return 会员总数
	 * @throws BusinessException
	 */
	int getUserCount() throws BusinessException;
	
	/**获取某个时间段商家新增会员
	 * @param merchantDTO - 商家
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 会员数量
	 * @throws BusinessException
	 */
	int getIncrementUserCountByMerchantDate(MerchantDTO merchantDTO, Date startDate, Date endDate) throws BusinessException;
	
	/**获取某个时间段商家新增某个等级的会员
	 * @param merchantDTO - 商家
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @param userLevelName - 会员等级
	 * @return 会员数量
	 * @throws BusinessException
	 */
	int getIncrementUserCountByMerchantDateLevel(MerchantDTO merchantDTO, Date startDate, Date endDate, String userLevelName) throws BusinessException;
	
	/**获取某个时间新增会员数量
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 会员数量
	 * @throws BusinessException
	 */
	int getIncrementUserCountByDate(Date startDate, Date endDate) throws BusinessException;
	
	/**获取某个时间段会员新增曲线
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 会员新增曲线数据
	 * @throws BusinessException
	 */
	List<ChartDTO> getIncrementUserCountChartByDate(Date startDate, Date endDate) throws BusinessException;
	
	/**获取某个时间段某个商家的会员新增曲线
	 * @param merchantDTO - 商家
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 会员新增曲线数据
	 * @throws BusinessException
	 */
	List<ChartDTO> getIncrementUserCountChartByMerchantDate(MerchantDTO merchantDTO, Date startDate, Date endDate) throws BusinessException;

	/**根据条件搜索会员资金明细
	 * @param searchDTO - 查询条件
	 * @return 资金明细
	 * @throws BusinessException
	 */
	List<UserStatementDTO> searchStatement(UserStatementSearchDTO searchDTO)throws BusinessException;

	/**会员积分赠送
	 * @param userGivePointDTO - 积分赠送
	 * @throws BusinessException
	 */
	void givePoint(UserGivePointDTO userGivePointDTO)throws BusinessException;

	/**查询会员资金明细记录数量
	 * @param searchDTO - 查询条件
	 * @return 资金明细记录数量
	 * @throws BusinessException
	 */
	int searchUserStatementTotal(UserStatementSearchDTO searchDTO)throws BusinessException;
	
	/**收藏商品
	 * @param userDTO
	 * @param productDTO
	 * @throws BusinessException
	 */
	void collectProduct(UserDTO userDTO, ProductDTO productDTO)throws BusinessException;
	/**取消收藏商品
	 * @param userDTO
	 * @param productDTO
	 * @throws BusinessException
	 */
	void cancelCollectProduct(UserDTO userDTO, ProductDTO productDTO)throws BusinessException;
	/**查询会员收藏的商品
	 * @param userDTO
	 * @throws BusinessException
	 */
	List<ProductDTO> getCollectProductsByUser(UserDTO userDTO)throws BusinessException;
	
	/**查询会员是否收藏该商品
	 * @param userDTO
	 * @param productDTO
	 * @throws BusinessException
	 */
	boolean isProductCollectedByUser(UserDTO userDTO, ProductDTO productDTO)throws BusinessException;

}
