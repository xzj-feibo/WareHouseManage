package com.xzj.aspect;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/17 16:53
 */
import com.xzj.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.*;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Slf4j
@Component
@Order(1)     //优先执行此切面，数字越小，优先级越高
public class WebLogAspect {
    @Pointcut("execution(* com.xzj.controller..*.*(..))")
    public void logPointCut(){}

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint){
        //RequestContextHolder.getRequestAttributes()是Spring框架中的一个静态方法，用于获取当前线程中的请求属性对象。
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("请求地址：{}",request.getRequestURL().toString());
        log.info("请求方法：{}",request.getMethod());
        //getDeclaringTypeName()返回方法所在类的全名称，getName()返回方法名称
        log.info("方法签名信息：{},{}",joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName());
        log.info("请求参数：{}", JsonUtil.getJsonString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "logPointCut()",returning = "ret")
    public void doAfterReturn(Object ret){
        log.debug("返回值：{}",JsonUtil.getJsonString(ret));
    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = point.proceed();
        log.info("业务层耗时：{} ms",(System.currentTimeMillis() - startTime));
        return proceed;
    }
}
