package com.github.kubenext.uaa.aop;

import com.github.kubenext.config.SpringProfiles;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import java.util.Arrays;

/**
 * Aspect for logging execution of service and repository Spring components.
 * By default, it only runs with the "dev" profile.
 *
 * @author shangjin.li
 */
@Aspect
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    private final Environment env;

    public LoggingAspect(Environment env) {
        this.env = env;
    }

    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {}

    @Pointcut("within(com.github.kubenext.uaa.repository..*)"+
            " || within(com.github.kubenext.uaa.service..*)"+
            " || within(com.github.kubenext.uaa.web.rest..*)")
    public void applicationPackagePointcut() {}

    /**
     * Advice that logs methods throwing exceptions.
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        if (env.acceptsProfiles(Profiles.of(SpringProfiles.SPRING_PROFILE_DEVELOPMENT))) {
            logger.error(
                "Exception in {}.{}() with cause = `{}` and exception = `{}`",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                e.getCause() != null ? e.getCause() : "NULL",
                e.getMessage(),
                e
            );
        } else {
            logger.error(
                "Exception in {}.{}() with cause = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                e.getCause() != null ? e.getCause() : "NULL"
            );
        }
    }

    /**
     * Advice that logs when a method is entered and exited.
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug(
                "Enter: {}.{}() with argument[s] = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs())
            );
        }
        try {
            Object result = joinPoint.proceed();
            if (logger.isDebugEnabled()) {
                logger.debug(
                    "Exit: {}.{}() with result = {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    result
                );
            }
            return result;
        } catch (IllegalArgumentException e) {
            logger.error(
                "Illegal argument: {} in {}.{}()",
                Arrays.toString(joinPoint.getArgs()),
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName()
            );
            throw e;
        }
    }

}
