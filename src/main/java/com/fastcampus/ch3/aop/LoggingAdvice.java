package com.fastcampus.ch3.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggingAdvice {
    @Around("execution(* com.fastcampus.ch3.aop.*.*(..))")
    public Object methodCallLog(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("start" + pjp.getSignature().getName() + Arrays.toString(pjp.getArgs()));

        Object result = pjp.proceed();

        System.out.println("result = " + result);
        System.out.println(" end" + (System.currentTimeMillis() - start));
        return result;
    }
}
