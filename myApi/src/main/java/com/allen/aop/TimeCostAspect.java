package com.allen.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Author: allen
 * @Date: 2022/3/26 1:54 PM
 * @Description: AOP切面。包含5个切面通知方法。
 **/

@Component
@Aspect
public class TimeCostAspect {

    private final static Logger logger = LoggerFactory.getLogger(TimeCostAspect.class);

    //    @Pointcut("execution(* com.allen.dao.NewStaff.*(..))")
//    @Pointcut("execution(* com.allen.service.impl.StaffInfoServiceImpl.*(..))")
    @Pointcut("execution(* com.allen.service.proxiedBeans..*.*(..))")
    public void pointCut() {
    }

    @Around(value = "pointCut()")
    public Object costTime(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取方法参数值数组
        Object[] args = joinPoint.getArgs();
        //得到其方法签名【被增强方法的method对象和返回值类型】
//		MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        String methodName = joinPoint.getSignature().getName();
        Object object = joinPoint.getTarget();
        //获取方法参数类型数组
//		Class[] paramTypeArray = methodSignature.getParameterTypes();
        long startTime = System.currentTimeMillis();
        //动态修改其参数
        //注意，如果调用joinPoint.proceed()方法，则修改的参数值不会生效，必须调用joinPoint.proceed(Object[] args)
        Object result = joinPoint.proceed(args);
        long costTime = System.currentTimeMillis() - startTime;
        logger.info("[{} - {}]方法耗时：{} ms", object, methodName, costTime);
        //如果这里不返回result，则目标对象实际返回值会被置为null
        return result;
    }

    @Before("execution(* com.allen.jwt.JWTUtil.createJWT(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("准备创建token【" + methodName + "】的<前置通知>, 入参:[{}]", Arrays.asList(joinPoint.getArgs()));
    }

    @After(value = "pointCut()")
    public String afterAdvice(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }

    @AfterThrowing(value = "pointCut()")
    public String afterThrowing(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }

    @AfterReturning(value = "pointCut()")
    public String afterReturningAdvice(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }
}
