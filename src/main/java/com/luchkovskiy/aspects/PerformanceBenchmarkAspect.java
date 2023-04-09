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
public class PerformanceBenchmarkAspect {
    @Pointcut("execution(public * com.luchkovskiy.repository.implementations.*.*(..)))")
    public void benchmarkPointCut() {
    }

    @Around("benchmarkPointCut()")
    public Object measureMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000;
        log.info("Method [" + joinPoint.getSignature().getName() + "] executed in {" + duration + "} microseconds");
        return result;
    }
}
