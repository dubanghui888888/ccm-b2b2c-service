package com.mb.ext.core.dao.impl.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.product.ProductBrandDAO;
import com.mb.ext.core.entity.product.ProductBrandEntity;
import com.mb.ext.core.entity.product.ProductCateEntity;
import com.mb.ext.core.service.spec.ProductBrandSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("productBrandDAO")
@Qualifier("productBrandDAO")
public class ProductBrandDAOImpl extends AbstractBaseDAO<ProductBrandEntity> implements ProductBrandDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public ProductBrandDAOImpl() {
		super();
		this.setEntityClass(ProductBrandEntity.class);
	}

	@Override
	public void createProductBrand(ProductBrandEntity productBrandEntity) throws DAOException {
		save(productBrandEntity);

	}

	@Override
	public void updateProductBrand(ProductBrandEntity productBrandEntity) throws DAOException {
		update(productBrandEntity);

	}

	@Override
	public void deleteProductBrand(ProductBrandEntity productBrandEntity) throws DAOException {
		deletePhysical(productBrandEntity);

	}

	@Override
	public List<ProductBrandEntity> getBrands() throws DAOException {
		List<ProductBrandEntity> brandEntityList = new ArrayList<ProductBrandEntity>();
		;
		try {
			brandEntityList = em.createQuery("select b from ProductBrandEntity b where  b.isDeleted=:isDeleted",
					ProductBrandEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return brandEntityList;
	}
	
	@Override
	public List<ProductBrandEntity> getProductBrandsByProductCate(ProductCateEntity productCateEntity) throws DAOException {
		List<ProductBrandEntity> brandEntityList = new ArrayList<ProductBrandEntity>();
		;
		try {
			brandEntityList = em.createQuery("select distinct(b.productBrandEntity) from ProductEntity b where b.productCateEntity.parentCateEntity.productCateUuid = :PRODCATEUUID and b.isDeleted=:isDeleted",
					ProductBrandEntity.class)
					.setParameter("PRODCATEUUID", productCateEntity.getProductCateUuid())
					.setParameter("isDeleted", Boolean.FALSE)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return brandEntityList;
	}

	@Override
	public ProductBrandEntity getBrandByUuid(String uuid) throws DAOException {
		ProductBrandEntity brandEntity = new ProductBrandEntity();
		try {
			brandEntity = (ProductBrandEntity) em.createQuery(
					"select b from ProductBrandEntity b where b.productBrandUuid = :UUID",
					ProductBrandEntity.class).setParameter("UUID", uuid)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return brandEntity;
	}

	@Override
	public List<ProductBrandEntity> searchBrand(ProductBrandSearchDTO searchDTO) throws DAOException {

		List<ProductBrandEntity> productBrandEntityList = new ArrayList<ProductBrandEntity>();
		String query = "select b from ProductBrandEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = searchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (ProductBrandSearchDTO.KEY_BRAND_NAME.equals(key)) {
				query = query + " and b.name like :BRANDNAME";
			}
		}
		query = query + " order by b.sortNumber desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, ProductBrandEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (ProductBrandSearchDTO.KEY_BRAND_NAME.equals(key)) {
					typedQuery.setParameter("BRANDNAME", "%" + searchDTO.getBrandName() + "%");
				}
			}
			productBrandEntityList = typedQuery.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(searchDTO.getStartIndex()).setMaxResults(searchDTO.getPageSize()).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product");
		}
		return productBrandEntityList;

	}

	@Override
	public int searchBrandTotal(ProductBrandSearchDTO searchDTO) throws DAOException {

		Long total = null;
		String query = "select count(b) from ProductBrandEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = searchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (ProductBrandSearchDTO.KEY_BRAND_NAME.equals(key)) {
				query = query + " and b.name like :BRANDNAME";
			}
		}
		query = query + " order by b.sortNumber desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (ProductBrandSearchDTO.KEY_BRAND_NAME.equals(key)) {
					typedQuery.setParameter("BRANDNAME", "%" + searchDTO.getBrandName() + "%");
				}
			}
			total = (Long) typedQuery.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product");
		}
		return total == null ? 0 : total.intValue();

	}

}
