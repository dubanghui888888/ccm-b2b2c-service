package com.mb.ext.core.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyun.oss.OSSClient;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.mb.ext.core.constant.LocationConstants;
import com.mb.ext.core.service.OSSService;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;

/**
 * 海报生成工具类
 * 
 * @author
 * 
 */
@Component
public class PosterUtil {
	
	@Autowired
	private OSSService ossService;
	
	@Autowired
	private OSSUtil ossUtil;

	private  LogHelper logger = LogHelper.getInstance(PosterUtil.class.getName());

	private static final int BLACK = 0xFF000000;
	
    private static final int WHITE = 0xFFFFFFFF;

	/**生成会员邀请码海报
	 * @param applicationName(应用名称)
	 * @param avatarUrl(会员头像)
	 * @param userName (会员姓名)
	 * @param qrCodeImage       (分享二维码图片)
	 * @return
	 */
	public String generateUserPoster(String applicationName,String avatarUrl, String userName,BufferedImage qrCodeImage) {
		try {

			String bgUrl = "https://ccmao-b2c.oss-cn-shenzhen.aliyuncs.com/invitation_bg.jpg";

			// 背景图
			Image bgSrc = Toolkit.getDefaultToolkit().getImage(new URL(bgUrl));
			BufferedImage bg = toBufferedImage(bgSrc);
//			BufferedImage bg = new BufferedImage(750, 1100, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bg.createGraphics();
//			Color bgColor = new Color(250,67,106,255);
//			g.setColor(bgColor);
//			g.fillRect(0, 0, 750, 1100);// 填充整个屏幕
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 消除画图锯齿
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // 消除文字锯齿
			int y = 300;

			// 写入商城名称
			g.setFont(new Font("Font.SERIF", Font.BOLD, 30));
			FontMetrics fm = g.getFontMetrics(g.getFont());
			int nameWidth = fm.stringWidth(applicationName);
			int nameWidthX = (750 - nameWidth) / 2;
			g.setColor(Color.ORANGE);
			g.drawString(applicationName, nameWidthX, y);
			y += 80;

			// 写入头像
			Image src = Toolkit.getDefaultToolkit().getImage(new URL(avatarUrl));
			BufferedImage avartaImg = toBufferedImage(src);
//			Rectangle rectangle = new Rectangle(275, y, 200, 200);
//			g.drawImage(avartaImg.getScaledInstance(rectangle.width, rectangle.height, Image.SCALE_SMOOTH), rectangle.x,
//					rectangle.y, null);
			//图片是一个圆型
			Ellipse2D.Double shape = new Ellipse2D.Double(300, y, 150, 150);
			//需要保留的区域
			g.setClip(shape);
			g.drawImage(avartaImg, 300, y, 150, 150, null);
			g.setClip(null);
			y += 200;

			// 写入会员姓名
			g.setFont(new Font("Font.SERIF", Font.PLAIN, 20));
			FontMetrics fm1 = g.getFontMetrics(g.getFont());
			String userNameX = "Hi, 我是"+userName;
			int textWidth = fm1.stringWidth(userNameX);
			int widthX = (750 - textWidth) / 2;
			g.setColor(Color.WHITE);
			g.drawString(userNameX, widthX, y);
			y += 50;

			// 写入会员描述
			g.setFont(new Font("Font.SERIF", Font.PLAIN, 25));
			FontMetrics fm2 = g.getFontMetrics(g.getFont());
			String desc = "注册会员, 立享优惠 ！";
			int descWidth = fm2.stringWidth(desc);
			int descWidthX = (750 - descWidth) / 2;
			g.setColor(Color.WHITE);
			g.drawString(desc, descWidthX, y);
			y += 110;

			// 写入二维码
//			BufferedImage qrcode = generateQrCode(qrCodeUrl,230,230);
			g.drawImage(qrCodeImage, 250, y, 250, 250, null);
			y += 280;

			g.setFont(new Font("Font.SERIF", Font.PLAIN, 22));
			g.setColor(Color.WHITE);
			g.drawString("长按识别二维码", 300, y);
			y += 25;


			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 消除画图锯齿
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // 消除文字锯齿

			g.dispose();

			String outputFileName = System.currentTimeMillis()+".jpg";
			File outputFile = new File(LocationConstants.TMP_PATH+"/"+outputFileName);
			ImageIO.write(bg, "jpg", outputFile);
			OSSClient client = new OSSClient(ossUtil.getOssEndPoint(), ossUtil.getOssAccessId(), ossUtil.getOssAccessKey());
			InputStream inputStream = new FileInputStream(outputFile);
			client.putObject(ossUtil.getOssBucketName(), outputFileName, inputStream);
			String url = ossService.getUrl(outputFileName);
			client.shutdown();
			return url;

		} catch (IOException e) {
			logger.error("生成会员邀请码失败", e);
		} catch (BusinessException e) {
			e.printStackTrace();
			logger.error("生成会员邀请码失败", e);
		}

		return null;
	}
	
	/**生成商品海报
	 * @param logoUrl(商城logo)
	 * @param productName(商品名称)
	 * @param unitPrice       (商品单价)
	 * @param productImageUrl (商品图片)
	 * @param qrCodeImage       (分享二维码链接)
	 * @return
	 */
	public String generateProductPoster(String logoUrl,String productName, BigDecimal unitPrice, BigDecimal unitPriceStandard, String productImageUrl,
			BufferedImage qrCodeImage) {
		try {
			String pngString = productImageUrl;
			String imageName = System.currentTimeMillis() + ".png";
			InputStream is = null;
			OutputStream os = null;
			try {
				URL url = new URL(productImageUrl);
				is = url.openStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				File imageFile = new File(LocationConstants.TMP_PATH + File.separator + imageName);
				os = new FileOutputStream(imageFile);
				if (!imageFile.exists()) {
					imageFile.mkdirs();
				}
				while ((len = is.read(buffer)) != -1) {
					os.write(buffer, 0, len);
				}
				os.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("生成商品海报图片时发生异常:" + e.getMessage());
			} finally {
				if (os != null)
					os.close();
				if (is != null)
					is.close();
			}

			// 平台
			BufferedImage platImage = ImageIO.read(new URL(logoUrl));

			// 透明底
			BufferedImage bg = new BufferedImage(750, 1100, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bg.createGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 750, 1100);// 填充整个屏幕
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 消除画图锯齿
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // 消除文字锯齿

			// 写入商品图
			Image src = Toolkit.getDefaultToolkit().getImage(new URL(productImageUrl));
			BufferedImage goodsImg = toBufferedImage(src);
			if (pngString.equals("png")) { // png单独处理
				String strImg =  LocationConstants.TMP_PATH + File.separator + imageName;
				goodsImg = ImageIO.read(new File(strImg));
			}
			int y = 10;
			Rectangle rectangle = new Rectangle(10, y, 730, 730);
			g.drawImage(goodsImg.getScaledInstance(rectangle.width, rectangle.height, Image.SCALE_SMOOTH), rectangle.x,
					rectangle.y, null);
			y += 735;

			// 优惠券背景
//			String couponPath = host + "/resources/images/invite/goodsDetailCoupon.png";
//			BufferedImage couponImg = ImageIO.read(new URL(couponPath));
//			g.drawImage(couponImg.getScaledInstance(730, 158, Image.SCALE_SMOOTH), 10, y + 10, 730, 158, null);
//			y += 75;
//
//			Double price = -1.0;
//			Double rebatePrice = 0.0;
//			try {
//				rebatePrice = (Double.valueOf(vo.getCommission()) * 0.33);
//				price = Double.valueOf(vo.getPrice()) - rebatePrice;
//				price = new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//				rebatePrice = new BigDecimal(rebatePrice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//			} catch (Exception e) {
//				logger.error("计算到手价失败", e);
//			}
//
//			String couponPrice = vo.getCouponPrice() == null ? "0" : vo.getCouponPrice();
//			String info = vo.getHasCoupon() ? "领" + couponPrice + "元券，下单返" + rebatePrice.toString() + "元"
//					: "下单返" + rebatePrice.toString() + "元";
//
//			g.setFont(new Font(Font.SERIF, Font.BOLD, 30));
//			g.setColor(new Color(255, 255, 255));
//			g.drawString(info, vo.getHasCoupon() ? 40 : 110, y + 15);
//
//			g.setFont(new Font(Font.SERIF, Font.PLAIN, 28));
//			g.setColor(new Color(255, 255, 255));
//			g.drawString("超值好物，买就返现", 90, y + 65);
//
//			String btnTitle = "长按获取";
//			g.setColor(new Color(255, 255, 255));
//			g.setFont(new Font(Font.SERIF, Font.PLAIN, 32));
//			g.drawString(btnTitle, 530, y + 25);
//
//			y += 88;

			// 写入二维码
//			BufferedImage qrcode = generateQrCode(qrCodeUrl,230,230);
			g.drawImage(qrCodeImage, 510, y + 5, 230, 230, null);

			g.setFont(new Font(Font.SERIF, Font.PLAIN, 22));
			g.setColor(Color.gray);
			g.drawString("长按识别二维码", 550, y + 240);
			y += 25;

			// 写入平台图片
			g.drawImage(platImage.getScaledInstance(52, 28, Image.SCALE_SMOOTH), 15, y + 7, 52, 28, null);
			y += 30;

			// 写入商品名
			g.setFont(new Font(Font.SERIF, Font.PLAIN, 32));
			g.setColor(Color.BLACK);
			String title = productName;
			char[] sc = title.toCharArray();
			int titleOffsetY = 0;
			String titleSub = "";
			int row = 0;
			int j = 0;
			for (int i = 0; i < title.length(); i++) {
				titleSub = title.substring(j, i);
				int titleSubW = g.getFontMetrics().charsWidth(sc, j, i - j);
				int len = row > 0 ? 450 : 400;
				if (titleSubW > len) {
					j = i;
					int ox = row > 0 ? 15 : 75;
					row++;
					if (row == 3 && j < title.length() - 1) {
						titleSub = titleSub.substring(0, titleSub.length() - 1) + "...";
					}
					g.drawString(titleSub, ox, y + titleOffsetY);

					if (row == 3) {
						break;
					} else {
						titleOffsetY += 40;
					}
				}
			}
			if (row == 0) {
				titleSub = title.substring(j, title.length());
				g.drawString(titleSub, 75, y + titleOffsetY);
			} else if (row == 1 || row == 2) {
				titleSub = title.substring(j, title.length());
				g.drawString(titleSub, 10, y + titleOffsetY);
			}
			y = y + titleOffsetY + 40;

			g.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
			g.setColor(Color.gray);
			g.drawString("商品价格以实际价格为准", 15, y);
			y += 30;

			// 到手价背景
			g.drawRect(15, y + 10, 90, 40);
			g.setColor(new Color(255, 0, 61));
			g.fillRect(15, y + 10, 90, 40);

			// 当前价
			g.setFont(new Font(Font.SERIF, Font.BOLD, 25));
			g.setColor(Color.white);
			g.drawString("到手价", 23, y + 38);

			// 当前价人民币图标
			g.setFont(new Font(Font.SERIF, Font.PLAIN, 36));
			g.setColor(Color.red);
			g.drawString("¥", 125, y + 45);

			// 当前价金额
			g.setFont(new Font(Font.SERIF, Font.BOLD, 60));
			g.setColor(Color.red);
			g.drawString(unitPrice.toString(), 145, y + 45);

			// 原价
			g.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
			g.setColor(new Color(89, 89, 89));
			g.drawString("原价: ", 350, y + 45);

			// 原价金额
			g.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
			g.setColor(new Color(89, 89, 89));
			g.drawString("¥" + unitPriceStandard, 415, y + 45);

			y += 110;

//			g.setFont(new Font(Font.SERIF, Font.PLAIN, 36));
//			g.setColor(Color.gray);
//			g.drawString("花得值", 110, y);
//
//			g.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
//			g.setColor(Color.gray);
//			g.drawString("—— 淘宝、天猫、京东...优惠专享APP", 225, y - 5);
//			y += 10;

			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 消除画图锯齿
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // 消除文字锯齿

			g.dispose();
			
			String outputFileName = System.currentTimeMillis()+".jpg";
			File outputFile = new File(LocationConstants.TMP_PATH+"/"+outputFileName);
			ImageIO.write(bg, "jpg", outputFile);
			OSSClient client = new OSSClient(ossUtil.getOssEndPoint(), ossUtil.getOssAccessId(), ossUtil.getOssAccessKey());
			InputStream inputStream = new FileInputStream(outputFile);
			client.putObject(ossUtil.getOssBucketName(), outputFileName, inputStream);
			String url = ossService.getUrl(outputFileName);
			client.shutdown();
			return url;
			
		} catch (IOException e) {
			logger.error("生成优惠券海报失败", e);
		} catch (BusinessException e) {
			e.printStackTrace();
			logger.error("生成优惠券海报失败", e);
		}
		
		return null;
	}
	
	/**生成商品海报
	 * @param logoUrl(商城logo)
	 * @param productName(商品名称)
	 * @param unitPrice       (商品单价)
	 * @param productImageUrl (商品图片)
	 * @param qrCodeUrl       (分享二维码链接地址)
	 * @return
	 */
	public String generateGroupPoster(String logoUrl,String productName, BigDecimal unitPrice, BigDecimal unitPriceStandard, String productImageUrl,
			String qrCodeUrl) {
		try {
			String pngString = productImageUrl;
			String imageName = System.currentTimeMillis() + ".png";
			InputStream is = null;
			OutputStream os = null;
			try {
				URL url = new URL(productImageUrl);
				is = url.openStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				File imageFile = new File(LocationConstants.TMP_PATH + File.separator + imageName);
				os = new FileOutputStream(imageFile);
				if (!imageFile.exists()) {
					imageFile.mkdirs();
				}
				while ((len = is.read(buffer)) != -1) {
					os.write(buffer, 0, len);
				}
				os.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("生成商品海报图片时发生异常:" + e.getMessage());
			} finally {
				if (os != null)
					os.close();
				if (is != null)
					is.close();
			}

			// 平台
			BufferedImage platImage = ImageIO.read(new URL(logoUrl));

			// 透明底
			BufferedImage bg = new BufferedImage(750, 1100, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bg.createGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 750, 1100);// 填充整个屏幕
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 消除画图锯齿
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // 消除文字锯齿

			// 写入商品图
			Image src = Toolkit.getDefaultToolkit().getImage(new URL(productImageUrl));
			BufferedImage goodsImg = toBufferedImage(src);
			if (pngString.equals("png")) { // png单独处理
				String strImg =  LocationConstants.TMP_PATH + File.separator + imageName;
				goodsImg = ImageIO.read(new File(strImg));
			}
			int y = 10;
			Rectangle rectangle = new Rectangle(10, y, 730, 730);
			g.drawImage(goodsImg.getScaledInstance(rectangle.width, rectangle.height, Image.SCALE_SMOOTH), rectangle.x,
					rectangle.y, null);
			y += 735;

			// 优惠券背景
//			String couponPath = host + "/resources/images/invite/goodsDetailCoupon.png";
//			BufferedImage couponImg = ImageIO.read(new URL(couponPath));
//			g.drawImage(couponImg.getScaledInstance(730, 158, Image.SCALE_SMOOTH), 10, y + 10, 730, 158, null);
//			y += 75;
//
//			Double price = -1.0;
//			Double rebatePrice = 0.0;
//			try {
//				rebatePrice = (Double.valueOf(vo.getCommission()) * 0.33);
//				price = Double.valueOf(vo.getPrice()) - rebatePrice;
//				price = new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//				rebatePrice = new BigDecimal(rebatePrice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//			} catch (Exception e) {
//				logger.error("计算到手价失败", e);
//			}
//
//			String couponPrice = vo.getCouponPrice() == null ? "0" : vo.getCouponPrice();
//			String info = vo.getHasCoupon() ? "领" + couponPrice + "元券，下单返" + rebatePrice.toString() + "元"
//					: "下单返" + rebatePrice.toString() + "元";
//
//			g.setFont(new Font(Font.SERIF, Font.BOLD, 30));
//			g.setColor(new Color(255, 255, 255));
//			g.drawString(info, vo.getHasCoupon() ? 40 : 110, y + 15);
//
//			g.setFont(new Font(Font.SERIF, Font.PLAIN, 28));
//			g.setColor(new Color(255, 255, 255));
//			g.drawString("超值好物，买就返现", 90, y + 65);
//
//			String btnTitle = "长按获取";
//			g.setColor(new Color(255, 255, 255));
//			g.setFont(new Font(Font.SERIF, Font.PLAIN, 32));
//			g.drawString(btnTitle, 530, y + 25);
//
//			y += 88;

			// 写入二维码
			BufferedImage qrcode = generateQrCode(qrCodeUrl,230,230);
			g.drawImage(qrcode, 510, y + 5, 230, 230, null);

			g.setFont(new Font(Font.SERIF, Font.PLAIN, 22));
			g.setColor(Color.gray);
			g.drawString("长按识别二维码", 550, y + 240);
			y += 25;

			// 写入平台图片
			g.drawImage(platImage.getScaledInstance(52, 28, Image.SCALE_SMOOTH), 15, y + 7, 52, 28, null);
			y += 30;

			// 写入商品名
			g.setFont(new Font(Font.SERIF, Font.PLAIN, 32));
			g.setColor(Color.BLACK);
			String title = productName;
			char[] sc = title.toCharArray();
			int titleOffsetY = 0;
			String titleSub = "";
			int row = 0;
			int j = 0;
			for (int i = 0; i < title.length(); i++) {
				titleSub = title.substring(j, i);
				int titleSubW = g.getFontMetrics().charsWidth(sc, j, i - j);
				int len = row > 0 ? 450 : 400;
				if (titleSubW > len) {
					j = i;
					int ox = row > 0 ? 15 : 75;
					row++;
					if (row == 3 && j < title.length() - 1) {
						titleSub = titleSub.substring(0, titleSub.length() - 1) + "...";
					}
					g.drawString(titleSub, ox, y + titleOffsetY);

					if (row == 3) {
						break;
					} else {
						titleOffsetY += 40;
					}
				}
			}
			if (row == 0) {
				titleSub = title.substring(j, title.length());
				g.drawString(titleSub, 75, y + titleOffsetY);
			} else if (row == 1 || row == 2) {
				titleSub = title.substring(j, title.length());
				g.drawString(titleSub, 10, y + titleOffsetY);
			}
			y = y + titleOffsetY + 40;

			g.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
			g.setColor(Color.gray);
			g.drawString("商品价格以实际价格为准", 15, y);
			y += 30;

			// 到手价背景
			g.drawRect(15, y + 10, 90, 40);
			g.setColor(new Color(255, 0, 61));
			g.fillRect(15, y + 10, 90, 40);

			// 当前价
			g.setFont(new Font(Font.SERIF, Font.BOLD, 25));
			g.setColor(Color.white);
			g.drawString("到手价", 23, y + 38);

			// 当前价人民币图标
			g.setFont(new Font(Font.SERIF, Font.PLAIN, 36));
			g.setColor(Color.red);
			g.drawString("¥", 125, y + 45);

			// 当前价金额
			g.setFont(new Font(Font.SERIF, Font.BOLD, 60));
			g.setColor(Color.red);
			g.drawString(unitPrice.toString(), 145, y + 45);

			// 原价
			g.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
			g.setColor(new Color(89, 89, 89));
			g.drawString("原价: ", 350, y + 45);

			// 原价金额
			g.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
			g.setColor(new Color(89, 89, 89));
			g.drawString("¥" + unitPriceStandard, 415, y + 45);

			y += 110;

//			g.setFont(new Font(Font.SERIF, Font.PLAIN, 36));
//			g.setColor(Color.gray);
//			g.drawString("花得值", 110, y);
//
//			g.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
//			g.setColor(Color.gray);
//			g.drawString("—— 淘宝、天猫、京东...优惠专享APP", 225, y - 5);
//			y += 10;

			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 消除画图锯齿
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // 消除文字锯齿

			g.dispose();
			
			String outputFileName = System.currentTimeMillis()+".jpg";
			File outputFile = new File(LocationConstants.TMP_PATH+"/"+outputFileName);
			ImageIO.write(bg, "jpg", outputFile);
			OSSClient client = new OSSClient(ossUtil.getOssEndPoint(), ossUtil.getOssAccessId(), ossUtil.getOssAccessKey());
			InputStream inputStream = new FileInputStream(outputFile);
			client.putObject(ossUtil.getOssBucketName(), outputFileName, inputStream);
			String url = ossService.getUrl(outputFileName);
			client.shutdown();
			return url;
			
		} catch (IOException e) {
			logger.error("生成优惠券海报失败", e);
		} catch (BusinessException e) {
			e.printStackTrace();
			logger.error("生成优惠券海报失败", e);
		}
		
		return null;
	}

	public static BufferedImage toBufferedImage(Image image) {
	    if (image instanceof BufferedImage) {
	        return (BufferedImage)image;
	     }
	 
	    // This code ensures that all the pixels in the image are loaded
	     image = new ImageIcon(image).getImage();
	 
	    // Determine if the image has transparent pixels; for this method's
	    // implementation, see e661 Determining If an Image Has Transparent Pixels
	    //boolean hasAlpha = hasAlpha(image);
	 
	    // Create a buffered image with a format that's compatible with the screen
	     BufferedImage bimage = null;
	     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    try {
	        // Determine the type of transparency of the new buffered image
	        int transparency = Transparency.OPAQUE;
	       /* if (hasAlpha) {
	         transparency = Transparency.BITMASK;
	         }*/
	 
	        // Create the buffered image
	         GraphicsDevice gs = ge.getDefaultScreenDevice();
	         GraphicsConfiguration gc = gs.getDefaultConfiguration();
	         bimage = gc.createCompatibleImage(
	         image.getWidth(null), image.getHeight(null), transparency);
	     } catch (HeadlessException e) {
	        // The system does not have a screen
	     }
	 
	    if (bimage == null) {
	        // Create a buffered image using the default color model
	        int type = BufferedImage.TYPE_INT_RGB;
	        //int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
	        /*if (hasAlpha) {
	         type = BufferedImage.TYPE_INT_ARGB;
	         }*/
	         bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
	     }
	 
	    // Copy image to buffered image
	     Graphics g = bimage.createGraphics();
	 
	    // Paint the image onto the buffered image
	     g.drawImage(image, 0, 0, null);
	     g.dispose();
	 
	    return bimage;
	}
	
	public BufferedImage generateQrCode(String str, int q, int h) {
		BitMatrix byteMatrix;
		try {
			byteMatrix = new MultiFormatWriter().encode(new String(str.getBytes(), "iso-8859-1"),
			        BarcodeFormat.QR_CODE, q, h);
	        int width = byteMatrix.getWidth();
	        int height = byteMatrix.getHeight();
	        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	        for (int x = 0; x < width; x++) {
	            for (int y = 0; y < height; y++) {
	                image.setRGB(x, y, byteMatrix.get(x, y) ? BLACK : WHITE);
	            }
	        }
	        return image;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("生成二维码时发生异常:" + e.getMessage());
		} catch (WriterException e) {
			e.printStackTrace();
			logger.error("生成二维码时发生异常:" + e.getMessage());
		}
		return null;
	}
	
	public static void main(String[] args) {
		PosterUtil posterUtil = new PosterUtil();
		BufferedImage image = posterUtil.generateQrCode("http://www.baidu.com", 230, 230);
		posterUtil.generateUserPoster("创创猫B2B2C商城","https://wx.qlogo.cn/mmopen/vi_32/icSjAllEOxCsmSV4mGkic1PM6PgBVDsh8Xe96sq1WffmlduX1v7hrzBr46Dg8YWljdUKQdZH7cKBxEqq663IibbCg/132","敬兴悦二店",image);
	}

}
