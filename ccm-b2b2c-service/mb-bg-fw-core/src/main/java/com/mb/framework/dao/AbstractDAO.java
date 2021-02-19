
package com.mb.framework.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mb.framework.entity.AbstractBaseEntity;



public abstract class AbstractDAO<T extends AbstractBaseEntity>
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
	public void save(final T entity)
	{
		em.persist(entity);
	}
	
	/**
	 * This method is used to update the entity
	 * @param entity
	 * @return entity
	 */
	public T update(final T entity)
	{
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
