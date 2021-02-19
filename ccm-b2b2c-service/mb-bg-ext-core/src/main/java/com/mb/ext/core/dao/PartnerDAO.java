package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.PartnerEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.UserSearchDTO;
import com.mb.framework.exception.DAOException;


public interface PartnerDAO
{
	/**
	 * 
	 * This method is used to add partner to enterprise.
	 * 
	 * @param partnerEntity
	 */
	List<PartnerEntity> searchPartners(UserSearchDTO userSearchDTO, int startIndex, int pageSize) throws DAOException;
	
	int searchPartnerTotal(UserSearchDTO userSearchDTO) throws DAOException;
	
	void addPartner(PartnerEntity partnerEntity) throws DAOException;
	
	void updatePartner(PartnerEntity partnerEntity) throws DAOException;
	
	void deletePartner(PartnerEntity partnerEntity) throws DAOException;
	
	void deletePhysicalPartner(PartnerEntity partnerEntity) throws DAOException;
	
	public PartnerEntity getPartnerByUuid(String partnerUuid) throws DAOException;
	
	public PartnerEntity getPartnerByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	public List<PartnerEntity> getPartnerByLevel(String partnerLevel) throws DAOException;
	
	public int getPartnerCount() throws DAOException;
	
	public int getPartnerCountByLevel(String partnerLevel) throws DAOException;
	
}
