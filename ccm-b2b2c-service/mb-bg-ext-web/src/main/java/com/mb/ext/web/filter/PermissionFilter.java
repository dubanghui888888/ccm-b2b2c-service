package com.mb.ext.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.MessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mb.ext.core.constant.AuthenticationConstants;
import com.mb.ext.core.service.AuthenticationService;
import com.mb.framework.util.log.LogHelper;

public class PermissionFilter extends OncePerRequestFilter
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * 
	 * This method is used for filter request
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
	{
		
		ObjectMapper mapper = new ObjectMapper();
		WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
//		WebApplicationContext ac = (WebApplicationContext)getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		AuthenticationService authenticationService = (AuthenticationService) ac.getBean("AuthenticationService");
		MessageSource messageSource = (MessageSource) ac.getBean("messageSource");
		String tokenId = request.getHeader(AuthenticationConstants.TOKEN_ID);
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String pathInfo = uri.substring(uri.indexOf(contextPath) + contextPath.length());
		
		
		//Login, Logout, Reset Password not required to check the permission
		if(pathInfo.contains("login")||pathInfo.contains("resetPassword")){
			//no need to do anything
		}
		
		try
		{
			filterChain.doFilter(request, response);
		}
		catch (Exception e)
		{
			logger.error("Encounter error while ", e);
		}
		
		
	}

}
