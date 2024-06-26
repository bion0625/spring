package springbook.learningtest.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SimpleMornitoringAspect {

    @Pointcut("execution(* hello(..))")
    private void all() {}

    @Pointcut("execution(* *..*Controller.*(..))")
    private void controller() {}

    @Pointcut("execution(* *..*Service.*(..))")
    private void service() {}

    @Pointcut("execution(* *..*Dao.*(..))")
    private void dao() {}

//    @Around("all()")
    @Around("execution(* *(..))")
    public Object printParametersAndReturnVal(ProceedingJoinPoint pip) throws Throwable {
        Object ret = pip.proceed();
        return ret;
    }

//    @Before("execution(* *(..))")
    @Before("controller() || service() || dao()")
    public void logJoinPoint(JoinPoint jp) {
        System.out.println("=====================================================");
        System.out.println(jp.getSignature().getDeclaringType());
        System.out.println(jp.getSignature().getName());
        for (Object arg : jp.getArgs()) {
            System.out.println(arg);
        }
        System.out.println("=====================================================");
    }
}
