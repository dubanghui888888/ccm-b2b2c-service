package com.mb.ext.core.dao;

import java.util.Date;
import java.util.List;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.UserSearchDTO;
import com.mb.framework.exception.DAOException;


public interface UserDAO
{
	/**
	 * 
	 * This method is used to add user to enterprise.
	 * 
	 * @param userEntity
	 */
	void addUser(UserEntity userEntity) throws DAOException;
	
	void updateUser(UserEntity userEntity) throws DAOException;
	
	void deleteUser(UserEntity userEntity) throws DAOException;
	
	void deletePhysicalUser(UserEntity userEntity) throws DAOException;
	
	/**
	 * @param id
	 * @return 
	 * @throws DAOException
	 */
	public UserEntity getUserById(String userId) throws DAOException;
	
	public UserEntity getUserByOpenId(String openId) throws DAOException;
	
	public UserEntity getUserByUuid(String userUuid) throws DAOException;
	
	public UserEntity getUserByMobileNo(String countryCode, String mobileNo) throws DAOException;
	
	public UserEntity getUserByIdCardNo(String idCardNo) throws DAOException;
	
	/**
	 * @param id
	 * @return 
	 * @throws DAOException
	 */
	
	List<UserEntity> searchUsers(UserSearchDTO userSearchDTO, int startIndex, int pageSize) throws DAOException;
	
	List<UserEntity> searchMerchantUsers(MerchantEntity merchantEntity, UserSearchDTO userSearchDTO, int startIndex, int pageSize) throws DAOException;
	
	int searchUserTotal(UserSearchDTO userSearchDTO) throws DAOException;
	
	int searchMerchantUserTotal(MerchantEntity merchantEntity, UserSearchDTO userSearchDTO) throws DAOException;
	
	public List<UserEntity> getL1ChildUsers(UserEntity userEntity) throws DAOException;
	
	public List<UserEntity> getL1AndL2ChildUsers(UserEntity userEntity) throws DAOException;
	
	public List<UserEntity> getL1ChildUsersByDate(UserEntity userEntity, Date startDate, Date endDate) throws DAOException;
	
	public int getL1ChildUserCount(UserEntity userEntity) throws DAOException;
	
	public int getL1ChildUserCountByDate(UserEntity userEntity, Date startDate, Date endDate) throws DAOException;
	
	public int getL1ChildUserCountByLevel(UserEntity userEntity, List<String> levelNameList) throws DAOException;
	
	public int getL1ChildUserCountByLevelDate(UserEntity userEntity, List<String> levelNameList, Date startDate, Date endDate) throws DAOException;
	
	public int getInvitedUserCountByLevelDate(UserEntity userEntity, List<String> levelList, Date startDate, Date endDate) throws DAOException;
	
	public List<UserEntity> getInvitedUsersByLevelDate(UserEntity userEntity, List<String> levelList, Date startDate, Date endDate) throws DAOException;
	
	public int getAllChildUserCount(UserEntity userEntity) throws DAOException;
	
	public List<UserEntity> getUserByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	public List<UserEntity> getAllUsersPagination(int startIndex, int pageSize) throws DAOException;
	
	public List<UserEntity> getAllUsers() throws DAOException;
	
	public List<UserEntity> getInactiveUsers() throws DAOException;
	
	public int getUserCountByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	public int getIncrementUserCountByMerchantDate(MerchantEntity merchantEntity, Date startDate, Date endDate) throws DAOException;
	
	public int getIncrementUserCountByMerchantDateLevel(MerchantEntity merchantEntity, Date startDate, Date endDate, String userLevelName) throws DAOException;
	
	public int getUserCount() throws DAOException;
	
	public int getUserCountByLevel(String userLevelName) throws DAOException;
	
	public int getIncrementUserCountByDate(Date startDate, Date endDate) throws DAOException;
	
	public UserEntity getUserByTokenId(String tokenId) throws DAOException;
	
	List<ChartDTO> getIncrementUserChart(Date startDate, Date endDate) throws DAOException;
	
	List<ChartDTO> getIncrementUserChartByMerchant(MerchantEntity merchantEntity, Date startDate, Date endDate) throws DAOException;
	
	void executeInsertUpdateNativeSQL(String sql) throws DAOException;
}
