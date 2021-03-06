package io.sbed.common.aspect;

import io.sbed.common.exception.SbedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author
 * @Description: (Redis切面处理类)
 * @date 2017-6-23 15:07
 */
@Aspect
@Configuration
public class RedisAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //是否开启redis缓存  true开启   false关闭
    @Value("${spring.redis.open: #{false}}")
    private boolean open;

    @Around("execution(* io.sbed.common.cache.RedisUtils.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        try {
            result = point.proceed();
        } catch (Exception e) {
            logger.error("redis error", e);
            throw new SbedException("Redis服务异常");
        }
        return result;
    }
}
