/**
 * <xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>12345678</CreateTime>
		<MsgType><![CDATA[news]]></MsgType>
		<ArticleCount>2</ArticleCount>
		<Articles>
			<item>
				<Title><![CDATA[title1]]></Title> 
				<Description><![CDATA[description1]]></Description>
				<PicUrl><![CDATA[picurl]]></PicUrl>
				<Url><![CDATA[url]]></Url>
			</item>
				<item>
				<Title><![CDATA[title]]></Title>
				<Description><![CDATA[description]]></Description>
				<PicUrl><![CDATA[picurl]]></PicUrl>
				<Url><![CDATA[url]]></Url>
			</item>
		</Articles>
	</xml>
 */
package com.mb.ext.msg;

/**
 *  ͼ����Ϣ���ݽṹ
 * @author qiaojianhui
 *
 */
public class WXMessageNews extends WXMessageObject {
	private int ArticleCount;
//	private WXMessageNewsArticles Articles;
	private WXMessageNewsArticle[] Articles;

	/**
	 * @return the articleCount
	 */
	public int getArticleCount() {
		return ArticleCount;
	}
	/**
	 * @param articleCount the articleCount to set
	 */
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	/**
	 * @return the articles
	 */
	public WXMessageNewsArticle[]  getArticles() {
		return Articles;
	}
	/**
	 * @param articles the articles to set
	 */
	public void setArticles(WXMessageNewsArticle[]  articles) {
		Articles = articles;
	}	
}
