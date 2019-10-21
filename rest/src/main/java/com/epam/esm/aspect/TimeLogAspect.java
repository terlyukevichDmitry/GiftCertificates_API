package com.epam.esm.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
@Aspect
@Component
public class TimeLogAspect {

    /**
     * Logger.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(TimeLogAspect.class);

    /**
     * Method to get time, how many times method worked.
     * @param proceedingJoinPoint object.
     * @return object element.
     * @throws Throwable, because we'll have exception situation.
     */
    @Around("@annotation(com.epam.esm.aspect.aspectsannotation.Timed) && execution(public * *(..))")
    public Object getTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object object = proceedingJoinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        LOGGER.info("{}.{} took {} ms",
                proceedingJoinPoint
                        .getSignature()
                        .getDeclaringType()
                        .getSimpleName(),
                proceedingJoinPoint
                            .getSignature()
                            .getName(),
                    duration);
        return object;
    }
}