package com.mb.ext.core.entity.point;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.order.OrderEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the point_product_comment database table.
 * 
 */
@Entity
@Table(name = "point_product_comment")
@NamedQuery(name = "PointProductCommentEntity.findAll", query = "SELECT u FROM PointProductCommentEntity u")
public class PointProductCommentEntity extends AbstractBaseEntity
{
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PRODUCTCOMMENT_UUID")
	@GenericGenerator(name = "PRODUCTCOMMENT_UUID", strategy = "uuid")
	@Column(name = "PRODUCTCOMMENT_UUID", nullable = false, length = 36)
	private String productCommentUuid;

	@Column(name = "COMMENT_CONTENT", length = 500)
	private String commentContent;
	
	@Column(name = "REPLAY_CONTENT", length = 500)
	private String replayContent;

	@Column(name = "COMMENT_RANK")
	private String commentRank;
	
	@Column(name = "IS_SHOW")
	private boolean isShow;
	
	@Column(name = "REPLAY_TIME")
	private Date replayTime;
	
	@Column(name = "EVALUATE_TIME")
	private Date evaluateTime;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_UUID")
	private OrderEntity orderEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UUID")
	private PointProductEntity productEntity;

	public PointProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(PointProductEntity productEntity) {
		this.productEntity = productEntity;
	}

	public OrderEntity getOrderEntity() {
		return orderEntity;
	}

	public void setOrderEntity(OrderEntity orderEntity) {
		this.orderEntity = orderEntity;
	}

	public Date getReplayTime() {
		return replayTime;
	}

	public void setReplayTime(Date replayTime) {
		this.replayTime = replayTime;
	}

	public Date getEvaluateTime() {
		return evaluateTime;
	}

	public void setEvaluateTime(Date evaluateTime) {
		this.evaluateTime = evaluateTime;
	}

	public String getProductCommentUuid() {
		return productCommentUuid;
	}

	public void setProductCommentUuid(String productCommentUuid) {
		this.productCommentUuid = productCommentUuid;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getReplayContent() {
		return replayContent;
	}

	public void setReplayContent(String replayContent) {
		this.replayContent = replayContent;
	}

	public String getCommentRank() {
		return commentRank;
	}

	public void setCommentRank(String commentRank) {
		this.commentRank = commentRank;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public List<PointProductCommentImageEntity> getImageList() {
		return imageList;
	}

	public void setImageList(List<PointProductCommentImageEntity> imageList) {
		this.imageList = imageList;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productCommentEntity")
	private List<PointProductCommentImageEntity> imageList;
	
}