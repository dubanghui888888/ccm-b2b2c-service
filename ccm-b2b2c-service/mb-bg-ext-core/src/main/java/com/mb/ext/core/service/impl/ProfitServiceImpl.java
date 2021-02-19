
package com.mb.ext.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.mb.ext.core.constant.CouponConstants;
import com.mb.ext.core.constant.PointConstants;
import com.mb.ext.core.constant.ProfitConstants;
import com.mb.ext.core.dao.*;
import com.mb.ext.core.dao.coupon.CouponDAO;
import com.mb.ext.core.dao.product.ProductDAO;
import com.mb.ext.core.dao.profit.*;
import com.mb.ext.core.entity.*;
import com.mb.ext.core.entity.coupon.CouponEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.profit.*;
import com.mb.ext.core.service.CouponService;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.coupon.CouponDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.service.spec.profit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.ProfitService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.ProfitDTO;
import com.mb.ext.core.service.spec.UserLevelDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;




@Service
@Qualifier("ProfitService")
public class ProfitServiceImpl extends AbstractService implements ProfitService<BodyDTO>
{
	
	
	@Autowired
	@Qualifier("userLevelDAO")
	private UserLevelDAO userLevelDAO;

	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("userBalanceDAO")
	private UserBalanceDAO userBalanceDAO;

	@Autowired
	@Qualifier("userPointStatementDAO")
	private UserPointStatementDAO userPointStatementDAO;
	
	@Autowired
	@Qualifier("settingDAO")
	private SettingDAO settingDAO;
	
	@Autowired
	@Qualifier("profitRecruitDAO")
	private ProfitRecruitDAO profitRecruitDAO;
	
	@Autowired
	@Qualifier("profitSaleDAO")
	private ProfitSaleDAO profitSaleDAO;
	
	@Autowired
	@Qualifier("profitPerformanceDAO")
	private ProfitPerformanceDAO profitPerformanceDAO;
	
	@Autowired
	@Qualifier("profitTrainerDAO")
	private ProfitTrainerDAO profitTrainerDAO;
	
	@Autowired
	@Qualifier("profitDistributionDAO")
	private ProfitDistributionDAO profitDistributionDAO;

	@Autowired
	@Qualifier("profitWelfareDAO")
	private ProfitWelfareDAO profitWelfareDAO;

	@Autowired
	@Qualifier("couponDAO")
	private CouponDAO couponDAO;

	@Autowired
	@Qualifier("CouponService")
	private CouponService couponService;

	@Autowired
	@Qualifier("productDAO")
	private ProductDAO productDAO;
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	@Override
	public List<ProfitRecruitDTO> inquiryProfitRecruits()
			throws BusinessException {
		List<ProfitRecruitDTO> dtoList = new ArrayList<ProfitRecruitDTO>();
		try{
			List<ProfitRecruitEntity> entityList = profitRecruitDAO.getProfitRecruits();
			for (Iterator<ProfitRecruitEntity> iterator = entityList.iterator(); iterator.hasNext();) {
				ProfitRecruitEntity profitRecruitEntity = iterator
						.next();
				ProfitRecruitDTO profitRecruitDTO = new ProfitRecruitDTO(); 
				recruitEntity2DTO(profitRecruitEntity, profitRecruitDTO);
				dtoList.add(profitRecruitDTO);
			}
			 
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return dtoList;
	}
	
	@Override
	public List<ProfitSaleDTO> inquiryProfitSales()
			throws BusinessException {
		List<ProfitSaleDTO> dtoList = new ArrayList<ProfitSaleDTO>();
		try{
			List<ProfitSaleEntity> entityList = profitSaleDAO.getProfitSales();
			for (Iterator<ProfitSaleEntity> iterator = entityList.iterator(); iterator.hasNext();) {
				ProfitSaleEntity profitSaleEntity = iterator
						.next();
				ProfitSaleDTO profitSaleDTO = new ProfitSaleDTO(); 
				saleEntity2DTO(profitSaleEntity, profitSaleDTO);
				dtoList.add(profitSaleDTO);
			}
			 
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return dtoList;
	}

	@Override
	public ProfitRecruitDTO inquiryProfitRecruitByName(String name)
			throws BusinessException {
		try{
			ProfitRecruitEntity profitRecruitEntity = profitRecruitDAO.getProfitRecruitByName(name);
			ProfitRecruitDTO profitRecruitDTO = new ProfitRecruitDTO(); 
			recruitEntity2DTO(profitRecruitEntity, profitRecruitDTO);
			return profitRecruitDTO;
			 
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public ProfitRecruitDTO inquiryProfitRecruitByUuid(String uuid)
			throws BusinessException {
		try{
			ProfitRecruitEntity profitRecruitEntity = profitRecruitDAO.getProfitRecruitByUuid(uuid);
			ProfitRecruitDTO profitRecruitDTO = new ProfitRecruitDTO(); 
			recruitEntity2DTO(profitRecruitEntity, profitRecruitDTO);
			return profitRecruitDTO;
			 
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public ProfitSaleDTO inquiryProfitSaleByUuid(String uuid)
			throws BusinessException {
		try{
			ProfitSaleEntity profitSaleEntity = profitSaleDAO.getProfitSaleByUuid(uuid);
			ProfitSaleDTO profitSaleDTO = new ProfitSaleDTO(); 
			saleEntity2DTO(profitSaleEntity, profitSaleDTO);
			return profitSaleDTO;
			 
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public ProfitSaleDTO inquiryProfitSaleByUserLevel(String userLevelUuid)
			throws BusinessException {
		try{
			ProfitSaleEntity profitSaleEntity = profitSaleDAO.getProfitSaleByUserLevel(userLevelUuid);
			ProfitSaleDTO profitSaleDTO = new ProfitSaleDTO(); 
			saleEntity2DTO(profitSaleEntity, profitSaleDTO);
			return profitSaleDTO;
			 
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public void addProfitRecruit(ProfitRecruitDTO profitRecruitDTO)
			throws BusinessException {
		try{
			ProfitRecruitEntity profitRecruitEntity = new ProfitRecruitEntity();
			UserLevelDTO profitUserLevel = profitRecruitDTO.getProfitUserLevelDTO();
			UserLevelDTO recruitUserLevel = profitRecruitDTO.getRecruitUserLevelDTO();
			UserLevelEntity profitUserLevelEntity = userLevelDAO.getUserLevelByUuid(profitUserLevel.getUserLevelUuid());
			UserLevelEntity recruitUserLevelEntity = userLevelDAO.getUserLevelByUuid(recruitUserLevel.getUserLevelUuid());
			profitRecruitEntity.setRecruitUserLevelEntity(recruitUserLevelEntity);
			profitRecruitEntity.setProfitUserLevelEntity(profitUserLevelEntity);
			profitRecruitEntity.setProfit(profitRecruitDTO.getProfit());
			profitRecruitDAO.addProfitRecruit(profitRecruitEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public void addProfitSale(ProfitSaleDTO profitSaleDTO)
			throws BusinessException {
		try{
			ProfitSaleEntity profitSaleEntity = new ProfitSaleEntity();
			UserLevelDTO profitUserLevel = profitSaleDTO.getProfitUserLevelDTO();
			UserLevelEntity profitUserLevelEntity = userLevelDAO.getUserLevelByUuid(profitUserLevel.getUserLevelUuid());
			profitSaleEntity.setProfitUserLevelEntity(profitUserLevelEntity);
			profitSaleEntity.setProfitRate(profitSaleDTO.getProfitRate());
			profitSaleDAO.addProfitSale(profitSaleEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public void updateProfitRecruit(ProfitRecruitDTO profitRecruitDTO)
			throws BusinessException {
		try{
			ProfitRecruitEntity profitRecruitEntity = profitRecruitDAO.getProfitRecruitByUuid(profitRecruitDTO.getProfitRecruitUuid());
			if(profitRecruitEntity!=null){
				UserLevelDTO profitUserLevel = profitRecruitDTO.getProfitUserLevelDTO();
				UserLevelDTO recruitUserLevel = profitRecruitDTO.getRecruitUserLevelDTO();
				UserLevelEntity profitUserLevelEntity = userLevelDAO.getUserLevelByUuid(profitUserLevel.getUserLevelUuid());
				UserLevelEntity recruitUserLevelEntity = userLevelDAO.getUserLevelByUuid(recruitUserLevel.getUserLevelUuid());
				profitRecruitEntity.setRecruitUserLevelEntity(recruitUserLevelEntity);
				profitRecruitEntity.setProfitUserLevelEntity(profitUserLevelEntity);
				profitRecruitEntity.setProfit(profitRecruitDTO.getProfit());
				profitRecruitDAO.updateProfitRecruit(profitRecruitEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void updateProfitSale(ProfitSaleDTO profitSaleDTO)
			throws BusinessException {
		try{
			ProfitSaleEntity profitSaleEntity = profitSaleDAO.getProfitSaleByUuid(profitSaleDTO.getProfitSaleUuid());
			if(profitSaleEntity!=null){
				UserLevelDTO profitUserLevel = profitSaleDTO.getProfitUserLevelDTO();
				UserLevelEntity profitUserLevelEntity = userLevelDAO.getUserLevelByUuid(profitUserLevel.getUserLevelUuid());
				profitSaleEntity.setProfitUserLevelEntity(profitUserLevelEntity);
				profitSaleEntity.setProfitRate(profitSaleDTO.getProfitRate());
				profitSaleDAO.updateProfitSale(profitSaleEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	
	@Override
	public void updateProfitRecruitList(List<ProfitRecruitDTO> profitRecruitDTOList)
			throws BusinessException {
		for (Iterator<ProfitRecruitDTO> iterator = profitRecruitDTOList.iterator(); iterator
				.hasNext();) {
			ProfitRecruitDTO profitRecruitDTO = iterator
					.next();
			this.updateProfitRecruit(profitRecruitDTO);
		}
		
	}
	
	@Override
	public void updateProfitSaleList(List<ProfitSaleDTO> profitSaleDTOList)
			throws BusinessException {
		for (Iterator<ProfitSaleDTO> iterator = profitSaleDTOList.iterator(); iterator
				.hasNext();) {
			ProfitSaleDTO profitSaleDTO = iterator
					.next();
			this.updateProfitSale(profitSaleDTO);
		}
		
	}
	
	@Override
	public void deleteProfitRecruit(ProfitRecruitDTO profitRecruitDTO)
			throws BusinessException {
		try{
			ProfitRecruitEntity profitRecruitEntity = profitRecruitDAO.getProfitRecruitByUuid(profitRecruitDTO.getProfitRecruitUuid());
			if(profitRecruitEntity!=null){
				profitRecruitDAO.deleteProfitRecruit(profitRecruitEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void deleteProfitSale(ProfitSaleDTO profitSaleDTO)
			throws BusinessException {
		try{
			ProfitSaleEntity profitSaleEntity = profitSaleDAO.getProfitSaleByUuid(profitSaleDTO.getProfitSaleUuid());
			if(profitSaleEntity!=null){
				profitSaleDAO.deleteProfitSale(profitSaleEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	void recruitEntity2DTO(ProfitRecruitEntity recruitEntity, ProfitRecruitDTO recruitDTO){
		recruitDTO.setProfitRecruitUuid(recruitEntity.getProfitRecruitUuid());
		recruitDTO.setProfit(recruitEntity.getProfit());
		
		UserLevelEntity userLevelEntity = recruitEntity.getRecruitUserLevelEntity();
		UserLevelDTO UserLevelDTO = new UserLevelDTO();
		UserLevelDTO.setUserLevelUuid(userLevelEntity.getUserLevelUuid());
		UserLevelDTO.setName(userLevelEntity.getName());
		recruitDTO.setRecruitUserLevelDTO(UserLevelDTO);
		
		UserLevelEntity profitUserLevelEntity = recruitEntity.getProfitUserLevelEntity();
		UserLevelDTO profitUserLevelDTO = new UserLevelDTO();
		profitUserLevelDTO.setUserLevelUuid(profitUserLevelEntity.getUserLevelUuid());
		profitUserLevelDTO.setName(profitUserLevelEntity.getName());
		recruitDTO.setProfitUserLevelDTO(profitUserLevelDTO);
	}
	
	void saleEntity2DTO(ProfitSaleEntity saleEntity, ProfitSaleDTO saleDTO){
		saleDTO.setProfitSaleUuid(saleEntity.getProfitSaleUuid());
		saleDTO.setProfitRate(saleEntity.getProfitRate());
		
		UserLevelEntity profitUserLevelEntity = saleEntity.getProfitUserLevelEntity();
		UserLevelDTO profitUserLevelDTO = new UserLevelDTO();
		profitUserLevelDTO.setUserLevelUuid(profitUserLevelEntity.getUserLevelUuid());
		profitUserLevelDTO.setName(profitUserLevelEntity.getName());
		saleDTO.setProfitUserLevelDTO(profitUserLevelDTO);
	}
	
	@Override
	public int getMerchantAward(){
		int award = 0;
		try{
			SettingEntity settingEntity = settingDAO.getSettingByName("MERCHANT_AWARD");
			if(settingEntity != null){
				award = Integer.valueOf(settingEntity.getValue());
			}
		}catch(DAOException e){
			logger.error(e.getMessage());
		}
		return award;
	}
	
	@Override
	public int getPartnerAward(){
		int award = 0;
		try{
			SettingEntity settingEntity = settingDAO.getSettingByName("PARTNER_AWARD");
			if(settingEntity != null){
				award = Integer.valueOf(settingEntity.getValue());
			}
		}catch(DAOException e){
			logger.error(e.getMessage());
		}
		return award;
	}
	
	@Override
	public int getMerchantAwardBaoDan(){
		int award = 0;
		try{
			SettingEntity settingEntity = settingDAO.getSettingByName("MERCHANT_AWARD_BAODAN");
			if(settingEntity != null){
				award = Integer.valueOf(settingEntity.getValue());
			}
		}catch(DAOException e){
			logger.error(e.getMessage());
		}
		return award;
	}
	
	@Override
	public int getMerchantDepositAmount(){
		int award = 0;
		try{
			SettingEntity settingEntity = settingDAO.getSettingByName("MERCHANT_DEPOSIT_AMOUNT");
			if(settingEntity != null){
				award = Integer.valueOf(settingEntity.getValue());
			}
		}catch(DAOException e){
			logger.error(e.getMessage());
		}
		return award;
	}
	
	@Override
	public BigDecimal getTaxRate(){
		BigDecimal taxRate = BigDecimal.valueOf(0);
		try{
			SettingEntity settingEntity = settingDAO.getSettingByName("TAX_RATE");
			if(settingEntity != null){
				Double taxRateDouble = Double.valueOf(settingEntity.getValue());
				taxRate = BigDecimal.valueOf(taxRateDouble);
			}
		}catch(DAOException e){
			logger.error(e.getMessage());
		}
		return taxRate;
	}
	
	@Override
	public BigDecimal getMerchantTaxRate(){
		BigDecimal taxRate = BigDecimal.valueOf(0);
		try{
			SettingEntity settingEntity = settingDAO.getSettingByName("TAX_RATE_MERCHANT");
			if(settingEntity != null){
				Double taxRateDouble = Double.valueOf(settingEntity.getValue());
				taxRate = BigDecimal.valueOf(taxRateDouble);
			}
		}catch(DAOException e){
			logger.error(e.getMessage());
		}
		return taxRate;
	}
	
	@Override
	public void updateMerchantAward(ProfitDTO profitDTO) throws BusinessException{
		try{
			SettingEntity settingEntity = settingDAO.getSettingByName("MERCHANT_AWARD");
			if(settingEntity != null){
				settingEntity.setValue(String.valueOf(profitDTO.getMerchantAward()));
				settingDAO.updateSetting(settingEntity);
			}
		}catch(DAOException e){
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
	}
	
	@Override
	public void updatePartnerAward(ProfitDTO profitDTO) throws BusinessException{
		try{
			SettingEntity settingEntity = settingDAO.getSettingByName("PARTNER_AWARD");
			if(settingEntity != null){
				settingEntity.setValue(String.valueOf(profitDTO.getPartnerAward()));
				settingDAO.updateSetting(settingEntity);
			}
		}catch(DAOException e){
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
	}
	
	@Override
	public void updateMerchantAwardBaoDan(ProfitDTO profitDTO) throws BusinessException{
		try{
			SettingEntity settingEntity = settingDAO.getSettingByName("MERCHANT_AWARD_BAODAN");
			if(settingEntity != null){
				settingEntity.setValue(String.valueOf(profitDTO.getMerchantAwardBaoDan()));
				settingDAO.updateSetting(settingEntity);
			}
		}catch(DAOException e){
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
	}
	
	@Override
	public void updateTaxRate(ProfitDTO profitDTO) throws BusinessException{
		try{
			SettingEntity settingEntity = settingDAO.getSettingByName("TAX_RATE");
			if(settingEntity != null){
				settingEntity.setValue(String.valueOf(profitDTO.getTaxRate()));
				settingDAO.updateSetting(settingEntity);
			}
		}catch(DAOException e){
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
	}
	
	@Override
	public List<ProfitPerformanceDTO> inquiryProfitPerformance() throws BusinessException {
		List<ProfitPerformanceDTO> performanceDTOList = new ArrayList<ProfitPerformanceDTO>();
		try{
			List<ProfitPerformanceEntity> performanceEntityList = profitPerformanceDAO.getProfitPerformance();
			
			for (Iterator<ProfitPerformanceEntity> iterator = performanceEntityList.iterator(); iterator.hasNext();) {
				ProfitPerformanceEntity profitPerformanceEntity = (ProfitPerformanceEntity) iterator.next();
				ProfitPerformanceDTO profitPerformanceDTO = new ProfitPerformanceDTO();
				performanceEntity2DTO(profitPerformanceEntity, profitPerformanceDTO);
				performanceDTOList.add(profitPerformanceDTO);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return performanceDTOList;
	}
	
	@Override
	public List<ProfitTrainerDTO> inquiryProfitTrainer() throws BusinessException {
		List<ProfitTrainerDTO> trainerDTOList = new ArrayList<ProfitTrainerDTO>();
		try{
			List<ProfitTrainerEntity> trainerEntityList = profitTrainerDAO.getProfitTrainer();
			
			for (Iterator<ProfitTrainerEntity> iterator = trainerEntityList.iterator(); iterator.hasNext();) {
				ProfitTrainerEntity profitTrainerEntity = (ProfitTrainerEntity) iterator.next();
				ProfitTrainerDTO profitTrainerDTO = new ProfitTrainerDTO();
				trainerEntity2DTO(profitTrainerEntity, profitTrainerDTO);
				trainerDTOList.add(profitTrainerDTO);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return trainerDTOList;
	}
	
	@Override
	public List<ProfitPerformanceDTO> inquiryProfitPerformanceByUserLevel(UserLevelDTO userLevelDTO) throws BusinessException {
		List<ProfitPerformanceDTO> performanceDTOList = new ArrayList<ProfitPerformanceDTO>();
		try{
			UserLevelEntity userLevelEntity = userLevelDAO.getUserLevelByUuid(userLevelDTO.getUserLevelUuid());
			List<ProfitPerformanceEntity> performanceEntityList = profitPerformanceDAO.getProfitPerformanceByUserLevel(userLevelEntity);
			
			for (Iterator<ProfitPerformanceEntity> iterator = performanceEntityList.iterator(); iterator.hasNext();) {
				ProfitPerformanceEntity profitPerformanceEntity = (ProfitPerformanceEntity) iterator.next();
				ProfitPerformanceDTO profitPerformanceDTO = new ProfitPerformanceDTO();
				performanceEntity2DTO(profitPerformanceEntity, profitPerformanceDTO);
				performanceDTOList.add(profitPerformanceDTO);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return performanceDTOList;
	}
	
	@Override
	public List<ProfitTrainerDTO> inquiryProfitTrainerByUserLevel(UserLevelDTO userLevelDTO) throws BusinessException {
		List<ProfitTrainerDTO> trainerDTOList = new ArrayList<ProfitTrainerDTO>();
		try{
			UserLevelEntity userLevelEntity = userLevelDAO.getUserLevelByUuid(userLevelDTO.getUserLevelUuid());
			List<ProfitTrainerEntity> trainerEntityList = profitTrainerDAO.getProfitTrainerByUserLevel(userLevelEntity);
			
			for (Iterator<ProfitTrainerEntity> iterator = trainerEntityList.iterator(); iterator.hasNext();) {
				ProfitTrainerEntity profitTrainerEntity = (ProfitTrainerEntity) iterator.next();
				ProfitTrainerDTO profitTrainerDTO = new ProfitTrainerDTO();
				trainerEntity2DTO(profitTrainerEntity, profitTrainerDTO);
				trainerDTOList.add(profitTrainerDTO);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return trainerDTOList;
	}

	@Override
	public List<ProfitWelfareDTO> inquiryProfitWelfare() throws BusinessException {
		List<ProfitWelfareDTO> fareDTOList = new ArrayList<ProfitWelfareDTO>();
		try{
			List<ProfitWelfareEntity> welfareEntityList = profitWelfareDAO.getProfitWelfares();
			for (Iterator<ProfitWelfareEntity> iterator = welfareEntityList.iterator(); iterator.hasNext();) {
				ProfitWelfareEntity profitWelfareEntity = iterator.next();
				ProfitWelfareDTO profitWelfareDTO = new ProfitWelfareDTO();
				welfareEntity2DTO(profitWelfareEntity, profitWelfareDTO);
				fareDTOList.add(profitWelfareDTO);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return fareDTOList;
	}

	@Override
	public void receiveProfitWelfare(UserDTO userDTO) throws BusinessException {
		try{
			List<ProfitWelfareEntity> welfareEntityList = profitWelfareDAO.getProfitWelfares();
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			UserBalanceEntity balanceEntity = userEntity.getUserBalanceEntity();
			for (Iterator<ProfitWelfareEntity> iterator = welfareEntityList.iterator(); iterator.hasNext();) {
				ProfitWelfareEntity profitWelfareEntity = iterator.next();
				String welfareType = profitWelfareEntity.getWelfareType();
				//领取优惠券
				if(ProfitConstants.WELFARE_TYPE_COUPON.equals(welfareType)) {
					CouponEntity couponEntity = profitWelfareEntity.getCouponEntity();
					int couponCount = profitWelfareEntity.getCouponCount();
					for (int i=0; i<couponCount; i++){
						couponService.receiveCoupon(couponEntity.getCouponUuid(),userDTO, CouponConstants.COUPON_RECEIVE_CHANNEL_SELF);
					}
				}
				//领取积分
				else if(ProfitConstants.WELFARE_TYPE_POINT.equals(welfareType)){
					int pointGiven = profitWelfareEntity.getPointGiven();
					//当前积分
					int pointBefore = balanceEntity.getAvailablePoint();
					//获取后积分
					int pointAfter = pointBefore + pointGiven;
					balanceEntity.setAvailablePoint(pointAfter);
					userBalanceDAO.updateUserBalance(balanceEntity);

					//积分明细
					UserPointStatementEntity userStatementEntity = new UserPointStatementEntity();
					userStatementEntity.setUserEntity(userEntity);
					userStatementEntity.setTransactionTime(new Date());
					userStatementEntity.setTransactionType(PointConstants.TRAN_TYPE_WELFARE);
					userStatementEntity.setTransactionDesc("新人福利赠送" + pointGiven+ "积分");
					userStatementEntity.setTransactionPoint(pointGiven);
					userStatementEntity.setPointBefore(pointBefore);
					userStatementEntity.setPointAfter(pointAfter);
					userPointStatementDAO.createUserPointStatement(userStatementEntity);
				}
				
			}
			//更新会员新人福利领取状态
			userEntity.setProfitWelfareReceived(true);
			userDAO.updateUser(userEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(BusinessException e){
			throw e;
		}
	}
	
	@Override
	public void updateProfitPerformance(UserLevelDTO userLevelDTO, List<ProfitPerformanceDTO> performanceDTOList) throws BusinessException {
		
		try{
			UserLevelEntity userLevelEntity = userLevelDAO.getUserLevelByUuid(userLevelDTO.getUserLevelUuid());
			List<ProfitPerformanceEntity> entityList = profitPerformanceDAO.getProfitPerformanceByUserLevel(userLevelEntity);
			for (Iterator<ProfitPerformanceEntity> iterator = entityList.iterator(); iterator.hasNext();) {
				ProfitPerformanceEntity profitPerformanceEntity = (ProfitPerformanceEntity) iterator.next();
				profitPerformanceDAO.deleteProfitPerformance(profitPerformanceEntity);
			}
			for (Iterator<ProfitPerformanceDTO> iterator = performanceDTOList.iterator(); iterator.hasNext();) {
				ProfitPerformanceDTO profitPerformanceDTO = (ProfitPerformanceDTO) iterator.next();
				ProfitPerformanceEntity profitPerformanceEntity = new ProfitPerformanceEntity();
				profitPerformanceEntity.setAmount(profitPerformanceDTO.getAmount());
				profitPerformanceEntity.setRating(profitPerformanceDTO.getRating());
				profitPerformanceEntity.setProfitUserLevelEntity(userLevelEntity);
				profitPerformanceDAO.addProfitPerformance(profitPerformanceEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public void updateProfitTrainer(UserLevelDTO userLevelDTO, List<ProfitTrainerDTO> trainerDTOList) throws BusinessException {
		
		try{
			UserLevelEntity userLevelEntity = userLevelDAO.getUserLevelByUuid(userLevelDTO.getUserLevelUuid());
			List<ProfitTrainerEntity> entityList = profitTrainerDAO.getProfitTrainerByUserLevel(userLevelEntity);
			for (Iterator<ProfitTrainerEntity> iterator = entityList.iterator(); iterator.hasNext();) {
				ProfitTrainerEntity profitTrainerEntity = (ProfitTrainerEntity) iterator.next();
				profitTrainerDAO.deleteProfitTrainer(profitTrainerEntity);
			}
			for (Iterator<ProfitTrainerDTO> iterator = trainerDTOList.iterator(); iterator.hasNext();) {
				ProfitTrainerDTO profitTrainerDTO = (ProfitTrainerDTO) iterator.next();
				ProfitTrainerEntity profitTrainerEntity = new ProfitTrainerEntity();
				profitTrainerEntity.setAmount(profitTrainerDTO.getAmount());
				profitTrainerEntity.setRating(profitTrainerDTO.getRating());
				profitTrainerEntity.setProfitUserLevelEntity(userLevelEntity);
				profitTrainerDAO.addProfitTrainer(profitTrainerEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public ProfitDistributionDTO inquiryProfitDistribution() throws BusinessException {
		
		try{
			ProfitDistributionDTO profitDistributionDTO = null;
			ProfitDistributionEntity profitDistributionEntity = profitDistributionDAO.getProfitDistribution();
			if(profitDistributionEntity != null) {
				profitDistributionDTO = new ProfitDistributionDTO();
				profitDistributionDTO.setDistributionEnabled(profitDistributionEntity.isDistributionEnabled());
				profitDistributionDTO.setDistributorPurchaseEnabled(profitDistributionEntity.isDistributorPurchaseEnabled());
				profitDistributionDTO.setDistributionLevel(profitDistributionEntity.getDistributionLevel());
				profitDistributionDTO.setLevel1Rate(profitDistributionEntity.getLevel1Rate());
				profitDistributionDTO.setLevel2Rate(profitDistributionEntity.getLevel2Rate());
				profitDistributionDTO.setLevel3Rate(profitDistributionEntity.getLevel3Rate());
				profitDistributionDTO.setApproveRequired(profitDistributionEntity.isApproveRequired());
				profitDistributionDTO.setFormRequired(profitDistributionEntity.isFormRequired());
				profitDistributionDTO.setShareRequired(profitDistributionEntity.isShareRequired());
				profitDistributionDTO.setApplicationContent(profitDistributionEntity.getApplicationContent());
				profitDistributionDTO.setName(profitDistributionEntity.getName());
			}
			return profitDistributionDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public void updateProfitDistribution(ProfitDistributionDTO profitDistributionDTO) throws BusinessException {
		
		try{
			ProfitDistributionEntity profitDistributionEntity = profitDistributionDAO.getProfitDistribution();
			if(profitDistributionEntity == null) {
				profitDistributionEntity = new ProfitDistributionEntity();
				profitDistributionEntity.setDistributionEnabled(false);
				profitDistributionDAO.addProfitDistribution(profitDistributionEntity);
			}else {
				profitDistributionEntity.setDistributionEnabled(profitDistributionDTO.isDistributionEnabled());
				profitDistributionEntity.setDistributorPurchaseEnabled(profitDistributionDTO.isDistributorPurchaseEnabled());
				profitDistributionEntity.setDistributionLevel(profitDistributionDTO.getDistributionLevel());
				profitDistributionEntity.setLevel1Rate(profitDistributionDTO.getLevel1Rate());
				profitDistributionEntity.setLevel2Rate(profitDistributionDTO.getLevel2Rate());
				profitDistributionEntity.setLevel3Rate(profitDistributionDTO.getLevel3Rate());
				profitDistributionEntity.setApproveRequired(profitDistributionDTO.isApproveRequired());
				profitDistributionEntity.setFormRequired(profitDistributionDTO.isFormRequired());
				profitDistributionEntity.setShareRequired(profitDistributionDTO.isShareRequired());
				profitDistributionEntity.setApplicationContent(profitDistributionDTO.getApplicationContent());
				profitDistributionEntity.setName(profitDistributionDTO.getName());
				profitDistributionDAO.updateProfitDistribution(profitDistributionEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public void updateProfitWelfare(List<ProfitWelfareDTO> welfareDTOList) throws BusinessException {

		try{
			//1. 先删除已有的设置
			profitWelfareDAO.deleteAllProfitWelfare();
			//2. 添加新人福利设置
			for (ProfitWelfareDTO welfareDTO:welfareDTOList) {
				ProfitWelfareEntity welfareEntity = new ProfitWelfareEntity();
				String welfareType = welfareDTO.getWelfareType();
				welfareEntity.setWelfareType(welfareType);
				//新人福利-积分赠送
				if(ProfitConstants.WELFARE_TYPE_POINT.equals(welfareType)) {
					welfareEntity.setPointEnabled(welfareDTO.isPointEnabled());
					welfareEntity.setPointGiven(welfareDTO.getPointGiven());
				}
				//新人福利-赠送优惠券
				if(ProfitConstants.WELFARE_TYPE_COUPON.equals(welfareType)) {
					welfareEntity.setCouponCount(welfareDTO.getCouponCount());
					CouponEntity couponEntity = couponDAO.getCouponByUuid(welfareDTO.getCouponDTO().getCouponUuid());
					welfareEntity.setCouponEntity(couponEntity);
				}
				//新人福利-低价商品
				if(ProfitConstants.WELFARE_TYPE_PRODUCT.equals(welfareType)) {
					welfareEntity.setProductUnitPrice(welfareDTO.getProductUnitPrice());
					ProductEntity productEntity = productDAO.getProductByUuid(welfareDTO.getProductDTO().getProductUuid());
					welfareEntity.setProductEntity(productEntity);
				}
				profitWelfareDAO.addProfitWelfare(welfareEntity);
			}

		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	void performanceEntity2DTO(ProfitPerformanceEntity performanceEntity, ProfitPerformanceDTO performanceDTO){
		performanceDTO.setAmount(performanceEntity.getAmount());
		performanceDTO.setRating(performanceEntity.getRating());
		performanceDTO.setProfitPerformanceUuid(performanceEntity.getProfitPerformanceUuid());
		UserLevelEntity userLevelEntity = performanceEntity.getProfitUserLevelEntity();
		if(userLevelEntity != null) {
			UserLevelDTO userLevelDTO = new UserLevelDTO();
			userLevelDTO.setUserLevelUuid(userLevelEntity.getUserLevelUuid());
			userLevelDTO.setName(userLevelEntity.getName());
			performanceDTO.setProfitUserLevel(userLevelDTO);
		}
	}
	
	void trainerEntity2DTO(ProfitTrainerEntity trainerEntity, ProfitTrainerDTO trainerDTO){
		trainerDTO.setAmount(trainerEntity.getAmount());
		trainerDTO.setRating(trainerEntity.getRating());
		trainerDTO.setProfitTrainerUuid(trainerEntity.getProfitTrainerUuid());
		UserLevelEntity userLevelEntity = trainerEntity.getProfitUserLevelEntity();
		if(userLevelEntity != null) {
			UserLevelDTO userLevelDTO = new UserLevelDTO();
			userLevelDTO.setUserLevelUuid(userLevelEntity.getUserLevelUuid());
			userLevelDTO.setName(userLevelEntity.getName());
			trainerDTO.setProfitUserLevel(userLevelDTO);
		}
	}

	void welfareEntity2DTO(ProfitWelfareEntity welfareEntity, ProfitWelfareDTO welfareDTO){
		welfareDTO.setProfitWelfareUuid(welfareEntity.getProfitWelfareUuid());
		welfareDTO.setWelfareType(welfareEntity.getWelfareType());
		welfareDTO.setPointEnabled(welfareEntity.isPointEnabled());
		welfareDTO.setCouponEnabled(welfareEntity.isCouponEnabled());
		welfareDTO.setProductEnabled(welfareEntity.isProductEnabled());
		welfareDTO.setPointGiven(welfareEntity.getPointGiven());
		welfareDTO.setCouponCount(welfareEntity.getCouponCount());
		welfareDTO.setProductUnitPrice(welfareEntity.getProductUnitPrice());
		CouponEntity couponEntity = welfareEntity.getCouponEntity();
		ProductEntity productEntity = welfareEntity.getProductEntity();
		if(couponEntity != null){
			CouponDTO couponDTO = new CouponDTO();
			couponDTO.setCouponUuid(couponEntity.getCouponUuid());
			couponDTO.setName(couponEntity.getName());
			MerchantEntity merchantEntity = couponEntity.getMerchantEntity();
			if(merchantEntity != null){
				MerchantDTO merchantDTO = new MerchantDTO();
				merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
				merchantDTO.setMerchantName(merchantEntity.getMerchantName());
				couponDTO.setMerchantDTO(merchantDTO);
			}
			welfareDTO.setCouponDTO(couponDTO);
		}
		if(productEntity != null){
			ProductDTO productDTO = new ProductDTO();
			productDTO.setProductUuid(productEntity.getProductUuid());
			productDTO.setProductName(productEntity.getProductName());
			MerchantEntity merchantEntity = productEntity.getMerchantEntity();
			if(merchantEntity != null){
				MerchantDTO merchantDTO = new MerchantDTO();
				merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
				merchantDTO.setMerchantName(merchantEntity.getMerchantName());
				productDTO.setMerchantDTO(merchantDTO);
			}
			welfareDTO.setProductDTO(productDTO);
		}
	}
}


