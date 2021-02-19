package com.mb.ext.core.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.SupplierDAO;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.supplier.SupplierEntity;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.MerchantSearchDTO;
import com.mb.ext.core.service.spec.SupplierSearchDTO;
import com.mb.ext.core.service.spec.UserSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("supplierDAO")
@Qualifier("supplierDAO")
public class SupplierDAOImpl extends AbstractBaseDAO<SupplierEntity> implements SupplierDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public SupplierDAOImpl()
	{
		super();
		this.setEntityClass(SupplierEntity.class);
	}

	/**
	 * This method is used for inserting supplier information.
	 * 
	 * @param supplier
	 */
	@Override
	public void addSupplier(SupplierEntity supplierEntity) throws DAOException
	{
		save(supplierEntity);
	}

	@Override
	public void updateSupplier(SupplierEntity supplierEntity) throws DAOException {
		update(supplierEntity);
		
	}

	@Override
	public void deleteSupplier(SupplierEntity supplierEntity) throws DAOException {
		
		deletePhysical(supplierEntity);
		
	}
	
	@Override
	public void closeSupplier(SupplierEntity supplierEntity) throws DAOException {
		
		delete(supplierEntity);
		
	}
	
	@Override
	public SupplierEntity getSupplierByMobileNo(String mobileNo) throws DAOException {
		SupplierEntity supplierEntity = null;
		try {
			supplierEntity = (SupplierEntity)em.createQuery("select b from SupplierEntity b where  b.mobileNo = :MOBILENO and b.isDeleted=:isDeleted",SupplierEntity.class).setParameter("MOBILENO", mobileNo).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchcant: "+mobileNo);
		}
		return supplierEntity;
	}
	

	@Override
	public SupplierEntity getSupplierByUuid(String uuid) throws DAOException {
		SupplierEntity supplierEntity = null;
		try {
			supplierEntity = (SupplierEntity)em.createQuery("select b from SupplierEntity b where  b.supplierUuid = :UUID and b.isDeleted=:isDeleted",SupplierEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for supplier: "+uuid);
		}
		return supplierEntity;
	}

	@Override
	public List<SupplierEntity> getSuppliers() throws DAOException {
		List<SupplierEntity> supplierEntityList = new ArrayList<SupplierEntity>();;
		try {
			supplierEntityList = em.createQuery("select b from SupplierEntity b where  b.isDeleted=:isDeleted",SupplierEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for supplier");
		}
		return supplierEntityList;
	}

	@Override
	public SupplierEntity getSupplierByTokenId(String tokenId) throws DAOException {
		SupplierEntity supplierEntity = null;
		try {
			supplierEntity = (SupplierEntity)em.createQuery("select b from SupplierEntity b where  b.supplierTokenEntity.tokenId = :TOKENID and b.isDeleted=:isDeleted",SupplierEntity.class).setParameter("TOKENID", tokenId).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for supplier: "+tokenId);
		}
		return supplierEntity;
	}
	
	@Override
	public List<SupplierEntity> searchSuppliers(SupplierSearchDTO supplierSearchDTO) throws DAOException {
		List<SupplierEntity> supplierEntityList = new ArrayList<SupplierEntity>();
		String query = "select b from SupplierEntity b where b.isDeleted=:isDeleted and b.isClosed='0'";
		String[] keyArray = supplierSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (SupplierSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.mobileNo like :MOBILENO";
			} else if (SupplierSearchDTO.KEY_SUPPLIERADDRESS.equals(key)) {
				query = query + " and b.supplierAddress like :SUPPLIERADDRESS";
			} else if (SupplierSearchDTO.KEY_SUPPLIERNAME.equals(key)) {
				query = query + " and b.supplierName like :SUPPLIERNAME";
			} else if (UserSearchDTO.KEY_REGISTERDATE.equals(key)) {
				query = query
						+ " and b.createDate >= :REGISTERDATESTART and b.registerDate <= :REGISTERDATEEND";
			}
		}
		 query = query + " order by b.registerDate desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, SupplierEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (SupplierSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" + supplierSearchDTO.getMobileNo() + "%");
				} else if (SupplierSearchDTO.KEY_SUPPLIERADDRESS.equals(key)) {
					typedQuery.setParameter("SUPPLIERADDRESS",
							"%" + supplierSearchDTO.getSupplierAddress() + "%");
				} else if (SupplierSearchDTO.KEY_SUPPLIERNAME.equals(key)) {
					typedQuery.setParameter("SUPPLIERNAME",
							"%" + supplierSearchDTO.getSupplierName() + "%");
				} else if (UserSearchDTO.KEY_REGISTERDATE.equals(key)) {
					typedQuery.setParameter("REGISTERDATESTART",
							supplierSearchDTO.getRegisterDateStart());
					typedQuery.setParameter("REGISTERDATEEND",
							supplierSearchDTO.getRegisterDateEnd());
				}
			}
			supplierEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(supplierSearchDTO.getStartIndex()).setMaxResults(supplierSearchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return supplierEntityList;
	}
	
	@Override
	public int searchSupplierTotal(SupplierSearchDTO supplierSearchDTO) throws DAOException {
		Long total = Long.valueOf(0);
		String query = "select count(b) from SupplierEntity b where b.isDeleted=:isDeleted and b.isClosed='0'";
		String[] keyArray = supplierSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (SupplierSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.mobileNo like :MOBILENO";
			} else if (SupplierSearchDTO.KEY_SUPPLIERADDRESS.equals(key)) {
				query = query + " and b.supplierAddress like :SUPPLIERADDRESS";
			} else if (SupplierSearchDTO.KEY_SUPPLIERNAME.equals(key)) {
				query = query + " and b.supplierName like :SUPPLIERNAME";
			} else if (UserSearchDTO.KEY_REGISTERDATE.equals(key)) {
				query = query
						+ " and b.createDate >= :REGISTERDATESTART and b.registerDate <= :REGISTERDATEEND";
			}
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (SupplierSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" + supplierSearchDTO.getMobileNo() + "%");
				} else if (SupplierSearchDTO.KEY_SUPPLIERADDRESS.equals(key)) {
					typedQuery.setParameter("SUPPLIERADDRESS",
							"%" + supplierSearchDTO.getSupplierAddress() + "%");
				} else if (SupplierSearchDTO.KEY_SUPPLIERNAME.equals(key)) {
					typedQuery.setParameter("SUPPLIERNAME",
							"%" + supplierSearchDTO.getSupplierName() + "%");
				} else if (UserSearchDTO.KEY_REGISTERDATE.equals(key)) {
					typedQuery.setParameter("REGISTERDATESTART",
							supplierSearchDTO.getRegisterDateStart());
					typedQuery.setParameter("REGISTERDATEEND",
							supplierSearchDTO.getRegisterDateEnd());
				}
			}
			total = (Long) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total.intValue();
	}

	@Override
	public int getIncrementSupplierCountByDate(Date startDate, Date endDate)
			throws DAOException {
		int count = 0;
		try {
			count = em
					.createQuery(
							"select count(b.supplierUuid) from SupplierEntity b where date(b.registerDate) >= date(:STARTDATE) and date(b.registerDate) <= date(:ENDDATE) and b.isDeleted=:isDeleted",
							Long.class).setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult()
					.intValue();
			return count;
		} catch (NoResultException e) {
			logger.info("No record found for user: ");
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChartDTO> getIncrementSupplierChart(Date startDate, Date endDate)
			throws DAOException {
		List<ChartDTO> dtoList = new ArrayList<ChartDTO>();
		try {
			List<Object> resultList = new ArrayList<Object>();
			String sqlStr = "select t1.datelist, ifnull(t2.suppliercount,0) from summary_day as t1 left join (select register_date, count(supplier_uuid) as suppliercount from supplier where date(register_date)>=date(:STARTDATE) and date(register_date)<=date(:ENDDATE) group by date(register_date)) as t2 on date(t1.datelist) = date(t2.register_date) where date(t1.datelist)>=date(:STARTDATE) and date(t1.datelist)<=date(:ENDDATE) order by t1.datelist";
			resultList = em.createNativeQuery(sqlStr)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate).getResultList();
			for (int i = 0; i < resultList.size(); i++) {
				Object[] result = (Object[]) resultList.get(i);
				String dateStr = String.valueOf(result[0]);
				BigInteger supplierCount = (BigInteger) result[1];
				ChartDTO chartDTO = new ChartDTO();
				chartDTO.setValueDate(dateStr);
				chartDTO.setValueInt(supplierCount.intValue());
				dtoList.add(chartDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found for supplier: ");
		}
		return dtoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> searchSupplierSoldUnit(
			SupplierSearchDTO supplierSearchDTO) throws DAOException {
		List<Object> resultList = new ArrayList<Object>();
		try {
			String sqlStr = "";
			String[] keyArray = supplierSearchDTO.getKeyArray();
			for(int i=0;i<keyArray.length;i++){
				String key = keyArray[i];
				if(SupplierSearchDTO.KEY_RANKING_DATE.equals(key)){
					sqlStr = "select s.SUPPLIER_UUID,IFNULL(sum(o.PRODUCT_UNIT),0) as soldUnit from junengdb.supplier s left join junengdb.order o on s.SUPPLIER_UUID = o.SUPPLIER_UUID and o.ORDER_STATUS not in (3) and o.order_time>=:DATESTART and o.order_time<=:DATEEND group by s.SUPPLIER_UUID order by soldUnit "+supplierSearchDTO.getSorts();
					resultList = em.createNativeQuery(sqlStr).setParameter("DATESTART", supplierSearchDTO.getRankingDateStart()).setParameter("DATEEND", supplierSearchDTO.getRankingDateEnd()).setFirstResult(supplierSearchDTO.getStartIndex()).setMaxResults(supplierSearchDTO.getPageSize()).getResultList();
				}
			}
			if(keyArray.length == 0){
				sqlStr = "select s.SUPPLIER_UUID,IFNULL(sum(o.PRODUCT_UNIT),0) as soldUnit from junengdb.supplier s left join junengdb.order o on s.SUPPLIER_UUID = o.SUPPLIER_UUID and o.ORDER_STATUS not in (3) group by s.SUPPLIER_UUID order by soldUnit "+supplierSearchDTO.getSorts();
				resultList = em.createNativeQuery(sqlStr).setFirstResult(supplierSearchDTO.getStartIndex()).setMaxResults(supplierSearchDTO.getPageSize()).getResultList();
			}
		} catch (NoResultException e) {
			logger.info("No record found for supplier: ");
		}
		return resultList;
	}
}
