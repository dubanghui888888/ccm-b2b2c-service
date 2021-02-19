
package com.mb.ext.core.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.ext.core.entity.point.PointProductSkuEntity;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.ProductCommentSearchDTO;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.UserPointStatementDTO;
import com.mb.ext.core.service.spec.UserSignDTO;
import com.mb.ext.core.service.spec.UserStatementSearchDTO;
import com.mb.ext.core.service.spec.point.PointProductCateDTO;
import com.mb.ext.core.service.spec.point.PointProductCommentDTO;
import com.mb.ext.core.service.spec.point.PointProductDTO;
import com.mb.ext.core.service.spec.point.PointProductSkuDTO;
import com.mb.framework.exception.BusinessException;

/** 积分接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface PointService<T extends BodyDTO>
{

	/**积分签到
	 * @param userDTO
	 * @return
	 * @throws BusinessException
	 */
	public int signPoint(UserDTO userDTO) throws BusinessException;
	
	/**分页查询会员积分明细
	 * @param searchDTO
	 * @return
	 * @throws BusinessException
	 */
	public List<UserPointStatementDTO> searchUserPointStatement(UserStatementSearchDTO searchDTO) throws BusinessException;
	
	/**分页查询会员积分明细记录数
	 * @param searchDTO
	 * @return
	 * @throws BusinessException
	 */
	public int searchUserPointStatementTotal(UserStatementSearchDTO searchDTO) throws BusinessException;
	
	/**查询会员签到记录
	 * @param userDTO
	 * @return
	 * @throws BusinessException
	 */
	public List<UserSignDTO> getSignByUser(UserDTO userDTO) throws BusinessException;
	
	/**查询会员某个日期是否签到
	 * @param userDTO
	 * @param signDate
	 * @return
	 * @throws BusinessException
	 */
	public boolean isUserSigned(UserDTO userDTO, Date signDate) throws BusinessException;
	
	/**查询会员已经连续第几天签到
	 * @param userDTO
	 * @return
	 * @throws BusinessException
	 */
	public int getSignedDateNum(UserDTO userDTO, Date signTime) throws BusinessException;
	
	/**添加积分商品类别
	 * @param productCateDTO - 商品类别
	 * @throws BusinessException
	 */
	void addProductCate(PointProductCateDTO productCateDTO) throws BusinessException;
	
	/**删除积分商品类别
	 * @param productCateDTO - 商品类别
	 * @throws BusinessException
	 */
	void deleteProductCate(PointProductCateDTO productCateDTO) throws BusinessException;
	
	/** 更新积分商品类别
	 * @param productCateDTO - 商品类别
	 * @throws BusinessException
	 */
	void updateProductCate(PointProductCateDTO productCateDTO) throws BusinessException;
	
	/**获取所有根商品类别
	 * @return 根商品类别
	 * @throws BusinessException
	 */
	List<PointProductCateDTO> getRootProductCate() throws BusinessException;
	
	/**获取在首页显示的积分商品类别
	 * @return
	 * @throws BusinessException
	 */
	List<PointProductCateDTO> getHomeProductCate() throws BusinessException;
	
	/**获取叶子积分商品类别, 该类别下没有更多子类别
	 * @return
	 * @throws BusinessException
	 */
	List<PointProductCateDTO> getLeafProductCate() throws BusinessException;
	
	/**获取某个积分商品类别的子类别
	 * @param productCateDTO - 商品类别
	 * @return
	 * @throws BusinessException
	 */
	List<PointProductCateDTO> getChildProductCate(PointProductCateDTO productCateDTO) throws BusinessException;
	
	/**根据id查询积分商品类别
	 * @param uuid - 商品类别id
	 * @return
	 * @throws BusinessException
	 */
	PointProductCateDTO getProductCateByUuid(String uuid) throws BusinessException;
	
	/**添加积分商品
	 * @param productDTO - 商品
	 * @throws BusinessException
	 */
	void addProduct(PointProductDTO productDTO) throws BusinessException;
	
	/**删除积分商品
	 * @param productDTO - 商品
	 * @throws BusinessException
	 */
	void deleteProduct(PointProductDTO productDTO) throws BusinessException;
	
	/**更新积分商品
	 * @param productDTO - 商品
	 * @throws BusinessException
	 */
	void updateProduct(PointProductDTO productDTO) throws BusinessException;
	
	/**积分商品上架
	 * @param productDTO - 商品
	 * @throws BusinessException
	 */
	void enableProduct(PointProductDTO productDTO) throws BusinessException;
	
	/**积分商品下架
	 * @param productDTO - 商品
	 * @throws BusinessException
	 */
	void disableProduct(PointProductDTO productDTO) throws BusinessException;
	
	/**查询团购商品详情
	 * @param uuid
	 * @return
	 * @throws BusinessException
	 */
	PointProductDTO getProductByUuid(String uuid) throws BusinessException;
	
	/**查询团购商品规格详情
	 * @param uuid
	 * @return
	 * @throws BusinessException
	 */
	PointProductSkuDTO getProductSkuByUuid(String uuid) throws BusinessException;
	
	/**分页搜索商品
	 * @param searchDTO - 查询条件
	 * @return 商品列表
	 * @throws BusinessException
	 */
	List<PointProductDTO> searchProduct(ProductSearchDTO searchDTO) throws BusinessException;
	
	/**根据条件查询商品数量
	 * @param searchDTO - 查询条件
	 * @return 商品数量
	 * @throws BusinessException
	 */
	int searchProductTotal(ProductSearchDTO searchDTO) throws BusinessException;
	
	
	/**搜索积分商品
	 * @param PointSearchDTO
	 * @return
	 * @throws BusinessException
	 */
	List<PointProductDTO> searchPointProduct(ProductSearchDTO searchDTO) throws BusinessException;
	
	/**搜索积分商品数量
	 * @param PointSearchDTO
	 * @return
	 * @throws BusinessException
	 */
	int searchPointProductTotal(ProductSearchDTO searchDTO) throws BusinessException;
	
	/**添加积分商品评论
	 * @param productCommentDTO - 商品评论
	 * @throws BusinessException
	 */
	void addProductComment(PointProductCommentDTO productCommentDTO) throws BusinessException;
	
	/**更新积分商品评论
	 * @param productCommentDTO - 商品评论
	 * @throws BusinessException
	 */
	void updateProductComment(PointProductCommentDTO productCommentDTO) throws BusinessException;
	
	/**设置评论是否可见
	 * @param commentDTO - 商品评论
	 * @param showStatus - 是否可见
	 * @throws BusinessException
	 */
	void setProductCommentShowStatus(PointProductCommentDTO commentDTO, boolean showStatus) throws BusinessException;
	
	/**删除积分商品评论
	 * @param productCommentDTO - 商品评论
	 * @throws BusinessException
	 */
	void deleteProductComment(PointProductCommentDTO productCommentDTO) throws BusinessException;

	/**回复积分商品评论
	 * @param productCommentDTO - 商品评论
	 * @throws BusinessException
	 */
	void replayProductComment(PointProductCommentDTO productCommentDTO) throws BusinessException;
	
	/** 更新积分商品评论
	 * @param productCommentDTO - 商品评论
	 * @throws BusinessException
	 */
	void updateProductCommentReply(PointProductCommentDTO productCommentDTO) throws BusinessException;
	
	/**查询某个积分商品所有评论
	 * @param productDTO - 商品
	 * @return 评论列表
	 * @throws BusinessException
	 */
	List<PointProductCommentDTO> getProductComments(PointProductDTO productDTO) throws BusinessException;
	
	/**根据id查询积分商品评论
	 * @param uuid - 评论id
	 * @return
	 * @throws BusinessException
	 */
	PointProductCommentDTO getProductCommentByUuid(String uuid) throws BusinessException;
	
	/** 获取某个积分商品评论总数
	 * @param productDTO - 商品
	 * @return 评论总数
	 * @throws BusinessException
	 */
	int getProductCommentTotal(PointProductDTO productDTO) throws BusinessException;
	
	/** 根据条件查询积分商品评论
	 * @param searchDTO - 查询条件
	 * @param isShowOnly - 是否可见
	 * @return
	 * @throws BusinessException
	 */
	List<PointProductCommentDTO> searchProductComment(ProductCommentSearchDTO searchDTO,boolean isShowOnly) throws BusinessException;
	
	/**根据条件查询积分商品评论总数
	 * @param searchDTO - 查询条件
	 * @param isShowOnly - 是否可见
	 * @return 评论总数
	 * @throws BusinessException
	 */
	int searchProductCommentTotal(ProductCommentSearchDTO searchDTO,boolean isShowOnly) throws BusinessException;
	
	/**查询正在积分的商品
	 * @param 
	 * @return
	 * @throws BusinessException
	 */
	//List<PointProductDTO> getBeingPointProducts() throws BusinessException;
	
	/**查询即将积分的商品
	 * @param 
	 * @return
	 * @throws BusinessException
	 */
	//List<PointProductDTO> getComingPointProducts() throws BusinessException;
	
	/**商品entity到dto对象的转换, 主要用于积分商品列表
	 * @param productEntity
	 * @param productDTO
	 */
	void productEntity2DTO(PointProductEntity productEntity, PointProductDTO productDTO);
	
	/**商品entity到dto对象的转换, 主要用于积分商品详情
	 * @param productEntity
	 * @param productDTO
	 */
	void productDetailEntity2DTO(PointProductEntity productEntity, PointProductDTO productDTO);
	
	/**积分商品规格entity到dto对象的转换
	 * @param productSkuEntity
	 * @param productSkuDTO
	 */
	void productSkuEntity2DTO(PointProductSkuEntity productSkuEntity, PointProductSkuDTO productSkuDTO);
	
}
