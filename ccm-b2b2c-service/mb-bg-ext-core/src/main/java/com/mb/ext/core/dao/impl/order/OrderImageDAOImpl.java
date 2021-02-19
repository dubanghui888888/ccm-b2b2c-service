package com.mb.ext.core.dao.impl.order;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.order.OrderImageDAO;
import com.mb.ext.core.entity.order.OrderImageEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("orderImageDAO")
@Qualifier("orderImageDAO")
public class OrderImageDAOImpl extends AbstractBaseDAO<OrderImageEntity> implements OrderImageDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public OrderImageDAOImpl()
	{
		super();
		this.setEntityClass(OrderImageEntity.class);
	}

	@Override
	public void createOrderImage(OrderImageEntity orderImageEntity)
			throws DAOException {
		save(orderImageEntity);
		
	}

	@Override
	public void updateOrderImage(OrderImageEntity orderImageEntity)
			throws DAOException {
		update(orderImageEntity);
		
	}

	@Override
	public void deleteOrderImage(OrderImageEntity orderImageEntity)
			throws DAOException {
		deletePhysical(orderImageEntity);
		
	}
	
	
	@Override
	public OrderImageEntity getApplicationImageByUuid(String uuid) throws DAOException {
		OrderImageEntity applicationImageEntity = new OrderImageEntity();
		try {
			applicationImageEntity = em.createQuery("select b from OrderImageEntity b where b.orderImageUuid = :UUID and  b.isDeleted=:isDeleted",OrderImageEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return applicationImageEntity;
	}

}
