package com.mb.ext.core.dao.impl.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.product.ProductDAO;
import com.mb.ext.core.entity.product.ProductBrandEntity;
import com.mb.ext.core.entity.product.ProductCateEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductGroupEntity;
import com.mb.ext.core.entity.supplier.SupplierEntity;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("productDAO")
@Qualifier("productDAO")
public class ProductDAOImpl extends AbstractBaseDAO<ProductEntity> implements
		ProductDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public ProductDAOImpl() {
		super();
		this.setEntityClass(ProductEntity.class);
	}


	@Override
	public void addProduct(ProductEntity productEntity) throws DAOException {
		
		save(productEntity);
		
	}


	@Override
	public void deleteProduct(ProductEntity productEntity) throws DAOException {
		
		delete(productEntity);
		
	}

	@Override
	public void updateProduct(ProductEntity productEntity) throws DAOException {
		
		update(productEntity);
		
	}

	@Override
	public ProductEntity getProductByUuid(String uuid)
			throws DAOException {
		ProductEntity productEntity = null;
		try {
			productEntity = (ProductEntity)em.createQuery("select b from ProductEntity b where  b.productUuid = :UUID",ProductEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product : "+uuid);
		}
		return productEntity;
	}
	
	@Override
	public List<ProductEntity> getProductByCate(ProductCateEntity cateEntity)
			throws DAOException {
		List<ProductEntity> productEntityList = new ArrayList<ProductEntity>();
		try {
			productEntityList = em.createQuery("select b from ProductEntity b where  b.productCateEntity.productCateUuid = :UUID and b.isDeleted=:isDeleted",ProductEntity.class).setParameter("UUID", cateEntity.getProductCateUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product : "+cateEntity.getProductCateUuid());
		}
		return productEntityList;
	}
	
	@Override
	public List<ProductEntity> getProductByType(String productType)
			throws DAOException {
		List<ProductEntity> productEntityList = new ArrayList<ProductEntity>();
		try {
			productEntityList = em.createQuery("select b from ProductEntity b where  b.productType = :PRODUCTTYPE and b.isDeleted=:isDeleted",ProductEntity.class).setParameter("PRODUCTTYPE", productType).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product : "+productType);
		}
		return productEntityList;
	}
	
	@Override
	public List<ProductEntity> getProductByGrup(ProductGroupEntity groupEntity)
			throws DAOException {
		List<ProductEntity> productEntityList = new ArrayList<ProductEntity>();
		try {
			productEntityList = em.createQuery("select b from ProductEntity b where  b.productGroupEntity.productGroupUuid = :UUID and b.isDeleted=:isDeleted",ProductEntity.class).setParameter("UUID", groupEntity.getProductGroupUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product : "+groupEntity.getProductGroupUuid());
		}
		return productEntityList;
	}


	@Override
	public List<ProductEntity> searchProduct(ProductSearchDTO productSearchDTO) throws DAOException {

		List<ProductEntity> productEntityList = new ArrayList<ProductEntity>();
		String query = "select b from ProductEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = productSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (ProductSearchDTO.KEY_PRODUCT_NAME.equals(key)) {
				query = query + " and b.productName like :PRODUCTNAME";
			}else if (ProductSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			}else if (ProductSearchDTO.KEY_PROVINCE.equals(key)) {
				query = query + " and b.merchantEntity.province = :PROVINCE";
			}else if (ProductSearchDTO.KEY_CITY.equals(key)) {
				query = query + " and b.merchantEntity.city= :CITY";
			}else if (ProductSearchDTO.KEY_PRODUCT_CATE.equals(key)) {
				query = query + " and (b.productCateEntity.productCateUuid = :PRODUCTCATEUUID or b.productCateEntity.parentCateEntity.productCateUuid = :PRODUCTCATEUUID)";
			} else if (ProductSearchDTO.KEY_SUPPLIER.equals(key)) {
				query = query + " and b.supplierEntity.supplierUuid = :SUPPLIERUUID";
			} else if (ProductSearchDTO.KEY_BRAND.equals(key)) {
				query = query + " and b.productBrandEntity.productBrandUuid = :PRODUCTBRANDUUID";
			} else if (ProductSearchDTO.KEY_HOT.equals(key)) {
				query = query + " and b.isHot = :ISHOT";
			} else if (ProductSearchDTO.KEY_NEW.equals(key)) {
				query = query + " and b.isNew = :ISNEW";
			} else if (ProductSearchDTO.KEY_RECOMMEND.equals(key)) {
				query = query + " and b.isRecommend = :ISRECOMMEND";
			} else if (ProductSearchDTO.KEY_ON_SALE.equals(key)) {
				query = query + " and b.isOnSale = :ISONSALE";
			} else if (ProductSearchDTO.KEY_INVENTORY.equals(key)) {
				query = query + " and b.totalUnit <= :INVENTORY";
			} else if (ProductSearchDTO.KEY_UNIT_POINT.equals(key)) {
				query = query + " and b.unitPoint >= :UNITPOINTSTART";
				query = query + " and b.unitPoint <= :UNITPOINTEND";
			} else if (ProductSearchDTO.KEY_CATE_NAME.equals(key)) {
				query = query + " and b.productCateEntity.parentCateEntity.cateName = :CATENAME";
			}
		}
		if(!StringUtils.isEmpty(productSearchDTO.getSortBy())) {
			query = query + " order by b."+productSearchDTO.getSortBy()+" "+productSearchDTO.getSort()+"";
		}else {
			query = query + " order by b.createDate desc";
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, ProductEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (ProductSearchDTO.KEY_PRODUCT_NAME.equals(key)) {
					typedQuery.setParameter("PRODUCTNAME",
							"%" + productSearchDTO.getProductName() + "%");
				} else if (ProductSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							productSearchDTO.getMerchantUuid());
				} else if (ProductSearchDTO.KEY_PROVINCE.equals(key)) {
					typedQuery.setParameter("PROVINCE",
							productSearchDTO.getProvince());
				} else if (ProductSearchDTO.KEY_CITY.equals(key)) {
					typedQuery.setParameter("CITY",
							productSearchDTO.getCity());
				} else if (ProductSearchDTO.KEY_PRODUCT_CATE.equals(key)) {
					typedQuery.setParameter("PRODUCTCATEUUID",
							productSearchDTO.getProductCateUuid());
				} else if (ProductSearchDTO.KEY_SUPPLIER.equals(key)) {
					typedQuery.setParameter("SUPPLIERUUID",
							productSearchDTO.getSupplierUuid());
				} else if (ProductSearchDTO.KEY_BRAND.equals(key)) {
					typedQuery.setParameter("PRODUCTBRANDUUID",
							productSearchDTO.getProductBrandUuid());
				} else if (ProductSearchDTO.KEY_HOT.equals(key)) {
					typedQuery.setParameter("ISHOT",
							productSearchDTO.isHot());
				} else if (ProductSearchDTO.KEY_NEW.equals(key)) {
					typedQuery.setParameter("ISNEW",productSearchDTO.isNew());
				} else if (ProductSearchDTO.KEY_RECOMMEND.equals(key)) {
					typedQuery.setParameter("ISRECOMMEND",productSearchDTO.isRecommend());
				} else if (ProductSearchDTO.KEY_ON_SALE.equals(key)) {
					typedQuery.setParameter("ISONSALE",productSearchDTO.isOnSale());
				} else if (ProductSearchDTO.KEY_INVENTORY.equals(key)) {
					typedQuery.setParameter("INVENTORY",productSearchDTO.getInventory());
				} else if (ProductSearchDTO.KEY_UNIT_POINT.equals(key)) {
					typedQuery.setParameter("UNITPOINTSTART",productSearchDTO.getUnitPointStart());
					typedQuery.setParameter("UNITPOINTEND",productSearchDTO.getUnitPointEnd());
				} else if (ProductSearchDTO.KEY_CATE_NAME.equals(key)) {
					typedQuery.setParameter("CATENAME",productSearchDTO.getCateName());
				}
			}
			productEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(productSearchDTO.getStartIndex()).setMaxResults(productSearchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product");
		}
		return productEntityList;
	
	}


	@Override
	public int searchProductTotal(ProductSearchDTO productSearchDTO) throws DAOException {

		Long total = null;
		String query = "select count(b) from ProductEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = productSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (ProductSearchDTO.KEY_PRODUCT_NAME.equals(key)) {
				query = query + " and b.productName like :PRODUCTNAME";
			} else if (ProductSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (ProductSearchDTO.KEY_PROVINCE.equals(key)) {
				query = query + " and b.merchantEntity.province = :PROVINCE";
			} else if (ProductSearchDTO.KEY_CITY.equals(key)) {
				query = query + " and b.merchantEntity.city= :CITY";
			} else if (ProductSearchDTO.KEY_PRODUCT_CATE.equals(key)) {
				query = query + " and (b.productCateEntity.productCateUuid = :PRODUCTCATEUUID or b.productCateEntity.parentCateEntity.productCateUuid = :PRODUCTCATEUUID)";
			} else if (ProductSearchDTO.KEY_SUPPLIER.equals(key)) {
				query = query + " and b.supplierEntity.supplierUuid = :SUPPLIERUUID";
			} else if (ProductSearchDTO.KEY_BRAND.equals(key)) {
				query = query + " and b.productBrandEntity.productBrandUuid = :PRODUCTBRANDUUID";
			} else if (ProductSearchDTO.KEY_HOT.equals(key)) {
				query = query + " and b.isHot = :ISHOT";
			} else if (ProductSearchDTO.KEY_NEW.equals(key)) {
				query = query + " and b.isNew = :ISNEW";
			} else if (ProductSearchDTO.KEY_RECOMMEND.equals(key)) {
				query = query + " and b.isRecommend = :ISRECOMMEND";
			} else if (ProductSearchDTO.KEY_ON_SALE.equals(key)) {
				query = query + " and b.isOnSale = :ISONSALE";
			} else if (ProductSearchDTO.KEY_INVENTORY.equals(key)) {
				query = query + " and b.totalUnit <= :INVENTORY";
			} else if (ProductSearchDTO.KEY_UNIT_POINT.equals(key)) {
				query = query + " and b.unitPoint >= :UNITPOINTSTART";
				query = query + " and b.unitPoint <= :UNITPOINTEND";
			} 
		}
		 query = query + " order by b.createDate desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (ProductSearchDTO.KEY_PRODUCT_NAME.equals(key)) {
					typedQuery.setParameter("PRODUCTNAME",
							"%" + productSearchDTO.getProductName() + "%");
				} else if (ProductSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							productSearchDTO.getMerchantUuid());
				} else if (ProductSearchDTO.KEY_PROVINCE.equals(key)) {
					typedQuery.setParameter("PROVINCE",
							productSearchDTO.getProvince());
				} else if (ProductSearchDTO.KEY_CITY.equals(key)) {
					typedQuery.setParameter("CITY",
							productSearchDTO.getCity());
				} else if (ProductSearchDTO.KEY_PRODUCT_CATE.equals(key)) {
					typedQuery.setParameter("PRODUCTCATEUUID",
							productSearchDTO.getProductCateUuid());
				} else if (ProductSearchDTO.KEY_SUPPLIER.equals(key)) {
					typedQuery.setParameter("SUPPLIERUUID",
							productSearchDTO.getSupplierUuid());
				} else if (ProductSearchDTO.KEY_BRAND.equals(key)) {
					typedQuery.setParameter("PRODUCTBRANDUUID",
							productSearchDTO.getProductBrandUuid());
				} else if (ProductSearchDTO.KEY_HOT.equals(key)) {
					typedQuery.setParameter("ISHOT",
							productSearchDTO.isHot());
				} else if (ProductSearchDTO.KEY_NEW.equals(key)) {
					typedQuery.setParameter("ISNEW",productSearchDTO.isNew());
				} else if (ProductSearchDTO.KEY_RECOMMEND.equals(key)) {
					typedQuery.setParameter("ISRECOMMEND",productSearchDTO.isRecommend());
				} else if (ProductSearchDTO.KEY_ON_SALE.equals(key)) {
					typedQuery.setParameter("ISONSALE",productSearchDTO.isOnSale());
				} else if (ProductSearchDTO.KEY_INVENTORY.equals(key)) {
					typedQuery.setParameter("INVENTORY",productSearchDTO.getInventory());
				} else if (ProductSearchDTO.KEY_UNIT_POINT.equals(key)) {
					typedQuery.setParameter("UNITPOINTSTART",productSearchDTO.getUnitPointStart());
					typedQuery.setParameter("UNITPOINTEND",productSearchDTO.getUnitPointEnd());
				}
			}
			total = (Long) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product");
		}
		return total == null?0:total.intValue();
	
	}


	@Override
	public int getSoldUnitTotalBySupplier(SupplierEntity supplierEntity)
			throws DAOException {
		Long total = null;
		try {
			total = em.createQuery("select sum(soldUnit) from ProductEntity b where b.supplierEntity.supplierUuid = :UUID and b.isDeleted = :isDeleted group by b.supplierEntity.supplierUuid",Long.class)
					.setParameter("UUID", supplierEntity.getSupplierUuid())
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : ");
		}
		return total==null?0:total.intValue();
	}


	@Override
	public void deleteProductByBrand(ProductBrandEntity productBrandEntity)
			throws DAOException {
		try {
			
			String updateSql = "update product set productbrand_uuid = null where productbrand_uuid = "+"'"+productBrandEntity.getProductBrandUuid()+"'";
			em.createNativeQuery(updateSql).executeUpdate();
			
		} catch (NoResultException e) {
			logger.info("No record found for product: " + productBrandEntity.getProductBrandUuid());
		}
		
	}
}
