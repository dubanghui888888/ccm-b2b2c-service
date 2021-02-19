package com.mb.ext.core.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.cookie.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.dao.MerchantDAO;
import com.mb.ext.core.dao.PrintSettingDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.order.OrderDAO;
import com.mb.ext.core.entity.PrintSettingEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.order.OrderEntity;
import com.mb.ext.core.entity.order.OrderProductEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.PrintService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.PrintSettingDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.util.PrintUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;

@Service
@Qualifier("PrintService")
public class PrintServiceImpl extends AbstractService implements PrintService<BodyDTO> {

	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("orderDAO")
	private OrderDAO orderDAO;
	
	@Autowired
	@Qualifier("merchantDAO")
	private MerchantDAO merchantDAO;
	
	@Autowired
	@Qualifier("printSettingDAO")
	private PrintSettingDAO printSettingDAO;

	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	@Override
	public void checkPrintSetting(MerchantDTO merchantDTO) throws BusinessException {
		
		try {
			PrintSettingEntity printSettingEntity = null;
			if(merchantDTO == null || StringUtils.isEmpty(merchantDTO.getMerchantUuid()))
				printSettingEntity = printSettingDAO.getPrintSetting();
			else {
				MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
				if(merchantEntity != null)
					printSettingEntity = printSettingDAO.getPrintSettingByMerchant(merchantEntity);
			}
			if(printSettingEntity == null) {
				throw new BusinessException(BusinessErrorCode.ORDER_PRINTER_NOT_SETTING);
			}
			String feieUser = printSettingEntity.getFeieUser();
			String feieUKey = printSettingEntity.getFeieUkey();
			String feieSn = printSettingEntity.getFeieSn();
			String feieKey = printSettingEntity.getFeieKey();
			if(StringUtils.isEmpty(feieUser) || StringUtils.isEmpty(feieUKey) || StringUtils.isEmpty(feieSn) || StringUtils.isEmpty(feieKey)) {
				throw new BusinessException(BusinessErrorCode.ORDER_PRINTER_NOT_SETTING);
			}
		} catch (DAOException e) {
			logger.error("检查打印设置时发生异常:"+e.getMessage());
			throw new BusinessException(e);
		} catch (BusinessException e) {
			throw e;
		}
		
	}
	
	@Override
	public PrintSettingDTO getPrintSetting(MerchantDTO merchantDTO) throws BusinessException {
		
		try {
			PrintSettingEntity printSettingEntity = null;
			if(merchantDTO == null || StringUtils.isEmpty(merchantDTO.getMerchantUuid()))
				printSettingEntity = printSettingDAO.getPrintSetting();
			else {
				MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
				if(merchantEntity != null)
					printSettingEntity = printSettingDAO.getPrintSettingByMerchant(merchantEntity);
			}
			if(printSettingEntity == null) {
				throw new BusinessException(BusinessErrorCode.ORDER_PRINTER_NOT_SETTING);
			}
			String feieUser = printSettingEntity.getFeieUser();
			String feieUKey = printSettingEntity.getFeieUkey();
			String feieSn = printSettingEntity.getFeieSn();
			String feieKey = printSettingEntity.getFeieKey();
			PrintSettingDTO printSettingDTO = new PrintSettingDTO();
			printSettingDTO.setPrintSettingUuid(printSettingEntity.getPrintSettingUuid());
			printSettingDTO.setFeieKey(feieKey);
			printSettingDTO.setFeieSn(feieSn);
			printSettingDTO.setFeieUkey(feieUKey);
			printSettingDTO.setFeieUser(feieUser);
			return printSettingDTO;
		} catch (DAOException e) {
			logger.error("检查打印设置时发生异常:"+e.getMessage());
			throw new BusinessException(e);
		} catch (BusinessException e) {
			throw e;
		}
		
	}
	
	@Override
	public void printOrder(String orderNo, PrintSettingDTO printSettingDTO) throws BusinessException {
		
		try {
			OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderNo);
			PrintSettingEntity printSettingEntity = printSettingDAO.getPrintSettingByUuid(printSettingDTO.getPrintSettingUuid());
			if(printSettingEntity == null) {
				throw new BusinessException(BusinessErrorCode.ORDER_PRINTER_NOT_SETTING);
			}
			String feieUser = printSettingEntity.getFeieUser();
			String feieUKey = printSettingEntity.getFeieUkey();
			String feieSn = printSettingEntity.getFeieSn();
			if(StringUtils.isEmpty(feieUser) || StringUtils.isEmpty(feieUKey) || StringUtils.isEmpty(feieSn)) {
				throw new BusinessException(BusinessErrorCode.ORDER_PRINTER_NOT_SETTING);
			}
			
			//构造飞鹅订单打印内容
			String content = buildOrderFeiePrintContent(orderEntity);
			//使用飞鹅云打印机打印订单
			PrintUtil.feiePrintMsg(feieUser, feieUKey, feieSn, content);
			
		} catch (DAOException e) {
			logger.error("打印订单"+orderNo+"时发生异常:"+e.getMessage());
			throw new BusinessException(e);
		} catch (BusinessException e) {
			throw e;
		}
		
	}

	private String buildOrderFeiePrintContent(OrderEntity orderEntity) {
		String orderNo = orderEntity.getOrderNo();
		String orderTime = DateUtils.formatDate(orderEntity.getOrderTime(),"yyyy-MM-dd HH:mm:ss");
		String deliveryName = orderEntity.getDeliveryName()==null?"":orderEntity.getDeliveryName();
		String deliveryContactNo = orderEntity.getDeliveryContactNo()==null?"":orderEntity.getDeliveryContactNo();
		String deliveryProvince = orderEntity.getDeliveryProvince()==null?"":orderEntity.getDeliveryProvince();
		String deliveryCity = orderEntity.getDeliveryCity()==null?"":orderEntity.getDeliveryCity();
		String deliveryArea = orderEntity.getDeliveryArea()==null?"":orderEntity.getDeliveryArea();
		String deliveryStreet = orderEntity.getDeliveryStreet()==null?"":orderEntity.getDeliveryStreet();
		String name = orderEntity.getUserEntity().getName()==null?"":orderEntity.getUserEntity().getName();
		String mobileNo = orderEntity.getUserEntity().getPersonalPhone();
		String merchantName = "";
		String merchantMobileNo = "";
		MerchantEntity merchantEntity = orderEntity.getMerchantEntity();
		if(merchantEntity != null) {
			merchantName = merchantEntity.getMerchantName();
			merchantMobileNo = merchantEntity.getMobileNo();
		}
		String content = "";
		content += "<CB>创创猫</CB><BR>";
		content += "订单号码："+orderNo+"<BR>";
		content += "订单时间："+orderTime+"<BR>";
		content += "商家名称："+merchantName+"<BR>";
		content += "商家电话："+merchantMobileNo+"<BR>";
		content += "客户姓名："+name+"<BR>";
		content += "客户手机号码："+mobileNo+"<BR>";
		
		List<Order> orderList = new ArrayList<>();
		List<OrderProductEntity> orderProductList =  orderEntity.getOrderProductList();
		for (Iterator<OrderProductEntity> iterator = orderProductList.iterator(); iterator.hasNext();) {
			OrderProductEntity orderProductEntity = (OrderProductEntity) iterator.next();
			String title = orderProductEntity.getProductName();
			if(!StringUtils.isEmpty(orderProductEntity.getProductSkuDesc()))
				title = title + "(" + orderProductEntity.getProductSkuDesc() + ")";
			String num = String.valueOf(orderProductEntity.getProductUnit());
			String price = String.valueOf(orderProductEntity.getProductUnitPrice());
			Order order = new Order(title,price,num);
			orderList.add(order);
		}
		content += getOrder(orderList, 14, 6, 3, 6);
		content += "--------------------------------<BR>";
		content += "<RIGHT>合计："+""+String.valueOf(orderEntity.getProductAmount().setScale(2))+"</RIGHT>";
		content += "<RIGHT>优惠："+"-"+String.valueOf(orderEntity.getDeductAmount().setScale(2))+"</RIGHT>";
		content += "<RIGHT>运费："+"+"+String.valueOf(orderEntity.getFreightAmount().setScale(2))+"</RIGHT>";
		content += "<RIGHT>实付金额："+"<BOLD>"+String.valueOf(orderEntity.getActualAmount().setScale(2))+"</BOLD>"+"</RIGHT>";
		content += "收货人姓名："+deliveryName+"<BR>";
		content += "收货人手机号码："+deliveryContactNo+"<BR>";
		content += "收货地址："+deliveryProvince+deliveryCity+deliveryArea+deliveryStreet+"<BR>";
		return content;
	}
	
	private String getOrder(List<Order> orderList, int b1, int b2, int b3, int b4) {
		String orderInfo = "";
		orderInfo += "商品           单价  数量 金额<BR>";
		orderInfo += "--------------------------------<BR>";
		for (int i = 0; i < orderList.size(); i++) {
			String title = orderList.get(i).getTitle();
			String price = orderList.get(i).getPrice();
			String num = orderList.get(i).getNum();
			String total = "" + Double.valueOf(price) * Integer.parseInt(num);
			price = addSpace(price, b2);
			num = addSpace(num, b3);
			total = addSpace(total, b4);
			String otherStr =  " " + price + num+ " " + total;

			int tl = 0;
			try {
				tl = title.getBytes("GBK").length;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			int spaceNum = (tl / b1 + 1) * b1 - tl;
			if (tl < b1) {
				for (int k = 0; k < spaceNum; k++) {
					title += " ";
				}
				title += otherStr;
			} else if (tl == b1) {
				title += otherStr;
			} else {
				List<String> list = null;
				if (isEn(title)) {
					list = getStrList(title, b1);
				} else {
					list = getStrList(title, b1 / 2);
				}
				String s0 = titleAddSpace(list.get(0));
				title = s0 + otherStr + "<BR>";// 添加 单价 数量 总额
				String s = "";
				for (int k = 1; k < list.size(); k++) {
					s += list.get(k);
				}
				try {
					s = getStringByEnter(b1, s);
				} catch (Exception e) {
					e.printStackTrace();
				}
				title += s;
			}
			orderInfo += title + "<BR>";
		}
		return orderInfo;
	}
	
	private String addSpace(String str, int size) {
		int len = str.length();
		if (len < size) {
			for (int i = 0; i < size - len; i++) {
				str += " ";
			}
		}
		return str;
	}
	
	private List<String> getStrList(String inputString, int length) {
		int size = inputString.length() / length;
		if (inputString.length() % length != 0) {
			size += 1;
		}
		return getStrList(inputString, length, size);
	}
	
	private String substring(String str, int f, int t) {
		if (f > str.length())
			return null;
		if (t > str.length()) {
			return str.substring(f, str.length());
		} else {
			return str.substring(f, t);
		}
	}

	private List<String> getStrList(String inputString, int length, int size) {
		List<String> list = new ArrayList<String>();
		for (int index = 0; index < size; index++) {
			String childStr = substring(inputString, index * length, (index + 1) * length);
			list.add(childStr);
		}
		return list;
	}
	
	private String titleAddSpace(String str) {
		int k=0;
		int b = 14;
		try {
			k = str.getBytes("GBK").length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < b-k; i++) {
			str += " ";
		}
		return str;
	}
	
	private Boolean isEn(String str) {
		Boolean b = false;
		try {
			b = str.getBytes("GBK").length == str.length();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return b;
	}
	
	private String getStringByEnter(int length, String string) throws Exception {
		for (int i = 1; i <= string.length(); i++) {
			if (string.substring(0, i).getBytes("GBK").length > length) {
				return string.substring(0, i - 1) + "<BR>" + getStringByEnter(length, string.substring(i - 1));
			}
		}
		return string;
	}
	
	
	public class Order{
		private String title;
		private String price;
		private String num;
		
		public Order() {
		}
		public Order(String title, String price, String num) {
			this.title = title;
			this.price = price;
			this.num = num;
		}
		@Override
		public String toString() {
			return "Order [title=" + title + ", price=" + price + ", num=" + num + "]";
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
	}
}
