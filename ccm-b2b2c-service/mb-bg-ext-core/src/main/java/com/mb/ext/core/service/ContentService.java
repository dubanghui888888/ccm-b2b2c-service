
package com.mb.ext.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.AdDTO;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.SwiperDTO;
import com.mb.ext.core.service.spec.content.ArticleDTO;
import com.mb.ext.core.service.spec.content.ArticleDTOList;
import com.mb.ext.core.service.spec.content.MateriaDTO;
import com.mb.ext.core.service.spec.content.PosterDTO;
import com.mb.ext.core.service.spec.content.QaDTO;
import com.mb.ext.core.service.spec.content.QaDTOList;
import com.mb.ext.core.service.spec.content.SceneDTO;
import com.mb.ext.core.service.spec.content.TagDTO;
import com.mb.framework.exception.BusinessException;

/**内容接口类, 处理图文视频音频轮播图广告
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface ContentService<T extends BodyDTO>
{
	
	/** 添加海报标签
	 * @param tagDTO - 标签
	 * @throws BusinessException
	 */
	void addPosterTag(TagDTO tagDTO) throws BusinessException;
	
	/** 添加场景标签
	 * @param tagDTO - 标签
	 * @throws BusinessException
	 */
	void addSceneTag(TagDTO tagDTO) throws BusinessException;
	
	/**添加图文音视频标签
	 * @param tagDTO - 标签
	 * @throws BusinessException
	 */
	void addArticleTag(TagDTO tagDTO) throws BusinessException;
	
	/**添加常见问题标签
	 * @param tagDTO - 标签
	 * @throws BusinessException
	 */
	void addQaTag(TagDTO tagDTO) throws BusinessException;
	
	/**删除海报标签
	 * @param tagDTO - 标签
	 * @throws BusinessException
	 */
	void deletePosterTag(TagDTO tagDTO) throws BusinessException;
	
	/**删除场景标签
	 * @param tagDTO - 标签
	 * @throws BusinessException
	 */
	void deleteSceneTag(TagDTO tagDTO) throws BusinessException;
	
	/**删除图文音视频标签
	 * @param tagDTO - 标签
	 * @throws BusinessException
	 */
	void deleteArticleTag(TagDTO tagDTO) throws BusinessException;
	
	/** 删除常见问题标签
	 * @param tagDTO - 标签
	 * @throws BusinessException
	 */
	void deleteQaTag(TagDTO tagDTO) throws BusinessException;
	
	/** 根据标签id查询标签信息
	 * @param uuid - 标签id
	 * @return
	 * @throws BusinessException
	 */
	TagDTO getTagByUuid(String uuid) throws BusinessException;
	
	
	/**根据标签类型和名称查询标签信息
	 * @param tagType - 标签类型(类型列表在ContentConstants常量类定义)
	 * @param tagName - 标签名称
	 * @return
	 * @throws BusinessException
	 */
	TagDTO getTagByTypeName(String tagType, String tagName) throws BusinessException;
	
	/**根据标签类型查询标签列表
	 * @param type - 标签类型(类型列表在ContentConstants常量类定义)
	 * @return
	 * @throws BusinessException
	 */
	List<TagDTO> getTagsByType(String type) throws BusinessException;
	
	/**添加海报
	 * @param posterDTO - 海报
	 * @throws BusinessException
	 */
	void addPoster(PosterDTO posterDTO) throws BusinessException;
	
	/** 更新海报
	 * @param posterDTO - 海报
	 * @throws BusinessException
	 */
	void updatePoster(PosterDTO posterDTO) throws BusinessException;
	
	/**删除海报
	 * @param posterDTO - 海报
	 * @throws BusinessException
	 */
	void deletePoster(PosterDTO posterDTO) throws BusinessException;
	
	/**发布海报
	 * @param posterDTO - 海报
	 * @throws BusinessException
	 */
	void publishPoster(PosterDTO posterDTO) throws BusinessException;
	
	/**查询某个标签下的所有海报
	 * @param tagName - 标签名称
	 * @return
	 * @throws BusinessException
	 */
	List<PosterDTO> getPostersByTagName(String tagName) throws BusinessException;
	
	/**获取所有海报
	 * @return
	 * @throws BusinessException
	 */
	List<PosterDTO> getPosters() throws BusinessException;

	/**根据海报id查询海报信息
	 * @param uuid - 海报id
	 * @return
	 * @throws BusinessException
	 */
	PosterDTO getPosterByUuid(String uuid) throws BusinessException;
	
	/**添加素材
	 * @param materiaDTO - 素材
	 * @throws BusinessException
	 */
	void addMateria(MateriaDTO materiaDTO) throws BusinessException;
	
	/**删除素材
	 * @param materiaDTO - 素材
	 * @throws BusinessException
	 */
	void deleteMateria(MateriaDTO materiaDTO) throws BusinessException;
	
	/**根据类型获取所有素材
	 * @param type - 素材类型((素材类型列表在ContentConstants常量类定义))
	 * @return
	 * @throws BusinessException
	 */
	List<MateriaDTO> getMateriasByType(String type) throws BusinessException;
	
	/**获取所有素材
	 * @return
	 * @throws BusinessException
	 */
	List<MateriaDTO> getMaterias() throws BusinessException;

	/**根据id获取素材信息
	 * @param uuid - 素材id
	 * @return
	 * @throws BusinessException
	 */
	MateriaDTO getMateriaByUuid(String uuid) throws BusinessException;

	/**添加场景
	 * @param sceneDTO - 场景
	 * @throws BusinessException
	 */
	void addScene(SceneDTO sceneDTO) throws BusinessException;

	/**更新场景
	 * @param sceneDTO - 场景
	 * @throws BusinessException
	 */
	void updateScene(SceneDTO sceneDTO) throws BusinessException;
	
	/**删除场景
	 * @param sceneDTO - 场景
	 * @throws BusinessException
	 */
	void deleteScene(SceneDTO sceneDTO) throws BusinessException;
	
	/**获取标签下的所有场景
	 * @param tagName - 标签名称
	 * @return
	 * @throws BusinessException
	 */
	List<SceneDTO> getScenesByTagName(String tagName) throws BusinessException;
	
	/**根据场景id获取场景信息
	 * @param uuid - 场景id
	 * @return
	 * @throws BusinessException
	 */
	SceneDTO getSceneByUuid(String uuid) throws BusinessException;
	
	/** 获取所有场景信息
	 * @return
	 * @throws BusinessException
	 */
	List<SceneDTO> getScenes() throws BusinessException;
	
	/**添加文案
	 * @param articleDTO - 文案
	 * @throws BusinessException
	 */
	void addArticle(ArticleDTO articleDTO) throws BusinessException;
	
	/**添加常见问题
	 * @param qaDTO - 常见问题
	 * @throws BusinessException
	 */
	void addQa(QaDTO qaDTO) throws BusinessException;
	
	/**发布文案
	 * @param articleDTO - 文案
	 * @throws BusinessException
	 */
	void publishArticle(ArticleDTO articleDTO) throws BusinessException;
	
	/**发布常见问题
	 * @param qaDTO - 常见问题
	 * @throws BusinessException
	 */
	void publishQa(QaDTO qaDTO) throws BusinessException;
	
	/**导入常见问题
	 * @param articleDTO - 常见问题
	 * @throws BusinessException
	 */
	void importArticle(ArticleDTO articleDTO) throws BusinessException;
	
	/**更新文案
	 * @param articleDTO - 文案
	 * @throws BusinessException
	 */
	void updateArticle(ArticleDTO articleDTO) throws BusinessException;
	
	/**更新常见问题
	 * @param articleDTO - 常见问题
	 * @throws BusinessException
	 */
	void updateQa(QaDTO qaDTO) throws BusinessException;
	
	/** 设置热门问题
	 * @param qaDTO - 热门问题
	 * @throws BusinessException
	 */
	void setQaHot(QaDTO qaDTO) throws BusinessException;
	
	/** 取消热门问题
	 * @param qaDTO - 热门问题
	 * @throws BusinessException
	 */
	void setQaNonHot(QaDTO qaDTO) throws BusinessException;
	
	/**删除文案
	 * @param articleDTO - 文案
	 * @throws BusinessException
	 */
	void deleteArticle(ArticleDTO articleDTO) throws BusinessException;
	
	/**删除常见问题
	 * @param qaDTO - 常见问题
	 * @throws BusinessException
	 */
	void deleteQa(QaDTO qaDTO) throws BusinessException;
	
	/**批量删除文案
	 * @param list - 文案列表
	 * @throws BusinessException
	 */
	void deleteMultipleArticles(ArticleDTOList list) throws BusinessException;
	
	/**批量删除常见问题
	 * @param list - 常见问题列表
	 * @throws BusinessException
	 */
	void deleteMultipleQas(QaDTOList list) throws BusinessException;
	
	/**获取某个标签下的所有文案
	 * @param tagName - 标签名称
	 * @return
	 * @throws BusinessException
	 */
	List<ArticleDTO> getArticlesByTagName(String tagName) throws BusinessException;
	
	/**获取某个标签下的所有常见问题
	 * @param tagName - 标签名称
	 * @return
	 * @throws BusinessException
	 */
	List<QaDTO> getQasByTagName(String tagName) throws BusinessException;
	
	/**获取某个类型的所有文案
	 * @param articleType - 文案类型(类型定义在ContentConstants)
	 * @return
	 * @throws BusinessException
	 */
	List<ArticleDTO> getArticlesByType(String articleType) throws BusinessException;
	
	/**获取热门问题列表
	 * @return
	 * @throws BusinessException
	 */
	List<QaDTO> getHotQas() throws BusinessException;
	
	/**分页查询某个标签下的文案
	 * @param tagName - 标签名称
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return
	 * @throws BusinessException
	 */
	List<ArticleDTO> getArticlesByTagNamePage(String tagName, int startIndex, int pageSize) throws BusinessException;
	
	/**分页查询某个标签下的问题
	 * @param tagName - 标签名称
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return
	 * @throws BusinessException
	 */
	List<QaDTO> getQasByTagNamePage(String tagName, int startIndex, int pageSize) throws BusinessException;
	
	/**分页查询某种类型的文案
	 * @param articleType - 文案类型(类型定义在ContentConstants)
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return
	 * @throws BusinessException
	 */
	List<ArticleDTO> getArticlesByTypePage(String articleType, int startIndex, int pageSize) throws BusinessException;
	
	/**分页查询多种类型的文案
	 * @param articleTypeList - 文案类型列表(类型定义在ContentConstants)
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return
	 * @throws BusinessException
	 */
	List<ArticleDTO> getArticlesByTypesPage(List<String> articleTypeList, int startIndex, int pageSize) throws BusinessException;
	
	/**获取某个标签下的所有已发布文案
	 * @param tagName - 标签名称
	 * @return
	 * @throws BusinessException
	 */
	List<ArticleDTO> getPublishedArticlesByTagName(String tagName) throws BusinessException;
	
	/**获取某个标签下的所有已发布常见问题
	 * @param tagName - 标签名称
	 * @return
	 * @throws BusinessException
	 */
	List<QaDTO> getPublishedQaByTagName(String tagName) throws BusinessException;
	
	/**获取某个文案类型下的所有已发布文案
	 * @param articleType - 文案类型(类型定义在ContentConstants)
	 * @return
	 * @throws BusinessException
	 */
	List<ArticleDTO> getPublishedArticlesByType(String articleType) throws BusinessException;
	
	/**根据标签分页查询已发布的文案
	 * @param tagName - 标签名称
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return
	 * @throws BusinessException
	 */
	List<ArticleDTO> getPublishedArticlesByTagNamePage(String tagName, int startIndex, int pageSize) throws BusinessException;
	
	/**根据标签分页查询已发布的常见问题
	 * @param tagName - 标签名称
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return
	 * @throws BusinessException
	 */
	List<QaDTO> getPublishedQasByTagNamePage(String tagName, int startIndex, int pageSize) throws BusinessException;
	
	/**分页查询某个文案类型下已发布的文案
	 * @param articleType - 文案类型(类型定义在ContentConstants)
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return
	 * @throws BusinessException
	 */
	List<ArticleDTO> getPublishedArticlesByTypePage(String articleType, int startIndex, int pageSize) throws BusinessException;
	
	/**获取某个标签下的文案数量
	 * @param tagName - 标签名称
	 * @return
	 * @throws BusinessException
	 */
	int getArticleCountByTagName(String tagName) throws BusinessException;
	
	/**
	 * 获取某个标签下的常见问题数量
	 * @param tagName - 标签名称
	 * @return
	 * @throws BusinessException
	 */
	int getQaCountByTagName(String tagName) throws BusinessException;
	
	/**获取某个文案类型下的文案数量
	 * @param articleType - 文案类型(类型定义在ContentConstants)
	 * @return
	 * @throws BusinessException
	 */
	int getArticleCountByType(String articleType) throws BusinessException;
	
	/** 获取多个文案类型下的文案数量
	 * @param articleTypeList - 文案类型列表(类型定义在ContentConstants)
	 * @return
	 * @throws BusinessException
	 */
	int getArticleCountByTypes(List<String> articleTypeList) throws BusinessException;
	
	/**获取某个标签下的所有已发布文案数量
	 * @param tagName - 标签名称
	 * @return
	 * @throws BusinessException
	 */
	int getPublishedArticleCountByTagName(String tagName) throws BusinessException;
	
	/**获取某个标签下的所有已发布常见问题数量
	 * @param tagName - 标签名称
	 * @return
	 * @throws BusinessException
	 */
	int getPublishedQaCountByTagName(String tagName) throws BusinessException;
	
	/**获取某个文案类型下的所有已发布文案数量
	 * @param articleType - 文案类型(类型定义在ContentConstants)
	 * @return
	 * @throws BusinessException
	 */
	int getPublishedArticleCountByType(String articleType) throws BusinessException;
	
	/**根据id查询文案信息
	 * @param uuid - 文案id
	 * @return
	 * @throws BusinessException
	 */
	ArticleDTO getArticleByUuid(String uuid) throws BusinessException;
	
	/**根据id查询常见问题信息
	 * @param uuid - 常见问题id
	 * @return
	 * @throws BusinessException
	 */
	QaDTO getQaByUuid(String uuid) throws BusinessException;
	
	/**查询所有文案
	 * @return
	 * @throws BusinessException
	 */
	List<ArticleDTO> getArticles() throws BusinessException;
	
	/**查询所有常见问题
	 * @return
	 * @throws BusinessException
	 */
	List<QaDTO> getQas() throws BusinessException;
	
	/**根据标题搜索常见问题
	 * @param title - 标题
	 * @return
	 * @throws BusinessException
	 */
	List<QaDTO> searchQas(String title) throws BusinessException;
	
	/** 获取所有已发布文案
	 * @return
	 * @throws BusinessException
	 */
	List<ArticleDTO> getPublishedArticles() throws BusinessException;
	
	/**获取所有已发布常见问题
	 * @return
	 * @throws BusinessException
	 */
	List<QaDTO> getPublishedQas() throws BusinessException;
	
	/**分页查询文案
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return
	 * @throws BusinessException
	 */
	List<ArticleDTO> getArticlesByPage(int startIndex, int pageSize) throws BusinessException;
	
	/**分页查询常见问题
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return
	 * @throws BusinessException
	 */
	List<QaDTO> getQasByPage(int startIndex, int pageSize) throws BusinessException;
	
	/**分页查询已发布文案
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return
	 * @throws BusinessException
	 */
	List<ArticleDTO> getPublishedArticlesByPage(int startIndex, int pageSize) throws BusinessException;
	
	/**
	 * 分页查询已发布常见问题
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return
	 * @throws BusinessException
	 */
	List<QaDTO> getPublishedQasByPage(int startIndex, int pageSize) throws BusinessException;
	
	/**获取所有文案数量
	 * @return
	 * @throws BusinessException
	 */
	int getArticleCount() throws BusinessException;
	
	/**获取所有常见问题数量
	 * @return
	 * @throws BusinessException
	 */
	int getQaCount() throws BusinessException;
	
	/** 获取所有已发布文案数量
	 * @return
	 * @throws BusinessException
	 */
	int getPublishedArticleCount() throws BusinessException;
	
	/**获取所有已发布常见问题数量
	 * @return
	 * @throws BusinessException
	 */
	int getPublishedQaCount() throws BusinessException;
	
	/**添加轮播图
	 * @param swiperDTO - 轮播图
	 * @throws BusinessException
	 */
	void addSwiper(SwiperDTO swiperDTO) throws BusinessException;
	
	/**删除轮播图
	 * @param swiperDTO - 轮播图
	 * @throws BusinessException
	 */
	void deleteSwiper(SwiperDTO swiperDTO) throws BusinessException;
	
	/**更新轮播图
	 * @param swiperDTO - 轮播图
	 * @throws BusinessException
	 */
	void updateSwiper(SwiperDTO swiperDTO) throws BusinessException;
	
	/**获取所有轮播图
	 * @return
	 * @throws BusinessException
	 */
	List<SwiperDTO> getSwipers()  throws BusinessException;
	
	/**根据id获取轮播图
	 * @param uuid - 轮播图id
	 * @return
	 * @throws BusinessException
	 */
	SwiperDTO getSwiperByUuid(String uuid)  throws BusinessException;
	
	/**添加广告
	 * @param adDTO - 广告
	 * @throws BusinessException
	 */
	void addAd(AdDTO adDTO) throws BusinessException;
	
	/**删除广告
	 * @param adDTO - 广告
	 * @throws BusinessException
	 */
	void deleteAd(AdDTO adDTO) throws BusinessException;
	
	/** 更新广告
	 * @param adDTO - 广告
	 * @throws BusinessException
	 */
	void updateAd(AdDTO AdDTO) throws BusinessException;
	
	/**启用广告
	 * @param adDTO - 广告
	 * @throws BusinessException
	 */
	void enableAd(AdDTO AdDTO) throws BusinessException;
	
	/**停用广告
	 * @param adDTO - 广告
	 * @throws BusinessException
	 */
	void disableAd(AdDTO AdDTO) throws BusinessException;
	
	/**获取所有有效广告
	 * @return
	 * @throws BusinessException
	 */
	List<AdDTO> getActiveAds()  throws BusinessException;

	/**
	 * 获取所有广告
	 * @return
	 * @throws BusinessException
	 */
	List<AdDTO> getAllAds()  throws BusinessException;
	
	/** 获取某个广告位置的广告列表
	 * @param location - 广告位置(位置列表定义在AdConstants)
	 * @return
	 * @throws BusinessException
	 */
	List<AdDTO> getAdsByLocation(String location)  throws BusinessException;
	
	/**根据商品分类id查询广告
	 * @param productCateUuid - 商品分类id
	 * @return
	 * @throws BusinessException
	 */
	List<AdDTO> getAdsByProductCate(String productCateUuid)  throws BusinessException;
	
	/**根据位置和商品分类id查询广告
	 * @param location - 广告位置(位置列表定义在AdConstants)
	 * @param productCateUuid - 商品分类id
	 * @return
	 * @throws BusinessException
	 */
	List<AdDTO> getAdsByLocationProductCate(String location, String productCateUuid)  throws BusinessException;
	
	/**根据id查询广告
	 * @param uuid - 广告id
	 * @return
	 * @throws BusinessException
	 */
	AdDTO getAdByUuid(String uuid)  throws BusinessException;
	
}
