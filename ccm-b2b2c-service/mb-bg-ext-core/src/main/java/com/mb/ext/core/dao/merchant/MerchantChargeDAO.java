package com.mb.ext.core.dao.merchant;

import java.math.BigDecimal;
import java.util.List;

import com.mb.ext.core.entity.merchant.MerchantChargeEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.MerchantChargeSearchDTO;
import com.mb.framework.exception.DAOException;



public interface MerchantChargeDAO
{
	void createMerchantCharge(MerchantChargeEntity merchantChargeEntity) throws DAOException;
	
	void updateMerchantCharge(MerchantChargeEntity merchantChargeEntity) throws DAOException;
	
	void deleteMerchantCharge(MerchantChargeEntity merchantChargeEntity) throws DAOException;
	
	List<MerchantChargeEntity> getChargeByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	List<MerchantChargeEntity> getChargeByStatus(String status) throws DAOException;
	
	List<MerchantChargeEntity> getCharges() throws DAOException;
	
	MerchantChargeEntity getChargeByUuid(String uuid) throws DAOException;
	
	MerchantChargeEntity getChargeByNo(String no) throws DAOException;
	
	/*
	 * 分页查询积分充值明细
	 */
	
	List<MerchantChargeEntity> searchMerchantCharge(MerchantChargeSearchDTO searchDTO) throws DAOException;
	/*
	 * 分页查询积分充值记录条数
	 */
	int searchMerchantChargeTotal(MerchantChargeSearchDTO searchDTO) throws DAOException;
	/*
	 * 分页查询积分充值总积分数
	 */
	int searchMerchantChargePoint(MerchantChargeSearchDTO searchDTO) throws DAOException;
	
	/*
	 * 分页查询积分充值总金额
	 */
	BigDecimal searchMerchantChargeAmount(MerchantChargeSearchDTO searchDTO) throws DAOException;
	
	BigDecimal getChargeTotalByMerchant(MerchantEntity merchantEntity)throws DAOException;
	
}
