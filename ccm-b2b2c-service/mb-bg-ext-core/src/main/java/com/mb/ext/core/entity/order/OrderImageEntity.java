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
 * The persistent class for the order_image database table.
 * 
 */
@Entity
@Table(name = "order_image")
@NamedQuery(name = "OrderImageEntity.findAll", query = "SELECT u FROM OrderImageEntity u")
public class OrderImageEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ORDERIMAGE_UUID")
	@GenericGenerator(name = "ORDERIMAGE_UUID", strategy = "uuid")
	@Column(name = "ORDERIMAGE_UUID", nullable = false, length = 36)
	private String orderImageUuid;


	public String getOrderImageUuid() {
		return orderImageUuid;
	}


	public void setOrderImageUuid(String orderImageUuid) {
		this.orderImageUuid = orderImageUuid;
	}


	public OrderEntity getOrderEntity() {
		return orderEntity;
	}


	public void setOrderEntity(OrderEntity orderEntity) {
		this.orderEntity = orderEntity;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_UUID")
	private OrderEntity orderEntity;
	

	@Column(name = "IMAGEURL")
	private String imageUrl;

}