package com.luchkovskiy.aspects;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceBenchmarkAspect {
    @Pointcut("execution(* com.luchkovskiy.repository.*(..))")
    public void benchmarkPointCut() {
    }

    @Around("benchmarkPointCut()")
    public Object measureMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();

        Object result = joinPoint.proceed();

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("Method [" + joinPoint.getSignature() + "] executed in {" + duration + "} nanoseconds");

        return result;
    }
}
