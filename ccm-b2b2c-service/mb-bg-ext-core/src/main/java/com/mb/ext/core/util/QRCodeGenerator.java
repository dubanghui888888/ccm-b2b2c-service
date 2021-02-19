package com.mb.ext.core.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.mb.ext.core.constant.WechatConstants;
import com.mb.ext.msg.HttpClientHelper;
import com.mb.ext.msg.WXJSONAccessToken;

@Component
public class QRCodeGenerator {

	@Autowired
	private PaymentUtil paymentUtil;

	/**获取小程序带参数二维码
	 * @param page - 小程序页面路径
	 * @param scene - 小程序页面参数值
	 * @return
	 * @throws Exception
	 */
	public BufferedImage getWXAppCodImg(String page, String scene, int width) throws Exception {
		String token = getWXAAccessToken();

		String url = WechatConstants.URL_WXA_CODE;
		url = url.replace("ACCESS_TOKEN", token);

		String data = "{\"scene\": \"SCENE_PARAM\",\"page\": \"PAGE_PARAM\", \"width\": "+width+",\"auto_color\": false,\"line_color\": {\"r\":\"0\",\"g\":\"0\",\"b\":\"0\"}}";
		data=data.replace("SCENE_PARAM",scene);
		data=data.replace("PAGE_PARAM",page);
		BufferedImage img = getWxQRImage(url, data);

		return img;
	}

	private String getWXAAccessToken(){
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + paymentUtil.getWechatAppIdMiniProgram() + "&secret=" + paymentUtil.getWechatAppSecretMiniProgram();
		String r = HttpClientHelper.sendGet(url, null, "UTF-8");
		WXJSONAccessToken token = JSON.parseObject(r,WXJSONAccessToken.class);

		return token.getAccess_token();
	}

	static BufferedImage getWxQRImage(String urlParam, String json) {

		HttpURLConnection con = null;
		OutputStreamWriter osw = null;
		BufferedReader br = null;
		BufferedImage bi=null;
		try {
			URL url = new URL(urlParam);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			osw = new OutputStreamWriter(con.getOutputStream(), "utf-8");
			osw.write(json);
			osw.flush();


			con.connect();
			InputStream is = con.getInputStream();

			bi=ImageIO.read(is);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					osw = null;
					throw new RuntimeException(e);
				} finally {
					if (con != null) {
						con.disconnect();
						con = null;
					}
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				} finally {
					if (con != null) {
						con.disconnect();
						con = null;
					}
				}
			}
		}

		return bi;
	}

}
