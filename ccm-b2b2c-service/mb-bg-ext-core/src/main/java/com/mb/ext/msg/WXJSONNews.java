/**
 * 
 */
package com.mb.ext.msg;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qiaojianhui
 *
 */
public class WXJSONNews {
	List<WXJSONNewsArticle> articles = new ArrayList<WXJSONNewsArticle>();

	public List<WXJSONNewsArticle> getArticles() {
		return articles;
	}

	public void setArticles(List<WXJSONNewsArticle> articles) {
		this.articles = articles;
	}

}
