package com.mb.ext.core.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.MerchantDAO;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.MerchantSearchDTO;
import com.mb.ext.core.service.spec.UserSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("merchantDAO")
@Qualifier("merchantDAO")
public class MerchantDAOImpl extends AbstractBaseDAO<MerchantEntity> implements MerchantDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public MerchantDAOImpl()
	{
		super();
		this.setEntityClass(MerchantEntity.class);
	}

	/**
	 * This method is used for inserting merchant information.
	 * 
	 * @param merchantEntity
	 */
	@Override
	public void addMerchant(MerchantEntity merchantEntity) throws DAOException
	{
		save(merchantEntity);
	}

	@Override
	public void updateMerchant(MerchantEntity merchantEntity) throws DAOException {
		update(merchantEntity);
		
	}

	@Override
	public void deleteMerchant(MerchantEntity merchantEntity) throws DAOException {
		
		deletePhysical(merchantEntity);
		
	}
	
	@Override
	public void closeMerchant(MerchantEntity merchantEntity) throws DAOException {
		
		delete(merchantEntity);
		
	}
	
	@Override
	public MerchantEntity getMerchantByMobileNo(String mobileNo) throws DAOException {
		MerchantEntity merchantEntity = null;
		try {
			merchantEntity = (MerchantEntity)em.createQuery("select b from MerchantEntity b where  b.mobileNo = :MOBILENO and b.isDeleted=:isDeleted",MerchantEntity.class).setParameter("MOBILENO", mobileNo).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchcant: "+mobileNo);
		}
		return merchantEntity;
	}
	

	@Override
	public MerchantEntity getMerchantByUuid(String uuid) throws DAOException {
		MerchantEntity merchantEntity = null;
		try {
			merchantEntity = (MerchantEntity)em.createQuery("select b from MerchantEntity b where  b.merchantUuid = :UUID and b.isDeleted=:isDeleted",MerchantEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant: "+uuid);
		}
		return merchantEntity;
	}

	@Override
	public List<MerchantEntity> getMerchants() throws DAOException {
		List<MerchantEntity> merchantEntityList = new ArrayList<MerchantEntity>();;
		try {
			merchantEntityList = em.createQuery("select b from MerchantEntity b where  b.isDeleted=:isDeleted",MerchantEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant");
		}
		return merchantEntityList;
	}

	@Override
	public MerchantEntity getMerchantByTokenId(String tokenId) throws DAOException {
		MerchantEntity merchantEntity = null;
		try {
			merchantEntity = (MerchantEntity)em.createQuery("select b from MerchantEntity b where  b.merchantTokenEntity.tokenId = :TOKENID and b.isDeleted=:isDeleted",MerchantEntity.class).setParameter("TOKENID", tokenId).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant: "+tokenId);
		}
		return merchantEntity;
	}
	
	@Override
	public List<MerchantEntity> searchMerchants(MerchantSearchDTO merchantSearchDTO) throws DAOException {
		List<MerchantEntity> merchantEntityList = new ArrayList<MerchantEntity>();
		String query = "select b from MerchantEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = merchantSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (MerchantSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.mobileNo like :MOBILENO";
			} else if (MerchantSearchDTO.KEY_MERCHANTADDRESS.equals(key)) {
				query = query + " and b.merchantAddress like :MERCHANTADDRESS";
			} else if (MerchantSearchDTO.KEY_MERCHANTNAME.equals(key)) {
				query = query + " and b.merchantName like :MERCHANTNAME";
			} else if (MerchantSearchDTO.KEY_PROVINCE.equals(key)) {
				query = query + " and b.province = :PROVINCE";
			} else if (MerchantSearchDTO.KEY_CITY.equals(key)) {
				query = query + " and b.city = :CITY";
			} else if (MerchantSearchDTO.KEY_REFERRER.equals(key)) {
				query = query + " and b.referrer = :REFERRER";
			} else if (UserSearchDTO.KEY_REGISTERDATE.equals(key)) {
				query = query
						+ " and b.registerDate >= :REGISTERDATESTART and b.registerDate <= :REGISTERDATEEND";
			} else if (MerchantSearchDTO.KEY_HAS_COUPON.equals(key)) {
				query = query
						+ " and (exists (select c from CouponEntity c where c.isDeleted is false and c.isActive is true and c.merchantEntity.merchantUuid = b.merchantUuid))";
			}
		}
		//按距离远近排序
		if("distance".equals(merchantSearchDTO.getSortBy()))
			query = query + " order by (6371 * acos ( cos ( radians("+merchantSearchDTO.getLatitude()+") )  * cos( radians( b.latitude ) )  * cos( radians( b.longitude ) - radians("+merchantSearchDTO.getLongitude()+") )  + sin ( radians("+merchantSearchDTO.getLatitude()+") ) * sin( radians( b.latitude ) ) )) asc";
		//按评分高低排序
		else if("score".equals(merchantSearchDTO.getSortBy()))
			query = query + " order by b.score desc";
		//按销量排序
		else if("sold_unit".equals(merchantSearchDTO.getSortBy()))
			query = query + " order by b.soldUnit desc";
		//默认按权重排序
		else
			query = query + " order by b.weight desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, MerchantEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (MerchantSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" + merchantSearchDTO.getMobileNo() + "%");
				} else if (MerchantSearchDTO.KEY_MERCHANTADDRESS.equals(key)) {
					typedQuery.setParameter("MERCHANTADDRESS",
							"%" + merchantSearchDTO.getMerchantAddress() + "%");
				} else if (MerchantSearchDTO.KEY_REFERRER.equals(key)) {
					typedQuery.setParameter("REFERRER",
							merchantSearchDTO.getReferrer());
				} else if (MerchantSearchDTO.KEY_PROVINCE.equals(key)) {
					typedQuery.setParameter("PROVINCE",merchantSearchDTO.getProvince());
				} else if (MerchantSearchDTO.KEY_CITY.equals(key)) {
					typedQuery.setParameter("CITY",merchantSearchDTO.getCity());
				} else if (MerchantSearchDTO.KEY_MERCHANTNAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%" + merchantSearchDTO.getMerchantName() + "%");
				} else if (UserSearchDTO.KEY_REGISTERDATE.equals(key)) {
					typedQuery.setParameter("REGISTERDATESTART",
							merchantSearchDTO.getRegisterDateStart());
					typedQuery.setParameter("REGISTERDATEEND",
							merchantSearchDTO.getRegisterDateEnd());
				}
			}
			merchantEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(merchantSearchDTO.getStartIndex()).setMaxResults(merchantSearchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return merchantEntityList;
	}
	
	
	
	@Override
	public int searchMerchantTotal(MerchantSearchDTO merchantSearchDTO) throws DAOException {
		Long total = Long.valueOf(0);
		String query = "select count(b) from MerchantEntity b where b.isDeleted=:isDeleted and b.isClosed='0'";
		String[] keyArray = merchantSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (MerchantSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.mobileNo like :MOBILENO";
			} else if (MerchantSearchDTO.KEY_REFERRER.equals(key)) {
				query = query + " and b.referrer = :REFERRER";
			} else if (MerchantSearchDTO.KEY_MERCHANTADDRESS.equals(key)) {
				query = query + " and b.merchantAddress like :MERCHANTADDRESS";
			} else if (MerchantSearchDTO.KEY_MERCHANTNAME.equals(key)) {
				query = query + " and b.merchantName like :MERCHANTNAME";
			} else if (MerchantSearchDTO.KEY_PROVINCE.equals(key)) {
				query = query + " and b.province = :PROVINCE";
			} else if (MerchantSearchDTO.KEY_CITY.equals(key)) {
				query = query + " and b.city = :CITY";
			} else if (UserSearchDTO.KEY_REGISTERDATE.equals(key)) {
				query = query
						+ " and b.createDate >= :REGISTERDATESTART and b.registerDate <= :REGISTERDATEEND";
			} else if (MerchantSearchDTO.KEY_HAS_COUPON.equals(key)) {
				query = query
						+ " and (exists (select c from CouponEntity c where c.isDeleted is false and c.isActive is true and c.merchantEntity.merchantUuid = b.merchantUuid))";
			}
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (MerchantSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" + merchantSearchDTO.getMobileNo() + "%");
				} else if (MerchantSearchDTO.KEY_REFERRER.equals(key)) {
					typedQuery.setParameter("REFERRER",
							merchantSearchDTO.getReferrer());
				} else if (MerchantSearchDTO.KEY_MERCHANTADDRESS.equals(key)) {
					typedQuery.setParameter("MERCHANTADDRESS",
							"%" + merchantSearchDTO.getMerchantAddress() + "%");
				} else if (MerchantSearchDTO.KEY_MERCHANTNAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%" + merchantSearchDTO.getMerchantName() + "%");
				} else if (MerchantSearchDTO.KEY_PROVINCE.equals(key)) {
					typedQuery.setParameter("PROVINCE",merchantSearchDTO.getProvince());
				} else if (MerchantSearchDTO.KEY_CITY.equals(key)) {
					typedQuery.setParameter("CITY",merchantSearchDTO.getCity());
				} else if (UserSearchDTO.KEY_REGISTERDATE.equals(key)) {
					typedQuery.setParameter("REGISTERDATESTART",
							merchantSearchDTO.getRegisterDateStart());
					typedQuery.setParameter("REGISTERDATEEND",
							merchantSearchDTO.getRegisterDateEnd());
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
	public int getIncrementMerchantCountByDate(Date startDate, Date endDate)
			throws DAOException {
		int count = 0;
		try {
			count = em
					.createQuery(
							"select count(b.merchantUuid) from MerchantEntity b where date(b.registerDate) >= date(:STARTDATE) and date(b.registerDate) <= date(:ENDDATE) and b.isDeleted=:isDeleted",
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
	public List<ChartDTO> getIncrementMerchantChart(Date startDate, Date endDate)
			throws DAOException {
		List<ChartDTO> dtoList = new ArrayList<ChartDTO>();
		try {
			List<Object> resultList = new ArrayList<Object>();
			String sqlStr = "select t1.datelist, ifnull(t2.merchantcount,0) from summary_day as t1 left join (select register_date, count(merchant_uuid) as merchantcount from merchant where date(register_date)>=date(:STARTDATE) and date(register_date)<=date(:ENDDATE) group by date(register_date)) as t2 on date(t1.datelist) = date(t2.register_date) where date(t1.datelist)>=date(:STARTDATE) and date(t1.datelist)<=date(:ENDDATE) order by t1.datelist";
			resultList = em.createNativeQuery(sqlStr)
					.setParameter("STARTDATE", startDate)
					.setParameter("ENDDATE", endDate).getResultList();
			for (int i = 0; i < resultList.size(); i++) {
				Object[] result = (Object[]) resultList.get(i);
				String dateStr = String.valueOf(result[0]);
				BigInteger merchantCount = (BigInteger) result[1];
				ChartDTO chartDTO = new ChartDTO();
				chartDTO.setValueDate(dateStr);
				chartDTO.setValueInt(merchantCount.intValue());
				dtoList.add(chartDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found for merchant: ");
		}
		return dtoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MerchantDTO> getDistance(MerchantDTO merchantDTO) throws DAOException {
		// TODO Auto-generated method stub
		List<MerchantDTO> dtoList = new ArrayList<MerchantDTO>();
		
		try {
			List<Object> resultList = new ArrayList<Object>();
			String sqlStr = "select MERCHANT_UUID,MERCHANTNAME,MERCHANTADDRESS,CONTACTNAME,MOBILENO,LATITUDE,LONGITUDE,ROUND(6378.138*2*ASIN(SQRT(POW(SIN((:LATITUDE*PI()/180-latitude*PI()/180)/2),2)+COS(:LATITUDE*PI()/180)*COS(latitude*PI()/180)*POW(SIN((:LONGITUDE*PI()/180-longitude*PI()/180)/2),2)))*1000) AS distance FROM merchant where ISCLOSED = :ISCLOSED and IS_DELETED =:ISDELETED  having distance <= 10000 order by distance asc";
			resultList = em.createNativeQuery(sqlStr)
					.setParameter("LATITUDE", merchantDTO.getUserLatitude())
					.setParameter("LONGITUDE", merchantDTO.getUserLongitude())
					.setParameter("ISCLOSED", Boolean.FALSE)
					.setParameter("ISDELETED",Boolean.FALSE).getResultList();
			for (int i = 0; i < resultList.size(); i++) {
				Object[] result = (Object[]) resultList.get(i);
				String merchantUuid = String.valueOf(result[0]);
				String merchantName = String.valueOf(result[1]);
				String merchantAddress = String.valueOf(result[2]);
				String contactName = String.valueOf(result[3]);
				String mobileNo = String.valueOf(result[4]);
				Double latitude = Double.parseDouble(result[5].toString());
				Double longitude = Double.parseDouble(result[6].toString());
				Double distance = Double.parseDouble(result[7].toString())/1000;
				MerchantDTO merchantDTO1 = new MerchantDTO();
				merchantDTO1.setMerchantUuid(merchantUuid);
				merchantDTO1.setMerchantName(merchantName);
				merchantDTO1.setMerchantAddress(merchantAddress);
				merchantDTO1.setContactName(contactName);
				merchantDTO1.setMobileNo(mobileNo);
				merchantDTO1.setLatitude(latitude);
				merchantDTO1.setLongitude(longitude);
				merchantDTO1.setDistance(distance);
				dtoList.add(merchantDTO1);
				
			}
		} catch (NoResultException e) {
			logger.info("No record found for merchant: ");
		}
		return dtoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> searchMerchantRanking(
			MerchantSearchDTO merchantSearchDTO) throws DAOException {
		List<Object> resultList = new ArrayList<Object>();
		try {
			String sqlStr = "";
			String[] keyArray = merchantSearchDTO.getKeyArray();
			for(int i=0;i<keyArray.length;i++){
				String key = keyArray[i];
				if(MerchantSearchDTO.KEY_RANKING_DATE.equals(key)){
					sqlStr = "select a.MERCHANT_UUID,a.charge,b.assign from"+
							"(select b.MERCHANT_UUID,IFNULL(sum(CHARGE_AMOUNT),0) as charge from merchant b left join merchantcharge m on b.MERCHANT_UUID = m.MERCHANT_UUID and m.CHARGE_STATUS = '1' and date(m.charge_time)>=:DATESTART and date(m.charge_time)<=:DATEEND group by b.MERCHANT_UUID) as a,"+
							"(select b.MERCHANT_UUID,IFNULL(sum(assign_point),0) as assign from merchant b left join merchantassign m on b.MERCHANT_UUID = m.MERCHANT_UUID and date(m.assign_time)>=:DATESTART and date(m.assign_time)<=:DATEEND group by b.MERCHANT_UUID) as b "+
							"where a.MERCHANT_UUID = b.MERCHANT_UUID order by "+merchantSearchDTO.getSortBy()+" "+merchantSearchDTO.getSorts();
					resultList = em.createNativeQuery(sqlStr).setParameter("DATESTART", merchantSearchDTO.getRankingDateStart()).setParameter("DATEEND", merchantSearchDTO.getRankingDateEnd()).setFirstResult(merchantSearchDTO.getStartIndex()).setMaxResults(merchantSearchDTO.getPageSize()).getResultList();
				}
			}
			if(keyArray.length == 0){
				sqlStr = "select a.MERCHANT_UUID,a.charge,b.assign from"+
						"(select b.MERCHANT_UUID,IFNULL(sum(CHARGE_AMOUNT),0) as charge from merchant b left join merchantcharge m on b.MERCHANT_UUID = m.MERCHANT_UUID and m.CHARGE_STATUS = '1' group by b.MERCHANT_UUID) as a,"+
						"(select b.MERCHANT_UUID,IFNULL(sum(assign_point),0) as assign from merchant b left join merchantassign m on b.MERCHANT_UUID = m.MERCHANT_UUID group by b.MERCHANT_UUID) as b "+
						"where a.MERCHANT_UUID = b.MERCHANT_UUID order by "+merchantSearchDTO.getSortBy()+" "+merchantSearchDTO.getSorts();
				resultList = em.createNativeQuery(sqlStr).setFirstResult(merchantSearchDTO.getStartIndex()).setMaxResults(merchantSearchDTO.getPageSize()).getResultList();
			}
		} catch (NoResultException e) {
			logger.info("No record found for merchantassign: ");
		}
		return resultList;
	}
	
	@Override
	public void executeInsertUpdateNativeSQL(String sql) throws DAOException {
		try{
			em.createNativeQuery(sql).executeUpdate();
		}catch(Exception e){
			throw new DAOException(e);
		}
		
	}
}	
