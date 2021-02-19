
package com.mb.ext.core.service;

import java.util.List;

import com.mb.ext.core.service.spec.*;
import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductSkuEntity;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.product.ProductBrandDTO;
import com.mb.ext.core.service.spec.product.ProductCateAttrDTO;
import com.mb.ext.core.service.spec.product.ProductCateDTO;
import com.mb.ext.core.service.spec.product.ProductCommentDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.service.spec.product.ProductFreightDTO;
import com.mb.ext.core.service.spec.product.ProductSkuDTO;
import com.mb.ext.core.service.spec.promote.PromoteDiscountDTO;
import com.mb.ext.core.service.spec.promote.PromoteFreightOffDTO;
import com.mb.ext.core.service.spec.promote.PromoteMoneyOffDTO;
import com.mb.ext.core.service.spec.supplier.SupplierDTO;
import com.mb.framework.exception.BusinessException;

/**商品接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface ProductService<T extends BodyDTO>
{

	/**添加商品类别
	 * @param productCateDTO - 商品类别
	 * @throws BusinessException
	 */
	void addProductCate(ProductCateDTO productCateDTO) throws BusinessException;
	
	/**删除商品类别
	 * @param productCateDTO - 商品类别
	 * @throws BusinessException
	 */
	void deleteProductCate(ProductCateDTO productCateDTO) throws BusinessException;
	
	/** 更新商品类别
	 * @param productCateDTO - 商品类别
	 * @throws BusinessException
	 */
	void updateProductCate(ProductCateDTO productCateDTO) throws BusinessException;
	
	/**添加品牌
	 * @param productBrandDTO - 品牌
	 * @throws BusinessException
	 */
	void addProductBrand(ProductBrandDTO productBrandDTO) throws BusinessException;
	
	/** 删除品牌
	 * @param productBrandDTO - 品牌
	 * @throws BusinessException
	 */
	void deleteProductBrand(ProductBrandDTO productBrandDTO) throws BusinessException;
	
	/**更新品牌
	 * @param productBrandDTO - 品牌
	 * @throws BusinessException
	 */
	void updateProductBrand(ProductBrandDTO productBrandDTO) throws BusinessException;
	
	/** 获取所有品牌
	 * @return 品牌列表
	 * @throws BusinessException
	 */
	List<ProductBrandDTO> getProductBrands() throws BusinessException;
	
	/**根据id获取品牌
	 * @param uuid - 品牌id
	 * @return
	 * @throws BusinessException
	 */
	ProductBrandDTO getProductBrandByUuid(String uuid) throws BusinessException;
	
	/**分页搜索商品品牌
	 * @param searchDTO - 查询条件
	 * @return
	 * @throws BusinessException
	 */
	List<ProductBrandDTO> searchProductBrand(ProductBrandSearchDTO searchDTO) throws BusinessException;
	
	/**根据条件查询品牌数量
	 * @param searchDTO
	 * @return 品牌数量
	 * @throws BusinessException
	 */
	int searchProductBrandTotal(ProductBrandSearchDTO searchDTO) throws BusinessException;
	
	/**获取所有根商品类别
	 * @return 根商品类别
	 * @throws BusinessException
	 */
	List<ProductCateDTO> getRootProductCate() throws BusinessException;
	
	/**获取在首页显示的商品类别
	 * @return
	 * @throws BusinessException
	 */
	List<ProductCateDTO> getHomeProductCate() throws BusinessException;
	
	/**获取叶子商品类别, 该类别下没有更多子类别
	 * @return
	 * @throws BusinessException
	 */
	List<ProductCateDTO> getLeafProductCate() throws BusinessException;
	
	/**获取某个类别的子类别
	 * @param productCateDTO - 商品类别
	 * @return
	 * @throws BusinessException
	 */
	List<ProductCateDTO> getChildProductCate(ProductCateDTO productCateDTO) throws BusinessException;
	
	/**查询商品类别下的所有品牌
	 * @param productCateDTO - 商品类别
	 * @return
	 * @throws BusinessException
	 */
	List<ProductBrandDTO> getProductBrandByProductCate(ProductCateDTO productCateDTO) throws BusinessException;
	
	/**根据id查询商品类别
	 * @param uuid - 商品类别id
	 * @return
	 * @throws BusinessException
	 */
	ProductCateDTO getProductCateByUuid(String uuid) throws BusinessException;
	
	/**添加商品类别的属性
	 * @param productCateAttrDTO - 类别属性
	 * @throws BusinessException
	 */
	void addProductCateAttr(ProductCateAttrDTO productCateAttrDTO) throws BusinessException;
	
	/**更新商品类别的属性
	 * @param productCateAttrDTO - 类别属性
	 * @throws BusinessException
	 */
	void updateProductCateAttr(ProductCateAttrDTO productCateAttrDTO) throws BusinessException;
	
	/** 删除商品类别的属性
	 * @param productCateAttrDTO - 类别属性
	 * @throws BusinessException
	 */
	void deleteProductCateAttr(ProductCateAttrDTO productCateAttrDTO) throws BusinessException;
	
	/**商品entity到dto对象的转换, 主要用于商品列表
	 * @param productEntity
	 * @param productDTO
	 */
	void productEntity2DTO(ProductEntity productEntity, ProductDTO productDTO);
	
	/**商品entity到dto对象的转换, 主要用于商品详情
	 * @param productEntity
	 * @param productDTO
	 */
	void productDetailEntity2DTO(ProductEntity productEntity, ProductDTO productDTO);
	
	/**商品规格entity到dto对象的转换
	 * @param productSkuEntity
	 * @param productSkuDTO
	 */
	void productSkuEntity2DTO(ProductSkuEntity productSkuEntity, ProductSkuDTO productSkuDTO);
	
	/** 新增商品组
	 * @param groupDTO - 商品组
	 * @throws BusinessException
	 */
	void addProductGroup(GroupDTO groupDTO) throws BusinessException;
	
	/**更新商品组
	 * @param groupDTO - 商品组
	 * @throws BusinessException
	 */
	void updateProductGroup(GroupDTO groupDTO) throws BusinessException;
	
	/**设置商品组在首页显示
	 * @param groupDTO - 商品组
	 * @throws BusinessException
	 */
	void setProductGroupDisplayedHome(GroupDTO groupDTO) throws BusinessException;
	
	/**取消设置商品组在首页显示
	 * @param groupDTO - 商品组
	 * @throws BusinessException
	 */
	void cancelProductGroupDisplayedHome(GroupDTO groupDTO) throws BusinessException;
	
	/**设置商品组为会员注册专用商品
	 * @param groupDTO - 商品组
	 * @throws BusinessException
	 */
	void setProductGroupForRegister(GroupDTO groupDTO) throws BusinessException;
	
	/**取消设置商品组为会员注册专用商品
	 * @param groupDTO - 商品组
	 * @throws BusinessException
	 */
	void cancelProductGroupForRegister(GroupDTO groupDTO) throws BusinessException;
	
	/**删除商品组
	 * @param groupDTO - 商品组
	 * @throws BusinessException
	 */
	void deleteProductGroup(GroupDTO groupDTO) throws BusinessException;

	/** 查询某个商家商品组
	 * @param merchantDTO - 商家
	 * @return
	 * @throws BusinessException
	 */
	List<GroupDTO> getProductGroupsByMerchant(MerchantDTO merchantDTO) throws BusinessException;
		
	/**查询所有商品组
	 * @return 商品组列表
	 * @throws BusinessException
	 */
	List<GroupDTO> getProductGroups() throws BusinessException;
	
	/**查询首页商品组
	 * @return 商品组列表
	 * @throws BusinessException
	 */
	List<GroupDTO> getHomeProductGroups() throws BusinessException;
	
	/**根据名称查询单个商品组
	 * @param name - 商品组名称
	 * @return
	 * @throws BusinessException
	 */
	GroupDTO getProductGroupByName(String name) throws BusinessException;
	
	/**根据id查询单个商品组
	 * @param uuid - 商品组id
	 * @return
	 * @throws BusinessException
	 */
	GroupDTO getProductGroupByUuid(String uuid) throws BusinessException;
	
	/**查询会员注册专用商品组
	 * @return
	 * @throws BusinessException
	 */
	GroupDTO getProductGroupForRegister() throws BusinessException;
	
	/**添加商品
	 * @param productDTO - 商品
	 * @throws BusinessException
	 */
	void addProduct(ProductDTO productDTO) throws BusinessException;
	
	/**删除商品
	 * @param productDTO - 商品
	 * @throws BusinessException
	 */
	void deleteProduct(ProductDTO productDTO) throws BusinessException;
	
	/**更新商品
	 * @param productDTO - 商品
	 * @throws BusinessException
	 */
	void updateProduct(ProductDTO productDTO) throws BusinessException;
	
	/**根据供应商查询商品
	 * @param supplierDTO - 供应商
	 * @return
	 * @throws BusinessException
	 */
	List<ProductDTO> getProductBySupplier(SupplierDTO supplierDTO) throws BusinessException;
	
	/**查询某个商品类别下的所有商品
	 * @param cateDTO - 商品类别
	 * @return
	 * @throws BusinessException
	 */
	List<ProductDTO> getProductByCate(ProductCateDTO cateDTO) throws BusinessException;
	
	/**根据商品种类查询
	 * @param productType - 商品种类
	 * @return
	 * @throws BusinessException
	 */
	List<ProductDTO> getProductByType(String productType) throws BusinessException;
	
	/**分页搜索商品
	 * @param searchDTO - 查询条件
	 * @return 商品列表
	 * @throws BusinessException
	 */
	List<ProductDTO> searchProduct(ProductSearchDTO searchDTO) throws BusinessException;
	
	/**根据条件查询商品数量
	 * @param searchDTO - 查询条件
	 * @return 商品数量
	 * @throws BusinessException
	 */
	int searchProductTotal(ProductSearchDTO searchDTO) throws BusinessException;
	
	/**根据id查询单个商户商品
	 * @param uuid - 商品id
	 * @return 商品
	 * @throws BusinessException
	 */
	ProductDTO getProductByUuid(String uuid) throws BusinessException;
	
	/**商品上架提交审核
	 * @param productDTO - 商品
	 * @throws BusinessException
	 */
	void submitProduct(ProductDTO productDTO) throws BusinessException;
	
	/**商品上架
	 * @param productDTO - 商品
	 * @throws BusinessException
	 */
	void enableProduct(ProductDTO productDTO) throws BusinessException;
	
	/**商品下架
	 * @param productDTO - 商品
	 * @throws BusinessException
	 */
	void disableProduct(ProductDTO productDTO) throws BusinessException;
	
	/**商品上架审核通过
	 * @param productDTO - 商品
	 * @throws BusinessException
	 */
	void approveProduct(ProductDTO productDTO) throws BusinessException;
	
	/**商品上架审核拒绝
	 * @param productDTO - 商品
	 * @throws BusinessException
	 */
	void rejectProduct(ProductDTO productDTO) throws BusinessException;
	
	/**添加运费模板
	 * @param productFreightDTO - 商品运费模板
	 * @throws BusinessException
	 */
	void addProductFreight(ProductFreightDTO productFreightDTO) throws BusinessException;
	
	/**更新运费模板
	 * @param productFreightDTO - 商品运费模板
	 * @throws BusinessException
	 */
	void updateProductFreight(ProductFreightDTO productFreightDTO) throws BusinessException;
	
	/**删除运费模板
	 * @param productFreightDTO - 商品运费模板
	 * @throws BusinessException
	 */
	void deleteProductFreight(ProductFreightDTO productFreightDTO) throws BusinessException;
	
	/**设置默认运费模板
	 * @param productFreightDTO - 商品运费模板
	 * @throws BusinessException
	 */
	void defaultProductFreight(ProductFreightDTO productFreightDTO) throws BusinessException;
	
	/** 取消设置默认运费模板
	 * @param productFreightDTO - 商品运费模板
	 * @throws BusinessException
	 */
	void cancelDefaultProductFreight(ProductFreightDTO productFreightDTO) throws BusinessException;
	
	/**启用运费模板
	 * @param productFreightDTO - 商品运费模板
	 * @throws BusinessException
	 */
	void enableProductFreight(ProductFreightDTO productFreightDTO) throws BusinessException;
		
	/**禁用运费模板
	 * @param productFreightDTO - 商品运费模板
	 * @throws BusinessException
	 */
	void disableProductFreight(ProductFreightDTO productFreightDTO) throws BusinessException;
	
	/**查询所有运费模板
	 * @return 运费模板
	 * @throws BusinessException
	 */
	List<ProductFreightDTO> getProductFreights() throws BusinessException;
	
	/**查询商家运费模板
	 * @param merchantDTO - 商家
	 * @return 运费模板列表
	 * @throws BusinessException
	 */
	List<ProductFreightDTO> getProductFreightsByMerchant(MerchantDTO merchantDTO) throws BusinessException;

	/**分页查询商家运费模板
	 * @param merchantSearchDTO - 查询条件
	 * @return 运费模板列表
	 * @throws BusinessException
	 */
	List<ProductFreightDTO> searchProductFreight(MerchantSearchDTO merchantSearchDTO) throws BusinessException;

	/**商家运费模板数量
	 * @param merchantSearchDTO - 查询条件
	 * @return 运费模板列表
	 * @throws BusinessException
	 */
	int searchProductFreightTotal(MerchantSearchDTO merchantSearchDTO) throws BusinessException;
	
	/**根据id查询运费模板
	 * @param uuid - 运费模板Id
	 * @return
	 * @throws BusinessException
	 */
	ProductFreightDTO getProductFreightByUuid(String uuid) throws BusinessException;
	
	/**添加商品评论
	 * @param productCommentDTO - 商品评论
	 * @throws BusinessException
	 */
	void addProductComment(ProductCommentDTO productCommentDTO) throws BusinessException;
	
	/**更新商品评论
	 * @param productCommentDTO - 商品评论
	 * @throws BusinessException
	 */
	void updateProductComment(ProductCommentDTO productCommentDTO) throws BusinessException;
	
	/**设置评论是否可见
	 * @param commentDTO - 商品评论
	 * @param showStatus - 是否可见
	 * @throws BusinessException
	 */
	void setProductCommentShowStatus(ProductCommentDTO commentDTO, boolean showStatus) throws BusinessException;
	
	/**删除商品评论
	 * @param productCommentDTO - 商品评论
	 * @throws BusinessException
	 */
	void deleteProductComment(ProductCommentDTO productCommentDTO) throws BusinessException;

	/**回复商品评论
	 * @param productCommentDTO - 商品评论
	 * @throws BusinessException
	 */
	void replayProductComment(ProductCommentDTO productCommentDTO) throws BusinessException;
	
	/** 更新商品评论
	 * @param productCommentDTO - 商品评论
	 * @throws BusinessException
	 */
	void updateProductCommentReply(ProductCommentDTO productCommentDTO) throws BusinessException;
	
	/**查询某个商品所有评论
	 * @param productDTO - 商品
	 * @return 评论列表
	 * @throws BusinessException
	 */
	List<ProductCommentDTO> getProductComments(ProductDTO productDTO) throws BusinessException;
	
	/**根据id查询商品评论
	 * @param uuid - 评论id
	 * @return
	 * @throws BusinessException
	 */
	ProductCommentDTO getProductCommentByUuid(String uuid) throws BusinessException;
	
	/** 获取某个商品评论总数
	 * @param productDTO - 商品
	 * @return 评论总数
	 * @throws BusinessException
	 */
	int getProductCommentTotal(ProductDTO productDTO) throws BusinessException;
	
	/** 根据条件查询商品评论
	 * @param searchDTO - 查询条件
	 * @param isShowOnly - 是否可见
	 * @return
	 * @throws BusinessException
	 */
	List<ProductCommentDTO> searchProductComment(ProductCommentSearchDTO searchDTO,boolean isShowOnly) throws BusinessException;
	
	/**根据条件查询商品评论总数
	 * @param searchDTO - 查询条件
	 * @param isShowOnly - 是否可见
	 * @return 评论总数
	 * @throws BusinessException
	 */
	int searchProductCommentTotal(ProductCommentSearchDTO searchDTO,boolean isShowOnly) throws BusinessException;
	
	/**商品添加满减送活动
	 * @param productDTOList - 商品列表
	 * @param moneyOffDTO - 满减活动
	 * @throws BusinessException
	 */
	void addProductPromoteMoneyOff(List<ProductDTO> productDTOList, PromoteMoneyOffDTO moneyOffDTO) throws BusinessException;
	
	/**取消商品添加满减送活动
	 * @param productDTOList - 商品列表
	 * @param moneyOffDTO - 满减活动
	 * @throws BusinessException
	 */
	void deleteProductPromoteMoneyOff(List<ProductDTO> productDTOList, PromoteMoneyOffDTO moneyOffDTO) throws BusinessException;
	
	/**商品添加折扣活动
	 * @param productDTOList - 商品列表
	 * @param discountDTO - 折扣
	 * @throws BusinessException
	 */
	void addProductPromoteDiscount(List<ProductDTO> productDTOList, PromoteDiscountDTO discountDTO) throws BusinessException;
	
	/**取消商品折扣活动
	 * @param productDTOList - 商品列表
	 * @param discountDTO - 折扣
	 * @throws BusinessException
	 */
	void deleteProductPromoteDiscount(List<ProductDTO> productDTOList, PromoteDiscountDTO discountDTO) throws BusinessException;
	
	/**商品添加包邮活动
	 * @param productDTOList - 商品列表
	 * @param freightOffDTO - 包邮
	 * @throws BusinessException
	 */
	void addProductPromoteFreightOff(List<ProductDTO> productDTOList, PromoteFreightOffDTO freightOffDTO) throws BusinessException;
	
	/**取消商品包邮活动
	 * @param productDTOList - 商品列表
	 * @param freightOffDTO - 包邮
	 * @throws BusinessException
	 */
	void deleteProductPromoteFreightOff(List<ProductDTO> productDTOList, PromoteFreightOffDTO freightOffDTO) throws BusinessException;
	
	/*
	//重建整个商品SKU, 已售数量清零
	void rebuildProductSkuAttr(ProductDTO productDTO) throws BusinessException;
	//更新单个SKU属性
	void updateProductSkuAttr(ProductAttrDTO productAttrDTO) throws BusinessException;
	//删除单个SKU属性
	void deleteProductSkuAttr(ProductAttrDTO productAttrDTO) throws BusinessException;
	//添加单个SKU属性
	void addProductSkuAttr(ProductAttrDTO productAttrDTO) throws BusinessException;
	//更新单个非SKU属性
	void updateProductNonSkuAttr(ProductAttrDTO productAttrDTO) throws BusinessException;
	//删除单个非SKU属性
	void deleteProductNonSkuAttr(ProductAttrDTO productAttrDTO) throws BusinessException;
	//添加单个非SKU属性
	void addProductNonSkuAttr(ProductAttrDTO productAttrDTO) throws BusinessException;
	*/
	
	
}
