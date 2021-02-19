
package com.mb.ext.core.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.MerchantAssignSearchDTO;
import com.mb.ext.core.service.spec.MerchantChargeSearchDTO;
import com.mb.ext.core.service.spec.MerchantSearchDTO;
import com.mb.ext.core.service.spec.MerchantStatementSearchDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.UserSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantApplicationDTO;
import com.mb.ext.core.service.spec.merchant.MerchantApplicationSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantAssignDTO;
import com.mb.ext.core.service.spec.merchant.MerchantChargeDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.merchant.MerchantShopperDTO;
import com.mb.ext.core.service.spec.merchant.MerchantStatementDTO;
import com.mb.framework.exception.BusinessException;

/** 商家接口, 处理商家相关业务
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface MerchantService<T extends BodyDTO>
{
	/**商家登录
	 * @param merchantDTO
	 * @return
	 * @throws BusinessException
	 */
	String login (MerchantDTO merchantDTO) throws BusinessException;
	
	/** 登出
	 * @param merchantDTO - 商家
	 * @throws BusinessException
	 */
	void logout (MerchantDTO merchantDTO) throws BusinessException;
	
	/**登出
	 * @param tokenId
	 * @throws BusinessException
	 */
	void logout (String tokenId) throws BusinessException;
	
	/** 设置当前线程的商家信息
	 * @param merchantDTO - 商家
	 * @throws BusinessException
	 */
	void setMerchantProfile(MerchantDTO merchantDTO) throws BusinessException;
	
	/**获取当前线程的商家信息
	 * @return
	 * @throws BusinessException
	 */
	MerchantDTO getMerchantProfile() throws BusinessException;
	
	/**根据token获取商家信息
	 * @param tokenId
	 * @return
	 * @throws BusinessException
	 */
	MerchantDTO getMerchantDTOByTokenId(String tokenId) throws BusinessException;
	
	/**修改商家登录密码
	 * @param merchantDTO - 商家
	 * @throws BusinessException
	 */
	void changePassword(MerchantDTO merchantDTO) throws BusinessException;
	
	/** 修改商家交易密码
	 * @param merchantDTO - 商家
	 * @throws BusinessException
	 */
	void changeTranPassword(MerchantDTO merchantDTO) throws BusinessException;
	
	/**验证商家交易密码
	 * @param merchantDTO - 商家
	 * @throws BusinessException
	 */
	void validateTranPassword(MerchantDTO merchantDTO) throws BusinessException;

	/**添加商家
	 * @param merchantDTO - 商家
	 * @return
	 * @throws BusinessException
	 */
	String createMerchant(MerchantDTO merchantDTO) throws BusinessException;
	
	/**删除商家
	 * @param merchantDTO - 商家
	 * @throws BusinessException
	 */
	void deleteMerchant(MerchantDTO merchantDTO) throws BusinessException;
	
	/**更新商家信息
	 * @param merchantDTO - 商家
	 * @throws BusinessException
	 */
	void updateMerchant(MerchantDTO merchantDTO) throws BusinessException;
	
	/** 更新商家某个特定信息
	 * @param merchantDTO - 商家
	 * @param filedName - 信息类型
	 * @throws BusinessException
	 */
	void updateMerchantField(MerchantDTO merchantDTO, String filedName) throws BusinessException;
	
	/**关闭商家
	 * @param merchantDTO - 商家
	 * @throws BusinessException
	 */
	void closeMerchant(MerchantDTO merchantDTO) throws BusinessException;
	
	/**根据唯一编号查询商家
	 * @param uuid - 商家id
	 * @return
	 * @throws BusinessException
	 */
	MerchantDTO getMerchantByUuid(String uuid) throws BusinessException;
	
	/**根据手机号码查询商家
	 * @param mobileNo - 手机号码
	 * @return
	 * @throws BusinessException
	 */
	MerchantDTO getMerchantByMobileNo(String mobileNo) throws BusinessException;
	
	/**查询所有商家, 慎用
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantDTO> getMerchants() throws BusinessException;
	
	/**分页搜索商家(运营后台)
	 * @param merchantSearchDTO - 搜索条件
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantDTO> searchMerchants(MerchantSearchDTO merchantSearchDTO) throws BusinessException;

	/**分页搜索商家(用户端)
	 * @param merchantSearchDTO - 搜索条件
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantDTO> searchPublicMerchants(MerchantSearchDTO merchantSearchDTO) throws BusinessException;

	/**分页搜索商家优惠券
	 * @param merchantSearchDTO - 搜索条件
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantDTO> searchMerchantCoupons(MerchantSearchDTO merchantSearchDTO) throws BusinessException;
	
	/**分页搜索商家总记录数(运营后台及用户端)
	 * @param merchantSearchDTO - 搜索条件
	 * @return 总记录数
	 * @throws BusinessException
	 */
	int searchMerchantTotal(MerchantSearchDTO merchantSearchDTO) throws BusinessException;
	
	/**分页搜索单个会员消费的商家列表
	 * @param userDTO - 会员
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantDTO> searchMerchantByUser(UserDTO userDTO, int startIndex, int pageSize) throws BusinessException;
	
	/**分页搜索单个会员消费的商家列表总记录数
	 * @param userDTO - 会员
	 * @return 总记录数
	 * @throws BusinessException
	 */
	int searchMerchantByUserTotal(UserDTO userDTO) throws BusinessException;
	
	/** 总商家数量
	 * @return
	 * @throws BusinessException
	 */
	int getMerchantCount() throws BusinessException;
	
	/** 搜索商家会员
	 * @param merchantDTO
	 * @param userSearchDTO
	 * @return
	 * @throws BusinessException
	 */
	List<UserDTO> searchMerchantUser(MerchantDTO merchantDTO, UserSearchDTO userSearchDTO) throws BusinessException;
	
	/**搜索商家会员记录数量
	 * @param merchantDTO
	 * @param userSearchDTO
	 * @return
	 * @throws BusinessException
	 */
	int searchMerchantUserTotal(MerchantDTO merchantDTO, UserSearchDTO userSearchDTO) throws BusinessException;

	/**获取商家关注数
	 * @param merchantDTO - 商家
	 * @return 关注数量
	 * @throws BusinessException
	 */
	int getFollowsTotal(MerchantDTO merchantDTO) throws BusinessException;

	/**获取会员关注的商家
	 * @param userDTO - 会员
	 * @return 商家列表
	 * @throws BusinessException
	 */
	List<MerchantDTO> getFollowedMerchantByUser(UserDTO userDTO) throws BusinessException;

	/**搜索充值记录
	 * @param searchDTO
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantChargeDTO> searchMerchantCharge(MerchantChargeSearchDTO searchDTO) throws BusinessException;
	
	/**搜索充值记录数量
	 * @param searchDTO
	 * @return
	 * @throws BusinessException
	 */
	int searchMerchantChargeTotal(MerchantChargeSearchDTO searchDTO) throws BusinessException;
	
	/**搜索充值记录总积分	
	 * @param searchDTO
	 * @return
	 * @throws BusinessException
	 */
	int searchMerchantChargeTotalPoint(MerchantChargeSearchDTO searchDTO) throws BusinessException;
	
	/**搜索充值记录总金额
	 * @param searchDTO
	 * @return
	 * @throws BusinessException
	 */
	BigDecimal searchMerchantChargeTotalAmount(MerchantChargeSearchDTO searchDTO) throws BusinessException;
	
	/** 搜索积分划拨记录
	 * @param searchDTO
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantAssignDTO> searchMerchantAssign(MerchantAssignSearchDTO searchDTO) throws BusinessException;
	
	/**搜索积分划拨数量
	 * @param searchDTO
	 * @return
	 * @throws BusinessException
	 */
	int searchMerchantAssignTotal(MerchantAssignSearchDTO searchDTO) throws BusinessException;
	
	/**搜索积分划拨总积分	
	 * @param searchDTO
	 * @return
	 * @throws BusinessException
	 */
	int searchMerchantAssignTotalPoint(MerchantAssignSearchDTO searchDTO) throws BusinessException;
	
	/** 搜索积分划拨总消费金额
	 * @param searchDTO
	 * @return
	 * @throws BusinessException
	 */
	BigDecimal searchMerchantAssignTotalAmount(MerchantAssignSearchDTO searchDTO) throws BusinessException;
	
	/**搜索交易记录
	 * @param statementSearchDTO - 搜索条件
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantStatementDTO> searchMerchantStatement(MerchantStatementSearchDTO statementSearchDTO) throws BusinessException;
	
	/**搜索交易记录数量
	 * @param searchDTO - 搜索条件
	 * @return
	 * @throws BusinessException
	 */
	int searchMerchantStatementTotal(MerchantStatementSearchDTO searchDTO) throws BusinessException;
	
	/**搜索交易记录总积分	
	 * @param searchDTO - 搜索条件
	 * @return
	 * @throws BusinessException
	 */
	int searchMerchantStatementTotalPoint(MerchantStatementSearchDTO searchDTO) throws BusinessException;
	
	/** 搜索交易记录总金额
	 * @param searchDTO - 搜索条件
	 * @return
	 * @throws BusinessException
	 */
	BigDecimal searchMerchantStatementTotalAmount(MerchantStatementSearchDTO searchDTO) throws BusinessException;
	
	/**
	 * 查询某个时间段商家总数
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return
	 * @throws BusinessException
	 */
	int getIncrementMerchantCountByDate(Date startDate, Date endDate) throws BusinessException;
	
	/**查询某个时间段新增商家曲线图数据
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return
	 * @throws BusinessException
	 */
	List<ChartDTO> getIncrementMerchantCountChartByDate(Date startDate, Date endDate) throws BusinessException;
	
	/** 商家忘记密码操作
	 * @param merchantDTO - 商家
	 * @throws BusinessException
	 */
	void forgetPassword(MerchantDTO merchantDTO) throws BusinessException;
	
	/**商家端修改商家信息
	 * @param merchantDTO - 商家
	 * @throws BusinessException
	 */
	void mUpdateMerchant(MerchantDTO merchantDTO) throws BusinessException;
	
	/** 生成商家收款码
	 * @param merchantDTO - 商家
	 * @return
	 * @throws BusinessException
	 */
	String generateMerchantPaymentQrCode(MerchantDTO merchantDTO) throws BusinessException;
	
	/** 搜索附近商家
	 * @param merchantDTO - 位置信息
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantDTO> inquiryNearMerchant(MerchantDTO merchantDTO)throws BusinessException;
	
	/**商家入驻申请
	 * @param applicationDTO - 入驻申请
	 * @throws BusinessException
	 */
	void applyMerchantApplication(MerchantApplicationDTO applicationDTO)throws BusinessException;
	
	/**审核通过商家入驻申请
	 * @param applicationDTO - 入驻申请
	 * @throws BusinessException
	 */
	void approveMerchantApplication(MerchantApplicationDTO applicationDTO)throws BusinessException;
	
	/**审核拒绝商家入驻申请
	 * @param applicationDTO - 入驻申请
	 * @throws BusinessException
	 */
	void rejectMerchantApplication(MerchantApplicationDTO applicationDTO)throws BusinessException;
	
	/**根据id查询商家入驻申请
	 * @param uuid - 入驻申请Id
	 * @return 入驻申请
	 * @throws BusinessException
	 */
	MerchantApplicationDTO inquiryApplication(String uuid)throws BusinessException;
	
	/**查询商家入驻申请
	 * @param merchantMobileNo - 商家手机号码
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantApplicationDTO> inquiryApplicationByMerchant(String merchantMobileNo)throws BusinessException;
	
	/**分页查询商家入驻申请
	 * @param searchDTO - 查询条件
	 * @return 入驻申请
	 * @throws BusinessException
	 */
	List<MerchantApplicationDTO> searchApplication(MerchantApplicationSearchDTO searchDTO)throws BusinessException;
	
	/**查询商家入驻申请总记录条数
	 * @param searchDTO - 查询条件
	 * @return
	 * @throws BusinessException
	 */
	int searchApplicationTotal(MerchantApplicationSearchDTO searchDTO)throws BusinessException;
	
	/**添加商家配送员
	 * @param merchantShopperDTO - 商家配送员
	 * @return
	 * @throws BusinessException
	 */
	void createMerchantShopper(MerchantShopperDTO merchantShopperDTO) throws BusinessException;
	
	/**编辑商家配送员
	 * @param merchantShopperDTO - 商家配送员
	 * @return
	 * @throws BusinessException
	 */
	void updateMerchantShopper(MerchantShopperDTO merchantShopperDTO) throws BusinessException;
	
	/**删除商家配送员
	 * @param merchantShopperDTO - 商家配送员
	 * @return
	 * @throws BusinessException
	 */
	void deleteMerchantShopper(MerchantShopperDTO merchantShopperDTO) throws BusinessException;
	
	/**启用商家配送员
	 * @param merchantShopperDTO - 商家配送员
	 * @return
	 * @throws BusinessException
	 */
	void enableMerchantShopper(MerchantShopperDTO merchantShopperDTO) throws BusinessException;
	
	/**停用商家配送员
	 * @param merchantShopperDTO - 商家配送员
	 * @return
	 * @throws BusinessException
	 */
	void disableMerchantShopper(MerchantShopperDTO merchantShopperDTO) throws BusinessException;
	
	/**查询商家配送员
	 * @param merchantDTO - 商家
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantShopperDTO> getShoppersByMerchant(MerchantDTO merchantDTO) throws BusinessException;
	
	/**查询商家配送员
	 * @param uuid - 配送员ID
	 * @return
	 * @throws BusinessException
	 */
	MerchantShopperDTO getShopperByUuid(String uuid) throws BusinessException;
	
	/**搜索商家配送员
	 * @param uuid - 配送员ID
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantShopperDTO> searchMerchantShopper(MerchantSearchDTO merchantSearchDTO) throws BusinessException;
	
	/**搜索商家配送员数量
	 * @param uuid - 配送员ID
	 * @return
	 * @throws BusinessException
	 */
	int searchMerchantShopperTotal(MerchantSearchDTO merchantSearchDTO) throws BusinessException;
}
