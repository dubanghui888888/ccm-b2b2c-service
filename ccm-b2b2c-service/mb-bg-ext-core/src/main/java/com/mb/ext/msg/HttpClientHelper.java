/** 
 * @Description:����http��������� 
 * @author: 
 * @time:2016��5��17�� ����3:25:32 
 */ 
package com.mb.ext.msg;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
  
//import org.apache.http.HttpResponse;  
//import org.apache.http.NameValuePair;  
//import org.apache.http.client.HttpClient;  
//import org.apache.http.client.entity.UrlEncodedFormEntity;  
//import org.apache.http.client.methods.HttpGet;  
//import org.apache.http.client.methods.HttpPost;  
//import org.apache.http.impl.client.DefaultHttpClient;  
//import org.apache.http.message.BasicNameValuePair;  

public class HttpClientHelper {  
    /** 
     * @Description:ʹ��HttpURLConnection����post���� 
     * @author: 
     * @time:2016��5��17�� ����3:26:07 
     */  
    public static String sendPost(String urlParam, Map<String, Object> params, String charset) {  
        StringBuffer resultBuffer = null;  
        // �����������  
        StringBuffer sbParams = new StringBuffer();  
        if (params != null && params.size() > 0) {  
            for (Entry<String, Object> e : params.entrySet()) {  
                sbParams.append(e.getKey());  
                sbParams.append("=");  
                sbParams.append(e.getValue());  
                sbParams.append("&");  
            }  
        }  
        HttpURLConnection con = null;  
        OutputStreamWriter osw = null;  
        BufferedReader br = null;  
        // ��������  
        try {  
            URL url = new URL(urlParam);  
            con = (HttpURLConnection) url.openConnection();  
            con.setRequestMethod("POST");  
            con.setDoOutput(true);  
            con.setDoInput(true);  
            con.setUseCaches(false);  
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
            if (sbParams != null && sbParams.length() > 0) {  
                osw = new OutputStreamWriter(con.getOutputStream(), charset);  
                osw.write(sbParams.substring(0, sbParams.length() - 1));  
                osw.flush();  
            }  
            // ��ȡ��������  
            resultBuffer = new StringBuffer();  
            int contentLength = Integer.parseInt(con.getHeaderField("Content-Length"));  
            if (contentLength > 0) {  
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));  
                String temp;  
                while ((temp = br.readLine()) != null) {  
                    resultBuffer.append(temp);  
                }  
            }  
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
  
        return resultBuffer.toString();  
    }  
  
    /** 
     * @Description:ʹ��URLConnection����post 
     * @author: 
     * @time:2016��5��17�� ����3:26:52 
     */  
    public static String sendPost2(String urlParam, Map<String, Object> params, String charset) {  
        StringBuffer resultBuffer = null;  
        // �����������  
        StringBuffer sbParams = new StringBuffer();  
        if (params != null && params.size() > 0) {  
            for (Entry<String, Object> e : params.entrySet()) {  
                sbParams.append(e.getKey());  
                sbParams.append("=");  
                sbParams.append(e.getValue());  
                sbParams.append("&");  
            }  
        }  
        URLConnection con = null;  
        OutputStreamWriter osw = null;  
        BufferedReader br = null;  
        try {  
            URL realUrl = new URL(urlParam);  
            // �򿪺�URL֮�������  
            con = realUrl.openConnection();  
            // ����ͨ�õ���������  
            con.setRequestProperty("accept", "*/*");  
            con.setRequestProperty("connection", "Keep-Alive");  
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
            con.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            // ����POST�������������������  
            con.setDoOutput(true);  
            con.setDoInput(true);  
            // ��ȡURLConnection�����Ӧ�������  
            osw = new OutputStreamWriter(con.getOutputStream(), charset);  
            if (sbParams != null && sbParams.length() > 0) {  
                // �����������  
                osw.write(sbParams.substring(0, sbParams.length() - 1));  
                // flush������Ļ���  
                osw.flush();  
            }  
            // ����BufferedReader����������ȡURL����Ӧ  
            resultBuffer = new StringBuffer();  
            int contentLength = Integer.parseInt(con.getHeaderField("Content-Length"));  
            if (contentLength > 0) {  
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));  
                String temp;  
                while ((temp = br.readLine()) != null) {  
                    resultBuffer.append(temp);  
                }  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        } finally {  
            if (osw != null) {  
                try {  
                    osw.close();  
                } catch (IOException e) {  
                    osw = null;  
                    throw new RuntimeException(e);  
                }  
            }  
            if (br != null) {  
                try {  
                    br.close();  
                } catch (IOException e) {  
                    br = null;  
                    throw new RuntimeException(e);  
                }  
            }  
        }  
        return resultBuffer.toString();  
    }  
  
    /**  
     * @Description:����get���󱣴������ļ�  
     * @author:  
     * @time:2016��5��17�� ����3:27:29  
     */  
    public static void sendGetAndSaveFile(String urlParam, Map<String, Object> params, String fileSavePath) {  
        // �����������  
        StringBuffer sbParams = new StringBuffer();  
        if (params != null && params.size() > 0) {  
            for (Entry<String, Object> entry : params.entrySet()) {  
                sbParams.append(entry.getKey());  
                sbParams.append("=");  
                sbParams.append(entry.getValue());  
                sbParams.append("&");  
            }  
        }  
        HttpURLConnection con = null;  
        BufferedReader br = null;  
        FileOutputStream os = null;  
        try {  
            URL url = null;  
            if (sbParams != null && sbParams.length() > 0) {  
                url = new URL(urlParam + "?" + sbParams.substring(0, sbParams.length() - 1));  
            } else {  
                url = new URL(urlParam);  
            }  
            con = (HttpURLConnection) url.openConnection();  
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
            con.connect();  
            InputStream is = con.getInputStream();  
            os = new FileOutputStream(fileSavePath);  
            byte buf[] = new byte[1024];  
            int count = 0;  
            while ((count = is.read(buf)) != -1) {  
                os.write(buf, 0, count);  
            }  
            os.flush();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        } finally {  
            if (os != null) {  
                try {  
                    os.close();  
                } catch (IOException e) {  
                    os = null;  
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
    }  
  
    /** 
     * @Description:ʹ��HttpURLConnection����get���� 
     * @author: 
     * @time:2016��5��17�� ����3:27:29 
     */  
    public static String sendGet(String urlParam, Map<String, Object> params, String charset) {  
        StringBuffer resultBuffer = null;  
        // �����������  
        StringBuffer sbParams = new StringBuffer();  
        if (params != null && params.size() > 0) {  
            for (Entry<String, Object> entry : params.entrySet()) {  
                sbParams.append(entry.getKey());  
                sbParams.append("=");  
                sbParams.append(entry.getValue());  
                sbParams.append("&");  
            }  
        }  
        HttpURLConnection con = null;  
        BufferedReader br = null;  
        try {  
            URL url = null;  
            if (sbParams != null && sbParams.length() > 0) {  
                url = new URL(urlParam + "?" + sbParams.substring(0, sbParams.length() - 1));  
            } else {  
                url = new URL(urlParam);  
            }  
            con = (HttpURLConnection) url.openConnection();  
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
            con.connect();  
            resultBuffer = new StringBuffer();  
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));  
            String temp;  
            while ((temp = br.readLine()) != null) {  
                resultBuffer.append(temp);  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        } finally {  
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
        return resultBuffer.toString();  
    }  
  
    /** 
     * @Description:ʹ��URLConnection����get���� 
     * @author: 
     * @time:2016��5��17�� ����3:27:58 
     */  
    public static String sendGet2(String urlParam, Map<String, Object> params, String charset) {  
        StringBuffer resultBuffer = null;  
        // �����������  
        StringBuffer sbParams = new StringBuffer();  
        if (params != null && params.size() > 0) {  
            for (Entry<String, Object> entry : params.entrySet()) {  
                sbParams.append(entry.getKey());  
                sbParams.append("=");  
                sbParams.append(entry.getValue());  
                sbParams.append("&");  
            }  
        }  
        BufferedReader br = null;  
        try {  
            URL url = null;  
            if (sbParams != null && sbParams.length() > 0) {  
                url = new URL(urlParam + "?" + sbParams.substring(0, sbParams.length() - 1));  
            } else {  
                url = new URL(urlParam);  
            }  
            URLConnection con = url.openConnection();  
            // ������������  
            con.setRequestProperty("accept", "*/*");  
            con.setRequestProperty("connection", "Keep-Alive");  
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
            con.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            // ��������  
            con.connect();  
            resultBuffer = new StringBuffer();  
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));  
            String temp;  
            while ((temp = br.readLine()) != null) {  
                resultBuffer.append(temp);  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        } finally {  
            if (br != null) {  
                try {  
                    br.close();  
                } catch (IOException e) {  
                    br = null;  
                    throw new RuntimeException(e);  
                }  
            }  
        }  
        return resultBuffer.toString();  
    }  
  
//    /**  
//     * @Description:ʹ��HttpClient����post����  
//     * @author:  
//     * @time:2016��5��17�� ����3:28:23  
//     */  
//    public static String httpClientPost(String urlParam, Map<String, Object> params, String charset) {  
//        StringBuffer resultBuffer = null;  
//        HttpClient client = new DefaultHttpClient();  
//        HttpPost httpPost = new HttpPost(urlParam);  
//        // �����������  
//        List<NameValuePair> list = new ArrayList<NameValuePair>();  
//        Iterator<Entry<String, Object>> iterator = params.entrySet().iterator();  
//        while (iterator.hasNext()) {  
//            Entry<String, Object> elem = iterator.next();  
//            list.add(new BasicNameValuePair(elem.getKey(), String.valueOf(elem.getValue())));  
//        }  
//        BufferedReader br = null;  
//        try {  
//            if (list.size() > 0) {  
//                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);  
//                httpPost.setEntity(entity);  
//            }  
//            HttpResponse response = client.execute(httpPost);  
//            // ��ȡ��������Ӧ���  
//            resultBuffer = new StringBuffer();  
//            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));  
//            String temp;  
//            while ((temp = br.readLine()) != null) {  
//                resultBuffer.append(temp);  
//            }  
//        } catch (Exception e) {  
//            throw new RuntimeException(e);  
//        } finally {  
//            if (br != null) {  
//                try {  
//                    br.close();  
//                } catch (IOException e) {  
//                    br = null;  
//                    throw new RuntimeException(e);  
//                }  
//            }  
//        }  
//        return resultBuffer.toString();  
//    }  
//  
//    /** 
//     * @Description:ʹ��HttpClient����get���� 
//     * @author: 
//     * @time:2016��5��17�� ����3:28:56 
//     */  
//    public static String httpClientGet(String urlParam, Map<String, Object> params, String charset) {  
//        StringBuffer resultBuffer = null;  
//        HttpClient client = new DefaultHttpClient();  
//        BufferedReader br = null;  
//        // �����������  
//        StringBuffer sbParams = new StringBuffer();  
//        if (params != null && params.size() > 0) {  
//            for (Entry<String, Object> entry : params.entrySet()) {  
//                sbParams.append(entry.getKey());  
//                sbParams.append("=");  
//                try {  
//                    sbParams.append(URLEncoder.encode(String.valueOf(entry.getValue()), charset));  
//                } catch (UnsupportedEncodingException e) {  
//                    throw new RuntimeException(e);  
//                }  
//                sbParams.append("&");  
//            }  
//        }  
//        if (sbParams != null && sbParams.length() > 0) {  
//            urlParam = urlParam + "?" + sbParams.substring(0, sbParams.length() - 1);  
//        }  
//        HttpGet httpGet = new HttpGet(urlParam);  
//        try {  
//            HttpResponse response = client.execute(httpGet);  
//            // ��ȡ��������Ӧ���  
//            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));  
//            String temp;  
//            resultBuffer = new StringBuffer();  
//            while ((temp = br.readLine()) != null) {  
//                resultBuffer.append(temp);  
//            }  
//        } catch (Exception e) {  
//            throw new RuntimeException(e);  
//        } finally {  
//            if (br != null) {  
//                try {  
//                    br.close();  
//                } catch (IOException e) {  
//                    br = null;  
//                    throw new RuntimeException(e);  
//                }  
//            }  
//        }  
//        return resultBuffer.toString();  
//    }  
//  
    /** 
     * @Description:ʹ��socket����post���� 
     * @author: 
     * @time:2016��5��18�� ����9:26:22 
     */  
    public static String sendSocketPost(String urlParam, Map<String, Object> params, String charset) {  
        String result = "";  
        // �����������  
        StringBuffer sbParams = new StringBuffer();  
        if (params != null && params.size() > 0) {  
            for (Entry<String, Object> entry : params.entrySet()) {  
                sbParams.append(entry.getKey());  
                sbParams.append("=");  
                sbParams.append(entry.getValue());  
                sbParams.append("&");  
            }  
        }  
        Socket socket = null;  
        OutputStreamWriter osw = null;  
        InputStream is = null;  
        try {  
            URL url = new URL(urlParam);  
            String host = url.getHost();  
            int port = url.getPort();  
            if (-1 == port) {  
                port = 80;  
            }  
            String path = url.getPath();  
            socket = new Socket(host, port);  
            StringBuffer sb = new StringBuffer();  
            sb.append("POST " + path + " HTTP/1.1\r\n");  
            sb.append("Host: " + host + "\r\n");  
            sb.append("Connection: Keep-Alive\r\n");  
            sb.append("Content-Type: application/x-www-form-urlencoded; charset=utf-8 \r\n");  
            sb.append("Content-Length: ").append(sb.toString().getBytes().length).append("\r\n");  
            // ����һ���س����У���ʾ��Ϣͷд�꣬��Ȼ�����������ȴ�  
            sb.append("\r\n");  
            if (sbParams != null && sbParams.length() > 0) {  
                sb.append(sbParams.substring(0, sbParams.length() - 1));  
            }  
            osw = new OutputStreamWriter(socket.getOutputStream());  
            osw.write(sb.toString());  
            osw.flush();  
            is = socket.getInputStream();  
            String line = null;  
            // ��������Ӧ����ݳ���  
            int contentLength = 0;  
            // ��ȡhttp��Ӧͷ����Ϣ  
            do {  
                line = readLine(is, 0, charset);  
                if (line.startsWith("Content-Length")) {  
                    // �õ���Ӧ�����ݳ���  
                    contentLength = Integer.parseInt(line.split(":")[1].trim());  
                }  
                // ���������һ�������Ļس����У����ʾ����ͷ����  
            } while (!line.equals("\r\n"));  
            // ��ȡ����Ӧ����ݣ�������Ҫ����ݣ�  
            result = readLine(is, contentLength, charset);  
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
                    if (socket != null) {  
                        try {  
                            socket.close();  
                        } catch (IOException e) {  
                            socket = null;  
                            throw new RuntimeException(e);  
                        }  
                    }  
                }  
            }  
            if (is != null) {  
                try {  
                    is.close();  
                } catch (IOException e) {  
                    is = null;  
                    throw new RuntimeException(e);  
                } finally {  
                    if (socket != null) {  
                        try {  
                            socket.close();  
                        } catch (IOException e) {  
                            socket = null;  
                            throw new RuntimeException(e);  
                        }  
                    }  
                }  
            }  
        }  
        return result;  
    }  
  
    /** 
     * @Description:ʹ��socket����get���� 
     * @author: 
     * @time:2016��5��18�� ����9:27:18 
     */  
    public static String sendSocketGet(String urlParam, Map<String, Object> params, String charset) {  
        String result = "";  
        // �����������  
        StringBuffer sbParams = new StringBuffer();  
        if (params != null && params.size() > 0) {  
            for (Entry<String, Object> entry : params.entrySet()) {  
                sbParams.append(entry.getKey());  
                sbParams.append("=");  
                sbParams.append(entry.getValue());  
                sbParams.append("&");  
            }  
        }  
        Socket socket = null;  
        OutputStreamWriter osw = null;  
        InputStream is = null;  
        try {  
            URL url = new URL(urlParam);  
            String host = url.getHost();  
            int port = url.getPort();  
            if (-1 == port) {  
                port = 80;  
            }  
            String path = url.getPath();  
            socket = new Socket(host, port);  
            StringBuffer sb = new StringBuffer();  
            sb.append("GET " + path + " HTTP/1.1\r\n");  
            sb.append("Host: " + host + "\r\n");  
            sb.append("Connection: Keep-Alive\r\n");  
            sb.append("Content-Type: application/x-www-form-urlencoded; charset=utf-8 \r\n");  
            sb.append("Content-Length: ").append(sb.toString().getBytes().length).append("\r\n");  
            // ����һ���س����У���ʾ��Ϣͷд�꣬��Ȼ�����������ȴ�  
            sb.append("\r\n");  
            if (sbParams != null && sbParams.length() > 0) {  
                sb.append(sbParams.substring(0, sbParams.length() - 1));  
            }  
            osw = new OutputStreamWriter(socket.getOutputStream());  
            osw.write(sb.toString());  
            osw.flush();  
            is = socket.getInputStream();  
            String line = null;  
            // ��������Ӧ����ݳ���  
            int contentLength = 0;  
            // ��ȡhttp��Ӧͷ����Ϣ  
            do {  
                line = readLine(is, 0, charset);  
                if (line.startsWith("Content-Length")) {  
                    // �õ���Ӧ�����ݳ���  
                    contentLength = Integer.parseInt(line.split(":")[1].trim());  
                }  
                // ���������һ�������Ļس����У����ʾ����ͷ����  
            } while (!line.equals("\r\n"));  
            // ��ȡ����Ӧ����ݣ�������Ҫ����ݣ�  
            result = readLine(is, contentLength, charset);  
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
                    if (socket != null) {  
                        try {  
                            socket.close();  
                        } catch (IOException e) {  
                            socket = null;  
                            throw new RuntimeException(e);  
                        }  
                    }  
                }  
            }  
            if (is != null) {  
                try {  
                    is.close();  
                } catch (IOException e) {  
                    is = null;  
                    throw new RuntimeException(e);  
                } finally {  
                    if (socket != null) {  
                        try {  
                            socket.close();  
                        } catch (IOException e) {  
                            socket = null;  
                            throw new RuntimeException(e);  
                        }  
                    }  
                }  
            }  
        }  
        return result;  
    }  
  
    /** 
     * @Description:��ȡһ����ݣ�contentLe���ݳ���Ϊ0ʱ����ȡ��Ӧͷ��Ϣ����Ϊ0ʱ������ 
     * @time:2016��5��17�� ����6:11:07 
     */  
    private static String readLine(InputStream is, int contentLength, String charset) throws IOException {  
        List<Byte> lineByte = new ArrayList<Byte>();  
        byte tempByte;  
        int cumsum = 0;  
        if (contentLength != 0) {  
            do {  
                tempByte = (byte) is.read();  
                lineByte.add(Byte.valueOf(tempByte));  
                cumsum++;  
            } while (cumsum < contentLength);// cumsum����contentLength��ʾ�Ѷ���  
        } else {  
            do {  
                tempByte = (byte) is.read();  
                lineByte.add(Byte.valueOf(tempByte));  
            } while (tempByte != 10);// ���з��ascii��ֵΪ10  
        }  
  
        byte[] resutlBytes = new byte[lineByte.size()];  
        for (int i = 0; i < lineByte.size(); i++) {  
            resutlBytes[i] = (lineByte.get(i)).byteValue();  
        }  
        return new String(resutlBytes, charset);  
    }  

//	public static String postJsonObject(String apiURL,String jsonObject) throws Exception{
//		URL url = new URL(apiURL);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setDoOutput(true);
//        connection.setDoInput(true);
//        connection.setRequestMethod("POST");
//        connection.setUseCaches(false);
//        connection.setInstanceFollowRedirects(true);
//        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//
//        connection.connect();
//
//        //POST����
//        DataOutputStream out = new DataOutputStream(  connection.getOutputStream());         
//     //   Encoding.UTF8.GetBytes
//        out.writeBytes( new String(jsonObject.getBytes(), "utf-8"));
//       
//        out.flush();
//        out.close();
//
//        //��ȡ��Ӧ
//        BufferedReader reader = new BufferedReader(new InputStreamReader(
//                connection.getInputStream()));
//        String lines;
//        StringBuffer sb = new StringBuffer("");
//        while ((lines = reader.readLine()) != null) {
//            lines = new String(lines.getBytes(), "utf-8");
//            sb.append(lines);
//        } 
//        reader.close();
//        // �Ͽ�����
//        connection.disconnect();
//		
//        return sb.toString();
//	}
//	
//	
	/**
	 * 
	 * @param urlParam
	 * @param jsonObject
	 * @return
	 */
	  public static String postJsonObject(String urlParam, String json) {  
	        StringBuffer resultBuffer = null;  
	     
	        HttpURLConnection con = null;  
	        OutputStreamWriter osw = null;  
	        BufferedReader br = null;  
	        // ��������  
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
	            // ��ȡ��������  
	            resultBuffer = new StringBuffer();  
	            int contentLength = Integer.parseInt(con.getHeaderField("Content-Length"));  
	            if (contentLength > 0) {  
	                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));  
	                String temp;  
	                while ((temp = br.readLine()) != null) {  
	                    resultBuffer.append(temp);  
	                }  
	            }  
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
	  
	        return resultBuffer.toString();  
	    }  
	  
	  public static BufferedImage getImageFromURL(String urlParam, Map<String, Object> params) {  
	        // �����������  
	        StringBuffer sbParams = new StringBuffer();  
	        if (params != null && params.size() > 0) {  
	            for (Entry<String, Object> entry : params.entrySet()) {  
	                sbParams.append(entry.getKey());  
	                sbParams.append("=");  
	                sbParams.append(entry.getValue());  
	                sbParams.append("&");  
	            }  
	        }  
	        HttpURLConnection con = null;  
	        BufferedReader br = null;  
	        FileOutputStream os = null;  
	        BufferedImage bi=null;
	        try {  
	            URL url = null;  
	            if (sbParams != null && sbParams.length() > 0) {  
	                url = new URL(urlParam + "?" + sbParams.substring(0, sbParams.length() - 1));  
	            } else {  
	                url = new URL(urlParam);  
	            }  
	            con = (HttpURLConnection) url.openConnection();  
	            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
	            con.connect();  
	            InputStream is = con.getInputStream();  
	            
	              bi=ImageIO.read(is);
	           
//	            os = new MemoryStream();//(fileSavePath);  
//	            byte buf[] = new byte[1024];  
//	            int count = 0;  
//	            while ((count = is.read(buf)) != -1) {  
//	                os.write(buf, 0, count);  
//	            }  
//	            os.flush();  
	        } catch (Exception e) {  
	            throw new RuntimeException(e);  
	        } finally {  
	            if (os != null) {  
	                try {  
	                    os.close();  
	                } catch (IOException e) {  
	                    os = null;  
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