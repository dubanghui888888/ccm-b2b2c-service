package com.mb.ext.core.dao.impl.coupon;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.coupon.UserVoucherDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.coupon.UserVoucherEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.order.OrderEntity;
import com.mb.ext.core.service.spec.coupon.CouponSearchDTO;
import com.mb.ext.core.service.spec.coupon.VoucherSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("userVoucherDAO")
@Qualifier("userVoucherDAO")
public class UserVoucherDAOImpl extends AbstractBaseDAO<UserVoucherEntity> implements UserVoucherDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public UserVoucherDAOImpl()
	{
		super();
		this.setEntityClass(UserVoucherEntity.class);
	}

	@Override
	public void createUserVoucher(UserVoucherEntity userVoucherEntity) throws DAOException {
		this.save(userVoucherEntity);
		
	}

	@Override
	public void updateUserVoucher(UserVoucherEntity userVoucherEntity) throws DAOException {
		this.update(userVoucherEntity);
		
	}

	@Override
	public void deleteUserVoucher(UserVoucherEntity userVoucherEntity) throws DAOException {
		this.delete(userVoucherEntity);
		
	}

	@Override
	public List<UserVoucherEntity> getVoucherByUser(UserEntity userEntity) throws DAOException {
		List<UserVoucherEntity> userVoucherEntityList = new ArrayList<UserVoucherEntity>();
		try {
			userVoucherEntityList =  em
					.createQuery(
							"select b from UserVoucherEntity b where b.userEntity.userUuid = :USERUUID and b.isDeleted=:isDeleted",
							UserVoucherEntity.class)
					.setParameter("USERUUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for voucher: " + userEntity.getUserUuid());
		}
		return userVoucherEntityList;
	}
	
	@Override
	public List<UserVoucherEntity> getVoucherByUserMerchant(UserEntity userEntity, MerchantEntity merchantEntity) throws DAOException {
		List<UserVoucherEntity> voucherEntityList = new ArrayList<UserVoucherEntity>();
		try {
			voucherEntityList =  em
					.createQuery(
							"select b from UserVoucherEntity b where b.userEntity.userUuid = :USERUUID and b.merchantEntity.merchantUuid = :MERCHANTUUID and b.isDeleted=:isDeleted",
							UserVoucherEntity.class)
					.setParameter("USERUUID", userEntity.getUserUuid())
					.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for voucher: " + userEntity.getUserUuid());
		}
		return voucherEntityList;
	}
	
	@Override
	public List<UserVoucherEntity> getUserVoucherByOrder(OrderEntity orderEntity) throws DAOException {
		List<UserVoucherEntity> userVoucherEntityList = new ArrayList<UserVoucherEntity>();
		try {
			userVoucherEntityList =  em
					.createQuery(
							"select b from UserVoucherEntity b where b.orderEntity.orderUuid = :ORDERUUID and b.isDeleted=:isDeleted",
							UserVoucherEntity.class)
					.setParameter("ORDERUUID", orderEntity.getOrderUuid())
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for voucher: " + orderEntity.getOrderUuid());
		}
		return userVoucherEntityList;
	}
	
	@Override
	public UserVoucherEntity getUserVoucherByUuid(String uuid) throws DAOException {
		UserVoucherEntity userVoucherEntity = null;
		try {
			userVoucherEntity =  em
					.createQuery(
							"select b from UserVoucherEntity b where b.userVoucherUuid = :UUID and b.isDeleted=:isDeleted",
							UserVoucherEntity.class)
					.setParameter("UUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for voucher: " + uuid);
		}
		return userVoucherEntity;
	}
	
	@Override
	public UserVoucherEntity getUserVoucherByCode(String voucherCode) throws DAOException {
		UserVoucherEntity userVoucherEntity = null;
		try {
			userVoucherEntity =  em
					.createQuery(
							"select b from UserVoucherEntity b where b.voucherCode = :CODE",
							UserVoucherEntity.class)
					.setParameter("CODE", voucherCode)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for voucher: " + voucherCode);
		}
		return userVoucherEntity;
	}

	@Override
	public List<UserVoucherEntity> searchUserVoucher(VoucherSearchDTO voucherSearchDTO) throws DAOException {

		List<UserVoucherEntity> voucherEntityList = new ArrayList<UserVoucherEntity>();
		String query = "select b from UserVoucherEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = voucherSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (VoucherSearchDTO.KEY_VOUCHER_USER_NAME.equals(key)) {
				query = query + " and b.userEntity.name like :USER_NAME";
			} else if (VoucherSearchDTO.KEY_VOUCHER_USER_MOBILENO.equals(key)) {
				query = query + " and b.userEntity.personalPhone like :USER_MOBILENO";
			} else if (CouponSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.name like :NAME";
			} else if (VoucherSearchDTO.KEY_ORDER.equals(key)) {
				query = query + " and b.orderEntity.orderUuid= :ORDER_UUID";
			} else if (VoucherSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid= :USER_UUID";
			} else if (VoucherSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid= :MERCHANT_UUID";
			} else if (CouponSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (CouponSearchDTO.KEY_MERCHANT_MOBILE_NO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo = :MERCHANTMOBILENO";
			} else if (VoucherSearchDTO.KEY_VOUCHER_CODE.equals(key)) {
				query = query + " and b.voucherCode = :VOUCHERCODE";
			} else if (VoucherSearchDTO.KEY_VOUCHER_ISUSED.equals(key)) {
				query = query + " and b.isUsed = :IS_USED";
			} else if (VoucherSearchDTO.KEY_VOUCHER_EXPIRED.equals(key) && voucherSearchDTO.isExpired()) {
				query = query + " and date(b.validEndDate) < date(current_date())";
			} else if (VoucherSearchDTO.KEY_VOUCHER_EXPIRED.equals(key) && !voucherSearchDTO.isExpired()) {
				query = query + " and date(b.validEndDate) >= date(current_date())";
			} else if (VoucherSearchDTO.KEY_VOUCHER_STARTED.equals(key) && voucherSearchDTO.isStarted()) {
				query = query + " and date(b.validStartDate) <= date(current_date())";
			} else if (VoucherSearchDTO.KEY_VOUCHER_STARTED.equals(key) && !voucherSearchDTO.isStarted()) {
				query = query + " and date(b.validStartDate) > date(current_date())";
			}
		}
		query = query + " order by b.createDate desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, UserVoucherEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (VoucherSearchDTO.KEY_VOUCHER_USER_NAME.equals(key)) {
					typedQuery.setParameter("USER_NAME",
							"%" + voucherSearchDTO.getUserName() + "%");
				} else if (VoucherSearchDTO.KEY_VOUCHER_USER_MOBILENO.equals(key)) {
					typedQuery.setParameter("USER_MOBILENO",
							"%" + voucherSearchDTO.getUserMobileNo() + "%");
				} else if (VoucherSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							"%"+voucherSearchDTO.getName()+"%");
				} else if (VoucherSearchDTO.KEY_ORDER.equals(key)) {
					typedQuery.setParameter("ORDER_UUID",
							voucherSearchDTO.getOrderUuid());
				} else if (VoucherSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USER_UUID",
							voucherSearchDTO.getUserUuid());
				} else if (VoucherSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANT_UUID",
							voucherSearchDTO.getMerchantUuid());
				} else if (VoucherSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%"+voucherSearchDTO.getMerchantName()+"%");
				} else if (VoucherSearchDTO.KEY_MERCHANT_MOBILE_NO.equals(key)) {
					typedQuery.setParameter("MERCHANTMOBILENO",
							voucherSearchDTO.getMerchantMobileNo());
				} else if (VoucherSearchDTO.KEY_VOUCHER_CODE.equals(key)) {
					typedQuery.setParameter("VOUCHERCODE",
							voucherSearchDTO.getVoucherCode());
				} else if (VoucherSearchDTO.KEY_VOUCHER_ISUSED.equals(key)) {
					typedQuery.setParameter("IS_USED",
							voucherSearchDTO.isUsed());
				}
			}
			voucherEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(voucherSearchDTO.getStartIndex()).setMaxResults(voucherSearchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for voucher");
		}
		return voucherEntityList;
	
	}

	@Override
	public int searchUserVoucherTotal(VoucherSearchDTO voucherSearchDTO) throws DAOException {

		Long total = null;
		String query = "select count(b) from UserVoucherEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = voucherSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (VoucherSearchDTO.KEY_VOUCHER_USER_NAME.equals(key)) {
				query = query + " and b.userEntity.name like :USER_NAME";
			} else if (VoucherSearchDTO.KEY_VOUCHER_USER_MOBILENO.equals(key)) {
				query = query + " and b.userEntity.personalPhone like :USER_MOBILENO";
			} else if (CouponSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.name like :NAME";
			} else if (VoucherSearchDTO.KEY_ORDER.equals(key)) {
				query = query + " and b.orderEntity.orderUuid= :ORDER_UUID";
			} else if (VoucherSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid= :USER_UUID";
			} else if (VoucherSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid= :MERCHANT_UUID";
			} else if (VoucherSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (VoucherSearchDTO.KEY_MERCHANT_MOBILE_NO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo = :MERCHANTMOBILENO";
			} else if (VoucherSearchDTO.KEY_VOUCHER_CODE.equals(key)) {
				query = query + " and b.voucherCode = :VOUCHERCODE";
			} else if (VoucherSearchDTO.KEY_VOUCHER_ISUSED.equals(key)) {
				query = query + " and b.isUsed = :IS_USED";
			} else if (VoucherSearchDTO.KEY_VOUCHER_EXPIRED.equals(key) && voucherSearchDTO.isExpired()) {
				query = query + " and date(b.validEndDate) < date(current_date())";
			} else if (VoucherSearchDTO.KEY_VOUCHER_EXPIRED.equals(key) && !voucherSearchDTO.isExpired()) {
				query = query + " and date(b.validEndDate) >= date(current_date())";
			} else if (VoucherSearchDTO.KEY_VOUCHER_STARTED.equals(key) && voucherSearchDTO.isStarted()) {
				query = query + " and date(b.validStartDate) <= date(current_date())";
			} else if (VoucherSearchDTO.KEY_VOUCHER_STARTED.equals(key) && !voucherSearchDTO.isStarted()) {
				query = query + " and date(b.validStartDate) > date(current_date())";
			}
		}
		query = query + " order by b.createDate desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (VoucherSearchDTO.KEY_VOUCHER_USER_NAME.equals(key)) {
					typedQuery.setParameter("USER_NAME",
							"%" + voucherSearchDTO.getUserName() + "%");
				} else if (VoucherSearchDTO.KEY_VOUCHER_USER_MOBILENO.equals(key)) {
					typedQuery.setParameter("USER_MOBILENO",
							"%" + voucherSearchDTO.getUserMobileNo() + "%");
				} else if (VoucherSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							"%"+voucherSearchDTO.getName()+"%");
				} else if (VoucherSearchDTO.KEY_ORDER.equals(key)) {
					typedQuery.setParameter("ORDER_UUID",
							voucherSearchDTO.getOrderUuid());
				} else if (VoucherSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USER_UUID",
							voucherSearchDTO.getUserUuid());
				} else if (VoucherSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANT_UUID",
							voucherSearchDTO.getMerchantUuid());
				} else if (CouponSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%"+voucherSearchDTO.getMerchantName()+"%");
				} else if (CouponSearchDTO.KEY_MERCHANT_MOBILE_NO.equals(key)) {
					typedQuery.setParameter("MERCHANTMOBILENO",
							voucherSearchDTO.getMerchantMobileNo());
				} else if (VoucherSearchDTO.KEY_VOUCHER_CODE.equals(key)) {
					typedQuery.setParameter("VOUCHERCODE",
							voucherSearchDTO.getVoucherCode());
				} else if (VoucherSearchDTO.KEY_VOUCHER_ISUSED.equals(key)) {
					typedQuery.setParameter("IS_USED",
							voucherSearchDTO.isUsed());
				}
			}
			total = (Long)typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for voucher");
		}
		return total == null?0:total.intValue();
	
	}

}
