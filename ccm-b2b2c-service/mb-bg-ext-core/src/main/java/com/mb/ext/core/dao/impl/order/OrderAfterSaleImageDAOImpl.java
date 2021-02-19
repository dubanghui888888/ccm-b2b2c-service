package com.mb.ext.core.dao.impl.order;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.order.OrderAfterSaleImageDAO;
import com.mb.ext.core.entity.order.OrderAfterSaleImageEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("orderAfterSaleImageDAO")
@Qualifier("orderAfterSaleImageDAO")
public class OrderAfterSaleImageDAOImpl extends AbstractBaseDAO<OrderAfterSaleImageEntity> implements OrderAfterSaleImageDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public OrderAfterSaleImageDAOImpl()
	{
		super();
		this.setEntityClass(OrderAfterSaleImageEntity.class);
	}

	@Override
	public void createOrderAfterSaleImage(OrderAfterSaleImageEntity orderAfterSaleImageEntity)
			throws DAOException {
		save(orderAfterSaleImageEntity);
		
	}

	@Override
	public void updateOrderAfterSaleImage(OrderAfterSaleImageEntity orderAfterSaleImageEntity)
			throws DAOException {
		update(orderAfterSaleImageEntity);
		
	}

	@Override
	public void deleteOrderAfterSaleImage(OrderAfterSaleImageEntity orderAfterSaleImageEntity)
			throws DAOException {
		deletePhysical(orderAfterSaleImageEntity);
		
	}
	
	
	@Override
	public OrderAfterSaleImageEntity getApplicationImageByUuid(String uuid) throws DAOException {
		OrderAfterSaleImageEntity applicationImageEntity = new OrderAfterSaleImageEntity();
		try {
			applicationImageEntity = em.createQuery("select b from OrderAfterSaleImageEntity b where b.orderAfterSaleImageUuid = :UUID and  b.isDeleted=:isDeleted",OrderAfterSaleImageEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return applicationImageEntity;
	}

}
