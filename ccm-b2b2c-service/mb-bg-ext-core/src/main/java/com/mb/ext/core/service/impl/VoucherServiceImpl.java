package com.mb.ext.core.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSSClient;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mb.ext.core.dao.FileDAO;
import com.mb.ext.core.dao.MerchantDAO;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.coupon.UserVoucherDAO;
import com.mb.ext.core.dao.order.OrderDAO;
import com.mb.ext.core.dao.product.ProductDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.coupon.UserVoucherEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.order.OrderEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.OSSService;
import com.mb.ext.core.service.VoucherService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.coupon.UserVoucherDTO;
import com.mb.ext.core.service.spec.coupon.VoucherSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.util.OSSUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;



@Service
@Qualifier("VoucherService")
public class VoucherServiceImpl extends AbstractService implements
		VoucherService<BodyDTO> {
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("fileDAO")
	private FileDAO fileDAO;
	
	@Autowired
	private OSSUtil ossUtil;

	@Autowired
	@Qualifier("sequenceDAO")
	private SequenceDAO sequenceDAO;
	
	@Autowired
	@Qualifier("orderDAO")
	private OrderDAO orderDAO;

	@Autowired
	@Qualifier("merchantDAO")
	private MerchantDAO merchantDAO;
	
	@Autowired
	@Qualifier("OSSService")
	private OSSService ossService;
	
	@Autowired
	@Qualifier("productDAO")
	private ProductDAO productDAO;
	
	@Autowired
	@Qualifier("userVoucherDAO")
	private UserVoucherDAO userVoucherDAO;
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	@Override
	public void deleteVoucher(String userVoucherUuid) throws BusinessException {

		try{
			UserVoucherEntity voucherEntity = userVoucherDAO.getUserVoucherByUuid(userVoucherUuid);
			userVoucherDAO.deleteUserVoucher(voucherEntity);
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	@Override
	public UserVoucherDTO getUserVoucherByUuid(String userVoucherUuid) throws BusinessException {

		try{
			UserVoucherEntity voucherEntity = userVoucherDAO.getUserVoucherByUuid(userVoucherUuid);
			UserVoucherDTO voucherDTO = new UserVoucherDTO(); 
			voucherEntity2DTO(voucherEntity, voucherDTO);
			return voucherDTO;
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}

	@Override
	public UserVoucherDTO getUserVoucherByCode(String voucherCode) throws BusinessException {

		try{
			UserVoucherEntity voucherEntity = userVoucherDAO.getUserVoucherByCode(voucherCode);
			UserVoucherDTO voucherDTO = new UserVoucherDTO(); 
			voucherEntity2DTO(voucherEntity, voucherDTO);
			return voucherDTO;
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	@Override
	public List<UserVoucherDTO> getVoucherByOrder(OrderDTO orderDTO) throws BusinessException {

		List<UserVoucherDTO> listDTO = new ArrayList<UserVoucherDTO>();
		try{
			OrderEntity orderEntity = null;
			if(!StringUtils.isEmpty(orderDTO.getOrderUuid())) {
				orderEntity = orderDAO.getOrderByUuid(orderDTO.getOrderUuid());
			}else if(!StringUtils.isEmpty(orderDTO.getOrderNo())) {
				orderEntity = orderDAO.getOrderByOrderNo(orderDTO.getOrderNo());
			}
			if(orderEntity != null) {
				List<UserVoucherEntity> entityList = userVoucherDAO.getUserVoucherByOrder(orderEntity);
				for(Iterator<UserVoucherEntity> iter = entityList.iterator();iter.hasNext();){
					UserVoucherEntity voucherEntity = iter.next();
					UserVoucherDTO voucherDTO = new UserVoucherDTO();
					voucherEntity2DTO(voucherEntity, voucherDTO);
					listDTO.add(voucherDTO);
				}
			}
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return listDTO;
	}

	@Override
	public List<UserVoucherDTO> searchUserVoucher(VoucherSearchDTO voucherSearchDTO) throws BusinessException {

		List<UserVoucherDTO> listDTO = new ArrayList<UserVoucherDTO>();
		try{
			List<UserVoucherEntity> entityList = userVoucherDAO.searchUserVoucher(voucherSearchDTO);
			for(Iterator<UserVoucherEntity> iter = entityList.iterator();iter.hasNext();){
				UserVoucherEntity voucherEntity = iter.next();
				UserVoucherDTO voucherDTO = new UserVoucherDTO();
				voucherEntity2DTO(voucherEntity, voucherDTO);
				listDTO.add(voucherDTO);
			}
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return listDTO;
	}

	@Override
	public int searchUserVoucherTotal(VoucherSearchDTO voucherSearchDTO) throws BusinessException {
		try{
			return userVoucherDAO.searchUserVoucherTotal(voucherSearchDTO);
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}

	@Override
	public void writeOffVoucher(UserVoucherDTO userVoucherDTO) throws BusinessException {
		try{
			UserVoucherEntity voucherEntity = userVoucherDAO.getUserVoucherByUuid(userVoucherDTO.getUserVoucherUuid());
			if(voucherEntity == null) {
				voucherEntity = userVoucherDAO.getUserVoucherByCode(userVoucherDTO.getVoucherCode());
			}
			if(voucherEntity.isUsed()) {
				throw new BusinessException(BusinessErrorCode.VOUCHER_USED);
			}else if(voucherEntity.getValidEndDate().before(new Date())) {
				throw new BusinessException(BusinessErrorCode.VOUCHER_EXPIRED);
			}else if(voucherEntity.getValidStartDate().after(new Date())) {
				throw new BusinessException(BusinessErrorCode.VOUCHER_NOT_STARTED);
			}
			MerchantDTO writeOffMerchantDTO = userVoucherDTO.getMerchantDTO();
			MerchantEntity writeOffMerchantEntity = merchantDAO.getMerchantByUuid(writeOffMerchantDTO.getMerchantUuid());
			MerchantEntity voucherMerchantEntity = voucherEntity.getMerchantEntity();
			if(!writeOffMerchantEntity.getMerchantUuid().equals(voucherMerchantEntity.getMerchantUuid())) {
				throw new BusinessException(BusinessErrorCode.VOUCHER_NOT_PERMITTED);
			}
			voucherEntity.setUsed(true);
			voucherEntity.setUseTime(new Date());
			userVoucherDAO.updateUserVoucher(voucherEntity);
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}catch(BusinessException e) {
			throw e;
		}
	}
	
	@Override
	public void extendVoucher(UserVoucherDTO userVoucherDTO) throws BusinessException {
		try{
			UserVoucherEntity voucherEntity = userVoucherDAO.getUserVoucherByUuid(userVoucherDTO.getUserVoucherUuid());
			MerchantDTO writeOffMerchantDTO = userVoucherDTO.getMerchantDTO();
			MerchantEntity writeOffMerchantEntity = merchantDAO.getMerchantByUuid(writeOffMerchantDTO.getMerchantUuid());
			MerchantEntity voucherMerchantEntity = voucherEntity.getMerchantEntity();
			if(!writeOffMerchantEntity.getMerchantUuid().equals(voucherMerchantEntity.getMerchantUuid())) {
				throw new BusinessException(BusinessErrorCode.VOUCHER_NOT_PERMITTED);
			}
			voucherEntity.setValidEndDate(userVoucherDTO.getNewValidEndDate());
			userVoucherDAO.updateUserVoucher(voucherEntity);
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}catch(BusinessException e) {
			throw e;
		}
	}

	@Override
	public String generateVoucherBarCode(String userVoucherUuid) throws BusinessException {

		InputStream is = null;
		ByteArrayOutputStream os = null;
		int width=400;
		int height=400;
		String format="png";
		Hashtable hints=new Hashtable();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.MARGIN, 2);
		try {
			BitMatrix bitMatrix=new MultiFormatWriter().encode(userVoucherUuid, BarcodeFormat.QR_CODE, width, height,hints);
			os = new ByteArrayOutputStream();  
			MatrixToImageWriter.writeToStream(bitMatrix, format, os);
			is = new ByteArrayInputStream(os.toByteArray());
			OSSClient client = new OSSClient(ossUtil.getOssEndPoint(), ossUtil.getOssAccessId(), ossUtil.getOssAccessKey()); 
            String key = "voucher/"+userVoucherUuid;
            client.putObject(ossUtil.getOssBucketName(), key, is);
            String url = ossService.getUrl(key);
            client.shutdown(); 
            return url;
		} catch (Exception e) {
			logger.error("生成电子卡券二维码码失败");
		}finally{
			try{
				if(is != null)
					is.close();
				if(os != null)
					os.close();
			}catch(Exception e){
				logger.error(e.getMessage());
			}
		}
		return null;
	
	}
	
	void voucherEntity2DTO(UserVoucherEntity voucherEntity, UserVoucherDTO voucherDTO) {
		voucherDTO.setUserVoucherUuid(voucherEntity.getUserVoucherUuid());
		voucherDTO.setMemo(voucherEntity.getMemo());
		voucherDTO.setName(voucherEntity.getName());
		voucherDTO.setReceiveTime(voucherEntity.getReceiveTime());
		voucherDTO.setUsed(voucherEntity.isUsed());
		voucherDTO.setUseTime(voucherEntity.getUseTime());
		voucherDTO.setValidStartDate(voucherEntity.getValidStartDate());
		voucherDTO.setValidEndDate(voucherEntity.getValidEndDate());
		voucherDTO.setVoucherCode(voucherEntity.getVoucherCode());
		voucherDTO.setQrCodeUrl(voucherEntity.getQrCodeUrl());
		voucherDTO.setBarCodeUrl(voucherEntity.getBarCodeUrl());
		UserEntity userEntity = voucherEntity.getUserEntity();
		if(userEntity != null) {
			UserDTO userDTO = new UserDTO();
			userDTO.setUserUuid(userEntity.getUserUuid());
			userDTO.setName(userEntity.getName());
			userDTO.setPersonalPhone(userEntity.getPersonalPhone());
			voucherDTO.setUserDTO(userDTO);
		}
		MerchantEntity merchantEntity = voucherEntity.getMerchantEntity();
		if(merchantEntity != null) {
			MerchantDTO merchantDTO = new MerchantDTO();
			merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
			merchantDTO.setMerchantName(merchantEntity.getMerchantName());
			voucherDTO.setMerchantDTO(merchantDTO);	
		}
		OrderEntity orderEntity = voucherEntity.getOrderEntity();
		if(orderEntity != null) {
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.setOrderUuid(orderEntity.getOrderUuid());
			orderDTO.setOrderNo(orderEntity.getOrderNo());
			voucherDTO.setOrderDTO(orderDTO);
		}
		ProductEntity productEntity = voucherEntity.getProductEntity();
		if(productEntity != null) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setProductUuid(productEntity.getProductUuid());
			productDTO.setProductName(productEntity.getProductName());
			voucherDTO.setProductDTO(productDTO);
		}
	}
	
}
