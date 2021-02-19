package com.mb.ext.web.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Order(5)
@Component
public class ResponseLogAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(public * com.mb.ext.web.controller..*.*(..))")
    public void responseLog() {
    }

    @Before("responseLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        //打印请求日志在ReqLoggerFilter
    }

    @AfterReturning(returning = "ret", pointcut = "responseLog()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {
        // 处理完请求，返回内容
        ServletRequestAttributes attributes=(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();

        HttpServletRequest httpServletRequest=attributes.getRequest();

        StringBuilder logMessage = new StringBuilder("[")
                .append(httpServletRequest.getRemoteHost()).append(" ")
                .append(httpServletRequest.getMethod()).append(" ")
                .append(httpServletRequest.getRequestURI()).append("]");

        logger.info(logMessage.toString());
//        logger.info("Response Message : " + JSON.toJSONString(ret));
    }

}