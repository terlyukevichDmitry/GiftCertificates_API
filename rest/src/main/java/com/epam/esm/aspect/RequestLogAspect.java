package com.epam.esm.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
@Aspect
@Component
public class RequestLogAspect {

    /**
     * Logger.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(RequestLogAspect.class);

    /**
     * Method to get info about http method.
     * @param proceedingJoinPoint element.
     * @return object element.
     * @throws Throwable, because we'll have exception situation.
     */
    @Around("@annotation(com.epam.esm.aspect.aspectsannotation.Requested) && execution(public * *(..))")
    public Object getRequestLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        Object object = proceedingJoinPoint.proceed();
        LOGGER.info("{} {} from {}", request.getMethod(), request.getRequestURI(), request.getRemoteAddr(),
                    request.getHeader("user-id"));
        return object;
    }
}