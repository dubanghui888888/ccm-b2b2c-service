package com.mb.ext.core.service.spec.point;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.util.JsonDateMinuteSerializer;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PointProductCommentDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String productCommentUuid;

	private String commentContent;

	private String replayContent;
	
	private String commentRank;
	
	private String actionType;
	
	private boolean isShow;
	
	private UserDTO userDTO;
	
	public String getProductUuid() {
		return productUuid;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	public void setProductUuid(String productUuid) {
		this.productUuid = productUuid;
	}

	private OrderDTO orderDTO;
	
	private PointProductDTO productDTO;
	
	private String orderUuid;
	
	private String productUuid;
	
	private Date replayTime;
	
	private Date evaluateTime;
	
	public PointProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(PointProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public OrderDTO getOrderDTO() {
		return orderDTO;
	}

	public void setOrderDTO(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
	}

	@JsonSerialize(using=JsonDateMinuteSerializer.class)
	public Date getReplayTime() {
		return replayTime;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public void setReplayTime(Date replayTime) {
		this.replayTime = replayTime;
	}

	@JsonSerialize(using=JsonDateMinuteSerializer.class)
	public Date getEvaluateTime() {
		return evaluateTime;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public void setEvaluateTime(Date evaluateTime) {
		this.evaluateTime = evaluateTime;
	}

	public String getOrderUuid() {
		return orderUuid;
	}

	public void setOrderUuid(String orderUuid) {
		this.orderUuid = orderUuid;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
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

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public List<String> getImageUrlList() {
		return imageUrlList;
	}

	public void setImageUrlList(List<String> imageUrlList) {
		this.imageUrlList = imageUrlList;
	}

	private List<String> imageUrlList;

	
}
