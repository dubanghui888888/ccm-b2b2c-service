package com.mb.ext.core.dao.merchant;

import java.math.BigDecimal;
import java.util.List;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.merchant.MerchantAssignEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.MerchantAssignSearchDTO;
import com.mb.framework.exception.DAOException;



public interface MerchantAssignDAO
{
	void createMerchantAssign(MerchantAssignEntity merchantAssignEntity) throws DAOException;
	
	void updateMerchantAssign(MerchantAssignEntity merchantAssignEntity) throws DAOException;
	
	void deleteMerchantAssign(MerchantAssignEntity merchantAssignEntity) throws DAOException;
	
	/*
	 * 分页查询积分划拨明细
	 */
	
	List<MerchantAssignEntity> searchMerchantAssign(MerchantAssignSearchDTO searchDTO) throws DAOException;
	/*
	 * 分页查询积分划拨记录条数
	 */
	int searchMerchantAssignTotal(MerchantAssignSearchDTO searchDTO) throws DAOException;
	/*
	 * 分页查询积分划拨总积分数
	 */
	int searchMerchantAssignPoint(MerchantAssignSearchDTO searchDTO) throws DAOException;
	/*
	 * 分页查询积分划拨总消费金额
	 */
	BigDecimal searchMerchantAssignAmount(MerchantAssignSearchDTO searchDTO) throws DAOException;
	/*
	 * 根据划拨交易号查询
	 */
	MerchantAssignEntity getMerchantAssignByAssignNo(String assignNo) throws DAOException;
	
	/*
	 * 根据数据唯一编号查询
	 */
	MerchantAssignEntity getMerchantAssignByUuid(String uuid) throws DAOException;
	
	/*
	 * 根据商家查询
	 */
	List<MerchantAssignEntity> getMerchantAssignByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	int getAssignPointTotal(MerchantEntity merchantEntity) throws DAOException;
	
}
