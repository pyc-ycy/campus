package com.pyc.campus.aspect;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file WebLogAspect
 * @pack com.pyc.campus.aspect
 * @date 2021/1/27
 * @time 8:38
 * @E-mail 2923616405@qq.com
 **/

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class WebLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(* com.pyc.campus.controller.*.*(..))")
    public void logPointCut(){}

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint)throws Throwable{
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("请求地址：" + request.getRequestURL().toString());
        logger.info("HTTP METHOD :" + request.getMethod());

        logger.info("CLASS_METHOD :" + joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName());
        logger.info("参数 ：" + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "logPointCut()")
    public void doAfterReturning(Object ret) throws Throwable{
        logger.debug("返回值 ：" + ret);
    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable{
        long startTime = System.currentTimeMillis();
        Object ob = pjp.proceed();
        logger.info("耗时 ：" + (System.currentTimeMillis() - startTime));
        return ob;
    }
}
