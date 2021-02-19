package com.mb.ext.core.dao;

import com.mb.ext.core.entity.UserWechatEntity;
import com.mb.framework.exception.DAOException;



public interface UserWechatDAO
{

	void addUserWechat(UserWechatEntity userWechatEntity) throws DAOException;
	
	void deleteUserWechat(UserWechatEntity userWechatEntity) throws DAOException;
	
	void updateUserWechat(UserWechatEntity userWechatEntity) throws DAOException;
	
	UserWechatEntity getUserWechatByOpenId(String openId) throws DAOException;
}
