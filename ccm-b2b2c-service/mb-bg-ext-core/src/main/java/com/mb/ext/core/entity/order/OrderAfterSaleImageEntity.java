package com.mb.ext.core.entity.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the order_after_sale_image database table.
 * 
 */
@Entity
@Table(name = "order_after_sale_image")
@NamedQuery(name = "OrderAfterSaleImageEntity.findAll", query = "SELECT u FROM OrderAfterSaleImageEntity u")
public class OrderAfterSaleImageEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ORDER_AFTER_SALE_IMAGE_UUID")
	@GenericGenerator(name = "ORDER_AFTER_SALE_IMAGE_UUID", strategy = "uuid")
	@Column(name = "ORDER_AFTER_SALE_IMAGE_UUID", nullable = false, length = 36)
	private String orderAfterSaleImageUuid;

	public String getOrderAfterSaleImageUuid() {
		return orderAfterSaleImageUuid;
	}

	public void setOrderAfterSaleImageUuid(String orderAfterSaleImageUuid) {
		this.orderAfterSaleImageUuid = orderAfterSaleImageUuid;
	}

	public OrderAfterSaleEntity getOrderAfterSaleEntity() {
		return orderAfterSaleEntity;
	}

	public void setOrderAfterSaleEntity(OrderAfterSaleEntity orderAfterSaleEntity) {
		this.orderAfterSaleEntity = orderAfterSaleEntity;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_AFTER_SALE_UUID")
	private OrderAfterSaleEntity orderAfterSaleEntity;
	
	@Column(name = "URL")
	private String url;

}