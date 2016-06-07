/*
* Copyright (c) 2014 Javaranger.com. All Rights Reserved.
*/
package com.feizi.redis.cache;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @Desc
 * @Author feizi
 * @Date 2016/6/7 9:21
 * @Package_name com.feizi.redis.cache
 */
public class MethodCacheInterceptor implements MethodInterceptor{

    private RedisTemplate<Serializable, Object> redisTemplate;
    private Long defaultCacheExpireTime = 10L;//缓存默认的过期时间，这里设置了10秒

    public void setRedisTemplate(RedisTemplate<Serializable, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object value = null;

        final String targetName = invocation.getThis().getClass().getName();
        String methodName = invocation.getMethod().getName();

        Object[] arguments = invocation.getArguments();
        String key = getCacheKey(targetName, methodName, arguments);

        try {
            //判断是否有缓存
            if(exists(key)){
                return getCache(key);
            }

            value = invocation.proceed();
            if(null != value){
                final String tkey = key;
                final Object tvalue = value;
                new Thread(new Runnable() {
                    public void run() {
                        setCache(tkey, tvalue, defaultCacheExpireTime);
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(null == value){
                return invocation.proceed();
            }
        }

        return value;
    }

    /**
     * @Desc 创建缓存key
     * @Author feizi
     * @Date 2016/6/7 9:30
     * @param
     */
    private String getCacheKey(String targetName, String methodName, Object[] arguments){
        StringBuffer sb = new StringBuffer();
        sb.append(targetName).append("_").append(methodName);
        if((null != arguments) && (arguments.length > 0)){
            for (int i = 0; i < arguments.length; i++){
                sb.append("_").append(arguments[i]);
            }
        }
        return sb.toString();
    }

    /**
     * @Desc 判断缓存中是否有对应的value
     * @Author feizi
     * @Date 2016/6/7 9:31
     * @param
     */
    public boolean exists(final String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * @Desc 根据key值读取缓存value值
     * @Author feizi
     * @Date 2016/6/7 9:33
     * @param
     */
    public Object getCache(final String key){
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);

        return result;
    }

    /**
     * @Desc 写入缓存
     * @Author feizi
     * @Date 2016/6/7 9:38
     * @param
     */
    public boolean setCache(final String key, Object value, Long expireTime){
        boolean result = false;

        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);

            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
