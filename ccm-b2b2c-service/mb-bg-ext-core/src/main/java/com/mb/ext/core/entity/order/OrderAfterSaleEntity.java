package com.mb.ext.core.entity.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the order_after_sale database table.
 * 
 */
@Entity
@Table(name = "order_after_sale")
@NamedQuery(name = "OrderAfterSaleEntity.findAll", query = "SELECT u FROM OrderAfterSaleEntity u")
public class OrderAfterSaleEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ORDER_AFTER_SALE_UUID")
	@GenericGenerator(name = "ORDER_AFTER_SALE_UUID", strategy = "uuid")
	@Column(name = "ORDER_AFTER_SALE_UUID", nullable = false, length = 36)
	private String orderAfterSaleUuid;

	@Column(name = "AFTER_SALE_TYPE")
	private String afterSaleType;
	
	@Column(name = "AFTER_SALE_AMOUNT")
	private BigDecimal afterSaleAmount;
	
	@Column(name = "AFTER_SALE_DESCRIPTION")
	private String afterSaleDescription;
	
	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getRefundMsg() {
		return refundMsg;
	}

	public void setRefundMsg(String refundMsg) {
		this.refundMsg = refundMsg;
	}

	@Column(name = "REFUND_ID")
	private String refundId;
	
	@Column(name = "REFUND_MSG")
	private String refundMsg;
	
	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	@Column(name = "TIME_APPLICATION")
	private Date timeApplication;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;
	
	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public String getCourierNo() {
		return courierNo;
	}

	public void setCourierNo(String courierNo) {
		this.courierNo = courierNo;
	}

	@Column(name = "COURIER_NAME")
	private String courierName;
	
	@Column(name = "COURIER_NO")
	private String courierNo;
	
	public String getOrderAfterSaleUuid() {
		return orderAfterSaleUuid;
	}

	public void setOrderAfterSaleUuid(String orderAfterSaleUuid) {
		this.orderAfterSaleUuid = orderAfterSaleUuid;
	}

	public String getAfterSaleType() {
		return afterSaleType;
	}

	public void setAfterSaleType(String afterSaleType) {
		this.afterSaleType = afterSaleType;
	}

	public BigDecimal getAfterSaleAmount() {
		return afterSaleAmount;
	}

	public void setAfterSaleAmount(BigDecimal afterSaleAmount) {
		this.afterSaleAmount = afterSaleAmount;
	}

	public String getAfterSaleDescription() {
		return afterSaleDescription;
	}

	public void setAfterSaleDescription(String afterSaleDescription) {
		this.afterSaleDescription = afterSaleDescription;
	}

	public Date getTimeApplication() {
		return timeApplication;
	}

	public void setTimeApplication(Date timeApplication) {
		this.timeApplication = timeApplication;
	}

	public Date getTimeOperation() {
		return timeOperation;
	}

	public void setTimeOperation(Date timeOperation) {
		this.timeOperation = timeOperation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<OrderAfterSaleImageEntity> getOrderAfterSaleList() {
		return orderAfterSaleList;
	}

	public void setOrderAfterSaleList(List<OrderAfterSaleImageEntity> orderAfterSaleList) {
		this.orderAfterSaleList = orderAfterSaleList;
	}

	@Column(name = "TIME_OPERATION")
	private Date timeOperation;
	
	@Column(name = "TIME_COURIER")
	private Date timeCouirer;
	
	@Column(name = "TIME_CONFIRM")
	private Date timeConfirm;
	
	public String getSaleNo() {
		return saleNo;
	}

	public Date getTimeCouirer() {
		return timeCouirer;
	}

	public void setTimeCouirer(Date timeCouirer) {
		this.timeCouirer = timeCouirer;
	}

	public Date getTimeConfirm() {
		return timeConfirm;
	}

	public void setTimeConfirm(Date timeConfirm) {
		this.timeConfirm = timeConfirm;
	}

	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}

	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "SALE_NO")
	private String saleNo;
	
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	@Column(name = "REJECT_REASON")
	private String rejectReason;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "orderAfterSaleEntity")
	private List<OrderAfterSaleImageEntity> orderAfterSaleList;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_UUID")
	private OrderEntity orderEntity;

	public OrderEntity getOrderEntity() {
		return orderEntity;
	}

	public void setOrderEntity(OrderEntity orderEntity) {
		this.orderEntity = orderEntity;
	}

}