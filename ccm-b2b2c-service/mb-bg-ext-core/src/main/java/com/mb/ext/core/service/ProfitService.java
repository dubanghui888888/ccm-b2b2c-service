
package com.mb.ext.core.service;

import java.math.BigDecimal;
import java.util.List;

import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.profit.*;
import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.ProfitDTO;
import com.mb.ext.core.service.spec.UserLevelDTO;
import com.mb.framework.exception.BusinessException;

@Transactional
public interface ProfitService<T extends BodyDTO>
{

	/**查询推广分润参数设置
	 * @return
	 * @throws BusinessException
	 */
	List<ProfitRecruitDTO> inquiryProfitRecruits() throws BusinessException;
	/**查询推广某个会员等级的分润参数设置
	 * @return
	 * @throws BusinessException
	 */
	ProfitRecruitDTO inquiryProfitRecruitByName(String name) throws BusinessException;
	/**查询推广某个会员等级的分润参数设置
	 * @return
	 * @throws BusinessException
	 */
	ProfitRecruitDTO inquiryProfitRecruitByUuid(String uuid) throws BusinessException;
	
	/**添加分润参数设置
	 * @return
	 * @throws BusinessException
	 */
	void addProfitRecruit(ProfitRecruitDTO profitRecruitDTO) throws BusinessException;
	
	/**更新分润参数设置
	 * @return
	 * @throws BusinessException
	 */
	void updateProfitRecruit(ProfitRecruitDTO profitRecruitDTO) throws BusinessException;
	/**批量更新分润参数设置
	 * @return
	 * @throws BusinessException
	 */
	void updateProfitRecruitList(List<ProfitRecruitDTO> profitRecruitDTOList) throws BusinessException;
	
	/**删除分润参数设置
	 * @return
	 * @throws BusinessException
	 */
	void deleteProfitRecruit(ProfitRecruitDTO profitRecruitDTO) throws BusinessException;
	
	/**查询销售分润参数设置
	 * @return
	 * @throws BusinessException
	 */
	List<ProfitSaleDTO> inquiryProfitSales() throws BusinessException;

	/**查询推单个分润参数
	 * @return
	 * @throws BusinessException
	 */
	ProfitSaleDTO inquiryProfitSaleByUuid(String uuid) throws BusinessException;
	
	/**查询推广某个会员等级的分润参数设置
	 * @return
	 * @throws BusinessException
	 */
	ProfitSaleDTO inquiryProfitSaleByUserLevel(String userLevelUuid) throws BusinessException;
	
	/**添加分润参数设置
	 * @return
	 * @throws BusinessException
	 */
	void addProfitSale(ProfitSaleDTO profitSaleDTO) throws BusinessException;
	
	/**更新分润参数设置
	 * @return
	 * @throws BusinessException
	 */
	void updateProfitSale(ProfitSaleDTO profitSaleDTO) throws BusinessException;
	/**批量更新分润参数设置
	 * @return
	 * @throws BusinessException
	 */
	void updateProfitSaleList(List<ProfitSaleDTO> profitSaleDTOList) throws BusinessException;
	
	/**删除分润参数设置
	 * @return
	 * @throws BusinessException
	 */
	void deleteProfitSale(ProfitSaleDTO profitSaleDTO) throws BusinessException;
	
	/**查询某个会员等级对应的团队销售奖励设置
	 * @return
	 * @throws BusinessException
	 */
	List<ProfitPerformanceDTO> inquiryProfitPerformanceByUserLevel(UserLevelDTO userLevel) throws BusinessException;
	
	/**查询团队销售奖励设置
	 * @return
	 * @throws BusinessException
	 */
	List<ProfitPerformanceDTO> inquiryProfitPerformance() throws BusinessException;

	/**查询新人福利设置
	 * @return
	 * @throws BusinessException
	 */
	List<ProfitWelfareDTO> inquiryProfitWelfare() throws BusinessException;

	/**领取新人福利
	 * @return
	 * @throws BusinessException
	 */
	void receiveProfitWelfare(UserDTO userDTO) throws BusinessException;

	/**更新团队销售奖励设置
	 * @param performanceDTOList
	 * @throws BusinessException
	 */
	void updateProfitPerformance(UserLevelDTO userLevelDTO, List<ProfitPerformanceDTO> performanceDTOList) throws BusinessException;
	
	/**查询某个会员等级对应的培训导师奖励设置
	 * @return
	 * @throws BusinessException
	 */
	List<ProfitTrainerDTO> inquiryProfitTrainerByUserLevel(UserLevelDTO userLevel) throws BusinessException;
	/**查询培训导师奖励设置
	 * @return
	 * @throws BusinessException
	 */
	List<ProfitTrainerDTO> inquiryProfitTrainer() throws BusinessException;
	
	/**更新培训导师奖励设置
	 * @param trainerDTOList
	 * @throws BusinessException
	 */
	void updateProfitTrainer(UserLevelDTO userLevelDTO, List<ProfitTrainerDTO> trainerDTOList) throws BusinessException;
	
	/**查询分销设置
	 * @param 
	 * @throws BusinessException
	 */
	ProfitDistributionDTO inquiryProfitDistribution() throws BusinessException;
	
	/**更新分销设置
	 * @param profitDistributionDTO
	 * @throws BusinessException
	 */
	void updateProfitDistribution(ProfitDistributionDTO profitDistributionDTO) throws BusinessException;

	/**更新新人福利设置
	 * @param profitWelfareDTO
	 * @throws BusinessException
	 */
	void updateProfitWelfare(List<ProfitWelfareDTO> profitWelfareDTO) throws BusinessException;
	
	int getMerchantAward();
	
	int getPartnerAward();
	
	int getMerchantAwardBaoDan();
	
	int getMerchantDepositAmount();
	
	BigDecimal getTaxRate() throws BusinessException;
	
	BigDecimal getMerchantTaxRate() throws BusinessException;
	
	void updateMerchantAward(ProfitDTO profitDTO) throws BusinessException;
	
	void updatePartnerAward(ProfitDTO profitDTO) throws BusinessException;
	
	void updateMerchantAwardBaoDan(ProfitDTO profitDTO) throws BusinessException;
	
	void updateTaxRate(ProfitDTO profitDTO) throws BusinessException;
}
