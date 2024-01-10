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

@Aspect
@Slf4j
@Component
@Order(1)
public class DaoLogAspect {
    @Pointcut("execution(* com.xzj.mapper.*Mapper.*(..))")
    public void logPointCut(){}


    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        log.info("数据库开始执行：{}",point.getSignature().toShortString());
        Object proceed = point.proceed();
        log.info("数据库耗时：{} ms",(System.currentTimeMillis() - startTime));
        return proceed;
    }
}
