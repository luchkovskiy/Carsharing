package com.luchkovskiy.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(public * com.luchkovskiy.repository.implementations.*.*(..)))")
    public void loggingPointCut() {
    }

    @Around("loggingPointCut()")
    public Object logExecuteTime(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Method " + joinPoint.getSignature() + " start");
        Object proceed = joinPoint.proceed();
        log.info("Method " + joinPoint.getSignature() + " finished");
        return proceed;
    }
}
