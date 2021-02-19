package com.mb.ext.core.dao.impl.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.product.ProductCommentDAO;
import com.mb.ext.core.entity.product.ProductCommentEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.service.spec.ProductCommentSearchDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("productCommentDAO")
@Qualifier("productCommentDAO")
public class ProductCommentDAOImpl extends AbstractBaseDAO<ProductCommentEntity> implements
		ProductCommentDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public ProductCommentDAOImpl() {
		super();
		this.setEntityClass(ProductCommentEntity.class);
	}

	@Override
	public void addProductComment(ProductCommentEntity productCommentEntity) throws DAOException {
		
		save(productCommentEntity);
		
	}


	@Override
	public void deleteProductComment(ProductCommentEntity productCommentEntity) throws DAOException {
		
		deletePhysical(productCommentEntity);
		
	}

	@Override
	public void updateProductComment(ProductCommentEntity productCommentEntity) throws DAOException {
		
		update(productCommentEntity);
		
	}


	@Override
	public ProductCommentEntity getProductCommentByUuid(String uuid)
			throws DAOException {
		ProductCommentEntity productCommentEntity = null;
		try {
			productCommentEntity = (ProductCommentEntity)em.createQuery("select b from ProductCommentEntity b where  b.productCommentUuid = :UUID",ProductCommentEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product : "+uuid);
		}
		return productCommentEntity;
	}

	@Override
	public List<ProductCommentEntity> getProductCommentByProduct(
			ProductEntity productEntity) throws DAOException {
		List<ProductCommentEntity> productCommentEntityList = new ArrayList<ProductCommentEntity>();
		try {
			productCommentEntityList = em.createQuery("select b from ProductCommentEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",ProductCommentEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product comment: "+productEntity.getProductUuid());
		}
		return productCommentEntityList;
	}

	@Override
	public int getProductCommentTotal(ProductDTO productDTO)
			throws DAOException {
		Long total = null;
//		List<ProductCommentEntity> productCommentEntityList = new ArrayList<ProductCommentEntity>();
		try {
			total = (Long)em.createQuery("select count(b) from ProductCommentEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",Long.class).setParameter("UUID", productDTO.getProductUuid())
					.setFirstResult(productDTO.getStartIndex()).setMaxResults(productDTO.getPageSize()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product comment: "+productDTO.getProductUuid());
		}
		return total == null?0:total.intValue();
	}

	@Override
	public List<ProductCommentEntity> searchProductComment(
			ProductCommentSearchDTO searchDTO,boolean isShowOnly) throws DAOException {
		List<ProductCommentEntity> productCommentEntityList = new ArrayList<ProductCommentEntity>();
		String query = "select b from ProductCommentEntity b where b.isDeleted =:isDeleted";
		String[] keyArray = searchDTO.getKeyArray();
		for(int i = 0;i < keyArray.length;i++){
			String key = keyArray[i];
			if(ProductCommentSearchDTO.KEY_PRODUCT_NAME.equals(key)){
				query = query +" and b.productEntity.productName like :PRODUCTNAME";
			}else if(ProductCommentSearchDTO.KEY_MERCHANT.equals(key)){
				query = query +" and b.productEntity.merchantEntity.merchantUuid = :MERCHANTUUID";
			}else if(ProductCommentSearchDTO.KEY_PRODUCT_UUID.equals(key)){
				query = query +" and b.productEntity.productUuid = :PRODUCTUUID";
			}else if(ProductCommentSearchDTO.KEY_SUPPLIER_NAME.equals(key)){
				query = query +" and b.orderEntity.supplierEntity.supplierName like :SUPPLIERNAME";
			}else if(ProductCommentSearchDTO.KEY_REPLIED.equals(key)){
				query = query +" and b.replayTime is not null";
			}else if(ProductCommentSearchDTO.KEY_NOT_REPLIED.equals(key)){
				query = query +" and b.replayTime is null";
			}else if(ProductCommentSearchDTO.KEY_DATE.equals(key)){
				query = query +" and date(b.evaluateTime)>=:DATESTART and date(b.evaluateTime)<=:DATEEND ";
			}
		}
		if(isShowOnly)
			query = query +" and b.isShow = 1";
		if(!StringUtils.isEmpty(searchDTO.getSortBy())) {
			query = query + " order by b."+searchDTO.getSortBy();
		}else {
			query = query + " order by b.createDate desc";
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, ProductCommentEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if(ProductCommentSearchDTO.KEY_PRODUCT_NAME.equals(key)){
					typedQuery.setParameter("PRODUCTNAME", "%"+searchDTO.getProductName()+"%");
				}else if(ProductCommentSearchDTO.KEY_MERCHANT.equals(key)){
					typedQuery.setParameter("MERCHANTUUID", searchDTO.getMerchantUuid());
				}else if(ProductCommentSearchDTO.KEY_PRODUCT_UUID.equals(key)){
					typedQuery.setParameter("PRODUCTUUID", searchDTO.getProductUuid());
				}else if(ProductCommentSearchDTO.KEY_SUPPLIER_NAME.equals(key)){
					typedQuery.setParameter("SUPPLIERNAME", "%"+searchDTO.getSupplierName()+"%");
				}else if(ProductCommentSearchDTO.KEY_DATE.equals(key)){
					typedQuery.setParameter("DATESTART", searchDTO.getDateStart())
					.setParameter("DATEEND", searchDTO.getDateEnd());
				}
			}
			productCommentEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(searchDTO.getStartIndex()).setMaxResults(searchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for ProductComment");
		}
		return productCommentEntityList;
	}

	@Override
	public int searchProductCommentTotal(ProductCommentSearchDTO searchDTO,boolean isShowOnly)
			throws DAOException {
		Long total = null;
		String query = "select count(b) from ProductCommentEntity b where b.isDeleted =:isDeleted";
		String[] keyArray = searchDTO.getKeyArray();
		for(int i = 0;i < keyArray.length;i++){
			String key = keyArray[i];
			if(ProductCommentSearchDTO.KEY_PRODUCT_NAME.equals(key)){
				query = query +" and b.productEntity.productName like :PRODUCTNAME";
			}else if(ProductCommentSearchDTO.KEY_MERCHANT.equals(key)){
				query = query +" and b.productEntity.merchantEntity.merchantUuid = :MERCHANTUUID";
			}else if(ProductCommentSearchDTO.KEY_PRODUCT_UUID.equals(key)){
				query = query +" and b.productEntity.productUuid = :PRODUCTUUID";
			}else if(ProductCommentSearchDTO.KEY_SUPPLIER_NAME.equals(key)){
				query = query +" and b.orderEntity.supplierEntity.supplierName like :SUPPLIERNAME";
			}else if(ProductCommentSearchDTO.KEY_REPLIED.equals(key)){
				query = query +" and b.replayTime is not null";
			}else if(ProductCommentSearchDTO.KEY_NOT_REPLIED.equals(key)){
				query = query +" and b.replayTime is null";
			}else if(ProductCommentSearchDTO.KEY_DATE.equals(key)){
				query = query +" and date(b.evaluateTime)>=:DATESTART and date(b.evaluateTime)<=:DATEEND ";
			}
		}
		if(isShowOnly)
			query = query +" and b.isShow = 1";
		if(!StringUtils.isEmpty(searchDTO.getSortBy())) {
			query = query + " order by b."+searchDTO.getSortBy();
		}else {
			query = query + " order by b.createDate desc";
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if(ProductCommentSearchDTO.KEY_PRODUCT_NAME.equals(key)){
					typedQuery.setParameter("PRODUCTNAME", "%"+searchDTO.getProductName()+"%");
				}else if(ProductCommentSearchDTO.KEY_MERCHANT.equals(key)){
					typedQuery.setParameter("MERCHANTUUID", searchDTO.getMerchantUuid());
				}else if(ProductCommentSearchDTO.KEY_PRODUCT_UUID.equals(key)){
					typedQuery.setParameter("PRODUCTUUID", searchDTO.getProductUuid());
				}else if(ProductCommentSearchDTO.KEY_SUPPLIER_NAME.equals(key)){
					typedQuery.setParameter("SUPPLIERNAME", "%"+searchDTO.getSupplierName()+"%");
				}else if(ProductCommentSearchDTO.KEY_DATE.equals(key)){
					typedQuery.setParameter("DATESTART", searchDTO.getDateStart())
					.setParameter("DATEEND", searchDTO.getDateEnd());
				}
			}
			total = (Long)typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for ProductComment");
		}
		return total == null?0:total.intValue();
	}
	
}
