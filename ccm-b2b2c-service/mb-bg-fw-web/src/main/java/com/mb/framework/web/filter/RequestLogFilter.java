package com.mb.framework.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.log.mask.DataMaskingUtil;
@WebFilter(urlPatterns = "/*", filterName = "RequestLogFilter")
public class RequestLogFilter implements Filter {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());
	
	private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
	        Arrays.asList("/alipayResponse")));
	
	private DataMaskingUtil dataMaskingUtil;

	/**
	 * 
	 * This method is not implemented
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	   
		ServletContext servletContext = filterConfig.getServletContext();
	    
		WebApplicationContext webApplicationContext = 
	            WebApplicationContextUtils.getWebApplicationContext(servletContext);

	    AutowireCapableBeanFactory autowireCapableBeanFactory =
	           webApplicationContext.getAutowireCapableBeanFactory();

	    autowireCapableBeanFactory.autowireBeanProperties(
				this, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);
		
		logger.trace("This method implementation not needed");
	}

	/**
	 * 
	 * This method is used to log the request and response body.
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain)

	throws IOException, ServletException {

		try {

			HttpServletRequest httpServletRequest = (HttpServletRequest) request;

			HttpServletResponse httpServletResponse = (HttpServletResponse) response;

			StringBuilder logMessage = new StringBuilder("[")
					.append(httpServletRequest.getRemoteHost()).append(" ")
					.append(httpServletRequest.getMethod()).append(" ")
					.append(httpServletRequest.getRequestURI()).append("]");

			logger.info(logMessage.toString());
			
			//排除掉不打印日志的请求
			if (isExcluded(httpServletRequest)) {
	            chain.doFilter(request, response);
	            return;
			}
			
			//上传文件不打印上传内容
			if(ServletFileUpload.isMultipartContent(httpServletRequest)){

				chain.doFilter(httpServletRequest, httpServletResponse);

			}
			//非上传文件打印请求参数
			else{
				RequestWrapper requestWrapper = new RequestWrapper(
						httpServletRequest);

				String requestBody = requestWrapper.getRequestBody();
				//打印请求参数
				logger.info("Request Message : "  + dataMaskingUtil.jsonMaskData(requestBody));

				chain.doFilter(requestWrapper, httpServletResponse);

				//返回参数在ResponseLogAspect打印
			}

		} catch (Exception e) {
			logger.error("Error occurred in logging the request and response",
					e);
		}

	}
	
	boolean isExcluded(HttpServletRequest request) {
        String path = request.getRequestURI();
        String extension = path.substring(path.lastIndexOf('/'));
        return ALLOWED_PATHS.contains(extension);
    }

	/**
	 * 
	 * This method is used for fetching the request method parameters.
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unused")
	private Map<String, String> getTypesafeRequestMap(HttpServletRequest request) {

		Map<String, String> typesafeRequestMap = new HashMap<String, String>();

		Enumeration<?> requestParamNames = request.getParameterNames();

		while (requestParamNames.hasMoreElements()) {

			String requestParamName = (String) requestParamNames.nextElement();

			String requestParamValue = request.getParameter(requestParamName);

			typesafeRequestMap.put(requestParamName, requestParamValue);

		}

		return typesafeRequestMap;

	}

	/**
	 * 
	 * This method is not implemented
	 */
	@Override
	public void destroy() {
		logger.trace("This method implementation not needed");
	}

	/**
	 * @return the dataMaskingUtil
	 */
	public final DataMaskingUtil getDataMaskingUtil() {
		return dataMaskingUtil;
	}

	/**
	 * @param dataMaskingUtil the dataMaskingUtil to set
	 */
	public final void setDataMaskingUtil(DataMaskingUtil dataMaskingUtil) {
		this.dataMaskingUtil = dataMaskingUtil;
	}

}