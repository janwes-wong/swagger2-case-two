package com.janwes.mall.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.mall.aspect
 * @date 2021/6/29 17:35
 * @description controller层切面类
 */
@Aspect
@Component
public class ControllerAspect {

    // 使用springboot原生的jackson
    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger log = LoggerFactory.getLogger(ControllerAspect.class);

    /**
     * 定义切点Pointcut
     */
    @Pointcut("execution(* com.janwes.mall.controller.*.*(..))")
    public void executeService() {
    }

    /**
     * 环绕通知
     *
     * @return
     */
    @Around("executeService()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        // 获取当前request请求属性对象
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        // 获取请求路径
        String url = request.getRequestURL().toString();
        // 获取请求方法
        String method = request.getMethod();
        // 获取连接参数 例如 http://localhost:8080/login?username=jack 得到的为：username=jack
        String queryString = request.getQueryString();

        Object[] args = joinPoint.getArgs();
        String params = "";
        if (args.length > 0) {
            // post请求
            if ("POST".equals(method)) {
                Object object;
                object = args[0];
                Map<String, Object> map = this.getKeyAndValue(object);
                params = objectMapper.writeValueAsString(map);
            } else if ("GET".equals(method)) {
                // get请求
                params = queryString;
            }
        }
        log.info("api request === method:{} url:{}", method, url);
        log.info("api request === parameters:{}", params);

        // 校验参数

        // result的值为被拦截方法的返回值
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        log.info("api response === result:{}", objectMapper.writeValueAsString(result));
        log.info("api response === runtime:{}", (end - start));
        return result;
    }

    private Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<>();
        // 获取类对象
        if (obj == null) {
            obj = new Object();
        }
        Class<?> clazz = obj.getClass();
        // 获取类中所有属性集合
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            // 设置私有属性可以访问
            field.setAccessible(true);
            try {
                // 获取到属性的值
                Object val = field.get(obj);
                // 设置map集合的键值
                map.put(field.getName(), val);
            } catch (IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
        }
        return map;
    }

    /**
     * 检验参数是否通过
     *
     * @param args
     * @return
     */
    public boolean checkParams(Object[] args) {
        for (Object arg : args) {
            if (arg != null) {

            }
        }
        return true;
    }
}
