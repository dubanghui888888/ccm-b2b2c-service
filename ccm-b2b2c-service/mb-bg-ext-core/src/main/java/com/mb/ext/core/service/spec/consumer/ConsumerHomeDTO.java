package com.mb.ext.core.service.spec.consumer;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.AdDTO;
import com.mb.ext.core.service.spec.GroupDTO;
import com.mb.ext.core.service.spec.SwiperDTO;
import com.mb.ext.core.service.spec.content.ArticleDTO;
import com.mb.ext.core.service.spec.group.GroupBuyProductDTO;
import com.mb.ext.core.service.spec.product.ProductCateDTO;
import com.mb.ext.core.service.spec.seckill.SecKillProductDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
/**
 * 用于存储移动端首页数据
 * @author 
 *
 */
@JsonInclude(Include.NON_NULL)
public class ConsumerHomeDTO extends AbstractBaseDTO{
	
	public List<SwiperDTO> getSwipers() {
		return swipers;
	}

	public List<AdDTO> getAds() {
		return ads;
	}

	public void setAds(List<AdDTO> ads) {
		this.ads = ads;
	}

	public List<ProductCateDTO> getCates() {
		return cates;
	}

	public void setCates(List<ProductCateDTO> cates) {
		this.cates = cates;
	}

	public void setSwipers(List<SwiperDTO> swipers) {
		this.swipers = swipers;
	}

	public List<SecKillProductDTO> getSecKills() {
		return secKills;
	}

	public List<GroupBuyProductDTO> getGroupBuys() {
		return groupBuys;
	}

	public void setGroupBuys(List<GroupBuyProductDTO> groupBuys) {
		this.groupBuys = groupBuys;
	}

	public List<GroupDTO> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupDTO> groups) {
		this.groups = groups;
	}

	public void setSecKills(List<SecKillProductDTO> secKills) {
		this.secKills = secKills;
	}

	public List<ArticleDTO> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleDTO> articles) {
		this.articles = articles;
	}

	private List<SwiperDTO> swipers = new ArrayList<SwiperDTO>();
	
	private List<AdDTO> ads = new ArrayList<AdDTO>();
	
	private List<ProductCateDTO> cates = new ArrayList<ProductCateDTO>();
	
	private List<SecKillProductDTO> secKills = new ArrayList<SecKillProductDTO>();
	
	private List<GroupBuyProductDTO> groupBuys = new ArrayList<GroupBuyProductDTO>();
	
	private List<GroupDTO> groups = new ArrayList<GroupDTO>();
	
	private List<ArticleDTO> articles= new ArrayList<ArticleDTO>();
}
