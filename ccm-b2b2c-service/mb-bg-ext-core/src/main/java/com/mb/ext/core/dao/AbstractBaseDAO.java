
package com.mb.ext.core.dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;

import com.mb.ext.core.constant.AuthorizationConstants;
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.framework.entity.AbstractBaseEntity;
import com.mb.framework.exception.DAOException;



public abstract class AbstractBaseDAO<T extends AbstractBaseEntity>
{
	private Class<T> entityClass;
	
	@PersistenceContext
	protected EntityManager em;
	
	/**
	 * This method is used to set entity
	 * @param entityClass
	 * @return
	 */
	public void setEntityClass(final Class<T> entityClass)
	{
		this.entityClass = entityClass;
	}
	
	/**
	 * This method is used to find By Id
	 * @param String
	 * @return entity
	 */
	public T findById(final String id)
	{
		return em.find(entityClass, id);
	}
	
	/**
	 * This method is used to save the entity
	 * @param entity
	 * @return
	 */
	public void save(final T entity) throws DAOException
	
	{
		String appChannel = (String) UserContext.get(AuthorizationConstants.APPCHANNEL);
		if(AuthorizationConstants.APPCHANNEL_ADMIN.equals(appChannel)){
			AdminDTO admdinDTO = (AdminDTO) UserContext.get("ADMINPROFILE");
			if(admdinDTO != null){
				if(StringUtils.isEmpty(entity.getCreateBy()))
					entity.setCreateBy(admdinDTO.getId());
				if(StringUtils.isEmpty(entity.getUpdateBy()))
					entity.setUpdateBy(admdinDTO.getId());
			}
		}
		else if(AuthorizationConstants.APPCHANNEL_MERCHANT.equals(appChannel)){
			MerchantDTO merchantDTO = (MerchantDTO) UserContext.get("MERCHANTPROFILE");
			if(merchantDTO != null){
				if(StringUtils.isEmpty(entity.getCreateBy()))
					entity.setCreateBy(merchantDTO.getMobileNo());
				if(StringUtils.isEmpty(entity.getUpdateBy()))
					entity.setUpdateBy(merchantDTO.getMobileNo());
			}
		}
		else if(AuthorizationConstants.APPCHANNEL_USER.equals(appChannel)){
			UserDTO userDTO = (UserDTO) UserContext.get("USERPROFILE");
			if(userDTO != null){
				if(StringUtils.isEmpty(entity.getCreateBy()))
					entity.setCreateBy(userDTO.getId());
				if(StringUtils.isEmpty(entity.getUpdateBy()))
					entity.setUpdateBy(userDTO.getId());
			}
		}
		em.persist(entity);

	}
	
	/**
	 * This method is used to update the entity
	 * @param entity
	 * @return entity
	 */
	public T update(final T entity)
	{
		String appChannel = (String) UserContext.get(AuthorizationConstants.APPCHANNEL);
		if(AuthorizationConstants.APPCHANNEL_ADMIN.equals(appChannel)){
			AdminDTO admdinDTO = (AdminDTO) UserContext.get("ADMINPROFILE");
			if(admdinDTO != null){
				entity.setUpdateBy(admdinDTO.getId());
			}
		}
		else if(AuthorizationConstants.APPCHANNEL_MERCHANT.equals(appChannel)){
			MerchantDTO merchantDTO = (MerchantDTO) UserContext.get("MERCHANTPROFILE");
			if(merchantDTO != null){
				entity.setUpdateBy(merchantDTO.getMobileNo());
			}
		}
		else if(AuthorizationConstants.APPCHANNEL_USER.equals(appChannel)){
			UserDTO userDTO = (UserDTO) UserContext.get("USERPROFILE");
			if(userDTO != null){
				entity.setUpdateBy(userDTO.getId());
			}
		}
		entity.setUpdateDate(new Date());
		return em.merge(entity);
	}
	
	/**
	 * This method is used to delete By Id ., row is inactive by setting deleted flag in table 
	 * @param String
	 * @return
	 */
	public void delete(final T entity)
	{
		entity.setDeleted(true);
		 em.merge(entity);
	}
	
	/**
	 * This method is used to delete physically from database
	 * @param String
	 * @return
	 */
	public void deletePhysical(final T entity)
	{
		em.remove(entity);
	}
	
	/**
	 * This method is used to delete By Id
	 * @param String
	 * @return
	 */
	public void deleteById(final String id)
	{
		final T entity = findById(id);
		delete(entity);
	}
}
