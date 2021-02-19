package com.mb.ext.core.sybpay;

import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;

@Service
@Qualifier("AllinPayService")
public class SybPayServiceImpl extends AbstractService implements
		SybPayService<BodyDTO> {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	public static void main(String[] args) {
//		try {
//			String userId = new AllinPayServiceImpl().createMember("456784930243-43728948323-4yhfjdsfds-67e832yrih1");
//			System.out.print("userId: "+userId);
//			new AllinPayServiceImpl().applyBindAcct("456784930243-43728948323-4yhfjdsfds-67e832yrih1","set","weChatPublic","oUpF8uMuAJO_M2pxb1Q9zNjWeS6o");
//			new SybPayServiceImpl().applyBindAcct("456784930243-43728948323-4yhfjdsfds-67e832yrih1","set","aliPayService","oUpF8uMuAJO_M2pxb1Q9zNjWxeS6o");
			
//		} catch (BusinessException e) {
//			e.printStackTrace();
//		}
	}

	@Override
	public Map<String,String> payNet(String cusid,long trxamt, String reqsn, String paytype, String body, String remark, String acct,
			String authcode, String notify_url, String limit_pay, String idno, String truename, String asinfo)
			throws BusinessException {
		try {
			HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.SYB_APIURL+"/pay");
			http.init();
			TreeMap<String,String> params = new TreeMap<String,String>();
			params.put("orgid", SybConstants.SYB_ORGID);
			params.put("cusid", cusid);
			params.put("appid", SybConstants.SYB_APPID);
			params.put("version", "11");
			params.put("trxamt", String.valueOf(trxamt));
			params.put("reqsn", reqsn);
			params.put("paytype", paytype);
			params.put("randomstr", SybUtil.getValidatecode(8));
			params.put("body", body);
			params.put("remark", remark);
			params.put("acct", acct);
			params.put("authcode", authcode);
			params.put("notify_url", notify_url);
			params.put("limit_pay", limit_pay);
			params.put("idno", idno);
			params.put("truename", truename);
			params.put("asinfo", asinfo);
			params.put("sign", SybUtil.sign(params,SybConstants.SYB_APPKEY));
			byte[] bys = http.postParams(params, true);
			String result = new String(bys,"UTF-8");
			Map<String,String> map = handleResult(result);
			return map;
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.ALLINPAY_CREATE_MEMBER_ERROR, e);
		}
	}
	

	@Override
	public String payH5(String cusid, long trxamt, String reqsn, String body, String remark, String notify_url,
			String return_url) throws BusinessException {
		try {
			StringBuilder sb=new StringBuilder();
			TreeMap<String,String> params = new TreeMap<String,String>();
			params.put("cusid", cusid);
			params.put("appid", SybConstants.SYB_APPID);
			params.put("version", "12");
			params.put("trxamt", String.valueOf(trxamt));
			params.put("reqsn", reqsn);
			params.put("charset", "utf-8");
			params.put("returl", return_url);
			params.put("notify_url", notify_url);
			params.put("body", body);
			params.put("remark", remark);
			params.put("randomstr", SybUtil.getValidatecode(8));
			params.put("sign", SybUtil.sign(params,SybConstants.SYB_APPKEY));
			for (Map.Entry<String,String>entry:params.entrySet()){
				sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(),"UTF-8")).append("&");
			}
			return SybConstants.SYB_H5URL+"?" + sb.substring(0,sb.length()-1);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.ALLINPAY_CREATE_MEMBER_ERROR, e);
		}
	}
	
	protected Map<String,String> handleResult(String result) throws Exception{
		Map map = SybUtil.json2Obj(result, Map.class);
		if(map == null){
			throw new Exception("返回数据错误");
		}
		if("SUCCESS".equals(map.get("retcode"))){
			TreeMap tmap = new TreeMap();
			tmap.putAll(map);
			String sign = tmap.remove("sign").toString();
			String sign1 = SybUtil.sign(tmap,SybConstants.SYB_APPKEY);
			if(sign1.toLowerCase().equals(sign.toLowerCase())){
				return map;
			}else{
				throw new Exception("验证签名失败");
			}
			
		}else{
			throw new Exception(map.get("retmsg").toString());
		}
	}

}
