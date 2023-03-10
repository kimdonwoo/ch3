package com.fastcampus.ch3.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggingAdvice {
    // pointcut - 부가 기능이 적용될 메서드의 패턴
    @Around("execution(* com.fastcampus.ch3.aop.MyMath.add*(..))")
    public Object methodCallLog(ProceedingJoinPoint pjp) throws Throwable{
        // BeforeAdvice
        long start = System.currentTimeMillis();
        System.out.println("<<[start] " + pjp.getSignature().getName()+ Arrays.toString(pjp.getArgs()));

        // target의 메서드를 호출
        Object result = pjp.proceed();

        //AfterAdvice
        System.out.println("result=" + result);
        System.out.println("[end] >> " + (System.currentTimeMillis() - start) + "ms");
        return result;
    }
}
