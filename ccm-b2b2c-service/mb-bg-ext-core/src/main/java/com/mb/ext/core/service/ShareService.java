
package com.mb.ext.core.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.content.ShareCommentAtDTO;
import com.mb.ext.core.service.spec.content.ShareCommentDTO;
import com.mb.ext.core.service.spec.content.ShareDTO;
import com.mb.ext.core.service.spec.content.ShareDTOList;
import com.mb.framework.exception.BusinessException;

/**会员文案发布接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface ShareService<T extends BodyDTO>
{
	
	
	/**会员添加文案
	 * @param shareDTO - 文案
	 * @return 文案id
	 * @throws BusinessException
	 */
	String addShare(ShareDTO shareDTO) throws BusinessException;
	
	/**会员发布文案
	 * @param shareDTO - 文案
	 * @return
	 * @throws BusinessException
	 */
	void publishShare(ShareDTO shareDTO) throws BusinessException;
	
	/**会员更新文案
	 * @param shareDTO - 文案
	 * @return
	 * @throws BusinessException
	 */
	void updateShare(ShareDTO shareDTO) throws BusinessException;
	
	/** 会员评论文案
	 * @param shareCommentDTO - 文案评论
	 * @throws BusinessException
	 */
	void commentShare(ShareCommentDTO shareCommentDTO) throws BusinessException;
	
	/**会员回复文案
	 * @param shareCommentAtDTO - 文案回复
	 * @throws BusinessException
	 */
	void atShareComment(ShareCommentAtDTO shareCommentAtDTO) throws BusinessException;
	
	/**会员点赞文案
	 * @param shareDTO - 文案
	 * @throws BusinessException
	 */
	void likeShare(ShareDTO shareDTO) throws BusinessException;
	
	/**取消点赞
	 * @param shareDTO - 文案
	 * @throws BusinessException
	 */
	void cancelLikeShare(ShareDTO shareDTO) throws BusinessException;
	
	/**删除文案
	 * @param shareDTO - 文案
	 * @throws BusinessException
	 */
	void deleteShare(ShareDTO shareDTO) throws BusinessException;
	
	/**批量删除文案
	 * @param list - 文案列表
	 * @throws BusinessException
	 */
	void deleteMultipleShares(ShareDTOList list) throws BusinessException;
	
	/**获取指定日期发布的文案
	 * @param createDate - 发布日期
	 * @return
	 * @throws BusinessException
	 */
	List<ShareDTO> getSharesByCreateDate(Date createDate) throws BusinessException;
	
	/**根据标签名查询文案
	 * @param tagName - 标签名称
	 * @return
	 * @throws BusinessException
	 */
	List<ShareDTO> getSharesByTagName(String tagName) throws BusinessException;
	
	/**查询会员发布的文案
	 * @param userDTO - 会员
	 * @return
	 * @throws BusinessException
	 */
	List<ShareDTO> getSharesByUser(UserDTO userDTO) throws BusinessException;
	
	/**根据标签名称分页查询会员所有文案
	 * @param tagName - 标签名称
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return 文案列表
	 * @throws BusinessException
	 */
	List<ShareDTO> getSharesByTagNamePage(String tagName, int startIndex, int pageSize) throws BusinessException;
	
	/**根据标签名称分页查询会员所有发布的文案
	 * @param tagName - 标签名称
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return 文案列表
	 * @throws BusinessException
	 */
	List<ShareDTO> getPublishedSharesByTagNamePage(String tagName, int startIndex, int pageSize) throws BusinessException;
	
	/**获取某个标签下的所有文案数量
	 * @param tagName - 标签名
	 * @return 文案数量
	 * @throws BusinessException
	 */
	int getShareCountByTagName(String tagName) throws BusinessException;
	
	/**获取某个标签下的已发布文案数量
	 * @param tagName - 标签名
	 * @return 文案数量
	 * @throws BusinessException
	 */
	int getPublishedShareCountByTagName(String tagName) throws BusinessException;
	
	/**根据id查询文案
	 * @param uuid - 文案id
	 * @return 文案
	 * @throws BusinessException
	 */
	ShareDTO getShareByUuid(String uuid) throws BusinessException;
	
	/**查询所有文案
	 * @return 文案列表
	 * @throws BusinessException
	 */
	List<ShareDTO> getShares() throws BusinessException;
	
	/**分页查询所有会员文案
	  * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return 文案列表
	 * @throws BusinessException
	 */
	List<ShareDTO> getSharesByPage(int startIndex, int pageSize) throws BusinessException;
	
	/**分页查询会员已发布文案
	  * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return 文案列表
	 * @throws BusinessException
	 */
	List<ShareDTO> getPublishedSharesByPage(int startIndex, int pageSize) throws BusinessException;
	
	/**获取所有会员文案数量
	 * @return 文案数量
	 * @throws BusinessException
	 */
	int getShareCount() throws BusinessException;

	/**获取所有会员已发布文案数量
	 * @return 文案数量
	 * @throws BusinessException
	 */
	int getPublishedShareCount() throws BusinessException;
	
}
