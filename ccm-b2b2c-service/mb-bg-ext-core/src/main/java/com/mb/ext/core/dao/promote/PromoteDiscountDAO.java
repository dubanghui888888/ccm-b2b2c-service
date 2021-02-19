package com.mb.ext.core.dao.promote;

import java.util.List;

import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.promote.PromoteDiscountEntity;
import com.mb.framework.exception.DAOException;



public interface PromoteDiscountDAO
{
	void createPromoteDiscount(PromoteDiscountEntity promoteDiscountEntity) throws DAOException;
	
	void updatePromoteDiscount(PromoteDiscountEntity promoteDiscountEntity) throws DAOException;
	
	void deletePromoteDiscount(PromoteDiscountEntity promoteDiscountEntity) throws DAOException;
	
	List<PromoteDiscountEntity> getPromoteDiscountByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	PromoteDiscountEntity getPromoteDiscountByName(MerchantEntity merchantEntity, String name) throws DAOException;
	
	PromoteDiscountEntity getPromoteDiscountByUuid(String promoteDiscountUuid) throws DAOException;
	
}
